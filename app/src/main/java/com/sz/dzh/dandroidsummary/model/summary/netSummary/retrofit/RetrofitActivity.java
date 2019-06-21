package com.sz.dzh.dandroidsummary.model.summary.netSummary.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by administrator on 2018/8/21.
 * Retrofit源码解读及流程
 */
public class RetrofitActivity extends BaseActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_retrofit);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("Retrofit 源码简读");

        mTvDesc.setText("内容写在代码里了，太长不方便在界面显示");
    }

    /**
     * 1.Retrofit的优势
     * 1）功能上：基于Okhttp；RESTful Api设计风格；支持同步异步；通过注解配置请求；可以搭配多种Converter将获得的数据解析&序列化
     * 2）性能上：性能好，处理快；简洁易用，代码简化；支持Rxjava
     * 3）应用场景：任何常用优先选择，特别是后台Api遵循RESTful风格&项目中使用Rxjava。
     *
     *
     * 2.Retrofit 使用的设计模式
     * 1）建造者模式通过Builder类进行创建，简易创建复杂对象。例如 Retrofit创建。
     * 2）工厂模式，XXXFactory。例如Retrofit.class 里的 callFactory，就是网络请求器的工厂。
     * 3）外观模式，代理模式。例如retrofit.create(ApiService.class) 创建网络接口。（使用外观模式进行访问，里面用了代理模式）
     * 4）装饰者模式。例如：callAdapter.adapt()里ExecutorCallbackCall = 装饰者，而里面真正去执行网络请求的还是OkHttpCall。
     *   用这个模式的原因是希望在OkHttpCall发送请求时做一些额外操作，此处就是作线程转换。
     *
     *
     * 3.请求流程
     * 1)通过解析 网络请求接口的注解 配置 网络请求参数
     * 2)通过 动态代理 生成 网络请求对象
     * 3)通过 网络请求适配器(CallAdapter) 将 网络请求对象 进行平台适配
     * 4)通过 网络请求执行器（OkHttpCall） 发送网络请求
     * 5)通过 数据转换器(Converter) 解析服务器返回的数据
     * 6)通过 回调执行器(CallBackExecutor) 切换线程( 子线程->主线程)
     * 7)用户主线程处理返回结果
     *
     *
     * 4.Retrofit  相关的类简要介绍
     *  ServiceMethod：      包含所有网络请求信息的对象
     *  baseUrl：            网络请求的url地址
     *  callFactory：        网络请求工厂
     *  adapterFactories：   网络请求适配器（CallAdapter）工厂的集合
     *  converterFactories： 数据转换器工厂的集合
     *  callbackExecutor：   回调方法执行器
     *
     * 5.CallAdapter
     *   网络请求执行器（Call）的适配器，Retrofit支持 Android、JAVA8、RXJAVA、Guava平台,提供4种CallAdapterFactory，
     *   默认是Android的。它的作用是将默认的网络请求执行器(OKHttpCall) 转换成适合被不同平台调用的网络请求执行器形式。
     *
     *   例如：要实现Rxjava的线程调度，就得用 RxJavaCallAdapterFactoryCallAdapter 将 OkHttpCall 转换成Rxjava(Scheduler)：
     *        也即是在创建Retrofit时，加入 .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) 这句。
     *
     * */


    /**
     * 源码追踪 注释
     * */
    private void test() {
        /**
         * 1.创建Retrofit
         *
         * 分析点1：
         * Builder类分析，可一步一步跳入Builder类->Platform类
         * Builder设置了默认的以下数据，但未真正配置到具体的Retrofit类的成员变量中
         * 平台类型对象：Android
         * 网络请求适配器工厂：CallAdapterFactory
         * 数据转换器工厂： converterFactory
         * 回调执行器：callbackExecutor
         *
         * 分析点2：
         * GsonConverterFactory.create() 实际上是创建了一个含有Gson对象实例的GsonConverterFactory
         *
         * 分析点3：
         * build(),判断callFactory、callbackExecutor等是否已配置，没配置就用默认的(platform类)
         * */
        //步骤2
        Retrofit retrofit = new Retrofit
                .Builder()                 //分析点1
                .baseUrl("http://fanyi.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())  //分析点2
                //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();  //分析点3


        /**
         * 2.创建网络请求实例
         * Retrofit是通过外观模式 & 代理模式 使用create（）方法创建网络请求接口的实例
         * （同时，通过网络请求接口里设置的注解进行了网络请求参数的配置）
         *
         * 步骤3：
         * retrofit.create(ApiService.class)，在这个方法里看到以下内容
         * 1) Proxy.newProxyInstance(xxx);
         *    创建了网络请求接口的动态代理对象，目的为了拿到网络请求接口实例上所有注解。
         * 2）ServiceMethod
         *    ServiceMethod对象包含了访问网络的所有基本信息。
         *    Builder()获取请求接口的注释，参数类型，注解内容。最终在build()可看解析过程。
         *    其实就是能弄清楚要发起一个怎么样的request，且决定response后做何种数据转换，如这里是Gson转换。
         * 3）OkHttpCall
         *     根据第一步配置好的ServiceMethod对象和输入的请求参数创建okHttpCall对象
         * 4）serviceMethod.callAdapter.adapt(okHttpCall);
         *    将第二步创建的OkHttpCall对象传给第一步创建的serviceMethod对象中对应的网络请求适配器工厂的adapt（）
         *    真正去执行网络请求的还是OkHttpCall。callAdapter里只是做了额外线程转换。
         *
         * 步骤4：
         * apiService.getCall()
         * 1）由步骤3分析可得知，apiService对象实际上是动态代理对象 Proxy.newProxyInstance（）
         * 2）调用getCall（）时会被动态代理对象Proxy.newProxyInstance（）拦截，然后调用自身的InvocationHandler # invoke（）
         * 3）然后就是步骤3讲到的解析过程了
         * 4）最终创建并返回一个OkHttpCall类型的Call对象
         * */
        ApiService apiService = retrofit.create(ApiService.class);  //步骤3
        Call<RetrofitBean> call = apiService.getCall();  //步骤4

        //和okhttp一样，enqueue()异步,execute()同步
        //这个call只是一个静态代理，使用静态代理的作用是：在okhttpCall发送网络请求的前后进行额外操作
        call.enqueue(new Callback<RetrofitBean>() {
            @Override
            public void onResponse(Call<RetrofitBean> call, Response<RetrofitBean> response) {
                if (response.isSuccessful()) {
                    response.body();
                }
            }

            @Override
            public void onFailure(Call<RetrofitBean> call, Throwable t) {

            }
        });
    }




    /**
     *
     * */


}
