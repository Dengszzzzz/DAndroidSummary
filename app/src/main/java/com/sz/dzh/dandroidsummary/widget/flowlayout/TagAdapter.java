package com.sz.dzh.dandroidsummary.widget.flowlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.bean.TagBean;

import java.util.List;


/**
 * 标签适配器
 */
public class TagAdapter extends BaseAdapter {

    private  Context mContext;
    private List<TagBean> mDataList;

    public TagAdapter(Context context, List<TagBean> mDataList) {
        this.mContext = context;
        this.mDataList = mDataList;

    }

    public List<TagBean> getmDataList() {
        return mDataList;
    }

    public void setmDataList(List<TagBean> mDataList) {
        this.mDataList = mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tag, null);
        TextView tagTv = view.findViewById(R.id.tagTv);
        String value = mDataList.get(position).getValue();
        tagTv.setText(value);
        if(mDataList.get(position).isSelect()){ //选中 主题色
            tagTv.setBackgroundResource(R.drawable.shape_bg_tag_select);
        }else{ //未选中 灰色
            tagTv.setBackgroundResource(R.drawable.shape_bg_tag);
        }

        return view;
    }


}
