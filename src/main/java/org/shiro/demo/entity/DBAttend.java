package org.shiro.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 夺宝计划参与情况
 * @author Christy
 *
 */
@Entity
@Table(name="db_dbattend")
public class DBAttend {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="attendid")
	private Long attendid;//id
	
	@Column(name="createtime")
	private Long createTime;//创建时间
	
	@Column(name="isplay")
	private Integer isplay;//是否付款
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE })
	@JoinColumn(name = "customerid")
	private Customer customer;//顾客
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE })
	@JoinColumn(name = "dbplanid")
	private DBPlan dbPlan;//参与夺宝计划

	public DBAttend() {
		super();
	}
	
	public DBAttend(Long attendid, Long createTime, Integer isplay,
			Customer customer, DBPlan dbPlan) {
		super();
		this.attendid = attendid;
		this.createTime = createTime;
		this.isplay = isplay;
		this.customer = customer;
		this.dbPlan = dbPlan;
	}

	public DBAttend(Long createTime, Integer isplay, Customer customer,
			DBPlan dbPlan) {
		super();
		this.createTime = createTime;
		this.isplay = isplay;
		this.customer = customer;
		this.dbPlan = dbPlan;
	}

	public Long getAttendid() {
		return attendid;
	}

	public void setAttendid(Long attendid) {
		this.attendid = attendid;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getIsplay() {
		return isplay;
	}

	public void setIsplay(Integer isplay) {
		this.isplay = isplay;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public DBPlan getDbPlan() {
		return dbPlan;
	}

	public void setDbPlan(DBPlan dbPlan) {
		this.dbPlan = dbPlan;
	}

	
	
}
