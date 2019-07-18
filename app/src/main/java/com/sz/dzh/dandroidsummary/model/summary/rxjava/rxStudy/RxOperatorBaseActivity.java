package com.sz.dzh.dandroidsummary.model.summary.rxjava.rxStudy;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.bean.RxOperatorBean;
import com.sz.dzh.dandroidsummary.widget.dialog.RxOperatorListSelectDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 每一种RxJava 2.x 操作符的基类
 */

public abstract class RxOperatorBaseActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_rx_operator_base);
        ButterKnife.bind(this);
        initTitle();
    }



    /**
     * 设置mList
     */
    protected abstract void initData();

    protected abstract void doSomething();


    @OnClick(R.id.btn)
    public void onViewClicked() {
        doSomething();
    }
}
