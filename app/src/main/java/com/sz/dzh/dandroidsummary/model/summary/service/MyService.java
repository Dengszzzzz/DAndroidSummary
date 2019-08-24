package com.sz.dzh.dandroidsummary.model.summary.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.model.MainActivity;

/**
 * Created by administrator on 2018/8/2.
 * 1.继承service，记得在AndroidManifest中部署
 * 2.生命周期
 *   startService, onCreate()->onStartCommand()->onDestroy();
 *   bindService,onCreate()->onbind()->onUnbind()->onDestroy；
 */

public class MyService extends Service {

    private String TAG = "MyService";

    private MyBinder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.d(TAG,"onCreate（）");
        binder = new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.d(TAG,"onStartCommand（）");

        testForeground();
        //testThread();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        KLog.d(TAG,"onDestroy（）");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        KLog.d(TAG,"onBind（）");
        //返回实例
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        KLog.d(TAG,"onUnbind（）");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder {

        public void connectTest(){
            KLog.d(TAG,"Binder 的 connectTest() 方法执行");
        }

    }


    /**
     * 1.测试子线程
     * service运行在UI线程，如果要做耗时任务，需要新开线程工作，如下
     * 如果要开启子线程，可以使用IntentService
     */
    private void testThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                KLog.d(TAG,"开始service耗时任务");
                try {
                    Thread.sleep(1000 * 3);  //模拟耗时3s
                    KLog.d(TAG,"结束service的耗时任务");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    private String notificationId = "channelId";
    private String notificationName = "channelName";
    private NotificationManager notificationManager;

    /**
     * 2.前台服务
     *
     * 应用必须在创建服务后的五秒内调用该服务的 startForeground() 函数。
     */
    private void testForeground(){
        //1.创建
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //2.在8.0系统以上，需要创建NotificationChannel
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(notificationId,notificationName,NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        //3.构建"点击通知后打开MainActivity"的Intent对象
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        //4.新建Builder对象
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("DAS测试服务")
                .setContentText("通知内容")
                .setSmallIcon(R.drawable.hide_pwd_icon)
                .setContentIntent(pendingIntent);  //设置点击通知后的操作
        //5.在8.0系统以上，设置ChannelId
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            builder.setChannelId(notificationId);
        }
        Notification notification = builder.build(); //将Builder对象转变成普通的notification
        startForeground(1,notification);
    }
}
