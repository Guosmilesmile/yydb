package org.shiro.demo.service;

import org.shiro.demo.entity.Customer;

/**
 * 客户服务层接口
 * @author Christy
 *
 */
public interface ICustomerService extends IBaseService{

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
}
