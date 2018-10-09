package com.whut.oneday.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共类，用于返回json对象
 *
 * @author Administrator
 *
 */
public class Msg {

    //  状态码
    private String status;

    private String message;

    //用于封装数据
    private Map<String, Object> data = new HashMap<String, Object>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    //当处理成功时
    public static Msg success() {
        Msg result = new Msg();
        result.setStatus("success");
        result.setMessage("处理成功");
        return result;
    }

    //当处理成功时，可附带message
    public static Msg success(String message) {
        Msg result = new Msg();
        result.setStatus("success");
        result.setMessage(message);
        return result;
    }

    //当处理失败时
    public static Msg error() {
        Msg result = new Msg();
        result.setStatus("error");
        result.setMessage("处理失败");
        return result;
    }

    //当处理失败时，可附带message
    public static Msg error(String message) {
        Msg result = new Msg();
        result.setStatus("error");
        result.setMessage(message);
        return result;
    }

    //用于添加封装的数据，实现链式编程
    public Msg add(String key, Object value) {
        this.getData().put(key, value);
        return this;
    }

}