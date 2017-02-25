package com.example.administrator.qiaoweather.http.api;


import com.example.administrator.qiaoweather.enty.HeFengWeather;
import com.example.administrator.qiaoweather.enty.HeWeatherSearch;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/1/19.
 */

public interface ApiInterface {
    public static final String HOST = "https://free-api.heweather.com/v5/";

    @GET("weather")
    Observable<HeFengWeather> mWeatherAPI(@Query("city") String city, @Query("key") String key);

    @GET("search")
    Observable<HeWeatherSearch> mSearch(@Query("city") String city, @Query("key") String key);
}
