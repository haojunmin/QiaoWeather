package com.example.administrator.qiaoweather;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.qiaoweather.di.componet.ActivityComponet;
import com.example.administrator.qiaoweather.di.componet.DaggerActivityComponet;
import com.example.administrator.qiaoweather.di.module.ActivityModule;
import com.example.administrator.qiaoweather.imageloader.ImageLoader;
import com.example.administrator.qiaoweather.imageloader.glide.GlideImageConfig;
import com.orhanobut.logger.Logger;
import com.zhy.autolayout.AutoLayoutActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 关于界面
 */
public class AboutActivity extends AutoLayoutActivity {

    Unbinder unbinder;


    @Inject
    ImageLoader imageLoader;

    @BindView(R.id.bt_code)
    Button button;

    String url = "http://img2.imgtn.bdimg.com/it/u=4074655655,3913363897&fm=21&gp=0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        setTitle("关于");

        unbinder = ButterKnife.bind(this);
        getActivityComponet().inject(this);
    }


    protected ActivityComponet getActivityComponet() {

        return DaggerActivityComponet.builder()
                .appComponet(App.getmAppComponet())
                .activityModule(getActivityModule()).build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.bt_code)
    public void goCode() {
        goToHtml(getString(R.string.app_html));
    }

    private void goToHtml(String url) {
        Uri uri = Uri.parse(url);   //指定网址
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);           //指定Action
        intent.setData(uri);                            //设置Uri
        startActivity(intent);        //启动Activity
    }
}
