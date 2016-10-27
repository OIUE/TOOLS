/**
 * 
 */
package org.oiue.tools.file;

import java.io.File;

/** 
 * 类说明:
 *		
 * @author Every E-mail/MSN:mwgjkf@hotmail.com
 *               QQ:30130942
 * @version FileHandle 1.0  Apr 17, 2009 11:33:00 PM
 * FileHandle
 */
public class FileHandle {
	static boolean flag;
	static File file;
//	Application application=new Application();
	/**
	 * 
	 */
	public FileHandle() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 方法说明：
	 *			创建文件夹
	 *CreateTime Apr 17, 2009 11:35:28 PM
	 * @param targetDir 文件夹完整路径
	 * @return 如创建文件已存在则返回false 不存在则创建文件并返回true
	 */
	public static boolean CreateFolder(String targetDir){
		File target = new File(targetDir);
		if (target.canExecute()) {
			return false;
		}
		target.mkdirs();
		return true;
	}
	
	/**
	 * 方法说明：
	 *			根据路径删除指定的目录或文件，无论存在与否
	 *CreateTime Apr 17, 2009 11:37:01 PM
	 * @param sPath 要删除的目录或文件
	 * @return 删除成功返回 true，否则返回 false
	 */
	public static boolean DeleteFolder(String sPath) {   
	    flag = false;   
	    file = new File(sPath);   
	    // 判断目录或文件是否存在   
	    if (!file.exists()) {  // 不存在返回 false   
	        return flag;   
	    } else {   
	        // 判断是否为文件   
	        if (file.isFile()) {  // 为文件时调用删除文件方法   
	            return deleteFile(sPath);   
	        } else {  // 为目录时调用删除目录方法   
	            return deleteDirectory(sPath);   
	        }   
	    }   
	}
	
	/**
	 * 方法说明：
	 *			删除单个文件
	 *CreateTime Apr 17, 2009 11:38:48 PM
	 * @param sPath 被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String sPath){
	    flag = false;
	    file = new File(sPath);
	    // 路径为文件且不为空则进行删除
	    if (file.isFile() && file.exists()){
	        file.delete();
	        flag = true;
	    }
	    return flag;
	}
	
	/**
	 * 方法说明：
	 *			删除目录（文件夹）以及目录下的文件
	 *CreateTime Apr 17, 2009 11:39:51 PM
	 * @param sPath 被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String sPath) {   
	    //如果sPath不以文件分隔符结尾，自动添加文件分隔符   
	    if (!sPath.endsWith(File.separator)) {   
	        sPath = sPath + File.separator;   
	    }   
	    File dirFile = new File(sPath);   
	    //如果dir对应的文件不存在，或者不是一个目录，则退出   
	    if (!dirFile.exists() || !dirFile.isDirectory()) {   
	        return false;   
	    }   
	    flag = true;   
	    //删除文件夹下的所有文件(包括子目录)   
	    File[] files = dirFile.listFiles();   
	    for (int i = 0; i < files.length; i++) {   
	        //删除子文件   
	        if (files[i].isFile()) {   
	            flag = deleteFile(files[i].getAbsolutePath());   
	            if (!flag) break;   
	        } //删除子目录   
	        else {   
	            flag = deleteDirectory(files[i].getAbsolutePath());   
	            if (!flag) break;   
	        }   
	    }   
	    if (!flag) return false;   
	    //删除当前目录   
	    if (dirFile.delete()) {   
	        return true;   
	    } else {   
	        return false;   
	    }   
	}
	
	/**方法说明：
	 *			
	 *CreateTime Apr 17, 2009 11:33:00 PM
	 * @param args 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/*public String CreateFolderByName(String FolderName){
		FolderName=application.getRootPath()+File.separator+"Themes"+File.separator+FolderName;
		(new File(FolderName)).mkdirs();
		return FolderName;
	}
	
	public boolean purview(String filePath) {
		String pathString=application.getRootPath()+"/Themes.{5,}";
		pathString=pathString.replace("%20", " ");
		filePath=filePath.replace("\\", "/");
		
		Pattern p=Pattern.compile(pathString); 
		Matcher m=p.matcher(filePath);
		
		String temp="";
		boolean havapurview=false;
		
		while (m.find()) {
			int start = m.start();
			int end=m.end();
			temp = filePath.substring(start,end);
			if (temp!=null&&!temp.trim().equals("")) {
				havapurview = true;
			}
		}
		if (havapurview) {
			return true;
		}
		return false;
	}*/

}
