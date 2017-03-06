package org.shiro.demo.service.impl;

import javax.annotation.Resource;

import org.shiro.demo.entity.Goods;
import org.shiro.demo.entity.Role;
import org.shiro.demo.entity.User;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.IGoodsService;
import org.shiro.demo.service.IRoleService;
import org.springframework.stereotype.Service;

@Service("goodsService")
public class GoodsServiceImpl extends DefultBaseService implements IGoodsService{

	@Resource(name="baseService")
	private IBaseService baseService;

	public boolean insertGoods(Goods goods) {
		boolean flag = false;
		try {
			baseService.save(goods);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteGoods(Long id) {
		boolean flag = false;
		try {
			baseService.delete(Goods.class, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateGoods(Goods goods) {
		boolean flag = false;
		try {
			baseService.update(goods);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}
