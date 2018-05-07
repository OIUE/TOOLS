package us.congcong.signtest;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942
 * @site oiue.org
 *
 */
public class DSAUtils {
	/**
	 * 签名
	 * @param content 原串
	 * @param privateKey 私有秘钥
	 * @return 签名值
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws SignatureException
	 */
	public static byte[] sign(byte[] content, byte[] privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		// 单例获取key工厂类，将拿到的privateKey创建PKCS8EncodedKeySpec对象，通过其获取PrivateKey对象
		KeyFactory keyFactory = KeyFactory.getInstance("DSA");
		PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
		
		// 获取Signature对象，签名算法为SHA1WithRSA
		Signature signature = Signature.getInstance("SHA1withDSA");
		signature.initSign(priKey);
		signature.update(content);
		byte[] encodeResult = signature.sign();
		
		return encodeResult;
	}
	
	/**
	 * 验签
	 * @param content 原串
	 * @param sign 签名
	 * @param publicKey 公有秘钥
	 * @return
	 */
	public static boolean verify(byte[] content, byte[] sign, byte[] publicKey) {
		try {
			// 单例获取key工厂类，将拿到的publicKey创建X509EncodedKeySpec对象，通过其获取PublicKey对象
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
			
			// 获取Signature对象，签名算法为SHA1withDSA
			Signature signature = Signature.getInstance("SHA1withDSA");
			signature.initVerify(pubKey);
			signature.update(content);
			boolean bverify = signature.verify(sign);
			
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
