package org.shiro.demo.controller.app.controller;

import java.util.Map;

import org.shiro.demo.controller.app.ReturnData;
import org.shiro.demo.controller.app.exception.EncryptWrongExcetion;
import org.shiro.demo.controller.app.exception.ParamsWromgException;
import org.shiro.demo.controller.app.exception.TimeOutException;
import org.shiro.demo.service.IDBAttendService;
import org.shiro.demo.util.FastJsonTool;
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
	
	
}
