package com.sz.dzh.dandroidsummary.base;



public interface BasePresenter<V extends BaseView>{
    void attachView(V view);

    void detachView();
}
