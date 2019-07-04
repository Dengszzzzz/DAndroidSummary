package com.sz.dzh.dandroidsummary.model.summary.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by administrator on 2018/7/3.
 * 横竖屏切换不创建activity的方法
 * 1.设置横竖屏，可以在AndroidManifest设置，也可以代码设置。
 * 例如设置竖屏
 * 1）AndroidManifest 的 <activity android:screenOrientation="portrait" .../>
 * 2) setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
 *
 * 2.设置android:configChanges="orientation|screenSize"，横竖屏切换不重建activity。
 * 3.如果要监听横竖屏切换，可重写onConfigurationChanged()进行处理。如显示不同布局，显示不同内容等。
 */

public class ConfigChangeActivity extends AppCompatActivity {

    @BindView(R.id.descTv)
    TextView mDescTv;
    @BindView(R.id.bgIv)
    ImageView mBgIv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_config_change);
        ButterKnife.bind(this);
    }

    /**
     * android：ConfigChanges 用于捕获手机状态的改变。
     * 在Activity中添加了android:configChanges属性，当所指定属性(Configuration Changes)发生改变时，通知程序调用onConfigurationChanged()函数。
     * 例如此处设置：android:configChanges="orientation|screenSize"，切屏不重建activity。
     *
     * 可以通过 onConfigurationChanged() 监听切屏方向做一些操作，如改变布局等。
     * 注意：
     * 1.布局横竖屏都是一个布局时，可以直接对原有控件进行调用操作。
     * 2.布局分别在layout-land和layout-port目录中的同名ac_config_change.xml时,要重新setContentView，重新获取控件id。
     * 3.布局为不按照layout-land和layout-port目录，而自定义名字时，可以判断横竖屏来使用何种布局
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //testTogtherXml();
        testNoTogetherXml();
    }


    /**
     * 布局横竖屏都是一个布局时，可以直接对原有控件进行调用操作。
     */
    private void testTogtherXml(){
        //不重新设置布局，则切换还是layout-port的布局，不用重新获取控件id。
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // 竖屏
            mDescTv.setText("竖屏");
            mBgIv.setBackgroundResource(R.color.c_87cfdc);
            KLog.e("ORIENTATION_PORTRAIT=" + Configuration.ORIENTATION_PORTRAIT);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            mDescTv.setText("横屏");
            mBgIv.setBackgroundResource(R.color.colorPrimary);
            KLog.e("ORIENTATION_LANDSCAPE=" + Configuration.ORIENTATION_LANDSCAPE);
        }
    }

    /**
     * 布局分别在layout-land和layout-port目录中的同名ac_config_change.xml时,要重新setContentView，重新获取控件id。
     */
    private void testNoTogetherXml(){
        //重新设置布局，layout-land,layout-port切换生效。
        setContentView(R.layout.ac_config_change);
        //如果要设置控件，要重新获取控件id
        mDescTv = findViewById(R.id.descTv);
        mBgIv = findViewById(R.id.bgIv);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { // 竖屏
            mDescTv.setText("port-竖屏");
            mBgIv.setBackgroundResource(R.color.c_87cfdc);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            mDescTv.setText("land-横屏");
            mBgIv.setBackgroundResource(R.color.colorPrimary);
        }
    }

}
