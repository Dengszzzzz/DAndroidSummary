package com.sz.dengzh.drxjavasummary;

import android.app.Application;

import com.socks.library.KLog;
import com.sz.dengzh.commonlib.CommonConfig;

/**
 * Created by dengzh on 2018/4/18.
 */

public class RxJavaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CommonConfig.init(this);
    }






}
