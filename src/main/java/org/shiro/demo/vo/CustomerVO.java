package org.shiro.demo.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Customer;
/**
 * 客户显示层
 * @author Christy
 *
 */
public class CustomerVO{

	private Long customerid;//id
	private String wechatid;//微信id
	private Double balance;//余额
	private Integer isshop;//是否为商家
	private Long phone;//联系方式
	private String address;//地址
	private String name;//商家名称
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static Map<String, Object> changeCustomer2CustomerVO(Pagination<Customer> pagination){
		List<Customer> recordList = pagination.getRecordList();
		List<CustomerVO> VOList = new ArrayList<CustomerVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(Customer item : recordList){
			VOList.add(new CustomerVO(item));
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
	public static List<CustomerVO> changeCustomer2CustomerVO(List<Customer> recordList){
		List<CustomerVO> roleVOList = new ArrayList<CustomerVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(Customer item : recordList){
			roleVOList.add(new CustomerVO(item));
		}
		return roleVOList;
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

	public CustomerVO() {
		super();
	}

	public CustomerVO(Long customerid, String wechatid, Double balance,
			Integer isshop, Long phone, String address) {
		super();
		this.customerid = customerid;
		this.wechatid = wechatid;
		this.balance = balance;
		this.isshop = isshop;
		this.phone = phone;
		this.address = address;
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}

	public Integer getIsshop() {
		return isshop;
	}

	public void setIsshop(Integer isshop) {
		this.isshop = isshop;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public CustomerVO(Customer customer) {
		super();
		this.customerid= customer.getCustomerid();
		this.wechatid = customer.getWechatid();
		this.balance = customer.getBalance();
		this.isshop = customer.getIsshop();
		this.phone = customer.getPhone();
		this.address = customer.getAddress();
		this.name = customer.getName();
	}

}
