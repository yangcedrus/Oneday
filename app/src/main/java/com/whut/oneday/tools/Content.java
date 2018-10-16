package com.whut.oneday.tools;

import com.whut.oneday.entity.Bill;
import com.whut.oneday.entity.Diary;
import com.whut.oneday.entity.User;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Content {
    public static User localUser=null;
    static {
        localUser=new User();
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

    public static Map<Integer,List<Bill>> billMap=new HashMap<>();

    public static Map<Integer,String> billTypeMap=new HashMap<>();
    static {
        billTypeMap.put(0,"其他");
        billTypeMap.put(1,"餐饮");
        billTypeMap.put(2,"出行");
        billTypeMap.put(3,"住宿");
        billTypeMap.put(4,"购物");
        billTypeMap.put(5,"娱乐");
    }

    public static void changeLocalUserIcon(String newPath){
        localUser.setIconpath(newPath);
    }

    public static Diary tempDiary=new Diary();
    static {
        tempDiary.setUserID(1);
        tempDiary.setDiaryID(1);
        tempDiary.setWeather("晴");
        tempDiary.setMood("开心");
        tempDiary.setCreatestamp(new Timestamp(System.currentTimeMillis()));
        String s="<img src=\"/storage/emulated/0/XRichText/1539680220390-\"/>it's nice to sleep.<img src=\"/storage/emulated/0/XRichText/1539680363764-\"/>Ahhhhhhhhhhhhhhhhhhh!";
        tempDiary.setBody(s);
        tempDiary.setDate("2018/10/16");
    }
}
