package com.sz.dzh.dandroidsummary.model.viewDetails.recyclerView.stickyItemDecoration;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.adapter.XrvAdapter;
import com.sz.dzh.dandroidsummary.bean.RxOperatorBean;
import com.sz.dzh.dandroidsummary.widget.recyclerview.MyLoadingMoreFooter;
import com.sz.dzh.dandroidsummary.widget.recyclerview.sticky.StickyItemDecoration;
import com.sz.dzh.dandroidsummary.widget.recyclerview.sticky.XRStickyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2019/11/3
 * 顶部吸附 RecyclerView
 */
public class StickRvActivity extends BaseActivity{

    @BindView(R.id.xrv)
    RecyclerView xrv;

    private List<Performer> list = new ArrayList<>();
    private PerformerListAdapter adapter;
    private MyLoadingMoreFooter footer;
    private int titleIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_rv);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("xRecyclerView");

        adapter = new PerformerListAdapter(this,list);
        xrv.setLayoutManager(new LinearLayoutManager(this));
        //添加ItemDecoration，作为吸附View
        xrv.addItemDecoration(new StickyItemDecoration());

        xrv.setAdapter(adapter);
        refresh();
    }

    private void refresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                titleIndex = 0;
                for (int i = 0;i<20;i++){
                    if(i%5 == 0){
                        titleIndex++;
                        list.add(new Performer("分类标题" + titleIndex));
                    }
                    Performer bean = new Performer("名称" + i, 10);
                    list.add(bean);
                }
                adapter.notifyDataSetChanged();
            }
        },1000);

    }

    /*private void loadMore(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int k = list.size();
                if(k>30){
                    xrv.setNoMore(true);
                    return;
                }
                for (int i = 0 ;i<10;i++){
                    if((i + k)%5 == 0){
                        titleIndex++;
                        list.add(new Performer("分类标题" + titleIndex));
                    }
                    Performer bean = new Performer("名称" + (i+k), 10);
                    list.add(bean);
                }
                adapter.notifyDataSetChanged();
                xrv.loadMoreComplete();
                if(k>30){
                    xrv.setNoMore(true);
                }
            }
        },2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(xrv != null){
            xrv.destroy(); // this will totally release XR's memory
            xrv = null;
        }
    }*/
}
