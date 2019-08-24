package com.sz.dengzh.drxjavasummary.module.rxlifecycle;


import com.sz.dengzh.commonlib.base.BasePresenter;
import com.sz.dengzh.commonlib.base.BaseView;

/**
 * @author
 * @date 2018/7/25
 * @description
 */
public class RxlifecContract {

     public interface View extends BaseView {
         void onTest1Success(String str);
         void onTest2Success(String str);
     }

     public interface  Presenter extends BasePresenter<View> {
         void onTest1() ;
         void onTest2() ;
    }


}
