package org.shiro.demo.service.impl;

import javax.annotation.Resource;

import org.shiro.demo.entity.User;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IRoleService;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleServiceImpl extends DefultBaseService implements IRoleService{

	@Resource(name="baseService")
	private IBaseService baseService;
	
}
