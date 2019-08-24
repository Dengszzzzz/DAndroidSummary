package com.sz.dzh.dandroidsummary.model.specialFunc.download;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.dengzh.commonlib.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.bean.EventBean;
import com.sz.dzh.dandroidsummary.utils.Constant;
import com.sz.dengzh.commonlib.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

/**
 * Created by dengzh on 2018/4/18.
 * 文件下载
 * 涉及存储权限，注意7.0和8.0的坑
 * 7.0的
 * 8.0之前，未知应用安装权限默认开启；8.0之后，未知应用安装权限默认关闭，且权限入口隐藏。
 */

public class DownloadListActivity extends BaseListShowActivity {

    private String downloadUrl = "http://admin123.pplussport.com/android/iPlus_V1.1.4.apk";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initUI() {
        tvTitle.setText("Service");
    }

    @Override
    protected void initData() {
        addClazzBean("Retrofit2 + RxJava2 断点续传下载",null);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case 0:
                        onRxDownloadApk();
                        break;
                    case 1:
                        break;
                }
            }
        });
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void onRxDownloadApk(){
        Intent intent = new Intent(this, DownloadIntentService.class);
        Bundle bundle = new Bundle();
        bundle.putString("download_url", downloadUrl);
        bundle.putString("download_file", "DAS+_v1.0.1.apk");
        intent.putExtras(bundle);
        startService(intent);
    }


    private final int REQUEST_CODE_APP_INSTALL = 312;
    /**
     * 因为涉及权限申请，所以把安装app的流程放到Activity里来。
     * 也可以考虑在Service中动态获取权限
     * 此处Service下载完成后，通过EventBus通知activity可以开始安装
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownloadSuccess(EventBean eventBean) {
       if(eventBean.getCode() == Constant.EventCode.DOWNLOAD_APK_SUCCESS){
           //8.0以上，判断未知应用安装权限是否开启，没开启引导用户去设置
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
               if (!hasInstallPermission) {
                   Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                   startActivityForResult(intent,REQUEST_CODE_APP_INSTALL);
                   return;
               }
           }
           installApp();
       }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_APP_INSTALL){
            //回调再查一次是否开启权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (hasInstallPermission) {
                    installApp();
                }else{
                    ToastUtils.showToast("若要安装，请允许未知应用安装权限");
                }
            }
        }
    }

    /**
     * 判断是否在7.0以上，7.0以上要用FileProvider
     */
    private void installApp() {
        File file = new File(DownloadIntentService.downLoadPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri apkUri;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            //参数2就是AndroidManifest.xml中provider的authorities
            apkUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", file);
            //临时授权读该Uri代表的文件的权限，不然安装的时候会出现“解析软件包出现问题”。
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else{
            apkUri = Uri.fromFile(file);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //注意别设置成setFlags(...)了,不然前面的addFlags就清掉了。
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
