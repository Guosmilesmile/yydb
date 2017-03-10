package org.shiro.demo.service.impl;

import java.util.List;

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


	public Long insertGoodsReturnId(Goods goods) {
		Long flag = new Long(0);
		try {
			List<Object> executeBySQLList = baseService.executeBySQLList("call p_insertgoods('"+goods.getName()+"',"
		+goods.getCategory().getCategoryid()+","
		+goods.getShop().getCustomerid()+",'"+goods.getSummary()+"',@id);");
			System.out.println();
			flag = new Long(executeBySQLList.get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateGoods(Long goodsid, String imgurl) {
		boolean flag = false;
		try {
			imgurl+=";";
			baseService.executeBySQL("update g_goods set imgurls =  concat(imgurls ,?)  where goodsid = ?",imgurl,goodsid);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteGoodsImgurl(Long id,String imgurl) {
		boolean flag = false;
		try {
			Goods goods = baseService.getById(Goods.class, id);
			String imgurls = goods.getImgurls();
			String newImgurls = imgurls.replaceFirst(imgurl+";", "");
			goods.setImgurls(newImgurls);
			baseService.update(goods);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
}
