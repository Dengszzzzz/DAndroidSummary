package com.sz.dengzh.javasummary.module.design_pattern.state;

import com.socks.library.KLog;

/**
 * Created by dengzh on 2019/10/4
 * 开机状态，此时再触发开机功能不作任何操作
 */
public class PowerOnState implements TvState{

    @Override
    public void nextChannel() {
        KLog.e("下一频道");
    }

    @Override
    public void prevChannel() {
        KLog.e("上一频道");
    }

    @Override
    public void turnUp() {
        KLog.e("调高音量");
    }

    @Override
    public void turnDown() {
        KLog.e("调低音量");
    }
}
