package com.example.administrator.qiaoweather.widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.qiaoweather.R;
import com.example.administrator.qiaoweather.enty.HeFengWeather;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/24.
 */

public class HourinfoAdapter extends RecyclerView.Adapter<HourinfoAdapter.HourViewHolder> {

    Context context;
    List<HeFengWeather.HeWeather5Bean.HourlyForecastBean> hourlyForecastBeens = new ArrayList<>();


    public HourinfoAdapter() {

    }

    public void setHourlyForecastBeens(List<HeFengWeather.HeWeather5Bean.HourlyForecastBean> hourlyForecastBeens) {
        this.hourlyForecastBeens = hourlyForecastBeens;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour_info_line, parent, false);

        return new HourViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        HeFengWeather.HeWeather5Bean.HourlyForecastBean hourlyForecastBean = hourlyForecastBeens.get(position);

        String date = hourlyForecastBean.getDate();


        holder.one_clock.setText(date.substring(date.length() - 5, date.length()));
        holder.one_temp.setText(String.format("%s℃", hourlyForecastBean.getTmp()));
        holder.one_humidity.setText(String.format("%s%%", hourlyForecastBean.getHum()));
        holder.one_wind.setText(String.format("%sKm/h", hourlyForecastBean.getWind().getSpd()));
    }

    @Override
    public int getItemCount() {
        return hourlyForecastBeens.size();
    }

    public static class HourViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.one_clock)
        TextView one_clock;

        @BindView(R.id.one_temp)
        TextView one_temp;
        @BindView(R.id.one_humidity)
        TextView one_humidity;
        @BindView(R.id.one_wind)
        TextView one_wind;

        public HourViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
