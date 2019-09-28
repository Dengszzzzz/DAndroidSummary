package com.sz.dengzh.javasummary.bean;

import com.socks.library.KLog;

/**
 * Created by dengzh on 2019/9/10
 */
public class DogBean extends AnimalBean{

    @Override
    public void yell() {
        KLog.e("DogBean yell()");
    }
}
