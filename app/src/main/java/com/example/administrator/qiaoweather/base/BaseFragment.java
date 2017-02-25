package com.example.administrator.qiaoweather.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.qiaoweather.di.componet.DaggerFragmentComponet;
import com.example.administrator.qiaoweather.di.componet.FragmentComponet;
import com.example.administrator.qiaoweather.di.module.FragmentModule;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/2/25.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements BaseView {

    protected T mPresenter;
    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        mContext = context;
    }

    protected FragmentComponet getFragmentComponet() {
        return DaggerFragmentComponet.builder().fragmentModule(new FragmentModule(this)).build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayoutId(), null);
        initInject();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
        mUnbinder = ButterKnife.bind(this, view);
        initEventAndData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mView = null;
    }

    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void initEventAndData();
}
