package com.sz.dzh.dandroidsummary.model.summary.service.keeplive;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sz.dzh.dandroidsummary.ProcessConnectionAidlInter;

/**
 * 守护进程
 */
public class GuardService extends Service{

    private String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnectionAidlInter.Stub() {};
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(this,KeepliveService.class),
                mServiceConnection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG,"GuardService:建立链接");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG,"GuardService:断开链接，重新绑定");
            startService(new Intent(GuardService.this,KeepliveService.class));
            bindService(new Intent(GuardService.this,KeepliveService.class),
                    mServiceConnection, Context.BIND_IMPORTANT);
        }
    };
}
