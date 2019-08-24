package com.sz.dzh.dandroidsummary.model.viewDetails.imageView;


import com.sz.dengzh.commonlib.base.BaseListShowActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class ImageViewListActivity extends BaseListShowActivity {

    @Override
    protected void initUI() {
        tvTitle.setText("自定义View");
    }

    @Override
    protected void initData() {
        addClazzBean("圆角or圆形图片", NiceImageActivity.class);
        mAdapter.notifyDataSetChanged();
    }


}
