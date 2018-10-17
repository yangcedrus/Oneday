package com.whut.oneday.weatherUtils;

/**
 * Copyright 2018 bejson.com
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auto-generated: 2018-10-16 23:54:28
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Forecast implements Parcelable {

    private String date;
    private String sunrise;
    private String high;
    private String low;
    private String sunset;
    private int aqi;
    private String fx;
    private String fl;
    private String type;
    private String notice;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getHigh() {
        return high;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getLow() {
        return low;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunset() {
        return sunset;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public int getAqi() {
        return aqi;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    public String getFx() {
        return fx;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getFl() {
        return fl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getNotice() {
        return notice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(date);
        dest.writeString(sunrise);
        dest.writeString(high);
        dest.writeString(low);
        dest.writeString(sunset);
        dest.writeInt(aqi);
        dest.writeString(fx);
        dest.writeString(fl);
        dest.writeString(type);
        dest.writeString(notice);
    }

    private static final Parcelable.Creator<Forecast> CREATOR = new Parcelable.Creator<Forecast>() {
        @Override
        public Forecast createFromParcel(Parcel source) {
            Forecast forecast = new Forecast();
            forecast.date = source.readString();
            forecast.sunrise = source.readString();
            forecast.high = source.readString();
            forecast.low = source.readString();
            forecast.sunset = source.readString();
            forecast.aqi = source.readInt();
            forecast.fx = source.readString();
            forecast.fl = source.readString();
            forecast.type = source.readString();
            forecast.notice = source.readString();
            return forecast;
        }

        @Override
        public Forecast[] newArray(int i) {
            return new Forecast[i];
        }
    };
}