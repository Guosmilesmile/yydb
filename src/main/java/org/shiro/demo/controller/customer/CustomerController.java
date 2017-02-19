package org.shiro.demo.controller.customer;

import java.util.List;
import java.util.Map;

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
	 * 分页获取所有用户信息
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/systemgetpagecustomer",method=RequestMethod.POST)
	@ResponseBody
	public String systemGetCustomerByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<Customer> rolePagination = customerService.getPagination(Customer.class, null, null, page, pageSize);
		Map<String, Object> roleVOMap = CustomerVO.changeCustomer2CustomerVO(rolePagination);
		returnResult =  FastJsonTool.createJsonString(roleVOMap);
		return returnResult;
	}
	
	/**
	 * 获取所有客户
	 */
	@RequestMapping(value = "/systemgetallcustomer", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String systemGetAllCustomer() {
		String returnResult = "";
		List<Customer> customers = customerService.getAll(Customer.class);
		List<CustomerVO> allCustomerVOs  = CustomerVO.changeCustomer2CustomerVO(customers);
		returnResult = FastJsonTool.createJsonString(allCustomerVOs);
		return returnResult;
	}
	/**
	 * 新增客户
	 * @param rowstr 客户信息
	 * @return
	 */
	@RequestMapping(value = "/systeminsertcustomer", method = RequestMethod.POST)
	@ResponseBody
	public String systemInsertRole(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			String wechatid  = jsonObject.getString("wechatid");
			Double balance = jsonObject.getDouble("balance");
			Customer customer = new Customer(wechatid, balance);
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
	@RequestMapping(value = "/systemupdatecustomer", method = RequestMethod.POST)
	@ResponseBody
	public String systemUpdateRole(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			Long customerid = jsonObject.getLong("customerid");
			String wechatid  = jsonObject.getString("wechatid");
			Double balance = jsonObject.getDouble("balance");
			Customer customer = new Customer(customerid,wechatid, balance);
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
	@RequestMapping(value = "/systemdeletecustomer", method = RequestMethod.POST)
	@ResponseBody
	public String systemDeleteRole(@RequestParam(value="ids")Long id){
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
