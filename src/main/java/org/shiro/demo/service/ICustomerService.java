package org.shiro.demo.service;

import java.util.List;

import org.shiro.demo.entity.Customer;

/**
 * 客户服务层接口
 * @author Christy
 *
 */
public interface ICustomerService extends IBaseService{

	/**
	 * 通过微信id获取用户
	 * @param wechatid 微信id
	 * @return
	 */
	public Customer getCustomerbyWechatid(String wechatid);
	
	/**
	 * 新增客户
	 * @param customer
	 * @return
	 */
	public boolean insertCustomer(Customer customer);
	
	/**
	 * 删除客户
	 * @param id 用户id
	 * @return
	 */
	public boolean deleteCustomer(Long id);
	
	/**
	 * 更新客户
	 * @param customer 客户
	 * @return
	 */
	public boolean updateCustomer(Customer customer);
	
	/**
	 * 获取所有的商家
	 * @return
	 */
	public List<Customer> getAllShop();
}
