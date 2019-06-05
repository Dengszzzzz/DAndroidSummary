package com.sz.dzh.dandroidsummary.model.viewDetails.anim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ObjectAnimActivity extends BaseActivity {

    @BindView(R.id.rotateIv)
    ImageView mRotateIv;
    @BindView(R.id.alphaIv)
    ImageView mAlphaIv;
    @BindView(R.id.combinationIv)
    ImageView mCombinationIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_object_anim);
        ButterKnife.bind(this);
    }
}
