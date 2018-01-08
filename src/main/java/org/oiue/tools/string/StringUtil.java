/**
 * 
 */
package org.oiue.tools.string;

import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/** 
 * 类说明:
 *		
 * @author Every E-mail/MSN:mwgjkf@hotmail.com
 *               QQ:30130942
 *  StringUtil 1.0  Apr 18, 2009 2:06:54 PM
 * StringUtil
 */
@SuppressWarnings( { "unchecked","rawtypes"})
public class StringUtil {

	/**
	 * 
	 */
	public StringUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 方法说明：
	 *			判断字符串是否为空, 如果为 null 或者长度为0, 均返回 true
	 *CreateTime Apr 18, 2009 2:07:23 PM
	 * @param sourceStr 输入的字符串
	 * @return 为 null 或者长度为0, 均返回 true 否则返回false
	 */
	public static boolean isEmpty(String sourceStr) {
		return (sourceStr == null || sourceStr.length() == 0);
	}
	/**
	 * 方法说明：
	 *			判断字符串是否为空, 如果为 null 或者长度为0或者"null", 均返回 true
	 *CreateTime Apr 18, 2009 2:07:23 PM
	 * @param sourceStr 输入的字符串
	 * @return 为 null 或者长度为0, 均返回 true 否则返回false
	 */
	public static boolean isEmptys(String sourceStr) {
		return (sourceStr == null || sourceStr.length() == 0||sourceStr.trim().equalsIgnoreCase("null"));
	}
	
	/**
	 * 方法说明：
	 *			检验字符串是否为空, 如果是, 则返回给定的出错信息
	 *CreateTime Apr 18, 2009 2:08:45 PM
	 * @param sourceStr 源字符串
	 * @param errorMsg 出错信息
	 * @return 空串返回出错信息
	 */
	public static String isEmpty(String sourceStr, String errorMsg) {
		if (isEmpty(sourceStr)) {
			return errorMsg;
		}
		return "";
	}
	/**
	 * 转换逻辑词汇true
	 * @param bool 值
	 * @return 是否为true
	 */
	public static boolean isTrue(String bool) {
		if (bool==null) {
			return false;
		}else {
			bool=bool.toLowerCase();
			bool=bool.trim();
			if (bool.equals("true")||bool.equals("yes")||bool.equals("y")) {
				return true;
			}else {
				return false;
			}
		}
	}
	/**
	 * 转换逻辑词汇false
	 * @param bool 值
	 * @return 是否为false
	 */
	public static boolean isFalse(String bool) {
		if (bool==null) {
			return true;
		}else {
			bool=bool.toLowerCase();
			bool=bool.trim();
			if (bool.equals("false")||bool.equals("no")||bool.equals("n")) {
				return false;
			}else {
				return true;
			}
		}
	}
	
	/**
	 * 方法说明：
	 *			获得源字符串的字节长度(即二进制字节数), 用于发送短信时判断是否超出长度.
	 *CreateTime Apr 18, 2009 3:48:22 PM
	 * @param sourceStr 源字符串
	 * @return  源字符串的字节长度(即二进制字节数,不是 Unicode 长度)
	 */
	public static int getBytesLength(String sourceStr) {
		if (sourceStr == null) {
			return 0;
		}
		int bytesLength = sourceStr.getBytes().length;
		//System.out.println("bytes length is:" + bytesLength);
		return bytesLength;
	}
	
	/**
	 * 方法说明：
	 *			清除字符串结尾的空格.
	 *CreateTime Apr 18, 2009 3:56:56 PM
	 * @param sourceStr 源字符串
	 * @return 滤除后的字符串
	 */
	public static String trimTailSpaces(String sourceStr) {
		if (isEmpty(sourceStr)) {
			return "";
		}

		String trimedString = sourceStr.trim();

		if (trimedString.length() == sourceStr.length()) {
			return sourceStr;
		}

		return sourceStr.substring(0, sourceStr.indexOf(trimedString) + trimedString.length());
	}
	

