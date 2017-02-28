package com.example.administrator.qiaoweather.presenter;

import android.app.Activity;

import com.example.administrator.qiaoweather.base.RxPresenter;
import com.example.administrator.qiaoweather.db.RealmService;
import com.example.administrator.qiaoweather.enty.HeWeatherSearch;
import com.example.administrator.qiaoweather.http.BaseObserver;
import com.example.administrator.qiaoweather.http.ExceptionHandle;
import com.example.administrator.qiaoweather.http.RetrofitHelper;
import com.example.administrator.qiaoweather.presenter.contact.AddCityInterface;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

/**
 * Created by Administrator on 2017/2/28.
 */

public class AddCityPresenter extends RxPresenter implements AddCityInterface.Persenter {

    AddCityInterface.View mView;
    private RetrofitHelper retrofitHelper;
    private RealmService realmService;

    @Inject
    public AddCityPresenter(Activity activity, RetrofitHelper retrofitHelper, RealmService realmService) {
        this.retrofitHelper = retrofitHelper;
        this.realmService = realmService;
        if (activity instanceof AddCityInterface.View) {
            mView = (AddCityInterface.View) activity;
        }
    }

    @Override
    public void search(String city) {
        addDisposable(retrofitHelper.mSearch(city).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HeWeatherSearch>() {
                    @Override
                    public void accept(@NonNull HeWeatherSearch heWeatherSearch) throws Exception {
                        mView.showResult(heWeatherSearch);
                    }
                }));
    }

    Realm realm;

    @Override
    public void save(HeWeatherSearch.HeWeather5Bean heWeather5Bean) {
        realmService.save(heWeather5Bean.getBasic().getCity());
        mView.saveSucess(heWeather5Bean);
    }

    @Override
    public void closeRealm() {
        realmService.closeRealm();
    }
}
