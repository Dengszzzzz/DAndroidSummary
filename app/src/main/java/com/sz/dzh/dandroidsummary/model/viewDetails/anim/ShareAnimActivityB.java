package com.sz.dzh.dandroidsummary.model.viewDetails.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.utils.glideUtils.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 共享元素做转场动画B
 */
public class ShareAnimActivityB extends BaseActivity {

    @BindView(R.id.iv_share_top)
    ImageView mIvShareTop;
    @BindView(R.id.tv_share_name)
    TextView mTvShareName;

    private String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=13326166,878266866&fm=26&gp=0.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_share_animb);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("界面切换--共享元素B");
        ivBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

        url = getIntent().getStringExtra("url");
        GlideUtils.loadImg(this,mIvShareTop,url);
/*

        //延迟共享动画
        postponeEnterTransition();
        //开始 已延迟的共享动画
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startPostponedEnterTransition();
            }
        },3000);
*/

    }

}
