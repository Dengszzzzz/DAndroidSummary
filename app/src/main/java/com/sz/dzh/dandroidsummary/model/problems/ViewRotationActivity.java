package com.sz.dzh.dandroidsummary.model.problems;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.TextView;

import com.socks.library.KLog;
import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;
import com.sz.dengzh.commonlib.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dengzh
 * on 2019/7/12 0012
 */
public class ViewRotationActivity extends BaseActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;

    private int lastRotate;
    private int rotate;
    private OrientationEventListener mOrientationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_view_rotation);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("View旋转");
        init();
    }

    private void init(){

        mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {

            @Override
            public void onOrientationChanged(int orientation) {
                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;  //手机平放时，检测不到有效的角度
                }
                //因为人和手机是镜面关系，所以orientation是反着的，所以90和270位置互换
                if (orientation > 350 || orientation < 10) { //0度
                    rotate = 0;
                } else if (orientation > 80 && orientation < 100) { //90度
                    rotate = 270;
                } else if (orientation > 170 && orientation < 190) { //180度
                    rotate = 180;
                } else if (orientation > 260 && orientation < 280) { //270度
                    rotate = 90;
                } else {
                    return;
                }
                KLog.e("OrientationEventListener:orientation=" + orientation + "   rotate=" + rotate);
                //方向发生了改变，才旋转View
                if(rotate!=lastRotate){
                    lastRotate = rotate;
                    mTvDesc.setRotation(rotate);
                }
            }};
        if(mOrientationListener.canDetectOrientation()){
            mOrientationListener.enable();
        }else{
            mOrientationListener.disable();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mOrientationListener.canDetectOrientation()){
            mOrientationListener.enable();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mOrientationListener.disable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrientationListener.disable();
    }

    @OnClick({R.id.btn_rotate, R.id.tv_desc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_rotate:
                rotate = rotate + 90;
                mTvDesc.setRotation(rotate);
                break;
            case R.id.tv_desc:
                ToastUtils.showToast("点击到了");
                break;
        }
    }
}
