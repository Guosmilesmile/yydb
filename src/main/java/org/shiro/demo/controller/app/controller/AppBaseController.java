package org.shiro.demo.controller.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shiro.demo.controller.app.ReturnData;
import org.shiro.demo.controller.app.exception.EncryptWrongExcetion;
import org.shiro.demo.controller.app.exception.ParamsWromgException;
import org.shiro.demo.controller.app.exception.TimeOutException;
import org.shiro.demo.util.RSAUtils;
import org.shiro.demo.util.SplitParamsUtil;

/**
 * 接口控制层基类
 * 
 * @author Christy
 * 
 */
public class AppBaseController {

	/**
	 * 过滤参数
	 * @param params
	 * @return
	 * @throws TimeOutException 接口超时异常
	 * @throws EncryptWrongExcetion 加密错误
	 * @throws ParamsWromgException 参数不全
	 */
	public Map<String, String> filterParam(String params) throws TimeOutException,EncryptWrongExcetion,ParamsWromgException{
		Map<String, String>  splitParams = null;
		ReturnData returnData = new ReturnData();
		String decryptString = RSAUtils.decryptString(params);
		System.out.println(decryptString);
		if (null == decryptString) {
			throw new EncryptWrongExcetion("加密数据错误");
		} else {
			splitParams = SplitParamsUtil.splitParams(decryptString, "&", "=");
			Long timestamp = Long.parseLong(splitParams.get("timestamp")) / 1000;
			Long nowTime = System.currentTimeMillis() / 1000;
			if (nowTime - timestamp >= ReturnData.TIMEOUT) {
				throw new TimeOutException("接口时间已过期");
			} else {
				String wechatid = splitParams.get("wechatid");
				if (null == wechatid || "".equals(wechatid)) {
					throw new ParamsWromgException("接口数据不全");
				} else {
					returnData.setCode(ReturnData.SUCCESS);
					returnData.setMessage("成功");
				}
			}
		}
		return splitParams;
	}
}
