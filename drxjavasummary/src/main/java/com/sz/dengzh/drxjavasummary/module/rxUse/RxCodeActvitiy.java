package com.sz.dengzh.drxjavasummary.module.rxUse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dengzh.drxjavasummary.R;
import com.sz.dengzh.drxjavasummary.R2;
import com.sz.dengzh.drxjavasummary.utils.RxCodeHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengzh
 * on 2019/7/22 0022
 * 验证码
 */
public class RxCodeActvitiy extends BaseActivity {

    @BindView(R2.id.btn_send)
    Button mBtnSend;

    private RxCodeHelper mCodeHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_ac_code);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("验证码倒计时");
        mCodeHelper = new RxCodeHelper(this, mBtnSend);
    }

    @OnClick({R2.id.btn_send, R2.id.btn_reset})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_send) {
            mCodeHelper.start();
        } else if (i == R.id.btn_reset) {
            mCodeHelper.reset();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCodeHelper.stop();
    }
}
