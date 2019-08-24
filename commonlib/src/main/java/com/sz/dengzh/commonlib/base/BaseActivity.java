package com.sz.dengzh.commonlib.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.socks.library.KLog;
import com.sz.dengzh.commonlib.R;
import com.sz.dengzh.commonlib.bean.NetBean;
import com.sz.dengzh.commonlib.utils.AppManager;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import java.lang.reflect.ParameterizedType;

/**
 * base
 */
public abstract class BaseActivity<V extends BaseView, P extends BasePresenterImpl<V>> extends RxFragmentActivity implements BaseView {

    protected String TAG = getClass().getSimpleName();

    //标题栏
    protected RelativeLayout titleBar;
    protected ImageView ivBack,ivRight;
    protected TextView tvBack,tvTitle,tvRight;

    public P mPresenter;
    KProgressHUD kProgressHUD;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置只能竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //加入activity管理器
        AppManager.getAppManager().addActivity(this);
        //打印
        printRunningActivity(this, true);



        kProgressHUD = new KProgressHUD(this);
        mPresenter = getInstance(this, 1);
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }

    }

    /**
     * 初始化标题
     */
    protected void initTitle(){
        titleBar = findViewById(R.id.titleBar);
        ivBack =  findViewById(R.id.ivBack);
        tvBack = findViewById(R.id.tvBack);
        tvTitle =  findViewById(R.id.tvTitle);
        ivRight =  findViewById(R.id.ivRight);
        tvRight =  findViewById(R.id.tvRight);
        ivBack.setOnClickListener(v -> finish());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        printRunningActivity(this, false);
        AppManager.getAppManager().finishActivity(this);
        if (mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
            kProgressHUD = null;
        }
    }

    //反射获取当前Presenter对象
    public <M> M getInstance(Object o, int i) {
        try {
            return ((Class<M>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            if (kProgressHUD.isShowing()) {
                return;
            }
            kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .setCancellable(true)
                    .show();
        });
    }

    @Override
    public void dismissLoading() {
        if (kProgressHUD != null && kProgressHUD.isShowing()) {
            runOnUiThread(() -> kProgressHUD.dismiss());
        }
    }

    /**
     * 跳转 减少重复代码
     * @param tarActivity 目标activity
     */
    public void startActivity(Class<? extends Activity> tarActivity) {
        Intent intent = new Intent(this, tarActivity);
        startActivity(intent);
       // overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    /*@Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }*/


    /**
     * 打印activity信息
     *
     * @param ac
     * @param isRunning
     */
    protected void printRunningActivity(Activity ac, boolean isRunning) {
        String contextString = ac.toString();
        String s = contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
        if (isRunning) {
            KLog.e("Activity", "app:当前正在加入的界面是:" + s);
        } else {
            KLog.e("Activity", "app:当前销毁的界面是:" + s);
        }
    }


    //统一处理请求失败业务
    @Override
    public void handleFailResponse(NetBean netBean) {

    }
}
