package org.shiro.demo.controller.app.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.DBPlan;

public class AppDBplanVO {
	
	public final static Integer BLOCKONE = 1;//一元区
	
	public final static Integer BLOCKTEN = 2;//十元区
	
	public final static Integer BLOCKHUNDRED = 3;//百元区
	
	public final static Integer BLOCKTHOUSAND = 4;//千元区
	
	public final static Integer BLOCKWANG = 5;//万元区
	
	private Long id;
	
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
	
	private String address;//商家地址
	
	private Long phone;//商家联系方式
	
	private Integer isfinish;//是否结束
	
	private Long systemTime;//系统时间

	public AppDBplanVO() {
		super();
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

	public Long getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Long systemTime) {
		this.systemTime = systemTime;
	}

	public Integer getAttendNumber() {
		return attendNumber;
	}

	public void setAttendNumber(Integer attendNumber) {
		this.attendNumber = attendNumber;
	}

	public String getImgurls() {
		return imgurls;
	}

	public void setImgurls(String imgurls) {
		this.imgurls = imgurls;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}
	
	public AppDBplanVO(DBPlan dbPlan) {
		super();
		this.id = dbPlan.getDbplanid();
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
		this.address = dbPlan.getGoods().getShop().getAddress();
		this.phone = dbPlan.getGoods().getShop().getPhone();
		this.isfinish = dbPlan.getIsfinish();
		this.systemTime = System.currentTimeMillis()/1000;
		
	}

	public AppDBplanVO(Long id, Integer block, String imgurls, Long split,
			Long startTime, Long endTime, Integer attendNumber, Integer number,
			Double money, String goodsName, String shopName, Integer isfinish,
			Long systemTime) {
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
		this.systemTime = systemTime;
	}

	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static List<AppDBplanVO> changeDBPlan2APPDBPlanVOList(Pagination<DBPlan> pagination){
		List<DBPlan> recordList = pagination.getRecordList();
		List<AppDBplanVO> VOList = new ArrayList<AppDBplanVO>();
		for(DBPlan item : recordList){
			VOList.add(new AppDBplanVO(item));
		}
		return VOList;
	}
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static Map<String, Object> changeDBPlan2APPDBPlanVO(Pagination<DBPlan> pagination){
		List<DBPlan> recordList = pagination.getRecordList();
		List<AppDBplanVO> VOList = new ArrayList<AppDBplanVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(DBPlan item : recordList){
			VOList.add(new AppDBplanVO(item));
		}
		map.put("rows", VOList);
		map.put("total", pagination.getRecordCount());
		return map;
	}
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static List<AppDBplanVO> changeDBPlan2APPDBPlanVOList(List<DBPlan> list){
		List<DBPlan> recordList = list;
		List<AppDBplanVO> VOList = new ArrayList<AppDBplanVO>();
		for(DBPlan item : recordList){
			VOList.add(new AppDBplanVO(item));
		}
		return VOList;
	}

}
