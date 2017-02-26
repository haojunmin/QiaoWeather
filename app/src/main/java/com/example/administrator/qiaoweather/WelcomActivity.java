package com.example.administrator.qiaoweather;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.administrator.qiaoweather.util.SharedPreferenceUtil;
import com.example.administrator.qiaoweather.util.ToastUtil;
import com.example.administrator.qiaoweather.util.Util;
import com.example.administrator.qiaoweather.util.Utils;
import com.example.administrator.qiaoweather.widget.adapter.GuideViewPagerAdapter;
import com.squareup.haha.perflib.Main;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class WelcomActivity extends AppCompatActivity implements AMapLocationListener {

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // 隐藏标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // 隐藏状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.guid_view4);
        // String sh1 = Util.sHA1(App.getInstance());
        //Timber.d(sh1);
        rxPermissions = new RxPermissions(this); // where this is an Activity instance

        Observable<Boolean> observable = Observable.create(new ObservableOnSubscribe<Boolean>() {

            @Override
            public void subscribe(final ObservableEmitter<Boolean> e) throws Exception {
                rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        Timber.d(Thread.currentThread().getName());
                        initLocation();

                        e.onNext(aBoolean);
                    }
                });
            }
        });

        observable.observeOn(Schedulers.io())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        Timber.d(Thread.currentThread().getName());

                        Timber.d(aBoolean + "");
                        if (aBoolean) {

                            location();
                        }

                    }
                })
                .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        Timber.d(Thread.currentThread().getName());

                        Timber.d("或者参数");
                        //SharedPreferenceUtil.getInstance().setCityName(Util.replaceCity(aMapLocation.getCity()));
                        String city = SharedPreferenceUtil.getInstance().getCityName();
                        Timber.d(city);
                        ToastUtil.showLong("c" + city);

                        if (aBoolean) {
                        } else {

                        }
                    }
                });

//        Observable.just().
//        rxPermissions
//                .request(Manifest.permission.ACCESS_COARSE_LOCATION)
//                .observeOn(Schedulers.io())
//                .doOnNext(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(@NonNull Boolean aBoolean) throws Exception {
//                        if (aBoolean) {
//                            initLocation();
//
//                            location();
//                        }
//
//                    }
//                })
//                .subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(@NonNull Boolean aBoolean) throws Exception {
//
//                        Timber.d("或者参数");
//                        //SharedPreferenceUtil.getInstance().setCityName(Util.replaceCity(aMapLocation.getCity()));
//                        String city = SharedPreferenceUtil.getInstance().getCityName();
//                        Timber.d(city);
//                        ToastUtil.showLong("c" + city);
//
//                        if (aBoolean) {
//                        } else {
//
//                        }
//                    }
//                });
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

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            String result = Utils.getLocationStr(aMapLocation);
            // Log.i(TAG,"RES"+result);
            ToastUtil.showLong(result);
            Timber.d(result);
            if (aMapLocation.getErrorCode() == 0) {
                aMapLocation.getLocationType();
                Timber.d(Util.replaceCity(aMapLocation.getCity()));
                ToastUtil.showLong(Util.replaceCity(aMapLocation.getCity()));

                // Log.i(TAG, Util.replaceCity(aMapLocation.getCity()));
                SharedPreferenceUtil.getInstance().setCityName(Util.replaceCity(aMapLocation.getCity()));
            } else {
                //  ToastUtil.showLong(getString(R.string.errorLocation));
            }
        } else {
            // ToastUtil.showLong(getString(R.string.errorLocation2));
        }

    }

    private void go() {
        Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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
}
