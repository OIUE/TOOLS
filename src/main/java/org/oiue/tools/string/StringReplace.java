/**
 * 
 */
package org.oiue.tools.string;

/**
 * 类说明:
 * 
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942 StringReplace 1.0 Apr 18, 2009 12:15:14 PM StringReplace
 */
public class StringReplace {
	
	/**
	 * 
	 */
	public StringReplace() {
	}
	
	/**
	 * 方法说明： 将字符串 sourceStr 中的 oldChar 替换为 newChar CreateTime Apr 18, 2009 12:15:54 PM
	 * @param sourceStr 源字符串
	 * @param oldChar 原字符
	 * @param newChar 新字符
	 * @return 替换后的字符串
	 */
	public static String replaceChar(String sourceStr, char oldChar, char newChar) {
		if (sourceStr == null) {
			return "";
		}
		return sourceStr.replace(oldChar, newChar);
	}
	
	/**
	 * 方法说明： 将字符串 sourceStr 中的 oldStr 替换为 newStr, matchCase 为是否设置大小写敏感查找 CreateTime Apr 18, 2009 12:28:05 PM
	 * @param sourceStr 需要替换的源字符串
	 * @param oldStr 需要被替换的老字符串
	 * @param newStr 替换为的新字符串
	 * @param matchCase 是否需要按照大小写敏感方式查找
	 * @return 替换后的字符串
	 */
	public static String replace(String sourceStr, String oldStr, String newStr, boolean matchCase) {
		if (sourceStr == null) {
			return null;
		}
		// 首先检查旧字符串是否存在, 不存在就不进行替换
		if (sourceStr.toLowerCase().indexOf(oldStr.toLowerCase()) == -1) {
			return sourceStr;
		}
		int findStartPos = 0;
		int a = 0;
		while (a > -1) {
			int b = 0;
			String str1, str2, str3, str4, strA, strB;
			str1 = sourceStr;
			str2 = str1.toLowerCase();
			str3 = oldStr;
			str4 = str3.toLowerCase();
			if (matchCase) {
				strA = str1;
				strB = str3;
			} else {
				strA = str2;
				strB = str4;
			}
			a = strA.indexOf(strB, findStartPos);
			if (a > -1) {
				b = oldStr.length();
				findStartPos = a + b;
				StringBuffer bbuf = new StringBuffer(sourceStr);
				sourceStr = bbuf.replace(a, a + b, newStr) + "";
				// 新的查找开始点位于替换后的字符串的结尾
				findStartPos = findStartPos + newStr.length() - b;
			}
		}
		return sourceStr;
	}
	
	/**
	 * 方法说明： 将字符串 sourceStr 中的 oldStr 替换为 newStr, 并以大小写不敏感方式进行查找替换 CreateTime Apr 18, 2009 12:31:02 PM
	 * @param sourceStr 需要替换的源字符串
	 * @param oldStr 需要被替换的老字符串
	 * @param newStr 替换为的新字符串
	 * @return 替换后的字符串
	 */
	public static String replaceStr(String sourceStr, String oldStr, String newStr) {
		return replace(sourceStr, oldStr, newStr, false);
	}
	
	/**
	 * 方法说明： 将字符串 sourceStr 中的 oldStr 替换为 newStr, 并以大小写敏感方式进行查找替换 CreateTime Apr 18, 2009 12:25:07 PM
	 * @param sourceStr 需要替换的源字符串
	 * @param oldStr 替换掉的字符串
	 * @param newStr 替换成新字符串
	 * @return 替换后的字符串
	 */
	public static String replaceString(String sourceStr, String oldStr, String newStr) {
		if (sourceStr == null || oldStr == null || newStr == null) {
			return "";
		}
		return sourceStr.replaceAll(oldStr, newStr);
	}
	
}
