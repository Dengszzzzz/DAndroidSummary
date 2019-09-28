package com.sz.dzh.dandroidsummary.model.viewDetails.dialog.dialogFragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sz.dengzh.commonlib.CommonConfig;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.utils.AppUtils;
import com.sz.dengzh.commonlib.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.utils.imageUtils.BitmapUtils;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dengzh on 2018/5/7.
 *
 * 有问题，待修改
 */

public class SelectPhotoDialogFragment extends BaseDialogFragment {

    public static final int PHOTO_REQUEST_TAKEPHOTO = 1; // 拍照
    public static final int PHOTO_REQUEST_GALLERY = 2;   // 从相册中选择
    private static final int CROP_SMALL_PICTURE = 10;    // 剪切图片

    private Uri imageUri;
    private int isCrop;    //传1的意义是，不吊起切图


    Unbinder unbinder;

    /**
     * @param isCrop  0-剪切图片  1-不剪切图片
     * @return
     */
    public static SelectPhotoDialogFragment newInstance(int isCrop){
        SelectPhotoDialogFragment fragment = new SelectPhotoDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("isCrop",isCrop);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        isCrop = getArguments().getInt("isCrop");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.Dialog_FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContainerView = inflater.inflate(R.layout.dialog_select_photo2, null);
        unbinder = ButterKnife.bind(this, mContainerView);
        return mContainerView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.takePhotoTv, R.id.selectPhotoTv, R.id.cancleTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.takePhotoTv:  //拍照
                headCamera();
                break;
            case R.id.selectPhotoTv:  //选择照片
                headGallery();
                break;
            case R.id.cancleTv:  //取消
                dismiss();
                break;
        }
    }

    /**
     * 点击拍照
     */
    private void headCamera() {
        // 1、创建存储照片的文件
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + CommonConfig.ctx.getPackageName() +  File.separator + "DASImage";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(path, BitmapUtils.getFileName() + ".jpg");

        //2、获取文件Uri，注意Android 7.0及以上获取文件
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            imageUri = FileProvider.getUriForFile(getContext(), getContext().getPackageName() + ".fileProvider", file);
        }else{
            imageUri = Uri.fromFile(file);
        }

        //3.调取系统相机拍照
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(it, PHOTO_REQUEST_TAKEPHOTO);
    }

    /**
     * 点从相册选取
     */
    private void headGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        // 指定调用相机拍照后照片的储存路径
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode){
                case PHOTO_REQUEST_TAKEPHOTO:  //拍照
                    if(isCrop == 1){
                        if (imageUri != null&&mDialogSelect!=null) {
                            mDialogSelect.onSelected(1,imageUri.toString());
                            dismiss();
                        }
                    }else {
                        cropImageUri(imageUri, 600, 400, CROP_SMALL_PICTURE);
                    }
                    break;
                case PHOTO_REQUEST_GALLERY:  //画库选择图片
                    if (data != null) {
                        if(isCrop == 1){
                            if (imageUri != null&&mDialogSelect!=null) {
                                mDialogSelect.onSelected(2,data.getData().toString());
                                dismiss();
                            }
                        }else {
                            cropImageUri(data.getData(), 600, 400, CROP_SMALL_PICTURE);
                        }
                    }
                    break;
                case CROP_SMALL_PICTURE:   //剪切图片
                    if (imageUri != null&&mDialogSelect!=null) {
                        mDialogSelect.onSelected(3,imageUri.toString());
                        dismiss();
                    }
                    break;
            }
        }
    }

    /**
     * 调用截图
     */
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
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
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", false); // no face detection
        startActivityForResult(intent, requestCode);
    }
}
