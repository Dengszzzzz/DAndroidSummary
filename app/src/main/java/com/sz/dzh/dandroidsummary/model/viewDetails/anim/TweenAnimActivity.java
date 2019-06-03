package com.sz.dzh.dandroidsummary.model.viewDetails.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 补间动画
 */
public class TweenAnimActivity extends BaseActivity {


    @BindView(R.id.mendingIv)
    ImageView mMendingIv;
    @BindView(R.id.compenceIv)
    ImageView mCompenceIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_tween_anim);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("补间动画");

        //补间动画-单
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_single);
        mMendingIv.startAnimation(animation);

        //补间动画-组合
        Animation combinationAnim = AnimationUtils.loadAnimation(this, R.anim.anim_combination);
        mCompenceIv.startAnimation(combinationAnim);
    }
}
