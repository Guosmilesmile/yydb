package org.shiro.demo.controller.goods;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Category;
import org.shiro.demo.service.ICategoryService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/category")
public class CategoryController {

	@Autowired
	private ICategoryService categoryService;
	
	/**
	 * 分页获取所有分类信息
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/systemgetpagecategory",method=RequestMethod.POST)
	@ResponseBody
	public String systemGetCategoryByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<Category> categoryPagination = categoryService.getPagination(Category.class, null, null, page, pageSize);
		Map<String, Object> categoryVOMap = CategoryVO.changeCategory2CategoryVO(categoryPagination);
		returnResult =  FastJsonTool.createJsonString(categoryVOMap);
		return returnResult;
	}
	
	/**
	 * 获取所有分类
	 */
	@RequestMapping(value = "/systemgetallcategory", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String systemGetAllCategory() {
		String returnResult = "";
		List<Category> allCategory = categoryService.getAll(Category.class);
		List<CategoryVO> allCategoryvo  = CategoryVO.changeCategory2CategoryVO(allCategory);
		returnResult = FastJsonTool.createJsonString(allCategoryvo);
		return returnResult;
	}
	
	
	/**
	 * 新增分类
	 * @param rowstr 用户信息
	 * @return
	 */
	@RequestMapping(value = "/systeminsertcategory", method = RequestMethod.POST)
	@ResponseBody
	public String systemInsertCategory(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			String name  = jsonObject.getString("name");
			Category category = new Category(name);
			boolean flag = categoryService.insertCategory(category);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	
	/**
	 * 更新分类
	 * @param rowstr 分类信息
	 * @return
	 */
	@RequestMapping(value = "/systemupdatecategory", method = RequestMethod.POST)
	@ResponseBody
	public String systemUpdateCategory(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			Long id = jsonObject.getLong("id");
			String name  = jsonObject.getString("name");
			Category category = categoryService.getById(Category.class, id);
			category.setName(name);
			boolean flag = categoryService.updateCategory(category);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	/**
	 * 删除分类
	 * @param ids 分类id
	 * @return
	 */
	@RequestMapping(value = "/systemdeletecategory", method = RequestMethod.POST)
	@ResponseBody
	public String systemDeleteCategory(@RequestParam(value="ids")Long id){
		String returnData = ReturnDataUtil.FAIL;
		try {
			categoryService.deleteCategory(id);
			returnData = ReturnDataUtil.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
}
