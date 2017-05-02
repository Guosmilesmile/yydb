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
 * 夺宝情况表
 * @author Christy
 *
 */
@Entity
@Table(name="db_situation")
public class DBSituation {



	@Id
	@Column(name="situationid")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long situationid;
	
	@Column(name="istake")
	private Integer istake;//是否领取  0：未领取 ;1：已领取

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dbplanid")
	private DBPlan dbPlan;
	
	@ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE })
	@JoinColumn(name = "customerid")
	private Customer customer;

	public DBSituation(Long situationid, Integer istake, DBPlan dbPlan,
			Customer customer) {
		super();
		this.situationid = situationid;
		this.istake = istake;
		this.dbPlan = dbPlan;
		this.customer = customer;
	}

	public DBSituation() {
		super();
	}

	public Long getSituationid() {
		return situationid;
	}

	public void setSituationid(Long situationid) {
		this.situationid = situationid;
	}

	public Integer getIstake() {
		return istake;
	}

	public void setIstake(Integer istake) {
		this.istake = istake;
	}

	public DBPlan getDbPlan() {
		return dbPlan;
	}

	public void setDbPlan(DBPlan dbPlan) {
		this.dbPlan = dbPlan;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public DBSituation(Integer istake, DBPlan dbPlan, Customer customer) {
		super();
		this.istake = istake;
		this.dbPlan = dbPlan;
		this.customer = customer;
	}
}
