package com.sz.dzh.dandroidsummary.model.viewDetails.dialog;


import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class DialogListActivity extends BaseListShowActivity {
    @Override
    protected void initUI() {
        tvTitle.setText("Dialog示例");
    }

    @Override
    protected void initData() {
        addClazzBean("DialogFragment试用", DialogFrActivity.class);
        addClazzBean("DialogUtils 示例", DialogUActivity.class);
        mAdapter.notifyDataSetChanged();
    }

}
