package com.sz.dengzh.javasummary.module.design_pattern;

/**
 * Created by dengzh on 2019/10/4
 * 单例模式：
 * 饿汉式、懒汉式、DCL、静态内部类、枚举单例、使用容器类实现单例模式。
 * 推荐使用：DCL 和 静态内部类。
 */
public class Singleton {

    /**1.饿汉式**/
    private Singleton(){ }

    public static Singleton getInstance1(){
        return new Singleton();
    }

    /**2.懒汉式
     * synchronized 关键字，多线程保证单例唯一，但每次调用getInstance 都会进行同步，这消耗了不必要的资源。**/
    private static Singleton instance;
    public static synchronized Singleton getInstance2(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }

    /**3.DCL模式(Double Check Lock)
     * 优点：在需要的时候才初始化单例，保证线程安全，且实例化后调用getInstance不进行同步锁。
     * 这是推荐使用的单例模式实现方式。
     * */
    public static Singleton getInstance3(){
        if(instance == null){
            synchronized (Singleton.class){
                if(instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    /**
     * 4.静态内部类单例模式
     * 优点：线程安全，保证单例对象的唯一性，延迟单例的实例化。这是推荐使用的单例模式实现方式。
     * */
    public static Singleton getInstance4(){
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder{
        private static final Singleton sInstance = new Singleton();
    }

}
