package com.sz.dzh.dandroidsummary.base;


import com.sz.dzh.dandroidsummary.bean.NetBean;
import com.trello.rxlifecycle2.LifecycleTransformer;

public interface BaseView {

     void handleFailResponse(NetBean netBean);  //统一处理响应失败
     void showLoading();
     void dismissLoading();
     <T> LifecycleTransformer<T> bindToLifecycle();   //为了让接口可以调用 RxFragmentActivity的bindToLifecycle()方法

}
