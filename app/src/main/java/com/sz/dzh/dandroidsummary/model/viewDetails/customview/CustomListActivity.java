package com.sz.dzh.dandroidsummary.model.viewDetails.customview;


import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class CustomListActivity extends BaseListShowActivity {

    @Override
    protected void initUI() {
        tvTitle.setText("自定义View");
    }

    @Override
    protected void initData() {
        addClazzBean("步骤指示器", VerticalStepViewActivity.class);

        mAdapter.notifyDataSetChanged();
    }


}
