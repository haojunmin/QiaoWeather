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
import com.orhanobut.logger.Logger;
import com.zhy.autolayout.AutoLayoutActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/2/24.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AutoLayoutActivity implements BaseView {

    @Inject
    protected T mPresenter;
    protected Activity mContext;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        initInject();
        if (mPresenter != null) {
            Logger.d("oncreate");
            // mPresenter.attachView(this);
        }
        initEventAndData();
    }


    protected ActivityComponet getActivityComponet() {
        return DaggerActivityComponet.builder()
                .appComponet(App.getAppComponet())
                .activityModule(getActivityModule()).build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mUnbinder.unbind();

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
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void showError(String msg) {

    }

    protected abstract void initInject();

    protected abstract int getLayout();

    protected abstract void initEventAndData();

}
