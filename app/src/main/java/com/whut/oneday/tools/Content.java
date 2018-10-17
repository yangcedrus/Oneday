package com.whut.oneday.tools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.whut.oneday.entity.Bill;
import com.whut.oneday.entity.Diary;
import com.whut.oneday.entity.User;
import com.whut.oneday.weatherUtils.WeatherMsg;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//一些全局变量
public class Content {
    public static User localUser = null;

    static {
        localUser = new User();
        localUser.setUserID(1);
        localUser.setUsername("admin");
        localUser.setPassword("admin");
        localUser.setPhone("18360101257");
        localUser.setCreatestamp(new Timestamp(System.currentTimeMillis()));
        localUser.setLaststamp(new Timestamp(System.currentTimeMillis()));
        localUser.setQq("2227862581");
        localUser.setStatus(1);
        localUser.setIconpath("https://gss0.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/3b87e950352ac65cbc92e3f3fff2b21192138a9f.jpg");
    }

    public static Map<Integer, List<Bill>> billMap = new HashMap<>();

    public static Map<Integer, String> billTypeMap = new HashMap<>();

    static {
        billTypeMap.put(0, "其他");
        billTypeMap.put(1, "餐饮");
        billTypeMap.put(2, "出行");
        billTypeMap.put(3, "住宿");
        billTypeMap.put(4, "购物");
        billTypeMap.put(5, "娱乐");
    }

    public static void changeLocalUserIcon(String newPath) {
        localUser.setIconpath(newPath);
    }

    public static Diary tempDiary;

    static {
        tempDiary = new Diary();
        tempDiary.setUserID(1);
        tempDiary.setDiaryID(1);
        tempDiary.setWeather("晴");
        tempDiary.setMood("开心");
        tempDiary.setCreatestamp(new Timestamp(System.currentTimeMillis()));
        String s = "<img src=\"/storage/emulated/0/XRichText/1539680220390-\"/>it's nice to sleep.<img src=\"/storage/emulated/0/XRichText/1539680363764-\"/>Ahhhhhhhhhhhhhhhhhhh!";
        tempDiary.setBody(s);
        tempDiary.setDate("2018/10/16");
    }

    public static Map<String,WeatherMsg> localWeatherMsg;
    static {
        localWeatherMsg=new HashMap<>();
        String msg = "{\"time\":\"2018-10-16 16:13:17\",\"cityInfo\":{\"city\":\"天津市\",\"cityId\":\"101030100\",\"parent\":\"天津\",\"updateTime\":\"15:52\"},\"date\":\"20181016\",\"message\":\"Success !\",\"status\":200,\"data\":{\"shidu\":\"75%\",\"pm25\":53.0,\"pm10\":68.0,\"quality\":\"良\",\"wendu\":\"13\",\"ganmao\":\"极少数敏感人群应减少户外活动\",\"yesterday\":{\"date\":\"15日星期一\",\"sunrise\":\"06:19\",\"high\":\"高温 20.0℃\",\"low\":\"低温 10.0℃\",\"sunset\":\"17:34\",\"aqi\":145.0,\"fx\":\"西南风\",\"fl\":\"<3级\",\"type\":\"阴\",\"notice\":\"不要被阴云遮挡住好心情\"},\"forecast\":[{\"date\":\"16日星期二\",\"sunrise\":\"06:20\",\"high\":\"高温 15.0℃\",\"low\":\"低温 9.0℃\",\"sunset\":\"17:33\",\"aqi\":87.0,\"fx\":\"东北风\",\"fl\":\"3-4级\",\"type\":\"小雨\",\"notice\":\"雨虽小，注意保暖别感冒\"},{\"date\":\"17日星期三\",\"sunrise\":\"06:21\",\"high\":\"高温 19.0℃\",\"low\":\"低温 6.0℃\",\"sunset\":\"17:31\",\"aqi\":72.0,\"fx\":\"东南风\",\"fl\":\"<3级\",\"type\":\"晴\",\"notice\":\"愿你拥有比阳光明媚的心情\"},{\"date\":\"18日星期四\",\"sunrise\":\"06:22\",\"high\":\"高温 20.0℃\",\"low\":\"低温 9.0℃\",\"sunset\":\"17:30\",\"aqi\":105.0,\"fx\":\"南风\",\"fl\":\"<3级\",\"type\":\"晴\",\"notice\":\"愿你拥有比阳光明媚的心情\"},{\"date\":\"19日星期五\",\"sunrise\":\"06:23\",\"high\":\"高温 19.0℃\",\"low\":\"低温 11.0℃\",\"sunset\":\"17:29\",\"aqi\":140.0,\"fx\":\"西南风\",\"fl\":\"<3级\",\"type\":\"多云\",\"notice\":\"阴晴之间，谨防紫外线侵扰\"},{\"date\":\"20日星期六\",\"sunrise\":\"06:24\",\"high\":\"高温 20.0℃\",\"low\":\"低温 12.0℃\",\"sunset\":\"17:27\",\"aqi\":125.0,\"fx\":\"西南风\",\"fl\":\"<3级\",\"type\":\"阴\",\"notice\":\"不要被阴云遮挡住好心情\"}]}}";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        localWeatherMsg.put("天津市",gson.fromJson(msg,WeatherMsg.class));
    }
}
