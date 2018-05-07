package us.congcong.rsatest;

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
 * RSA 签名工具类
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942
 * @Site oiue.org/
 */
public class RSAUtils {
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
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey priKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKey));
		
		// 获取Signature对象，签名算法为SHA1WithRSA，此处还有较多可选择比如MD2withRSA/MD5withRSA/SHA1withRSA/SHA256withRSA等
		Signature signature = Signature.getInstance("SHA1WithRSA");
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
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKey));
			
			// 获取Signature对象，签名算法为SHA1WithRSA，此处还有较多可选择比如MD2withRSA/MD5withRSA/SHA1withRSA/SHA256withRSA等
			Signature signature = Signature.getInstance("SHA1WithRSA");
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