package com.whut.oneday.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whut.oneday.R;
import com.whut.oneday.activity.WeatherDetails;
import com.whut.oneday.item.TodayWeather;

public class TodayRecyclerViewAdapter extends  RecyclerView.Adapter<TodayRecyclerViewAdapter.ViewHolder>{

    final static private int WEATHER=0x00000001;
    final static private int HISTORY=0x00000002;
    private Context mcontext;
    private static TodayWeather todayWeather;

    public TodayRecyclerViewAdapter(TodayWeather todayWeather,Context mcontext){
        this.todayWeather=todayWeather;
        this.mcontext=mcontext;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return WEATHER;
        }else {
            return HISTORY;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == WEATHER) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_today_weather, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mcontext, WeatherDetails.class);
                    mcontext.startActivity(intent);
                }
            });
            return holder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.time.setText(todayWeather.getTime());
        holder.location.setText(todayWeather.getLocation());
        holder.temperature.setText(todayWeather.getTemperature());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView location,time,temperature;
        ImageView symbol,weather;
        View view;
        ViewHolder(View view) {
            super(view);
            this.view=view;
            location = (TextView) view.findViewById(R.id.item_today_location);
            time = (TextView) view.findViewById(R.id.item_today_time);
            temperature = (TextView) view.findViewById(R.id.item_today_temperature);
            symbol = (ImageView) view.findViewById(R.id.item_today_symbol);
            weather= (ImageView) view.findViewById(R.id.item_today_weather);
        }
    }
}
