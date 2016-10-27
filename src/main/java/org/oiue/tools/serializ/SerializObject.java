/**
 * 
 */
package org.oiue.tools.serializ;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.oiue.tools.bytes.ByteUtil;


/** 
 * 类说明:
 *		序列化对象
 * @author Every E-mail/MSN:mwgjkf@hotmail.com
 *               QQ:30130942
 * @version SerializObject 1.0：Feb 11, 2010 10:02:00 AM 
 */
public class SerializObject {

	/**
	 * 将对象序列化保存为文件
	 * @param o 实现了Serializable接口的对象
	 * @param path 文件保存路径
	 * @return
	 * @throws IOException
	 */
	public static boolean serializObj2File(Object o,String path) throws IOException{
		// 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(o);
		File file = new File(path);
//		if (!file.exists()) {
//			file.createNewFile();
//		}
		DataOutputStream to = new DataOutputStream(new FileOutputStream(file));
		bos.writeTo(to);
		return true;
	}
	/**
	 * 将对象序列化成字符串
	 * @param o 实现了Serializable接口的对象
	 * @return 序列化的字符串
	 * @throws IOException
	 */
	public static String serializObj2String(Object o) throws IOException{
		// 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(o);
		return ByteUtil.toHexString(bos.toByteArray());
	}
	
	/**
	 * 将文件反序列化成对实例化对象
	 * @param path 文件路径
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object serializFile2Obj(String path) throws IOException, ClassNotFoundException{
		File file = new File(path);
		if (!file.exists() || file.isDirectory())
			throw new FileNotFoundException();
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		byte[] temp = new byte[1024];
		int size = 0;
		while ((size = in.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		in.close();
		// 将流序列化成对象
		ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}
	/**
	 * 将字符串反序列化成对实例化对象
	 * @param line 序列化字符串
	 * @return 序列化的对象
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object serializString2Obj(String line) throws IOException, ClassNotFoundException{
		// 将流序列化成对象
		ByteArrayInputStream bis = new ByteArrayInputStream(ByteUtil.toBytes4HexString(line));
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}
	/**
	 * 将二进制转换成字符串
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuffer sb = new StringBuffer();
		String tmp = "";
		for (int i = 0; i < b.length; i++) {
			tmp = Integer.toHexString(b[i] & 0XFF);
			if (tmp.length() == 1) {
				sb.append("0" + tmp);
			} else {
				sb.append(tmp);
			}
		}
		return sb.toString();
	}
	/**
	 * 将字符串转换成二进制数组
	 * @param str
	 * @return
	 */
	public static byte[] hex2byte(String str) {
		if (str == null) {
			return null;
		}

		str = str.trim();
		int len = str.length();

		if (len == 0 || len % 2 == 1) {
			return null;
		}

		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}
}
