package com.example.administrator.qiaoweather;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.administrator.qiaoweather.base.BaseActivity;
import com.example.administrator.qiaoweather.enty.BaseWeatherInfo;
import com.example.administrator.qiaoweather.enty.City;
import com.example.administrator.qiaoweather.enty.DailyForceastList;
import com.example.administrator.qiaoweather.enty.HeFengWeather;
import com.example.administrator.qiaoweather.enty.HeWeatherSearch;
import com.example.administrator.qiaoweather.enty.HourlyForecastBeanList;
import com.example.administrator.qiaoweather.presenter.MainPresenter;
import com.example.administrator.qiaoweather.presenter.contact.MainInterface;
import com.example.administrator.qiaoweather.util.SharedPreferenceUtil;
import com.example.administrator.qiaoweather.util.ToastUtil;
import com.example.administrator.qiaoweather.util.Util;
import com.example.administrator.qiaoweather.widget.provider.ForceastProvider;
import com.example.administrator.qiaoweather.widget.provider.HourInfoViewProvider;
import com.example.administrator.qiaoweather.widget.provider.SuggestionViewProvider;
import com.example.administrator.qiaoweather.widget.provider.TemperatureProviderViewProvider;
import com.orhanobut.logger.Logger;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends BaseActivity<MainPresenter> implements MainInterface.View, OnMenuItemClickListener {

    MaterialDialog materialDialog;

    private ContextMenuDialogFragment mMenuDialogFragment;
    private MultiTypeAdapter multiTypeAdapter;
    @BindView(R.id.recylerview)
    RecyclerView recyclerView;
    @BindView(R.id.swipelayout)
    SwipeRefreshLayout refreshLayout;
    @Inject
    MainPresenter mainPresenter;
    ArrayList list = new ArrayList();
    FragmentManager fragmentManager;
    String getCity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("瞧天气");

        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCity = SharedPreferenceUtil.getInstance().getCityName();
                if (TextUtils.isEmpty(getCity)) {
                    mainPresenter.startLocation();
                } else {
                    mPresenter.weather(getCity);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        multiTypeAdapter = new MultiTypeAdapter(list);
        recyclerView.setAdapter(multiTypeAdapter);

        mainPresenter.startLocation();
        fragmentManager = getSupportFragmentManager();
        initMenuFragment();


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getCity = intent.getStringExtra("city");
        SharedPreferenceUtil.getInstance().setCityName(getCity);
        setTitle(getCity);
        mPresenter.weather(getCity);
        Logger.d("onNewIntent");
    }


    @Override
    protected void initInject() {
        getActivityComponet().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        Logger.d("init");

    }

    @Override
    public void startLocation() {

    }

    @Override
    public void LocationSucess() {
    }

    @Override
    public void LocationFail() {
        ToastUtil.showLong("获取当前位置失败，请设定城市");
    }

    @Override
    public void setViewTitle(String title) {
        setTitle(title);
    }

    BaseWeatherInfo baseWeatherInfo;
    HourlyForecastBeanList hourlyForecastBeanList;
    HeFengWeather.HeWeather5Bean.SuggestionBean suggestionBean;
    DailyForceastList dailyForceastList;

    @Override
    public void getWeather(HeFengWeather heFengWeather) {
        refreshLayout.setRefreshing(false);
        list.clear();
        multiTypeAdapter.notifyDataSetChanged();
        HeFengWeather.HeWeather5Bean heWeather5Bean = heFengWeather.getHeWeather5().get(0);

        //Aqi这个类可能会为空
        if (heWeather5Bean.getAqi() == null) {
            baseWeatherInfo = new BaseWeatherInfo(heWeather5Bean.getDaily_forecast().get(0), heWeather5Bean.getNow());

        } else {
            baseWeatherInfo = new BaseWeatherInfo(heWeather5Bean.getDaily_forecast().get(0), heWeather5Bean.getAqi().getCity(), heWeather5Bean.getNow());
        }

        list.add(baseWeatherInfo);
        multiTypeAdapter.register(BaseWeatherInfo.class, new TemperatureProviderViewProvider());
        multiTypeAdapter.notifyDataSetChanged();

        hourlyForecastBeanList = new HourlyForecastBeanList(heWeather5Bean.getHourly_forecast());
        list.add(hourlyForecastBeanList);
        multiTypeAdapter.register(HourlyForecastBeanList.class, new HourInfoViewProvider());
        multiTypeAdapter.notifyDataSetChanged();

        suggestionBean = heWeather5Bean.getSuggestion();
        list.add(suggestionBean);
        multiTypeAdapter.register(HeFengWeather.HeWeather5Bean.SuggestionBean.class, new SuggestionViewProvider());
        multiTypeAdapter.notifyDataSetChanged();

        dailyForceastList = new DailyForceastList(heWeather5Bean.getDaily_forecast());
        list.add(dailyForceastList);
        multiTypeAdapter.register(DailyForceastList.class, new ForceastProvider());
        multiTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getWeatherFailed() {
        ToastUtil.showLong("获取天气失败，请稍后再试");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("onDestroy");
        mainPresenter.stopLocation();
        mainPresenter.cleanView();
        mainPresenter = null;
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);

    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);


        MenuObject addFr = new MenuObject("添加城市");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        addFr.setDrawable(bd);

        MenuObject send = new MenuObject("关于");
        send.setResource(R.drawable.icn_1);


        menuObjects.add(close);
        menuObjects.add(addFr);
        menuObjects.add(send);
        return menuObjects;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Intent intent;
        switch (position) {
            case 0:
                mMenuDialogFragment.dismiss();
                break;
            case 1:
                intent = new Intent(MainActivity.this, CityListActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
        }
    }
}
