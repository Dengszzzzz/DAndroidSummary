package com.sz.dengzh.commonlib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sz.dengzh.commonlib.R;
import com.sz.dengzh.commonlib.bean.ClazzBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengzh on 2018/4/18.
 * 列表显示界面基类
 */

public abstract class BaseListShowActivity extends BaseActivity {

    RecyclerView recyclerView;

    protected List<ClazzBean> mList = new ArrayList<>();
    protected BaseListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_ac_custom_list);
        initTitle();
        initView();
        initUI();
        initData();
    }

    private void initView(){
        recyclerView = findViewById(R.id.recyclerView);

        mAdapter = new BaseListAdapter(mList);
        mAdapter.setOnItemClickListener((adapter, view, position) -> startActivity(mList.get(position).getClazz()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }


    protected abstract void initUI();
    //子类给mList添加数据，再调用mAdapter.notifyDataSetChanged();
    protected abstract void initData();

    protected void addClazzBean(String name, Class clazz){
        mList.add(new ClazzBean(name,clazz));
    }


}
