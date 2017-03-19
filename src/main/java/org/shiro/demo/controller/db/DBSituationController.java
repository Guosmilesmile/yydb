package org.shiro.demo.controller.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.entity.DBAttend;
import org.shiro.demo.entity.DBPlan;
import org.shiro.demo.entity.DBSituation;
import org.shiro.demo.entity.Goods;
import org.shiro.demo.entity.Role;
import org.shiro.demo.service.ICustomerService;
import org.shiro.demo.service.IDBPlanService;
import org.shiro.demo.service.IDBSituationService;
import org.shiro.demo.service.IGoodsService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.util.TimeUtil;
import org.shiro.demo.vo.DBSituationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/dbsituation")
public class DBSituationController {

	
	@Autowired
	private IDBSituationService dbSituationService;
	
	@Autowired
	private IDBPlanService dbplanService;
	
	@Autowired
	private IGoodsService goodsService;
	
	@Autowired
	private ICustomerService customerService;
	
	/**
	 * 分页获取所有夺宝计划信息
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/getpagedbsituation",method=RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"db:dbsituation"}, logical = Logical.OR)
	public String getDBSituationByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		List<DBSituationVO> dbSituations = dbSituationService.getDBSituation(page, pageSize);
		int dbSituationCount = dbSituationService.getDBSituationCount();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", dbSituations);
		map.put("total", dbSituationCount);
		returnResult =  FastJsonTool.createJsonString(map);
		return returnResult;
	}
	
	
	/**
	 * 新增
	 * @param rowstr 信息
	 * @return
	 */
	@RequestMapping(value = "/insertdbsituation", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"db:dbsituation"}, logical = Logical.OR)
	public String insertDBSituation(@RequestParam(value="dbplanid")Long dbplanid,@RequestParam(value="customerid")Long customerid,
			@RequestParam(value="istake")Integer istake){
		String returnData = ReturnDataUtil.FAIL;
		try {
			DBPlan dbPlan = dbplanService.getById(DBPlan.class, dbplanid);
			Customer customer = customerService.getById(Customer.class, customerid);
			DBSituation dbSituation = new DBSituation(istake, dbPlan , customer );
			boolean flag = dbSituationService.insertDBSituation(dbSituation);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return returnData;
	}
	
	
	/**
	 * 更新
	 * @param rowstr 信息
	 * @return
	 */
	@RequestMapping(value = "/updatedbsituation", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"db:dbsituation"}, logical = Logical.OR)
	public String updateDBSituation(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			Long id = jsonObject.getLong("id");
			Integer istake = jsonObject.getInt("istake");
			DBSituation dbSituation = dbSituationService.getById(DBSituation.class, id);
			dbSituation.setIstake(istake);
			boolean flag = dbSituationService.updateDBSituation(dbSituation);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	/**
	 * 删除
	 * @param ids id
	 * @return
	 */
	@RequestMapping(value = "/deletedbsituation", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"db:dbsituation"}, logical = Logical.OR)
	public String deleteDBSituation(@RequestParam(value="ids")Long id){
		String returnData = ReturnDataUtil.FAIL;
		try {
			dbSituationService.deleteDBSituation(id);
			returnData = ReturnDataUtil.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	
	/**
	 * 根据id获取详细的夺宝计划
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getdbsituationWithdbplanid", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	@RequiresPermissions(value = {"db:dbsituation"}, logical = Logical.OR)
	public String getdbsituationWithdbplanid(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize,@RequestParam(value="dbplanid")Long dbplanid) {
		String returnResult = "";
		List<DBSituation> list = dbSituationService.getPaginationJpql(page, pageSize, "from DBSituation as s where s.dbPlan.dbplanid = ?", dbplanid);
		int number = dbSituationService.getByJpql("from DBSituation as s where s.dbPlan.dbplanid = ?", dbplanid).size();
		List<DBSituationVO> returnlist = new ArrayList<DBSituationVO>();
		for(DBSituation item : list ){
			DBSituationVO dbSituationVO = new DBSituationVO();
			dbSituationVO.setId(item.getSituationid());
			dbSituationVO.setWechatid(item.getCustomer().getWechatid());
			dbSituationVO.setIstake(item.getIstake());
			returnlist.add(dbSituationVO);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", returnlist);
		map.put("total", number);
		returnResult = FastJsonTool.createJsonString(map);
		return returnResult;
	}
		 
	/*
	*//**
	 * 根据id获取详细的夺宝计划
	 *//*
	@RequestMapping(value = "/getdbplanwithid", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getdbplanWithid(@RequestParam(value="id")Long id) {
		String returnResult = "";
		DBPlan dbPlan = dbplanService.getById(DBPlan.class,id);
		returnResult = FastJsonTool.createJsonString(new UDBPlanVO(dbPlan));
		return returnResult;
	}
	
	
	*//**
	 * 获取所有客户
	 *//*
	@RequestMapping(value = "/getalldbplan", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getAllCustomer() {
		String returnResult = "";
		List<DBPlan> dbPlans = dbplanService.getAll(DBPlan.class);
		List<DBPlanVO> dbPlanVOs  = DBPlanVO.changeDBPlan2DBPlanVO(dbPlans);
		returnResult = FastJsonTool.createJsonString(dbPlanVOs);
		return returnResult;
	}*/
}
