package com.example.administrator.qiaoweather.di.componet;

import com.example.administrator.qiaoweather.di.module.RxCacheModule;
import javax.inject.Singleton;
import dagger.Component;
import io.rx_cache2.internal.RxCache;

/**
 * Created by Administrator on 2017/2/25.
 */

@Singleton
@Component(modules = RxCacheModule.class)
public interface RxCacheComponet {
    RxCache rxCache();
}
