package org.shiro.demo.util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.KeyPair;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.Provider;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidParameterException;
import java.security.interfaces.RSAPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Properties;

import javax.crypto.Cipher;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

/**
 * RSA算法加密/解密工具类。
 * 
 * @author fuchun
 * @version 1.0.0, 2010-05-05
 */
public abstract class RSAUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RSAUtils.class);

    /** 算法名称 */
    private static final String ALGORITHOM = "RSA";
    /**保存生成的密钥对的文件名称。 */
    private static final String RSA_PAIR_FILENAME = "/__RSA_PAIR.txt";
    /** 密钥大小 */
    private static final int KEY_SIZE = 1024;
    /** 默认的安全服务提供者 */
    private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

    private static KeyPairGenerator keyPairGen = null;
    private static KeyFactory keyFactory = null;
    /** 缓存的密钥对。 */
    private static KeyPair oneKeyPair = null;

    private static File rsaPairFile = null;

    static {
        try {
            keyPairGen = KeyPairGenerator.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
            keyFactory = KeyFactory.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error(ex.getMessage());
        }
        rsaPairFile = new File(getRSAPairFilePath());
    }

    private RSAUtils() {
    }

    /**
     * 生成并返回RSA密钥对。
     */
    private static synchronized KeyPair generateKeyPair() {
        try {
            keyPairGen.initialize(KEY_SIZE, new SecureRandom(DateFormatUtils.format(new Date(),"yyyyMMdd").getBytes()));
            oneKeyPair = keyPairGen.generateKeyPair();
            saveKeyPair(oneKeyPair);
            return oneKeyPair;
        } catch (InvalidParameterException ex) {
            LOGGER.error("KeyPairGenerator does not support a key length of " + KEY_SIZE + ".", ex);
        } catch (NullPointerException ex) {
            LOGGER.error("RSAUtils#KEY_PAIR_GEN is null, can not generate KeyPairGenerator instance.",
                    ex);
        }
        return null;
    }

    /**
     * 返回生成/读取的密钥对文件的路径。
     */
    private static String getRSAPairFilePath() {
        String urlPath = RSAUtils.class.getResource("/").getPath();
        return (new File(urlPath).getParent() + RSA_PAIR_FILENAME);
    }

    /**
     * 若需要创建新的密钥对文件，则返回 {@code true}，否则 {@code false}。
     */
    private static boolean isCreateKeyPairFile() {
        // 是否创建新的密钥对文件
        boolean createNewKeyPair = false;
        if (!rsaPairFile.exists() || rsaPairFile.isDirectory()) {
            createNewKeyPair = true;
        }
        return createNewKeyPair;
    }

    /**
     * 将指定的RSA密钥对以文件形式保存。
     * 
     * @param keyPair 要保存的密钥对。
     */
    private static void saveKeyPair(KeyPair keyPair) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = FileUtils.openOutputStream(rsaPairFile);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(keyPair);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        	IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(fos);
        }
    }

    /**
     * 返回RSA密钥对。
     */
    public static KeyPair getKeyPair() {
        // 首先判断是否需要重新生成新的密钥对文件
        if (isCreateKeyPairFile()) {
            // 直接强制生成密钥对文件，并存入缓存。
            return generateKeyPair();
        }
        if (oneKeyPair != null) {
            return oneKeyPair;
        }
        return readKeyPair();
    }
    
    // 同步读出保存的密钥对
    private static KeyPair readKeyPair() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = FileUtils.openInputStream(rsaPairFile);
            ois = new ObjectInputStream(fis);
            oneKeyPair = (KeyPair) ois.readObject();
            return oneKeyPair;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(fis);
        }
        return null;
    }

    /**
     * 根据给定的系数和专用指数构造一个RSA专用的公钥对象。
     * 
     * @param modulus 系数。
     * @param publicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) {
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(new BigInteger(modulus),
                new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException ex) {
            LOGGER.error("RSAPublicKeySpec is unavailable.", ex);
        } catch (NullPointerException ex) {
            LOGGER.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
        }
        return null;
    }

    /**
     * 根据给定的系数和专用指数构造一个RSA专用的私钥对象。
     * 
     * @param modulus 系数。
     * @param privateExponent 专用指数。
     * @return RSA专用私钥对象。
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
        RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus),
                new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException ex) {
            LOGGER.error("RSAPrivateKeySpec is unavailable.", ex);
        } catch (NullPointerException ex) {
            LOGGER.error("RSAUtils#KEY_FACTORY is null, can not generate KeyFactory instance.", ex);
        }
        return null;
    }
    
    
    
    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的私钥对象。
     * 
     * @param modulus 系数。
     * @param privateExponent 专用指数。
     * @return RSA专用私钥对象。
     */
    public static RSAPrivateKey getRSAPrivateKey(String hexModulus, String hexPrivateExponent) {
        if(StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPrivateExponent)) {
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("hexModulus and hexPrivateExponent cannot be empty. RSAPrivateKey value is null to return.");
            }
            return null;
        }
        byte[] modulus = null;
        byte[] privateExponent = null;
        try {
            modulus = Hex.decodeHex(hexModulus.toCharArray());
            privateExponent = Hex.decodeHex(hexPrivateExponent.toCharArray());
        } catch(DecoderException ex) {
            LOGGER.error("hexModulus or hexPrivateExponent value is invalid. return null(RSAPrivateKey).");
        }
        if(modulus != null && privateExponent != null) {
            return generateRSAPrivateKey(modulus, privateExponent);
        }
        return null;
    }
    
    /**
     * 根据给定的16进制系数和专用指数字符串构造一个RSA专用的公钥对象。
     * 
     * @param modulus 系数。
     * @param publicExponent 专用指数。
     * @return RSA专用公钥对象。
     */
    public static RSAPublicKey getRSAPublidKey(String hexModulus, String hexPublicExponent) {
        if(StringUtils.isBlank(hexModulus) || StringUtils.isBlank(hexPublicExponent)) {
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("hexModulus and hexPublicExponent cannot be empty. return null(RSAPublicKey).");
            }
            return null;
        }
        byte[] modulus = null;
        byte[] publicExponent = null;
        try {
            modulus = Hex.decodeHex(hexModulus.toCharArray());
            publicExponent = Hex.decodeHex(hexPublicExponent.toCharArray());
        } catch(DecoderException ex) {
            LOGGER.error("hexModulus or hexPublicExponent value is invalid. return null(RSAPublicKey).");
        }
        if(modulus != null && publicExponent != null) {
            return generateRSAPublicKey(modulus, publicExponent);
        }
        return null;
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
     * 若{@code plaintext} 为 {@code null} 则返回 {@code null}。
     * 
     * @param plaintext 字符串。
     * @return 给定字符串的密文。
     */
    public static String encryptString(String plaintext) {
        if(plaintext == null) {
            return null;
        }
        byte[] data = plaintext.getBytes();
        KeyPair keyPair = getKeyPair();
        try {
            byte[] en_data = encrypt((RSAPublicKey)keyPair.getPublic(), data);
            return new String(Hex.encodeHex(en_data));
        } catch(NullPointerException ex) {
            LOGGER.error("keyPair cannot be null.");
        } catch(Exception ex) {
            LOGGER.error(ex.getCause().getMessage());
        }
        return null;
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
     * 若{@code encrypttext} 为 {@code null}或空字符串则返回 {@code null}。
     * 私钥不匹配时，返回 {@code null}。
     * 
     * @param encrypttext 密文。
     * @return 原文字符串。
     */
    public static String decryptString(String encrypttext) {
        if(StringUtils.isBlank(encrypttext)) {
            return null;
        }
        KeyPair keyPair = getKeyPair();
        try {
            byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
            byte[] data = decrypt((RSAPrivateKey)keyPair.getPrivate(), en_data);
            return new String(data);
        } catch(NullPointerException ex) {
            LOGGER.error("keyPair cannot be null.");
        } catch (Exception ex) {
            LOGGER.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getMessage()));
        }
        return null;
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
    
    /** 返回已初始化的默认的公钥。*/
    public static RSAPublicKey getDefaultPublicKey() {
        KeyPair keyPair = getKeyPair();
        if(keyPair != null) {
            return (RSAPublicKey)keyPair.getPublic();
        }
        return null;
    }
    
    /** 返回已初始化的默认的私钥。*/
    public static RSAPrivateKey getDefaultPrivateKey() {
        KeyPair keyPair = getKeyPair();
        if(keyPair != null) {
            return (RSAPrivateKey)keyPair.getPrivate();
        }
        return null;
    }
    
    public static PublicKeyMap getPublicKeyMap() {
    	PublicKeyMap publicKeyMap = new PublicKeyMap();
    	RSAPublicKey rsaPublicKey = getDefaultPublicKey();
    	publicKeyMap.setModulus(new String(Hex.encodeHex(rsaPublicKey.getModulus().toByteArray())));
		publicKeyMap.setExponent(new String(Hex.encodeHex(rsaPublicKey.getPublicExponent().toByteArray())));
		return publicKeyMap;
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
     * 使用公钥加密，调用接口
     * @param args
     * @throws DecoderException
     */
    public static void main(String[] args) throws DecoderException {
    	/*//文本
    	//String text = "admin";
    	String text  = "a=1&b=2";
    	byte[] exp = Hex.decodeHex("010001".toCharArray());
    	//byte[] model = Hex.decodeHex("0086a59f701b449dea3e89ddfe3d886d872f0c1049d8c5df32283196c3369766450ba28217f642e528fcf8b7907d7532a59acdfa87461bf3ae99b49888b5a9f287b75df63f3bbf7b45df3aad79ca90135a7a66be1ab694873dfe8d5fb8d6a23e01857e067b1b4a3d954fba910bbc41176e9d97be63ec51c23775f89ac361ac8e11".toCharArray());
		//公钥
    	byte[] model = Hex.decodeHex("008c8866509e2fd5dda1cfd2268504a71f9c773dce97cd6aef6fcb13f50db2ec1b9f30c717047b6ba4d90314e3543b915b7dee92ad2d40f958afed67ba0de9ad0eb02fd336d335265c3cd1abf92373d0c8ec00cfb9f8b98f6fe913511554b9404029db6df23dcc3f3332f91f4db7bc0037b1faf45032d3db4a65d930a0c8287d09".toCharArray());
    	RSAPublicKey generateRSAPublicKey = generateRSAPublicKey(model, exp);
		String encryptString = encryptString(generateRSAPublicKey, text);
		System.out.println(encryptString);
		String decryptString = decryptString(encryptString);
		System.out.println(decryptString);*/
    	
    	String text = "admin";
    	FileInputStream fis = null;
        ObjectInputStream ois = null;
        KeyPair keyPair = null;
        try {
            fis = FileUtils.openInputStream(new File("D:\\javawork\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\yydb\\WEB-INF\\__RSA_PAIR.txt"));
            ois = new ObjectInputStream(fis);
            keyPair = (KeyPair) ois.readObject();
            PublicKey public1 = keyPair.getPublic();
            PrivateKey private1 = keyPair.getPrivate();
            byte[] encoded = public1.getEncoded();
            System.out.println(public1.toString());
            System.out.println(new String(Base64.encodeBase64(public1.toString().getBytes())));
            //java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(encoded);
            //java.security.KeyFactory keyFactory;
            //keyFactory = java.security.KeyFactory.getInstance("RSA");
            //PublicKey publicKey  = keyFactory.generatePublic(bobPubKeySpec);
            //String encryptString = encryptString(publicKey, text);
            //System.out.println(encryptString);
            //String decryptString = decryptString(private1, encryptString);
            //System.out.println(decryptString);
            //System.out.println(new String(Base64.encodeBase64(public1.getEncoded()))  );
            //System.out.println(new String(Base64.encodeBase64(private1.getEncoded()))  );
            String encryptString = encryptString(public1, text);
            System.out.println(encryptString);
            String decryptString = decryptString(private1, encryptString);
            System.out.println(decryptString);
           
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            IOUtils.closeQuietly(ois);
            IOUtils.closeQuietly(fis);
        }
    	/*PublicKey publickey = getPublicKeyFromFile("__RSA_PAIR.txt");
    	PrivateKey privateKey = getPrivateFromFile("__RSA_PAIR.txt");
    	String text = "admin";
    	String encryptString = encryptString(publickey, text);
        System.out.println(encryptString);
        String decryptString = decryptString(privateKey, encryptString);
        System.out.println(decryptString);*/
    }
}