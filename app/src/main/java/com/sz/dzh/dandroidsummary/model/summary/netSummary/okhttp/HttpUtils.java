package com.sz.dzh.dandroidsummary.model.summary.netSummary.okhttp;

import android.content.Context;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by administrator on 2018/8/20.
 * okhttp使用，最好写一个工具类来保障OkHttpClient全局唯一
 */
public class HttpUtils {

    private static OkHttpClient mClient;

    private HttpUtils() {
    }

    public static OkHttpClient getInstance() {
        if (mClient == null) {
            synchronized (HttpUtils.class) {
                if (mClient == null) {
                    mClient = new OkHttpClient().newBuilder()
                            .connectTimeout(5, TimeUnit.SECONDS)  //建立连接的超时时间，适用于网络状况正常的情况下，两端连接所用的时间
                            .readTimeout(5, TimeUnit.SECONDS)     //传递数据的超时时间
                            .addInterceptor(new LogInterceptor())
                            .build();
                }
            }
        }
        return mClient;
    }

    public static void doPost(Map<String, String> mapParams, String url, Callback callback, Context context) {
        //1.封装formbody参数
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (String key : mapParams.keySet()) {
            bodyBuilder.add(key, mapParams.get(key));
        }
        RequestBody formBody = bodyBuilder.build();
        //2.创建Request
        Request request = new Request.Builder().url(url).post(formBody).build();
        //3.发起请求
        Call call = getInstance().newCall(request);
        //调用enqueue的都是异步请求
        call.enqueue(callback);
        //同步如下
        /*try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public static void doGet(Map<String, String> mapParams, String url, Callback callback, Context context) {
        //1.整理get的请求参数
        if(mapParams.size()>0){
            StringBuilder sb = new StringBuilder();
            for (String key : mapParams.keySet()) {
                sb.append(key).append("=").append(mapParams.get(key)).append("&");
            }
            sb.delete(sb.length()-1,sb.length());
            url = url + "?" + sb.toString();
        }
        //相比Post，少了.post(formBody)
        Request request = new Request.Builder().url(url).build();
        getInstance().newCall(request).enqueue(callback);
    }

}
