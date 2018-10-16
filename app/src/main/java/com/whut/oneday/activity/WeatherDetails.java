package com.whut.oneday.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.whut.oneday.BaseActivity;
import com.whut.oneday.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WeatherDetails extends BaseActivity {

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
    @InjectView(R.id.weather_details_weather1_min)
    TextView weatherDetailsWeather1Min;
    @InjectView(R.id.weather_details_weather2_max)
    TextView weatherDetailsWeather2Max;
    @InjectView(R.id.weather_details_weather2_min)
    TextView weatherDetailsWeather2Min;
    @InjectView(R.id.weather_details_weather3_max)
    TextView weatherDetailsWeather3Max;
    @InjectView(R.id.weather_details_weather3_min)
    TextView weatherDetailsWeather3Min;
    @InjectView(R.id.weather_details_weather4_max)
    TextView weatherDetailsWeather4Max;
    @InjectView(R.id.weather_details_weather4_min)
    TextView weatherDetailsWeather4Min;
    @InjectView(R.id.weather_details_weather_channel)
    ImageView weatherDetailsWeatherChannel;
    @InjectView(R.id.weather_details_weather_list)
    ImageView weatherDetailsWeatherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        ButterKnife.inject(this);

        hideStatus();
    }
}
