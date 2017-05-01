package org.shiro.demo.controller.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.shiro.demo.controller.app.ReturnData;
import org.shiro.demo.controller.app.exception.EncryptWrongExcetion;
import org.shiro.demo.controller.app.exception.ParamsWromgException;
import org.shiro.demo.controller.app.exception.TimeOutException;
import org.shiro.demo.controller.app.vo.AppSituationVO;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.DBSituation;
import org.shiro.demo.service.IDBSituationService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.vo.DBSituationVO;
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
	@RequestMapping(value="/getLastDBsituation",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String getLastDBsituation(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			Integer page = Integer.parseInt(paramsMap.get("page"));
			Integer pageSize = Integer.parseInt(paramsMap.get("pageSize"));
			//Pagination<DBPlan> dbPlanPagination = dbPlanService.getPagination(DBPlan.class, null, "order by dbplanid desc", page, pageSize);
			//Map<String, Object> dataMap = AppDBplanVO.changeDBPlan2APPDBPlanVO(dbPlanPagination);
			Map<String, Object> dataMap = dbsituationService.getLastDBsituation( page, pageSize);
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
	
	/**
	 * 获取中奖记录
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getDBsituationDetail",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String getDBsituationDetail(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			Long dbplanid = Long.parseLong(paramsMap.get("dbplanid"));
			String wechatid = paramsMap.get("wechatid");
			//Pagination<DBPlan> dbPlanPagination = dbPlanService.getPagination(DBPlan.class, null, "order by dbplanid desc", page, pageSize);
			//Map<String, Object> dataMap = AppDBplanVO.changeDBPlan2APPDBPlanVO(dbPlanPagination);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition(" customer.wechatid='"+wechatid+"'"));
			queryConditions.add(new QueryCondition(" dbPlan.dbplanid="+dbplanid));
			DBSituation dbSituation = (DBSituation) dbsituationService.getSingleResult(DBSituation.class, queryConditions);
			AppSituationVO appSituationVO = new AppSituationVO(dbSituation);
			returnData.setCode(ReturnData.SUCCESS);
			returnData.setMessage("成功");
			returnData.setData(FastJsonTool.createJsonString(appSituationVO));
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
	 * 修改领取状态
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/changeSituationTake",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String changeSituationTake(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			Long dbplanid = Long.parseLong(paramsMap.get("dbplanid"));
			String wechatid = paramsMap.get("wechatid");
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition(" customer.wechatid='"+wechatid+"'"));
			queryConditions.add(new QueryCondition(" dbPlan.dbplanid="+dbplanid));
			//Pagination<DBPlan> dbPlanPagination = dbPlanService.getPagination(DBPlan.class, null, "order by dbplanid desc", page, pageSize);
			//Map<String, Object> dataMap = AppDBplanVO.changeDBPlan2APPDBPlanVO(dbPlanPagination);
			List<DBSituation> list = dbsituationService.get(DBSituation.class, queryConditions );
			if(null==list||list.size()==0){
				returnData.setCode(ReturnData.FAIL);
				returnData.setMessage("失败");
				returnData.setData("");
			}else{
				DBSituation dbSituation = list.get(0);
				dbSituation.setIstake(1);
				dbsituationService.update(dbSituation);
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
}
