package org.shiro.demo.controller.customer;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.service.ICustomerService;
import org.shiro.demo.service.IRoleService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.vo.CustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/customer")

public class CustomerController {

	@Autowired
	private ICustomerService customerService;
	
	/**
	 * 分页获取所有客户信息
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequiresPermissions(value = {"customer:manage"}, logical = Logical.OR)
	@RequestMapping(value = "/getpagecustomer",method=RequestMethod.POST)
	@ResponseBody
	public String GetCustomerByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<Customer> rolePagination = customerService.getPagination(Customer.class, null, null, page, pageSize);
		Map<String, Object> customerVOMap = CustomerVO.changeCustomer2CustomerVO(rolePagination);
		returnResult =  FastJsonTool.createJsonString(customerVOMap);
		return returnResult;
	}
	
	/**
	 * 获取所有客户
	 */
	@RequestMapping(value = "/getallcustomer", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	@RequiresPermissions(value = {"customer:manage","db:dbattend","db:dbsituation"}, logical = Logical.OR)
	public String getAllCustomer() {
		String returnResult = "";
		List<Customer> customers = customerService.getAll(Customer.class);
		List<CustomerVO> allCustomerVOs  = CustomerVO.changeCustomer2CustomerVO(customers);
		returnResult = FastJsonTool.createJsonString(allCustomerVOs);
		return returnResult;
	}
	
	/**
	 * 获取所有商家
	 */
	@RequestMapping(value = "/getallshop", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	@RequiresPermissions(value = {"customer:manage","goods:manage"}, logical = Logical.OR)
	public String getAllShop() {
		String returnResult = "";
		List<Customer> customers = customerService.getAllShop();
		List<CustomerVO> allCustomerVOs  = CustomerVO.changeCustomer2CustomerVO(customers);
		returnResult = FastJsonTool.createJsonString(allCustomerVOs);
		return returnResult;
	}
	
	/**
	 * 新增客户
	 * @param rowstr 客户信息
	 * @return
	 */
	@RequestMapping(value = "/insertcustomer", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"customer:manage"}, logical = Logical.OR)
	public String insertRole(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			String wechatid  = jsonObject.getString("wechatid");
			Double balance = jsonObject.getDouble("balance");
			Integer isshop = jsonObject.getInt("isshop");
			String address = jsonObject.getString("address");
			String phoneTemp =  jsonObject.getString("phone");
			String name = jsonObject.getString("name");
			String wxname = jsonObject.getString("wxname");
			String wxavatar = jsonObject.getString("wxavatar");
			Long phone = null ; 
			if("".equals(phoneTemp) || null == phoneTemp){
				phone = new Long(0);
			}else{
				phone = Long.valueOf(phoneTemp);
			}
			if(0==isshop){
				address = "";
				phone = new Long(0);
				name = "";
			}
			Customer customer = new Customer(wechatid, balance, isshop, address, phone,name,wxname,wxavatar);
			boolean flag = customerService.insertCustomer(customer);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	
	/**
	 * 更新客户信息
	 * @param rowstr 客户信息
	 * @return
	 */
	@RequestMapping(value = "/updatecustomer", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"customer:manage"}, logical = Logical.OR)
	public String updateRole(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			Long customerid = jsonObject.getLong("customerid");
			String wechatid  = jsonObject.getString("wechatid");
			Double balance = jsonObject.getDouble("balance");
			Integer isshop = jsonObject.getInt("isshop");
			String address = jsonObject.getString("address");
			String phoneTemp = jsonObject.getString("phone");
			String name = jsonObject.getString("name");
			String wxname = jsonObject.getString("wxname");
			String wxavatar = jsonObject.getString("wxavatar");
			Long phone = 0l;
			if(0==isshop){
				address = "";
				phone = new Long(0);
				name = "";
			}
			if("".equals(phoneTemp) || null == phoneTemp){
				phone = new Long(0);
			}else{
				phone = Long.valueOf(phoneTemp);
			}
			Customer customer = new Customer(customerid,wechatid, balance, isshop, address, phone,name,wxname,wxavatar);
			boolean flag = customerService.updateCustomer(customer);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	/**
	 * 删除客户
	 * @param ids 客户id
	 * @return
	 */
	@RequestMapping(value = "/deletecustomer", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"customer:manage"}, logical = Logical.OR)
	public String deleteRole(@RequestParam(value="ids")Long id){
		String returnData = ReturnDataUtil.FAIL;
		try {
			customerService.deleteCustomer(id);
			returnData = ReturnDataUtil.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
}
