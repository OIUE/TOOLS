/**
 * 
 */
package org.oiue.tools.file;

/**
 * 类说明: 文件路径操作类
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942 FileStringUtil 1.0 Apr 18, 2009 12:46:50 AM FileStringUtil
 */
public class FileStringUtil {
	
	/**
	 * 
	 */
	public FileStringUtil() {
	}
	
	public static String initFilePath(String file) {
		file = file.replace("%20", " ");
		return file;
	}
	
	/**
	 * 方法说明： 得到文件的扩展名 CreateTime Apr 18, 2009 12:48:34 AM
	 * @param fileName 需要处理的文件全路径
	 * @return he extension portion of the file's name
	 */
	public static String getExtension(String fileName) {
		if (fileName != null) {
			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(i + 1).toLowerCase();
			}
		}
		return "";
	}
	
	/**
	 * 方法说明： 得到文件的前缀名 CreateTime Apr 18, 2009 12:50:38 AM
	 * @param fileName 需要处理的文件的名字
	 * @return the prefix portion of the file's name
	 */
	public static String getPrefix(String fileName) {
		if (fileName != null) {
			fileName = fileName.replace('\\', '/');
			
			if (fileName.lastIndexOf("/") > 0) {
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
			}
			
			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				return fileName.substring(0, i);
			}
		}
		return "";
	}
	
	/**
	 * 方法说明： 得到文件的短路径, 不保护目录 CreateTime Apr 18, 2009 12:51:39 AM
	 * @param fileName 需要处理的文件的名字
	 * @return the short version of the file's name
	 */
	public static String getShortFileName(String fileName) {
		if (fileName != null) {
			String oldFileName = new String(fileName);
			
			fileName = fileName.replace('\\', '/');
			
			// Handle dir
			if (fileName.endsWith("/")) {
				int idx = fileName.indexOf('/');
				if (idx == -1 || idx == fileName.length() - 1) {
					return oldFileName;
				} else {
					return oldFileName.substring(idx + 1, fileName.length() - 1);
				}
			}
			if (fileName.lastIndexOf("/") > 0) {
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
			}
			
			return fileName;
		}
		return "";
	}
	
	/**
	 * 方法说明： 得到文件的短路径, 不保护目录 CreateTime Apr 18, 2009 12:52:46 AM
	 * @param fileName 需要处理的文件的名字
	 * @return the short version of the file's name
	 */
	public static String getFileName(String fileName) {
		if (fileName != null) {
			String oldFileName = new String(fileName);
			
			fileName = fileName.replace('\\', '/');
			
			// Handle dir
			if (fileName.endsWith("/")) {
				int idx = fileName.indexOf('/');
				if (idx == -1 || idx == fileName.length() - 1) {
					return oldFileName;
				} else {
					return oldFileName.substring(idx + 1, fileName.length() - 1);
				}
			}
			if (fileName.lastIndexOf("/") > 0) {
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
			}
			String[] file = fileName.replace(".", "/").split("/");
			
			return file[0];
		}
		return "";
	}
	
	/**
	 * 方法说明： 检查指定的应用程序目录下的文件是否存在 CreateTime Apr 18, 2009 1:04:48 AM
	 * @param fileName 文件绝对路径
	 * @return 文件是否存在
	 */
	public static boolean checkFileExists(String fileName) {
		java.io.File file = new java.io.File(fileName);
		return file.exists();
	}
	
	/**
	 * 方法说明： 获取文件图标名. CreateTime Apr 18, 2009 5:07:53 PM
	 * @param iconDirPath 图标文件夹的路径 如：/img/
	 * @param fileName 需要处理的文件名
	 * @return 图标文件相对路径
	 */
	public static String getFileIcon(String iconDirPath, String fileName) {
		String ext = getExtension(fileName);
		String filePath = iconDirPath + ext + ".gif";
		if (checkFileExists(filePath)) {
			return filePath;
		}
		return iconDirPath + "file.gif";
	}
	
}
