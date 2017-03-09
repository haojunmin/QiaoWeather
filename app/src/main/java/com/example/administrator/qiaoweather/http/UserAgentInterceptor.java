package com.example.administrator.qiaoweather.http;

/**
 * Created by Administrator on 2017/3/9.
 */

import java.io.IOException;
import android.support.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Response;

public class UserAgentInterceptor implements Interceptor {

    @NonNull
    private final String userAgent;

    public UserAgentInterceptor(@NonNull String userAgent) {
        this.userAgent = userAgent;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request().newBuilder()
                .header("User-Agent", userAgent)
                .build());
    }
}