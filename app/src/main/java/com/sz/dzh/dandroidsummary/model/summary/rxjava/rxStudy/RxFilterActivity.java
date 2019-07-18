package com.sz.dzh.dandroidsummary.model.summary.rxjava.rxStudy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by administrator on 2018/11/8.
 * 过滤操作符
 * filter（）                         过滤 特定条件的事件
 * ofType（）                         过滤 特定数据类型的数据
 * skip（） / skipLast（）             跳过某个事件
 * distinct（） / distinctUntilChanged（）     过滤事件序列中重复的事件 / 连续重复的事件
 * take（）                           指定观察者最多能接收到的事件数量
 * takeLast（）                       指定观察者只能接收到被观察者发送的最后几个事件
 * <p>
 * 应用场景：
 * 1.根据 指定条件 过滤事件
 * 2.根据 指定事件数量 过滤事件
 * 3.根据 指定时间 过滤事件
 * 4.根据 指定事件位置 过滤事件
 */
public class RxFilterActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_rx_filter);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("过滤操作符");
    }

    @OnClick({R.id.btn_filter, R.id.btn_ofType, R.id.btn_skip, R.id.btn_skipLast, R.id.btn_distinct, R.id.btn_distinctUntilChanged, R.id.btn_take, R.id.btn_takeLast})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_filter:
                break;
            case R.id.btn_ofType:
                break;
            case R.id.btn_skip:
                break;
            case R.id.btn_skipLast:
                break;
            case R.id.btn_distinct:
                break;
            case R.id.btn_distinctUntilChanged:
                break;
            case R.id.btn_take:
                break;
            case R.id.btn_takeLast:
                break;
        }
    }



    /**
     * 需要使用RxBinding
     * 实际应用：
     * 模仿短时间多次点击按钮， throttleFirst()规定n秒内，只发送一次事件
     */
    private void throttleFirst() {
        //1.每1s发送事件   2.5s内只会发送一次
        Observable.interval(1, TimeUnit.SECONDS)
                .throttleFirst(5, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "发送了网络请求" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * 需要使用RxBinding
     * 实际应用：
     * 联网搜索，EditText监听，当输入改变时，发起请求。
     * 问题：
     * 1.避免在没输完整时就发起了请求    debounce(n)操作符，只有当用户输入关键字后 n毫秒才发射数据
     * 2.只需要最新一次请求的返回        switchMap操作符，只会发送最近的Observables
     */
    private void debounce() {

    }


}
