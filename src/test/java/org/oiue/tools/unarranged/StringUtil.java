/*
 * @(#)StringUtil.java	1.0 2003.11.2
 *
 * Copyright 2003 - 2006 BeanSoft Studio. All rights reserved.
 */

package org.oiue.tools.unarranged;



/**
 * StringUtil, 字符串工具类, 一些方便的字符串工具方法. 浏览器编码
 *
 * Dependencies: Servlet/JSP API.
 *
 * @author beansoft
 *  1.2 2006-07-31
 */
public class StringUtil {
	/**
	   * 以16进制 打印字节数组
	   * @author zhangweiquan
	   * @param bytes byte[]
	   */
	  public static String toHexString(byte[] bytes) {
	      StringBuffer buffer = new StringBuffer(bytes.length);
	      int startIndex = 0 ;
	      int column = 0;
	      for (int i = 0; i < bytes.length; i++) {
	          column = i % 16;
	          switch (column) {
	          case 0:
	              startIndex = i ;
	              buffer.append(fixHexString(Integer.toHexString(i),8)).append(": ") ;
	              buffer.append(toHex(bytes[i]));
	              buffer.append(" ");
	              break;
	          case 15:
	              buffer.append(toHex(bytes[i]));
	              buffer.append(" ");
	              buffer.append(filterString(bytes,startIndex, column + 1)) ;
	              buffer.append("\n");
	              break;
	          default:
	              buffer.append(toHex(bytes[i]));
	              buffer.append(" ");
	          }
	      }
	      if(column != 15){
	          for(int i = 0; i < (15 - column); i++){
	              buffer.append(" ");
	          }
	          buffer.append(filterString(bytes,startIndex,column + 1)) ;
	          buffer.append("\n");
	      }

	      return buffer.toString();
	  }

	  /**
	   * 将hexStr格式化成length长度16进制数，并在后边加上h
	   * @param hexStr String
	   * @return String
	   */
	  private static String fixHexString(String hexStr,int length){
	      if(hexStr == null || hexStr.length() == 0){
	          return "00000000h";
	      }else{
	          StringBuffer buf = new StringBuffer(length) ;
	          int strLen = hexStr.length() ;
	          for(int i = 0; i < length - strLen ; i ++){
	              buf.append("0");
	          }
	          buf.append(hexStr).append("h");
	          return buf.toString() ;
	      }
	  }
	  /**
	   * 将字节转换成16进制显示
	   * @param b byte
	   * @author zhangweiquan
	   * @return String
	   */
	  public static String toHex(byte b) {
	      char[] buf = new char[2];
	      for (int i = 0; i < 2; i++) {
//	          buf[1 - i] = digits[b & 0xF];
	          b = (byte) (b >>> 4);
	      }
	      return new String(buf);
	  }



