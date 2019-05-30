package com.sz.dzh.dandroidsummary.model.summary.service.keeplive;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;

import com.socks.library.KLog;

import java.util.List;


/**
 * Created by dml on 2018/3/3.
 * 后台进程保活管理类
 * 1.设置前台服务 (仿微信后台保活) 无效
 *   目的：解决点击home键使app长时间停留在后台，内存不足被kill
 *
 * 2.监听锁屏，解锁广播  保留1像素Activity (仿手Q)
 *   目的：在大多数国产手机下，进入锁屏状态一段时间，省电机制会kill后台进程
 *
 */

public class KeepLiveManager{

    /**
     * 前台进程的NotificationId
     */
    private final static int GRAY_SERVICE_ID = 1001;
    private static KeepLiveManager instance;
    //1像素的透明Activity
    private PixelActivity activity;
    //监听锁屏/解锁的广播（必须动态注册）
    private LockReceiver lockReceiver;
    private Service mService;


    public static KeepLiveManager getInstance(){
        if(instance == null){
            synchronized (KeepLiveManager.class){
                instance = new KeepLiveManager();
            }
        }
        return instance;
    }

    /*********************************** 设置通知栏，前台服务 *************************************/
    /**
     * 设置服务为前台服务
     * @param service
     */
    public void setServiceForeground(Service service){
        mService = service;
        //设置service为前台服务，提高优先级
        if (Build.VERSION.SDK_INT < 18) {
            //Android4.3以下 ，此方法能有效隐藏Notification上的图标
            service.startForeground(GRAY_SERVICE_ID, new Notification());
        } else if(Build.VERSION.SDK_INT>18 && Build.VERSION.SDK_INT<25){
            //Android4.3 - Android7.0，此方法能有效隐藏Notification上的图标
            Intent innerIntent = new Intent(service, GrayInnerService.class);
            service.startService(innerIntent);
            service.startForeground(GRAY_SERVICE_ID, new Notification());
        }else{
            //Android7.1 google修复了此漏洞，暂无解决方法（现状：Android7.1以上app启动后通知栏会出现一条"正在运行"的通知消息）
            service.startForeground(GRAY_SERVICE_ID, new Notification());
        }
    }


    /**
     * 辅助Service
     */
    public static class GrayInnerService extends Service {
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
    /*********************************** 设置通知栏，前台服务 *************************************/

    /******************************锁屏/解锁广播  开启or关闭1像素页面*********************************/

    /**
     * 动态 注册锁屏/解锁广播
     * @param context
     */
    public void registerReceiver(Context context){
        lockReceiver = new LockReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(lockReceiver,filter);
    }

    /**
     * 注销锁屏/解锁广播
     * @param context
     */
    public void unRegisterReceiver(Context context){
        if(lockReceiver!=null){
            context.unregisterReceiver(lockReceiver);
        }
    }

    /**
     * 广播接收者
     * 1.接收到锁屏广播，开启1像素Activity
     * 2.接收到解锁广播，关闭1像素Activity
     * */
    class LockReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case Intent.ACTION_SCREEN_OFF:  //锁屏
                    KLog.e("锁屏");
                    startLiveActivity(context);
                    break;
                case Intent.ACTION_USER_PRESENT: //解锁
                    KLog.e("解锁");
                    destroyLiveActivity();
                    if(!isServiceRunning(context,mService.getClass().getName())){
                        context.startService(new Intent(context,KeepliveService.class));
                    }
                    break;
            }
        }
    }

    private void startLiveActivity(Context context){
        Intent intent = new Intent(context,PixelActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void destroyLiveActivity(){
        if(activity!=null){
            activity.finish();
        }
    }

    public void setKeepLiveActivity(PixelActivity activity){
        this.activity = activity;
    }
    /******************************锁屏/解锁广播 开启or关闭1像素页面**********************************/


    /**
     * 用来判断服务是否运行.
     *
     * @param className Service 类全名
     * @return true 在运行 false 不在运行
     */
    private boolean isServiceRunning(Context context,String className) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (serviceList.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo info : serviceList) {
            if (info.service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }


}
