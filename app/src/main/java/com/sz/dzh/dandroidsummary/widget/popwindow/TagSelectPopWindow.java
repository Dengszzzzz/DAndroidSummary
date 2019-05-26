package com.sz.dzh.dandroidsummary.widget.popwindow;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.bean.TagBean;
import com.sz.dzh.dandroidsummary.widget.flowlayout.FlowTagLayout;
import com.sz.dzh.dandroidsummary.widget.flowlayout.OnTagClickListener;
import com.sz.dzh.dandroidsummary.widget.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dengzh on 2017/11/10.
 * Tag选择弹窗
 * 使用了 FlowLayout
 */
public class TagSelectPopWindow extends BasePopupWindow{

    private FlowTagLayout flowTagLayout;
    private TagAdapter adapter;
    private String[] values;
    private String[] keys;
    private OnChildClickListener listener;
    private List<TagBean> mList = new ArrayList<>();
    private int lastSelectPosition;  //最近一次选中位置，默认是0

    public TagSelectPopWindow(Context mContext) {
        super(mContext, R.layout.pop_tag_select, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        init();
    }

    private void init(){
        //阴影
        getView(R.id.translucentView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //标签
        flowTagLayout = getView(R.id.flowTagLayout);
        adapter = new TagAdapter(mContext, mList);
        flowTagLayout.setAdapter(adapter);
        flowTagLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                //改变选中状态
                mList.get(lastSelectPosition).setSelect(false);
                lastSelectPosition = position;
                mList.get(position).setSelect(true);
                adapter.notifyDataSetChanged();
                //回调监听
                if(listener!=null){
                    listener.onTagSelect(mList.get(position).getKey());
                }
                dismiss();
            }
        });
    }

    /**
     * 设置数据
     * @param keys      {1,2}
     * @param values    {中国，美国}
     */
    public void setData(String keys[],String[] values){
        this.values = values;
        this.keys = keys;
        for(int i = 0;i<values.length;i++){
            mList.add(new TagBean(keys[i],values[i],false));
        }
        mList.get(lastSelectPosition).setSelect(true);
        adapter.notifyDataSetChanged();
    }


    /**
     * 监听接口
     */
    public interface OnChildClickListener{
         void onTagSelect(String key);
    }

    public void setOnChildClickListener(OnChildClickListener listener){
        this.listener = listener;
    }

}
