package com.whut.oneday.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.whut.oneday.R;
import com.whut.oneday.weatherUtils.Data;
import com.whut.oneday.weatherUtils.Forecast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForecastFragment extends Fragment {

    @InjectView(R.id.weather_details_location)
    TextView weatherDetailsLocation;
    @InjectView(R.id.weather_details_weather)
    TextView weatherDetailsWeather;
    @InjectView(R.id.weather_details_temperature)
    TextView weatherDetailsTemperature;
    @InjectView(R.id.weather_details_day1)
    TextView weatherDetailsDay1;
    @InjectView(R.id.weather_details_day2)
    TextView weatherDetailsDay2;
    @InjectView(R.id.weather_details_day3)
    TextView weatherDetailsDay3;
    @InjectView(R.id.weather_details_day4)
    TextView weatherDetailsDay4;
    @InjectView(R.id.weather_details_weather1)
    ImageView weatherDetailsWeather1;
    @InjectView(R.id.weather_details_weather2)
    ImageView weatherDetailsWeather2;
    @InjectView(R.id.weather_details_weather3)
    ImageView weatherDetailsWeather3;
    @InjectView(R.id.weather_details_weather4)
    ImageView weatherDetailsWeather4;
    @InjectView(R.id.weather_details_weather1_max)
    TextView weatherDetailsWeather1Max;
    @InjectView(R.id.weather_details_weather2_max)
    TextView weatherDetailsWeather2Max;
    @InjectView(R.id.weather_details_weather3_max)
    TextView weatherDetailsWeather3Max;
    @InjectView(R.id.weather_details_weather4_max)
    TextView weatherDetailsWeather4Max;
    @InjectView(R.id.weather_details_quality)
    TextView weatherDetailsQuality;
    @InjectView(R.id.weather_details_pm25)
    TextView weatherDetailsPm25;
    @InjectView(R.id.weather_details_notice)
    TextView weatherDetailsNotice;
    @InjectView(R.id.weather_details_min)
    TextView weatherDetailsMin;

    private List<TextView> datelist;
    private List<ImageView> imagelist;
    private List<TextView> maxlist;
    private String city;
    private Data data;

    public static ForecastFragment newInstance(Data data, String city) {
        ForecastFragment frag = new ForecastFragment();
        Bundle args = new Bundle();
        args.putParcelable("data", data);
        args.putString("city", city);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast_weather, container, false);
        ButterKnife.inject(this, view);

        initView();
        initData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView() {
        //分配好view
        datelist = new ArrayList<>();
        datelist.add(weatherDetailsDay1);
        datelist.add(weatherDetailsDay2);
        datelist.add(weatherDetailsDay3);
        datelist.add(weatherDetailsDay4);

        imagelist = new ArrayList<>();
        imagelist.add(weatherDetailsWeather1);
        imagelist.add(weatherDetailsWeather2);
        imagelist.add(weatherDetailsWeather3);
        imagelist.add(weatherDetailsWeather4);

        maxlist = new ArrayList<>();
        maxlist.add(weatherDetailsWeather1Max);
        maxlist.add(weatherDetailsWeather2Max);
        maxlist.add(weatherDetailsWeather3Max);
        maxlist.add(weatherDetailsWeather4Max);
    }

    //初始化页面数据
    private void initData() {
        if (getArguments() != null) {
            data = getArguments().getParcelable("data");
            city = getArguments().getString("city");
        } else
            return;

        //填充数据
        Forecast forecast = data.getForecast().get(0);
        weatherDetailsLocation.setText(city);
        weatherDetailsWeather.setText(forecast.getType());
        weatherDetailsQuality.setText("空气  " + data.getQuality());
        weatherDetailsTemperature.setText(data.getWendu());
        weatherDetailsPm25.setText("PM2.5  " + Double.toString(data.getPm25()));
        weatherDetailsMin.setText(forecast.getHigh() + "/" + forecast.getLow());
        weatherDetailsNotice.setText(forecast.getNotice());
        // FIXME: 2018/10/17 今天的最高气温以及最低气温,以及图片更换
        for (int i = 0; i < 4; i++) {
            forecast = data.getForecast().get(i + 1);
            datelist.get(i).setText(forecast.getDate());
            imagelist.get(i).setImageResource(R.drawable.ic_eye_grey);
            maxlist.get(i).setText(forecast.getHigh().substring(3) + "/" + forecast.getLow().substring(3));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
