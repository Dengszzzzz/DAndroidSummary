package com.sz.dzh.dandroidsummary.model.summary.activity;

import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.model.summary.service.ServiceTestActivity;
import com.sz.dzh.dandroidsummary.model.summary.service.keeplive.KeepServiceActivity;

public class AcListActivity extends BaseListShowActivity {

    @Override
    protected void initUI() {
        tvTitle.setText("Service");
    }

    @Override
    protected void initData() {
        addClazzBean("Activity生命周期和保存数据",OrientationActivity.class);
        addClazzBean("横竖屏切换和监听",ConfigChangeActivity.class);
        mAdapter.notifyDataSetChanged();
    }
}
