package com.sz.dzh.dandroidsummary.model.summary.service.keeplive;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.ProcessConnectionAidlInter;

/**
 *  要存活的进程，目的减少被杀死的几率，杀死后拉活
 *
 * 1、 onStartCommand方法，返回START_STICKY
 * 2、 提升Service优先级,AndroidMainfest 设置  <intent-filter android:priority="1000"/>
 * 3、 提升Service进程优先级, 即设置为前台进程（显示个通知栏）
 * 4、 监听关键广播，如解锁、开关机等，做一些处理，如
 *     1）开启一个像素的Activity
 *     2）播放无声广播等 （可能导致位置BUG，不推荐）
 *     3）判断Service是否存活，不存活重启Service
 * 5、 在onDestory（）重启Service
 * 6、 双进程守护，aidl通信
 *
 */

public class KeepliveService extends Service {

    private String TAG = getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        //创建的时候，注册广播
        KeepLiveManager.getInstance().registerReceiver(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"KeepliveService process = " + android.os.Process.myPid());
        //设置前台进程
        KeepLiveManager.getInstance().setServiceForeground(this);
        //绑定守护线程
        bindService(new Intent(this,GuardService.class),
                mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        KeepLiveManager.getInstance().unRegisterReceiver(this);
        //重启Service
        startService(new Intent(this,KeepliveService.class));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnectionAidlInter.Stub() {};
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG,"KeepliveService:建立链接");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG,"KeepliveService:断开链接，重新绑定");
            startService(new Intent(KeepliveService.this,GuardService.class));
            bindService(new Intent(KeepliveService.this,GuardService.class),
                    mServiceConnection, Context.BIND_IMPORTANT);
        }
    };
}
