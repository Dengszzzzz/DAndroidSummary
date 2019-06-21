package com.sz.dzh.dandroidsummary.model.summary.netSummary.okhttp;

import com.socks.library.KLog;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by administrator on 2018/8/20.
 * 自定义拦截器
 * 查看源码可知：
 * 拦截器，Interceptor在RealCall的getResponseWithInterceptorChain()中可看到调用，
 * 我们自定义的拦截器排在最前面，加入到拦截器列表里，系统默认加入了几个拦截器，
 * 如BridgeInterceptor、CacheInterceptor等。使用过程可到RealInterceptorChain里查看。
 *
 * intercepotr的调用情况大概如下：RealCall.proceed(),index为0 - >RealInterceptorChain.proceed(),且index+1 ->RealCall.proceed(),index为1如此循环
 * 当有自定义拦截器时，第一个先加入拦截器列表。
 */
public class LogInterceptor implements Interceptor {

    private String TAG = getClass().getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        //1.打印请求数据
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        //2.响应结果
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        //3.打印数据
        KLog.e(TAG, "\n");
        KLog.e(TAG, "------------请求Start----------------");
        KLog.e(TAG, "| " + request.toString());   //Request{method=POST, url=http://192.168.1.128:9003/mobile/nouser/indexv1, tag=null}
        //对于Post方法，请求参数在FormBody里，要做特殊处理
        String method = request.method();
        if ("POST".equals(method)) {
            if (request.body() instanceof FormBody) {
                StringBuilder sb = new StringBuilder();
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i)).append("=").append(body.encodedValue(i)).append(",");
                }
                sb.delete(sb.length() - 1, sb.length());
                KLog.e(TAG, "| RequestParams:{" + sb.toString() + "}");
            }
        }
        KLog.e(TAG, "| Response:" + content);
        KLog.e(TAG, "----------响应End:" + duration + "毫秒----------");

        return response.newBuilder().body(okhttp3.ResponseBody.create(mediaType, content)).build();
    }
}
