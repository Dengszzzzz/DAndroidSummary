package com.sz.dengzh.javasummary.module.design_pattern.proxy;

import com.socks.library.KLog;

/**
 * Created by dengzh on 2019/10/5
 * 真实主题类
 */
public class XiaoMin implements ILawsuit{

    @Override
    public void submit() {
        KLog.e("提交申请");
    }

    @Override
    public void burden() {
        KLog.e("进行举证");
    }

    @Override
    public void defend() {
        KLog.e("开始辩护");
    }

    @Override
    public void finish() {
        KLog.e("诉讼完成");
    }
}
