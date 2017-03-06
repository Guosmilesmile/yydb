package org.shiro.demo.controller.goods;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.shiro.demo.dao.util.Pagination;
import org.shiro.demo.entity.Category;
import org.shiro.demo.entity.Customer;
import org.shiro.demo.entity.Goods;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="/goods")
public class GoodsController {

	@Autowired
	private IGoodsService goodsService;
	
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
	 * @param ids 商品id
	 * @return
	 */
	@RequestMapping(value = "/uploadPictures", method = RequestMethod.POST)
	@ResponseBody
	public String uploadPicture( @RequestParam(value = "fileList", required = false) MultipartFile file){
		System.out.println(FastJsonTool.createJsonString(file));
		return ReturnDataUtil.SUCCESS;
	}
}
