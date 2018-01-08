package org.oiue.tools.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.oiue.tools.StatusResult;
import org.oiue.tools.exception.OIUEException;

public class SecurityUtil {
	/**
	 * 定义加密方式
	 */
	private static final String KEY_DH = "DH";
	public static final String PUBLIC_KEY = "DHPublicKey";
	public static final String PRIVATE_KEY = "DHPrivateKey";
	// 开始生成本地密钥SecretKey 密钥算法为对称密码算法可以为 DES DES AES
	public static final String KEY_DH_DES = "DES";

	/**
	 * 甲方初始化并返回密钥对
	 * @return Map&lt;String, Object&gt;
	 */
	public static Map<String, Object> initKey() {
		try {
			// 实例化密钥对生成器
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_DH);
			// 初始化密钥对生成器 默认是1024 512-1024 & 64的倍数
			keyPairGenerator.initialize(1024);
			// 生成密钥对
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			// 得到甲方公钥
			DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
			// 得到甲方私钥
			DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
			// 将公钥和私钥封装在Map中， 方便之后使用
			Map<String, Object> keyMap = new HashMap<String, Object>();
			keyMap.put(PUBLIC_KEY, publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);
			return keyMap;
		} catch (Throwable e) {
			throw new OIUEException(StatusResult._ncriticalAbnormal, "", e);
		}
	}

	/**
	 * 乙方根据甲方公钥初始化并返回密钥对
	 * @param key 甲方的公钥
	 * @return Map&lt;String, Object&gt;
	 */
	public static Map<String, Object> initKey(byte[] key) {
		try {
			// 将甲方公钥从字节数组转换为PublicKey
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
			// 实例化密钥工厂
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_DH);
			// 产生甲方公钥pubKey
			DHPublicKey dhPublicKey = (DHPublicKey) keyFactory.generatePublic(keySpec);
			// 剖析甲方公钥，得到其参数
			DHParameterSpec dhParameterSpec = dhPublicKey.getParams();
			// 实例化密钥对生成器
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_DH);
			// 用甲方公钥初始化密钥对生成器
			keyPairGenerator.initialize(dhParameterSpec);
			// 产生密钥对
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			// 得到乙方公钥
			DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
			// 得到乙方私钥
			DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();
			// 将公钥和私钥封装在Map中， 方便之后使用
			Map<String, Object> keyMap = new HashMap<String, Object>();
			keyMap.put(PUBLIC_KEY, publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);
			return keyMap;
		} catch (Throwable e) {
			throw new OIUEException(StatusResult._ncriticalAbnormal, "", e);
		}
	}

	/**
	 * 根据对方的公钥和自己的私钥生成 本地密钥,返回的是SecretKey对象的字节数组
	 * @param publicKey 公钥
	 * @param privateKey 私钥
	 * @return byte[]
	 */
	public static byte[] getSecretKeyBytes(byte[] publicKey, byte[] privateKey) {
		try {
			return getSecretKey(publicKey, privateKey).getEncoded();
		} catch (Throwable e) {
			throw new OIUEException(StatusResult._ncriticalAbnormal, "", e);
		}
	}

	/**
	 * 根据对方的公钥和自己的私钥生成 本地密钥,返回的是SecretKey对象
	 * @param publicKey 公钥
	 * @param privateKey 私钥
	 * @return SecretKey
	 */
	public static SecretKey getSecretKey(byte[] publicKey, byte[] privateKey) {
		try {
			// 实例化密钥工厂
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_DH);
			// 将公钥从字节数组转换为PublicKey
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
			// 将私钥从字节数组转换为PrivateKey
			PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(privateKey);
			PrivateKey priKey = keyFactory.generatePrivate(priKeySpec);

			// 准备根据以上公钥和私钥生成本地密钥SecretKey
			// 先实例化KeyAgreement
			KeyAgreement keyAgreement = KeyAgreement.getInstance(KEY_DH);
			// 用自己的私钥初始化keyAgreement
			keyAgreement.init(priKey);
			// 结合对方的公钥进行运算
			keyAgreement.doPhase(pubKey, true);
			// 开始生成本地密钥SecretKey 密钥算法为对称密码算法
			SecretKey secretKey = keyAgreement.generateSecret(KEY_DH_DES);
			return secretKey;
		} catch (Throwable e) {
			throw new OIUEException(StatusResult._ncriticalAbnormal, "", e);
		}
	}

	/**
	 * 从 Map 中取得公钥
	 * 
	 * @param keyMap key map
	 * @return byte[]
	 */
	public static byte[] getPublicKey(Map<String, Object> keyMap) {
		DHPublicKey key = (DHPublicKey) keyMap.get(PUBLIC_KEY);
		return key.getEncoded();
	}

	/**
	 * 从 Map 中取得私钥
	 * @param keyMap key map
	 * @return byte[]
	 */
	public static byte[] getPrivateKey(Map<String, Object> keyMap) {
		DHPrivateKey key = (DHPrivateKey) keyMap.get(PRIVATE_KEY);
		return key.getEncoded();
	}

	/**
	 * DH 加密
	 * 
	 * @param data 带加密数据
	 * @param publicKey 甲方公钥
	 * @param privateKey 乙方私钥
	 * @return byte[]
	 */
	public static byte[] encryptDH(byte[] data, byte[] publicKey, byte[] privateKey) {
		byte[] bytes = null;
		try {
			//
			SecretKey secretKey = getSecretKey(publicKey, privateKey);
			// 数据加密
			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			bytes = cipher.doFinal(data);
		} catch (Throwable e) {
			throw new OIUEException(StatusResult._ncriticalAbnormal, "", e);
		}
		return bytes;
	}

	/**
	 * DH 解密
	 * 
	 * @param data 待解密数据
	 * @param publicKey 乙方公钥
	 * @param privateKey 甲方私钥
	 * @return byte[]
	 */
	public static byte[] decryptDH(byte[] data, byte[] publicKey, byte[] privateKey) {
		byte[] bytes = null;
		try {
			//
			SecretKey secretKey = getSecretKey(publicKey, privateKey);
			// 数据加密
			Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			bytes = cipher.doFinal(data);
		} catch (Throwable e) {
			throw new OIUEException(StatusResult._ncriticalAbnormal, "", e);
		}
		return bytes;
	}
}
