package org.oiue.tools;

import java.io.Serializable;

import org.oiue.tools.string.StringUtil;

/** 
 * 类说明:
 *		取得当前项目的根目录 
 * @author Every E-mail/MSN:mwgjkf@hotmail.com
 *               QQ:30130942
 *  Application 1.0：Feb 11, 2009 10:02:00 AM 
 */
@SuppressWarnings("serial")
public class Application  implements Serializable{
	/**
	 * 方法说明：
	 *			方法用于获取 获取根目录 
	 *CreateTime Apr 17, 200911:14:42 PM
	 * @return 项目根目录 如"F:/ ..."，"/..."样的路径 
	 */
	public static String getRootPath(){ 
		//因为类名为"Application"，因此" Application.class"一定能找到 
		String result = Application.class.getResource("Application.class").toString(); 
		int index = result.indexOf("WEB-INF"); 
		if(index == -1){ 
			index = result.indexOf("bin"); 
		}
		if (index==-1) {
			index = result.indexOf("org.oiue.tools"); 
		}
		result = result.substring(0,index);
		if(result.startsWith("jar")){ 
			// 当class文件在jar文件中时，返回"jar:file:/F:/ ..."样的路径 
			result = result.substring(10); 
		}else if(result.startsWith("file")){ 
			// 当class文件在class文件中时，返回"file:/F:/ ..."样的路径 
			result = result.substring(6); 
		} 
		if(result.endsWith("/"))
			//不包含最后的"/" 
			result = result.substring(0,result.length()-1);
		if (OS.isLinux()||OS.isMacOSX()) {
			//当系统为linux or MacOS时返回"/..."样的路径
			result="/"+result;
		}
		result=result.replace("%20", " ");
		result=result.replace("\\", "/");
		return result; 
	}
	/**
	 * 方法说明：
	 *			方法用于获取 获取根目录 
	 *CreateTime Apr 17, 200911:14:42 PM
	 * @return 项目根目录 如"F:/ ..."，"/..."样的路径 
	 */
	public static String getClassRootPath(){ 
		//因为类名为"Application"，因此" Application.class"一定能找到 
		String result = Application.class.getResource("Application.class").toString(); 
		boolean isWeb=false;
		if (result.indexOf("webapps")>0) {
			isWeb=true;
		}
		int index = result.indexOf("WEB-INF"); 
		if(index == -1){
			index = result.indexOf("bin"); 
		}
		if (index==-1) {
			index = result.indexOf("org.oiue.tools"); 
		}
		result = result.substring(0,index);
		if(result.startsWith("jar")){ 
			// 当class文件在jar文件中时，返回"jar:file:/F:/ ..."样的路径 
			result = result.substring(10); 
		}else if(result.startsWith("file")){ 
			// 当class文件在class文件中时，返回"file:/F:/ ..."样的路径 
			result = result.substring(6); 
		} 
		if(result.endsWith("/"))
			result = result.substring(0,result.length()-1);//不包含最后的"/"
		if (OS.isLinux()||OS.isMacOSX()) {
			//当系统为linux时返回"/..."样的路径
			result="/"+result;
		}
		if (isWeb) {
			result+="/WEB-INF/classes/";
		}else {
			result+="/../bo/";
		}
		result=result.replace("%20", " ");
		result=result.replace("\\", "/");
		return result; 
	}
	/**
	 * 返回不同的发布平台
	 * getPublishType
	 * @return 是否为web
	 */
	public final static boolean isWebPublish(){
		String appPath = Application.getRootPath();

		if (appPath.indexOf("webapps") >= 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Gets the absolute pathname of the class or resource file containing the
	 * specified class or resource name, as prescribed by the current classpath.
	 *
	 * @param resourceName Name of the class or resource name.
	 * @return the absolute pathname of the given resource
	 */
//	/**
//	 * 方法说明：
//	 *			
//	 *CreateTime Apr 18, 2009 9:51:55 PM
//	 * @param resourceName
//	 * @return
//	 */
//	public static String getPath(String resourceName) {
//
//		if (!resourceName.startsWith("/")) {
//			resourceName = "/" + resourceName;
//		}
//
//		//resourceName = resourceName.replace('.', '/');
//
//		java.net.URL classUrl = new Application().getClass().getResource(resourceName);
//
//		if (classUrl != null) {
//			//System.out.println("\nClass '" + className +
//			//"' found in \n'" + classUrl.getFile() + "'");
//			//System.out.println("\n资源 '" + resourceName +
//			//"' 在文件 \n'" + classUrl.getFile() + "' 中找到.");
//
//			return classUrl.getFile();
//		}
//		//System.out.println("\nClass '" + className +
//		//"' not found in \n'" +
//		//System.getProperty("java.class.path") + "'");
//		//System.out.println("\n资源 '" + resourceName +
//		//"' 没有在类路径 \n'" +
//		//System.getProperty("java.class.path") + "' 中找到");
//		return null;
//	}
	
	/**
	 * 方法说明：
	 *			获取类路径中的资源文件的物理文件路径.
	 *			NOTE: 仅在 Win32 平台下测试通过开发.
	 *CreateTime Apr 19, 2009 12:22:13 AM
	 * @param resourcePath 资源路径
	 * @return 配置文件路径
	 */
	public static String getRealFilePath(String resourcePath) {
		java.net.URL inputURL = Application.class.getResource(resourcePath);

		String filePath = inputURL.getFile();

		// For windows platform, the filePath will like this:
		// /E:/Push/web/WEB-INF/classes/studio/beansoft/smtp/MailSender.ini
		// So must remove the first /

		if(OS.isWindows() && filePath.startsWith("/")) {
			filePath = filePath.substring(1);
		}

		return filePath;
	}
	/**
	 * 获取调用者信息
	 * @return 调用者
	 */
	public static String getCaller(){
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		for (int i=0; i < stack.length; i++){
			StackTraceElement ste=stack[i];
			System.out.println(ste.getClassName()+"."+ste.getMethodName()+"(.);["+ste.getLineNumber()+"]");
		}
		return "";
	}
	/**
	 * 获取调用者信息
	 * @param num 层次
	 * @return 调用者 
	 */
	public static String getCaller(int num){
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		for (int i=0; i < stack.length&&i<num; i++){
			StackTraceElement ste=stack[i];
			System.out.println(ste.getClassName()+"."+ste.getMethodName()+"(...);["+ste.getLineNumber()+"]");
		}
		return "";
	}
	/**
	 * 获取调用者信息
	 * @param className 类名
	 * @return class link
	 */
	public static String getClassLine(String className){
		if(StringUtil.isEmptys(className)){
			return null;
		}
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		for (StackTraceElement element : stack){
			if(className.equals(element.getClassName())){
				return element+"";
			}
		}
		return null;
	}
	/**
	 * 获取调用者信息
	 * @param className 类名
	 * @return 下一个 类 行
	 */
	public static String getNextClassLine(String className) {
		if (StringUtil.isEmptys(className)) {
			return null;
		}
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		boolean next = false;
		for (final StackTraceElement element : stack) {
			if (next) {
				return element + "";
			}
			if (className.equals(element.getClassName())) {
				next = true;
			}
		}
		return null;
	}
}