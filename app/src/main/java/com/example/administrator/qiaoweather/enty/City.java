package com.example.administrator.qiaoweather.enty;


import io.realm.RealmObject;

/**
 * Created by Administrator on 2017/2/6.
 */

public class City extends RealmObject {

    private String name;

    public City()
    {

    }

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                '}';
    }
}
