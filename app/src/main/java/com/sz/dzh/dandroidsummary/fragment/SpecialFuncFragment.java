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
import com.sz.dengzh.commonlib.base.BaseFragment;
import com.sz.dengzh.commonlib.base.BaseListAdapter;
import com.sz.dengzh.commonlib.bean.ClazzBean;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.model.specialFunc.camera.CameraActivity;
import com.sz.dzh.dandroidsummary.model.specialFunc.crop.CropActivity;
import com.sz.dzh.dandroidsummary.model.specialFunc.download.DownloadListActivity;
import com.sz.dzh.dandroidsummary.model.specialFunc.emoji_encoder.EmojiEncoderActivity;
import com.sz.dzh.dandroidsummary.model.specialFunc.fingerprint.FingerPrintActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SpecialFuncFragment extends BaseFragment {

    protected List<ClazzBean> mList = new ArrayList<>();
    protected BaseListAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    Unbinder unbinder;

    public static SpecialFuncFragment newInstance() {
        SpecialFuncFragment fragment = new SpecialFuncFragment();
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
        mList.add(new ClazzBean("文件下载",DownloadListActivity.class));
        mList.add(new ClazzBean("指纹识别",FingerPrintActivity.class));
        mList.add(new ClazzBean("Emoji表情编解码",EmojiEncoderActivity.class));
        mList.add(new ClazzBean("自定义相机", CameraActivity.class));
        mList.add(new ClazzBean("自定义裁剪页面", CropActivity.class));


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
