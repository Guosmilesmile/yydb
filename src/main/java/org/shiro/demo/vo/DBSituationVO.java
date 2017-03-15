package org.shiro.demo.vo;

/**
 * 夺宝情况显示层
 * @author Christy
 *
 */
public class DBSituationVO {

	private Long id;
	
	private String goodsName;//商品名称
	
	private String shopName;//商家名称

	private String wechatid;//中奖者微信id
	
	private Integer istake;//是否领取 0：未领取 1：领取
	
	private Integer situation;//是否流标 0:流标 1:非流标

	public DBSituationVO() {
		super();
	}
	
	public DBSituationVO(Long id, String goodsName, String shopName,
			String wechatid, Integer istake, Integer situation) {
		super();
		this.id = id;
		this.goodsName = goodsName;
		this.shopName = shopName;
		this.wechatid = wechatid;
		this.istake = istake;
		this.situation = situation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getWechatid() {
		return wechatid;
	}

	public void setWechatid(String wechatid) {
		this.wechatid = wechatid;
	}

	public Integer getIstake() {
		return istake;
	}

	public void setIstake(Integer istake) {
		this.istake = istake;
	}

	public Integer getSituation() {
		return situation;
	}

	public void setSituation(Integer situation) {
		this.situation = situation;
	}

	
}
