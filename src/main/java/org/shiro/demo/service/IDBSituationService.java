package org.shiro.demo.service;

import java.util.List;

import org.shiro.demo.entity.DBSituation;
import org.shiro.demo.vo.DBSituationVO;


/**
 * 夺宝情况业务层
 * @author Christy
 *
 */
public interface IDBSituationService extends IBaseService{
	
	/**
	 * 获取
	 * @return
	 */
	public  List<DBSituationVO>  getDBSituation(Integer page , Integer pageSize);
	
	/**
	 * 获取
	 * @return
	 */
	public  int  getDBSituationCount();
	
	
	/**
	 * 新增
	 * @return
	 */
	public boolean insertDBSituation(DBSituation dbSituation);
	
	/**
	 * 删除
	 * @param id 用户id
	 * @return
	 */
	public boolean deleteDBSituation(Long id);
	
	/**
	 * 更新
	 * @return
	 */
	public boolean updateDBSituation(DBSituation dbSituation);
}
