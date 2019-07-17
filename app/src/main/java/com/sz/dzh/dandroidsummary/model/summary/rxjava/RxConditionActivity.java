package com.sz.dzh.dandroidsummary.model.summary.rxjava;


import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by administrator on 2018/11/9.
 * 条件/布尔操作符
 * 1.all()         判断发送的每项数据是否都满足设置的函数条件,若满足，返回 true；否则，返回 false。
 * 2.takeWhile（）  判断发送的每项数据是否满足设置函数条件，条件 = true时，才发送Observable的数据，当为false后，再也不发生事件
 * 3.skipWhile（）  判断发送的每项数据是否满足设置函数条件，条件 = false时，才发送Observable的数据
 * 4.takeUntil（）  执行到某个条件时，停止发送事件
 * 5.skipUntil()    和takeUntil相反
 * 6.sequenceEqual（）
 * 7.contains（）
 * 8.isEmpty（）
 * 9.amb（）         当需要发送多个 Observable时，只发送 先发送数据的Observable的数据，而其余 Observable则被丢弃。
 */
public class RxConditionActivity extends RxOperatorBaseActivity{
    @Override
    protected String getSubTitle() {
        return "条件/布尔操作符";
    }

    @Override
    protected void doSomething() {
        //all();
        //takeWhile();
       // skipWhile();
       // takeUntil();
        //skipUntil();
        //amb();
        defaultEmpty();
    }


    /**
     * all()
     * 作用： 判断发送的每项数据是否都满足 设置的函数条件
     *       若满足，返回 true；否则，返回 false
     */
    private void all(){
        Observable.just(1,2,3,4,5,6)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return (integer<=10);
                        //判断Obervable发送的全部数据是否都满足integer<=10
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                Log.d(TAG,"result is "+ aBoolean);
                // 输出返回结果
            }
        });
    }

    /**
     * takeWhile（）
     * 作用:  判断发送的每项数据是否满足 设置函数条件
     *       若发送的数据满足该条件，则发送该项数据；否则不再发送
     */
    private void takeWhile(){
        // 1. 每1s发送1个数据 = 从0开始，递增1，即0、1、2、3
        Observable.interval(1, TimeUnit.SECONDS)
                // 2. 通过takeWhile传入一个判断条件
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return aLong<3;
                        //当发送的数据满足<3时才发送Observable的数据
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG,"发送了事件 "+ aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    /**
     * skipWhile()
     * 作用: 判断发送的每项数据是否满足 设置函数条件
     *      直到该判断条件 = false时，才开始发送Observable的数据
     */
    private void skipWhile(){
        // 1. 每隔1s发送1个数据 = 从0开始，每次递增1
        Observable.interval(1, TimeUnit.SECONDS)
                // 2. 通过skipWhile（）设置判断条件
                .skipWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return (aLong<5);
                        // 直到判断条件不成立 = false = 发射的数据≥5，才开始发送数据
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG,"发送了事件 "+ aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     *  takeUntil（）
     *  接收第一个Observable（调用takUtil的Observable）发送的数据，当第二个Observable
     *  （takUtil参数中的Observable）发送数据时，两个Obserable会同时取消订阅。
     */
    private void takeUntil(){
        //（原始）第1个Observable：每隔1s发送1个数据 = 从0开始，每次递增1
        Observable.interval(1, TimeUnit.SECONDS)
                // 第2个Observable：延迟5s后开始发送1个Long型数据
                .takeUntil(Observable.timer(5, TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {

                    //结果：
                    // 当第 5s 时，第2个 Observable 开始发送数据，于是（原始）第1个 Observable 停止发送数据

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "接收到了事件"+ aLong  );
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


    /**
     *  skipUntil（）
     *  作用: 等到 skipUntil（）传入的Observable开始发送数据，（原始）第1个Observable的数据才开始发送数据
     */
    private void skipUntil(){
        //（原始）第1个Observable：每隔1s发送1个数据 = 从0开始，每次递增1
        Observable.interval(1, TimeUnit.SECONDS)
                // 第2个Observable：延迟5s后开始发送1个Long型数据
                .skipUntil(Observable.timer(5, TimeUnit.SECONDS))
                .subscribe(new Observer<Long>() {

                    //结果
                    //5s后（ skipUntil（） 传入的Observable开始发送数据），（原始）第1个Observable的数据才开始发送

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "接收到了事件"+ aLong);
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


    /**
     * SequenceEqual（）
     * 作用: 判定两个Observables需要发送的数据是否相同
     *       若相同，返回 true；否则，返回 false
     */
    private void sequenceEqual(){
        Observable.sequenceEqual(Observable.just(3,4,5), Observable.just(3,4,5))
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG,"2个Observable是否相同："+ aBoolean);
                    }
                });
    }

    /**
     *  contains（）
     *  判断发送的数据中是否包含指定数据
     */
    private void contains(){
        Observable.just(3,4,5)
                .contains(3)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG,"result is "+ aBoolean);
                    }
                });
    }

    /**
     * amb()
     * 作用： 当需要发送多个 Observable时，只发送 先发送数据的Observable的数据，而其余 Observable则被丢弃。
     */
    private void amb(){
        // 设置2个需要发送的Observable & 放入到集合中
        List<ObservableSource<Integer>> list = new ArrayList<>();
        // 第1个Observable延迟1秒发射数据
        list.add(Observable.just(1,2,3).delay(1, TimeUnit.SECONDS));
        // 第2个Observable正常发送数据
        list.add(Observable.just(4,5,6));

        // 一共需要发送2个Observable的数据
        // 但由于使用了amba（）,所以仅发送先发送数据的Observable,也就是第2个Observable
        Observable.amb(list).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "接收到了事件 "+integer);
                //结果：发送数据的Observable的数据 = 4，5，6
            }
        });
    }

    /**
     *  defaultIfEmpty（）
     *  作用:
     *  在不发送任何有效事件（ Next事件）、仅发送了 Complete 事件的前提下，发送一个默认值
     */
    private void defaultEmpty(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // 不发送任何有效事件
                //  emitter.onNext(1);
                //  emitter.onNext(2);

                // 仅发送Complete事件
                emitter.onComplete();
            }
        }).defaultIfEmpty(10)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "接收到了事件"+ integer  );
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
