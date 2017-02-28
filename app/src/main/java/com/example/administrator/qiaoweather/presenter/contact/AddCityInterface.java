package com.example.administrator.qiaoweather.presenter.contact;

import com.example.administrator.qiaoweather.base.BasePresenter;
import com.example.administrator.qiaoweather.enty.HeWeatherSearch;

/**
 * Created by Administrator on 2017/2/28.
 */

public interface AddCityInterface {
    interface View {
        void showResult(HeWeatherSearch heWeatherSearch);

        void saveSucess(HeWeatherSearch.HeWeather5Bean heWeather5Bean);
    }

    interface Persenter extends BasePresenter {
        void search(String city);

        void save(HeWeatherSearch.HeWeather5Bean city);

        void closeRealm();
    }
}
