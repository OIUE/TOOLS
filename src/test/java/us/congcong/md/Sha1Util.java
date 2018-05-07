package us.congcong.md;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1Util {
	public static byte[] sha1Encode(byte[] content) throws NoSuchAlgorithmException, InvalidKeyException {
		MessageDigest md = MessageDigest.getInstance("SHA");
		byte[] mdRes = md.digest(content);
		
		return mdRes;
	}
}
