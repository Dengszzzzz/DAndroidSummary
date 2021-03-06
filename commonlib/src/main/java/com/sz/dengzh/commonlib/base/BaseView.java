package com.sz.dengzh.commonlib.base;


import android.support.annotation.NonNull;

import com.sz.dengzh.commonlib.bean.NetBean;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

public interface BaseView {

     void handleFailResponse(NetBean netBean);  //统一处理响应失败
     void showLoading();
     void dismissLoading();

     //为了让 IView 可以调用 RxLifeCycle的生命周期绑定
     <T> LifecycleTransformer<T> bindToLifecycle();
     <T> LifecycleTransformer<T> bindUntilEvent(@NonNull ActivityEvent event);

}
