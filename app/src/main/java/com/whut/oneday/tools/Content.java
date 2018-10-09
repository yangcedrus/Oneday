package com.whut.oneday.tools;

import com.whut.oneday.entity.Bill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Content {
    public static final Map<Integer,List<Bill>> billMap=new HashMap<>();

    public static final Map<Integer,String> billTypeMap=new HashMap<>();
    static {
        billTypeMap.put(0,"其他");
        billTypeMap.put(1,"餐饮");
        billTypeMap.put(2,"出行");
        billTypeMap.put(3,"住宿");
        billTypeMap.put(4,"购物");
        billTypeMap.put(5,"娱乐");
    }
}
