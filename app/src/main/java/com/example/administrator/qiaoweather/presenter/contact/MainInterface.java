package com.example.administrator.qiaoweather.presenter.contact;

import com.example.administrator.qiaoweather.base.BasePresenter;

import com.example.administrator.qiaoweather.enty.HeFengWeather;


/**
 * Created by Administrator on 2017/2/26.
 */

public interface MainInterface {

    interface View {

        void startLocation();

        void LocationSucess();

        void LocationFail();

        void setViewTitle(String title);

        void getWeather(HeFengWeather weatherReply);
        void getWeatherFailed();
    }

    interface Presenter extends BasePresenter {
        void weather(String city);

        void startLocation();

        void stopLocation();
    }
}
