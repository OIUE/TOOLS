package us.congcong.asyencrypttest;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * RSA加解密算法
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942
 * @site oiue.org
 *
 */
public class RSAEncryptUtil {
	/**
	 * 私钥加密公钥解密之私钥加密
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchPaddingException
	 */
	public static byte[] encryptByPriKey(byte[] content, byte[] privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, priKey);
		byte[] encodeRes = cipher.doFinal(content);
		
		return encodeRes;
	}
	
	/**
	 * 私钥加密公钥解密之公钥解密
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decryptByPubKey(byte[] content, byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, pubKey);
		byte[] decodeRes = cipher.doFinal(content);
		
		return decodeRes;
	}
	
	/**
	 * 公钥加密私钥解密之公钥加密
	 * @param content
	 * @param publicKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 */
	public static byte[] encryptByPubKey(byte[] content, byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		byte[] encodeRes = cipher.doFinal(content);
		
		return encodeRes;
	}
	
	/**
	 * 公钥加密私钥解密之私钥解密
	 * @param content
	 * @param privateKey
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public static byte[] decryptByPriKey(byte[] content, byte[] privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		Key priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		byte[] decodeRes = cipher.doFinal(content);
		
		return decodeRes;
	}
	
}
