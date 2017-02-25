package com.example.administrator.qiaoweather.di.componet;

import com.example.administrator.qiaoweather.App;
import com.example.administrator.qiaoweather.db.RealmService;
import com.example.administrator.qiaoweather.di.module.AppModule;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by Administrator on 2017/2/25.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponet {
    App getContext();

    RealmService realmService();

//    ImageModule imageModule();
}
