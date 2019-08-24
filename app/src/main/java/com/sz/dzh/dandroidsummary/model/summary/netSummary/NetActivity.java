package com.sz.dzh.dandroidsummary.model.summary.netSummary;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.socks.library.KLog;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by administrator on 2018/8/17.
 * 网络总结：
 * 了解http协议相关知识，总结okhttp和Retrofit的重要知识点。
 * 网络体系、http、https、socket、TCP/UDP、websocket的相关知识。
 * <p>
 * 网络体系结构：
 * 应用层(http) - 传输层(TCP、UDP) - 网络层(IP) - 数据链路层 - 物理层
 */
public class NetActivity extends BaseActivity {


    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_net);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("网络体系知识");
        webSocketSummary();
    }

    /**
     *
     * 1.网络http协议
     * 1)属于应用层面向对象的协议。
     2)特点：支持C/S;简单快速，通信速度快；无连接(一次问答)；无状态。
     3)请求报文由请求行(Method Request-URI HTTP-Version CRLF)、请求头、空行、和请求数据
     （请求数据不在GET方法中使用，而是在POST方法中使用）4个部分组成。
     4）响应报文由响应状态行、响应头、空行、响应正文组成。
     * */

    /**
     * 2.HTTPS
     * 1.Https是披着SSL外壳的HTTP，也就是Http通讯的时候，利用SSL加密数据包。
     * 2.Https请求分为单向认证和双向认证。
     * 3.单向认证很简单，服务器提供证书 A.cer，将证书放入Assert目录。
     *   代码中读取证书，构建SslContext，再添加到OkHttp里即可。（用过）
     * 4.双向认证比较复杂，客户端和服务器都有证书，发起请求的时候，互相验证。（目前没有用过）
     * 5.常用对称加密有DES，3DES，AES； 常用非对称加密有RSA； 常用摘要算法有MD5
     *
     * */


    /**
     * 3.socket知识
     * 1)Socket即是套接字，是应用层与TCP/IP协议族通信的中间软件抽象层，属于传输层(因为TCP/IP属于传输层)，其实就是封装了TCP/IP协议族的编程接口(API)。
     * socket类型：流套接字(基于TCP协议)。 数据报套接字(基于UDP协议)。
     * 2) socket和http的区别
     * 1.socket属于传输层，解决如何传输数据；http属于应用层，解决如何包装数据。
     * 2.socket服务器推送，http请求响应。
     */
    private void socketSummary() {
        // 简单的客户端socket创建演示,了解就好,记住的意义不大。

        // 创建Socket对象 & 指定服务端的IP及端口号
        try {
            Socket socket = new Socket("192.168.1.101", 1989);
            if (socket.isConnected()) {
                // <-- 操作1：接收服务器的数据 -->
                // 步骤1：创建输入流对象InputStream
                InputStream is = socket.getInputStream();
                // 步骤2：创建输入流读取器对象 并传入输入流对象
                // 该对象作用：获取服务器返回的数据
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                // 步骤3：通过输入流读取器对象 接收服务器发送过来的数据
                br.readLine();


                //<-- 操作2：发送数据 到 服务器 -->

                // 步骤1：从Socket 获得输出流对象OutputStream
                // 该对象作用：发送数据
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write("向服务器发送数据\n".getBytes("utf-8"));
                // 特别注意：数据的结尾加上换行符才可让服务器端的readline()停止阻塞

                // 步骤3：发送数据到服务端
                outputStream.flush();


                //步骤3：断开客户端 & 服务器 连接
                br.close();
                // 断开 客户端发送到服务器 的连接，即关闭输出流对象OutputStream
                br.close();
                // 断开 服务器发送到客户端 的连接，即关闭输入流读取器对象BufferedReader
                socket.close();
                // 最终关闭整个Socket连接
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 4.TCP和UDP的差异
     *  1)TCP速度慢，效率低，占用系统资源高，易被攻击；面向连接；适用于对数据要求高的地方，如http，https，ftp等传输协议。
     *  2)UDP速度快，效率高，比TCP稍安全，但不稳定，很可能丢包；不面向连接；适用于通讯速度尽量快的地方，如视频，语音等。
     * */


    /**
     * 5.websocket
     * WebSocket 是 HTML5 一种新的协议，但很多语言框架也支持。
     * 1) WebSocket同HTTP一样也是应用层的协议，但是它是一种双向通信协议，是建立在TCP之上的，也要经历三次握手。
     * 2）webSocket在建立握手时，数据通过http传输。建成后，数据传输不需要Http协议了。
     * 3) 与socket的区别
     * socket是传输层，且都不算是一个协议；webSocket在应用层，是个协议。这两者没有关系。
     * 文章指引：https://blog.csdn.net/w690333243/article/details/87916542
     */
    private void webSocketSummary() {

        //URI - Java
        //Uri - Android
        //其实Uri是URI的“扩展”，以适应Android系统的需要，所以用Uri足够了。
        //记住 scheme、authority是必须要有的
        String uriStr = "ws://192.168.1.101:8080/test/test.htm?id=12&path=34567";
        Uri uri = Uri.parse(uriStr);
        KLog.d(TAG, "获取Uri中的scheme:  " + uri.getScheme());
        KLog.d(TAG, "获取Uri中的scheme-specific-part:  " + uri.getSchemeSpecificPart());
        KLog.d(TAG, "获取Uri中Authority:  " + uri.getAuthority());
        KLog.d(TAG, "获取Authority中的Host:  " + uri.getHost());
        KLog.d(TAG, "获取Authority中的Port:  " + uri.getPort());
        KLog.d(TAG, "获取Uri中path:  " + uri.getPath());
        KLog.d(TAG, "获取Uri中的query:  " + uri.getQuery());

        StringBuilder builder = new StringBuilder();
        builder.append("Uri 各部分\n")
        .append("ws://192.168.1.101:8080/test/test.htm?id=12&path=34567\n")
        .append("获取Uri中的scheme:  " + uri.getScheme() + "\n")
                .append("获取Uri中的scheme-specific-part:  " + uri.getSchemeSpecificPart() + "\n")
                .append("获取Uri中Authority:  " + uri.getAuthority() + "\n")
                .append("获取Authority中的Host:  " + uri.getHost() + "\n")
                .append("获取Authority中的Port:  " + uri.getPort() + "\n")
                .append("获取Uri中path:  " + uri.getPath() + "\n")
                .append("获取Uri中的query:  " + uri.getQuery());
        mTvDesc.setText(builder.toString());
    }

}
