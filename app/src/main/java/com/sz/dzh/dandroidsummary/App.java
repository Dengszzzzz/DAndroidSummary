package com.sz.dzh.dandroidsummary;

import android.app.Application;
import com.sz.dengzh.commonlib.CommonConfig;
import com.sz.dzh.dandroidsummary.utils.CrashHandler;
import com.xiaojinzi.component.Component;
import com.xiaojinzi.component.impl.application.ModuleManager;

/**
 * Created by dengzh on 2018/4/18.
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        CommonConfig.init(this);
        CrashHandler.getInstance().init(this);

        initComponent();
    }

    /**
     * 组件化：壳工程 App 的 Application 配置
     */
    private void initComponent(){
        // 初始化
        Component.init(this, BuildConfig.DEBUG);
        // 如果你依赖了 rx 版本,需要配置这句代码,否则删除这句
        // RxErrorIgnoreUtil.ignoreError();
        // 注册业务模块,注册的字符串是各个业务模块配置在 build.gradle 中的 HOST
        ModuleManager.getInstance().registerArr("app","drxjava");
        if (BuildConfig.DEBUG) {
            // 框架还带有检查重复的路由和重复的拦截器等功能,在 `debug` 的时候开启它
            ModuleManager.getInstance().check();
        }
    }






}
