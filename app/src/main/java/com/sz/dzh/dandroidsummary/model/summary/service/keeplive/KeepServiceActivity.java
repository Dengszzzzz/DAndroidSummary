package com.sz.dzh.dandroidsummary.model.summary.service.keeplive;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.sz.dengzh.commonlib.base.BaseActivity;
import com.sz.dzh.dandroidsummary.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dengzh on 2018/4/23.
 * 保活测试
 */

public class KeepServiceActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_keep_service);
        ButterKnife.bind(this);
        initTitle();
        tvTitle.setText("保活设置");
    }

    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:  //启动服务
                Intent intent = new Intent(this,KeepliveService.class);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                }else{
                    startService(intent);
                }
                break;
            case R.id.btn2:  //跳转白名单设置界面
                SettingUtils.enterWhiteListSetting(this);
                break;
        }
    }
}