	/**
	 * 方法说明：
	 *			将字符串转换为 int.
	 *CreateTime Apr 18, 2009 7:14:35 PM
	 * @param sourceStr 输入的字串
	 * @return 结果数字 如转换失败则返回 0
	 */
	public static int parseInt(String sourceStr) {
		try {
			return Integer.parseInt(sourceStr);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}
	
	/**
	 * 方法说明：
	 *			判断字符串是否全是数字字符.
	 *CreateTime Apr 18, 2009 7:36:52 PM
	 * @param sourceStr 输入的字符串
	 * @return 判断结果, true 为全数字, false 为还有非数字字符
	 */
	public static boolean isNumeric(String sourceStr) {
		if (isEmpty(sourceStr)) {
			return false;
		}

		for (int i = 0; i < sourceStr.length(); i++) {
			char charAt = sourceStr.charAt(i);

			if (!Character.isDigit(charAt)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 方法说明：
	 *			根据传入的字符串，返回字符串第一个数字以前的字符
	 *CreateTime Apr 22, 2009 11:57:56 AM
	 * @param sourceStr 源字符串
	 * @return 字符串第一个数字以前的字符
	 */
	public static String getFieldName(String sourceStr) {
		if (isEmpty(sourceStr)) {
			return "";
		}
		StringBuffer sBuffer=new StringBuffer();
		for (int i = 0; i < sourceStr.length(); i++) {
			char charAt = sourceStr.charAt(i);

			if (!Character.isDigit(charAt)) {
				sBuffer.append(charAt);
			}else {
				break;
			}
		}
		return sBuffer.toString();
	}
	
	/**
	 * 方法说明：
	 *			根据传入的字符串，返回字符串后面的数字
	 *CreateTime Apr 22, 2009 5:17:22 PM
	 * @param sourceStr 源字符串
	 * @return 字符串后面的数字
	 */
	public static int getFieldId(String sourceStr) {
		if (isEmpty(sourceStr)) {
			return 0;
		}
		StringBuffer sBuffer=new StringBuffer();
		for (int i = 0; i < sourceStr.length(); i++) {
			char charAt = sourceStr.charAt(i);

			if (Character.isDigit(charAt)) {
				sBuffer.append(charAt);
			}
		}
		if(sBuffer!=null&&sBuffer.length()>0){
			return Integer.parseInt(sBuffer.toString());
		}else{
			return -1;
		}
	}
	/**
	 * 方法说明：
	 *			转换字符串的内码.
	 *CreateTime Apr 18, 2009 7:43:30 PM
	 * @param sourceStr 源字符串
	 * @param sourceEncoding 源字符集名称
	 * @param targetEncoding 目标字符集名称
	 * @return 转换结果, 如果有错误发生, 则返回原来的值
	 * @throws UnsupportedEncodingException 异常
	 */
	public static String changeEncoding(String sourceStr, String sourceEncoding,String targetEncoding) throws UnsupportedEncodingException {
		if (isEmpty(sourceStr)) {
			return sourceStr;
		}
		byte[] bytes = sourceStr.getBytes(sourceEncoding);
		return new String(bytes, targetEncoding);
	}
	
	/**
	 * 方法说明：
	 *			转换数据的内码(从 ISO8859 转换到 UTF-8).
	 *CreateTime Apr 18, 2009 7:40:54 PM
	 * @param sourceStr 源字符串
	 * @return 转码后的字符串
	 * @throws UnsupportedEncodingException 异常
	 */
	public static String toUTF8(String sourceStr) throws UnsupportedEncodingException {
		return changeEncoding(sourceStr,"ISO8859-1", "UTF-8");
	}
	
	/**
	 * 方法说明：
	 *			转换数据的内码(从 UTF-8 转换到 ISO8859).
	 *CreateTime Apr 18, 2009 7:46:15 PM
	 * @param sourceStr 源字符串
	 * @return 转码后的字符串
	 * @throws UnsupportedEncodingException 异常
	 */
	public static String toISO(String sourceStr) throws UnsupportedEncodingException {
		return changeEncoding(sourceStr, "UTF-8", "ISO8859-1");
	}
	
	/**
	 * 方法说明：
	 *			Change the null string value to "", if not null, then return it self, use this to avoid display a null string to "null".
	 *CreateTime Apr 18, 2009 11:34:23 PM
	 * @param input the string to clearthe string to clear
	 * @return the result
	 */
	public static String clearNull(String input) {
		return isEmpty(input) ? "" : input;
	}
	
	/**
	 * 方法说明：
	 *			Return the limited length string of the input string (added at:April 10, 2004).
	 *CreateTime Apr 18, 2009 11:36:10 PM
	 * @param input String
	 * @param maxLength int 
	 * @return String processed result
	 */
	public static String limitStringLength(String input, int maxLength) {
		if (isEmpty(input))
			return "";

		if (input.length() <= maxLength) {
			return input;
		} else {
			return input.substring(0, maxLength - 3) + "...";
		}

	}
	/**
	 * 方法说明：
	 * 获取license授权
	 * @param license license
	 * @param index 下标
	 * @return 是否有权限
	 */
	public static boolean License(String license,int index){
		if (license==null||license.length()<=index) {
			return false;
		}
		char c=license.charAt(index);
		if (c=='1') {
			return true;
		}else {
			return false;
		}
	}
	/**
	 * 方法说明：
	 * 获取license授权
	 * @param license license
	 * @param index 下标
	 * @return 是否有权限
	 */
	public static boolean LicenseArray(String license,int index){
		if (license==null||license.length()<1) {
			return false;
		}
		String[] c=license.split(",");
		return LicenseArray(c, index);
	}
	/**
	 * 方法说明：
	 * 获取license授权
	 * @param license license
	 * @param index 下标
	 * @return 是否有权限
	 */
	public static boolean LicenseArray(String[] license,int index){
		if (license==null||license.length==0) {
			return false;
		}
		for (int i = 0; i < license.length; i++) {
			if (license[i]!=null&&license[i].equals(index+"")) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 首字母大写
	 * @param str 字符串
	 * @return 值
	 */
	public static String firstToUpper(String str){
		return str.substring(0,1).toUpperCase()+str.substring(1);
	}
	
	/**
	 * 解析一维参数串到Map
	 * @param par 参数串
	 * @param firstSeparator 一维分割符
	 * @param secondSeparator 键值分割符
	 * @return 结果
	 */
	public static Map parStr2Map(String par,String firstSeparator,String secondSeparator) {
		Map map=new HashMap();
		String[] strings=par.split(firstSeparator);
		for (String string : strings) {
			if (string!=null) {
				String[] st=string.split(secondSeparator);
				map.put(st[0].trim(), st.length>1?st[1].trim():"");
			}
		}
		return map;
	}
	/**
	 * 功能:解析以为参数串到二维数组
	 * 作者: Every
	 * 创建日期:2012-2-9
	 * @param par 字符串
	 * @param firstSeparator 一维分隔符
	 * @param secondSeparator 二维分隔符
	 * @return 结果
	 */
	public static List parStr2Arr(String par,String firstSeparator,String secondSeparator) {
		List list=new ArrayList();
		String[] strings=par.split(firstSeparator);
		for (String string : strings) {
			if (string!=null) {
				String[] st=string.split(secondSeparator);
				List lists=new ArrayList();
				lists.add(st[0].trim());
				lists.add(st.length>1?st[1].trim():"");
				list.add(lists);
			}
		}
		return list;
	}
	/**
	 * 功能:解析以为参数串到二维数组 键值相反
	 * 作者: Every
	 * 创建日期:2012-2-13
	 * @param par 字符串
	 * @param firstSeparator 一维分隔符
	 * @param secondSeparator 二维分隔符
	 * @return 结果
	 */
	public static List parStr2ArrR(String par,String firstSeparator,String secondSeparator) {
		List list=new ArrayList();
		String[] strings=par.split(firstSeparator);
		for (String string : strings) {
			if (string!=null) {
				String[] st=string.split(secondSeparator);
				List lists=new ArrayList();
				lists.add(st.length>1?st[1].trim():"");
				lists.add(st[0].trim());
				list.add(lists);
			}
		}
		return list;
	}
	/**
	 * 解析一维参数串到Map 键值与parStr2Map相反
	 * @param par 参数串
	 * @param firstSeparator 一维分割符
	 * @param secondSeparator 键值分割符
	 * @return 结果
	 */
	public static Map parStr2MapR(String par,String firstSeparator,String secondSeparator) {
		Map map=new HashMap();
		String[] strings=par.split(firstSeparator);
		for (String string : strings) {
			if (string!=null) {
				String[] st=string.split(secondSeparator);
				map.put( st.length>1?st[1].trim():"",st[0].trim());
			}
		}
		return map;
	}
	
	/**
	 * 分割字符串为list 暂时只支持一维分割
	 * @param source 源
	 * @param separator 分割符
	 * @return 结果
	 */
	public static List Str2List(String source,String separator){
		if(StringUtil.isEmptys(source)||StringUtil.isEmptys(separator)){
			return new ArrayList();
		}
		String[] strs=source.split(separator);
		return Arrays.asList(strs);
	}
	/**
	 * 括号匹配
	 * 		括号{{},[],()}是否匹配
	 * @param sourceStr 检查字符串
	 * @return 结果
	 */
	public static boolean BracketMatching(String sourceStr){
		if (isEmpty(sourceStr))
			return true;

		Deque<Character> stack = new ArrayDeque<Character>();
		boolean flag = true;
		char ch;
		for(int i = 0; i < sourceStr.length(); i++) {
			ch = sourceStr.charAt(i);
			if(ch == '(' || ch == '[' || ch == '{') {
				stack.offerFirst(ch);
			}else if(ch == ')') {
				if(stack.peekFirst() != null && stack.peekFirst() == '(') {
					stack.pollFirst();
				}else {
					flag = false;
					break;
				}
			}else if(ch == ']') {
				if(stack.peekFirst() != null && stack.peekFirst() == '[') {
					stack.pollFirst();
				}else {
					flag = false;
					break;
				}
			}else if(ch == '}') {
				if(stack.peekFirst() != null && stack.peekFirst() == '{') {
					stack.pollFirst();
				}else {
					flag = false;
					break;
				}
			}
		}
		
		if(flag) {
			flag = stack.isEmpty();
		}
		return flag;
	}

	public static void checkNull(Object object, String name) {   
        if (null == object) {   
            String message = MessageFormat.format("t", new String(name));   
            throw new NullPointerException(message);   
        }   
    }
    public static void checkString(String string, String name) {
		if (StringUtil.isEmpty(string)) {
			String message = MessageFormat.format("t", new String(name));
			throw new InvalidParameterException(message);
		}
	}
    /**
     * 表达式运算
     * @param epStr 表达式
     * @param per 值
     * @return 对象
     * @throws ScriptException Script Exception
     */
    public static Object scriptEngine(String epStr,Map per) throws ScriptException{
    	ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        for (Iterator iterator = per.keySet().iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			engine.put(type, per.get(type));
		}
        return engine.eval(epStr);
    }
}
