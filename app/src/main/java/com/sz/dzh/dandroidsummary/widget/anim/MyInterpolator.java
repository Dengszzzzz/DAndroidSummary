package com.sz.dzh.dandroidsummary.widget.anim;

import android.view.animation.BaseInterpolator;

/**
 * Created by administrator on 2018/8/15.
 * 自定义先减速后加速插值器
 * 插值器，直接控制动画的变化速率
 */
public class MyInterpolator extends BaseInterpolator {

    /**
     * input参数是系统根据设置的动画持续时间计算出来的
     * @param input  [0,1],从0匀速增加到1
     * @return
     */
    @Override
    public float getInterpolation(float input) {
        return ((4*input-2)*(4*input-2)*(4*input-2))/16f + 0.5f;
    }

}
