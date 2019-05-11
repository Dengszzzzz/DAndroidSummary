package com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.tabpager;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dengzh on 2018/4/23.
 * TabLayout指示条长度。
 * 1.通过反射修改指示条宽度，但是宽度最小不能比内容小。
 * 2.用第三方库吧
 * https://github.com/hackware1993/MagicIndicator
 * https://github.com/H07000223/FlycoTabLayout
 * https://github.com/ogaclejapan/SmartTabLayout
 */

public class TabIndicatorActivity extends BaseActivity {

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
        tvTitle.setText("TabLayout指示条长度");
        initData();
        initView();
    }

    /**
     * 接受数据后
     * 填充tab 数据
     */
    private void initData(){
        // 封装数据
        for(int i = 0;i<5;i++){
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
         * 注意：setupWithViewPager 会执行 removeAllTabs,然后重新new Tab，所以要在关联之后。
         *      调用TabLayout.getTabAt(i)方法来设置title。或者在PagerAdapter的getPageTitle()返回标题。
         * */
        mTabLayout.setupWithViewPager(viewPager);
        for(int i = 0;i<mTypeList.size();i++){
            //此处用getTab()
            mTabLayout.getTabAt(i).setText(mTypeList.get(i).getTypename());
        }
        viewPager.setCurrentItem(0,false);
        setIndicatorWidth(mTabLayout,30);
    }




    /**
     * 方法1：
     * 反射指示器变短,制定margin。
     * api 28的Tablayout 的源码发现，原来的mTabStrip和mTextView已经改名为slidingTabIndicator和textView。
     * 此方法会把整个tabView margin设置，点击范围变小。不好
     *
     * 此方法不是对TextView做修改，而是对整个tabView做修改
     */
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        /**
         * View.post()保证操作是在View宽高计算完毕之后
         * */
        tabs.post(() -> {
            Class<?> tabLayout = tabs.getClass();
            Field tabStrip = null;
            try {
                tabStrip = tabLayout.getDeclaredField("slidingTabIndicator");
                //tabStrip = tabLayout.getDeclaredField("mTabStrip");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            //设置该字段对象的可访问标志,在其他类里获取该类的私有成员变量时，需要设置访问标志为true，否则会报异常
            tabStrip.setAccessible(true);
            LinearLayout llTab = null;
            try {
                llTab = (LinearLayout) tabStrip.get(tabs);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //转变为标准尺寸的一个函数
            int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
            int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());


            //直接获取子view改变宽度
            for (int i = 0; i < llTab.getChildCount(); i++) {
                View child = llTab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        });
    }


    /**
     * 方法2：
     * 只对TextView做修改
     * @param tabLayout
     * @param margin
     */
    public void setIndicatorWidth(@NonNull final TabLayout tabLayout, final int margin) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    // 拿到tabLayout的slidingTabIndicator属性
                    Field slidingTabIndicatorField = tabLayout.getClass().getDeclaredField("slidingTabIndicator");
                    slidingTabIndicatorField.setAccessible(true);
                    LinearLayout mTabStrip = (LinearLayout) slidingTabIndicatorField.get(tabLayout);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性
                        Field textViewField = tabView.getClass().getDeclaredField("textView");
                        textViewField.setAccessible(true);
                        TextView mTextView = (TextView) textViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        // 因为想要的效果是字多宽线就多宽，所以测量mTextView的宽度
                        int width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }
                        // 设置tab左右间距,注意这里不能使用Padding,因为源码中线的宽度是根据tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = margin;
                        params.rightMargin = margin;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
