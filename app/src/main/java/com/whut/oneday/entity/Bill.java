package com.whut.oneday.entity;


import java.sql.Timestamp;

/**
 * @author 22278
 * @version 1.0
 * @created 18-9月-2018 19:06:27
 */
public class Bill {

	/**
	 * 账单ID
	 */
	private Integer billID;
	/**
	 * 用户ID
	 */
	private Integer userID;
	/**
	 * 项目名称
	 */
	private String title;
	/**
	 * income or expenses，true为收，false为出
	 */
	private Boolean IOE = false;
	/**
	 * 默认中0为其他花费，另外见文档
	 */
	private Integer billType = 0;
	/**
	 * 创建账单时间
	 */
	private Timestamp createstamp;
	/**
	 * 本账单记录金额
	 */
	private Double money=0.00;
	/**
	 * 备注，默认无
	 */
	private String tips;

	public Bill() {
	}

	public Bill(Integer billID, Integer userID, String title, Boolean IOE, Integer billType, Double money, String tips) {
		this.billID = billID;
		this.userID = userID;
		this.title = title;
		this.IOE = IOE;
		this.billType = billType;
		this.money = money;
		this.tips = tips;
		createstamp=new Timestamp(0);
	}

	public Integer getBillID() {
		return billID;
	}

	public void setBillID(Integer billID) {
		this.billID = billID;
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

	public Boolean getIOE() {
		return IOE;
	}

	public void setIOE(Boolean IOE) {
		this.IOE = IOE;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public Timestamp getCreatestamp() {
		return createstamp;
	}

	public void setCreatestamp(Timestamp createstamp) {
		this.createstamp = createstamp;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}
}