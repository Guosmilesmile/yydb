package org.shiro.demo.controller.app.controller;

import static org.junit.Assert.*;

import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.shiro.demo.util.HttpUtils;
import org.shiro.demo.util.RSAUtils;

public class AppDBPlanControllerTest {
	
	@Test
	public void getDBPlanPageWithCategory()  {
		try {
			byte[] exp = Hex.decodeHex(AppProperties.exp.toCharArray());
	    	byte[] model = Hex.decodeHex(AppProperties.model.toCharArray());
	    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
	    	Long currentTime = System.currentTimeMillis();
			String temp = "wechatid=123&timestamp="+currentTime+"&page=1&pageSize=5&categoryid=1&block=2";
			String param = "params="+RSAUtils.encryptString(publicKey, temp);
			String url = "http://127.0.0.1:8080/yydb/app/dbplan/getDBPlanPageWithCategory?"+param;
			System.out.println(url);
			String sendPost = HttpUtils.sendGet("http://127.0.0.1:8080/yydb/app/dbplan/getDBPlanPageWithCategory", param);
			System.out.println(sendPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getDBPlanPageOrderByMoney()  {
		try {
			byte[] exp = Hex.decodeHex(AppProperties.exp.toCharArray());
	    	byte[] model = Hex.decodeHex(AppProperties.model.toCharArray());
	    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
			String temp = "wechatid=123&timestamp=1490427594000&page=1&pageSize=5&columnName=money";
			String param = "params="+RSAUtils.encryptString(publicKey, temp);
			String url = "http://127.0.0.1:8080/yydb/app/dbplan/getDBPlanPage?"+param;
			System.out.println(url);
			String sendPost = HttpUtils.sendGet("http://127.0.0.1:8080/yydb/app/dbplan/getDBPlanPage", param);
			System.out.println(sendPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getDBPlanPageOderByStartTime()  {
		try {
			byte[] exp = Hex.decodeHex(AppProperties.exp.toCharArray());
	    	byte[] model = Hex.decodeHex(AppProperties.model.toCharArray());
	    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
			String temp = "wechatid=123&timestamp=1490427594000&page=1&pageSize=5&columnName=starttime";
			String param = "params="+RSAUtils.encryptString(publicKey, temp);
			String url = "http://127.0.0.1:8080/yydb/app/dbplan/getDBPlanPage?"+param;
			System.out.println(url);
			String sendPost = HttpUtils.sendGet("http://127.0.0.1:8080/yydb/app/dbplan/getDBPlanPage", param);
			System.out.println(sendPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getDBPlanbyid()  {
		try {
			byte[] exp = Hex.decodeHex(AppProperties.exp.toCharArray());
	    	byte[] model = Hex.decodeHex(AppProperties.model.toCharArray());
	    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
			
			String temp = "wechatid=123&timestamp=14927377650000&dbplanid=3";
			String param = "params="+RSAUtils.encryptString(publicKey, temp);
			System.out.println(param);
			String url = "http://127.0.0.1:8080/yydb/app/dbplan/getDBPlanbyid?"+param;
			System.out.println(url);
			String sendPost = HttpUtils.sendGet("http://127.0.0.1:8080/yydb/app/dbplan/getDBPlanbyid", param);
			System.out.println(sendPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
