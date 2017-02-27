package com.example.administrator.qiaoweather.enty;

import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */

public class DailyForceastList {
    private List<HeFengWeather.HeWeather5Bean.DailyForecastBean> dailyForecastBeanList;

    public DailyForceastList(List<HeFengWeather.HeWeather5Bean.DailyForecastBean> dailyForecastBeanList) {
        this.dailyForecastBeanList = dailyForecastBeanList;
    }

    public List<HeFengWeather.HeWeather5Bean.DailyForecastBean> getDailyForecastBeanList() {
        return dailyForecastBeanList;
    }

    public void setDailyForecastBeanList(List<HeFengWeather.HeWeather5Bean.DailyForecastBean> dailyForecastBeanList) {
        this.dailyForecastBeanList = dailyForecastBeanList;
    }
}
