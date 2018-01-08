package org.oiue.tools.security;
import java.util.Map;

import org.oiue.tools.bytes.ByteUtil;
import org.oiue.tools.security.SecurityUtil;

public class Test {
	// 待加密的明文
	public static final String DATA = "userPass";

	public static void main(String[] args) throws Exception {
		/* Test DH */
		// 甲方公钥
		byte[] publicKey1;
		// 甲方私钥
		byte[] privateKey1;
		// 甲方本地密钥
		byte[] secretKey1;
		// 乙方公钥
		byte[] publicKey2;
		// 乙方私钥
		byte[] privateKey2;
		// 乙方本地密钥
		byte[] secretKey2;

		// 初始化密钥 并生成甲方密钥对
		Map<String, Object> keyMap1 = SecurityUtil.initKey();
		publicKey1 = SecurityUtil.getPublicKey(keyMap1);
		privateKey1 = SecurityUtil.getPrivateKey(keyMap1);
		System.out.println("DH 甲方公钥 : " + ByteUtil.toHexString(publicKey1));
		System.out.println("DH 甲方私钥 : " + ByteUtil.toHexString(privateKey1));

		// 乙方根据甲方公钥产生乙方密钥对
		Map<String, Object> keyMap2 = SecurityUtil.initKey(publicKey1);
		publicKey2 = SecurityUtil.getPublicKey(keyMap2);
		privateKey2 = SecurityUtil.getPrivateKey(keyMap2);
		System.out.println("DH 乙方公钥 : " + ByteUtil.toHexString(publicKey2));
		System.out.println("DH 乙方私钥 : " + ByteUtil.toHexString(privateKey2));

		// 对于甲方， 根据其私钥和乙方发过来的公钥， 生成其本地密钥secretKey1
		secretKey1 = SecurityUtil.getSecretKeyBytes(publicKey2, privateKey1);
		System.out.println("DH 甲方 本地密钥 : " + ByteUtil.toHexString(secretKey1));

		// 对于乙方， 根据其私钥和甲方发过来的公钥， 生成其本地密钥secretKey2
		secretKey2 = SecurityUtil.getSecretKeyBytes(publicKey1, privateKey2);
		System.out.println("DH 乙方 本地密钥 : " + ByteUtil.toHexString(secretKey2));
		// ---------------------------
		// 测试数据加密和解密
		System.out.println("加密前的数据" + DATA);
		// 甲方进行数据的加密
		// 用的是甲方的私钥和乙方的公钥
		byte[] encryptDH = SecurityUtil.encryptDH(DATA.getBytes(), publicKey2, privateKey1);
		System.out.println("加密后的数据 字节数组转16进制显示" + ByteUtil.toHexString(encryptDH));
		// 乙方进行数据的解密
		// 用的是乙方的私钥和甲方的公钥
		byte[] decryptDH = SecurityUtil.decryptDH(encryptDH, publicKey1, privateKey2);
		System.out.println("解密后数据:" + new String(decryptDH));
	}
}