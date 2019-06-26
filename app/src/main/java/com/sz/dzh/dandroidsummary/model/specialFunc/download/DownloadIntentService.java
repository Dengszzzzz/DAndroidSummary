package com.sz.dzh.dandroidsummary.model.specialFunc.download;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.App;
import com.sz.dzh.dandroidsummary.bean.EventBean;
import com.sz.dzh.dandroidsummary.utils.Constant;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * 下载服务
 * 继承IntentService 是因为读写文件比较耗时，所以用这个开了子线程的Service，而且停止服务的后，也会继续执行子线程
 * 1.判断文件下载程度，是否已完成下载，下载完成直接安装apk
 * 2.开启通知栏
 * 3.下载apk，通知栏显示下载进度
 *
 * 当把targetSdk设为26，也就是Android 8.0后，记得填坑
 * 比如通知栏没显示问题，吊不起安装页面问题等
 */
public class DownloadIntentService extends IntentService {

    private static final String TAG = "DownloadIntentService";
    public static String downLoadPath;

    private  final String NOTIFICATION_CHANNEL_ID = "com.sz.dzh.dandroidsummary";
    private  final String NOTIFICATION_CHANNEL_NAME = "apk_download_channel";
    private NotificationManager mNotifyManager;  //通知管理类
    private Notification mNotification;

    private int downloadId = 101;
    private String mDownloadFileName;

    public static String DOWNLOAD_DIR;  //下载目录

    public DownloadIntentService() {
        super("DownloadService");
        DOWNLOAD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + App.ctx.getPackageName() + "/download/";
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //下载地址
        String downloadUrl = intent.getExtras().getString("download_url");
        //文件名
        mDownloadFileName = intent.getExtras().getString("download_file");
        KLog.d(TAG, "下载地址：" + downloadUrl);
        KLog.d(TAG, "文件名" + mDownloadFileName);
        downLoadPath = DOWNLOAD_DIR + mDownloadFileName;

        //1.判断文件是否存在，具提的下载进度，如果已下载完成，则执行安装
        final File file = new File(downLoadPath);
        long range = 0;
        int progress = 0;
        if (file.exists()) {
            //文件存在，找到文件已下载多少KB，计算下载进度
            //用路径作为key不妥，后台可能设置各个版本下载链接是一样的。所以用apk版本名合适一些
            range = SPDownloadUtil.getInstance().get(mDownloadFileName, 0);
            progress = (int) (range * 100 / file.length());
            //判断是否已下载完整
            if (range == file.length()) {
                EventBus.getDefault().post(new EventBean(Constant.EventCode.DOWNLOAD_APK_SUCCESS,null));
                //installApp(file);
                return;
            }
        }
        KLog.d(TAG, "range = " + range);

        //2.设置通知栏显示下载进度
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_download);
        remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
        remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");
        createNotification(remoteViews);

        //3.下载apk
        DownloadRetrofitUtils.getInstance().downloadFile(range, downloadUrl, mDownloadFileName, new DownloadCallBack() {
            @Override
            public void onProgress(int progress) {
                KLog.e("已下载" + progress + "%");
                remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
                remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");
                mNotifyManager.notify(downloadId, mNotification);
            }

            @Override
            public void onCompleted() {
                KLog.d(TAG, "下载完成");
                mNotifyManager.cancel(downloadId);
                EventBus.getDefault().post(new EventBean(Constant.EventCode.DOWNLOAD_APK_SUCCESS,null));
                //installApp(file);
            }

            @Override
            public void onError(String msg) {
                KLog.d(TAG, "下载发生错误--" + msg);
                mNotifyManager.cancel(downloadId);
            }
        });
    }

    /**
     * 创建通知栏
     * targetSdk >= 26 时，系统不会默认添加Channel，反之低版本则会默认添加；
     * @param remoteViews
     */
    private void createNotification(RemoteViews remoteViews){
        //1.创建通知通道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //参数：通道id、名字、优先级。
            NotificationChannel notifyChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            notifyChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            mNotifyManager.createNotificationChannel(notifyChannel);
        }
        //2.创建Builder对象
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder =  new Notification.Builder(this, NOTIFICATION_CHANNEL_ID);
        } else {
            builder =  new Notification.Builder(this);
        }
        builder.setContent(remoteViews)
                .setTicker("正在下载")
                .setSmallIcon(R.mipmap.ic_launcher);
        //3.将Builder对象转变成普通的notification
        mNotification = builder.build();
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(downloadId, mNotification);
    }

    /**
     * 安装apk
     */
    private void installApp() {
        File file = new File(DownloadIntentService.downLoadPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

}