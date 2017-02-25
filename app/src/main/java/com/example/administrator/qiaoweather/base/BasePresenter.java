package com.example.administrator.qiaoweather.base;

/**
 * Created by Administrator on 2017/2/23.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
