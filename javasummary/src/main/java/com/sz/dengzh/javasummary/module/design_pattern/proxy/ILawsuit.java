package com.sz.dengzh.javasummary.module.design_pattern.proxy;

/**
 * Created by dengzh on 2019/10/5
 * 抽象主题类
 */
public interface ILawsuit {
    //提交申请
    void submit();
    //进行举证
    void burden();
    //开始辩护
    void defend();
    //诉讼完成
    void finish();
}
