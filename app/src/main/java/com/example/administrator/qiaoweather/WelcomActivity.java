package com.example.administrator.qiaoweather;



import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;


import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class WelcomActivity extends AppCompatActivity {


    @BindView(R.id.btn_login)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.guid_view4);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_login)
    public void go() {
        Intent intent = new Intent(WelcomActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
