package org.shiro.demo.controller.app.controller;

import static org.junit.Assert.*;

import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.shiro.demo.util.HttpUtils;
import org.shiro.demo.util.RSAUtils;

public class AppShopControllerTest {

	
	@Test
	public void getDBPlanPage() throws DecoderException {
		byte[] exp = Hex.decodeHex(AppProperties.exp.toCharArray());
    	byte[] model = Hex.decodeHex(AppProperties.model.toCharArray());
    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
    	Long currentTime = System.currentTimeMillis();
		String temp = "wechatid=wx_shop&timestamp="+currentTime+"&page=1&pageSize=5";
		System.out.println(temp);
		String param = "params="+RSAUtils.encryptString(publicKey, temp);
		System.out.println(param);
		String url = "http://127.0.0.1:8080/yydb/app/shop/getDBPlanPage?"+ param;
		System.out.println(url);
		String sendPost = HttpUtils.sendGet("http://127.0.0.1:8080/yydb/app/shop/getDBPlanPage",param);
		System.out.println(sendPost);
	}

	@Test
	public void login() throws DecoderException {
		byte[] exp = Hex.decodeHex(AppProperties.exp.toCharArray());
    	byte[] model = Hex.decodeHex(AppProperties.model.toCharArray());
    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
    	Long currentTime = System.currentTimeMillis();
		String temp = "wechatid=null&timestamp="+currentTime;
		System.out.println(temp);
		String param = "params="+RSAUtils.encryptString(publicKey, temp);
		System.out.println(param);
		String url = "http://127.0.0.1:8080/yydb/app/customer/login?"+ param;
		System.out.println(url);
		String sendPost = HttpUtils.sendGet("http://127.0.0.1:8080/yydb/app/customer/login",param);
		System.out.println(sendPost);
	}
}
