package com.sz.dzh.dandroidsummary.model.summary.netSummary.okhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by administrator on 2018/8/20.
 * OkHttp 源码解读及总结
 * 目的：大概清楚整个流程，对相关类有一定了解。
 * 1.Request、Response、Call 基本概念
 * 2.x.newCall(request) 返回RealCall对象，RealCall实现了Call接口，所以要看RealCall的enqueue()
 * 3.x.enqueue()引出Dispatcher（调度分配器），这个类维护一个线程池，看它的enqueue(AsyncCall call)进行下一步解读。
 * 4.AsyncCall是RealCall的内部类，它的execute()，在execute()中可以得到Response，网络响应结果。
 * 然后finally中调用了client.dispatcher().finished(this);在finished()中会移除正在运行的call，把readyCalls的一个请求放入runningCalls中。
 * 5.Request的分析到此为止，下面看Response的分析。首先Interceptor(拦截器)，在RealCall的getResponseWithInterceptorChain()中可看到。
 * intercepotr的调用情况大概如下：RealCall.proceed(),index为0 - >RealIntercepotrChain.proceed(),且index+1 ->RealCall.proceed(),index为1如此循环
 * <p>
 * 参考：
 * https://blog.csdn.net/qq_29152241/article/details/82011539
 * https://www.jianshu.com/p/cb444f49a777
 * ...
 */
public class OkHttpActivity extends BaseActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    String desc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_okhttp);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("OkHttp 源码解读");

        desc =  "OkHttp 源码解读及总结\n" +
                " * 目的：大概清楚整个流程，对相关类有一定了解。\n" +
                " * 1.Request、Response、Call 基本概念\n" +
                " * 2.x.newCall(request) 返回RealCall对象，RealCall实现了Call接口，所以要看RealCall的enqueue()\n" +
                " * 3.x.enqueue()引出Dispatcher（调度分配器），这个类维护一个线程池，看它的enqueue(AsyncCall call)进行下一步解读。\n" +
                " * 4.AsyncCall是RealCall的内部类，它的execute()，在execute()中可以得到Response，网络响应结果。\n" +
                " * 然后finally中调用了client.dispatcher().finished(this);在finished()中会移除正在运行的call，把readyCalls的一个请求放入runningCalls中。\n" +
                " * 5.Request的分析到此为止，下面看Response的分析。首先Interceptor(拦截器)，在RealCall的getResponseWithInterceptorChain()中可看到。\n" +
                " * intercepotr的调用情况大概如下：RealCall.proceed(),index为0 - >RealIntercepotrChain.proceed(),且index+1 ->RealCall.proceed(),index为1如此循环\n\n\n"+
                "Dispatcher:\n" +
                " * 1.调度，执行请求，取消请求，结束请求等之类的调用。\n" +
                " * 2.是一个主要在异步请求时用到的策略，在同步请求时用的少\n" +
                " * 3.Dispatcher内部维护了一个线程池，最大并发64个请求，每个主机最大并发5个。\n" +
                " * 备注：某些手机对线程数上限做了限制，比如华为AL10手机，在线程数达到500后就会出现OOM。\n" +
                " * 因为OkHttpClient 每new一次，就会创建一个dispathcer(增加一个线程池)，大概多5、6个线程池后，线程就会满500。\n";

        mTvDesc.setText(desc);
    }

    /**
     * Dispatcher:
     * 1.调度，执行请求，取消请求，结束请求等之类的调用。
     * 2.是一个主要在异步请求时用到的策略，在同步请求时用的少
     * 3.Dispatcher内部维护了一个线程池，最大并发64个请求，每个主机最大并发5个。
     * 备注：某些手机对线程数上限做了限制，比如华为AL10手机，在线程数达到500后就会出现OOM。
     * 因为OkHttpClient 每new一次，就会创建一个dispathcer(增加一个线程池)，大概多5、6个线程池后，线程就会满500。
     */
    private void test1() {
        //简单的请求
        String url = "http://wwww.baidu.com";
        OkHttpClient okHttpClient = new OkHttpClient();
        //x.url()会对url格式验证，http和https。如果是ws或wss，会先替换成http和https。
        Request request = new Request.Builder().url(url).build();
        //x.newCall(request) 返回RealCall对象，RealCall实现了Call接口，所以要看RealCall的enqueue()
        //在enqueue()可看到Dispatcher（调度分配器）
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


    private void test2() {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", "123");
        map.put("name", "哈哈哈哈");
        HttpUtils.doPost(map, "https://www.baidu.com/", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        }, this);
    }

}
