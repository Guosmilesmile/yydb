package org.shiro.demo.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.DBPlan;

public class UDBPlanVO {

	private Long id;
	
	private Integer block;//分区（1：一元区，2：十元区，3：百元区，4：千元区）
	
	private Long split;//单次竞标价
	
	private Long startTime;//竞标开始时间
	
	private Long endTime;//竞标结束

	private Integer number;//数量
	
	private Double money;//价位
	
	private Long goodsid;
	
	

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

	public UDBPlanVO() {
		super();
	}
	

	

	public UDBPlanVO(DBPlan dbPlan) {
		super();
		this.id = dbPlan.getDbplanid();
		this.block = dbPlan.getBlock();
		this.split = dbPlan.getSplit();
		this.startTime = dbPlan.getStartTime();
		this.endTime = dbPlan.getEndTime();
		this.number = dbPlan.getNumber();
		this.money = dbPlan.getMoney();
		this.goodsid = dbPlan.getGoods().getGoodsid();
	}

	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static Map<String, Object> changeDBPlan2DBPlanVO(Pagination<DBPlan> pagination){
		List<DBPlan> recordList = pagination.getRecordList();
		List<UDBPlanVO> VOList = new ArrayList<UDBPlanVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(DBPlan item : recordList){
			VOList.add(new UDBPlanVO(item));
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
	public static List<UDBPlanVO> changeDBPlan2DBPlanVO(List<DBPlan> recordList){
		List<UDBPlanVO> roleVOList = new ArrayList<UDBPlanVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(DBPlan item : recordList){
			roleVOList.add(new UDBPlanVO(item));
		}
		return roleVOList;
	}


	public UDBPlanVO(Long id, Integer block, Long split, Long startTime,
			Long endTime, Integer number, Double money, Long goodsid) {
		super();
		this.id = id;
		this.block = block;
		this.split = split;
		this.startTime = startTime;
		this.endTime = endTime;
		this.number = number;
		this.money = money;
		this.goodsid = goodsid;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
	}
	
}
