package org.shiro.demo.controller.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.DBPlan;
import org.shiro.demo.entity.Goods;
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
	
	/**
	 * 分页获取所有夺宝计划信息
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/getpagedbsituation",method=RequestMethod.POST)
	@ResponseBody
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
	 * 新增夺宝计划
	 * @param rowstr 信息
	 * @return
	 */
	@RequestMapping(value = "/insertdbplan", method = RequestMethod.POST)
	@ResponseBody
	public String insertDBPlan(@RequestParam(value="goodsid")Long goodsid,@RequestParam(value="money")Double money,
			@RequestParam(value="number")Integer number,@RequestParam(value="split")Long split,
			@RequestParam(value="starttime")String starttimestr,@RequestParam(value="endtime")String endtimestr){
		String returnData = ReturnDataUtil.FAIL;
		try {
			System.out.println(starttimestr);
			System.out.println(endtimestr);
			Long starttime = TimeUtil.convert2Long(starttimestr, "yyyy-MM-dd HH:mm:ss")/1000;
			Long endtime = TimeUtil.convert2Long(endtimestr, "yyyy-MM-dd HH:mm:ss")/1000;
			Goods goods = goodsService.getById(Goods.class, goodsid);
			DBPlan dbPlan = new DBPlan(split, starttime, endtime, number, money, goods );
			boolean flag = dbplanService.insertDBPlan(dbPlan );
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	
	/**
	 * 更新
	 * @param rowstr 信息
	 * @return
	 */
	@RequestMapping(value = "/updatedbplan", method = RequestMethod.POST)
	@ResponseBody
	public String updateDBPlan(@RequestParam(value="dbplanid")Long dbplanid,@RequestParam(value="goodsid")Long goodsid,@RequestParam(value="money")Double money,
			@RequestParam(value="number")Integer number,@RequestParam(value="split")Long split,
			@RequestParam(value="starttime")String starttimestr,@RequestParam(value="endtime")String endtimestr){
		String returnData = ReturnDataUtil.FAIL;
		try {
			System.out.println(starttimestr);
			System.out.println(endtimestr);
			Long starttime = TimeUtil.convert2Long(starttimestr, "yyyy-MM-dd HH:mm:ss")/1000;
			Long endtime = TimeUtil.convert2Long(endtimestr, "yyyy-MM-dd HH:mm:ss")/1000;
			Goods goods = goodsService.getById(Goods.class, goodsid);
			DBPlan dbPlan = new DBPlan(dbplanid,split, starttime, endtime, number, money, goods );
			boolean flag = dbplanService.updateDBPlan(dbPlan );
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
	@RequestMapping(value = "/deletedbplan", method = RequestMethod.POST)
	@ResponseBody
	public String deleteDBPlan(@RequestParam(value="ids")Long id){
		String returnData = ReturnDataUtil.FAIL;
		try {
			dbplanService.deleteDBPlan(id);
			returnData = ReturnDataUtil.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
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
