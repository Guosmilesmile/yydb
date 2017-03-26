package org.shiro.demo.service;

import java.util.List;
import java.util.Map;

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
	
	/**
	 * 通过微信id获取中奖记录
	 * @param page
	 * @param pageSize
	 * @param wechatid
	 * @return
	 */
	public Map<String, Object> getDBsituationWithWechatid(int page,int pageSize, String wechatid) ;
}
