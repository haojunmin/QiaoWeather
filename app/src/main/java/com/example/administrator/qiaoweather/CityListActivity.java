package com.example.administrator.qiaoweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.administrator.qiaoweather.base.BaseActivity;
import com.example.administrator.qiaoweather.base.DefaultAdapter2;
import com.example.administrator.qiaoweather.enty.City;
import com.example.administrator.qiaoweather.presenter.contact.CityListInterface;
import com.example.administrator.qiaoweather.presenter.CityListPresenter;
import com.example.administrator.qiaoweather.widget.adapter.CityAdapter;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmResults;

/**
 * 获取查询的城市列表
 */
public class CityListActivity extends BaseActivity<CityListPresenter> implements CityListInterface.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Inject
    CityListPresenter cityListPresenter;
    @BindView(R.id.recycview)
    RecyclerView recyclerView;
    @BindView(R.id.fab_add)
    FloatingActionButton fab;
    CityAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("城市列表");

        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    protected void initInject() {
        getActivityComponet().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_city_list;
    }

    @Override
    protected void initEventAndData() {
        cityListPresenter.queryCityData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getCityData(RealmResults<City> cities) {

        cityAdapter = new CityAdapter(cities);
        cityAdapter.setOnItemClickListener(onRecyclerViewItemClickListener);
        recyclerView.setAdapter(cityAdapter);
    }

    DefaultAdapter2.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = new DefaultAdapter2.OnRecyclerViewItemClickListener() {
        @Override
        public void onItemClick(View view, int viewType, Object data, int position) {

            RealmResults<City> realmResults = (RealmResults<City>) data;

            Intent intent = new Intent(CityListActivity.this, MainActivity.class);
            intent.putExtra("city", realmResults.get(position).getName());
            startActivity(intent);
            finish();
        }
    };

    @OnClick(R.id.fab_add)
    public void OnClick() {
        Intent intent = new Intent(CityListActivity.this, AddCityActivity.class);
        startActivity(intent);
    }

    /**
     * 数据清理
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.closeRealm();
        mPresenter.cleanView();
        DefaultAdapter2.releaseAllHolder(recyclerView);
        mPresenter=null;
    }
}
