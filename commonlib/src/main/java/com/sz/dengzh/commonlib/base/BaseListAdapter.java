package com.sz.dengzh.commonlib.base;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sz.dengzh.commonlib.R;
import com.sz.dengzh.commonlib.bean.ClazzBean;

import java.util.List;


/**
 * Created by dengzh on 2018/4/18.
 */

public class BaseListAdapter extends BaseQuickAdapter<ClazzBean, BaseViewHolder> {

    public BaseListAdapter(@Nullable List<ClazzBean> data) {
        super(R.layout.base_item_base_list,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClazzBean item) {
        helper.setText(R.id.tvName,item.getClazzName());
    }
}
