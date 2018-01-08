/**
 * 
 */
package org.oiue.tools.string;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletContext;

/** 
 * 类说明:
 *		
 * @author Every E-mail/MSN:mwgjkf@hotmail.com
 *               QQ:30130942
 *  StringForPage 1.0  Apr 18, 2009 2:03:02 PM
 * StringToPage
 */
public class StringForPage {

	/**
	 * 方法说明：
	 *			滤除帖子中的危险 HTML 代码, 主要是脚本代码, 滚动字幕代码以及脚本事件处理代码
	 *CreateTime Apr 18, 2009 2:11:50 PM
	 * @param content 需要滤除的字符串
	 * @return 过滤的结果
	 */
	public static String replaceHtmlCode(String content) {
		if (StringUtil.isEmpty(content)) {
			return "";
		}
		// 需要滤除的脚本事件关键字
		String[] eventKeywords = { "onmouseover", "onmouseout", "onmousedown",
				"onmouseup", "onmousemove", "onclick", "ondblclick",
				"onkeypress", "onkeydown", "onkeyup", "ondragstart",
				"onerrorupdate", "onhelp", "onreadystatechange", "onrowenter",
				"onrowexit", "onselectstart", "onload", "onunload",
				"onbeforeunload", "onblur", "onerror", "onfocus", "onresize",
				"onscroll", "oncontextmenu" };
		content = StringReplace.replace(content, "<script", "&ltscript", false);
		content = StringReplace.replace(content, "</script", "&lt/script", false);
		content = StringReplace.replace(content, "<marquee", "&ltmarquee", false);
		content = StringReplace.replace(content, "</marquee", "&lt/marquee", false);
		// FIXME 加这个过滤换行到 BR 的功能会把原始 HTML 代码搞乱 2006-07-30
		//content = replace(content, "\r\n", "<BR>");
		// 滤除脚本事件代码
		for (int i = 0; i < eventKeywords.length; i++) {
			// 添加一个"_", 使事件代码无效
			content = StringReplace.replace(content, eventKeywords[i],"_" + eventKeywords[i], false); 
		}
		return content;
	}
	
	/**
	 * 方法说明：
	 *			滤除 HTML 标记,因为 XML 中转义字符依然有效, 因此把特殊字符过滤成中文的全角字符.
	 *CreateTime Apr 18, 2009 2:14:47 PM
	 * @param sourceStr 输入的字串
	 * @return 过滤后的字串
	 */
	public static String setTag(String sourceStr) {
		int j = sourceStr.length();
		StringBuffer stringbuffer = new StringBuffer(j + 500);
		char ch;
		for (int i = 0; i < j; i++) {
			ch = sourceStr.charAt(i);
			if (ch == '<') {
				stringbuffer.append("&lt");
//				stringbuffer.append("〈");
			} else if (ch == '>') {
				stringbuffer.append("&gt");
//				stringbuffer.append("〉");
			} else if (ch == '&') {
				stringbuffer.append("&amp");
//				stringbuffer.append("〃");
			} else if (ch == '%') {
				stringbuffer.append("%%");
//				stringbuffer.append("※");
			} else {
				stringbuffer.append(ch);
			}
		}

		return stringbuffer.toString();
	}
	
