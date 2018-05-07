package us.congcong.md;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha5Util {
	public static byte[] sha5Encode(byte[] content) throws NoSuchAlgorithmException, InvalidKeyException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[] mdRes = md.digest(content);
		return mdRes;
	}
}
