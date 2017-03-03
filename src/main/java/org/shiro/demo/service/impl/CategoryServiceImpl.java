package org.shiro.demo.service.impl;

import javax.annotation.Resource;

import org.shiro.demo.entity.Category;
import org.shiro.demo.service.IBaseService;
import org.shiro.demo.service.ICategoryService;
import org.springframework.stereotype.Service;

@Service("categoryService")
public class CategoryServiceImpl extends DefultBaseService implements ICategoryService{

	@Resource(name="baseService")
	private IBaseService baseService;

	public boolean insertCategory(Category category) {
		boolean flag = false;
		try {
			baseService.save(category);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean deleteCategory(Long id) {
		boolean flag = false;
		try {
			baseService.delete(Category.class, id);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public boolean updateCategory(Category category) {
		boolean flag = false;
		try {
			baseService.update(category);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	
}
