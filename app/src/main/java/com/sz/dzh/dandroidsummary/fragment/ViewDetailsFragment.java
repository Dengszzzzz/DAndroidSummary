package com.sz.dzh.dandroidsummary.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.adapter.BaseListAdapter;
import com.sz.dzh.dandroidsummary.base.BaseFragment;
import com.sz.dzh.dandroidsummary.bean.ClazzBean;
import com.sz.dzh.dandroidsummary.model.viewDetails.anim.AnimListActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.customview.CustomListActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.dialog.DialogListActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.imageView.ImageViewListActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.popwindow.PopWindowListActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.ViewPagerListActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.tabpager.TabPagerActivity;
import com.sz.dzh.dandroidsummary.model.viewDetails.webview.WebListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ViewDetailsFragment extends BaseFragment {

    protected List<ClazzBean> mList = new ArrayList<>();
    protected BaseListAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    Unbinder unbinder;


    public static ViewDetailsFragment newInstance() {
        ViewDetailsFragment fragment = new ViewDetailsFragment();
        /*Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);*/
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_custom_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        mList.add(new ClazzBean("ViewPager",ViewPagerListActivity.class));
        mList.add(new ClazzBean("Dialog",DialogListActivity.class));
        mList.add(new ClazzBean("PopWindow", PopWindowListActivity.class));
        mList.add(new ClazzBean("WebView", WebListActivity.class));
        mList.add(new ClazzBean("自定义View", CustomListActivity.class));
        mList.add(new ClazzBean("动画Anim", AnimListActivity.class));
        mList.add(new ClazzBean("ImageView", ImageViewListActivity.class));

        mAdapter = new BaseListAdapter(mList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(mList.get(position).getClazz());
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mSrlRefresh.setEnableRefresh(false);
        mSrlRefresh.setEnableLoadMore(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
