package org.shiro.demo.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.Instanceof;

import javax.annotation.Resource;

import org.shiro.demo.controller.app.vo.AppAttendVO;
import org.shiro.demo.controller.app.vo.AppDBplanVO;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.entity.DBAttend;
import org.shiro.demo.entity.DBPlan;
import org.shiro.demo.entity.DBSituation;
import org.shiro.demo.entity.Goods;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IDBPlanService;
import org.shiro.demo.service.IDBSituationService;
import org.shiro.demo.vo.DBSituationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dbsituationService")
public class DBSituationServiceImpl extends DefultBaseService implements IDBSituationService{

	@Resource(name="baseService")
	private IBaseService baseService;

	@Autowired
	private IDBPlanService dbPlanService;
	
	public boolean insertDBSituation(DBSituation dbSituation) {
		/*boolean flag = false;
		try {
			baseService.save(dbSituation);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;*/
		boolean flag = false;
		Long returndata = new Long(0);
		try {
			String sql = "call p_insertsituation("+dbSituation.getDbPlan().getDbplanid()+","
					+dbSituation.getCustomer().getCustomerid()+","+dbSituation.getIstake()+")";
			System.out.println(sql);
			List<Object> executeBySQLList = baseService.executeBySQLList(sql);
			returndata = new Long(executeBySQLList.get(0).toString());
			if(1==returndata){
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteDBSituation(Long id) {
		boolean flag = false;
		try {
			baseService.delete(DBSituation.class, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateDBSituation(DBSituation dbSituation) {
		boolean flag = false;
		try {
			baseService.update(dbSituation);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public List<DBSituationVO> getDBSituation(Integer page, Integer pageSize) {
		int start = (page-1)*pageSize;
		int end = page*pageSize;
		List<DBSituationVO> returnList = new ArrayList<DBSituationVO>();
		List<Object> list = baseService.executeBySQLList("select b.situationid,g.`name`,s.`name` as shopname,a.dbplanid from db_dbplan as a LEFT JOIN db_situation as b on a.dbplanid = b.dbplanid left join g_goods as g on a.goodsid = g.goodsid  left join c_customer as s on s.customerid = g.shopid GROUP BY a.dbplanid order by a.dbplanid limit "+start+","+end);
		if(null==list || list.size()==0){
			
		}else{
			for(Object object : list){
				Object os[] = (Object[]) object;
				BigInteger temp = (BigInteger)os[0];
				Long dbsituationid = 0l;
				Integer situation = 0;
				if(null==temp){
					situation = 0;
				}else{
					situation = 1;
					dbsituationid = Long.parseLong(temp+"");
				}
				
				BigInteger dbplanidtemp = (BigInteger)os[3];
				Long dbplanid = Long.parseLong(dbplanidtemp+"");
				returnList.add(new DBSituationVO(dbsituationid,dbplanid, (String)os[1],(String)os[2],"",0,situation));
			}
		}
		for(int i=0;i<returnList.size();i++){
			DBSituationVO dbSituationVO = returnList.get(i);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition("dbPlan.dbplanid="+dbSituationVO.getDbplanid()));
			List<DBSituation> dbSituations = baseService.get(DBSituation.class, queryConditions );
			if(dbSituations.size()==0){//进行中
				dbSituationVO.setSituation(2);
				returnList.set(i, dbSituationVO);
			}else if(dbSituations.size()!=0){
				if(null == dbSituations.get(0).getCustomer()){//流标
					dbSituationVO.setSituation(0);
					returnList.set(i, dbSituationVO);
				}
			}
		}
		return returnList;
	}

	
	public  int  getDBSituationCount(){
		List<Object> list = baseService.executeBySQLList("select b.situationid,g.`name`,s.`name` as shopname from db_dbplan as a LEFT JOIN db_situation as b on a.dbplanid = b.dbplanid left join g_goods as g on a.goodsid = g.goodsid left join c_customer as s on s.customerid = g.shopid GROUP BY a.dbplanid order by a.dbplanid");
		int number = 0;
		if(null==list){
			
		}else{
			number = list.size();
		}
		return number;
	}
	
	public Map<String, Object> getDBsituationWithWechatid(int page,int pageSize, String wechatid) {
		String jpql = "";
		Integer total = 0;
		
		jpql = "select o.dbPlan from DBSituation o where o.customer.wechatid=? order by situationid desc";
		List<DBPlan> dbplans = baseService.getPaginationJpql(page, pageSize, jpql, wechatid);
		total = baseService.getByJpql(jpql, wechatid).size();
		
		List<AppAttendVO> appAttendVOs = new ArrayList<AppAttendVO>();
		for(DBPlan dbPlan : dbplans){
			AppAttendVO appAttendVO = new AppAttendVO(dbPlan);
			appAttendVO.setLuckdogWechatid(wechatid);
			appAttendVOs.add(appAttendVO);
		}
		List<AppAttendVO> returnappAttendVOs = new ArrayList<AppAttendVO>();
		for(AppAttendVO appAttendVO : appAttendVOs){
			int attendNumber = dbPlanService.getAttendNumber(appAttendVO.getDbplanid());
			appAttendVO.setAttendNumber(attendNumber);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition(" customer.wechatid ='"+wechatid+"' and dbPlan.dbplanid="+appAttendVO.getDbplanid()));
			List<DBAttend> list = baseService.get(DBAttend.class, queryConditions );
			appAttendVO.setSelfCount(list.size());
			returnappAttendVOs.add(appAttendVO);
		}
		for(int i =0;i<returnappAttendVOs.size();i++){
			AppAttendVO appAttendVO = returnappAttendVOs.get(i);
			List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition(" dbPlan.dbplanid="+appAttendVO.getDbplanid()));
			List<DBSituation> list = baseService.get(DBSituation.class, queryConditions );
			if(list.size()!=0){
				appAttendVO.setIstake(list.get(0).getIstake());
			}
			returnappAttendVOs.set(i, appAttendVO);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("rows", returnappAttendVOs);
		returnMap.put("total",total);
		return returnMap;
	}
	
	public Map<String, Object> getLastDBsituation(int page,int pageSize) {
		String jpql = "";
		Integer total = 0;
		
		jpql = "select o from DBSituation o  where o.customer is not null order by situationid desc";
		List<DBSituation> dbSituations = baseService.getPaginationJpql(page, pageSize, jpql);
		total = baseService.getByJpql(jpql).size();
		
		List<AppAttendVO> appAttendVOs = new ArrayList<AppAttendVO>();
		for(DBSituation dbSituation : dbSituations){
			DBPlan dbPlan = dbSituation.getDbPlan();
			AppAttendVO appAttendVO = new AppAttendVO(dbPlan);
			appAttendVO.setLuckdogWechatid(dbSituation.getCustomer().getWechatid());
			appAttendVOs.add(appAttendVO);
		}
		List<AppAttendVO> returnappAttendVOs = new ArrayList<AppAttendVO>();
		for(AppAttendVO appAttendVO : appAttendVOs){
			int attendNumber = dbPlanService.getAttendNumber(appAttendVO.getDbplanid());
			appAttendVO.setAttendNumber(attendNumber);
			/*List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
			queryConditions.add(new QueryCondition(" customer.wechatid ='"+wechatid+"' and dbPlan.dbplanid="+appAttendVO.getDbplanid()));
			List<DBAttend> list = baseService.get(DBAttend.class, queryConditions );
			appAttendVO.setSelfCount(list.size());*/
			returnappAttendVOs.add(appAttendVO);
		}
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("rows", returnappAttendVOs);
		returnMap.put("total",total);
		return returnMap;
	}
}
