package com.example.administrator.qiaoweather.http;


import com.example.administrator.qiaoweather.App;
import com.example.administrator.qiaoweather.BuildConfig;
import com.example.administrator.qiaoweather.enty.C;
import com.example.administrator.qiaoweather.enty.HeFengWeather;
import com.example.administrator.qiaoweather.http.api.ApiInterface;
import com.example.administrator.qiaoweather.util.DataHelper;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.Reply;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retorfit工具类
 */
public class RetrofitHelper {

    private static OkHttpClient okHttpClient = null;
    private ApiInterface apiInterface;

    private void init() {
        initOkHttp();
        apiInterface = getApiService(ApiInterface.HOST, ApiInterface.class);
    }

    public RetrofitHelper() {
        init();
    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = DataHelper.getCacheFile(App.getInstance());
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        builder.addNetworkInterceptor(new StethoInterceptor());
        okHttpClient = builder.build();
    }

    public <T> T getApiService(String baseUrl, Class<T> clz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clz);
    }

    public Observable<HeFengWeather> fetchWeather(final String city) {
        return apiInterface.mWeatherAPI(city, C.KEY);
    }
}
