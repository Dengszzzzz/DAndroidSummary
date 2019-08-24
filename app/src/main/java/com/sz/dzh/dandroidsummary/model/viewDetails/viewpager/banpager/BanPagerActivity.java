package com.sz.dzh.dandroidsummary.model.viewDetails.viewpager.banpager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.utils.glideUtils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ViewPager 广告页
 * ViewPager实现轮播图效果，通过addOnPageChangeListener()，在 OnPageChangeListener 监听器中有一个页面
 * 滑动结束时的回调方法 onPageSelected(int position) ,我们只需要在这个方法中，来设置item和指示器跟随变化就行了。
 * 指示器就是在ViewPager上放一个覆盖层。
 */
public class BanPagerActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.ll_dot)
    LinearLayout mLlDot;

    private List<String> list = new ArrayList<>();
    private int lastPosition;
    private long delayMillis = 2000;  //切换间隔，2s
    private boolean isAutoPlay = true;  //是否自动切换

    private int type;  //0-轮播  1-首尾轮播

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_ban_pager);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("banner广告页");
        initData();
        initView();
    }

    private void initData(){
        list.add("http://pic15.nipic.com/20110628/1369025_192645024000_2.jpg");
        list.add("http://pic25.nipic.com/20121112/9252150_150552938000_2.jpg");
        list.add("http://www.2cto.com/uploadfile/Collfiles/20140615/20140615094106112.jpg");
    }

    private void initView(){
        initDotView();
        mViewPager.setAdapter(new BannerAdapter(this,list));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            /**
             * 页面滑动结束时回调
             * @param i
             */
            @Override
            public void onPageSelected(int i) {
                mLlDot.getChildAt(lastPosition).setBackgroundResource(R.drawable.dot_grey);
                if(type == 0){
                    lastPosition = i ;
                }else if(type == 1){
                    lastPosition = i % list.size();
                }
                mLlDot.getChildAt(lastPosition).setBackgroundResource(R.drawable.dot_blue);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        if(type == 0){
            mViewPager.setCurrentItem(list.size());
        }else if(type == 1){
            mViewPager.setCurrentItem(10000 * list.size());
        }

        mHandler.sendEmptyMessage(1);
    }


    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1 && isAutoPlay){
                if(type == 0) {
                    //这种除模方式，会到第3张时，又切回第1张，但是中间划过了第二张
                    mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1) % list.size());
                }else if(type == 1) {
                    //无限轮播时
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                }
                mHandler.sendEmptyMessageDelayed(1,delayMillis);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(1);
    }

    /**
     * 初始化指示器
     */
    private void initDotView(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
        params.setMargins(10,0,10,0);
        for(int i = 0;i<list.size();i++){
            View view = new View(this);
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.dot_grey);
            mLlDot.addView(view);
        }
    }


    /**
     * Banner适配器
     */
    class BannerAdapter extends PagerAdapter{

        private Context mContext;
        private List<String> mList;

        public BannerAdapter(Context context, List<String> list) {
            mContext = context;
            mList = list;
        }

        /**
         * 是获取当前窗体界面数，也就是数据的个数。
         * @return
         */
        @Override
        public int getCount() {
            int count = mList.size();
            if(type == 1){
                count =  Integer.MAX_VALUE;
            }
            return count;
        }

        /**
         * 这个方法用于判断是否由对象生成界面，官方建议直接返回 return view == object;
         * @param view
         * @param o
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view==o;
        }

        /**
         * 要显示的页面或需要缓存的页面，会调用这个方法进行布局的初始化。
         * @param container
         * @param position
         * @return
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            if(type == 0){
                GlideUtils.loadImg(mContext, view, mList.get(position));
            }else if(type == 1){
                GlideUtils.loadImg(mContext, view, mList.get(position%mList.size()));
            }
            container.addView(view);
            return view;
        }

        /**
         * 如果页面不是当前显示的页面也不是要缓存的页面，会调用这个方法，将页面销毁。
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
