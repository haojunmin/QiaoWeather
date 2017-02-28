package com.example.administrator.qiaoweather.di.componet;

import android.app.Activity;

import com.example.administrator.qiaoweather.AboutActivity;
import com.example.administrator.qiaoweather.AddCityActivity;
import com.example.administrator.qiaoweather.CityListActivity;
import com.example.administrator.qiaoweather.MainActivity;
import com.example.administrator.qiaoweather.di.ActivityScope;
import com.example.administrator.qiaoweather.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by Administrator on 2017/2/25.
 * 可以绑定不同的activity
 */

@ActivityScope
@Component(dependencies = AppComponet.class, modules = ActivityModule.class)
public interface ActivityComponet {
    Activity activity();

    void inject(MainActivity mainActivity);

    void inject(CityListActivity cityListActivity);

    void inject(AddCityActivity addCityActivity);

    void inject(AboutActivity aboutActivity);

}
