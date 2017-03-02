package com.example.administrator.qiaoweather.widget.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.qiaoweather.R;
import com.example.administrator.qiaoweather.base.BaseHolder;
import com.example.administrator.qiaoweather.base.DefaultAdapter;
import com.example.administrator.qiaoweather.base.DefaultAdapter2;
import com.example.administrator.qiaoweather.enty.City;

import java.util.List;

import butterknife.BindView;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2017/2/28.
 */

public class CityAdapter extends DefaultAdapter2<RealmResults<City>> {

    public CityAdapter(RealmResults<City> infos) {
        super(infos);
    }

    public CityAdapter() {

    }

    @Override
    public BaseHolder<RealmResults<City>> getHolder(View v, int viewType) {
        return new CityHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_city;
    }

    @Override
    public int getItemCount() {
        if (t != null) {
            return t.size();
        }
        return 0;
    }


    public static class CityHolder extends BaseHolder<RealmResults<City>> {
        @BindView(R.id.city_name)
        TextView city_name;

        public CityHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(RealmResults<City> data, int position) {
            city_name.setText(data.get(position).getName());
        }

    }
}
