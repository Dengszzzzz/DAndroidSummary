package com.sz.dzh.dandroidsummary.model.viewDetails.anim;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 逐帧动画
 */
public class FrameAnimActivity extends BaseActivity {

    @BindView(R.id.iv1)
    ImageView mIv1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_frame_anim);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("逐帧动画");

        //逐帧动画
        AnimationDrawable animationDrawable = (AnimationDrawable) mIv1.getDrawable();
        animationDrawable.start();

        mIv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!animationDrawable.isRunning()){
                    animationDrawable.setOneShot(false);
                    animationDrawable.start();
                }
            }
        });
    }
}
