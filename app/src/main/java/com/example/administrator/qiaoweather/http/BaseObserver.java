package com.example.administrator.qiaoweather.http;

import android.content.Context;
import com.orhanobut.logger.Logger;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<T> {
    private Context context;


    @Override
    public void onError(Throwable e) {
        Logger.d(e);
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }


    @Override
    public void onComplete() {

    }


    public abstract void onError(ExceptionHandle.ResponeThrowable e);

}
