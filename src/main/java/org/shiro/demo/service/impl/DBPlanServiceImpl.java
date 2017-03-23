package org.shiro.demo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.shiro.demo.controller.app.vo.AppDBplanVO;
import org.shiro.demo.entity.DBPlan;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IDBPlanService;
import org.shiro.demo.vo.DBPlanVO;
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

	public int getAttendNumber(Long id){
		try {
			List<Object> executeBySQLList = baseService.executeBySQLList("select count(attendid) from db_dbattend where dbplanid = ?", id);
			int data = Integer.parseInt(executeBySQLList.get(0).toString());
			return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public AppDBplanVO getAppDBplanVObyId(Long id) {
		AppDBplanVO appDBplanVO = null;
		try {
			DBPlan dbPlan = baseService.getById(DBPlan.class, id);
			appDBplanVO = new AppDBplanVO(dbPlan);
			int attendNumber = getAttendNumber(id);
			appDBplanVO.setAttendNumber(attendNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return appDBplanVO;
	}
	
}
