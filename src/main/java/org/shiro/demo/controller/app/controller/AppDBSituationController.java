package org.shiro.demo.controller.app.controller;

import java.util.Map;

import org.shiro.demo.controller.app.ReturnData;
import org.shiro.demo.controller.app.exception.EncryptWrongExcetion;
import org.shiro.demo.controller.app.exception.ParamsWromgException;
import org.shiro.demo.controller.app.exception.TimeOutException;
import org.shiro.demo.service.IDBSituationService;
import org.shiro.demo.util.FastJsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="app/situation")
public class AppDBSituationController extends AppBaseController{

	@Autowired
	private IDBSituationService dbsituationService;
	
	/**
	 * 获取中奖记录
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getDBsituationWithWechatid",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String getDBsituationWithWechatid(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			Integer page = Integer.parseInt(paramsMap.get("page"));
			Integer pageSize = Integer.parseInt(paramsMap.get("pageSize"));
			String wechatid = paramsMap.get("wechatid");
			//Pagination<DBPlan> dbPlanPagination = dbPlanService.getPagination(DBPlan.class, null, "order by dbplanid desc", page, pageSize);
			//Map<String, Object> dataMap = AppDBplanVO.changeDBPlan2APPDBPlanVO(dbPlanPagination);
			Map<String, Object> dataMap = dbsituationService.getDBsituationWithWechatid( page, pageSize,  wechatid);
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
