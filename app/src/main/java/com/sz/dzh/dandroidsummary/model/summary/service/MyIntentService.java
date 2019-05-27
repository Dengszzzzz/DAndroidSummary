package com.sz.dzh.dandroidsummary.model.summary.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.socks.library.KLog;

/**
 * Created by administrator on 2018/8/2.
 * IntentService 创建了子线程Handler来执行子线程工作，
 * 也就是onHandleIntent()是执行在子线程的，当完成任务后，该IntentService会自动停止。
 */

public class MyIntentService extends IntentService {

    private String TAG = "MyIntentService";

    //需要提供一个无参构造函数
    public MyIntentService() {
        super("MyIntentService");   //调用父类的有参构造函数
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.d(TAG,"onCreate（）");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.d(TAG,"onStartCommand（）");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //打印当前线程的id
        KLog.d(TAG,"Thred id is" + Thread.currentThread().getId());
        KLog.d(TAG,"开始MyIntentService耗时任务");
        try {
            Thread.sleep(1000 * 3);  //模拟耗时3s
            KLog.d(TAG,"结束MyIntentService的耗时任务");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 这个服务在运行结束后会自动停止。
     * 如果手动调用stopService(),子线程还会继续执行完整。
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.d(TAG,"MyIntentService onDestroy()");
    }
}
