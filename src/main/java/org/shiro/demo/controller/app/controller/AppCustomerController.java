package org.shiro.demo.controller.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.shiro.demo.controller.app.ReturnData;
import org.shiro.demo.controller.app.exception.EncryptWrongExcetion;
import org.shiro.demo.controller.app.exception.ParamsWromgException;
import org.shiro.demo.controller.app.exception.TimeOutException;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Category;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.service.ICustomerService;
import org.shiro.demo.util.FastJsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="app/customer")
public class AppCustomerController extends AppBaseController{

	@Autowired
	private ICustomerService customerService;
	
	/**
	 * 获取用户余额、微信头像、微信昵称信息
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getbalance",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String getBalance(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			String wechatid = paramsMap.get("wechatid");
			//Double balance = Double.parseDouble(paramsMap.get("balance"));
			Customer customer = customerService.getCustomerbyWechatid(wechatid);
			returnData.setCode(ReturnData.SUCCESS);
			returnData.setMessage("成功");
			Customer returnCustomer = new Customer();
			returnCustomer.setBalance(customer.getBalance());
			returnCustomer.setWxname(customer.getWxname());
			returnCustomer.setWxavatar(customer.getWxavatar());
			returnData.setData(FastJsonTool.createJsonString(returnCustomer));
		} catch(EncryptWrongExcetion e){
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口数据有误");
			returnData.setData("");
		}catch (TimeOutException e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口已过期");
			returnData.setData("");
		}catch (ParamsWromgException e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口数据有误");
			returnData.setData("");
		}
		String resultdata = FastJsonTool.createJsonString(returnData);
		return resultdata;
	}
	
	/**
	 * 注册用户
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/regist",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String regist(@RequestParam(value="params")String params,@RequestParam(value="avator")String avator){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			String wechatid = paramsMap.get("wechatid");
			String wxname = paramsMap.get("wxname");
			Customer customer = new Customer(wechatid, new Double(0), 0, "", 0l, "",wxname,avator);
			boolean flag = customerService.insertCustomer(customer);
			returnData.setCode(ReturnData.SUCCESS);
			returnData.setMessage("成功");
			returnData.setData("");
		} catch(EncryptWrongExcetion e){
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口数据有误");
			returnData.setData("");
		}catch (TimeOutException e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口已过期");
			returnData.setData("");
		}catch (ParamsWromgException e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口数据有误");
			returnData.setData("");
		}
		String resultdata = FastJsonTool.createJsonString(returnData);
		return resultdata;
	}
	
	
	/**
	 * login
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String login(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			String wechatid = paramsMap.get("wechatid");
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			QueryCondition queryCondition = new QueryCondition("wechatid",QueryCondition.EQ,wechatid);
			queryConditions.add(queryCondition);
			List<Customer> list = customerService.get(Customer.class, queryConditions );
			boolean flag = false;
			if(list.size()>0){
				flag = true;
			}
			returnData.setCode(ReturnData.SUCCESS);
			returnData.setMessage("成功");
			if(flag){
				returnData.setData("1");
			}else{
				returnData.setData("0");
			}
			
		} catch(EncryptWrongExcetion e){
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口数据有误");
			returnData.setData("");
		}catch (TimeOutException e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口已过期");
			returnData.setData("");
		}catch (ParamsWromgException e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口数据有误");
			returnData.setData("");
		}
		String resultdata = FastJsonTool.createJsonString(returnData);
		return resultdata;
	}
	
}
