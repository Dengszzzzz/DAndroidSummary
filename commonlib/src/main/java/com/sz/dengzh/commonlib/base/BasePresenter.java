package com.sz.dengzh.commonlib.base;



public interface BasePresenter<V extends BaseView>{
    void attachView(V view);

    void detachView();
}
