package com.whut.oneday.entity;


import java.sql.Timestamp;

/**
 * @author 22278
 * @version 1.0
 * @created 18-9月-2018 19:06:58
 */
public class Memo {

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

	public Memo(){
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
}