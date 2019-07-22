package com.sz.dzh.dandroidsummary.model.summary.rxjava.rxlifecycle;

import android.content.Context;

import com.sz.dzh.dandroidsummary.base.BasePresenter;
import com.sz.dzh.dandroidsummary.base.BaseView;

import org.json.JSONObject;

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
