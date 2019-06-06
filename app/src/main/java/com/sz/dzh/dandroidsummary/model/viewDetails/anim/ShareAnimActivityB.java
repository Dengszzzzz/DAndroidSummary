package com.sz.dzh.dandroidsummary.model.viewDetails.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.utils.glideUtils.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 共享元素做转场动画B
 */
public class ShareAnimActivityB extends BaseActivity {

    @BindView(R.id.iv_share_top)
    ImageView mIvShareTop;
    @BindView(R.id.tv_share_name)
    TextView mTvShareName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_share_animb);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("url");
        GlideUtils.loadImg(this,mIvShareTop,url);
    }

}
