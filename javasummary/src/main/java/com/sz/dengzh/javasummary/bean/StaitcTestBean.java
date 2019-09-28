package com.sz.dengzh.javasummary.bean;

import com.socks.library.KLog;

/**
 * Created by dengzh on 2019/9/10
 */
public class StaitcTestBean {

    public static int testValue = 1;
    public static int testValue2 = 10;

    public static void handle(){
        KLog.e("StaitcTestBean 的 handle() ---- " + testValue);
    }

    public static void handle2(){
        KLog.e("StaitcTestBean 的 handle2() ---- " + testValue2);
    }

}
