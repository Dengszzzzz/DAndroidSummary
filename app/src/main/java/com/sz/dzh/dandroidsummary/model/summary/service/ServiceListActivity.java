package com.sz.dzh.dandroidsummary.model.summary.service;

import com.sz.dengzh.commonlib.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.model.summary.service.keeplive.KeepServiceActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class ServiceListActivity extends BaseListShowActivity {
    @Override
    protected void initUI() {
        tvTitle.setText("Service");
    }

    @Override
    protected void initData() {
        addClazzBean("Service总结",ServiceTestActivity.class);
        addClazzBean("Service保活",KeepServiceActivity.class);
        mAdapter.notifyDataSetChanged();
    }
}
