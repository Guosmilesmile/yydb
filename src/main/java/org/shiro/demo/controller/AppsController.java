package org.shiro.demo.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app")
public class AppsController {
	
	//@RequiresPermissions("admin:user")
	//@RequiresUser
	@ResponseBody
	@RequestMapping(value = "/getdata" ,method=RequestMethod.GET)
	public String getdata(){
		return "app:getdata";
	}
	
	@ResponseBody
	@RequestMapping(value = "/error" ,method=RequestMethod.GET)
	public String errormessage(){
		return "Unauthenticated,return to login";
	}

	
}
