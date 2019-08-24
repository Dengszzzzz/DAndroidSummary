package com.sz.dengzh.drxjavasummary.module.rxUse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;


import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dengzh.commonlib.bean.NetBean;
import com.sz.dengzh.drxjavasummary.R;
import com.sz.dengzh.drxjavasummary.R2;
import com.sz.dengzh.drxjavasummary.bean.OrderBean;
import com.sz.dengzh.drxjavasummary.bean.RxEventBean;
import com.sz.dengzh.drxjavasummary.utils.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2019/7/21 0021.
 */
public class RxBusBActvitiy extends BaseActivity {

    @BindView(R2.id.tv_desc)
    TextView tvDesc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_ac_bus2);
        ButterKnife.bind(this);
    }

    @OnClick({R2.id.btn_send, R2.id.btn_send2, R2.id.btn_send3})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.btn_send) {
            RxBus.getIntanceBus().post("这是String类型的事件");
        } else if (i == R.id.btn_send2) {
            RxEventBean<NetBean> rxEventBean = new RxEventBean<>();
            rxEventBean.setCode(404);
            rxEventBean.setContent(new NetBean("111", "这是NetBean"));
            RxBus.getIntanceBus().post(rxEventBean);
        } else if (i == R.id.btn_send3) {
            RxBus.getIntanceBus().post(100, new OrderBean(1, "冬瓜", 10, "深圳"));
        }
    }
}
