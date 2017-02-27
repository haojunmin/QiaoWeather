package com.example.administrator.qiaoweather.enty;

/**
 * Created by Administrator on 2017/2/26.
 */


public class LocationCity {
    String city;

    int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LocationCity() {
    }

    public LocationCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "LocationCity{" +
                "city='" + city + '\'' +
                ", code=" + code +
                '}';
    }
}
