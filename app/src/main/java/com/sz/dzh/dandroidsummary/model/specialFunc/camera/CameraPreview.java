package com.sz.dzh.dandroidsummary.model.specialFunc.camera;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.socks.library.KLog;

import java.io.IOException;

/**
 * Created by dengzh on 2019/9/24
 *
 * https://www.cnblogs.com/my334420/p/6921373.html
 * https://blog.csdn.net/qq_36726461/article/details/100772882
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private String TAG = getClass().getSimpleName();
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        //初始化Camera对象
        mCamera = camera;
        //得到SurfaceHolder对象
        mHolder = getHolder();
        //添加回调，得到Surface的三个生命周期方法
        mHolder.addCallback(this);
        //给mHolder设置缓存信息,在Android 3.0之后就是自动设置的了，所以可以忽略这句代码
        //mHolder.setType(SurfaceHolder.SURFACE_TYPE_HARDWARE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            //设置预览方向
            mCamera.setDisplayOrientation(90);
            //把这个预览效果展示在SurfaceView上面
            mCamera.setPreviewDisplay(holder);
            //开启预览效果
            mCamera.startPreview();
        } catch (IOException e) {
            KLog.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(holder.getSurface() == null){
            return;
        }
        //停止预览效果
        mCamera.stopPreview();
        //重新设置预览效果
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            KLog.d(TAG, "Error starting camera preview: " + e.getMessage());
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
