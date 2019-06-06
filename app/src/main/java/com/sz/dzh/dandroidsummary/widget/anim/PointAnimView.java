package com.sz.dzh.dandroidsummary.widget.anim;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.socks.library.KLog;

/**
 * Created by administrator on 2018/8/10.
 */
public class PointAnimView extends View {
    public final String TAG = getClass().getSimpleName();
    public static final float RADIUS = 20f;

    //画笔
    private Paint mPaint;
    private Paint linePaint;
    //当前点
    private Point currentPoint;
    //动画集
    private AnimatorSet animSet;
    //插值器
    private TimeInterpolator interpolatorType = new LinearInterpolator();
    //圆初始颜色
    private int color;
    //圆初始半径
    private float radius = RADIUS;

    public PointAnimView(Context context) {
        this(context,null);
    }

    public PointAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PointAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.TRANSPARENT);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);  //没有设置这个，canvas.drawPath()失效
        linePaint.setStrokeWidth(5);
    }


    /**
     * onDraw()的canvas，每次都是干净的
     * 所以此处X,Y轴即使一直不变，也得每次都画
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);;
        KLog.e(TAG,"onDraw()");
        if (currentPoint == null) {
            currentPoint = new Point((int)RADIUS, (int)RADIUS);
        }
        //画X,Y轴
        canvas.drawLine(10, getHeight() / 2, getWidth(), getHeight() / 2, linePaint);
        canvas.drawLine(10, getHeight() / 2 - 150, 10, getHeight() / 2 + 150, linePaint);
        //画点
        canvas.drawPoint(currentPoint.x, currentPoint.y, linePaint);
        //画圆
        canvas.drawCircle(currentPoint.x,currentPoint.y, radius, mPaint);
    }


    /**
     * 插值器，在动画开启前设置
     * @param type
     */
    public void setInterpolatorType(int type) {
        switch (type) {
            case 1:
                interpolatorType = new BounceInterpolator();   //弹跳效果
                break;
            case 2:
                interpolatorType = new AccelerateDecelerateInterpolator();  //加减速插值器
                break;
            case 3:
                interpolatorType = new DecelerateInterpolator();  //减速插值器
                break;
            case 4:
                interpolatorType = new AccelerateInterpolator();  //加速插值器
                break;
            case 5:
                interpolatorType = new AnticipateInterpolator();  //开始的时候向后然后向前甩
                break;
            case 6:
                interpolatorType=new LinearOutSlowInInterpolator();
                break;
            case 7:
                interpolatorType = new OvershootInterpolator();  // 向前甩一定值后再回到原来位置
                break;
            case 8:
                interpolatorType = new MyInterpolator();
                break;
            default:
                interpolatorType = new LinearInterpolator();  //线性插值器
                break;
        }
    }

    /**
     * 设置默认颜色,ObjectAnimator动画显示的属性，必须有set和get
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(this.color);
    }

    /**
     * 设置圆半径
     * @param radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

    public int getColor() {
        return color;
    }

    public float getRadius() {
        return radius;
    }

    public void startAnimation(){
        //球位置
        Point startP = new Point((int)radius,(int)radius);
        Point endP = new Point(getWidth() - (int)radius,getHeight() - (int)radius);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointSinEvaluator(), startP, endP);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);  //设置重复模式：逆向重复
        //valueAnimator.setRepeatMode(ValueAnimator.RESTART); //设置重复模式：重新开始
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //当前点位置，自定义估值器返回值
                currentPoint = (Point) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        //颜色,目前开启动画后，没有用到设置的颜色。
        ObjectAnimator animColor = ObjectAnimator.ofObject(this, "color", new ArgbEvaluator(), Color.GREEN,
                Color.YELLOW, Color.BLUE, Color.WHITE, Color.RED);
        animColor.setRepeatCount(-1);
        animColor.setRepeatMode(ValueAnimator.REVERSE);

        //缩放
        ValueAnimator animScale = ValueAnimator.ofFloat(radius, radius*4, 60, 10f, 35f,55f,10f);
        animScale.setRepeatCount(-1);
        animScale.setRepeatMode(ValueAnimator.REVERSE);
        animScale.setDuration(5000);
        animScale.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //半径，Float的估值器返回值
                //从20->80->60->10->35->55->10,再逆序回来
                radius = (float) animation.getAnimatedValue();
            }
        });

        //动画集合
        animSet = new AnimatorSet();
        //animSet.playTogether(valueAnimator,animColor,animScale);
        animSet.play(valueAnimator).with(animColor).with(animScale);  //和playTogether()是一样的。
        animSet.setDuration(5000);
        animSet.setInterpolator(interpolatorType);
        animSet.start();
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void pauseAnimation() {
        if (animSet != null) {
            animSet.pause();
        }
    }


    public void stopAnimation() {
        if (animSet != null) {
            animSet.cancel();
            this.clearAnimation();
        }
    }

}
