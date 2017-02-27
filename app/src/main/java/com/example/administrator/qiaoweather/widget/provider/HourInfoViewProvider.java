package com.example.administrator.qiaoweather.widget.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.qiaoweather.R;
import com.example.administrator.qiaoweather.enty.HeFengWeather;
import com.example.administrator.qiaoweather.enty.HourlyForecastBeanList;
import com.example.administrator.qiaoweather.widget.adapter.HourinfoAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by Administrator on 2017/2/24.
 */
public class HourInfoViewProvider
        extends ItemViewProvider<HourlyForecastBeanList, HourInfoViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_recycview, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HourlyForecastBeanList heWeather5Bean) {
        holder.setData(heWeather5Bean.getHourly_forecast());
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recycview)
        RecyclerView recycview;
        HourinfoAdapter adapter;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            adapter = new HourinfoAdapter();
            recycview.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            recycview.setAdapter(adapter);
        }

        public void setData(List<HeFengWeather.HeWeather5Bean.HourlyForecastBean> hourlyForecastBeanArrayList) {
            adapter.setHourlyForecastBeens(hourlyForecastBeanArrayList);
            adapter.notifyDataSetChanged();
        }
    }
}