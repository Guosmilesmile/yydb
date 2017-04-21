package org.shiro.demo.controller.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.shiro.demo.controller.app.ReturnData;
import org.shiro.demo.controller.app.exception.EncryptWrongExcetion;
import org.shiro.demo.controller.app.exception.ParamsWromgException;
import org.shiro.demo.controller.app.exception.TimeOutException;
import org.shiro.demo.controller.app.vo.AppDBplanVO;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.DBPlan;
import org.shiro.demo.service.IDBPlanService;
import org.shiro.demo.util.FastJsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 夺宝计划获取
 * @author Christy
 *
 */
@Controller
@RequestMapping(value="app/dbplan")
public class AppDBPlanController extends AppBaseController{

	@Autowired
	private IDBPlanService dbPlanService;
	
	/**
	 * 获取所有夺宝计划
	 * @param params
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
			String columnName = paramsMap.get("columnName");
			//Pagination<DBPlan> dbPlanPagination = dbPlanService.getPagination(DBPlan.class, null, "order by dbplanid desc", page, pageSize);
			//Map<String, Object> dataMap = AppDBplanVO.changeDBPlan2APPDBPlanVO(dbPlanPagination);
			Map<String, Object> dataMap = dbPlanService.getDBPlanWithOrder(page, pageSize, columnName);
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
	 * 根据价格区间以及类别获取所有夺宝计划
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getDBPlanPageWithCategory",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String getDBPlanPageWithCategory(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			Integer page = Integer.parseInt(paramsMap.get("page"));
			Integer pageSize = Integer.parseInt(paramsMap.get("pageSize"));
			Long categoryid = Long.parseLong(paramsMap.get("categoryid"));
			Integer block = Integer.parseInt(paramsMap.get("block"));
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			/*queryConditions.add(new QueryCondition("isfinish", QueryCondition.EQ, 0));
			queryConditions.add(new QueryCondition("goods.category.categoryid", QueryCondition.EQ, categoryid));
			queryConditions.add(new QueryCondition("block", QueryCondition.EQ, block));*/
			queryConditions.add(new QueryCondition(" goods.category.categoryid="+categoryid+" and block="+block+" and isfinish=0"));
			Pagination<DBPlan> pagination = dbPlanService.getPagination(DBPlan.class, queryConditions, "", page, pageSize);
			//List<DBPlan> list = dbPlanService.getPaginationJpql(page, pageSize, "from DBPlan as o where o.goods.categoryid=? and o.block=?", categoryid,block);
			//Pagination<DBPlan> dbPlanPagination = dbPlanService.getPagination(DBPlan.class, null, "order by dbplanid desc", page, pageSize);
			Map<String, Object> maps = AppDBplanVO.changeDBPlan2APPDBPlanVO(pagination);
			returnData.setCode(ReturnData.SUCCESS);
			returnData.setMessage("成功");
			returnData.setData(FastJsonTool.createJsonString(maps));
		} catch(EncryptWrongExcetion e){
			e.printStackTrace();
			returnData.setCode(ReturnData.FAIL);
			returnData.setMessage("接口数据加密有误");
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
	 * 获取夺宝计划详细信息
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getDBPlanbyid",method=RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String getDBPlanbyid(@RequestParam(value="params")String params){
		ReturnData returnData = new ReturnData();
		try {
			Map<String, String> paramsMap = filterParam(params);
			Long dbplanid = Long.parseLong(paramsMap.get("dbplanid"));
			AppDBplanVO appDBplanVO = dbPlanService.getAppDBplanVObyId(dbplanid);
			returnData.setCode(ReturnData.SUCCESS);
			returnData.setMessage("成功");
			returnData.setData(FastJsonTool.createJsonString(appDBplanVO));
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
