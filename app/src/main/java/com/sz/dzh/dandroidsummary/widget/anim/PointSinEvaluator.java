package com.sz.dzh.dandroidsummary.widget.anim;

import android.animation.TypeEvaluator;
import android.graphics.Point;

import com.socks.library.KLog;

/**
 * Created by administrator on 2018/8/10.
 */
public class PointSinEvaluator implements TypeEvaluator {

    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        float x = startPoint.x + fraction * (endPoint.x - startPoint.x);

        float y = (float) (Math.sin(x * Math.PI / 180) * 100) + endPoint.y / 2;
        Point point = new Point((int)x,(int)y);
        KLog.d("Point","x: " + point.x  + "    y: " + point.y);
        return point;
    }
}
