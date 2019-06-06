package com.sz.dzh.dandroidsummary.widget.anim;

import android.animation.TypeEvaluator;
import android.graphics.Point;

import com.socks.library.KLog;

/**
 * Created by administrator on 2018/8/10.
 * 估值器，决定值的具体变化数值。
 */
public class PointSinEvaluator implements TypeEvaluator {

    /**
     * @param fraction   表示动画完成度（根据它来计算当前动画的值）
     * @param startValue 动画的初始值
     * @param endValue   动画的结束值
     * @return
     */
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        //将动画初始值startValue 和 动画结束值endValue 强制类型转换成Point对象
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;

        //根据fraction来计算当前动画的x和y的值
        float x = startPoint.x + fraction * (endPoint.x - startPoint.x);
        float y = (float) (Math.sin(x * Math.PI / 180) * 100) + endPoint.y / 2;

        //将计算后的坐标封装到一个新的Point对象中并返回
        Point point = new Point((int)x,(int)y);
        KLog.d("Point","x: " + point.x  + "    y: " + point.y);
        return point;
    }
}
