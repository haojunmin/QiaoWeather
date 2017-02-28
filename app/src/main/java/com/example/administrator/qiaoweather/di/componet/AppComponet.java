package com.example.administrator.qiaoweather.di.componet;

import com.example.administrator.qiaoweather.App;
import com.example.administrator.qiaoweather.CityListActivity;
import com.example.administrator.qiaoweather.MainActivity;
import com.example.administrator.qiaoweather.db.RealmService;
import com.example.administrator.qiaoweather.di.module.ActivityModule;
import com.example.administrator.qiaoweather.di.module.AppModule;
import com.example.administrator.qiaoweather.http.RetrofitHelper;

import com.example.administrator.qiaoweather.imageloader.ImageLoader;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/2/25.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponet {
    void inject(App app);


    App getContext();

    RealmService realmService();

    RetrofitHelper retrofitHelper();

    ImageLoader imageLoader();


}
