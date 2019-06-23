package com.sz.dzh.dandroidsummary.model.summary.premission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 2018/9/29.
 * 权限工具类
 */
public class PermissionsUtils {

    private final int mRequestCode = 199;   //权限请求码

    private IPermissionsResult mListener;
    private static PermissionsUtils instance;

    private PermissionsUtils() {}
    public static PermissionsUtils getInstance(){
        if(instance == null){
            synchronized (PermissionsUtils.class){
                if(instance == null){
                    instance = new PermissionsUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 检测权限是否已授权
     * @param context
     * @param permissions  权限数组
     * @param listener     回调结果
     */
    public void checkPermissions(Activity context,String[] permissions,@NonNull IPermissionsResult listener){
        mListener = listener;
        if(Build.VERSION.SDK_INT < 23){  //6.0才申请权限
            mListener.accept();
            return;
        }

        //mNoList 填装尚未授权的权限
        List<String> mNoList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if(ContextCompat.checkSelfPermission(context,permissions[i]) != PackageManager.PERMISSION_GRANTED){
                //判断用户是否勾选过不再询问
                if(ActivityCompat.shouldShowRequestPermissionRationale(context,permissions[i])){
                    //用户拒绝过这个权限了，应该提示用户，为什么需要这个权限。
                    
                }else{
                    mNoList.add(permissions[i]);
                }
            }
        }

        //申请权限
        if(mNoList.size()>0){ //有权限未通过
            ActivityCompat.requestPermissions(context,permissions,mRequestCode);
        }else{
            //权限已通过
            mListener.accept();
        }
    }

    /**
     * @param requestCode  自定义的权限请求码
     * @param permissions  请求的权限名称数组
     * @param grantResults 弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
     *                     PackageManager.PERMISSION_DENIED：被拒绝。
     *                     PackageManager.PERMISSION_GRANTED：被授权。
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean hasPermissionDismiss = false;//是否有权限未通过
        if(mRequestCode == requestCode){
            for (int i = 0; i < grantResults.length; i++) {
                if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                    hasPermissionDismiss = true;
                }
            }
            //如果有权限没有被允许
            if(hasPermissionDismiss){
                mListener.forbit();
            }else{
                mListener.accept();
            }
        }
    }


    public interface IPermissionsResult {
        void accept();  //允许权限
        void forbit();  //拒绝权限
    }
}
