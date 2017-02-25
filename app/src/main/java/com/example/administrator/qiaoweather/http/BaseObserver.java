package com.example.administrator.qiaoweather.http;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.qiaoweather.widget.LoadingDialog;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;


public abstract class BaseObserver<T> implements Observer<T> {
    private Context context;

    public BaseObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onError(Throwable e) {
        Timber.d(e);
        if (e instanceof ExceptionHandle.ResponeThrowable) {
            onError((ExceptionHandle.ResponeThrowable) e);
        } else {
            onError(new ExceptionHandle.ResponeThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        Toast.makeText(context, "建立连接", Toast.LENGTH_SHORT).show();

        //可以弹出Dialog 提示正在加载
        LoadingDialog.showDialogForLoading(context);
    }

    //protected abstract void hideDialog();

    // protected abstract void showDialog();

    @Override
    public void onComplete() {

        Toast.makeText(context, "请求完毕", Toast.LENGTH_SHORT).show();
        //可以取消Dialog 加载完毕
        LoadingDialog.cancelDialogForLoading();
    }


    public abstract void onError(ExceptionHandle.ResponeThrowable e);

}
