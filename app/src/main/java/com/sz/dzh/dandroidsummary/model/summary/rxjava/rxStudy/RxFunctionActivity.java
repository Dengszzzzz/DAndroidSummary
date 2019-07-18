package com.sz.dzh.dandroidsummary.model.summary.rxjava.rxStudy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by administrator on 2018/11/8.
 * 功能性操作符
 * 1.subscribe（）   订阅，即连接观察者 & 被观察者
 * 2.delay（）       使得被观察者延迟一段时间再发送事件
 * 3.do()            各个事件操作符
 * 4.retry()         重试，即当出现错误时，让被观察者（Observable）重新发射数据
 * retryUntil（）
 * retryWhen（）
 * 5.repeat（）                  无条件地、重复发送 被观察者事件
 * repeatWhen（Integer int）   传入参数 = 重复发送次数有限
 * <p>
 * <p>
 * 此类功能符应用场景：
 * 1.线程操作（切换 / 调度 / 控制 ）
 * 2.轮询
 * 3.发送网络请求时的差错重试机制
 */
public class RxFunctionActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_rx_function);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("功能性操作符");
    }

    @OnClick({R.id.btn_do, R.id.btn_retry, R.id.btn_retryUntil, R.id.btn_retryWhen, R.id.btn_repeat, R.id.btn_repeatWhen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_do:
                doRx();
                break;
            case R.id.btn_retry:
                break;
            case R.id.btn_retryUntil:
                break;
            case R.id.btn_retryWhen:
                break;
            case R.id.btn_repeat:
                break;
            case R.id.btn_repeatWhen:
                break;
        }
    }


    private void doRx() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new Throwable("发生错误了"));
            }
        })
                //1.当Observable每发送1次数据事件就会调用1次
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Exception {
                        Log.d(TAG, "doOnEach: " + integerNotification.getValue());
                    }
                })
                // 2. 执行Next事件前调用
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doOnNext: " + integer);
                    }
                })
                // 3. 执行Next事件后调用
                .doAfterNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "doAfterNext: " + integer);
                    }
                })
                // 4. Observable正常发送事件完毕后调用
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doOnComplete: ");
                    }
                })
                // 5. Observable发送错误事件时调用
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "doOnError: " + throwable.getMessage());
                    }
                })
                // 6. 观察者订阅时调用
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.e(TAG, "doOnSubscribe: ");
                    }
                })
                // 7. Observable发送事件完毕后调用，无论正常发送完毕 / 异常终止
                .doAfterTerminate(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doAfterTerminate: ");
                    }
                })
                // 8. 最后执行
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.e(TAG, "doFinally: ");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });

    }


}
