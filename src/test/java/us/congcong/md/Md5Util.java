package us.congcong.md;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
	public static byte[] md5Encode(byte[] content) throws NoSuchAlgorithmException, InvalidKeyException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] mdRes = md.digest(content);
		
		return mdRes;
	}
}
