package com.sz.dzh.dandroidsummary.model.summary.service;

import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.model.summary.service.keeplive.KeepServiceActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.banpager.BanPagerActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.tabpager.TabIndicatorActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.tabpager.TabPagerActivity;

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
