package com.sz.dzh.dandroidsummary.model.summary.netSummary.retrofit;


import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dengzh on 2017/12/14.
 * 服务接口基类
 * 此处定义泛型是无法实现的
 */

public interface ApiService {

    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    Call<RetrofitBean> getCall();
}
