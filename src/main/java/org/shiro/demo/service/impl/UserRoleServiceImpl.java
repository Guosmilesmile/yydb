package org.shiro.demo.service.impl;

import javax.annotation.Resource;

import org.shiro.demo.entity.User;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IUserRoleService;
import org.shiro.demo.service.IUserService;
import org.springframework.stereotype.Service;

@Service("userroleService")
public class UserRoleServiceImpl extends DefultBaseService implements IUserRoleService{

	@Resource(name="baseService")
	private IBaseService baseService;
	
}
