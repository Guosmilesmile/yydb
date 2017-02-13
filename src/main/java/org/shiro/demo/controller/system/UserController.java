package org.shiro.demo.controller.system;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.User;
import org.shiro.demo.service.IUserService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.vo.UserVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Resource(name="userService")
	private IUserService userService;

	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/register",method=RequestMethod.POST)
	@ResponseBody
	@RequiresRoles("administrator")
	public boolean register(User user){
		return userService.register(user);
	}
	
	//int pageSize = Integer.parseInt(request.getParameter("rows"));
	//int page = Integer.parseInt(request.getParameter("page"));
	/**
	 * 分页获取所有用户信息
	 */
	@RequestMapping(value = "/systemgetpageuser",method=RequestMethod.POST)
	@ResponseBody
	public String systemGetUserByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<User> userPagination = userService.getPagination(User.class, null, null, page, pageSize);
		Map<String, Object> userVOMap = UserVO.changeUser2UserVO(userPagination);
		returnResult =  FastJsonTool.createJsonString(userVOMap);
		return returnResult;
	}
	
}
