package com.sz.dzh.dandroidsummary.model.summary.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;

import com.socks.library.KLog;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dengzh.commonlib.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.R;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2019/9/8
 * Handler：实现任意两个线程的数据传递，主要是将子线程的数据传递给主线程
 * 关键对象：Looper、MessageQueue、Message
 * <p>
 * 1、创建步骤
 * 1) Looper.prepare()
 * 创建的handler之前要先调用Looper.prepare()，不然会报错，它的作用是当前thread创建了一个Looper()，Looper又包含一个MessageQueue
 * 2）创建Handler，实现handleMessage()方法。
 * 3) Looper.loop()
 * 没有这个，Handler收不到消息，这个方法开启了死循环，不断从looper内的MessageQueue中取出Message，再通过用
 * dispatchMessage去分发消息，会执行 handleCallback() 或者 handleMessage()。handleCallback()可以在runOnUiThread()方法看到。
 * <p>
 * 2、用法注意点
 * 1）创建Handler注意要用弱引用。
 * 2）子线程创建Handler。
 * <p>
 * 参考：
 * https://blog.csdn.net/wsq_tomato/article/details/80301851
 */
public class HandlerActivity extends BaseActivity {

    private MyHandler myHandler;
    private Handler thandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_handler);
        ButterKnife.bind(this);

        String s = new String();
        myHandler = new MyHandler(this);
        //myHandler.sendEmptyMessageAtTime(1,2000);  //指定时间发送，开机2s发送
        myHandler.sendEmptyMessageDelayed(1,2000);  //延迟2s后发送

    }

    /**
     * 内存泄漏问题？
     * 1、非静态内部类是会隐式持有外部类的引用，所以当其他线程持有了该Handler，线程没有被销毁，
     *   则意味着Activity会一直被Handler持有引用而无法导致回收。
     * 2、MessageQueue中如果存在未处理完的Message，Message的target也是对Activity等的持有引用，也会造成内存泄漏
     *
     * 解决方法：
     * 1）使用静态内部类 + 弱引用的方式
     * 2）在onDestroy()，清空messageQueue
     */
    static class MyHandler extends Handler{
        private WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = mActivity.get();
            if(activity!=null){
                //TODO:
                KLog.d("MyHandler","MyHandler 接收 Message");
                sendEmptyMessageDelayed(1,1000);
            }
        }
    }

    @OnClick(R.id.btn1)
    public void onViewClicked() {
        createHandlerOnThread();
    }

    private void createHandlerOnThread() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                Looper.prepare(); //创建Looper对象 -> 创建MessageQueue
                thandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        KLog.d(TAG, "thread1 收到 message：" + msg.obj.toString());
                        ToastUtils.showToast("thread1 收到 message：" + msg.obj.toString());
                    }
                };
                Looper.loop();  //没有这句，接收不到message
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = thandler.obtainMessage();
                message.obj = "dengzh";
                thandler.sendMessage(message);
            }
        };

        thread1.start();
        thread2.start();
    }

    @Override
    protected void onDestroy() {
        myHandler.removeMessages(1);
        if(thandler!=null){
            thandler.removeCallbacksAndMessages(null);
        }
        super.onDestroy();
    }
}
