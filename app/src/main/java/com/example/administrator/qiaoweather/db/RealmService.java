package com.example.administrator.qiaoweather.db;


import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2017/2/23.
 */

public class RealmService {
    private Realm mRealm;

    public RealmService() {
        mRealm = Realm.getDefaultInstance();
    }


    public void closeRealm() {
        mRealm.close();
    }

    public interface OnTransactionCallback {
        void onRealmSucess();

        void onRealmErroe(Exception e);
    }
}
