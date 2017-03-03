package org.shiro.demo.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Category;
/**
 * 分类显示层
 * @author Christy
 *
 */
public class CategoryVO{

	private Long id;//id
	private String name;//角色名
	
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
	public CategoryVO() {
		super();
	}
	
	public CategoryVO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public CategoryVO(Category category) {
		super();
		this.id = category.getCategoryid();
		this.name = category.getName();
	}
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static Map<String, Object> changeCategory2CategoryVO(Pagination<Category> pagination){
		List<Category> recordList = pagination.getRecordList();
		List<CategoryVO> VOList = new ArrayList<CategoryVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(Category item : recordList){
			VOList.add(new CategoryVO(item));
		}
		map.put("rows", VOList);
		map.put("total",pagination.getRecordCount());
		return map;
	}
	
	/**
	 * 将实体类转换成显示层实体类
	 * @param pagination 分页数据
	 * @return
	 */
	public static List<CategoryVO> changeCategory2CategoryVO(List<Category> recordList){
		List<CategoryVO> VOList = new ArrayList<CategoryVO>();
		Map<String, Object> map = new HashMap<String, Object>();
		for(Category item : recordList){
			VOList.add(new CategoryVO(item));
		}
		return VOList;
	}
}
