package com.sz.dengzh.javasummary.bean;

import com.socks.library.KLog;

/**
 * Created by dengzh on 2019/9/10
 *
 */
public class StaitcTestBean2 extends StaitcTestBean{

    public static int testValue = 2;

    public static void handle(){
        KLog.e("StaitcTestBean2 的 handle() ---- " + testValue);
    }

}
