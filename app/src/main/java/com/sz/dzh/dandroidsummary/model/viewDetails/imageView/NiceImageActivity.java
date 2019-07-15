package com.sz.dzh.dandroidsummary.model.viewDetails.imageView;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

/**
 * Created by Dengzh
 * on 2019/7/15 0015
 */
public class NiceImageActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_nice_image);
        initTitle();
        tvTitle.setText("圆角or圆形图片");
    }
}
