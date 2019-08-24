package com.sz.dzh.dandroidsummary.model.viewDetails.viewpager;

import com.sz.dengzh.commonlib.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.banpager.BanPagerActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.tabpager.TabIndicatorActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.tabpager.TabPagerActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class ViewPagerListActivity extends BaseListShowActivity {
    @Override
    protected void initUI() {
        tvTitle.setText("viewpager示例");
    }

    @Override
    protected void initData() {
        addClazzBean("TabLayout，ViewPager，Fragment",TabPagerActivity.class);
        addClazzBean("TabLayout的指示器", TabIndicatorActivity.class);
        addClazzBean("广告条 viewpager", BanPagerActivity.class);
        mAdapter.notifyDataSetChanged();
    }
}
