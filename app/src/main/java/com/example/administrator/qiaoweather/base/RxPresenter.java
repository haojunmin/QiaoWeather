package com.example.administrator.qiaoweather.base;

import com.orhanobut.logger.Logger;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2017/2/23.
 */

public abstract class RxPresenter implements BasePresenter {
    protected CompositeDisposable compositeDisposable;

    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    protected void clearDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }


    @Override
    public void detachView() {
        Logger.d("destory");
        clearDisposable();
    }
}
