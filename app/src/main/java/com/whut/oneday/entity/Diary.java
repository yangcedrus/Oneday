package com.whut.oneday.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

/**
 * @author 22278
 * @version 1.0
 * @created 18-9月-2018 19:06:44
 */
public class Diary implements Parcelable {

    /**
     * 日记ID
     */
    private Integer diaryID;
    /**
     * 所属用户id
     */
    private Integer userID;
    /**
     * 日记主体
     */
    private String body;
    /**
     * 日记创建时间
     */
    private Timestamp createstamp;
    /**
     * 日期
     */
    private String date;
    /**
     * 当日天气
     */
    private String weather;
    /**
     * 当日心情
     */
    private String mood;

    public Diary() {
    }

    public Integer getDiaryID() {
        return diaryID;
    }

    public void setDiaryID(Integer diaryID) {
        this.diaryID = diaryID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getCreatestamp() {
        return createstamp;
    }

    public void setCreatestamp(Timestamp createstamp) {
        this.createstamp = createstamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(diaryID);
        dest.writeInt(userID);
        dest.writeString(body);
        dest.writeString(createstamp.toString());
        dest.writeString(date);
        dest.writeString(weather);
        dest.writeString(mood);
    }

    public static final Parcelable.Creator<Diary> CREATOR = new Parcelable.Creator<Diary>() {

        @Override
        public Diary createFromParcel(Parcel source) {
            Diary diary = new Diary();
            diary.diaryID = source.readInt();
            diary.userID = source.readInt();
            diary.body = source.readString();
            diary.createstamp = Timestamp.valueOf(source.readString());
            diary.date = source.readString();
            diary.weather = source.readString();
            diary.mood = source.readString();
            return diary;
        }

        @Override
        public Diary[] newArray(int size) {
            return new Diary[size];
        }
    };
}