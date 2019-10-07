package com.sz.dengzh.javasummary.module.design_pattern.factory;

/**
 * Created by dengzh on 2019/10/4
 * 抽象产品类
 */
public abstract class Product {

    /**
     * 产品类的抽象方法，由具体的产品类去实现
     */
    public abstract void method();


    /**
     * 在任何需要生成复杂对象的地方，都可以使用工厂方法模式，主要分为抽象产品、抽象工厂、具体产品、具体工厂。
     * 多个具体工厂叫多工厂模式。
     * 工厂类只有一个，且工厂方法为静态方法，则叫简单工厂模式 or 静态工厂模式。
     *
     * 应用场景：
     * 1、Java里的List和Set。
     * 2、例如有File、XML、DB 这3种方式对数据进行增删改查。
     *    那么增删改查作为抽象产品，File、XML、DB 定义具体产品。
     * */
}
