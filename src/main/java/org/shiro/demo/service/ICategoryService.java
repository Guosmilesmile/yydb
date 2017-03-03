package org.shiro.demo.service;

import org.shiro.demo.entity.Category;

/**
 * 分类业务层
 * @author Christy
 *
 */
public interface ICategoryService extends IBaseService{
	
	/**
	 * 新增分类
	 * @param user
	 * @return
	 */
	public boolean insertCategory(Category category);
	
	/**
	 * 删除分类
	 * @param id 用户id
	 * @return
	 */
	public boolean deleteCategory(Long id);
	
	/**
	 * 更新分类
	 * @param user 用户
	 * @return
	 */
	public boolean updateCategory(Category category);
}
