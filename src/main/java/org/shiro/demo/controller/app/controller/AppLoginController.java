package org.shiro.demo.controller.app.controller;

import java.util.List;
import java.util.Map;

import org.shiro.demo.controller.app.ReturnData;
import org.shiro.demo.controller.app.exception.EncryptWrongExcetion;
import org.shiro.demo.controller.app.exception.ParamsWromgException;
import org.shiro.demo.controller.app.exception.TimeOutException;
import org.shiro.demo.entity.Category;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.HttpUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="app/login")
public class AppLoginController {

	private String APPID="wx93871d2909ef71e8";
	
	private String SECRET="c044cc56f8876ff712cf5cb6347a74d8";
	/**
	 * 步骤一
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/stepOne",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String stepOne(@RequestParam(value="CODE")String code){
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String param = "appid="+APPID+"&secret="+SECRET+"&code="+code+"&grant_type=authorization_code";
		System.out.println(param);
		String token = HttpUtils.sendGet(url, param);
		return token;
	}
	
	/**
	 * 步骤二
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/stepTwo",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String stepTwo(@RequestParam(value="ACCESS_TOKEN")String token,@RequestParam(value="OPENID")String openid){
		String url ="https://api.weixin.qq.com/sns/userinfo";
		String param = "access_token="+token+"&openid="+openid;
		String resultdata = HttpUtils.sendGet(url, param);
		return resultdata;
	}
	
}
