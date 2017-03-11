package org.shiro.demo.service;

import org.shiro.demo.entity.DBPlan;


/**
 * 夺宝计划业务层
 * @author Christy
 *
 */
public interface IDBPlanService extends IBaseService{
	
	/**
	 * 新增夺宝计划
	 * @param user
	 * @return
	 */
	public boolean insertDBPlan(DBPlan dbPlan);
	
	/**
	 * 删除夺宝计划
	 * @param id 用户id
	 * @return
	 */
	public boolean deleteDBPlan(Long id);
	
	/**
	 * 更新夺宝计划
	 * @param user 用户
	 * @return
	 */
	public boolean updateDBPlan(DBPlan dbPlan);
}
