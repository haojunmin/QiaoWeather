package com.example.administrator.qiaoweather.http.api;



import com.example.administrator.qiaoweather.enty.HeFengWeather;

import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;

/**
 * Created by Administrator on 2017/2/24.
 */

public interface CacheProviders {

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<HeFengWeather>> mWeatherAPI(Observable<HeFengWeather> observable, DynamicKey idLastUserQueried);


}
