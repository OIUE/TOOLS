/**
 * 
 */
package org.oiue.tools.file;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.oiue.tools.Application;
import org.oiue.tools.ITask;
import org.oiue.tools.string.StringUtil;

/** 
 * 类说明:
 *		文件查找
 * @author Every E-mail/MSN:mwgjkf@hotmail.com
 *               QQ:30130942
 * @version FileSearch 1.0  Apr 18, 2009 12:20:53 AM
 * FileSearch
 */
@SuppressWarnings( { "unchecked","unused","rawtypes"})
public class FileSearch {

	/**
	 * 方法说明：
	 *			返回路径下的文件
	 *CreateTime Apr 18, 2009 12:26:12 AM
	 * @param fileName 目录
	 * @return
	 */
	public static List FileDirectorySearch(String fileName) {
		List fileList=new ArrayList();
		LinkedList<File> list = new LinkedList<File>();
        File dir = new File(fileName);
        File file[] = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory()){
                list.add(file[i]);
            }else{
                fileList.add(file[i]);
            }
        }
        File tmp;
        while (!list.isEmpty()){
            tmp = list.removeFirst();
            if (tmp.isDirectory()){
                file = tmp.listFiles();
                if (file == null)
                    continue;
                for (int i = 0; i < file.length; i++){
                    if (file[i].isDirectory()){
                        list.add(file[i]);
                    }else{
                        fileList.add(file[i]);
                    }
                }
            } else {
                fileList.add(tmp.getAbsolutePath());
            }
        }
		return fileList;
	}
	
	/**
	 * 方法说明：
	 *			返回路径下的文件夹
	 *CreateTime Apr 18, 2009 12:28:59 AM
	 * @param fileName 目录
	 * @return 路径下的文件夹
	 */
	public static List FileDirectorysSearch(String fileName) {
		List directotyList=new ArrayList();
		LinkedList<File> list = new LinkedList<File>();
        File dir = new File(fileName);
        File file[] = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory()){
                list.add(file[i]);
                directotyList.add(file[i]);
            }
        }
        File tmp;
        while (!list.isEmpty()){
            tmp = list.removeFirst();
            if (tmp.isDirectory()){
                file = tmp.listFiles();
                if (file == null)
                    continue;
                for (int i = 0; i < file.length; i++){
                    if (file[i].isDirectory()){
                        list.add(file[i]);
                        directotyList.add(file[i]);
                    }
                }
            } 
        }
		return directotyList;
	}
	
	/**
	 * 方法说明：
	 *			比较两个目录下文件是否有覆盖
	 *CreateTime Apr 18, 2009 12:30:23 AM
	 * @param sourceDir 源文件
	 * @param targetDir 新文件
	 * @return 相同的文件
	 */
	public static List ParallelFile(String sourceDir, String targetDir) {
		List list =new  ArrayList();
		LinkedList<File> sourceFilelist = new LinkedList<File>();
		LinkedList<File> targetFilelist = new LinkedList<File>();
		File source = new File(sourceDir);
		File sourceFile[] = source.listFiles();
		File target = new File(targetDir);
		File targetFile[] = target.listFiles();
		for (int i = 0; i < targetFile.length; i++) {
			for (int j = 0; j < sourceFile.length; j++) {
				if (targetFile[i].getName().trim().equals(sourceFile[j].getName().trim())) {
					if (targetFile[i].isDirectory()){
						sourceFilelist.add(sourceFile[j]);
		            	targetFilelist.add(targetFile[i]);
		            }else{
		            	list.add(targetFile[i].getAbsolutePath());
		            }
				}
			}
        }
		File targetTmp;
		File sourceTmp;
        while (!targetFilelist.isEmpty()&&!sourceFilelist.isEmpty()){
        	targetTmp = targetFilelist.removeFirst();
        	sourceTmp = sourceFilelist.removeFirst();
            if (targetTmp.isDirectory()&&sourceTmp.isDirectory()){
            	targetFile = targetTmp.listFiles();
            	sourceFile = sourceTmp.listFiles();
                if (targetFile == null&&sourceFile==null)
                    continue;
                for (int i = 0; i < targetFile.length; i++){
                	for (int j = 0; j < sourceFile.length; j++) {
        				if (targetFile[i].getName().trim().equals(sourceFile[j].getName().trim())) {
        					if (targetFile[i].isDirectory()){
        						sourceFilelist.add(sourceFile[j]);
        		            	targetFilelist.add(targetFile[i]);
        		            }else{
        		            	list.add(targetFile[i].getAbsolutePath());
//        		                System.out.println(targetFile[i].getAbsolutePath()+"&&"+sourceFile[j].getAbsolutePath()+"$$"+targetFile[i].getName());
        		            }
        				}
        			}
                }
            } else {
//                System.out.println(targetTmp.getAbsolutePath());
            }
        }
		return list;
	}
	/**
	 * 查找文件 并处理
	 * @param parentPath
	 * @param itask
	 * @return
	 */
	public static boolean searchFile(String parentPath,ITask itask){
		File[] file = null;
		File current = null;
		//是否为空
		if (StringUtil.isEmpty(parentPath)) {
			try {
				//显示的是系统盘符
				file = File.listRoots();
			} catch (Exception ex) {
			}
		} else {
			current = new File(parentPath);
	        file = new File(parentPath).listFiles();
		}
		if(file!=null)
			for (int i = 0; i < file.length; i++) {
				current = file[i];
				if(current.isDirectory()){//文件路径
					searchFile(current.getPath(),itask);
				}else{//文件
					itask.invoke(current);
				}
			}
		
		return true;
	}
	
	/**
	 * 返回指定路径下的指定文件类型的文件集合
	 * @param fileName
	 * @param fileType
	 * @return
	 */
	public static List FileDirectorySearch(String fileName,List fileType) {
		List list=FileDirectorySearch(fileName);
		List listList=new ArrayList();
		String fileExtension="";
		for (int i = 0; i < list.size(); i++) {
			fileExtension=FileStringUtil.getExtension(list.get(i).toString());
			for (int j = 0; j < fileType.size(); j++) {
				if (fileExtension!=null&&fileExtension.toLowerCase().trim().equals(fileType.get(j).toString().toLowerCase())){
					listList.add(list.get(i));
				}
			}
		}
		return listList;
	}
	
	/**
	 * 返回指定文件路径下指定文件类型
	 * @param dir
	 * @param extension
	 * @return
	 */
	public static List<File> GetFileByExtension(String dir,String extension){
		File file = new File(dir);
		List list = new ArrayList();
		
		return list;
	}
	/**
	 * 
	 * @param dir
	 * @param extension
	 * @param list
	 * @return
	 */
	public static List<File> GetFileByExtension(String dir,String extension,List list){
		File file = new File(dir);
		if(list==null)list = new ArrayList();
		
		return list;
	}
	/**方法说明：
	 *			
	 *CreateTime Apr 18, 2009 12:20:53 AM
	 * @param args 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dir = Application.getRootPath();   // directory of your choice
		File file = new File(dir);
		System.out.println(dir);
		File[] files = file.listFiles(new ExtensionFileFilter(".jsp"));
		for(File f:files){
			System.out.println(f.getAbsolutePath());
		}
	}
}
