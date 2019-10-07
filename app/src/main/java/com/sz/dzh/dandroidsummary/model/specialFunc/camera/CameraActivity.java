package com.sz.dzh.dandroidsummary.model.specialFunc.camera;

import android.Manifest;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.socks.library.KLog;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dengzh.commonlib.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.utils.glideUtils.GlideUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by dengzh on 2019/9/24
 */
public class CameraActivity extends BaseActivity {

    @BindView(R.id.camera_preview)
    FrameLayout cameraPreview;
    @BindView(R.id.iv_show)
    ImageView ivShow;
    @BindView(R.id.btn_take_photo)
    Button btnTakePhoto;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.resetIv)
    ImageView resetIv;

    private Camera mCamera;
    private File mFile;
    private boolean isPreview; //是否处于预览状态


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_camera);
        ButterKnife.bind(this);
        ToastUtils.showToast("这是个有BUG的自定义相机~");
        new RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .subscribe(granted -> {
                    if (granted) {
                        initView();
                    } else {
                        ToastUtils.showToast("需要读写权限和相机权限");
                        finish();
                    }
                });


    }

    private void initView() {
        //初始化Camera对象
        mCamera = Camera.open();
        CameraPreview mPreview = new CameraPreview(this, mCamera);
        cameraPreview.addView(mPreview);

        //得到相机的参数
        Camera.Parameters parameters = mCamera.getParameters();
        //图片格式
        parameters.setPictureFormat(ImageFormat.JPEG);
        // 设置照片质量
        //parameters.setJpegQuality(100);
        //预览的大小
       // parameters.setPreviewSize(cameraPreview.getMeasuredHeight(), cameraPreview.getMeasuredWidth());
        //设置对焦模式，自动对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        mCamera.setParameters(parameters);
    }



    @OnClick({R.id.btn_take_photo, R.id.btn_submit,R.id.resetIv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_take_photo:
                //获取照片
                isPreview = false;
                changeStatus();
                mCamera.takePicture(null, null, mPictureCallback);
                break;
            case R.id.btn_submit:
                if (mFile != null) {
                    ToastUtils.showToast("提交成功：" + mFile.getName());
                } else {
                    ToastUtils.showToast("提交失败，文件为空");
                }
                break;
            case R.id.resetIv:
                //重新预览
                isPreview = true;
                changeStatus();
                break;
        }
    }

    private void changeStatus(){
        if(isPreview){
            ivShow.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);
            resetIv.setVisibility(View.GONE);
            btnTakePhoto.setVisibility(View.VISIBLE);
        }else{
            ivShow.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
            resetIv.setVisibility(View.VISIBLE);
            btnTakePhoto.setVisibility(View.GONE);
        }
    }


    //获取照片中的接口回调
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            mFile = null;
            //这里就是拍照成功后返回照片的地方，注意，返回的是原图，可能几兆十几兆大，需要压缩处理
            FileOutputStream fos = null;
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "DASImage";
            File dirFile = new File(path);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            String mFilePath = path + File.separator + System.currentTimeMillis() + ".jpg";
            //保存文件
            try {
                File jpgFile = new File(mFilePath);

                FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
                outputStream.write(data); // 写入sd卡中
                outputStream.close(); // 关闭输出流

                //显示图片
                GlideUtils.loadImg(CameraActivity.this, ivShow, mFilePath);

                //Luban压缩
                Luban.with(CameraActivity.this)
                        .load(jpgFile)    //传入原图
                        .ignoreBy(100)    //不压缩的阈值，单位为K
                        .setTargetDir(path) //缓存压缩图片路径
                        .filter(path1 -> !TextUtils.isEmpty(path1))  //设置开启压缩条件
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                mFile = file;
                                KLog.e(TAG, "压缩成功：" + file.length());
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                                KLog.e(TAG, "压缩失败");
                            }
                        }).launch();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }


}
