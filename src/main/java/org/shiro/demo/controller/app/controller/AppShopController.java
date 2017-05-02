package org.shiro.demo.controller.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.shiro.demo.controller.app.ReturnData;
import org.shiro.demo.controller.app.exception.EncryptWrongExcetion;
import org.shiro.demo.controller.app.exception.ParamsWromgException;
import org.shiro.demo.controller.app.exception.TimeOutException;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.service.ICustomerService;
import org.shiro.demo.service.IDBPlanService;
import org.shiro.demo.util.FastJsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="app/shop")
public class AppShopController  extends AppBaseController{

	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private IDBPlanService dbPlanService;
	
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
	
	
	/**
	 * 获取所有夺宝计划
	 * @param params columnName 排序字段  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getDBPlanPage",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String getDBPlanPage(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			Integer page = Integer.parseInt(paramsMap.get("page"));
			Integer pageSize = Integer.parseInt(paramsMap.get("pageSize"));
			String wechatid = paramsMap.get("wechatid");
			Map<String, Object> dataMap = dbPlanService.getDBPlanWithWechatid(page, pageSize, wechatid);
			returnData.setCode(ReturnData.SUCCESS);
			returnData.setMessage("成功");
			returnData.setData(FastJsonTool.createJsonString(dataMap));
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
		}catch (Exception e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口数据有误");
			returnData.setData("");
		}
		String resultdata = FastJsonTool.createJsonString(returnData);
		return resultdata;
	}
}
