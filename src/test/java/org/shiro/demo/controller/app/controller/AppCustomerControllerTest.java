package org.shiro.demo.controller.app.controller;

import static org.junit.Assert.*;

import java.security.interfaces.RSAPublicKey;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.shiro.demo.util.HttpUtils;
import org.shiro.demo.util.RSAUtils;

public class AppCustomerControllerTest {

	@Test
	public void getBalance() throws DecoderException {
		byte[] exp = Hex.decodeHex(AppProperties.exp.toCharArray());
    	byte[] model = Hex.decodeHex(AppProperties.model.toCharArray());
    	RSAPublicKey publicKey = RSAUtils.generateRSAPublicKey(model, exp);
		
		String temp = "wechatid=chris&timestamp=1490408844000";
		String param = "params="+RSAUtils.encryptString(publicKey, temp);
		String url = "http://127.0.0.1:8080/yydb/app/customer/getbalance?"+ param;
		System.out.println(url);
		String sendPost = HttpUtils.sendGet("http://127.0.0.1:8080/yydb/app/customer/getbalance",param);
		System.out.println(sendPost);
	}

}
