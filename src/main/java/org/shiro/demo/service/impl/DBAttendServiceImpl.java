package org.shiro.demo.service.impl;

import javax.annotation.Resource;

import org.shiro.demo.entity.DBAttend;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IDBAttendService;
import org.springframework.stereotype.Service;

@Service("dbattendService")
public class DBAttendServiceImpl extends DefultBaseService implements IDBAttendService{

	@Resource(name="baseService")
	private IBaseService baseService;

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
	
}
