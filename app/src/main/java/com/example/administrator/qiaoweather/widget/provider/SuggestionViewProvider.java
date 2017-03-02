package com.example.administrator.qiaoweather.widget.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.qiaoweather.R;
import com.example.administrator.qiaoweather.enty.HeFengWeather;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by Administrator on 2017/2/27.
 */
public class SuggestionViewProvider
        extends ItemViewProvider<HeFengWeather.HeWeather5Bean.SuggestionBean, SuggestionViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_suggestion, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HeFengWeather.HeWeather5Bean.SuggestionBean suggestion) {

        Logger.d(suggestion.toString());

        Logger.d(suggestion.getDrsg().toString());
//        Logger.d(suggestion);
        holder.cloth_brief.setText(String.format("穿衣指数--%s", suggestion.getDrsg().getBrf()));
        holder.cloth_txt.setText(suggestion.getDrsg().getTxt());

        holder.sport_brief.setText(String.format("运动指数--%s", suggestion.getSport().getBrf()));
        holder.sport_txt.setText(suggestion.getSport().getTxt());

        holder.travel_brief.setText(String.format("旅游指数--%s", suggestion.getTrav().getBrf()));
        holder.travel_txt.setText(suggestion.getTrav().getTxt());

        holder.flu_brief.setText(String.format("感冒指数--%s", suggestion.getFlu().getBrf()));
        holder.flu_txt.setText(suggestion.getFlu().getTxt());

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cloth_brief)
        TextView cloth_brief;

        @BindView(R.id.cloth_txt)
        TextView cloth_txt;

        @BindView(R.id.sport_brief)
        TextView sport_brief;

        @BindView(R.id.sport_txt)
        TextView sport_txt;

        @BindView(R.id.travel_brief)
        TextView travel_brief;

        @BindView(R.id.travel_txt)
        TextView travel_txt;

        @BindView(R.id.flu_brief)
        TextView flu_brief;
        @BindView(R.id.flu_txt)
        TextView flu_txt;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}