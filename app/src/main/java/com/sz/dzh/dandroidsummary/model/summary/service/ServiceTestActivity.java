package com.sz.dzh.dandroidsummary.model.summary.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import com.socks.library.KLog;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by administrator on 2018/8/2.
 * service 总结
 */

public class ServiceTestActivity extends BaseActivity {

    private boolean isBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_service_test);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("Service总结");
    }

    @OnClick({R.id.startBt, R.id.stopBt, R.id.bindBt, R.id.unBindBt,R.id.start2Bt, R.id.stop2Bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startBt:
                Intent intent = new Intent(this, MyService.class);
                /**
                 * 针对 Android 8.0 的应用尝试在不允许其创建后台服务的情况下使用 startService() 函数，
                 * 则该函数将引发一个 IllegalStateException。
                 * android8.0 以上可以通过startForegroundService启动service
                 * */
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                }else{
                    startService(intent);
                }
                break;
            case R.id.stopBt:
                Intent stopIntent = new Intent(this, MyService.class);
                stopService(stopIntent);
                break;
            case R.id.bindBt:
                //绑定
                Intent bindIntent = new Intent(this, MyService.class);
                bindService(bindIntent, myConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unBindBt:
                //解绑，多次调用unBindService会报异常
                if (isBind) {
                    unbindService(myConnection);
                    isBind = false;
                }
                break;
            case R.id.start2Bt:  //IntentService 启动
                Intent serviceIntent = new Intent(this, MyIntentService.class);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(serviceIntent);
                }else{
                    startService(serviceIntent);
                }
                break;
            case R.id.stop2Bt:   //IntentService 停止
                Intent serviceIntent2 = new Intent(this, MyIntentService.class);
                stopService(serviceIntent2);
                break;
        }
    }

    private ServiceConnection myConnection = new ServiceConnection() {

        //重写onServiceConnected()方法和onServiceDisconnected()方法
        //在Activity与Service建立关联和解除关联的时候调用
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            KLog.d("MyService", "onServiceConnected()");
            isBind = true;
            MyService.MyBinder myBinder = (MyService.MyBinder) iBinder;
            myBinder.connectTest();
        }

        /**
         * onServiceDisconnected() 在连接正常关闭的情况下是不会被调用的,
         * 该方法只在Service 被破坏了或者被杀死的时候调用.
         * @param componentName
         */
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            KLog.d("MyService", "onServiceDisconnected()");
            isBind = false;
        }
    };

}
