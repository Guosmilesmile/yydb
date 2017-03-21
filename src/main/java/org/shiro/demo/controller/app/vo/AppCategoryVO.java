package org.shiro.demo.controller.app.vo;

/**
 * 分类app接口显示类
 * @author Christy
 *
 */
public class AppCategoryVO {

	private Long categoryid;
	
	private String name;//类别名称

	
	public AppCategoryVO() {
		super();
	}

	public AppCategoryVO(Long categoryid, String name) {
		super();
		this.categoryid = categoryid;
		this.name = name;
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
