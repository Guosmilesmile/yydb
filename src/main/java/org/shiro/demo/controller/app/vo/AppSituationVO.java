package org.shiro.demo.controller.app.vo;

import org.shiro.demo.entity.DBSituation;

public class AppSituationVO {

	private Long id;
	
	private String imgurls;//图片地址
	
	private Double money;//价位
	
	private String goodsName;//商品名称
	
	private String shopName;//商家名称
	
	private String address;//商家地址
	
	private Long systemTime;//系统时间
	
	private Long endTime;//结束时间

	public AppSituationVO(Long id, String imgurls, Double money,
			String goodsName, String shopName, String address, Long systemTime,
			Long endTime) {
		super();
		this.id = id;
		this.imgurls = imgurls;
		this.money = money;
		this.goodsName = goodsName;
		this.shopName = shopName;
		this.address = address;
		this.systemTime = systemTime;
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImgurls() {
		return imgurls;
	}

	public void setImgurls(String imgurls) {
		this.imgurls = imgurls;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Long systemTime) {
		this.systemTime = systemTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public AppSituationVO() {
		super();
	}

	public AppSituationVO(DBSituation dbSituation) {
		super();
		this.id = dbSituation.getSituationid();
		this.imgurls = dbSituation.getDbPlan().getGoods().getImgurls();
		this.money = dbSituation.getDbPlan().getMoney();
		this.goodsName = dbSituation.getDbPlan().getGoods().getName();
		this.shopName = dbSituation.getDbPlan().getGoods().getShop().getName();
		this.address = dbSituation.getDbPlan().getGoods().getShop().getAddress();
		this.systemTime = System.currentTimeMillis()/1000;
		this.endTime = dbSituation.getDbPlan().getEndTime();
	}
	
	
}
