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
import org.shiro.demo.entity.DBAttend;
import org.shiro.demo.entity.DBPlan;
import org.shiro.demo.entity.DBSituation;
import org.shiro.demo.service.IDBAttendService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="app/dbattend")
public class AppDBAttendController extends AppBaseController{

	
	@Autowired
	private IDBAttendService dbAttendService;
	
	
	/**
	 * 获取参与的夺宝计划
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getDBAttendPage",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String getDBAttendPage(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			Integer page = Integer.parseInt(paramsMap.get("page"));
			Integer pageSize = Integer.parseInt(paramsMap.get("pageSize"));
			String wechatid = paramsMap.get("wechatid");
			int isfinish = Integer.parseInt(paramsMap.get("isfinish"));
			//Pagination<DBPlan> dbPlanPagination = dbPlanService.getPagination(DBPlan.class, null, "order by dbplanid desc", page, pageSize);
			//Map<String, Object> dataMap = AppDBplanVO.changeDBPlan2APPDBPlanVO(dbPlanPagination);
			Map<String, Object> dataMap = dbAttendService.getDBAttendVOWithWechatid( page, pageSize,  wechatid,isfinish);
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
	
	/**
	 * 修改夺宝参与记录的支付状态
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/changeispay",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String changeIsPay(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			Integer isplay = Integer.parseInt(paramsMap.get("isplay"));
			String wechatid = paramsMap.get("wechatid");
			Long dbattendid = Long.parseLong(paramsMap.get("dbattendid"));
			//Pagination<DBPlan> dbPlanPagination = dbPlanService.getPagination(DBPlan.class, null, "order by dbplanid desc", page, pageSize);
			//Map<String, Object> dataMap = AppDBplanVO.changeDBPlan2APPDBPlanVO(dbPlanPagination);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition(" customer.wechatid='"+wechatid+"'"));
			queryConditions.add(new QueryCondition(" attendid="+dbattendid));
			List<DBAttend> list = dbAttendService.get(DBAttend.class, queryConditions);
			if(null==list||list.size()==0){
				returnData.setCode(ReturnData.FAIL);
				returnData.setMessage("失败");
				returnData.setData("");
			}else{
				DBAttend dbAttend = list.get(0);
				if(isplay==0){
					dbAttendService.delete(DBAttend.class, dbAttend.getAttendid());
				}else{
					dbAttend.setIsplay(isplay);
					dbAttendService.update(dbAttend);
				}
				returnData.setCode(ReturnData.SUCCESS);
				returnData.setMessage("成功");
				returnData.setData("");
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
		}catch (Exception e) {
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口数据有误");
			returnData.setData("");
		}
		String resultdata = FastJsonTool.createJsonString(returnData);
		return resultdata;
	}
	
	/**
	 * 参与夺宝
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/insertAttend",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String insertAttend(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			String wechatid = paramsMap.get("wechatid");
			Integer useBalance = Integer.parseInt(paramsMap.get("usebalance"));
			Long dbplanid = Long.parseLong(paramsMap.get("dbplanid"));
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition(" wechatid='"+wechatid+"'"));
			Customer customer =  dbAttendService.get(Customer.class, queryConditions).get(0);
			DBPlan dbPlan = dbAttendService.getById(DBPlan.class, dbplanid);
			DBAttend dbattend = new DBAttend(System.currentTimeMillis()/1000, 0, customer, dbPlan);
			//Pagination<DBPlan> dbPlanPagination = dbPlanService.getPagination(DBPlan.class, null, "order by dbplanid desc", page, pageSize);
			//Map<String, Object> dataMap = AppDBplanVO.changeDBPlan2APPDBPlanVO(dbPlanPagination);
			int  flag = dbAttendService.insertDBAttend(dbattend,useBalance);
			if(flag==1){
				returnData.setCode(ReturnData.SUCCESS);
				returnData.setMessage("成功");
				returnData.setData("");
			}else if(flag==0){
				returnData.setCode(ReturnData.FAIL);
				returnData.setMessage("人数已满");
				returnData.setData("");
			}else if(flag ==2){
				returnData.setCode(ReturnData.FAIL);
				returnData.setMessage("余额不足");
				returnData.setData("");
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
