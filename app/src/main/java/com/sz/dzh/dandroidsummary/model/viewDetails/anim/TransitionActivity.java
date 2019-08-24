package com.sz.dzh.dandroidsummary.model.viewDetails.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.Visibility;
import android.view.Gravity;
import android.view.View;

import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2019/6/9
 * https://www.jianshu.com/p/69d48f313dc4
 *
 * 1.Transition 是指不同 UI 状态转换时的动画。其中有两个关键概念：场景（scenes）和转换（transitions）。
 *   场景定义了一个确定的 UI 状态，而转换定义了两个场景切换时的动画。
 *
 * 2.默认三种转换，分解（Explode）、滑动（Slide）、淡入淡出（Fade），如有需要可自定义Visibility子类
 *
 * 3.下列方法，需要配合使用才有对应的效果。
 *   setExitTransition() ：    A->B, A的退出变换, 在A中设置
 *   setEnterTransition() ：   A->B, B的进入变换, 在B中设置
 *   setReturnTransition() ：  B->A, B的返回变换, 在B中设置
 *   setReenterTransition() ： B->A, A的再次进入变换, 在A中设置
 *
 * 4.//设置使用TransitionManager进行动画，不设置的话系统会使用一个默认的TransitionManager
 *   getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
 *   网上都说要设置这句，目前没有设置也可以执行动画
 *
 */
public class TransitionActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //打开窗口内容转换开关,要在setContentView()之前
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.ac_transition);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("转场动画");
    }

    @OnClick({R.id.btn_slide_in, R.id.btn_slide_in2, R.id.btn_fade_in, R.id.btn_fade_in2 ,R.id.btn_explode_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_slide_in:
                //1.加载xml的
                Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
                getWindow().setExitTransition(slide);
                TransitionActivityB.go(this,0);
                break;
            case R.id.btn_slide_in2:
                //2.代码创建
                Slide slide2 = new Slide();
                slide2.setDuration(3000);
                slide2.setSlideEdge(Gravity.END);
                slide2.setMode(Visibility.MODE_OUT);
                getWindow().setExitTransition(slide2);
                TransitionActivityB.go(this,1);
                break;
            case R.id.btn_fade_in:
                Transition fade = TransitionInflater.from(this).inflateTransition(R.transition.activity_fade);
                getWindow().setExitTransition(fade);
                TransitionActivityB.go(this,2);
                break;
            case R.id.btn_fade_in2:
                getWindow().setExitTransition(new Fade().setDuration(3000));
                TransitionActivityB.go(this,3);
                break;
            case R.id.btn_explode_in:

                Transition explode = TransitionInflater.from(this).inflateTransition(R.transition.activity_explode);
                getWindow().setExitTransition(explode);

                /*Explode explode = new Explode();
                explode.setDuration(3000);
                explode.setMode(Visibility.MODE_OUT);
                getWindow().setExitTransition(explode);*/

                TransitionActivityB.go(this,4);
                break;
        }
    }



}
