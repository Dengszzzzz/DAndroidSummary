package com.sz.dengzh.javasummary.module;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dengzh.javasummary.R;
import com.sz.dengzh.javasummary.bean.AnimalBean;
import com.sz.dengzh.javasummary.bean.CatBean;
import com.sz.dengzh.javasummary.bean.DogBean;
import com.sz.dengzh.javasummary.bean.StaitcTestBean;
import com.sz.dengzh.javasummary.bean.StaitcTestBean2;

/**
 * Created by dengzh on 2019/9/10
 */
public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.j_ac_test);
        test1();

        test2();
    }

    /***
     * 静态属性和静态方法是否可以被继承？是否可以被重写？以及原因？
     *
     *
     * */
    private void test1(){
        //StaticBean2 “重写” StaticBean1 的 testValue 和 handle()
        StaitcTestBean.handle();
        StaitcTestBean2.handle();

        //StaticBean2 没有重写 StaticBean1 的 testValue2 和 handle2()
        StaitcTestBean.handle2();
        StaitcTestBean2.handle2();
    }

    /**
     * 多态机制
     */
    private void test2(){
        AnimalBean catbean = new CatBean();
        catbean.yell();
        ((CatBean) catbean).eat();

        AnimalBean dogBean = new DogBean();
        dogBean.yell();
    }
}
