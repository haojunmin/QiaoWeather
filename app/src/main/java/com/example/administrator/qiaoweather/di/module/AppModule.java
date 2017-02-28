package com.example.administrator.qiaoweather.di.module;


import com.example.administrator.qiaoweather.App;
import com.example.administrator.qiaoweather.db.RealmService;
import com.example.administrator.qiaoweather.http.RetrofitHelper;

import com.example.administrator.qiaoweather.imageloader.BaseImageLoaderStrategy;
import com.example.administrator.qiaoweather.imageloader.ImageLoader;
import com.example.administrator.qiaoweather.imageloader.glide.GlideImageLoaderStrategy;
import com.example.administrator.qiaoweather.util.DataHelper;

import java.io.File;
import java.lang.annotation.Retention;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Created by Administrator on 2017/2/23.
 */
@Singleton
@Module
public class AppModule {
    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return app;
    }

    @Provides
    @Singleton
    RealmService provideRealmService() {
        return new RealmService(app);
    }

    @Provides
    @Singleton
    RetrofitHelper provideRetrofitHelper() {
        return new RetrofitHelper();
    }

    @Provides
    @Singleton
    public GlideImageLoaderStrategy provideGlideImageLoaderStrategy() {
        return new GlideImageLoaderStrategy();
    }

    @Provides
    @Singleton
    public BaseImageLoaderStrategy provideImageLoaderStrategy(GlideImageLoaderStrategy glideImageLoaderStrategy) {
        return glideImageLoaderStrategy;
    }

    @Provides
    @Singleton
    public ImageLoader provideImageLoader(BaseImageLoaderStrategy baseImageLoaderStrategy) {
        return new ImageLoader(baseImageLoaderStrategy);
    }


    ;
}
