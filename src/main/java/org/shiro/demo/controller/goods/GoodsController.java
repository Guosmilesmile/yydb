package org.shiro.demo.controller.goods;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Category;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.entity.Goods;
import org.shiro.demo.service.ICategoryService;
import org.shiro.demo.service.ICustomerService;
import org.shiro.demo.service.IGoodsService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="/goods")
public class GoodsController implements ServletConfigAware,ServletContextAware{
	
	private ServletContext servletContext; 
	private ServletConfig servletConfig;  
   
	public void setServletConfig(ServletConfig servletConfig) {
		this.servletConfig = servletConfig;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@Autowired
	private IGoodsService goodsService;
	
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private ICategoryService categoryService;
	
	/**
	 * 分页获取所有商品信息
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/systemgetpageGoods",method=RequestMethod.POST)
	@ResponseBody
	public String systemGetGoodsByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<Goods> GoodsPagination = goodsService.getPagination(Goods.class, null, null, page, pageSize);
		Map<String, Object> GoodsVOMap = GoodsVO.changeGoods2GoodsVO(GoodsPagination);
		returnResult =  FastJsonTool.createJsonString(GoodsVOMap);
		return returnResult;
	}
	
	/**
	 * 获取所有商品
	 */
	@RequestMapping(value = "/systemgetallGoods", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String systemGetAllGoods() {
		String returnResult = "";
		List<Goods> allGoods = goodsService.getAll(Goods.class);
		List<GoodsVO> allGoodsvo  = GoodsVO.changeGoods2GoodsVO(allGoods);
		returnResult = FastJsonTool.createJsonString(allGoodsvo);
		return returnResult;
	}
	
	
	/**
	 * 新增商品
	 * @param rowstr 商品信息
	 * @return
	 */
	@RequestMapping(value = "/systeminsertGoods", method = RequestMethod.POST)
	@ResponseBody
	public String systemInsertGoods(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			String name  = jsonObject.getString("name");
			String summary  = jsonObject.getString("summary");
			String imgurls = "";
			Category category = null;
			Customer shop = null;
			Goods Goods = new Goods(name, imgurls, summary, category, shop);
			boolean flag = goodsService.insertGoods(Goods);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	
	/**
	 * 更新商品
	 * @param rowstr 商品信息
	 * @return
	 */
	@RequestMapping(value = "/systemupdateGoods", method = RequestMethod.POST)
	@ResponseBody
	public String systemUpdateGoods(@RequestParam(value="rowstr")String rowstr){
		System.out.println(rowstr);
		String returnData = ReturnDataUtil.FAIL;
		try {
			JSONArray jsonArray = new JSONArray(rowstr);
			JSONObject jsonObject = jsonArray.getJSONObject(0);
			Long id = jsonObject.getLong("id");
			String name  = jsonObject.getString("name");
			Goods Goods = goodsService.getById(Goods.class, id);
			Goods.setName(name);
			boolean flag = goodsService.updateGoods(Goods);
			if(flag){
				returnData = ReturnDataUtil.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	/**
	 * 删除商品
	 * @param ids 商品id
	 * @return
	 */
	@RequestMapping(value = "/systemdeleteGoods", method = RequestMethod.POST)
	@ResponseBody
	public String systemDeleteGoods(@RequestParam(value="ids")Long id){
		String returnData = ReturnDataUtil.FAIL;
		try {
			goodsService.deleteGoods(id);
			returnData = ReturnDataUtil.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	/**
	 * 上传图片
	 * @return
	 */
	@RequestMapping(value = "/uploadPictures", method = RequestMethod.POST)
	@ResponseBody
	public String uploadPicture( @RequestParam(value = "fileList",required = false) MultipartFile file,HttpServletRequest request,
			@RequestParam(value="goodsid")Long goodsid){
		String filePath = servletContext.getRealPath("/") + "upload/";
        String saveUrl = request.getContextPath() + "/upload/";
        System.out.println(filePath);
        File filedir = new File(filePath);
        if (!filedir.exists()){
            filedir.mkdir();
        }
        String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newfilename = System.currentTimeMillis() + ext;
        String PathAndName = filePath + newfilename;
        saveUrl = saveUrl + newfilename;
        File resultFile = new File(PathAndName);
        try{
            file.transferTo(resultFile);
            goodsService.updateGoods(goodsid,"upload/"+newfilename);
        } catch (IOException e1){
            e1.printStackTrace();
        }
        return "";
	}

	
	/**
	 * 上传商品信息
	 * @return
	 */
	@RequestMapping(value = "/uploadgoods", method = RequestMethod.POST)
	@ResponseBody
	public String uploadGoods(@RequestParam(value="name")String name,@RequestParam(value="categoryid")Long categoryid,
			@RequestParam(value="summary")String summary,@RequestParam(value="shopid")Long shopid){
		String returnData = ReturnDataUtil.FAIL;
		try {
			Category category = categoryService.getById(Category.class, categoryid);
			Customer shop = customerService.getById(Customer.class, shopid);
			Goods goods = new Goods(name, "", summary, category, shop);
			Long id = goodsService.insertGoodsReturnId(goods);
			if(null==id){
				returnData = "-1";
			}else{
				returnData = id +"";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	

	
}
