package com.sz.dzh.dandroidsummary.model.summary.rxjava;


import android.util.Log;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by administrator on 2018/11/6.
 * 转化类操作符
 * Map():
 * FlatMap():
 * ConcatMap():
 * Buffer():
 */
public class RxTransformActivity extends RxOperatorBaseActivity {
    @Override
    protected String getSubTitle() {
        return "转换操作符";
    }

    @Override
    protected void doSomething() {
        map();
        flatMap();
        contactMap();
        buffer();
    }


    /**
     * Map（）
     * 作用: 将被观察者发送的事件转换为任意的类型事件。
     * 应用场景：数据类型转换
     */
    private void map() {
        //举例： 整型 变换成 字符串类型
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);

            }
        }).map(new Function<Integer, String>() {
            //将参数中的Integer类型对象转换成一个String类型对象
            @Override
            public String apply(Integer integer) throws Exception {
                return "使用 Map变换操作符 将事件" + integer + "的参数从 整型" + integer + " 变换成 字符串类型" + integer;
            }
        }).subscribe(new Consumer<String>() {
            //事件的参数类型也由Integer类型->String类型
            @Override
            public void accept(String s) throws Exception {
                KLog.d(TAG, s);
            }
        });
    }


    /**
     * flatMap()
     * 作用：将被观察者发送的事件序列进行 拆分 & 单独转换，再合并成一个新的事件序列，最后再进行发送
     * <p>
     * 原理:
     * 1.为事件序列中每个事件都创建一个 Observable 对象；
     * 2.将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
     * 3.将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
     * 4.新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
     * <p>
     * 应用场景:
     * 无序的将被观察者发送的整个事件序列进行变换
     */
    private void flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件" + integer + "拆分后的子事件" + i);
                    // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                KLog.d(TAG, s);
            }
        });
    }

    /**
     * 和flatMap类似
     * 区别：新事件序列和 被观察者旧序列生成顺序一致
     */
    private void contactMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件" + integer + "拆分后的子事件" + i);
                    // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                    // 最终合并，再发送给被观察者
                }
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                KLog.d(TAG, s);
            }
        });
    }


    /**
     * buffer()
     * 作用:
     * 定期从 被观察者（Obervable）需要发送的事件中 获取一定数量的事件 & 放到缓存区中，最终发送
     * <p>
     * 应用场景:
     * 缓存被观察者发送的事件
     */
    private void buffer() {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 1)  //设置缓存区大小 & 步长
                // 缓存区大小 = 每次从被观察者中获取的事件数量
                // 步长 = 每次获取新事件的数量
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> stringList) {
                        KLog.d(TAG, " 缓存区里的事件数量 = " + stringList.size());
                        for (Integer value : stringList) {
                            KLog.d(TAG, " 事件 = " + value);
                        }
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
     * compose 和 Transformers
     */
    private void compose() {
        Observable.just(1,2,3)
                .compose(new SchedulerTransformer<Integer>())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG,integer + "");
                    }
                });
    }

    /**
     * 定义一个Transformers。
     * 1.ObservableTransformer 能够将一个 Observable/Flowable/Single/Completable/Maybe 对象转换成另一个
     * Observable/Flowable/Single/Completable/Maybe 对象
     * <p>
     * 2.当创建Observable/Flowable...时，compose操作符会立即执行，而不像其他的操作符需要在onNext()调用后才执行。
     *
     * 3.不考虑上下流类型，就用泛型T
     **/
    public class SchedulerTransformer<T> implements ObservableTransformer<T, T> {

        @Override
        public ObservableSource<T> apply(Observable<T> upstream) {
            return upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}
