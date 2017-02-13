package org.shiro.demo.service;

import org.shiro.demo.entity.User;

public interface IUserService extends IBaseService{
	
	/**
	 * 根据账号获取用户信息
	 * @param account 账户id
	 * @return
	 */
	public User getByAccount(String account);

	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	public boolean register(User user);
	
}
