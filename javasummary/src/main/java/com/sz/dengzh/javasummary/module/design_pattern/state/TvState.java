package com.sz.dengzh.javasummary.module.design_pattern.state;

/**
 * Created by dengzh on 2019/10/4
 * 状态接口（也可以写抽象状态类）
 */
public interface TvState {
    void nextChannel();
    void prevChannel();
    void turnUp();
    void turnDown();
}
