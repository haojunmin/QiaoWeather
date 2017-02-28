package com.example.administrator.qiaoweather.db;


import com.example.administrator.qiaoweather.App;
import com.example.administrator.qiaoweather.enty.City;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2017/2/23.
 */

public class RealmService {
    private Realm mRealm;
    App mApp;

    public RealmService(App app) {
        this.mApp = app;
    }

    public Realm getmRealm() {
        mRealm = Realm.getInstance(mApp.getRealmConfiguration());
        return mRealm;
    }

    public RealmResults<City> findAllCity() {
        return mRealm.where(City.class).findAllAsync();
    }

    public void setmRealm(Realm mRealm) {
        this.mRealm = mRealm;
    }

    public void save(final String city) {
        mRealm.beginTransaction();
        City p = mRealm.createObject(City.class);
        p.setName(city);
        mRealm.commitTransaction();
        mRealm.close();
    }

    public void closeRealm() {
        if (mRealm != null) {
            mRealm.close();
        }

    }

    public interface OnTransactionCallback {
        void onRealmSucess();

        void onRealmErroe(Exception e);
    }
}
