package org.shiro.demo.controller.app.vo;

import java.util.ArrayList;
import java.util.List;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.DBAttend;
import org.shiro.demo.entity.DBPlan;

public class AppAttendVO {

	public final static Integer BLOCKONE = 1;//一元区
	
	public final static Integer BLOCKTEN = 2;//十元区
	
	public final static Integer BLOCKHUNDRED = 3;//百元区
	
	public final static Integer BLOCKTHOUSAND = 4;//千元区
	
	public final static Integer BLOCKWANG = 5;//万元区
	
	public final static Integer SITUATIONNULL = 0;//流标
	
	public final static Integer SITUATIONFULL = 1;//未流标
	
	public final static Integer SITUATIONING = 2;//进行中
	
	private Long id;
	
	private Long dbplanid;
	
	private Integer block;//分区（1：一元区，2：十元区，3：百元区，4：千元区）
	
	private String imgurls;//图片地址
	
	private Long split;//单次竞标价
	
	private Long startTime;//竞标开始时间
	
	private Long endTime;//竞标结束

	private Integer attendNumber;//已参与人数
	
	private Integer number;//总参与人数
	
	private Double money;//价位
	
	private String goodsName;//商品名称
	
	private String shopName;//商家名称
	
	private Integer isfinish;//是否结束
	
	private String luckdogWechatid;//中奖者微信id
	
	private Integer selfCount;//个人参数次数
	
	private Long systemTime;//系统时间
	
	private Integer istake = 0;//是否领取  未领取：0 已领取：1
	
	private Integer situation;//状态   0：流标  1：有中奖者 2：未得出结果
	
	public AppAttendVO(Long id, Integer block, String imgurls, Long split,
			Long startTime, Long endTime, Integer attendNumber, Integer number,
			Double money, String goodsName, String shopName, Integer isfinish,
			String luckdogWechatid, Integer selfCount, Long systemTime) {
		super();
		this.id = id;
		this.block = block;
		this.imgurls = imgurls;
		this.split = split;
		this.startTime = startTime;
		this.endTime = endTime;
		this.attendNumber = attendNumber;
		this.number = number;
		this.money = money;
		this.goodsName = goodsName;
		this.shopName = shopName;
		this.isfinish = isfinish;
		this.luckdogWechatid = luckdogWechatid;
		this.selfCount = selfCount;
		this.systemTime = systemTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getBlock() {
		return block;
	}

	public void setBlock(Integer block) {
		this.block = block;
	}

	public String getImgurls() {
		return imgurls;
	}

	public void setImgurls(String imgurls) {
		this.imgurls = imgurls;
	}

	public Long getSplit() {
		return split;
	}

	public void setSplit(Long split) {
		this.split = split;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public Integer getAttendNumber() {
		return attendNumber;
	}

	public void setAttendNumber(Integer attendNumber) {
		this.attendNumber = attendNumber;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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

	public Integer getIsfinish() {
		return isfinish;
	}

	public void setIsfinish(Integer isfinish) {
		this.isfinish = isfinish;
	}

	public String getLuckdogWechatid() {
		return luckdogWechatid;
	}

	public void setLuckdogWechatid(String luckdogWechatid) {
		this.luckdogWechatid = luckdogWechatid;
	}

	public Integer getSelfCount() {
		return selfCount;
	}

	public void setSelfCount(Integer selfCount) {
		this.selfCount = selfCount;
	}

	public Long getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Long systemTime) {
		this.systemTime = systemTime;
	}
	public Long getDbplanid() {
		return dbplanid;
	}

	public void setDbplanid(Long dbplanid) {
		this.dbplanid = dbplanid;
	}
	
	public Integer getIstake() {
		return istake;
	}

	public void setIstake(Integer istake) {
		this.istake = istake;
	}

	public Integer getSituation() {
		return situation;
	}

	public void setSituation(Integer situation) {
		this.situation = situation;
	}
	public AppAttendVO(DBAttend dbAttend) {
		super();
		this.id = dbAttend.getAttendid();
		this.dbplanid = dbAttend.getDbPlan().getDbplanid();
		this.block = dbAttend.getDbPlan().getBlock();
		this.imgurls = dbAttend.getDbPlan().getGoods().getImgurls();
		this.split = dbAttend.getDbPlan().getSplit();
		this.startTime = dbAttend.getDbPlan().getStartTime();
		this.endTime = dbAttend.getDbPlan().getEndTime();
		this.attendNumber = 0;
		this.number = dbAttend.getDbPlan().getNumber();
		this.money = dbAttend.getDbPlan().getMoney();
		this.goodsName = dbAttend.getDbPlan().getGoods().getName();
		this.shopName = dbAttend.getDbPlan().getGoods().getShop().getName();
		this.isfinish = dbAttend.getDbPlan().getIsfinish();
		this.luckdogWechatid = "";
		this.selfCount = 0;
		this.systemTime = System.currentTimeMillis()/1000;
	}

	
	public static List<AppAttendVO> changeAttend2AttendVO(List<DBAttend> dbAttends){
		List<AppAttendVO> appAttendVOs = new ArrayList<AppAttendVO>();
		for(DBAttend dbAttend : dbAttends){
			appAttendVOs.add(new AppAttendVO(dbAttend));
		}
		return appAttendVOs;
	}
	
	
	public static List<AppAttendVO> changeAttend2AttendVO(Pagination<DBAttend> dbAttends){
		List<DBAttend> recordList = dbAttends.getRecordList();
		List<AppAttendVO> appAttendVOs = new ArrayList<AppAttendVO>();
		for(DBAttend dbAttend : recordList){
			appAttendVOs.add(new AppAttendVO(dbAttend));
		}
		return appAttendVOs;
	}

	
	public AppAttendVO(DBPlan dbPlan) {
		super();
		this.id = 0l;
		this.dbplanid = dbPlan.getDbplanid();
		this.block = dbPlan.getBlock();
		this.imgurls = dbPlan.getGoods().getImgurls();
		this.split = dbPlan.getSplit();
		this.startTime = dbPlan.getStartTime();
		this.endTime = dbPlan.getEndTime();
		this.attendNumber = 0;
		this.number = dbPlan.getNumber();
		this.money = dbPlan.getMoney();
		this.goodsName = dbPlan.getGoods().getName();
		this.shopName = dbPlan.getGoods().getShop().getName();
		this.isfinish = dbPlan.getIsfinish();
		this.luckdogWechatid = "";
		this.selfCount = 0;
		this.systemTime = System.currentTimeMillis()/1000;
	}


	
}
