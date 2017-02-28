package com.example.administrator.qiaoweather.widget.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.qiaoweather.R;
import com.example.administrator.qiaoweather.base.BaseHolder;
import com.example.administrator.qiaoweather.base.DefaultAdapter;
import com.example.administrator.qiaoweather.enty.HeWeatherSearch;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/2/28.
 */

public class SearchAdapter extends DefaultAdapter<HeWeatherSearch.HeWeather5Bean> {

    public SearchAdapter(List<HeWeatherSearch.HeWeather5Bean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<HeWeatherSearch.HeWeather5Bean> getHolder(View v, int viewType) {
        return new SearchHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_search;
    }

    class SearchHolder extends BaseHolder<HeWeatherSearch.HeWeather5Bean> {
        @BindView(R.id.city)
        TextView city;

        public SearchHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(HeWeatherSearch.HeWeather5Bean data, int position) {
            city.setText(data.getBasic().getCity());
        }
    }
}
