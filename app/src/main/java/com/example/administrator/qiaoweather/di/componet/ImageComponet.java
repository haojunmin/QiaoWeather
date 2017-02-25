package com.example.administrator.qiaoweather.di.componet;

import com.example.administrator.qiaoweather.di.module.ImageModule;
import com.example.administrator.qiaoweather.imageloader.ImageLoader;
import javax.inject.Singleton;
import dagger.Component;
import dagger.Module;

/**
 * Created by Administrator on 2017/2/25.
 */
@Singleton
@Component(modules = ImageModule.class)
public interface ImageComponet {
    ImageLoader imageLoader();
}
