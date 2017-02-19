package org.shiro.demo.entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统顾客实体
 * @author Christy
 *
 */
@Entity
@Table(name="c_customer")
public class Customer {

	public Customer(String wechatid, Double balance) {
		super();
		this.wechatid = wechatid;
		this.balance = balance;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "customerid")
	private Long customerid;//id
	
	@Column(name = "wechatid")
	private String wechatid;//微信号
	
	@Column(name = "balance")
	private Double balance;//余额

	public Customer() {
		super();
	}

	public Customer(Long customerid, String wechatid, Double balance) {
		super();
		this.customerid = customerid;
		this.wechatid = wechatid;
		this.balance = balance;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public String getWechatid() {
		return wechatid;
	}

	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}
