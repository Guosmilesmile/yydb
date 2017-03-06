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

	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "customerid")
	private Long customerid;//id
	
	@Column(name = "wechatid")
	private String wechatid;//微信号
	
	@Column(name = "balance")
	private Double balance;//余额

	@Column(name = "isshop")
	private Integer isshop;//是否商家

	@Column(name = "address")
	private String address;//商家地址
	
	@Column(name = "phone")
	private Long phone;//商家联系方式
	
	@Column(name = "name")
	private String name;//商家名称

	public Customer() {
		super();
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

	public Integer getIsshop() {
		return isshop;
	}

	public void setIsshop(Integer isshop) {
		this.isshop = isshop;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Customer(Long customerid, String wechatid, Double balance,
			Integer isshop, String address, Long phone,String name) {
		super();
		this.customerid = customerid;
		this.wechatid = wechatid;
		this.balance = balance;
		this.isshop = isshop;
		this.address = address;
		this.phone = phone;
		this.name = name;
	}

	public Customer(String wechatid, Double balance, Integer isshop,
			String address, Long phone,String name) {
		super();
		this.wechatid = wechatid;
		this.balance = balance;
		this.isshop = isshop;
		this.address = address;
		this.phone = phone;
		this.name = name;
	}

}
