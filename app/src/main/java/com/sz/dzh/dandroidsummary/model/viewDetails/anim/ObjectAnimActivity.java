package com.sz.dzh.dandroidsummary.model.viewDetails.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.widget.anim.PointAnimView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 属性动画
 * 1）为什么要有属性动画？
 *   1.传统动画的缺点是作用局限于View，没有改变View的属性，只是改变视觉效果，动画效果单一。
 *   2.属性动画能对非View进行动画操作，如改变View的颜色属性，而不是针对整个View做动画操作。
 *
 * 2）工作原理：
 *   在一定时间间隔内，通过不断对值进行改变，并不断将该值赋给对象的属性，从而实现该对象在该属性上的动画效果
 *
 * 3）核心：
 *   ValueAnimator
 *   TypeEvaluator：估值器，决定值的具体变化数值。
 *   Interpolator:  插值器，决定值的变化模式，如匀速、先加速再减速等。
 *
 */
public class ObjectAnimActivity extends BaseActivity {

    @BindView(R.id.rotateIv)
    ImageView mRotateIv;
    @BindView(R.id.alphaIv)
    ImageView mAlphaIv;
    @BindView(R.id.combinationIv)
    ImageView mCombinationIv;
    @BindView(R.id.pointAnimView)
    PointAnimView mPointAnimView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_object_anim);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("属性动画");
        onRotateAnim();
        onAlphaAnim();
        onCombinationObject();
        onPointAnim();
    }

    /**
     * 旋转 从 90° 转到 360°
     */
    private void onRotateAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(mRotateIv,
                "rotation", 90f, 360f);
        anim.setDuration(10000);
        anim.start();
    }

    /**
     * 透明度  此处设置的值可以不按大小排序，比补间动画更灵活
     */
    private void onAlphaAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mAlphaIv, "alpha",
                1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0.0f);
        animator.setRepeatCount(ObjectAnimator.INFINITE);   //无限循环
        animator.setRepeatMode(ObjectAnimator.REVERSE);     //REVERSE：0->1->0 如此模式
        animator.setDuration(3000);
        animator.start();
    }

    /**
     * 组合动画
     * alpha，scaleX，scaleY，rotate，transX，transY 简单组合用法
     * 可以同时播放，也能依次播放
     */
    private void onCombinationObject() {
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mCombinationIv, "alpha",
                1.0f, 0.5f, 0.8f, 1.0f);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(mCombinationIv, "scaleX",
                0.0f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(mCombinationIv, "scaleY",
                0.0f, 2.0f);
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(mCombinationIv, "rotation",
                0, 360);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(mCombinationIv, "translationX",
                100, 400);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(mCombinationIv, "translationY",
                100, 750);
        //组合动画
        AnimatorSet set = new AnimatorSet();
        //同时执行
        set.playTogether(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
        //按顺序执行
        //set.playSequentially(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);
        set.setDuration(10000);
        set.start();
    }

    private void onPointAnim(){
        //runnable对象中的方法会在View的measure、layout等事件后触发,
        //解决onCreate()过程中获取View的width和Height为0的问题
        mPointAnimView.setInterpolatorType(8);
        mPointAnimView.setRadius(10);
        mPointAnimView.setColor(ContextCompat.getColor(this,R.color.c_87cfdc));
        mPointAnimView.post(new Runnable() {
            @Override
            public void run() {
                mPointAnimView.startAnimation();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPointAnimView.stopAnimation();
    }
}
