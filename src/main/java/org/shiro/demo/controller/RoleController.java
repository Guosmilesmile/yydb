package org.shiro.demo.controller;

import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Role;
import org.shiro.demo.service.IRoleService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.vo.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/role")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	/**
	 * 分页获取所有角色信息
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/systemgetpagerole",method=RequestMethod.POST)
	@ResponseBody
	public String systemGetUserByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<Role> rolePagination = roleService.getPagination(Role.class, null, null, page, pageSize);
		Map<String, Object> userVOMap = RoleVO.changeRole2RoleVO(rolePagination);
		returnResult =  FastJsonTool.createJsonString(userVOMap);
		return returnResult;
	}
}
