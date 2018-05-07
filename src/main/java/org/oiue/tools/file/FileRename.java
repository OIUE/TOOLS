/**
 * 
 */
package org.oiue.tools.file;

import java.io.File;

/**
 * 类说明: 文件重命名
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942 FileRename 1.0 Apr 17, 2009 11:42:40 PM FileRename
 */
public class FileRename {
	
	/**
	 * 方法说明： 重命名 CreateTime Apr 17, 2009 11:43:15 PM
	 * @param sourceFile 重命名文件（详细路径）
	 * @param newName 命名为
	 */
	public static void ReName(String sourceFile, String newName) {
		File directory = new File(sourceFile);
		directory.renameTo(new File(directory.getParent() + File.separator + newName));
	}
	
}
