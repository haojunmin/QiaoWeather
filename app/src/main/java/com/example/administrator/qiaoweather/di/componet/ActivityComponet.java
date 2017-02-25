package com.example.administrator.qiaoweather.di.componet;

import android.app.Activity;

import com.example.administrator.qiaoweather.di.ActivityScope;
import com.example.administrator.qiaoweather.di.module.ActivityModule;

import dagger.Component;

/**
 * Created by Administrator on 2017/2/25.
 */

@ActivityScope
@Component(modules = ActivityModule.class)
public interface ActivityComponet {
    Activity getActivity();
}
