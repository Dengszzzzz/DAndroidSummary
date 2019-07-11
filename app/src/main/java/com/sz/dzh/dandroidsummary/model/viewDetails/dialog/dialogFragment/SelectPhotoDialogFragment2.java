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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.App;
import com.sz.dzh.dandroidsummary.utils.AppUtils;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.utils.imageUtils.BitmapUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dengzh on 2018/5/7.
 */

public class SelectPhotoDialogFragment2 extends BaseDialogFragment implements View.OnClickListener {

    public static final int REQUEST_TAKEPHOTO = 1; // 拍照
    public static final int REQUEST_GALLERY = 2;   // 从相册中选择
    private static final int CROP_SMALL_PICTURE = 10;    // 剪切图片

    private String IMAGE_PATH;  //图片保存路径
    private Uri imageUri;   //Uri.parse(IMAGE_FILE_LOCATION); ;//The Uri to store the big bitmap
    private int tag;   //传1的意义是，不吊起切图


    private TextView takePhotoTv,selectPhotoTv,cancleTv;

    /**
     * @param tag 0-剪切图片  1-不剪切图片
     * @return
     */
    public static SelectPhotoDialogFragment2 newInstance(int tag) {
        SelectPhotoDialogFragment2 fragment = new SelectPhotoDialogFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        tag = getArguments().getInt("tag");
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
            //1.创建图片保存文件夹
            IMAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    File.separator + App.ctx.getPackageName() +  File.separator + "DASImage";
            File dirFile = new File(IMAGE_PATH);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            File file = new File(IMAGE_PATH, BitmapUtils.getFileName() + ".jpg");
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
        // 指定调用相机拍照后照片的储存路径
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
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
                case REQUEST_TAKEPHOTO:  //拍照，data为null
                    //data为null，imageUri为content://com.sz.dzh.dandroidsummary.fileProvider/my_images/DAS_1562837817999.jpg
                    if (tag == 1) {
                        if (imageUri != null && mDialogSelect != null) {
                            mDialogSelect.onSelected(1, imageUri.toString());
                            dismiss();
                        }
                    } else {
                        onCropImage(imageUri, 600, 400, CROP_SMALL_PICTURE);
                    }
                    break;
                case REQUEST_GALLERY:  //画库选择图片
                    //data 为 content://media/external/file/1710928 flg=0x1
                    if (data != null) {
                        if (tag == 1) {
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
