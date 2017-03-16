package org.shiro.demo.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javassist.expr.Instanceof;

import javax.annotation.Resource;

import org.shiro.demo.entity.DBSituation;
import org.shiro.demo.entity.Goods;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IDBSituationService;
import org.shiro.demo.vo.DBSituationVO;
import org.springframework.stereotype.Service;

@Service("dbsituationService")
public class DBSituationServiceImpl extends DefultBaseService implements IDBSituationService{

	@Resource(name="baseService")
	private IBaseService baseService;

	
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
}
