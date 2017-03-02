package com.example.administrator.qiaoweather.presenter;

import android.app.Activity;

import com.example.administrator.qiaoweather.base.RxPresenter;
import com.example.administrator.qiaoweather.db.RealmService;
import com.example.administrator.qiaoweather.enty.City;
import com.example.administrator.qiaoweather.http.RetrofitHelper;
import com.example.administrator.qiaoweather.presenter.contact.CityListInterface;
import com.orhanobut.logger.Logger;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.Iterator;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2017/2/27.
 */

public class CityListPresenter extends RxPresenter implements CityListInterface.Presenter {

    CityListInterface.View mView;

    private RealmService realmService;

    @Inject
    public CityListPresenter(Activity activity, RealmService realmService) {
        this.realmService = realmService;
        if (activity instanceof CityListInterface.View) {
            mView = (CityListInterface.View) activity;
        }
    }

    Realm realm;

    @Override
    public void queryCityData() {

        realm = realmService.getmRealm();
        RealmResults<City> cities = realm.where(City.class).findAllAsync();
        mView.getCityData(cities);
    }

    @Override
    public void closeRealm() {
        realmService.closeRealm();
    }

    public void cleanView() {
        mView = null;
    }
}
