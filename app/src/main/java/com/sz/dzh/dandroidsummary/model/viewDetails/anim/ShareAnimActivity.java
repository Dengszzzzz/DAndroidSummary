package com.sz.dzh.dandroidsummary.model.viewDetails.anim;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.utils.glideUtils.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 共享元素做转场动画A
 *
 * https://www.jianshu.com/p/37e94f8b6f59
 *
 */
public class ShareAnimActivity extends BaseActivity {

    @BindView(R.id.iv_share_top)
    ImageView mIvShareTop;
    @BindView(R.id.tv_share_name)
    TextView mTvShareName;

    private String url = GlideUtils.url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_share_anim1);
        ButterKnife.bind(this);
        GlideUtils.loadImg(this, mIvShareTop, url);
    }

    @OnClick(R.id.btn1)
    public void onViewClicked() {
        //开启共享元素动画



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
                //单个控件共享 - shareElement就是共享元素id，sharedElementName就是共享元素名。
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, mIvShareTop, getString(R.string.share_iv_top_str)).toBundle();
                startActivity(intent, bundle);
                break;
            case R.id.btn3:
                //多共享
                Pair<View, String> first = Pair.create(mIvShareTop,ViewCompat.getTransitionName(mIvShareTop));
                Pair<View, String> second = Pair.create(mTvShareName,ViewCompat.getTransitionName(mTvShareName));
               /* Pair first = new Pair<>(mIvShareTop, ViewCompat.getTransitionName(mIvShareTop));
                Pair second = new Pair<>(mTvShareName, ViewCompat.getTransitionName(mTvShareName));
*/

               /* ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,first,second);


                Bundle bundle2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this, first).toBundle();
                startActivity(intent, bundle);*/
                break;
        }
    }
}
