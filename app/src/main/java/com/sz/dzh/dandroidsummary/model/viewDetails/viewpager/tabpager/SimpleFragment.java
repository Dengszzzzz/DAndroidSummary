package com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.tabpager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sz.dengzh.commonlib.base.BaseFragment;
import com.sz.dzh.dandroidsummary.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dengzh on 2018/4/17.
 * 简单的Fragment演示
 */

public class SimpleFragment extends BaseFragment {

    @BindView(R.id.descTv)
    TextView descTv;
    Unbinder unbinder;

    private String id;


    /**
     *  Activity 传给 Fragment的参数
     * @param id
     * @return
     */
    public static SimpleFragment newInstance(String id) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Fragment 接收 Activity的参数
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        id = getArguments().getString("id");
    }

    /**
     * Fragment设置布局的方法，一般在return之前 initView();
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_simple, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void init(){
        descTv.setText("页面" + id);
    }
}
