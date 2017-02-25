package com.example.administrator.qiaoweather;

import android.app.Application;

import com.example.administrator.qiaoweather.di.componet.AppComponet;
import com.example.administrator.qiaoweather.di.componet.DaggerAppComponet;
import com.example.administrator.qiaoweather.di.componet.DaggerImageComponet;
import com.example.administrator.qiaoweather.di.componet.DaggerRxCacheComponet;
import com.example.administrator.qiaoweather.di.componet.ImageComponet;
import com.example.administrator.qiaoweather.di.componet.RxCacheComponet;
import com.example.administrator.qiaoweather.di.module.AppModule;
import com.example.administrator.qiaoweather.di.module.ImageModule;
import com.example.administrator.qiaoweather.di.module.RxCacheModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2017/2/23.
 * 对于这种空构造函数的Modfule，是不是在最新版的Dagger2当中必须要构造一个Componet，然后才能注册成功呢？
 */

public class App extends Application {
    private static App instance;
    private static final String DB_NAME = "weather.realm";
    private static AppComponet mAppComponet;
    private static RxCacheComponet mRxCacheComponet;
    private static ImageComponet mImageComponet;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initRealm();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name(DB_NAME).build();
        Realm.deleteRealm(config);
        Realm.setDefaultConfiguration(config);
    }

    public static AppComponet getAppComponet() {
        if (mAppComponet == null) {
            mAppComponet = DaggerAppComponet.builder().appModule(new AppModule(instance)).build();
        }
        return mAppComponet;
    }

    public static RxCacheComponet getRxCacheComponet() {
        if (mRxCacheComponet == null) {

            mRxCacheComponet = DaggerRxCacheComponet.builder().rxCacheModule(new RxCacheModule()).build();
        }
        return mRxCacheComponet;
    }

    public static ImageComponet getImageComponet() {
        if (mImageComponet == null) {
            mImageComponet = DaggerImageComponet.builder().imageModule(new ImageModule()).build();
        }
        return mImageComponet;
    }

}
