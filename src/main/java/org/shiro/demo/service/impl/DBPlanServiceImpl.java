package org.shiro.demo.service.impl;

import javax.annotation.Resource;

import org.shiro.demo.entity.DBPlan;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IDBPlanService;
import org.springframework.stereotype.Service;

@Service("dbplanService")
public class DBPlanServiceImpl extends DefultBaseService implements IDBPlanService{

	@Resource(name="baseService")
	private IBaseService baseService;

	public boolean insertDBPlan(DBPlan dbPlan) {
		boolean flag = false;
		try {
			baseService.save(dbPlan);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteDBPlan(Long id) {
		boolean flag = false;
		try {
			baseService.delete(DBPlan.class, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateDBPlan(DBPlan dbPlan) {
		boolean flag = false;
		try {
			baseService.update(dbPlan);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}
