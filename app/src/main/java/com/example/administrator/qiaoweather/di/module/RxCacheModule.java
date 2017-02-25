package com.example.administrator.qiaoweather.di.module;

import com.example.administrator.qiaoweather.App;
import com.example.administrator.qiaoweather.util.DataHelper;
import java.io.File;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Created by Administrator on 2017/2/25.
 */
@Singleton
@Module
public class RxCacheModule {

    public RxCacheModule() {
    }


    @Provides
    @Singleton
    RxCache provideRxCache() {
        File cacheDir = DataHelper.getCacheFile(App.getInstance());
        File cacheDirectory = new File(cacheDir, "RxCache");

        return new RxCache
                .Builder()
                .persistence(DataHelper.makeDirs(cacheDirectory), new GsonSpeaker());
    }
}
