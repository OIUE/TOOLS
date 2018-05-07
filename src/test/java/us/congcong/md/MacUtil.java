package us.congcong.md;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MacUtil {
	public static byte[] macEncode(byte[] content, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
		// 对原始秘钥进行特性处理
		SecretKey restoreSecretKey = new SecretKeySpec(key, "HmacMD5");
		Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
		// 设置MAC秘钥
		mac.init(restoreSecretKey);
		// 编码
		byte[] macRes = mac.doFinal(content);
		
		return macRes;
	}
}
