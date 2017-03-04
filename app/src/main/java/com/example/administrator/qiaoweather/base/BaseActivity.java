package com.example.administrator.qiaoweather.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.example.administrator.qiaoweather.App;
import com.example.administrator.qiaoweather.di.componet.ActivityComponet;
import com.example.administrator.qiaoweather.di.componet.DaggerActivityComponet;
import com.example.administrator.qiaoweather.di.module.ActivityModule;
import com.example.administrator.qiaoweather.util.SMFrameCallback;
import com.orhanobut.logger.Logger;
import com.zhy.autolayout.AutoLayoutActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/2/24.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AutoLayoutActivity {

    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
      //  SMFrameCallback.getInstance().start();
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        initInject();

        initEventAndData();
      //  ViewServer.get(this).addWindow(this);
    }


    protected ActivityComponet getActivityComponet() {

        return DaggerActivityComponet.builder()
                .appComponet(App.getmAppComponet())
                .activityModule(getActivityModule()).build();
    }

    protected void onResume() {
        super.onResume();
        //ViewServer.get(this).setFocusedWindow(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mUnbinder.unbind();
        //ViewServer.get(this).removeWindow(this);
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected void setToolBar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }





    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAndData();

}
