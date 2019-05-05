package com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.tabpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dengzh on 2018/4/23.
 * FragmentPagerAdapter、FragmentStatePagerAdapter介绍
 * 1.两者很相似，每个页面都是Fragment，且每个Fragment保存到FragmentManager中。
   2.平时使用如果是数量特别多的条目时，才用FragmentStatePagerAdapter节省内存，两者区别如下：
       FragmentPagerAdapter：对于不需要的fragment，调用onDetach()销毁视图而不销毁实例。
       FragmentStatePagerAdapter：销毁实例，从FragmentManager中移除，注意销毁时，会将onSaveInstanceState(Bundle outState)
       中的bundle信息保存下来，当用户切换回来，可以通过该bundle恢复生成新的fragment。
 两者都要实现以下方法：
 Fragment  getItem(int position)； 返回的是对应的Fragment实例。一般使用Fragment集合返回。
 getCount()；集合的长度。
 *
 */

public class MyFrPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    private List<T> mFrList;

    public MyFrPagerAdapter(FragmentManager fm, ArrayList<T> fragments) {
        super(fm);
        mFrList = fragments;
    }

    @Override
    public T getItem(int position) {
        T fragment = mFrList.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFrList  == null ? 0:mFrList.size();
    }
}
