package org.shiro.demo.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSAUtils2 {

	private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils2.class);

	/** 算法名称 */
    private static final String ALGORITHOM = "RSA";
    
    /** 默认的安全服务提供者 */
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();
    
    private static PublicKey publicKey = null;
    
    private static PrivateKey privateKey = null;
    
    static{
    	publicKey = getPublicKeyFromFile("__RSA_PAIR.txt");
    	privateKey = getPrivateFromFile("__RSA_PAIR.txt");
    }
	 /**
     * 通过文件获取公钥
     * @param filename
     */
    public static PublicKey getPublicKeyFromFile(String filename){
    	try {
    		String keyFile = getKeyFile(filename);
        	String[] splits = keyFile.split("\r\n");
        	byte[] decodeBase64 = Base64.decodeBase64(splits[4]);
        	java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(decodeBase64);
            java.security.KeyFactory keyFactory;
            keyFactory = java.security.KeyFactory.getInstance("RSA");
            PublicKey publicKey  = keyFactory.generatePublic(bobPubKeySpec);
            return publicKey;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 通过文件获取私钥
     * @param filename
     */
    public static PrivateKey getPrivateFromFile(String filename){
    	try {
    		String keyFile = getKeyFile(filename);
        	String[] splits = keyFile.split("\r\n");
        	byte[] decodeBase64 = Base64.decodeBase64(splits[1]);
        	java.security.spec.PKCS8EncodedKeySpec priPKCS8  = new java.security.spec.PKCS8EncodedKeySpec(decodeBase64);
            java.security.KeyFactory keyFactory;
            keyFactory = java.security.KeyFactory.getInstance("RSA");
            PrivateKey privateKey  = keyFactory.generatePrivate(priPKCS8 );
            return privateKey;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    /**
     * 获取key文件内容
     * @param filename
     * @return
     */
    public static String getKeyFile(String filename){
    	String result = "";
    	try {
    		ClassLoader classLoader = RSAUtils.class.getClassLoader();  
            URL resource = classLoader.getResource("__RSA_PAIR.txt");  
            String path = resource.getPath();  
            System.out.println(path); 
            FileInputStream  fileInputStream = new FileInputStream (path);
            InputStreamReader isr=new InputStreamReader(fileInputStream);                    
            BufferedReader br=new BufferedReader(isr);    
            String line ="";
            while((line=br.readLine()) != null){    
            	result += line+"\r\n";
            } 
        }  catch (Exception e) {
            e.printStackTrace();
        }
    	System.out.println(result);
    	return result;
    }
    
    /**
     * 使用指定的公钥加密数据。
     * 
     * @param publicKey 给定的公钥。
     * @param data 要加密的数据。
     * @return 加密后的数据。
     */
    public static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.ENCRYPT_MODE, publicKey);
        return ci.doFinal(data);
    }
    
    /**
     * 使用指定的私钥解密数据。
     * 
     * @param privateKey 给定的私钥。
     * @param data 要解密的数据。
     * @return 原数据。
     */
    public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
        Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        ci.init(Cipher.DECRYPT_MODE, privateKey);
        return ci.doFinal(data);
    }
    
    /**
     * 使用给定的私钥解密给定的字符串。
     * <p />
     * 若私钥为 {@code null}，或者 {@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。
     * 私钥不匹配时，返回 {@code null}。
     * 
     * @param privateKey 给定的私钥。
     * @param encrypttext 密文。
     * @return 原文字符串。
     */
    public static String decryptString(PrivateKey privateKey, String encrypttext) {
        if (privateKey == null || StringUtils.isBlank(encrypttext)) {
            return null;
        }
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt(privateKey, en_data);
            return new String(data);
        } catch (Exception ex) {
            LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getCause().getMessage()));
        }
        return null;
    }
    
    /**
     * 使用默认的私钥解密给定的字符串。
     * <p />
     * 若私钥为 {@code null}，或者 {@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。
     * 私钥不匹配时，返回 {@code null}。
     * 
     * @param privateKey 给定的私钥。
     * @param encrypttext 密文。
     * @return 原文字符串。
     */
    public static String decryptString(String encrypttext) {
        if (privateKey == null || StringUtils.isBlank(encrypttext)) {
            return null;
        }
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt(privateKey, en_data);
            return new String(data);
        } catch (Exception ex) {
            LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getCause().getMessage()));
        }
        return null;
    }
    
    /**
     * 使用给定的公钥加密给定的字符串。
     * <p />
     * 若 {@code publicKey} 为 {@code null}，或者 {@code plaintext} 为 {@code null} 则返回 {@code
     * null}。
     * 
     * @param publicKey 给定的公钥。
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    public static String encryptString(PublicKey publicKey, String plaintext) {
        if (publicKey == null || plaintext == null) {
            return null;
        }
        byte[] data = plaintext.getBytes();
        try {
            byte[] en_data = encrypt(publicKey, data);
            return new String(Hex.encodeHex(en_data));
        } catch (Exception ex) {
            LOGGER.error(ex.getCause().getMessage());
        }
        return null;
    }
    
    /**
     * 使用默认的公钥加密给定的字符串。
     * <p />
     * 若 {@code publicKey} 为 {@code null}，或者 {@code plaintext} 为 {@code null} 则返回 {@code
     * null}。
     * 
     * @param publicKey 给定的公钥。
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    public static String encryptString(String plaintext) {
        if (publicKey == null || plaintext == null) {
            return null;
        }
        byte[] data = plaintext.getBytes();
        try {
            byte[] en_data = encrypt(publicKey, data);
            return new String(Hex.encodeHex(en_data));
        } catch (Exception ex) {
            LOGGER.error(ex.getCause().getMessage());
        }
        return null;
    }

    /**
     * 获取公钥
     * @return
     */
    public static PublicKeyMap getPublicKeyMap() {
    	PublicKeyMap publicKeyMap = new PublicKeyMap();
    	publicKeyMap.setModulus(new String(Hex.encodeHex(((RSAPublicKey)publicKey).getModulus().toByteArray())));
		publicKeyMap.setExponent(new String(Hex.encodeHex( ((RSAPublicKey)publicKey).getPublicExponent().toByteArray())));
		return publicKeyMap;
    }
    
    /**
     * 使用默认的私钥解密由JS加密（使用此类提供的公钥加密）的字符串。
     * 
     * @param encrypttext 密文。
     * @return {@code encrypttext} 的原文字符串。
     */
    public static String decryptStringByJs(String encrypttext) {
        String text = decryptString(encrypttext);
        if(text == null) {
            return null;
        }
        return StringUtils.reverse(text);
    }
    public static void main(String[] args) {
    	BigInteger modulus = ((RSAPublicKey)publicKey).getModulus();
    	System.out.println(modulus);
	}
}
