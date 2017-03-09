package com.example.administrator.qiaoweather.http;


import com.example.administrator.qiaoweather.App;
import com.example.administrator.qiaoweather.BuildConfig;
import com.example.administrator.qiaoweather.enty.C;
import com.example.administrator.qiaoweather.enty.HeFengWeather;
import com.example.administrator.qiaoweather.enty.HeWeatherSearch;
import com.example.administrator.qiaoweather.http.api.ApiInterface;
import com.example.administrator.qiaoweather.util.DataHelper;
import com.example.administrator.qiaoweather.util.SystemUtil;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitHelper工具类
 */
public class RetrofitHelper {

    private static OkHttpClient okHttpClient = null;
    private ApiInterface apiInterface;


    public RetrofitHelper() {
        Logger.d("RetrofitHelper");
        initOkHttp();
        apiInterface = getApiService(ApiInterface.HOST, ApiInterface.class);
    }


    private static void initOkHttp() {
        Logger.d("initOkHttp");

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = DataHelper.getCacheFile(App.getInstance());
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);

//        Interceptor cacheInterceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                if (!SystemUtil.isNetworkConnected()) {
//                    request = request.newBuilder()
//                            .cacheControl(CacheControl.FORCE_CACHE)
//                            .build();
//                }
//                Response response = chain.proceed(request);
//                if (SystemUtil.isNetworkConnected()) {
//                    int maxAge = 0;
//                    // 有网络时, 不缓存, 最大保存时长为0
//                    response.newBuilder()
//                            .header("Cache-Control", "public, max-age=" + maxAge)
//                            .removeHeader("Pragma")
//                            .build();
//                } else {
//                    // 无网络时，设置超时为4周
//                    int maxStale = 60 * 60 * 24 * 28;
//                    response.newBuilder()
//                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                            .removeHeader("Pragma")
//                            .build();
//                }
//                return response;
//            }
//        };

        OfflineRequestInterceptor cacheInterceptor = new OfflineRequestInterceptor();
        //设置缓存
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
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

    private static Retrofit retrofit;

    public <T> T getApiService(String baseUrl, Class<T> clz) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit.create(clz);
    }

    public Observable<HeFengWeather> fetchWeather(final String city) {
        return apiInterface.mWeatherAPI(city, C.KEY);
    }

    public Observable<HeWeatherSearch> mSearch(String city) {
        return apiInterface.mSearch(city, C.KEY);
    }
}
