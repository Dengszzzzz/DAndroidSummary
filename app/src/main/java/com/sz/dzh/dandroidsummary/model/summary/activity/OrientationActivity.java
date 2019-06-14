package com.sz.dzh.dandroidsummary.model.summary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by administrator on 2018/7/3.
 * activity的生命周期验证
 * 1.默认情况下横竖屏切换都是重建一次activity
 * 2.activity被回收，会调用onSaveInstanceState，在onStop()之前
 * 3.被回收后重建，会调用onRestoreInstanceState，在onStart()之后
 * 4.可以在onCreate()做非空判断，获取保存的信息
 */

public class OrientationActivity extends RxFragmentActivity {


    @BindView(R.id.descTv)
    TextView mDescTv;
    @BindView(R.id.tvTitle)
    TextView mTvTitle;

    private StringBuilder mBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_orientaition);
        ButterKnife.bind(this);
        mTvTitle.setText("横竖屏切换");
        mBuilder = new StringBuilder();


        if(savedInstanceState!=null){
            String string = savedInstanceState.getString("saveContent");
            mBuilder.append("保存的内容:\n").append(string);
        }


        mBuilder.append("-----------生命周期-----------\n")
                .append("onCreate()\n");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBuilder.append("onStart()\n");
        KLog.e("onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBuilder.append("onResume()\n");
        KLog.e("onResume()");

        mDescTv.setText(mBuilder.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBuilder.append("onPause()\n");
        KLog.e("onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBuilder.append("onStop()\n");
        KLog.e("onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBuilder.append("onDestroy()\n");
        KLog.e("onDestroy()");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        mBuilder.append("onRestart()\n");
        KLog.e("onRestart()");
    }

    /**
     * 被回收， 在onPause之后，onStop()之前执行
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBuilder.append("Activity被回收，调用onSaveInstanceState()\n")
                .append("--------------------\n");
        KLog.e("onSaveInstanceState:" + mBuilder.toString());

        outState.putString("saveContent", mBuilder.toString());
    }

    /**
     * 在onStart()之后，onResume()之前执行
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mBuilder.append("Activity重建，调用onRestoreInstanceState()\n");

        String string = savedInstanceState.getString("saveContent");
        mBuilder.append(string);
        KLog.e("onRestoreInstanceState:" + string);
    }


    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }
}