	 /**
	   * 过滤掉字节数组中0x0 - 0x1F的控制字符，生成字符串
	   * @param bytes byte[]
	   * @param offset int
	   * @author zhangweiquan
	   * @param count int
	   * @return String
	   */
	  public static String filterString(byte[] bytes, int offset, int count) {
	      byte[] buffer = new byte[count];
	      System.arraycopy(bytes, offset, buffer, 0, count);
	      for (int i = 0; i < count; i++) {
	          if (buffer[i] >= 0x0 && buffer[i] <= 0x1F) {
	              buffer[i] = 0x2e;
	          }
	      }
	      return new String(buffer);
	  }

//
//
//	/**
//	 * 返回 HTTP 请求的 Referer, 如果没有, 就返回默认页面值.
//	 * 仅用于移动博客开发页面命名风格: // Added at 2004-10-12 // 如果前一页面的地址包含 _action.jsp ,
//	 * 为了避免链接出错, 就返回默认页面
//	 * 2006-08-02 增加从 url 参数 referer 的判断 
//	 * @param request - HttpServletRequest 对象
//	 * @param defaultPage - String, 默认页面
//	 * @return String - Referfer
//	 */
//	public static String getReferer(HttpServletRequest request, String defaultPage) {
//		String referer = request.getHeader("Referer");// 前一页面的地址, 提交结束后返回此页面
//
//		// 获取URL中的referer参数
//		String refererParam = request.getParameter("referer");
//		
//		if(!isEmpty(refererParam)) {
//			referer = refererParam;
//		}
//		
//		// Added at 2004-10-12
//		// 如果前一页面的地址包含 _action.jsp , 为了避免链接出错, 就返回默认页面
//		if (isEmpty(referer) || referer.indexOf("_action.jsp") != -1) {
//			referer = defaultPage;
//		}
//
//		return referer;
//	}
//
//	/**
//	 * Write the HTML base tag to support servlet forward calling relative path
//	 * changed problems.
//	 *
//	 * Base is used to ensure that your document's relative links are associated
//	 * with the proper document path. The href specifies the document's
//	 * reference URL for associating relative URLs with the proper document
//	 * path. This element may only be used within the HEAD tag. Example: <BASE
//	 * HREF="http://www.sample.com/hello.htm">
//	 *
//	 * @param pageContext the PageContext of the jsp page object
//	 */
//	public static void writeHtmlBase(PageContext pageContext) {
//		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
//		StringBuffer buf = new StringBuffer("<base href=\"");
//		buf.append(request.getScheme());
//		buf.append("://");
//		buf.append(request.getServerName());
//		buf.append(":");
//		buf.append(request.getServerPort());
//		buf.append(request.getRequestURI());
//		buf.append("\">");
//		JspWriter out = pageContext.getOut();
//		try {
//			out.write(buf.toString());
//		} catch (java.io.IOException e) {
//
//		}
//	}
//
//	/**
//	 * Get the base path of this request.
//	 *
//	 * @param request - HttpServletRequest
//	 * @return String - the base path, eg: http://www.abc.com:8000/someApp/
//	 */
//	public static String getBasePath(HttpServletRequest request) {
//		String path = request.getContextPath();
//		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
//		return basePath;
//	}
//
//	/**
//	 * Get the current page's full path of this request. 获取当前页的完整访问 URL 路径.
//	 *
//	 * @author BeanSoft
//	 *  2005-08-01
//	 * @param request - HttpServletRequest
//	 * @return String - the full url path, eg: http://www.abc.com:8000/someApp/index.jsp?param=abc
//	 */
//	public static String getFullRequestURL(HttpServletRequest request) {
//		StringBuffer url = request.getRequestURL();
//		String qString = request.getQueryString();
//
//		if (qString != null) {
//			url.append('?');
//			url.append(qString);
//		}
//
//		return url.toString();
//	}
//
//	/**
//	 * Get the current page's full path of this request. 获取当前页的完整访问 URI 路径.
//	 *
//	 * @author BeanSoft
//	 *  2005-08-01
//	 * @param request - HttpServletRequest
//	 * @return String - the full uri path, eg: /someApp/index.jsp?param=abc
//	 */
//	public static String getFullRequestURI(HttpServletRequest request) {
//		StringBuffer url = new StringBuffer(request.getRequestURI());
//		String qString = request.getQueryString();
//
//		if (qString != null) {
//			url.append('?');
//			url.append(qString);
//		}
//
//		return url.toString();
//	}
//
//
//	/* -----------------------------------------------------------------------------------------------*/
//	/* ---------------------------------字符串To页面处理方法--------------------------------------------*/
//	/* -----------------------------------------------------------------------------------------------*/
//	
//
//	
//	/**
//	 * 获得指定表单域的值, 并将单个的 ' 换成 ''; 
//	 * SQL 规则:如果单引号中的字符串包含一个嵌入的引号， 可以使用两个单引号表示嵌入的单引号。
//	 */
//	public String requestSql(HttpServletRequest request, String fieldName) {
//		return replaceSql(request1(request, fieldName));
//	}
//	
//	
//	/* -----------------------------------------------------------------------------------------------*/
//	/* ---------------------------------字符串To页面处理方法--------------------------------------------*/
//	/* -----------------------------------------------------------------------------------------------*/
//
//
//
//
//	
//
//
//	/**
//	 * 获取表单参数并做默认转码, 从 ISO8859-1 转换到 GBK.
//	 *
//	 * @author BeanSoft
//	 *  2005-08-01
//	 *
//	 * @param request HttpServletRequest 对象
//	 * @param fieldName 参数名
//	 * @return 取得的表单值
//	 */
//	public static String getParameter(HttpServletRequest request,String fieldName) {
////		判断编码是否已经指定
////		String encoding = request.getCharacterEncoding();
////
////		if("GBK".equalsIgnoreCase(encoding) || "GB2312".equalsIgnoreCase(encoding)) {
////			return request.getParameter(fieldName);
////		}
////
////		return request(request, fieldName);
//		// 2005-08-01 临时修改
////		try {
////			request.setCharacterEncoding("UTF-8");
////		} catch (UnsupportedEncodingException e) {
////			// TODO auto generated try-catch
////			e.printStackTrace();
////		}
//
//		return request.getParameter(fieldName);
//	}
//	
//	/*------------------------------------ JSP 参数处理方法----------------------------------------------*/
//	
//	
//	/** 
//	 * 一个与 ASP 类似的方法, 返回表单域的值, 并转换内码 
//	 */
//	public static String request(HttpServletRequest request, String fieldName) {
//		// POST 方法的参数有编码错误
//		if (request.getMethod().equalsIgnoreCase("POST")) {
//			// JSP Smart upload 的 request 对象没有中文问题
//			//			try {
//			//				if (request instanceof com.jspsmart.upload.Request) {
//			//					// 2004-04-02, Fix the encoding bug on SCO open server(Unix,
//			//					// os.name=OpenServer)
//			//					// when running jsp smart at tomcat 3.1.1,
//			//					// but on Windows and Linux no this problems
//			//					if (System.getProperty("os.name").toLowerCase().indexOf(
//			//							"openserver") != -1) {
//			//						return toChi(request.getParameter(fieldName));
//			//					} else {
//			//						return request.getParameter(fieldName);
//			//					}
//			//				}
//			//			} catch (Throwable ex) {
//			//				// Throwable -- 防止未导入 smartupload 类库时出错
//			//				System.err.println(ex);
//			//			}
//			// 文件上传模式
//			//if(isUploadMode) {
//			//	return request.getParameter(fieldName);
//			//}
//			// For Tomcat 4.0.6
//			return toChi(request.getParameter(fieldName));
//		}
//		// 将通过 GET 方式发送的中文字符解码(但是必须使用 java.net.URLEncoder 进行中文字符参数的编码)
//		// 解码时需使用内码转换, 也可使用反编码, 即: return
//		// decode(request.getParameter(fieldName));
//		// 问题: decode() 仅适用于 JDK 1.3 + Tomcat 4.0
//		return toChi(request.getParameter(fieldName));
//	}
//	
//	/** 
//	 * 如果表单的值是 null, 则返回 "", 避免 NullPointerException 
//	 */
//	public String request1(HttpServletRequest request, String fieldName) {
//		String s = request(request, fieldName);
//		if (s == null) {
//			return "";
//		}
//		return s;
//	}
//	
//	/**
//	 * 根据 Cookie 名称得到请求中的 Cookie 值, 需要事先给 _request 一个初始值; 
//	 * 如果 Cookie 值是 null, 则返回 ""
//	 */
//	public static String getCookieValue(HttpServletRequest request, String name) {
//		Cookie[] cookies = request.getCookies();
//		if (cookies == null) {
//			return "";
//		}
//		for (int i = 0; i < cookies.length; i++) {
//			Cookie cookie = cookies[i];
//			if (cookie.getName().equals(name)) {
//				// 需要对 Cookie 中的汉字进行 URL 反编码, 适用版本: Tomcat 4.0
//				return decode(cookie.getValue());
//				// 不需要反编码, 适用版本: JSWDK 1.0.1
//				//return cookie.getValue();
//			}
//		}
//		// A cookie may not return a null value, may return a ""
//		return "";
//	}
//	
//	/**
//	 * 返回指定表单名的数组
//	 */ 
//	public String[] getParameterValues(HttpServletRequest request, String name) {
//		// POST 方法的参数没有编码错误
//		//if (request.getMethod().equalsIgnoreCase("POST")) {
//		// 文件上传模式
//		//if(isUploadMode) {
//		//	return request.getParameterValues(name);
//		//}
//		// -- For Tomcat 4.0
//		//return request.getParameterValues(name);
//		// -- For JSWDK 1.0.1
//		/*
//		 * String values[] = _request.getParameterValues(name); if(values !=
//		 * null) { for(int i = 0; i < values.length; i++) { values[i] =
//		 * toChi(values[i]); } } return values;
//		 */
//		//}
//		//else {
//		// 将通过 GET 方式发送的中文字符解码(但是必须使用 java.net.URLEncoder 进行中文字符参数的编码)
//		// 解码时需使用内码转换, 也可使用反编码, 即: return decode(_request.getParameter(name));
//		// 问题: decode() 仅适用于 JDK 1.3 + Tomcat 4.0
//		String encoding = request.getCharacterEncoding();
//
//		if("GBK".equalsIgnoreCase(encoding) || "GB2312".equalsIgnoreCase(encoding)) {
//			return request.getParameterValues(name);
//		}
//
//		String values[] = request.getParameterValues(name);
//		if (values != null) {
//			for (int i = 0; i < values.length; i++) {
//				values[i] = toChi(values[i]);
//			}
//		}
//		return values;
//		//}
//	}
//	
//
//
//
//	/**
//	 * 输出分页显示的结果.
//	 *
//	 * @param page 当前页面
//	 * @param recordCount 所有结果
//	 * @param pageSize 一页显示的多少
//	 * @param pageCountSize 前后跳页的多少
//	 * @param linkpageurl 连接页面的 URL 字符串
//	 * @return 分页结果的字符串.
//	 */
//	public static String paging(int page, int recordCount, int pageSize,int pageCountSize, String linkpageurl) {
//		int PageCount = -1; //页面总数
//		String LinkPageName = linkpageurl;
//		String LinkText = "";
//		int StartPage;
//		int TempPage;
//		int TempPageCount;
//		TempPage = (page - 1) % pageCountSize; //唱赣瘤 备窃
//		StartPage = page - TempPage; //矫累 其捞瘤 备窃
//		TempPageCount = recordCount % pageSize;
//		if (TempPageCount == 0) {
//			PageCount = recordCount / pageSize;
//		} else {
//			PageCount = (recordCount / pageSize) + 1; //傈眉 其捞瘤 荐
//		}
//		String txtPrev = " [前" + pageCountSize + "页] ";
//		String txtNext = " [后" + pageCountSize + "页] ";
//		String txtStart = " [首页] ";
//		String txtEnd = " [末页] ";
//		//贸澜栏肺
//		if (StartPage - 1 > 0) {
//			LinkText += "<a href='" + LinkPageName + "&page=1' title='到此页'>"
//					+ txtStart + "</a>";
//		} else {
//			LinkText += txtStart;
//		}
//		//捞傈 10俺..
//		if (StartPage - 1 > 0) {
//			LinkText += "<a href='" + LinkPageName + "&page=" + (StartPage - 1)
//					+ "' title='到第" + pageCountSize + "页'>" + txtPrev + "</a>";
//		} else {
//			LinkText += txtPrev;
//		}
//		for (int i = StartPage; i < StartPage + pageCountSize; i++) {
//			if (i < PageCount + 1) {
//				LinkText += "<a href='" + LinkPageName + "&page=";
//				LinkText += i + "' title='" + i + "页'>";
//				if (i == page) {
//					LinkText += "[" + i + "]";
//				} else {
//					LinkText += "[" + i + "]";
//				}
//				LinkText += "</a>";
//			}
//		}
//		//中间页面
//		if (StartPage + pageCountSize - PageCount - 1 < 0) {
//			LinkText += "<a href='" + LinkPageName + "&page="
//					+ (StartPage + pageCountSize) + "' title='到第"
//					+ pageCountSize + "页'>" + txtNext + "</a>";
//		} else {
//			LinkText += txtNext;
//		}
//		//最后一页
//		if (StartPage + pageCountSize <= PageCount) {
//			LinkText += "<a href='" + LinkPageName + "&page=" + PageCount + "' title='最后一页'>" + txtEnd + "</a>";
//		} else {
//			LinkText += txtEnd;
//		}
//		return LinkText;
//	}
//
//	
//	
//
//	// -----------------------------------------------------------
//	// ---------- 字符串和数字转换工具方法, 2004.03.27 添加 --------
//	//------------------------------------------------------------
//	public static byte getByte(HttpServletRequest httpservletrequest, String s) {
//		if (httpservletrequest.getParameter(s) == null || httpservletrequest.getParameter(s).equals("")) {
//			return 0;
//		}
//		return Byte.parseByte(httpservletrequest.getParameter(s));
//	}
//	
//	/**
//	 * Reading a parameter as integer from the http servlet request.
//	 *
//	 */
//	public static int getInt(HttpServletRequest httpservletrequest, String s) {
//		if (httpservletrequest.getParameter(s) == null || httpservletrequest.getParameter(s).equals("")) {
//			return 0;
//		}
//		return Integer.parseInt(httpservletrequest.getParameter(s));
//	}
//	
//	public static long getLong(HttpServletRequest httpservletrequest, String s) {
//		if (httpservletrequest.getParameter(s) == null || httpservletrequest.getParameter(s).equals("")) {
//			return 0L;
//		}
//		return Long.parseLong(httpservletrequest.getParameter(s));
//	}
//	
//	public static short getShort(HttpServletRequest httpservletrequest, String s) {
//		if (httpservletrequest.getParameter(s) == null || httpservletrequest.getParameter(s).equals("")) {
//			return 0;
//		}
//		return Short.parseShort(httpservletrequest.getParameter(s));
//	}
//
//	// Test only.
//	public static void main(String[] args)  {
//		//System.out.println(textToHtml("1<2\r\nBold"));
//		//System.out.println(scriptAlert("oh!"));
//		//System.out.println(scriptRedirect("http://localhost/"));
//		//    System.out.println(StringUtil.getPath("/databaseconfig.properties"));
//		//		java.io.File file = new java.io.File("e:\\Moblog\\abcd\\");
//		//
//		//		file.mkdir();
////		Date time = (parseHMSStringToDate("12:23:00"));
////		System.out.println(time.toLocaleString());
////		Date nowTime = parseHMSStringToDate(formatDateToHMSString(new Date()));
////		System.out.println(nowTime.toLocaleString());
//
//		//		GregorianCalendar cal = new GregorianCalendar();
//		//		cal.setTime(new Date());
//		//		cal.add(cal.YEAR, -cal.get(cal.YEAR) + 1970);
//		//		cal.add(cal.MONTH, -cal.get(cal.MONTH));
//		//		cal.add(cal.DATE, -cal.get(cal.DATE) + 1);
//		//
//		//		System.out.println(cal.getTime().toLocaleString());
////		StringUtil stringUtil=new StringUtil();
////		System.out.println(stringUtil.getShortFileName("D:\\temp\\新建文件夹\\昌平大队.xls"));
////		System.out.println(stringUtil.getShortFileName("temp\\新建文件夹\\昌平大队.xls"));
////		System.out.println(stringUtil.getFileName("D:\\temp\\新建文件夹\\昌平大队.xls"));
////		System.out.println(stringUtil.getFileName("temp\\新建文件夹\\昌平大队.xls"));
////
////		System.out.println(stringUtil.getExtension("temp\\新建文件夹\\昌平大队.xls"));
//		
//	}
//	
//	public String toMinuscule(String str) {
//		return str;
//	}

}