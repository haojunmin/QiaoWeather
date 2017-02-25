package com.example.administrator.qiaoweather.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.autolayout.utils.AutoUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected final String TAG = this.getClass().getSimpleName();
    protected Unbinder unbinder;
    protected OnViewClickListener mOnViewClickListener = null;

    public BaseHolder(View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);
        AutoUtils.autoSize(itemView);//适配
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mOnViewClickListener != null) {
            mOnViewClickListener.onViewClick(view, this.getPosition());
        }
    }

    public interface OnViewClickListener {
        void onViewClick(View view, int position);
    }

    public void setOnItemClickListener(OnViewClickListener listener) {
        this.mOnViewClickListener = listener;
    }

    /**
     * 设置数据
     * 刷新界面
     *
     * @param
     * @param position
     */
    public abstract void setData(T data, int position);


    /**
     * 释放资源
     */
    protected void onRelease() {

    }


}
