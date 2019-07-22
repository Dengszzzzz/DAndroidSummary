package com.sz.dzh.dandroidsummary.model.summary.rxjava.rxUse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengzh
 * on 2019/7/22 0022
 * 验证码
 */
public class RxCodeActvitiy extends BaseActivity {

    @BindView(R.id.btn_send)
    Button mBtnSend;

    private RxCodeHelper mCodeHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_rx_code);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("验证码倒计时");
        mCodeHelper = new RxCodeHelper(this, mBtnSend);
    }

    @OnClick({R.id.btn_send, R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                mCodeHelper.start();
                break;
            case R.id.btn_reset:
                mCodeHelper.reset();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCodeHelper.stop();
    }
}
