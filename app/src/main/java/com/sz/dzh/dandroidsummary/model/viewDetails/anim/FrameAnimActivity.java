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
 * 1)在drawable文件夹下，写animation-list
 * 2）ImageView的src设置为该resId
 * 3）iv.getDrawable()得到AnimationDrawable，调用drawable.start()开启。
 * 4) 动画结束监听，不能用isRunning来做判断。具体可参考：https://www.jianshu.com/p/dc66b371cd3b
 */
public class FrameAnimActivity extends BaseActivity {

    @BindView(R.id.iv1)
    ImageView mIv1;
    @BindView(R.id.iv2)
    ImageView mIv2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_frame_anim);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("逐帧动画");

        AnimationDrawable animationDrawable = (AnimationDrawable) mIv1.getDrawable();
        animationDrawable.start();

        AnimationDrawable animationDrawable2 = (AnimationDrawable) mIv2.getDrawable();
        animationDrawable2.setOneShot(false);
        animationDrawable2.start();

        mIv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animationDrawable.stop();
                animationDrawable.start();
            }
        });


    }
}
