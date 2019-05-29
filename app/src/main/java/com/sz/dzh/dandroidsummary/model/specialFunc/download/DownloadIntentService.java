package com.sz.dzh.dandroidsummary.model.specialFunc.download;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.App;

import java.io.File;

/**
 * 下载服务
 * 继承IntentService 是因为读写文件比较耗时，所以用这个开了子线程的Service，而且停止服务的后，也会继续执行子线程
 * 1.判断文件下载程度，是否已完成下载，下载完成直接安装apk
 * 2.开启通知栏
 * 3.下载apk，通知栏显示下载进度
 */
public class DownloadIntentService extends IntentService {

    private static final String TAG = "DownloadIntentService";
    private NotificationManager mNotifyManager;
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
        downloadUrl = "http://admin123.pplussport.com/android/PPlus_V1.0.0.apk";

        //1.判断文件是否存在，具提的下载进度，如果已下载完成，则执行安装
        final File file = new File(DownloadIntentService.DOWNLOAD_DIR + mDownloadFileName);
        long range = 0;
        int progress = 0;
        if (file.exists()) {
            //文件存在，找到文件已下载多少KB，计算下载进度
            //用路径作为key不妥，后台可能设置各个版本下载链接是一样的。所以用apk版本名合适一些
            range = SPDownloadUtil.getInstance().get(mDownloadFileName, 0);
            progress = (int) (range * 100 / file.length());
            //判断是否已下载完整
            if (range == file.length()) {
                installApp(file);
                return;
            }
        }
        KLog.d(TAG, "range = " + range);

        //2.设置通知栏显示下载进度
        final RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notify_download);
        remoteViews.setProgressBar(R.id.pb_progress, 100, progress, false);
        remoteViews.setTextViewText(R.id.tv_progress, "已下载" + progress + "%");
        //新建Builder对象
        Notification.Builder builder = new Notification.Builder(this)
                .setContent(remoteViews)
                .setTicker("正在下载")
                .setSmallIcon(R.mipmap.ic_launcher);
        //将Builder对象转变成普通的notification
        mNotification = builder.build();
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyManager.notify(downloadId, mNotification);

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
                installApp(file);
            }

            @Override
            public void onError(String msg) {
                KLog.d(TAG, "下载发生错误--" + msg);
                mNotifyManager.cancel(downloadId);
            }
        });
    }


    /**
     * 安装apk
     * @param file
     */
    private void installApp(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

}