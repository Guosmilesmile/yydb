package org.shiro.demo.controller.app.controller;

import static org.junit.Assert.*;

import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.shiro.demo.util.HttpUtils;
import org.shiro.demo.util.RSAUtils;

public class AppDBPlanControllerTest {
	
	@Test
	public void getDBPlanPage() throws Exception {
		byte[] exp = Hex.decodeHex("010001".toCharArray());
    	byte[] model = Hex.decodeHex("008623fae5b9dcaf85432f3ee60d5a4a7fe4adee107f9383ad528d0f0fac6627adf6aadeb92ede9643d15fc5de5dade7d3edbfb715e4bad900af8f14fa8f0f56baf8713864c9cbd45469e89acc5525f6c317d19a2365c0ae738b0d9b5961da2817b6aa4df5b0d54e7edafb617546c58134e27c3b1f9cd93be06b43402364ed21db".toCharArray());
    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
		
		String temp = "wechatid=123&timestamp=1490083735000&page=1&pageSize=5";
		String param = "params="+RSAUtils.encryptString(publicKey, temp);
		System.out.println(param);
		String sendPost = HttpUtils.sendPost("http://127.0.0.1:8080/yydb/app/dbplan/getDBPlanPage", param);
		System.out.println(sendPost);
	}

}
