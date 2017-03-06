package org.shiro.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.ICustomerService;
import org.springframework.stereotype.Service;

@Service("customerService")
public class CustomerServiceImpl extends DefultBaseService implements ICustomerService{

	@Resource(name="baseService")
	private IBaseService baseService;
	
	public boolean insertCustomer(Customer customer) {
		boolean flag = false;
		try {
			baseService.save(customer);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteCustomer(Long id) {
		boolean flag = false;
		try {
			baseService.delete(Customer.class, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateCustomer(Customer customer) {
		boolean flag = false;
		try {
			baseService.update(customer);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public List<Customer> getAllShop() {
		List<Customer> resultList = null;
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		QueryCondition queryCondition = new QueryCondition("isshop", QueryCondition.EQ, 1);
		queryConditions.add(queryCondition);
		resultList = baseService.get(Customer.class, queryConditions);
		return resultList;
	}
	
}
