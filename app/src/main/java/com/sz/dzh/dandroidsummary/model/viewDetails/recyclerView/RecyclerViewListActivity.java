package com.sz.dzh.dandroidsummary.model.viewDetails.recyclerView;


import com.sz.dengzh.commonlib.base.BaseListShowActivity;
import com.sz.dengzh.commonlib.bean.ClazzBean;
import com.sz.dzh.dandroidsummary.model.viewDetails.imageView.NiceImageActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.recyclerView.stickyItemDecoration.StickRvActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.recyclerView.stickyItemDecoration.StickXRvActivity;

/**
 * Created by dengzh on 2018/4/18.
 */

public class RecyclerViewListActivity extends BaseListShowActivity {

    @Override
    protected void initUI() {
        tvTitle.setText("RecyclerView");
    }

    @Override
    protected void initData() {
        addClazzBean("XRecyclerView 自定义Footer", XRVActivity.class);
        addClazzBean("XRecyclerView 置顶吸附", StickXRvActivity.class);
        addClazzBean("RecyclerView 置顶吸附", StickRvActivity.class);

        mAdapter.notifyDataSetChanged();
    }


}
