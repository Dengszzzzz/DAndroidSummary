package com.sz.dzh.dandroidsummary.model.viewDetails.imageView;


import com.sz.dzh.dandroidsummary.base.BaseListShowActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.customview.InflaterActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.customview.MyViewActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.customview.VerticalStepViewActivity;

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
