package com.sz.dengzh.javasummary.module.design_pattern.state;

/**
 * Created by dengzh on 2019/10/4
 * 关机状态，此时只有开机功能有效
 */
public class PowerOffState implements TvState{

    @Override
    public void nextChannel() {

    }

    @Override
    public void prevChannel() {

    }

    @Override
    public void turnUp() {

    }

    @Override
    public void turnDown() {

    }
}