	/**
	 * 方法说明：
	 *			滤除 BR 代码
	 *CreateTime Apr 18, 2009 2:44:46 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String setBr(String sourceStr) {
		int j = sourceStr.length();
		StringBuffer stringbuffer = new StringBuffer(j + 500);
		for (int i = 0; i < j; i++) {

			if (sourceStr.charAt(i) == '\n' || sourceStr.charAt(i) == '\r') {
				continue;
			}
			stringbuffer.append(sourceStr.charAt(i));
		}

		return stringbuffer.toString();
	}
	
	/**
	 * 方法说明：
	 *			滤除空格
	 *CreateTime Apr 18, 2009 2:46:10 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String setNbsp(String sourceStr) {
		int j = sourceStr.length();
		StringBuffer stringbuffer = new StringBuffer(j + 500);
		for (int i = 0; i < j; i++) {
			if (sourceStr.charAt(i) == ' ') {
				stringbuffer.append("&nbsp;");
			} else {
				stringbuffer.append(sourceStr.charAt(i) + "");
			}
		}
		return stringbuffer.toString();
	}
	
	/**
	 * 方法说明：
	 *			将',",\r\n替换成数据库内可存储的字符
	 *CreateTime Apr 18, 2009 2:49:53 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String toQuoteMark(String sourceStr) {
		sourceStr = StringReplace.replaceStr(sourceStr, "'", "&#39;");
		sourceStr = StringReplace.replaceStr(sourceStr, "\"", "&#34;");
		sourceStr = StringReplace.replaceStr(sourceStr, "\r\n", "\n");
		return sourceStr;
	}
	
	/**
	 * 方法说明：
	 *			替换 替换为网页上显示的文本
	 *CreateTime Apr 18, 2009 2:50:53 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String toHtml(String sourceStr) {
		sourceStr = StringReplace.replaceStr(sourceStr, "<", "&#60;");
		sourceStr = StringReplace.replaceStr(sourceStr, ">", "&#62;");
		return sourceStr;
	}
	
	/**
	 * 方法说明：
	 *			将 TEXT 文本转换为 HTML 代码, 已便于网页正确的显示出来.
	 *CreateTime Apr 18, 2009 3:31:46 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String textToHtml(String sourceStr) {
		if (StringUtil.isEmpty(sourceStr)) {
			return "";
		}

		sourceStr = StringReplace.replaceStr(sourceStr, "<", "&#60;");
		sourceStr = StringReplace.replaceStr(sourceStr, ">", "&#62;");

		sourceStr = StringReplace.replaceStr(sourceStr, "\n", "<br>\n");
		sourceStr = StringReplace.replaceStr(sourceStr, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		sourceStr = StringReplace.replaceStr(sourceStr, "  ", "&nbsp;&nbsp;");

		return sourceStr;
	}
	
	/**
	 * 方法说明：
	 *			滤除 HTML 代码 为文本代码.
	 *CreateTime Apr 18, 2009 3:33:46 PM
	 * @param sourceStr 源串
	 * @return 结果
	 */
	public static String replaceHtmlToText(String sourceStr) {
		if (StringUtil.isEmpty(sourceStr)) {
			return "";
		}
		return setBr(setTag(sourceStr));
	}
	/**
	 * 方法说明：
	 *			将\n,\t, 等替换成网页上显示的文本
	 *CreateTime Apr 18, 2009 2:52:15 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String toBR(String sourceStr) {
		sourceStr = StringReplace.replaceStr(sourceStr, "\n", "<br>\n");
		sourceStr = StringReplace.replaceStr(sourceStr, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		sourceStr = StringReplace.replaceStr(sourceStr, "  ", "&nbsp;&nbsp;");
		return sourceStr;
	}
	
	
	/**
	 * 方法说明：
	 *			将文本中的\n替换成页面的换行<br>
	 *CreateTime Apr 18, 2009 2:53:16 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String replaceEnter(String sourceStr){
		return StringReplace.replaceStr(sourceStr, "\n", "<br>");
	}
	
	/**
	 * 方法说明：
	 *			将文本中的<br>换行替换成\n
	 *CreateTime Apr 18, 2009 2:56:25 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String replacebr(String sourceStr){
		return StringReplace.replaceStr(sourceStr,"<br>", "\n");
	}
	
	/**
	 * 方法说明：
	 *			将字符串中可能出现的'替换成能插入mysql的''
	 *CreateTime Apr 18, 2009 3:04:50 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String replaceQuote(String sourceStr){
		return StringReplace.replaceStr(sourceStr,"'", "''");
	}

	/**
	 * 方法说明：
	 *			将字符串中的\r\n替换成\n
	 *CreateTime Apr 18, 2009 3:20:45 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String toSQL(String sourceStr) {
		return StringReplace.replaceStr(sourceStr, "\r\n", "\n");
	}
	
	/**
	 * 方法说明：
	 *			SQL语句转换
	 *			将单个的 ' 换成 '';
	 *			SQL 规则:如果单引号中的字符串包含一个嵌入的引号,可以使用两个单引号表示嵌入的单引号.
	 *CreateTime Apr 18, 2009 3:23:01 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String replaceSql(String sourceStr) {
		return StringReplace.replaceStr(sourceStr, "'", "''");
	}
	
	/**
	 * 方法说明：
	 *			将字符串中可能出现的\"替换成能\\\"
	 *CreateTime Apr 18, 2009 3:07:18 PM
	 * @param sourceStr 源字符串
	 * @return  滤除后的字符串
	 */
	public static String replacefu(String sourceStr) {
		return StringReplace.replaceStr(sourceStr,"\"", "\\\"");
	}
	
