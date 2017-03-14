package org.shiro.demo.controller.db;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.entity.DBAttend;
import org.shiro.demo.entity.DBPlan;
import org.shiro.demo.entity.Goods;
import org.shiro.demo.service.ICustomerService;
import org.shiro.demo.service.IDBAttendService;
import org.shiro.demo.service.IDBPlanService;
import org.shiro.demo.service.IGoodsService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.util.TimeUtil;
import org.shiro.demo.vo.DBAttendVO;
import org.shiro.demo.vo.UDBAttendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/dbattend")
public class DBAttendController {

	@Autowired
	private IDBAttendService dbAttendService;
	
	@Autowired
	private IGoodsService goodsService;
	
	@Autowired
	private IDBPlanService dbPlanService;
	
	@Autowired
	private ICustomerService customerService;
	/**
	 * 分页获取所有夺宝计划信息
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/getpagedbattend",method=RequestMethod.POST)
	@ResponseBody
	public String getDBAttendByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<DBAttend> DBAttendPagination = dbAttendService.getPagination(DBAttend.class, null, "order by attendid desc", page, pageSize);
		Map<String, Object> VOMap = DBAttendVO.changeDBAttend2DBAttendVO(DBAttendPagination);
		returnResult =  FastJsonTool.createJsonString(VOMap);
		return returnResult;
	}
	
	
	/**
	 * 新增夺宝计划
	 * @param rowstr 信息
	 * @return
	 */
	@RequestMapping(value = "/insertDBAttend", method = RequestMethod.POST)
	@ResponseBody
	public String insertDBAttend(@RequestParam(value="dbplanid")Long dbplanid,@RequestParam(value="customerid")Long customerid,
			@RequestParam(value="isplay")Integer isplay){
		String returnData = ReturnDataUtil.FAIL;
		try {
			Customer customer =  customerService.getById(Customer.class, customerid);
			DBPlan dbPlan = dbPlanService.getById(DBPlan.class, dbplanid);
			DBAttend DBAttend = new DBAttend(System.currentTimeMillis()/1000, isplay, customer, dbPlan);
			boolean flag = dbAttendService.insertDBAttend(DBAttend );
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
	@RequestMapping(value = "/updatedbattend", method = RequestMethod.POST)
	@ResponseBody
	public String updateDBAttend(@RequestParam(value="dbattendid")Long dbattendid,@RequestParam(value="dbplanid")Long dbplanid,@RequestParam(value="customerid")Long customerid,
			@RequestParam(value="isplay")Integer isplay){
		String returnData = ReturnDataUtil.FAIL;
		try {
			Customer customer =  customerService.getById(Customer.class, customerid);
			DBPlan dbPlan = dbPlanService.getById(DBPlan.class, dbplanid);
			DBAttend dbAttend = dbAttendService.getById(DBAttend.class, dbattendid);
			dbAttend.setCustomer(customer);
			dbAttend.setDbPlan(dbPlan);
			dbAttend.setIsplay(isplay);
			boolean flag = dbAttendService.updateDBAttend(dbAttend );
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
	@RequestMapping(value = "/deletedbattend", method = RequestMethod.POST)
	@ResponseBody
	public String deleteDBAttend(@RequestParam(value="ids")Long id){
		String returnData = ReturnDataUtil.FAIL;
		try {
			dbAttendService.deleteDBAttend(id);
			returnData = ReturnDataUtil.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	/**
	 * 根据id获取详细的夺宝计划
	 */
	@RequestMapping(value = "/getDBAttendwithid", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getDBAttendWithid(@RequestParam(value="id")Long id) {
		String returnResult = "";
		DBAttend DBAttend = dbAttendService.getById(DBAttend.class,id);
		returnResult = FastJsonTool.createJsonString(new UDBAttendVO(DBAttend));
		return returnResult;
	}
}
