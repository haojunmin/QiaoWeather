package com.example.administrator.qiaoweather.widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.qiaoweather.R;
import com.example.administrator.qiaoweather.enty.HeFengWeather;
import com.example.administrator.qiaoweather.util.Util;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/27.
 */

public class DailForecastAdapter extends RecyclerView.Adapter<DailForecastAdapter.DailForecastHolder> {


    Context context;
    List<HeFengWeather.HeWeather5Bean.DailyForecastBean> dailyForecastBeanList = new ArrayList<>();

    public List<HeFengWeather.HeWeather5Bean.DailyForecastBean> getDailyForecastBeanList() {
        return dailyForecastBeanList;
    }

    public void setDailyForecastBeanList(List<HeFengWeather.HeWeather5Bean.DailyForecastBean> dailyForecastBeanList) {
        this.dailyForecastBeanList = dailyForecastBeanList;
    }

    @Override
    public DailForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast_line, parent, false);
        return new DailForecastHolder(view);
    }

    @Override
    public void onBindViewHolder(DailForecastHolder holder, int position) {
        HeFengWeather.HeWeather5Bean.DailyForecastBean dailyForecastBean = dailyForecastBeanList.get(position);
        try {
            holder.forecast_date.setText(Util.dayForWeek(dailyForecastBean.getDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.forecast_temp.setText(String.format("%s℃ - %s℃", dailyForecastBean.getTmp().getMin(), dailyForecastBean.getTmp().getMax()));
        holder.forecast_txt.setText(String.format("%s。 %s %s %s km/h。 降水几率 %s%%。"
                , dailyForecastBean.getCond().getTxt_d(), dailyForecastBean.getWind().getSc(),
                dailyForecastBean.getWind().getDir(), dailyForecastBean.getWind().getSpd(), dailyForecastBean.getPop()));
    }

    @Override
    public int getItemCount() {
        Logger.d(dailyForecastBeanList.size());
        return dailyForecastBeanList.size();
    }

    public static class DailForecastHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.forecast_date)
        TextView forecast_date;

        @BindView(R.id.forecast_temp)
        TextView forecast_temp;

        @BindView(R.id.forecast_txt)
        TextView forecast_txt;

        public DailForecastHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
