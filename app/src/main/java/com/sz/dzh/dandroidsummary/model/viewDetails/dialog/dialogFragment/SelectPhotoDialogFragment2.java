package com.sz.dzh.dandroidsummary.model.viewDetails.dialog.dialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.sz.dengzh.commonlib.CommonConfig;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.utils.AppUtils;
import com.sz.dengzh.commonlib.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.utils.imageUtils.BitmapUtils;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dengzh on 2018/5/7.
 * 相册选择和拍照要优化的地方很多，比如选择图片如何选多张图片、拍照的Uri问题、拍照保存的图片路径、图片剪切、加载图片太大等问题。
 * 如果只是简单的选择照片和拍照，记得处理好拍照文件保存路径，Uri问题。注意onActivityResult(...)的返回结果
 */

public class SelectPhotoDialogFragment2 extends BaseDialogFragment implements View.OnClickListener {

    public static final int REQUEST_TAKEPHOTO = 1;       // 拍照
    public static final int REQUEST_GALLERY = 2;         // 从相册中选择
    private static final int CROP_SMALL_PICTURE = 10;    // 剪切图片

    private String imagePath;  //图片保存路径,如 com.sz.dzh.dandroidsummary.fileProvider/my_images/DAS_1562837817999.jpg
    private Uri imageUri;      //图片Uri，如    content://com.sz.dzh.dandroidsummary.fileProvider/my_images/DAS_1562837817999.jpg
    private int type;          //0-剪切图片  1-不剪切图片


    private TextView takePhotoTv,selectPhotoTv,cancleTv;

    /**
     * @param type 0-剪切图片  1-不剪切图片
     * @return
     */
    public static SelectPhotoDialogFragment2 newInstance(int type) {
        SelectPhotoDialogFragment2 fragment = new SelectPhotoDialogFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        type = getArguments().getInt("type");
    }


    //从生命周期的顺序而言，先执行onCreateDialog()，后执行onCreateView()
    //不需自定义view  可以试用onCreateDialog();
    //别同时使用onCreatView和onCreatDialog方法
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mContainerView = getActivity().getLayoutInflater().inflate(R.layout.dialog_select_photo2, null);
        init();
        builder.setView(mContainerView);
        return builder.create();
    }


    private void init() {
        takePhotoTv = mContainerView.findViewById(R.id.takePhotoTv);
        selectPhotoTv = mContainerView.findViewById(R.id.selectPhotoTv);
        cancleTv = mContainerView.findViewById(R.id.cancleTv);
        takePhotoTv.setOnClickListener(this);
        selectPhotoTv.setOnClickListener(this);
        cancleTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.takePhotoTv:  //拍照
                onCamera();
                break;
            case R.id.selectPhotoTv:  //选择照片
                onGallery();
                break;
            case R.id.cancleTv:  //取消
                dismiss();
                break;
        }
    }

    /**
     * 拍照
     */
    private void onCamera() {
        if (AppUtils.hasSdcard()) {
            //1.创建图片文件夹
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + CommonConfig.ctx.getPackageName() +  File.separator + "DASImage";
            imagePath = path +  File.separator + BitmapUtils.getFileName() + ".jpg";
            //创建目录
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File file = new File(imagePath);
            //2.获取Uri
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                imageUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".fileProvider", file);
            }else{
                imageUri = Uri.fromFile(file);
            }
            //3.拍照
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            it.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(it, REQUEST_TAKEPHOTO);
        } else {
            ToastUtils.showToast("SdCard不存在，不允许拍照");
        }
    }

    /**
     * 相册选取
     */
    private void onGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    /**
     * 剪切图片
     */
    private void onCropImage(Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", false); // no face detection
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_TAKEPHOTO:  //拍照，data为null，因为是保存在指定路径下，所以获取图片，直接拿那个路径即可
                    //imageUri为content://com.sz.dzh.dandroidsummary.fileProvider/my_images/DAS_1562837817999.jpg
                    if (type == 1) {
                        if (mDialogSelect != null) {
                            mDialogSelect.onSelected(1, imagePath);
                            dismiss();
                        }
                    } else {
                        onCropImage(imageUri, 600, 400, CROP_SMALL_PICTURE);
                    }
                    break;
                case REQUEST_GALLERY:  //画库选择图片
                    //data 为 content://media/external/file/1710928 flg=0x1
                    if (data != null) {
                        if (type == 1) {
                            if (mDialogSelect != null) {
                                mDialogSelect.onSelected(2, data.getData().toString());
                                dismiss();
                            }
                        } else {
                            onCropImage(data.getData(), 600, 400, CROP_SMALL_PICTURE);
                        }
                    }
                    break;
                case CROP_SMALL_PICTURE:   //剪切图片
                    if (imageUri != null && mDialogSelect != null) {
                        mDialogSelect.onSelected(3, imageUri.toString());
                        dismiss();
                    }
                    break;
            }
        }
    }


}
