package com.whut.oneday.entity;

import java.sql.Timestamp;

/**
 * @author 22278
 * @version 1.0
 * @created 18-9月-2018 19:07:13
 */
public class User {

    /**
     * 用户ID
     */
    private Integer userID;
    /**
     * 用户昵称
     */
    private String username;
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户qq号码
     */
    private String qq;
    /**
     * 用户微信号
     */
    private String wechat;
    /**
     * 用户账号创建时间
     */
    private Timestamp createstamp;
    /**
     * 用户最后一次登录时间
     */
    private Timestamp laststamp;
    /**
     * 用户状态（0为未登录，1为已登录）
     */
    private Integer status = 0;
    /**
     * 用户头像地址
     */
    private String iconpath;

    public User() {
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Timestamp getCreatestamp() {
        return createstamp;
    }

    public void setCreatestamp(Timestamp createstamp) {
        this.createstamp = createstamp;
    }

    public Timestamp getLaststamp() {
        return laststamp;
    }

    public void setLaststamp(Timestamp laststamp) {
        this.laststamp = laststamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }
}