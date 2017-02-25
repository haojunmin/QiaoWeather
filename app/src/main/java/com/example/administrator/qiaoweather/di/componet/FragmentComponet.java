package com.example.administrator.qiaoweather.di.componet;

import android.app.Activity;

import com.example.administrator.qiaoweather.di.FragmentScope;
import com.example.administrator.qiaoweather.di.module.FragmentModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/2/25.
 */
@FragmentScope
@Component(modules = FragmentModule.class)
public interface FragmentComponet {
    Activity getActivity();
}
