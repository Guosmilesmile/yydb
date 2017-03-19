package org.shiro.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="db_dbplan")
public class DBPlan {

	public final static Integer BLOCKONE = 1;//一元区
	
	public final static Integer BLOCKTEN = 2;//十元区
	
	public final static Integer BLOCKHUNDRED = 3;//百元区
	
	public final static Integer BLOCKTHOUSAND = 4;//千元区
	
	public final static Integer BLOCKWANG = 5;//万元区
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dbplanid")
	private Long dbplanid;
	
	@Column(name="block")
	private Integer block;//分区（1：一元区，2：十元区，3：百元区，4：千元区，5：万元区）
	
	@Column(name="split")
	private Long split;//单次竞标价
	
	@Column(name="starttime")
	private Long startTime;//竞标开始时间
	
	@Column(name="endtime")
	private Long endTime;//竞标结束
	
	@Column(name="number")
	private Integer number;//数量
	
	@Column(name="money")
	private Double money;//价位
	
	@Column(name="isfinish")
	private Integer isfinish = 0;//是否结束
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "goodsid")
	private Goods goods;

	public DBPlan(Long dbplanid, Integer block, Long split, Long startTime,
			Long endTime, Integer number, Double money, Goods goods) {
		super();
		this.dbplanid = dbplanid;
		this.block = block;
		this.split = split;
		this.startTime = startTime;
		this.endTime = endTime;
		this.number = number;
		this.money = money;
		this.goods = goods;
	}

	public DBPlan(Integer block, Long split, Long startTime, Long endTime,
			Integer number, Double money, Goods goods) {
		super();
		this.block = block;
		this.split = split;
		this.startTime = startTime;
		this.endTime = endTime;
		this.number = number;
		this.money = money;
		this.goods = goods;
	}
	
	public DBPlan(Long id,Long split, Long startTime, Long endTime,
			Integer number, Double money, Goods goods) {
		super();
		this.dbplanid = id;
		this.split = split;
		this.startTime = startTime;
		this.endTime = endTime;
		this.number = number;
		this.money = money;
		this.goods = goods;
		if(split>0&&split<=10){
			this.block = DBPlan.BLOCKONE;
		}else if(split>10&&split<=100){
			this.block = DBPlan.BLOCKTEN;
		}else if(split>100&&split<=1000){
			this.block = DBPlan.BLOCKHUNDRED;
		}else if(split>1000&&split<=10000){
			this.block = DBPlan.BLOCKTHOUSAND;
		}else if(split>100000){
			this.block = DBPlan.BLOCKWANG;
		}
	}
	
	public DBPlan(Long split, Long startTime, Long endTime,
			Integer number, Double money, Goods goods) {
		super();
		this.split = split;
		this.startTime = startTime;
		this.endTime = endTime;
		this.number = number;
		this.money = money;
		this.goods = goods;
		if(split>0&&split<=10){
			this.block = DBPlan.BLOCKONE;
		}else if(split>10&&split<=100){
			this.block = DBPlan.BLOCKTEN;
		}else if(split>100&&split<=1000){
			this.block = DBPlan.BLOCKHUNDRED;
		}else if(split>1000&&split<=10000){
			this.block = DBPlan.BLOCKTHOUSAND;
		}else if(split>100000){
			this.block = DBPlan.BLOCKWANG;
		}
	}

	public DBPlan() {
		super();
	}

	public Long getDbplanid() {
		return dbplanid;
	}

	public void setDbplanid(Long dbplanid) {
		this.dbplanid = dbplanid;
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

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Integer getIsfinish() {
		return isfinish;
	}

	public void setIsfinish(Integer isfinish) {
		this.isfinish = isfinish;
	}
	
	
}
