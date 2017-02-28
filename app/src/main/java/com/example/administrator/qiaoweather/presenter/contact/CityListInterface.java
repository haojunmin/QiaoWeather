package com.example.administrator.qiaoweather.presenter.contact;

import com.example.administrator.qiaoweather.base.BasePresenter;
import com.example.administrator.qiaoweather.enty.City;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by Administrator on 2017/2/27.
 */

public interface CityListInterface {
    interface View
    {
        void getCityData(RealmResults<City> cities);
    }

    interface Presenter extends BasePresenter
    {
        void queryCityData();
        void closeRealm();
    }
}
