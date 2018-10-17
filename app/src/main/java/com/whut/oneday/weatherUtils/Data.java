package com.whut.oneday.weatherUtils;

/**
 * Copyright 2018 bejson.com
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Auto-generated: 2018-10-16 23:54:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data implements Parcelable {

    private String shidu;
    private Double pm25;
    private Double pm10;
    private String quality;
    private String wendu;
    private String ganmao;
    private Forecast yesterday;
    private List<Forecast> forecast;

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getShidu() {
        return shidu;
    }

    public void setPm25(Double pm25) {
        this.pm25 = pm25;
    }

    public Double getPm25() {
        return pm25;
    }

    public void setPm10(Double pm10) {
        this.pm10 = pm10;
    }

    public Double getPm10() {
        return pm10;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getQuality() {
        return quality;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getWendu() {
        return wendu;
    }

    public void setGanmao(String ganmao) {
        this.ganmao = ganmao;
    }

    public String getGanmao() {
        return ganmao;
    }

    public void setYesterday(Forecast yesterday) {
        this.yesterday = yesterday;
    }

    public Forecast getYesterday() {
        return yesterday;
    }

    public void setForecast(List<Forecast> forecast) {
        this.forecast = forecast;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(shidu);
        dest.writeDouble(pm25);
        dest.writeDouble(pm10);
        dest.writeString(quality);
        dest.writeString(wendu);
        dest.writeString(ganmao);
        dest.writeParcelable(yesterday, i);
        dest.writeList(forecast);
    }

    private final static Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            Data data = new Data();
            data.shidu = source.readString();
            data.pm25 = source.readDouble();
            data.pm10 = source.readDouble();
            data.quality = source.readString();
            data.wendu = source.readString();
            data.ganmao = source.readString();
            data.yesterday = source.readParcelable(Forecast.class.getClassLoader());
            if (data.forecast == null)
                data.forecast = new ArrayList<>();
            source.readList(data.forecast, Forecast.class.getClassLoader());
            return data;
        }

        @Override
        public Data[] newArray(int i) {
            return new Data[i];
        }
    };

}