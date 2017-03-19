package org.shiro.demo.controller.db;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.entity.DBPlan;
import org.shiro.demo.entity.Goods;
import org.shiro.demo.service.IDBPlanService;
import org.shiro.demo.service.IGoodsService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.util.TimeUtil;
import org.shiro.demo.vo.CustomerVO;
import org.shiro.demo.vo.DBPlanVO;
import org.shiro.demo.vo.UDBPlanVO;
import org.shiro.demo.vo.UGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/dbplan")
public class DBPlanController {

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
	@RequestMapping(value = "/getpagedbplan",method=RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"db:dbplan"}, logical = Logical.OR)
	public String getDBPlanByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<DBPlan> dbPlanPagination = dbplanService.getPagination(DBPlan.class, null, "order by dbplanid desc", page, pageSize);
		Map<String, Object> VOMap = DBPlanVO.changeDBPlan2DBPlanVO(dbPlanPagination);
		returnResult =  FastJsonTool.createJsonString(VOMap);
		return returnResult;
	}
	
	
	/**
	 * 新增夺宝计划
	 * @param rowstr 信息
	 * @return
	 */
	@RequestMapping(value = "/insertdbplan", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"db:dbplan"}, logical = Logical.OR)
	public String insertDBPlan(@RequestParam(value="goodsid")Long goodsid,@RequestParam(value="money")Double money,
			@RequestParam(value="split")Long split,
			@RequestParam(value="starttime")String starttimestr,@RequestParam(value="endtime")String endtimestr){
		String returnData = ReturnDataUtil.FAIL;
		try {
			System.out.println(starttimestr);
			System.out.println(endtimestr);
			Long starttime = TimeUtil.convert2Long(starttimestr, "yyyy-MM-dd HH:mm:ss")/1000;
			Long endtime = TimeUtil.convert2Long(endtimestr, "yyyy-MM-dd HH:mm:ss")/1000;
			Goods goods = goodsService.getById(Goods.class, goodsid);
			Integer number = (int) Math.ceil(money / split);
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
	@RequiresPermissions(value = {"db:dbplan"}, logical = Logical.OR)
	public String updateDBPlan(@RequestParam(value="dbplanid")Long dbplanid,@RequestParam(value="goodsid")Long goodsid,@RequestParam(value="money")Double money,
			@RequestParam(value="split")Long split,
			@RequestParam(value="starttime")String starttimestr,@RequestParam(value="endtime")String endtimestr){
		String returnData = ReturnDataUtil.FAIL;
		try {
			System.out.println(starttimestr);
			System.out.println(endtimestr);
			Long starttime = TimeUtil.convert2Long(starttimestr, "yyyy-MM-dd HH:mm:ss")/1000;
			Long endtime = TimeUtil.convert2Long(endtimestr, "yyyy-MM-dd HH:mm:ss")/1000;
			Goods goods = goodsService.getById(Goods.class, goodsid);
			Integer number = (int) Math.ceil(money / split);
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
	@RequiresPermissions(value = {"db:dbplan"}, logical = Logical.OR)
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
	
	/**
	 * 根据id获取详细的夺宝计划
	 */
	@RequestMapping(value = "/getdbplanwithid", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	@RequiresPermissions(value = {"db:dbplan"}, logical = Logical.OR)
	public String getdbplanWithid(@RequestParam(value="id")Long id) {
		String returnResult = "";
		DBPlan dbPlan = dbplanService.getById(DBPlan.class,id);
		returnResult = FastJsonTool.createJsonString(new UDBPlanVO(dbPlan));
		return returnResult;
	}
	
	
	/**
	 * 获取所有夺宝计划
	 */
	@RequestMapping(value = "/getalldbplan", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	@RequiresPermissions(value = {"db:dbplan","db:dbattend"}, logical = Logical.OR)
	public String getAllDBPlan() {
		String returnResult = "";
		List<DBPlan> dbPlans = dbplanService.getAll(DBPlan.class);
		List<DBPlanVO> dbPlanVOs  = DBPlanVO.changeDBPlan2DBPlanVO(dbPlans);
		returnResult = FastJsonTool.createJsonString(dbPlanVOs);
		return returnResult;
	}
}
