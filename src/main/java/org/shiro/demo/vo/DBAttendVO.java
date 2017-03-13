package org.shiro.demo.vo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.DBAttend;

/**
 * 夺宝计划参与情况显示层
 * @author Christy
 *
 */
public class DBAttendVO {


	private Long id;//id
	
	private Long createTime;//创建时间
	
	private Integer isplay;//是否付款
	
	private String wechatid;//顾客微信id
	
	private String goodsname;//商品名称
	
	private String shopName;//商家名称


	public DBAttendVO() {
		super();
	}
	
	public DBAttendVO(Long id, Long createTime, Integer isplay,
			String wechatid, String goodsname, String shopName) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.isplay = isplay;
		this.wechatid = wechatid;
		this.goodsname = goodsname;
		this.shopName = shopName;
	}
	
	public DBAttendVO(DBAttend dbAttend) {
		super();
		this.id = dbAttend.getAttendid();
		this.createTime = dbAttend.getCreateTime();
		this.isplay = dbAttend.getIsplay();
		this.wechatid = dbAttend.getCustomer().getWechatid();
		this.goodsname = dbAttend.getDbPlan().getGoods().getName();
		this.shopName = dbAttend.getDbPlan().getGoods().getShop().getName();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getIsplay() {
		return isplay;
	}

	public void setIsplay(Integer isplay) {
		this.isplay = isplay;
	}

	public String getWechatid() {
		return wechatid;
	}

	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static Map<String, Object> changeDBAttend2DBAttendVO(Pagination<DBAttend> pagination){
		List<DBAttend> recordList = pagination.getRecordList();
		List<DBAttendVO> VOList = new ArrayList<DBAttendVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(DBAttend item : recordList){
			VOList.add(new DBAttendVO(item));
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
	public static List<DBAttendVO> changeDBAttend2DBAttendVO(List<DBAttend> recordList){
		List<DBAttendVO> roleVOList = new ArrayList<DBAttendVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(DBAttend item : recordList){
			roleVOList.add(new DBAttendVO(item));
		}
		return roleVOList;
	}

}
