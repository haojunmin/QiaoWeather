package com.example.administrator.qiaoweather.di.module;

import com.example.administrator.qiaoweather.imageloader.BaseImageLoaderStrategy;
import com.example.administrator.qiaoweather.imageloader.ImageLoader;
import com.example.administrator.qiaoweather.imageloader.glide.GlideImageLoaderStrategy;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class ImageModule {


    public ImageModule() {

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
}
