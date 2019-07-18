package com.sz.dzh.dandroidsummary.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.bean.RxOperatorBean;

import java.util.List;

/**
 * Created by administrator on 2018/10/12.
 */
public class RxOperatorListSelectAdapter extends BaseQuickAdapter<RxOperatorBean, BaseViewHolder> {

    public RxOperatorListSelectAdapter(@Nullable List<RxOperatorBean> data) {
        super(R.layout.item_list_select,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RxOperatorBean item) {
        helper.setText(R.id.nameTv,item.getName());
    }
}
