package com.example.administrator.qiaoweather.http;

import android.util.Log;

import com.example.administrator.qiaoweather.App;
import com.example.administrator.qiaoweather.enty.C;
import com.example.administrator.qiaoweather.enty.HeFengWeather;
import com.example.administrator.qiaoweather.enty.HeWeatherSearch;
import com.example.administrator.qiaoweather.http.api.ApiInterface;
import com.example.administrator.qiaoweather.http.api.CacheProviders;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/2/25.
 */

public class RxCacheHelper {
    RxCache mRxCache;
    private ApiInterface apiInterface;
    RetrofitHelper retrofitHelper;
    private CacheProviders cacheProviders;

    @Inject
    public RxCacheHelper(RxCache mRxCache, RetrofitHelper retrofitHelper) {
        this.mRxCache = mRxCache;
        this.retrofitHelper = retrofitHelper;
        apiInterface = retrofitHelper.getApiService(ApiInterface.HOST, ApiInterface.class);
        cacheProviders = mRxCache.using(CacheProviders.class);
    }

    /**
     * 查询城市的当天天气状况
     *
     * @param city
     * @param observer
     */
    public void fetchWeather(final String city, BaseObserver<Reply<HeFengWeather>> observer) {

        cacheProviders.mWeatherAPI(apiInterface.mWeatherAPI(city, C.KEY), new DynamicKey(city)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        ;
    }

    /**
     * 搜索是否有这个城市
     *
     * @param city
     * @param observer
     */
    public void queryWeather(String city, BaseObserver<Reply<HeWeatherSearch>> observer) {
        cacheProviders.mSearch(apiInterface.mSearch(city, C.KEY), new DynamicKey(city)).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
