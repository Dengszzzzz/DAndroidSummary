package com.sz.dzh.dandroidsummary.model.viewDetails.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.socks.library.KLog;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.base.BaseActivity;
import com.sz.dzh.dandroidsummary.utils.ToastUtils;
import com.sz.dzh.dandroidsummary.widget.custom.MyView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyViewActivity extends BaseActivity{

    @BindView(R.id.myView)
    MyView mMyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_my_view);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("简单验证自定义view和viewGroup");

        mMyView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KLog.d(TAG,"onTouch");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ToastUtils.showToast("onTouch 的 ACTION_DOWN");
                        KLog.d(TAG, "onTouch 的 ACTION_DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        ToastUtils.showToast("onTouch 的 ACTION_MOVE" + "  x:" +  event.getX() +
                                "  y:" + event.getY());
                        KLog.d(TAG, "onTouch 的 ACTION_MOVE" + "  x:" +  event.getX() +
                                "  y:" + event.getY());
                        break;
                    case MotionEvent.ACTION_UP:
                        ToastUtils.showToast("onTouch 的 ACTION_UP");
                        KLog.d(TAG, "onTouch 的 ACTION_UP");
                        break;
                }
                return true;
            }
        });
    }

}
