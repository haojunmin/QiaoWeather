package com.example.administrator.qiaoweather.presenter;

import android.Manifest;
import android.app.Activity;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.administrator.qiaoweather.App;
import com.example.administrator.qiaoweather.base.RxPresenter;
import com.example.administrator.qiaoweather.enty.HeFengWeather;
import com.example.administrator.qiaoweather.enty.LocationCity;
import com.example.administrator.qiaoweather.http.RetrofitHelper;
import com.example.administrator.qiaoweather.presenter.contact.MainInterface;
import com.example.administrator.qiaoweather.util.SharedPreferenceUtil;
import com.example.administrator.qiaoweather.util.ToastUtil;
import com.example.administrator.qiaoweather.util.Util;
import com.example.administrator.qiaoweather.util.Utils;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/2/26.
 */

public class MainPresenter extends RxPresenter implements MainInterface.Presenter, AMapLocationListener {

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    String mPreCity;

    MainInterface.View mView;

    private RetrofitHelper retrofitHelper;

    private Activity activity;

    @Inject
    public MainPresenter(RetrofitHelper retrofitHelper, Activity activity) {
//        this.mView=View;
        if (activity instanceof MainInterface.View) {
            mView = (MainInterface.View) activity;
            Logger.d(mView);
        }
        this.retrofitHelper = retrofitHelper;
        rxPermissions = new RxPermissions(activity);
    }


    @Override
    public void weather(final String city) {
        Logger.d(mView);
        addDisposable(retrofitHelper.fetchWeather(city).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<HeFengWeather>() {
                    @Override
                    public void accept(@NonNull HeFengWeather heFengWeather) throws Exception {


                        if (heFengWeather == null) {
                            mView.getWeatherFailed();
                            ;
                        } else {
                            Logger.d(heFengWeather.toString());
                            mView.setViewTitle(city);
                            mView.getWeather(heFengWeather);
                        }

                    }
                }));
    }


    RxPermissions rxPermissions;

    /**
     * 请求定位这个方法比较耗时，所以就把它放在io线程里面
     */
    @Override
    public void startLocation() {
        Logger.d(mView);


        addDisposable(rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION).observeOn(Schedulers.io())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        Logger.d(Thread.currentThread().getName());
                        if (aBoolean) {
                            mPreCity = SharedPreferenceUtil.getInstance().getCityName();

                            if (!TextUtils.isEmpty(mPreCity)) {
                                weather(mPreCity);
                            }
                            initLocation();
                            location();
                        } else {
                            ToastUtil.showLong("需要获取位置权限");
                        }
                    }
                }));


    }


    @Override
    public void stopLocation() {
        destoryLocation();
    }

    public void cleanView() {
        mView = null;
    }

    private void destoryLocation() {
        if (locationClient != null) {
            locationClient.stopLocation();
            ;
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {


        if (aMapLocation != null) {
            String result = Utils.getLocationStr(aMapLocation);

            if (aMapLocation.getErrorCode() == 0) {
                String city = Util.replaceCity(aMapLocation.getCity());
                if (!TextUtils.isEmpty(city)) {
                    Logger.d("mPreCity" + mPreCity + "city" + city);
                    if (city.equals(mPreCity)) {

                    } else {
                        //更新数据
                        weather(city);
                    }
                }


                Logger.d(Util.replaceCity(aMapLocation.getCity()));
                SharedPreferenceUtil.getInstance().setCityName(Util.replaceCity(aMapLocation.getCity()));
            } else {
                mView.LocationFail();

            }
        } else {
            mView.LocationFail();
        }
    }

    private void initLocation() {
        //初始化client
        locationClient = new AMapLocationClient(App.getInstance());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(this);
    }


    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(10000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }


    private void location() {
        locationClient.startLocation();
    }
}
