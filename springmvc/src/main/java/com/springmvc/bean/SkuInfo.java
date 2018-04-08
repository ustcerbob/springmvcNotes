package com.springmvc.bean;

import java.util.Date;

public class SkuInfo {
	private String id;
	private String skuName;
	private Integer price;
	private Integer skuNum;
	private Date createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getSkuNum() {
		return skuNum;
	}
	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	
}
