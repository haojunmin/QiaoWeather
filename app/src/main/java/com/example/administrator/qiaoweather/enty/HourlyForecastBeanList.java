package com.example.administrator.qiaoweather.enty;

import java.util.List;

/**
 * Created by Administrator on 2017/2/24.
 */

public class HourlyForecastBeanList {
    public void setHourly_forecast(List<HeFengWeather.HeWeather5Bean.HourlyForecastBean> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    private List<HeFengWeather.HeWeather5Bean.HourlyForecastBean> hourly_forecast;

    public List<HeFengWeather.HeWeather5Bean.HourlyForecastBean> getHourly_forecast() {
        return hourly_forecast;
    }

    public HourlyForecastBeanList(List<HeFengWeather.HeWeather5Bean.HourlyForecastBean> hourly_forecast) {
        this.hourly_forecast = hourly_forecast;
    }

    public HourlyForecastBeanList() {

    }
}
