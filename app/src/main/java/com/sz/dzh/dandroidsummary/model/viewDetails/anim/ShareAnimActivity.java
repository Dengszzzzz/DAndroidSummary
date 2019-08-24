package com.sz.dzh.dandroidsummary.model.viewDetails.anim;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.utils.glideUtils.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 共享元素做转场动画A
 * https://www.jianshu.com/p/37e94f8b6f59
 *
 * 步骤：
 * 1.将两个Activity中需要过渡的View加上android:transitionName属性
 * 2.调用startActivity(intent, bundle);
 *   1）单元素：
 *   ActivityOptionsCompat.makeSceneTransitionAnimation（Activity，View，String）
 *   第二个参数共享元素View，第三个参数是共享元素名。
 *   2）多元素：
 *   Pair<>(View, String);   //每个Pair包括一个共享元素的View和name
 *   ActivityOptionsCompat.makeSceneTransitionAnimation(Activity activity, Pair<View, String>... sharedElements)
 *
 *
 */
public class ShareAnimActivity extends BaseActivity {

    @BindView(R.id.iv_share_top)
    ImageView mIvShareTop;
    @BindView(R.id.tv_share_name)
    TextView mTvShareName;
    @BindView(R.id.tv_share_date)
    TextView mTvShareDate;

    private String url = GlideUtils.url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_share_anim1);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("界面切换--共享元素");
        GlideUtils.loadImg(this, mIvShareTop, url);
    }


    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view) {
        Intent intent = new Intent(this, ShareAnimActivityB.class);
        intent.putExtra("url", url);
        switch (view.getId()) {
            case R.id.btn1:
                startActivity(intent);
                break;
            case R.id.btn2:
                //单元素 共享
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this, mIvShareTop, getString(R.string.share_iv_top_str)).toBundle();
                startActivity(intent, bundle);
                break;
            case R.id.btn3:
                //多元素 共享
                Pair first = new Pair<>(mIvShareTop, ViewCompat.getTransitionName(mIvShareTop));
                Pair second = new Pair<>(mTvShareName, ViewCompat.getTransitionName(mTvShareName));
                Pair third = new Pair<>(mTvShareDate,ViewCompat.getTransitionName(mTvShareDate));
                ActivityOptionsCompat compat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this, first, second,third);
                startActivity(intent, compat.toBundle());
                break;
        }
    }
}
