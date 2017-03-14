package org.shiro.demo.vo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.DBAttend;

/**
 * 夺宝计划参与情况显示层
 * @author Christy
 *
 */
public class UDBAttendVO {


	private Long id;//id
	
	private Long createTime;//创建时间
	
	private Integer isplay;//是否付款
	
	private Long customerid;//顾客id
	
	private Long dbplanid;//计划id
	


	public UDBAttendVO() {
		super();
	}


	public UDBAttendVO(Long id, Long createTime, Integer isplay,
			Long customerid, Long dbplanid) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.isplay = isplay;
		this.customerid = customerid;
		this.dbplanid = dbplanid;
	}


	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}



	public Integer getIsplay() {
		return isplay;
	}



	public void setIsplay(Integer isplay) {
		this.isplay = isplay;
	}



	public Long getCustomerid() {
		return customerid;
	}



	public void setCustomerid(Long customerid) {
		this.customerid = customerid;
	}



	public UDBAttendVO(DBAttend dbAttend) {
		super();
		this.id = dbAttend.getAttendid();
		this.createTime = dbAttend.getCreateTime();
		this.isplay = dbAttend.getIsplay();
		this.customerid = dbAttend.getCustomer().getCustomerid();
		this.dbplanid = dbAttend.getDbPlan().getDbplanid();
	}


	public Long getDbplanid() {
		return dbplanid;
	}






	public void setDbplanid(Long dbplanid) {
		this.dbplanid = dbplanid;
	}
	
	

}