	/**
	 * 方法说明：
	 *			过滤URL中的参数中的特殊字符
	 *CreateTime Apr 18, 2009 5:58:14 PM
	 * @param src 需编码的字符串
	 * @return 编码后的字符串
	 */
	public static String escape(String src) {
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 方法说明：
	 *			完整过滤URL中的参数中的特殊字符
	 *CreateTime Apr 18, 2009 6:00:57 PM
	 * @param src 需编码的字符串
	 * @return 编码后的字符串
	 */
	public static String escapeSrc(String src) {
		return escape(escape(src));
	}
	
	/**
	 * 方法说明：
	 *			解析被过滤的参数
	 *CreateTime Apr 18, 2005 6:53:01 PM
	 * @param src 需解析的字符串
	 * @return 解析后的参数
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	
	/**
	 * 方法说明：
	 *			完整解析被编码的参数		
	 *CreateTime Apr 18, 2009 6:55:31 PM
	 * @param src 解析被编码的参数
	 * @return 解析后的参数
	 */
	public static String unescapeSrc(String src) {
		return unescape(unescape(src));
	}
	
	/**
	 * 方法说明：
	 *			对给定字符进行 URL 编码 编码格式为UTF-8
	 *CreateTime Apr 18, 2009 7:01:04 PM
	 * @param src 需编码的字符串
	 * @return 编码后的字符串
	 * @throws UnsupportedEncodingException 异常
	 */
	public static String encode(String src) throws UnsupportedEncodingException {
		if (StringUtil.isEmpty(src)) {
			return "";
		}
		return URLEncoder.encode(src, "UTF-8");
	}
	
	/**
	 * 方法说明：
	 *			对给定字符进行 URL 解码 解码UTF-8
	 *CreateTime Apr 18, 2009 7:09:05 PM
	 * @param src 需解码的字符串
	 * @return 解码后的字符串
	 * @throws UnsupportedEncodingException 异常
	 */
	public static String decode(String src) throws UnsupportedEncodingException {
		if (StringUtil.isEmpty(src)) {
			return "";
		}
		return URLDecoder.decode(src, "UTF-8");
	}
	
	/**
	 * 方法说明：
	 *			 在指定的 Web 应用程序目录下以指定路径创建文件
	 *CreateTime Apr 18, 2009 10:33:44 PM
	 * @param application JSP/Servlet 的 ServletContext
	 * @param filePath 相对文件路径
	 * @return   结果
	 */
	public static boolean createFile(ServletContext application, String filePath) {
		if (!StringUtil.isEmpty(filePath)) {
			String physicalFilePath = application.getRealPath(filePath);
			if (!StringUtil.isEmpty(physicalFilePath)) {
				File file = new File(physicalFilePath);
				// 创建文件
				try {
					return file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * 方法说明：
	 *			在指定的 Web 应用程序目录下以指定路径创建目录
	 *CreateTime Apr 18, 2009 10:36:22 PM
	 * @param application JSP/Servlet 的 ServletContext
	 * @param filePath 相对文件路径
	 * @return 是否创建成功
	 */
	public static boolean createDir(ServletContext application, String filePath) {
		if (!StringUtil.isEmpty(filePath)) {
			String physicalFilePath = application.getRealPath(filePath);
			if (!StringUtil.isEmpty(physicalFilePath)) {
				// 创建目录
				File dir = new File(application.getRealPath(filePath));
				return dir.mkdirs();
			}
		}

		return false;
	}
	
	/**
	 * 方法说明：
	 *			检查指定的 Web 应用程序目录下的文件是否存在.
	 *CreateTime Apr 18, 2009 10:42:21 PM
	 * @param application JSP/Servlet 的 ServletContext
	 * @param filePath 相对文件路径
	 * @return boolean - 文件是否存在
	 */
	public static boolean checkFileExists(ServletContext application,String filePath) {
		if (!StringUtil.isEmpty(filePath)) {
			String physicalFilePath = application.getRealPath(filePath);
			if (!StringUtil.isEmpty(physicalFilePath)) {
				File file = new File(physicalFilePath);
				return file.exists();
			}
		}
		return false;
	}
	
	/**
	 * 方法说明：
	 *			删除指定的 Web 应用程序目录下所上传的文件
	 *CreateTime Apr 18, 2009 10:44:38 PM
	 * @param application JSP/Servlet 的 ServletContext
	 * @param filePath 相对文件路径
	 */
	public static void deleteFile(ServletContext application, String filePath) {
		if (!StringUtil.isEmpty(filePath)) {
			String physicalFilePath = application.getRealPath(filePath);
			if (!StringUtil.isEmpty(physicalFilePath)) {
				File file = new File(physicalFilePath);
				file.delete();
			}
		}
	}
	
	/**
	 * 方法说明：
	 *			将字符串转换为一个 JavaScript 的 document.location 改变调用. eg: htmlAlert("a.jsp");
	 *	returns &lt;SCRIPTlanguage="JavaScript"&gt;document.location="a.jsp";&lt;/SCRIPT&gt;
	 *CreateTime Apr 18, 2009 11:30:03 PM
	 * @param url 需要显示的 URL 字符串
	 * @return 转换结果
	 */
	public static String scriptRedirect(String url) {
		return "<SCRIPT language=\"JavaScript\">document.location=\"" + url + "\";</SCRIPT>";
	}
	
	/**
	 * 方法说明：
	 *			 返回脚本语句 &lt;SCRIPT language="JavaScript"&gt;history.back();&lt;/SCRIPT&gt;
	 *CreateTime Apr 18, 2009 11:31:44 PM
	 * @return 脚本语句
	 */
	public static String scriptHistoryBack() {
		return "<SCRIPT language=\"JavaScript\">history.back();</SCRIPT>";
	}
	
	/**
	 * 方法说明：
	 *			将字符串转换为一个 JavaScript 的 alert 调用. eg: htmlAlert("What?");
	 *   returns &lt;SCRIPT language="JavaScript"&gt;alert("What?")&lt;/SCRIPT&gt;
	 *CreateTime Apr 18, 2009 11:32:38 PM
	 * @param message 需要显示的信息
	 * @return 转换结果
	 */
	public static String scriptAlert(String message) {
		return "<SCRIPT language=\"JavaScript\">alert(\"" + message + "\");</SCRIPT>";
	}
	
}
