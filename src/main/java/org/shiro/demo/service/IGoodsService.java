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
	 * 新增商品,返回id
	 * @param user
	 * @return
	 */
	public Long insertGoodsReturnId(Goods goods);
	
	/**
	 * 删除商品
	 * @param id 用户id
	 * @return
	 */
	public boolean deleteGoods(Long id);
	
	/**
	 * 更新商品
	 * @param goods 商品
	 * @return
	 */
	public boolean updateGoods(Goods goods);
	
	/**
	 * 更新商品图片
	 * @param goodsid 商品id
	 * @param imgurl 图片地址
	 * @return
	 */
	public boolean updateGoods(Long goodsid,String imgurl);

	
	/**
	 * 删除图片
	 * @param id 
	 * @param imgurl
	 * @return
	 */
	public boolean deleteGoodsImgurl(Long id,String imgurl);
	
}
