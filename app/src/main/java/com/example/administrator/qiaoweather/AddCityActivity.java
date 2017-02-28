package com.example.administrator.qiaoweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.administrator.qiaoweather.base.BaseActivity;
import com.example.administrator.qiaoweather.base.DefaultAdapter;
import com.example.administrator.qiaoweather.enty.HeWeatherSearch;
import com.example.administrator.qiaoweather.presenter.AddCityPresenter;
import com.example.administrator.qiaoweather.presenter.contact.AddCityInterface;
import com.example.administrator.qiaoweather.widget.adapter.SearchAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AddCityActivity extends BaseActivity<AddCityPresenter> implements AddCityInterface.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.searchview)
    SearchView searchView;
    @BindView(R.id.recycview)
    RecyclerView recyclerView;
    SearchAdapter searchAdapter;

    List<HeWeatherSearch.HeWeather5Bean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("添加城市");
        toolbar.setNavigationIcon(R.drawable.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // 将搜索按钮放到搜索输入框的外边
        searchView.setIconifiedByDefault(true);

        // 设置输入框底部的横线的颜色
        View searchPlate = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        searchPlate.setBackgroundResource(R.mipmap.ic_searchview_plate);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchAdapter = new SearchAdapter(list);
        searchAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                Logger.d(data);
                if (data instanceof HeWeatherSearch.HeWeather5Bean) {
                    HeWeatherSearch.HeWeather5Bean heWeather5Bean = (HeWeatherSearch.HeWeather5Bean) data;
                    mPresenter.save(heWeather5Bean);
                }
            }
        });

        recyclerView.setAdapter(searchAdapter);

    }

    @Override
    protected void initInject() {
        getActivityComponet().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_city;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void showResult(HeWeatherSearch heWeatherSearch) {

        searchAdapter.setmInfos(heWeatherSearch.getHeWeather5());
    }

    @Override
    public void saveSucess(HeWeatherSearch.HeWeather5Bean heWeather5Bean) {
        Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
        intent.putExtra("city", heWeather5Bean.getBasic().getCity());
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.closeRealm();
        DefaultAdapter.releaseAllHolder(recyclerView);
    }
}
