package org.shiro.demo.controller.app.controller;

import static org.junit.Assert.*;

import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.shiro.demo.util.HttpUtils;
import org.shiro.demo.util.RSAUtils;

public class AppDBPlanControllerTest {
	
	@Test
	public void getDBPlanPage()  {
		try {
			byte[] exp = Hex.decodeHex("010001".toCharArray());
	    	byte[] model = Hex.decodeHex("009250de9f72158be907d7373d919950c2653fdf92f5cdbfc2c5fdb3390f9dc4a7b186ad1687d79e004e7afc65a94ab6ade087976780afcb1e3273a0588912867f40c786f91cfe0ee29921645bbab8b491a4add21ef96ae1432a2b5385f6085b92675a9b50c49a4f488fd39fd88e6875c1e814086d7ad8d3d5d4b78538aa1eca73".toCharArray());
	    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
			
			String temp = "wechatid=123&timestamp=1490253034000&page=1&pageSize=5";
			String param = "params="+RSAUtils.encryptString(publicKey, temp);
			System.out.println(param);
			String sendPost = HttpUtils.sendPost("http://127.0.0.1:8080/yydb/app/dbplan/getDBPlanPage", param);
			System.out.println(sendPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getDBPlanbyid()  {
		try {
			byte[] exp = Hex.decodeHex("010001".toCharArray());
	    	byte[] model = Hex.decodeHex("009250de9f72158be907d7373d919950c2653fdf92f5cdbfc2c5fdb3390f9dc4a7b186ad1687d79e004e7afc65a94ab6ade087976780afcb1e3273a0588912867f40c786f91cfe0ee29921645bbab8b491a4add21ef96ae1432a2b5385f6085b92675a9b50c49a4f488fd39fd88e6875c1e814086d7ad8d3d5d4b78538aa1eca73".toCharArray());
	    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
			
			String temp = "wechatid=123&timestamp=1490254353000&dbplanid=3";
			String param = "params="+RSAUtils.encryptString(publicKey, temp);
			System.out.println(param);
			String sendPost = HttpUtils.sendPost("http://127.0.0.1:8080/yydb/app/dbplan/getDBPlanbyid", param);
			System.out.println(sendPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
