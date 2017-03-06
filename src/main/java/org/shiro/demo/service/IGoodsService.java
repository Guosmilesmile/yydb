package org.shiro.demo.service;

import org.shiro.demo.entity.Goods;


/**
 * 商品管理服务层
 * @author Christy
 *
 */
public interface IGoodsService extends IBaseService{

	/**
	 * 新增商品
	 * @param user
	 * @return
	 */
	public boolean insertGoods(Goods goods);
	
	/**
	 * 删除商品
	 * @param id 用户id
	 * @return
	 */
	public boolean deleteGoods(Long id);
	
	/**
	 * 更新用户
	 * @param user 用户
	 * @return
	 */
	public boolean updateGoods(Goods goods);
	
}
