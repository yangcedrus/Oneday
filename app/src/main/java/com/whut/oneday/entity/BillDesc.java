package com.whut.oneday.entity;

import java.util.Date;

/**
 * 账单信息javabean
 */
public class BillDesc {
    String name;    //消费名称
    String classify; //消费分类
    Double money;   //消费金额
    Date createTime; //记录时间
    String others;  //备注

    public BillDesc() {
    }

    public BillDesc(String name, String classify, Double money, Date createTime, String others) {
        this.name = name;
        this.classify = classify;
        this.money = money;
        this.createTime = createTime;
        this.others = others;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }
}
