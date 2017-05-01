package org.shiro.demo.service;

import java.util.Map;

import org.shiro.demo.controller.app.vo.AppDBplanVO;
import org.shiro.demo.entity.DBPlan;


/**
 * 夺宝计划业务层
 * @author Christy
 *
 */
public interface IDBPlanService extends IBaseService{
	
	/**
	 * 根据字段排序分页获取夺宝计划
	 * @param page
	 * @param pageSize
	 * @param columnName 排序字段
	 * @param order 0位降序 1位升序
	 * @return
	 */
	public Map<String, Object> getDBPlanWithOrder(int page,int pageSize,String columnName,int order);
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
	
	/**
	 * 通过id获取夺宝计划
	 * @param id
	 * @return
	 */
	public AppDBplanVO getAppDBplanVObyId(Long id);
	
	/**
	 * 通过id获取已参加人数
	 * @param id
	 * @return
	 */
	public int getAttendNumber(Long id);
}
