package com.sz.dengzh.javasummary.module.design_pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by dengzh on 2019/10/5
 * 动态代理
 */
public class DynamicProxy implements InvocationHandler {

    private Object object;   //被代理的类引用

    public DynamicProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用被代理对象的方法
        Object result = method.invoke(object,args);
        return result;
    }
}
