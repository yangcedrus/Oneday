package com.whut.oneday.entity;


import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

/**
 * @author 22278
 * @version 1.0
 * @created 18-9月-2018 19:06:58
 */
public class Memo implements Parcelable {

    /**
     * 备忘录ID
     */
    private Integer memoID;
    /**
     * 用户ID
     */
    private Integer userID;
    /**
     * 备忘录标题
     */
    private String title;
    /**
     * 备忘录主体
     */
    private String body;
    /**
     * 创建时间
     */
    private Timestamp createstamp;
    /**
     * 提醒时间
     */
    private Timestamp remindstamp;
    /**
     * 备忘录删除时间
     */
    private Timestamp deletestamp;
    /**
     * 状态（0：activated，1：reminded，2：deleted）
     */
    private Integer status = 0;

    public Memo() {
    }

    public Integer getMemoID() {
        return memoID;
    }

    public void setMemoID(Integer memoID) {
        this.memoID = memoID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Timestamp getRemindstamp() {
        return remindstamp;
    }

    public void setRemindstamp(Timestamp remindstamp) {
        this.remindstamp = remindstamp;
    }

    public Timestamp getDeletestamp() {
        return deletestamp;
    }

    public void setDeletestamp(Timestamp deletestamp) {
        this.deletestamp = deletestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(memoID);
        dest.writeInt(userID);
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(createstamp.toString());
        dest.writeString(remindstamp.toString());
        dest.writeString(deletestamp.toString());
        dest.writeInt(status);
    }

    public static final Parcelable.Creator<Memo> CREATOR = new Parcelable.Creator<Memo>() {
        @Override
        public Memo createFromParcel(Parcel source) {
            Memo memo = new Memo();
            memo.memoID = source.readInt();
            memo.userID = source.readInt();
            memo.title = source.readString();
            memo.body = source.readString();
            memo.createstamp = Timestamp.valueOf(source.readString());
            memo.remindstamp = Timestamp.valueOf(source.readString());
            memo.deletestamp = Timestamp.valueOf(source.readString());
            memo.status = source.readInt();
            return memo;
        }

        @Override
        public Memo[] newArray(int i) {
            return new Memo[i];
        }
    };
}