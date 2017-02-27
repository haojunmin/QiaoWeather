package com.example.administrator.qiaoweather.widget.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.administrator.qiaoweather.R;
import com.example.administrator.qiaoweather.enty.BaseWeatherInfo;
import com.example.administrator.qiaoweather.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by Administrator on 2017/2/24.
 */
public class TemperatureProviderViewProvider
        extends ItemViewProvider<BaseWeatherInfo, TemperatureProviderViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_temperature, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull BaseWeatherInfo baseWeatherInfo) {
        holder.temp_flu.setText(baseWeatherInfo.getNowBean().getTmp());
        holder.temp_max.setText(String.format("↑ %s ℃", baseWeatherInfo.getDailyForecastBean().getTmp().getMax()));
        holder.temp_min.setText(String.format("↑ %s ℃", baseWeatherInfo.getDailyForecastBean().getTmp().getMin()));
        holder.temp_pm.setText(String.format("PM2.5: %s μg/m³", Util.safeText(baseWeatherInfo.getCityBean().getPm25())));
        holder.temp_quality.setText(String.format("↑ %s ℃", baseWeatherInfo.getCityBean().getQlty()));

    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.temp_quality)
        TextView temp_quality;

        @BindView(R.id.temp_max)
        TextView temp_max;
        @BindView(R.id.temp_min)
        TextView temp_min;
        @BindView(R.id.temp_pm)
        TextView temp_pm;

        @BindView(R.id.temp_flu)
        TextView temp_flu;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}