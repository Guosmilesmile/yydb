package org.shiro.demo.service;

import org.shiro.demo.entity.DBAttend;



/**
 * 夺宝参与情况业务层
 * @author Christy
 *
 */
public interface IDBAttendService extends IBaseService{
	
	/**
	 * 新增夺宝参与情况
	 * @return
	 */
	public boolean insertDBAttend(DBAttend dbAttend);
	
	/**
	 * 删除夺宝参与情况
	 * @param id 用户id
	 * @return
	 */
	public boolean deleteDBAttend(Long id);
	
	/**
	 * 更新夺宝参与情况
	 * @return
	 */
	public boolean updateDBAttend(DBAttend DBAttend);
}
