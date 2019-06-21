package com.sz.dzh.dandroidsummary.model.summary.netSummary;

import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.model.summary.netSummary.okhttp.OkHttpActivity;
import com.sz.dzh.dandroidsummary.model.summary.netSummary.retrofit.RetrofitActivity;
import com.sz.dzh.dandroidsummary.model.summary.service.ServiceTestActivity;
import com.sz.dzh.dandroidsummary.model.summary.service.keeplive.KeepServiceActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class NetListActivity extends BaseListShowActivity {
    @Override
    protected void initUI() {
        tvTitle.setText("网络知识");
    }

    @Override
    protected void initData() {
        addClazzBean("网络知识体系",NetActivity.class);
        addClazzBean("OkHttp源码简读",OkHttpActivity.class);
        addClazzBean("Retrofit源码简读",RetrofitActivity.class);
        mAdapter.notifyDataSetChanged();
    }
}
