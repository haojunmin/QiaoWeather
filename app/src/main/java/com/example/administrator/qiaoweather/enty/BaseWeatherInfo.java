package com.example.administrator.qiaoweather.enty;

/**
 * Created by Administrator on 2017/2/24.
 */

public class BaseWeatherInfo {
    HeFengWeather.HeWeather5Bean.DailyForecastBean dailyForecastBean;
    HeFengWeather.HeWeather5Bean.AqiBean.CityBean cityBean;
    HeFengWeather.HeWeather5Bean.NowBean nowBean;

    public BaseWeatherInfo(HeFengWeather.HeWeather5Bean.DailyForecastBean dailyForecastBean, HeFengWeather.HeWeather5Bean.AqiBean.CityBean cityBean, HeFengWeather.HeWeather5Bean.NowBean nowBean) {
        this.dailyForecastBean = dailyForecastBean;
        this.cityBean = cityBean;
        this.nowBean = nowBean;
    }

    public BaseWeatherInfo(HeFengWeather.HeWeather5Bean.DailyForecastBean dailyForecastBean, HeFengWeather.HeWeather5Bean.NowBean nowBean) {
        this.dailyForecastBean = dailyForecastBean;
        this.nowBean = nowBean;
    }

    public BaseWeatherInfo(HeFengWeather.HeWeather5Bean.DailyForecastBean dailyForecastBean, HeFengWeather.HeWeather5Bean.AqiBean.CityBean cityBean) {
        this.dailyForecastBean = dailyForecastBean;
        this.cityBean = cityBean;
    }

    public HeFengWeather.HeWeather5Bean.DailyForecastBean getDailyForecastBean() {
        return dailyForecastBean;
    }

    public void setDailyForecastBean(HeFengWeather.HeWeather5Bean.DailyForecastBean dailyForecastBean) {
        this.dailyForecastBean = dailyForecastBean;
    }

    public HeFengWeather.HeWeather5Bean.AqiBean.CityBean getCityBean() {
        return cityBean;
    }

    public void setCityBean(HeFengWeather.HeWeather5Bean.AqiBean.CityBean cityBean) {
        this.cityBean = cityBean;
    }

    public HeFengWeather.HeWeather5Bean.NowBean getNowBean() {
        return nowBean;
    }

    public void setNowBean(HeFengWeather.HeWeather5Bean.NowBean nowBean) {
        this.nowBean = nowBean;
    }
}
