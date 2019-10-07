package com.sz.dengzh.javasummary.module.design_pattern.factory;

import com.socks.library.KLog;

/**
 * Created by dengzh on 2019/10/4
 */
public class ConcreteProduceA extends Product{
    @Override
    public void method() {
        KLog.e("具体的产品A");
    }
}
