package com.sz.dzh.dandroidsummary.model.viewDetails.anim;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2019/6/9
 * https://www.jianshu.com/p/69d48f313dc4
 * https://www.jianshu.com/p/10a820329959
 * https://blog.csdn.net/w630886916/article/details/78319502
 *
 * 1.通过返回键 or finishAfterTransition() 触发回退，退场效果生效，且和进场效果相反。
 * 2.调用finish()，没有退场效果。
 * 3.默认回退动画和进场动画相反。
 *   因为如果reenter 或者 return transition没有明确设置，则将用exit 和enter的transition替代。
 * 4.如果要设置B退回A，B的退出动画，要设置的事setReturnTransition，而不是setExitTransition。
 *
 *
 */
public class TransitionActivityB extends BaseActivity {

    @BindView(R.id.tv_desc)
    TextView tvDesc;

    private int type;

    public static void go(Activity context, int type) {
        Intent intent = new Intent(context, TransitionActivityB.class);
        intent.putExtra("type", type);
        context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context).toBundle());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.ac_transitionb);
        ButterKnife.bind(this);

        initTitle();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });
        type = getIntent().getIntExtra("type", 0);
        setEnterAnim();
    }

    /**
     * 设置进场动画
     */
    private void setEnterAnim() {
        switch (type) {
            case 0:
                tvTitle.setText("转场动画-Slide");
                getWindow().setEnterTransition(
                        TransitionInflater.from(this).inflateTransition(R.transition.activity_slide));
                break;
            case 1:
                tvTitle.setText("转场动画-Slide2");
                Slide slide2 = new Slide();
                slide2.setDuration(3000);
                slide2.setSlideEdge(Gravity.END);
                getWindow().setEnterTransition(slide2);
                break;
            case 2:
                tvTitle.setText("转场动画-Fade");
                getWindow().setEnterTransition(
                        TransitionInflater.from(this).inflateTransition(R.transition.activity_fade));
                break;
            case 3:
                tvTitle.setText("转场动画-Fade2");
                getWindow().setEnterTransition(new Fade().setDuration(3000));
                break;
            case 4:
                tvTitle.setText("转场动画-Explode");
                getWindow().setEnterTransition(
                        TransitionInflater.from(this).inflateTransition(R.transition.activity_explode));
                break;
        }
    }


    @OnClick({R.id.btn_out1, R.id.btn_out2, R.id.btn_out3})
    public void onViewClicked(View view) {
        Slide slide = new Slide();
        switch (view.getId()) {
            case R.id.btn_out1:
                finish();
                break;
            case R.id.btn_out2:
                finishAfterTransition();
                break;
            case R.id.btn_out3:
                slide.setDuration(3000);
                slide.setSlideEdge(Gravity.TOP);
                //当B 返回A时，使B中的View退出场景的transition 在B中设置
                getWindow().setReturnTransition(slide);
                finishAfterTransition();
                break;
        }
    }
}
