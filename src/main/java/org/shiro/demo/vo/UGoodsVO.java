package org.shiro.demo.vo;

import org.shiro.demo.entity.Goods;

public class UGoodsVO {

	private Long id;
	
	private String name;
	
	private String imgurls;
	
	private String summary;
	
	private Long shopid;
	
	private Long categoryid;
	

	

	public UGoodsVO(Long id, String name, String imgurls, String summary,
			Long shopid, Long categoryid) {
		super();
		this.id = id;
		this.name = name;
		this.imgurls = imgurls;
		this.summary = summary;
		this.shopid = shopid;
		this.categoryid = categoryid;
	}

	public UGoodsVO() {
		super();
	}
	
	public UGoodsVO(Goods goods) {
		super();
		this.id = goods.getGoodsid();
		this.name = goods.getName();
		this.imgurls = goods.getImgurls();
		this.summary = goods.getSummary();
		this.shopid = goods.getShop().getCustomerid();
		this.categoryid = goods.getCategory().getCategoryid();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImgurls() {
		return imgurls;
	}

	public void setImgurls(String imgurls) {
		this.imgurls = imgurls;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Long getShopid() {
		return shopid;
	}

	public void setShopid(Long shopid) {
		this.shopid = shopid;
	}

	public Long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
