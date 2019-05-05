package com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.tabpager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/4/23.
 * TabLayout,ViewPager,fragment 演示
 * 1.TabLayout tabMode、指示条长度。
 */

public class TabPagerActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<EventTypeBean> mTypeList = new ArrayList<>();
    private MyFrPagerAdapter<SimpleFragment> myPagerAdapter;
    private ArrayList<SimpleFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_tab_pager);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("Tab和ViewPager演示");
        initData();
        initView();
    }

    /**
     * 接受数据后
     * 填充tab 数据
     */
    private void initData(){
        // 封装数据
        for(int i = 0;i<6;i++){
            mTypeList.add(new EventTypeBean(i,"类型" + i,""));
        }
        for(int i = 0;i<mTypeList.size();i++){
            fragments.add(SimpleFragment.newInstance(mTypeList.get(i).getId()+""));
        }
    }

    private void initView(){
        //关联数据
        myPagerAdapter = new MyFrPagerAdapter<>(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(myPagerAdapter);
        /**
         * TabLayout.setupWithViewPager(viewPager)，将TabLayout和Viewpager两者绑定在一起。
         * 实际上，是setupWithViewPager()方法底部调用PagerAdapter中的getPageTitle()方法实现联系。
         *
         * 注意：setupWithViewPager 会执行 removeAllTabs,所以要放在tab设置前面关联。
         * */
        mTabLayout.setupWithViewPager(viewPager);
        //设置样式
        for(int i = 0;i<mTypeList.size();i++){
            // mTabLayout.getTabAt(i).setText(mTypeList.get(i).getTypename());
            //自定义样式
            mTabLayout.getTabAt(i).setCustomView(getTabView(mTypeList.get(i).getTypename(),mTypeList.get(i).getPicture()));
        }
        viewPager.setCurrentItem(0,false);
    }

    /**
     * 构造 tabview
     * @param name
     * @param url
     * @return
     */
    public View getTabView(String name, String url) {
        View view = getLayoutInflater().inflate(R.layout.item_tab_pager_top,null);
        TextView typeNameTv = view.findViewById(R.id.typeNameTv);
        typeNameTv.setText(name);
        ImageView typeIv =  view.findViewById(R.id.typeIv);
       // GlideUtils.loadImg(this,typeIv,url);
        return view;
    }

}
