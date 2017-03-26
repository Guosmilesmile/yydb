package org.shiro.demo.service;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * 分页获取参与记录
	 * @param page
	 * @param pageSize
	 * @param wechatid
	 * @param isfinish 0、未完结 1、完结  2、全部
	 * @return
	 */
	public Map<String, Object> getDBAttendVOWithWechatid(int page, int pageSize,String wechatid,int isfinish);
}
