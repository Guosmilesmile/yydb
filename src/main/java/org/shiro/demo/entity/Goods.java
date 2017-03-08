package org.shiro.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="g_goods")
public class Goods {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="goodsid")
	private Long goodsid;
	
	@Column(name="name")
	private String name;
	
	@Column(name="imgurls")
	private String imgurls;//图片地址，；分隔
	
	@Column(name="summary")
	private String summary;//概要
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryid")
	private Category category;//分类
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "shopid")
	private Customer shop;//商店

	public Long getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Long goodsid) {
		this.goodsid = goodsid;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Customer getShop() {
		return shop;
	}

	public void setShop(Customer shop) {
		this.shop = shop;
	}

	public Goods() {
		super();
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Goods(Long goodsid, String name, String imgurls, String summary,
			Category category, Customer shop) {
		super();
		this.goodsid = goodsid;
		this.name = name;
		this.imgurls = imgurls;
		this.summary = summary;
		this.category = category;
		this.shop = shop;
	}

	public Goods(String name, String imgurls, String summary,
			Category category, Customer shop) {
		super();
		this.name = name;
		this.imgurls = imgurls;
		this.summary = summary;
		this.category = category;
		this.shop = shop;
	}
	
	public void appendImgurls(String imgurl){
		if("".equals(this.imgurls)){
			this.imgurls = this.imgurls + imgurl;
		}else{
			this.imgurls = this.imgurls +";"+ imgurl;
		}
		
	}
}
