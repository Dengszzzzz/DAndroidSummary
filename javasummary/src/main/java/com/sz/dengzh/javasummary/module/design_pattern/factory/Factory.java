package com.sz.dengzh.javasummary.module.design_pattern.factory;

/**
 * Created by dengzh on 2019/10/4
 * 抽象工厂类
 */
public abstract class Factory {

    /**
     * 具体生产什么由子类去实现
     * @param clz 产品对象类类型
     * @param <T> 具体的产品对象
     * @return
     */
    public abstract <T extends Product> T createProduce(Class<T> clz);
}
