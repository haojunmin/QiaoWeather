package com.example.administrator.qiaoweather.util;

import android.widget.Toast;

import com.example.administrator.qiaoweather.App;




public class ToastUtil {

    public static void showShort(String msg) {
        Toast.makeText(App.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String msg) {
        Toast.makeText(App.getInstance(), msg, Toast.LENGTH_LONG).show();
    }
}
