package org.shiro.demo.controller.app.controller;

import static org.junit.Assert.*;

import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.shiro.demo.util.HttpUtils;
import org.shiro.demo.util.RSAUtils;

public class AppDBAttendControllerTest {

	@Test
	public void getDBAttendPage()  {
		try {
			byte[] exp = Hex.decodeHex(AppProperties.exp.toCharArray());
	    	byte[] model = Hex.decodeHex(AppProperties.model.toCharArray());
	    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
	    	Long currentTime = System.currentTimeMillis();
			String temp = "wechatid=chris&timestamp="+currentTime+"&page=1&pageSize=5&isfinish=2";
			String param = "params="+RSAUtils.encryptString(publicKey, temp);
			System.out.println(temp);
			String url = "http://127.0.0.1:8080/yydb/app/dbattend/getDBAttendPage?"+param;
			System.out.println(url);
			String sendPost = HttpUtils.sendGet("http://127.0.0.1:8080/yydb/app/dbattend/getDBAttendPage", param);
			System.out.println(sendPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	@Test
	public void changeIsPay()  {
		try {
			byte[] exp = Hex.decodeHex(AppProperties.exp.toCharArray());
	    	byte[] model = Hex.decodeHex(AppProperties.model.toCharArray());
	    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
	    	Long currentTime = System.currentTimeMillis();
			String temp = "wechatid=chris&timestamp="+currentTime+"&isplay=0&dbattendid=2";
			String param = "params="+RSAUtils.encryptString(publicKey, temp);
			System.out.println(temp);
			String url = "http://127.0.0.1:8080/yydb/app/dbattend/changeispay?"+param;
			System.out.println(url);
			String sendPost = HttpUtils.sendGet("http://127.0.0.1:8080/yydb/app/dbattend/changeispay", param);
			System.out.println(sendPost);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
