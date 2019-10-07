package com.sz.dengzh.javasummary.module.design_pattern.state;

import com.socks.library.KLog;

/**
 * Created by dengzh on 2019/10/4
 */
public class TvController {

    TvState mTvState;

    public void setTvState(TvState tvState) {
        this.mTvState = tvState;
    }

    public void powerOn(){
        setTvState(new PowerOnState());
        KLog.e("开机啦");
    }

    public void powerOff(){
        setTvState(new PowerOffState());
        KLog.e("关机啦");
    }

    public void nextChannel() {
        mTvState.nextChannel();
    }

    public void prevChannel() {
        mTvState.prevChannel();
    }

    public void turnUp() {
        mTvState.turnUp();
    }

    public void turnDown() {
        mTvState.turnDown();
    }
}
