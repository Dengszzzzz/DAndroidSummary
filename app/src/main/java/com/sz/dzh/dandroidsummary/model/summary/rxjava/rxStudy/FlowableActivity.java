package com.sz.dzh.dandroidsummary.model.summary.rxjava.rxStudy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by administrator on 2018/11/9.
 * Flowable:背压
 * 1.对应的观察者 Subscriber
 * 2.缓存区，默认大小 128,与Flowable的buffersize大小有关，当满128时，会溢出报错
 */
public class FlowableActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_rx_flowable);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("背压问题");
    }

    @OnClick({R.id.btn_asynchronous, R.id.btn_synchronized})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_asynchronous:
                asynchronizedTest();
                break;
            case R.id.btn_synchronized:
                synchronizedTest();
                break;
        }
    }

    /**
     * 演示创建，也可以使用链式
     */
    private void create() {
        //步骤1：上流发送事件，传入背压参数
        Flowable<Integer> upStream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR);

        // 步骤2：下流
        Subscriber<Integer> downStream = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                // 对比Observer传入的Disposable参数，Subscriber此处传入的参数 = Subscription
                // 相同点：Subscription具备Disposable参数的作用，即Disposable.dispose()切断连接, 同样的调用Subscription.cancel()切断连接
                // 不同点：Subscription增加了void request(long n)
                Log.d(TAG, "onSubscribe");
                s.request(Long.MAX_VALUE);

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
        //步骤3：订阅
        upStream.subscribe(downStream);
    }

    /**
     * Flowable与Observable在功能上的区别主要是 多了背压的功能
     * 异步订阅情况举例
     * 1.上流发送很多事件，放入缓存区
     * 2.下流 指定从缓存区 获取多少个事件  request()
     */
    private void asynchronizedTest() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {

                //在异步中，emitter.requested() 是128，
                Log.d(TAG, "观察者可接收事件数量 = " + emitter.requested());

                // 一共发送4个事件
                Log.d(TAG, "发送事件 1");
                emitter.onNext(1);
                Log.d(TAG, "发送事件 2");
                emitter.onNext(2);
                Log.d(TAG, "发送事件 3");
                emitter.onNext(3);
                Log.d(TAG, "发送事件 4");
                emitter.onNext(4);
                Log.d(TAG, "发送完成");
                emitter.onComplete();

//                //模拟缓存超过128
//                for (int i = 0;i< 129; i++) {
//                    Log.d(TAG, "发送了事件" + i);
//                    emitter.onNext(i);
//                }
//                emitter.onComplete();
//
            }
        }, BackpressureStrategy.ERROR)  //缓存区超过128，直接抛出异常
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // 在异步订阅情况下，一定要调用request，否则下流不接收事件
                        // 作用：决定观察者能够接收多少个事件
                        s.request(3);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "onError：", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }


    /**
     * 同步订阅：
     * 不存在缓存区，request用于控制流速，下流需要多少，上流才发送多少
     */
    private void synchronizedTest() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                // 调用emitter.requested()获取当前观察者需要接收的事件数量
                long n = emitter.requested();
                Log.d(TAG, "观察者可接收事件" + n);
                // 根据emitter.requested()的值，即当前观察者需要接收的事件数量来发送事件
                for (int i = 0; i < n; i++) {
                    Log.d(TAG, "发送了事件" + i);
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        // 设置观察者每次能接受10个事件
                        s.request(10);
                        //可以多次调用request
                        s.request(20);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });


    }




    /***
     * 背压模式
     * 面向对象：针对缓存区
     * 作用：当缓存区大小存满、被观察者仍然继续发送下1个事件时，该如何处理的策略方式
     * 缓存区大小存满、溢出 = 发送事件速度 ＞ 接收事件速度 的结果 = 发送 & 接收事件不匹配的结果
     *
     * BackpressureStrategy.ERROR;     直接抛异常
     * BackpressureStrategy.MISSING;   友好提示：缓存区满了
     * BackpressureStrategy.BUFFER;    将缓存区设为无限大
     * BackpressureStrategy.DROP;      超过缓存区大小(128)的事件丢弃
     * BackpressureStrategy.LATEST;    只保存最后事件，超过缓存区大小(128)的事件丢弃
     *
     * 对于非自身手动创建的Flowable，可以用 onBackpressureXXX()方法
     *  Flowable.interval(1, TimeUnit.MILLISECONDS).onBackpressureBuffer()
     *
     * **/
}
