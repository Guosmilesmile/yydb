package org.shiro.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bouncycastle.jcajce.provider.asymmetric.dsa.DSASigner.noneDSA;
import org.shiro.demo.controller.app.vo.AppAttendVO;
import org.shiro.demo.controller.app.vo.AppDBplanVO;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.entity.DBAttend;
import org.shiro.demo.entity.DBPlan;
import org.shiro.demo.entity.DBSituation;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IDBAttendService;
import org.shiro.demo.service.IDBPlanService;
import org.shiro.demo.util.FastJsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dbattendService")
public class DBAttendServiceImpl extends DefultBaseService implements IDBAttendService{

	@Resource(name="baseService")
	private IBaseService baseService;
	
	@Autowired
	private IDBPlanService dbPlanService;

	public boolean insertDBAttend(DBAttend dbAttend) {
		boolean flag = false;
		try {
			baseService.save(dbAttend);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteDBAttend(Long id) {
		boolean flag = false;
		try {
			baseService.delete(DBAttend.class, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateDBAttend(DBAttend dbAttend) {
		boolean flag = false;
		try {
			baseService.update(dbAttend);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public Map<String, Object> getDBAttendVOWithWechatid(int page,int pageSize, String wechatid,int isfinish) {
		String jpql = "";
		List<Object[]> paginationJpql = null;
		Integer total = 0;
		if(isfinish==2){
			jpql = "select count(o.customer.wechatid),o from DBAttend o where customer.wechatid=? group by o.dbPlan.dbplanid  order by attendid desc";
			paginationJpql = baseService.getPaginationJpql(page, pageSize, jpql, wechatid);
			total = baseService.getByJpql(jpql, wechatid).size();
		}else{
			jpql = "select count(o.customer.wechatid),o from DBAttend o where customer.wechatid=? and o.dbPlan.isfinish=?   group by o.dbPlan.dbplanid  order by attendid desc";
			paginationJpql = baseService.getPaginationJpql(page, pageSize, jpql, wechatid,isfinish);
			total = baseService.getByJpql(jpql, wechatid,isfinish).size();
		}
		List<AppAttendVO> appAttendVOs = new ArrayList<AppAttendVO>();
		for(Object item[] : paginationJpql){
			DBAttend dbAttend = (DBAttend) item[1];
			AppAttendVO appAttendVO = new AppAttendVO(dbAttend);
			appAttendVO.setSelfCount(Integer.parseInt(item[0]+""));
			appAttendVOs.add(appAttendVO);
		}
		List<AppAttendVO> returnappAttendVOs = new ArrayList<AppAttendVO>();
		for(AppAttendVO appAttendVO : appAttendVOs){
			AppDBplanVO appDBplanVO = dbPlanService.getAppDBplanVObyId(appAttendVO.getDbplanid());
			appAttendVO.setAttendNumber(appDBplanVO.getAttendNumber());
			
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("dbPlan.dbplanid="+appAttendVO.getDbplanid()));
			List<DBSituation> dbSituations = baseService.get(DBSituation.class, queryConditions );
			Customer customer = dbSituations.get(0).getCustomer();
			if(null==customer){
				appAttendVO.setLuckdogWechatid("");
			}else{
				appAttendVO.setLuckdogWechatid(customer.getWechatid());
			}
			returnappAttendVOs.add(appAttendVO);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("rows", returnappAttendVOs);
		returnMap.put("total",total);
		return returnMap;
	}
	
}
