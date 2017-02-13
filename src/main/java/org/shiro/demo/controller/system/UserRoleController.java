package org.shiro.demo.controller.system;

import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Role;
import org.shiro.demo.entity.UserRole;
import org.shiro.demo.service.IRoleService;
import org.shiro.demo.service.IUserRoleService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.vo.RoleVO;
import org.shiro.demo.vo.UserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/userrole")
public class UserRoleController {

	@Autowired
	private IUserRoleService userRoleService;
	
	/**
	 * 分页获取所有角色信息
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/systemgetpageuserrole",method=RequestMethod.POST)
	@ResponseBody
	public String systemGetUserRoleByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<UserRole> userRolePagination = userRoleService.getPagination(UserRole.class, null, "order by user.id", page, pageSize);
		Map<String, Object> useRoleVOMap = UserRoleVO.changeUserRole2UserRoleVO(userRolePagination);
		returnResult =  FastJsonTool.createJsonString(useRoleVOMap);
		return returnResult;
	}
}
