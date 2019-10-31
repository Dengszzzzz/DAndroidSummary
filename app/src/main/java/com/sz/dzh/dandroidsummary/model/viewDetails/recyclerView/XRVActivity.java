package com.sz.dzh.dandroidsummary.model.viewDetails.recyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.CustomFooterViewCallBack;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.adapter.XrvAdapter;
import com.sz.dzh.dandroidsummary.bean.RxOperatorBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2019/10/21
 */
public class XRVActivity extends BaseActivity {

    @BindView(R.id.xrv)
    XRecyclerView xrv;

    private List<RxOperatorBean> list = new ArrayList<>();
    private XrvAdapter adapter;
    private MyLoadingMoreFooter  footer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_xrv);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("xRecyclerView");

        adapter = new XrvAdapter(this,list);
        xrv.setLayoutManager(new LinearLayoutManager(this));
        xrv.setAdapter(adapter);
        xrv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        footer = new MyLoadingMoreFooter(this);
        xrv.setFootView(footer, footer.callBack);
        xrv.setNoMore(true);
    }

    private void refresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                for (int i = 0;i<20;i++){
                    list.add(new RxOperatorBean(i,"名称" + i,"描述" + i));
                }
                adapter.notifyDataSetChanged();
                xrv.refreshComplete();
                xrv.setLoadingMoreEnabled(true);
            }
        },1000);

    }

    private void loadMore(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                int k = list.size();
                if(k>30){
                    xrv.setNoMore(true);
                    return;
                }
                for (int i = 0 ;i<10;i++){
                    list.add(new RxOperatorBean((i+k),"名称" + (i+k),"描述" + (i+k)));
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
    }
}
