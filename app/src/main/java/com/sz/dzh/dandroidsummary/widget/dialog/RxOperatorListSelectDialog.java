package com.sz.dzh.dandroidsummary.widget.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.adapter.RxOperatorListSelectAdapter;
import com.sz.dzh.dandroidsummary.bean.RxOperatorBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 2018/10/12.
 * C2c列表选择弹窗
 */
public class RxOperatorListSelectDialog extends BaseDialog {

    RecyclerView mRecyclerView;

    private RxOperatorListSelectAdapter mAdapter;
    private List<RxOperatorBean> mList = new ArrayList<>();

    public RxOperatorListSelectDialog(Context mContext) {
        super(mContext, R.layout.dialog_rx_operator_list_select, Gravity.BOTTOM, true, true);
        initView();
    }

    private void initView() {
        getView(R.id.cancelTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDialog();
            }
        });
        mRecyclerView = getView(R.id.recyclerView);
        mAdapter = new RxOperatorListSelectAdapter(mList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(mListener!=null){
                    mListener.onSelect(mList.get(position));
                }
                toggleDialog();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void setData(List<RxOperatorBean> lists){
        mList.clear();
        mList.addAll(lists);
        mAdapter.notifyDataSetChanged();
    }

    private onDialogListener mListener;
    public void setOnDialogListener(onDialogListener listener){
        mListener = listener;
    }
    public interface onDialogListener{
        void onSelect(RxOperatorBean bean);
    }

}
