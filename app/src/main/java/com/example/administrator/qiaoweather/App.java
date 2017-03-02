package com.example.administrator.qiaoweather;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.example.administrator.qiaoweather.di.componet.AppComponet;
import com.example.administrator.qiaoweather.di.componet.DaggerAppComponet;
import com.example.administrator.qiaoweather.di.module.AppModule;
import com.example.administrator.qiaoweather.util.AppBlockCanaryContext;
import com.facebook.stetho.Stetho;
import com.github.moduth.blockcanary.BlockCanary;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Administrator on 2017/2/23.
 * 对于这种空构造函数的Modfule，是不是在最新版的Dagger2当中必须要构造一个Componet，然后才能注册成功呢？
 */

public class App extends Application {
    private RefWatcher refWatcher;
    private static App instance;
    private static final String DB_NAME = "weather.realm";
    private static AppComponet mAppComponet;


    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;


        mAppComponet = DaggerAppComponet.builder().appModule(new AppModule(this)).build();
        InitService.startAction(this);
    }

    public static AppComponet getmAppComponet() {
        return mAppComponet;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Logger.d("onTerminate");

        instance = null;
        mAppComponet = null;

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Logger.d("onLowMemory");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //Android应用打破65K方法数限制的方法
        // MultiDex.install(this);
    }

    RealmConfiguration realmConfiguration;

    /**
     * 初始化数据库
     */
    private void initRealm() {
        Realm.init(this);

    }

    public RealmConfiguration getRealmConfiguration() {
        return realmConfiguration;
    }
}
