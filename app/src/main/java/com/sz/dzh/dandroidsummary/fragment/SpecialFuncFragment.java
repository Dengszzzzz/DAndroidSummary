package com.sz.dzh.dandroidsummary.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseFragment;

public class SpecialFuncFragment extends BaseFragment{

    public static SpecialFuncFragment newInstance() {
        SpecialFuncFragment fragment = new SpecialFuncFragment();
        /*Bundle bundle = new Bundle();
        bundle.putString("id", id);
        fragment.setArguments(bundle);*/
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_custom_list, container, false);
        initView();
        return view;
    }

    private void initView(){

    }
}
