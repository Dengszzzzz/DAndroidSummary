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
import com.sz.dzh.dandroidsummary.model.summary.activity.AcListActivity;
import com.sz.dzh.dandroidsummary.model.summary.imageSummary.ImageListActivity;
import com.sz.dzh.dandroidsummary.model.summary.netSummary.NetActivity;
import com.sz.dzh.dandroidsummary.model.summary.netSummary.NetListActivity;
import com.sz.dzh.dandroidsummary.model.summary.premission.PermissionActivity;
import com.sz.dzh.dandroidsummary.model.summary.rxjava.RxJavaListActivity;
import com.sz.dzh.dandroidsummary.model.summary.service.ServiceListActivity;
import com.sz.dzh.dandroidsummary.model.summary.storageSummary.StorageActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SummaryFragment extends BaseFragment{

    protected List<ClazzBean> mList = new ArrayList<>();
    protected BaseListAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    Unbinder unbinder;

    public static SummaryFragment newInstance() {
        SummaryFragment fragment = new SummaryFragment();
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

    private void initView(){
        mList.add(new ClazzBean("Service",ServiceListActivity.class));
        mList.add(new ClazzBean("Activity",AcListActivity.class));
        mList.add(new ClazzBean("数据存储知识",StorageActivity.class));
        mList.add(new ClazzBean("网络知识",NetListActivity.class));
        mList.add(new ClazzBean("权限管理", PermissionActivity.class));
        mList.add(new ClazzBean("图片知识详解", ImageListActivity.class));
        mList.add(new ClazzBean("RxJava2学习笔记", RxJavaListActivity.class));



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
