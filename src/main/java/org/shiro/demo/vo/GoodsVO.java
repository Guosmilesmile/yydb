package org.shiro.demo.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Goods;

/**
 * 商品显示层
 * @author Christy
 *
 */
public class GoodsVO {

	private Long id;
	
	private String name;//商品名称
	
	private String imgurls;//图片地址，；分隔
	
	private String categoryName;//分类名称
	
	private String shopwechatid;//商店微信id
	
	private String shopName;//商店名称

	private String summary;//概要

	public GoodsVO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImgurls() {
		return imgurls;
	}

	public void setImgurls(String imgurls) {
		this.imgurls = imgurls;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getShopwechatid() {
		return shopwechatid;
	}

	public void setShopwechatid(String shopwechatid) {
		this.shopwechatid = shopwechatid;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public GoodsVO(Goods goods) {
		super();
		this.id = goods.getGoodsid();
		this.name = goods.getName();
		this.imgurls = goods.getImgurls();
		this.summary = goods.getSummary();
		this.categoryName = goods.getCategory().getName();
		this.shopwechatid = goods.getShop().getWechatid();
		this.shopName = goods.getShop().getName();
	}
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static Map<String, Object> changeGoods2GoodsVO(Pagination<Goods> pagination){
		List<Goods> recordList = pagination.getRecordList();
		List<GoodsVO> VOList = new ArrayList<GoodsVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(Goods item : recordList){
			VOList.add(new GoodsVO(item));
		}
		map.put("rows", VOList);
		map.put("total", pagination.getRecordCount());
		return map;
	}
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static List<GoodsVO> changeGoods2GoodsVO(List<Goods> recordList){
		List<GoodsVO> roleVOList = new ArrayList<GoodsVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(Goods item : recordList){
			roleVOList.add(new GoodsVO(item));
		}
		return roleVOList;
	}
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static List<String> change2Imgurls(Goods goods){
		List<String> returnList = new ArrayList<String>();
		String imgurls = goods.getImgurls();
		String[] imgBlocks = imgurls.split(";");
		for(String item : imgBlocks){
			returnList.add(item);
		}
		return returnList;
	}

	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static Map<String, Object> change2Imgurls(Pagination<Goods> pagination){
		List<Goods> recordList = pagination.getRecordList();
		List<String> returnList = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(Goods goods : recordList){
			String imgurls = goods.getImgurls();
			if(!"".equals(imgurls)){
				String[] imgBlocks = imgurls.split(";");
				for(String item : imgBlocks){
					returnList.add(item);
				}
			}
		}
		map.put("rows", returnList);
		map.put("total", returnList.size());
		return map;
	}
	
	public GoodsVO(Long id, String name, String imgurls, String categoryName,
			String shopwechatid, String shopName, String summary) {
		super();
		this.id = id;
		this.name = name;
		this.imgurls = imgurls;
		this.categoryName = categoryName;
		this.shopwechatid = shopwechatid;
		this.shopName = shopName;
		this.summary = summary;
	}

}
