package com.sz.dzh.dandroidsummary.model.specialFunc.crop;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.jaeger.library.StatusBarUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.socks.library.KLog;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dengzh.commonlib.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.model.specialFunc.download.DownloadIntentService;
import com.sz.dzh.dandroidsummary.utils.imageUtils.BitmapUtils;
import com.sz.dzh.dandroidsummary.utils.imageUtils.ImageUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by dengzh on 2019/9/28
 * 裁剪2  使用SimpleCropView
 */
public class CropActivity extends BaseActivity {

    private final String TAG = getClass().getSimpleName();

    @BindView(R.id.ll_control)
    LinearLayout llControl;
    @BindView(R.id.needOffsetView)
    View needOffsetView;
    @BindView(R.id.iv_show)
    CropImageView ivShow;

    private String path;      //原图路径
    private String cutPath;   //剪切图路径
    private File compressFile;  //压缩后文件
    private boolean isCropped;  //是否裁剪成功

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_camera2);
        ButterKnife.bind(this);
        StatusBarUtil.setTransparentForImageView(this, needOffsetView);
        new RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        PictureSelector.create(this)
                                .openCamera(PictureMimeType.ofImage())
                                //.enableCrop(true)   //剪切  1：1
                                //.withAspectRatio(1, 1)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                    } else {
                        ToastUtils.showToast("需要读写权限和相机权限");
                        finish();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    LocalMedia media = selectList.get(0);
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    path = media.getPath();
                    if (TextUtils.isEmpty(path)) {
                        ToastUtils.showToast("相机拍照出错");
                        finish();
                    }
                    Glide.with(this).load(path).into(ivShow);
                    llControl.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            finish();
        }
    }

    @OnClick({R.id.iv_close, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                finish();
                break;
            case R.id.btn_submit:
                crop();
               /* if (compressFile != null) {
                    ToastUtils.showToast("图片上传成功");
                } else {
                    ToastUtils.showToast( "图片压缩中，请稍等");
                }*/
                break;
        }
    }

    private void crop(){
        //1.创建裁剪保存路径
        File file = new File(ImageUtils.getCacheDir() + File.separator + "picture_cache",BitmapUtils.getFileName() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        cutPath = file.getPath();
        Uri cutSaveUri;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            cutSaveUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", file);
        }else{
            cutSaveUri = Uri.fromFile(file);
        }

        //开始裁剪且保存
        ivShow.startCrop(cutSaveUri, new CropCallback() {
            @Override
            public void onSuccess(Bitmap cropped) {
                isCropped = true;
                KLog.e(TAG,"裁剪成功");
                ivShow.setImageBitmap(cropped);
            }

            @Override
            public void onError(Throwable e) {
                KLog.e(TAG,"裁剪失败");
            }
        }, new SaveCallback() {
            @Override
            public void onSuccess(Uri uri) {
                KLog.e(TAG,"保存成功");
                compress();
            }

            @Override
            public void onError(Throwable e) {
                KLog.e(TAG,"保存失败" + e.toString());
                compress();
            }
        });
    }

    /**
     * 压缩
     */
    private void compress() {
        if(!isCropped){
            return;
        }
        ImageUtils.compress(this, cutPath, new OnCompressListener() {
            @Override
            public void onStart() {
                KLog.e(TAG, "压缩开始");
            }

            @Override
            public void onSuccess(File file) {
                KLog.e(TAG, "压缩成功，图片路径：" + file.getPath());
                KLog.e(TAG, "压缩成功，图片大小：" + file.length() / 1024 + "KB");
                compressFile = file;
            }

            @Override
            public void onError(Throwable e) {
                KLog.e(TAG, "压缩失败" + e.toString());
            }
        });
    }

    @Override
    protected void onDestroy() {
        //删除压缩的缓存
        //PictureFileUtils.deleteCacheDirFile(this);
        ImageUtils.deleteCacheDirImgFile();
        super.onDestroy();
    }

}
