package us.congcong.syencrypttest;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DESedeEncryptUtil {

	public static byte[] encrypt(byte[] content,Key dESKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, dESKey);
		byte[] encodeDESRes = cipher.doFinal(content);
		return encodeDESRes;
	}
	
	public static byte[] decrypt(byte[] content,Key dESKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, dESKey);
		byte[] decodeDESRes = cipher.doFinal(content);
		return decodeDESRes;
	}
}
