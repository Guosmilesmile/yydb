package org.shiro.demo.controller.goods;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.dao.util.QueryCondition;
import org.shiro.demo.entity.Category;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.entity.Goods;
import org.shiro.demo.service.ICategoryService;
import org.shiro.demo.service.ICustomerService;
import org.shiro.demo.service.IGoodsService;
import org.shiro.demo.util.FastJsonTool;
import org.shiro.demo.util.ReturnDataUtil;
import org.shiro.demo.vo.GoodsVO;
import org.shiro.demo.vo.UGoodsVO;
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
public class GoodsController{
	
   
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
	@RequestMapping(value = "/getpageGoods",method=RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"goods:manage"}, logical = Logical.OR)
	public String getGoodsByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize){
		String returnResult = "";
		Pagination<Goods> GoodsPagination = goodsService.getPagination(Goods.class, null, null, page, pageSize);
		Map<String, Object> GoodsVOMap = GoodsVO.changeGoods2GoodsVO(GoodsPagination);
		returnResult =  FastJsonTool.createJsonString(GoodsVOMap);
		return returnResult;
	}
	
	/**
	 * 获取所有商品
	 */
	@RequestMapping(value = "/getallGoods", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	@RequiresPermissions(value = {"goods:manage","db:dbplan"}, logical = Logical.OR)
	public String getAllGoods() {
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
	@RequiresPermissions(value = {"goods:manage"}, logical = Logical.OR)
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
	@RequestMapping(value = "/updateGoods", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"goods:manage"}, logical = Logical.OR)
	public String updateGoods(@RequestParam(value="goodsid")Long goodsid,@RequestParam(value="name")String name,@RequestParam(value="categoryid")Long categoryid,
			@RequestParam(value="summary")String summary,@RequestParam(value="shopid")Long shopid){
		String returnData = ReturnDataUtil.FAIL;
		try {
			
			Goods goods = goodsService.getById(Goods.class, goodsid);
			goods.setName(name);
			goods.setSummary(summary);
			Category category = categoryService.getById(Category.class, categoryid);
			goods.setCategory(category);
			Customer shop = customerService.getById(Customer.class, shopid);
			goods.setShop(shop);
			boolean flag = goodsService.updateGoods(goods);
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
	@RequiresPermissions(value = {"goods:manage"}, logical = Logical.OR)
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
	@RequiresPermissions(value = {"goods:manage"}, logical = Logical.OR)
	public String uploadPicture( @RequestParam(value = "fileList",required = false) MultipartFile file,HttpServletRequest request,
			@RequestParam(value="goodsid")Long goodsid){
		String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/";
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
	@RequiresPermissions(value = {"goods:manage"}, logical = Logical.OR)
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
	

	/**
	 * 分页获取指定商品的图片
	 * @param page 当前页
	 * @param pageSize 每页的数据量
	 * @return
	 */
	@RequestMapping(value = "/getpagegoodsimgurls",method=RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"goods:manage"}, logical = Logical.OR)
	public String getGoodsImgurlsByPage(@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer pageSize,
			@RequestParam(value="goodsid")Long goodsid){
		String returnResult = "";
		List<QueryCondition> query = new ArrayList<QueryCondition>();
		QueryCondition queryCondition = new QueryCondition("goodsid", QueryCondition.EQ, goodsid);
		query.add(queryCondition);
		Pagination<Goods> GoodsPagination = goodsService.getPagination(Goods.class, query , null, page, pageSize);
		Map<String, Object> GoodsVOMap = GoodsVO.change2Imgurls(GoodsPagination);
		returnResult =  FastJsonTool.createJsonString(GoodsVOMap);
		return returnResult;
	}
	
	
	/**
	 * 删除商品图片
	 * @param ids 商品id
	 * @return
	 */
	@RequestMapping(value = "/deletegoodsimgurl", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = {"goods:manage"}, logical = Logical.OR)
	public String deleteGoodsImgurls(@RequestParam(value="ids")Long id,@RequestParam(value="imgurl")String imgurl){
		String returnData = ReturnDataUtil.FAIL;
		try {
			goodsService.deleteGoodsImgurl(id,imgurl);
			returnData = ReturnDataUtil.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnData;
	}
	
	/**
	 * 获取商品信息
	 */
	@RequestMapping(value = "/getgoodswithid", method = RequestMethod.POST,produces = "text/json;charset=UTF-8")
	@ResponseBody
	@RequiresPermissions(value = {"goods:manage"}, logical = Logical.OR)
	public String getGoodsWithid(@RequestParam(value="goodsid")Long id) {
		String returnResult = "";
		Goods goods = goodsService.getById(Goods.class,id);
		returnResult = FastJsonTool.createJsonString(new UGoodsVO(goods));
		return returnResult;
	}
}
