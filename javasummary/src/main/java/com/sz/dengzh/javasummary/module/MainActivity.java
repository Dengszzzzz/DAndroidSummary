package com.sz.dengzh.javasummary.module;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dengzh.commonlib.base.BaseListAdapter;
import com.sz.dengzh.commonlib.bean.ClazzBean;
import com.sz.dengzh.javasummary.R;
import com.sz.dengzh.javasummary.R2;
import com.sz.dengzh.javasummary.module.compare.CompareActivity;
import com.sz.dengzh.javasummary.module.design_pattern.DesignPatternActivity;
import com.xiaojinzi.component.anno.RouterAnno;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by administrator on 2018/11/5.
 */
@RouterAnno(
        host = "JavaSummary",
        path = "Main"
)
public class MainActivity extends BaseActivity {

    @BindView(R2.id.recyclerView)
    RecyclerView recyclerView;

    protected List<ClazzBean> mList = new ArrayList<>();
    protected BaseListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        ButterKnife.bind(this);
        initTitle();
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("Java 知识整理笔记");
        initData();
        initView();
    }

    private void initData(){
        addClazzBean("Java 测试", TestActivity.class);
        addClazzBean("设计模式", DesignPatternActivity.class);
        addClazzBean("Compare 理解", CompareActivity.class);

    }

    private void initView(){
        mAdapter = new BaseListAdapter(mList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(mList.get(position).getClazz());
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }



    protected void addClazzBean(String name, Class clazz){
        mList.add(new ClazzBean(name,clazz));
    }

}
