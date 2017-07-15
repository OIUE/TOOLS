package org.oiue.tools.bytes;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class ByteUtil {

	/**
	 * GBK编码
	 * @param src 操作源
	 * @return 结果
	 */
	public static byte[] getBytesGBK(String src) {
		if(src == null) {
			throw new IllegalArgumentException("src不能为空");
		}
		try {
			return src.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			throw new Error("不支持GBK编码：" + e.getMessage());
		}
	}
	/**
	 * 判断部分字节内容相等
	 * @param src bytes 源
	 * @param starts 对比对象
	 * @return 是否相等  
	 */
	public static boolean startsWith(byte[] src, byte[] starts) {
		if(src == null || starts == null || src.length < starts.length) {
			return false;
		}
		for(int i=0; i<starts.length; i++) {
			if((src[i] & 0xFF) != (starts[i] & 0xFF)) {
				return false;
			}
		}
		return true;
	}

	public static byte[] long2bytes(long v,int len){
		byte[] b = new byte[len];  
	    for (int i=7;i>0&&i>(8-len);i--) { 
	        b[i-(8-len)] = (byte)(v>>>(56-(i*8)));  
	    }  
	    return b;
	}

	public static byte[] double2bytes(double v,int len){
		byte[] b = new byte[len];  
		long l = Double.doubleToLongBits(v);  
		for (int i=0;i<len&&i<8;i++) {  
			b[i] = new Long(l).byteValue();  
	        l = l >> 8;  
		}  
		return b;
	}
	/**
    * 将64位的long值放到8字节的byte数组 
    * @param v 数值 
    * @return 返回转换后的byte数组 
    */  
   public static byte[] long2bytes(long v) {  
       return long2bytes(v, 8);  
   }  
   /** 
    * 将8字节的byte数组转成一个long值 
    * @param bs byteArray 
    * @return 转换后的long型数值 
    */  
	public static long bytes2long(byte[] bs) {
		long rl=0;
		if(bs==null){
			throw new NullPointerException("字节数组不能为空");
		}
		if(bs.length > 8) {
			throw new IllegalArgumentException("字节数组长度超过8个字节，不能转换成long类型：" + toHexString(bs));
		}
		for (int i = 0; i < bs.length; i++) {
			rl+=(bs[i]& 0xff) << (56-(i*8));
		}
		return rl;
	}

	/**
	 * Int转换为字节数组，如果转换的字节长度小于4，则忽略相应高位字节
	 * @param v 值
	 * @param len 转换后的字节长度
	 * @return 字节由高位到低位存储在数组中索引由低到高(索引0存放最高位，索引1存放次高位...)
	 */
	public static byte[] int2bytes(int v, int len) throws IllegalArgumentException {
		if(len <= 0 || len > 4) {
			throw new IllegalArgumentException("转换后的字节数组长度必须大于0且小于等于4：" + len);
		}
		//下述两种方式均可实现相同功能，鉴于代码可读性原则，因此采用第二种实现方式
//		第一种实现方式
//		byte[] ret = new byte[len];
//		switch(len) {
//			case 4:
//				ret[4-len]/*tmp[0]*/ = (byte)(v >>> 24 &0xFF);
//			case 3:
//				ret[Math.abs(3-len)]/*tmp[1]*/ = (byte)((v >>> 16) &0xFF);
//			case 2:
//				ret[Math.abs(2-len)]/*tmp[2]*/ = (byte)((v >>> 8) &0xFF);
//			case 1:
//				ret[Math.abs(1-len)]/*tmp[3]*/ = (byte)v;
//			default:
////				throw new Error("");
//		}
//		return ret;	
//		第二种实现方式
		byte[] tmp = new byte[4];
		switch(len) {
			case 4:
				tmp[0] = (byte)(v >>> 24 &0xFF);
			case 3:
				tmp[1] = (byte)((v >>> 16) &0xFF);
			case 2:
				tmp[2] = (byte)((v >>> 8) &0xFF);
			case 1:
				tmp[3] = (byte)v;
			default:
//				throw new Error("");
		}
		byte[] ret = new byte[len];
		System.arraycopy(tmp, 4 - len, ret, 0, len);
		return ret;	
	}
	/**
	 * 转换从字节数组中0索引开始的4个字节为Int类型
	 * @param b 数组
	 * @return 值
	 * @throws NullPointerException 数组为空
	 * @throws IllegalArgumentException 转换错误
	 */
	public static int bytes2Int(byte ... b) throws NullPointerException, IllegalArgumentException {
		return bytes2Int(b, 0);
	}
	/**
	 * 转换从字节数组中pos索引开始的4个字节为Int类型
	 * @param b 数组
	 * @param pos 索引
	 * @return 值
	 * @throws NullPointerException 数组为空
	 * @throws IllegalArgumentException 转换错误
	 */
	public static int bytes2Int(byte[] b, int pos) throws NullPointerException, IllegalArgumentException {
		if(b == null) {
			throw new NullPointerException("字节数组不能为空");
		}
		if(b.length - pos < 4) {
			return bytes2Int(b, pos, b.length - pos);
		} else {
			return bytes2Int(b, pos, 4);	
		}
	}
	/**
	 * 转换从字节数组中pos索引开始的len个字节为Int类型
	 * @param b 数组
	 * @param pos 索引
	 * @param len 小于等于4字节
	 * @return 值
	 */
	public static int bytes2Int(byte[] b, int pos, int len) {
		if(b == null) {
			throw new NullPointerException("字节数组不能为空");
		}
		if(pos < 0 || len <= 0 || len > 4 || (pos + len) > b.length) {
			throw new IllegalArgumentException("pos(" + pos + ") < 0 || len(" + len + ") <= 0 || len > 4 || (pos + len) > b.length(" + b.length + ")");
		}
		int v = 0;
		switch(len) {
			case 4:
				v |= (b[pos++] << 24) & 0xFF000000;
			case 3:
				v |= (b[pos++] << 16) & 0x00FF0000;
			case 2:
				v |= (b[pos++] << 8) & 0x0000FF00;
			case 1:
				v |= b[pos++] & 0xFF;
			default:
//				throw new Error("");
		}
		return v;
	}
	/**
	 * 将字节数组按BCD解码成10进制的Int类型
	 * @param bs 数组
	 * @return 值
	 * @throws NullPointerException 数组为空
	 * @throws IllegalArgumentException 转换错误
	 */
	public static int bcdBytes2Int(byte... bs) throws NullPointerException, IllegalArgumentException {
		if(bs == null) {
			throw new NullPointerException("字节数组不能为空");
		}
		if(bs.length > 4) {
			throw new IllegalArgumentException("字节数组长度超过4个字节，不能转换成Int类型：" + toHexString(bs));
		}
		return Integer.parseInt(bcdBytes2Str(bs));
	}
	
	/**
	 * 10进制数转BCD码字节
	 * @param hRadix10 转换至
	 * @param lRadix10 标识
	 * @return 值
	 */
	private static byte int2bcdByte(int hRadix10, int lRadix10) {
		int bcd = ((hRadix10 & 0x0F) << 4) + (lRadix10 & 0x0F);
		return (byte)bcd;
	}

	/**
	 * 将一个字节按BCD解码成10进制的String类型
	 * @param b 值
	 * @return 值
	 * @throws IllegalArgumentException 转换错误
	 */
	public static String bcdBytes2Str(byte b) throws IllegalArgumentException {
		int high4 = ((b&0xF0) >>> 4);
		int low4 = (b&0x0F);
		if(high4 >= 10 || low4 >= 10) {
			throw new IllegalArgumentException("BCD码转换过程中有大于9的值：" + hex(b, true));
		}
		return String.valueOf(high4) + String.valueOf(low4);
	}
	/**
	 * 将字节数组按BCD解码成10进制的String类型
	 * @param bs 数组
	 * @return 值
	 * @throws IllegalArgumentException 转换错误
	 */
	public static String bcdBytes2Str(byte... bs) throws IllegalArgumentException {
		if(bs == null || bs.length <= 0) {
			throw new IllegalArgumentException("字节数组不能为空，且长度不能为0:" + bs == null ? "null" : String.valueOf(bs.length));
		}
		StringBuilder bcd = new StringBuilder();
		try {
			for(byte b : bs) {
				int high4 = ((b&0xF0) >>> 4);
				int low4 = (b&0x0F);
				if(high4 >= 10 || low4 >= 10) {
					throw new IllegalArgumentException("BCD码转换过程中有大于9的值：" + Integer.toHexString(b & 0xFF));
				}
				bcd.append(high4).append(low4);
			}
		} catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("转换BCD码失败：" + toHexString(bs) + " " + e.getMessage());
		}
		return bcd.toString();
	}
	/**
	 * 10进制字符串转换成BCD编码的字节
	 * @param radix10Str 值
	 * @param len 字节长度
	 * @return 数组
	 */
	public static byte[] str2bcdBytes(String radix10Str, int len) {
		if(radix10Str == null) {
			throw new NullPointerException("radix10Str不能为空");
		}
		if(radix10Str.length() != (len * 2)) {
			throw new IllegalArgumentException("字符串长度与字节长度不一致：" + radix10Str + ", 字节长度" + len + " * 2");
		}
		return str2bcdBytes(radix10Str, len, 0);
	}
	/**
	 * 10进制字符串转换成BCD编码的字节
	 * @param radix10Str  数值
	 * @param len 字节长度
	 * @param additional 位数不足时补充的内容(前面填充)
	 * @return 转换后
	 */
	public static byte[] str2bcdBytes(String radix10Str, int len, int additional) {
		if(len <= 0) {
			throw new IllegalArgumentException("len必须大于0：" + len);
		}
		//BCD码个数为字节数的两倍
		int bcdLen = len * 2;
		if(radix10Str == null || radix10Str.length() > bcdLen) {
			throw new IllegalArgumentException("10进制字符串不能为空并且长度不能大于限定的len: " + radix10Str + 
					(radix10Str == null ? "" : ", " + bcdLen));
		}
		if(additional < 0 || additional > 9) {
			throw new IllegalArgumentException("位数不足时的补充内容只能为0～9：" + additional);
		}
		if(!radix10Str.matches("\\d*")) {
			throw new IllegalArgumentException("字符串中含有非数字内容：" + radix10Str);
		}
		int[] radix10Int = new int[bcdLen];
		
		int addLen = radix10Int.length - radix10Str.length();
		//将字符串转换为int数组，每个字符转换成相应10进制数
		for(int i=0, radix10StrIndex=0; i<radix10Int.length; i++) {
			//位数不足时，以additional填充
			if(i < addLen) {
				radix10Int[i] = additional;
				continue;
			}
			radix10Int[i] = Character.digit(radix10Str.charAt(radix10StrIndex++), 10);
		}
		
		byte[] bcd = new byte[len];
		//将10进制转换为BCD码，从低位开始，每两个10进制数转换为1个字节
		for(int i=len-1, j=radix10Int.length-1; i>=0; i--,j-=2) {
			bcd[i] = int2bcdByte(radix10Int[j-1], radix10Int[j]);
		}
		return bcd;
	}
	
	/**
	 * 合并两个byte数组
	 * @param s 源
	 * @param d 源2
	 * @return 合并后
	 */
	public static byte[] join(byte[] s,byte[] d){
		if(d==null)return s;
		if(s==null)return d;
		byte[] rtn = new byte[s.length+d.length];
		System.arraycopy(s, 0, rtn, 0, s.length);
		System.arraycopy(d, 0, rtn, s.length, d.length);
		return rtn;
	}
	
	/**
	 * 返回bytes数组中从后向前第一个不是0的下标值
	 * @param bytes 数组
	 * @return 下标
	 */
	public static int charAtReverseNot0(byte[] bytes) {
		if(bytes == null) {
			throw new IllegalArgumentException("bytes不能为空");
		}
		for(int i=bytes.length-1; i>=0; i--) {
			if(bytes[i] != 0) {
				return i;
			}
		}
		return 0;
	}
	/**
	 * 将数值转换成相应的字符
	 * @param val 值
	 * @param upCase 大写
	 * @return 字符
	 */
	private static char hex(int val, boolean upCase) {
		switch(val) {
			case 0:
				return '0';
			case 1:
				return '1';
			case 2:
				return '2';
			case 3:
				return '3';
			case 4:
				return '4';
			case 5:
				return '5';
			case 6:
				return '6';
			case 7:
				return '7';
			case 8:
				return '8';
			case 9:
				return '9';
			case 10:
				return upCase ? 'A' : 'a';
			case 11:
				return upCase ? 'B' : 'b';
			case 12:
				return upCase ? 'C' : 'c';
			case 13:
				return upCase ? 'D' : 'd';
			case 14:
				return upCase ? 'E' : 'e';
			case 15:
				return upCase ? 'F' : 'f';
			default:
				throw new IllegalArgumentException("val必须为0～15的数值：" + val);
		}
	}
	private static final char[] HEX_UPPERCASE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		'A', 'B', 'C', 'D', 'E', 'F'};
	/**
	 * 将字节以16进制字符串显示
	 * @param bytes 数组
	 * @return 字符串
	 */
	public static String toHexString(byte... bytes) {
		if(bytes == null) {
			return "";
		}
		return toHexString(bytes, ' ', 0, bytes.length);
	}
	/**
	 * 将字节以16进制字符串显示
	 * @param bytes 数组
	 * @param separated 数组
	 * @param pos 数组
	 * @param len 数组
	 * @return 字符串
	 */
	public static String toHexString(byte[] bytes, char separated, int pos, int len) {
		if(bytes == null || bytes.length <= 0 || len == 0) {
			return "";
		}
		if(pos < 0 || len < 0 || (pos + len) > bytes.length) {
			throw new IllegalArgumentException("参数范围错误：pos(" + pos + ") < 0 || len(" + len + ") < 0 " +
					"|| (pos + len) > bytes.length(" + bytes.length + ")");
		}
		StringBuilder hexStr = new StringBuilder();
		for(int i=pos; i<(pos+len); i++) {
			char h4 = HEX_UPPERCASE[((bytes[i] & 0xF0) >>> 4)];
			char l4 = HEX_UPPERCASE[(bytes[i] & 0x0F)];
			hexStr.append(h4).append(l4).append(separated);
		}
		hexStr.deleteCharAt(hexStr.length() - 1);
		return hexStr.toString();
	}
	public static String toHexString(ByteBuffer buf) {
		return toHexString(buf, buf.position(), buf.remaining());
	}
	public static String toHexString(ByteBuffer buf, int pos, int len) {
		if(buf == null || buf.capacity() <= 0 || len == 0) {
			return "";
		}
		if(pos < 0 || len < 0 || (pos + len) > buf.capacity()) {
			throw new IllegalArgumentException("参数范围错误：pos(" + pos + ") < 0 || len(" + len + ") < 0 " +
					"|| (pos + len) > bytes.length(" + buf.capacity() + ")");
		}
		StringBuilder hexStr = new StringBuilder();
		for(int i=pos; i<(pos+len); i++) {
			byte b = buf.get(i);
			char h4 = HEX_UPPERCASE[((b & 0xF0) >>> 4)];
			char l4 = HEX_UPPERCASE[(b & 0x0F)];
			hexStr.append(h4).append(l4).append(' ');
		}
		hexStr.deleteCharAt(hexStr.length() - 1);
		return hexStr.toString();
	}
	
	public static byte[] toBytes4HexString(String str){
		if(str==null)
			return new byte[0];
		
		String[] strArr = str.split(" ");
		byte[] array = new byte[strArr.length];
		for(int i=0; i<strArr.length; i++) {
			array[i] = (byte)Integer.parseInt(strArr[i], 16);
		}
		return array;
	}
}
