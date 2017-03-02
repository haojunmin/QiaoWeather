package com.example.administrator.qiaoweather.db;


import com.example.administrator.qiaoweather.App;
import com.example.administrator.qiaoweather.enty.City;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2017/2/23.
 */

public class RealmService {
    private static Realm mRealm;
    App mApp;

    RealmConfiguration realmConfiguration;

    public RealmService(RealmConfiguration realmConfiguration) {
        this.realmConfiguration = realmConfiguration;
    }

    public Realm getmRealm() {
        mRealm = Realm.getInstance(realmConfiguration);

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
