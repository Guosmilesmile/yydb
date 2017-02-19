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
		map.put("total", pagination.getPageCount());
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

	
	public CustomerVO(Long customerid, String wechatid, Double balance) {
		super();
		this.customerid = customerid;
		this.wechatid = wechatid;
		this.balance = balance;
	}

	public CustomerVO(Customer customer) {
		super();
		this.customerid= customer.getCustomerid();
		this.wechatid = customer.getWechatid();
		this.balance = customer.getBalance();
	}

	public Long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}
}
