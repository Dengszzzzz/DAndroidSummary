package com.sz.dzh.dandroidsummary.model.summary.imageSummary;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 图片加载，三级缓存
 * 1.图片加载，最终调用BitmapFactory的方法生成Bitmap。
 */
public class ImageLoadActivity extends BaseActivity {

    @BindView(R.id.iv_show)
    ImageView mIvShow;
    @BindView(R.id.et_url)
    EditText mEtUrl;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_image);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("图片加载及三级缓存");
    }

    private void test() {
        String mFilePath = "";
        //加载资源
        BitmapFactory.decodeResource(getResources(), R.mipmap.follow_anim0);
        //加载本地图片
        BitmapFactory.decodeFile(mFilePath);
    }

    @OnClick(R.id.btn_load)
    public void onViewClicked() {
    }
}
