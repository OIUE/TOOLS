/**
 * 
 */
package org.oiue.tools.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.oiue.tools.string.StringReplace;

/** 
 * 类说明:
 *		替换文件内的字符串
 * @author Every E-mail/MSN:mwgjkf@hotmail.com
 *               QQ:30130942
 *  FileReplace 1.0  Apr 17, 2009 11:45:12 PM
 * FileReplace
 */

@SuppressWarnings( { "unused", "rawtypes","static-access","resource"})
public class FileReplace {

	/**
	 * 方法说明：
	 *			是否以大小写敏感替换文件中的字符串返回新文件
	 *CreateTime Apr 18, 2009 1:09:50 AM
	 * @param fileString 文件绝对路径
	 * @param regex 用来匹配替换的正则表达式
	 * @param replacement 替换后的字符串
	 * @param matchCase 是否大小写敏感
	 * @return 临时文件绝对路径
	 * @throws IOException  io异常
	 */
	public static String FileReplaceOutTemp(String fileString,String regex,String replacement,boolean matchCase) throws IOException {
        String line;
        String templine;
        File file=new File(fileString);
        FileWriter fileWriter=new FileWriter(file.getParent()+File.separator+"temp"+file.getName());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileString),"UTF-8"));
        line = reader.readLine();      // 读取第一行 
        while (line!=null) {           // 如果 line 为空说明读完了 
       	// templine=line.replaceAll(regex, replacement);
       	 templine=StringReplace.replace(line,regex, replacement,matchCase);
       	 fileWriter.write(templine+"\n");
            line = reader.readLine();   // 读取下一行  **
        }
        fileWriter.close();
        return file.getParent()+File.separator+"temp"+file.getName();
	}
	
	/**
	 * 方法说明：
	 *			大小写敏感替换文件中的字符串返回新文件	
	 *CreateTime Apr 17, 2009 11:48:03 PM
	 * @param fileString 文件绝对路径
	 * @param regex 用来匹配替换的正则表达式
	 * @param replacement 替换后的字符串
	 * @return 临时文件绝对路径
	 */
	public static String FileReplaceOutTemp(String fileString,String regex,String replacement) {
		 try {
	         String line;
	         String templine;
	         File file=new File(fileString);
	         FileWriter fileWriter=new FileWriter(file.getParent()+File.separator+"temp"+file.getName());
	         BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileString),"GBK"));
	         line = reader.readLine();      // 读取第一行 
	         while (line!=null) {           // 如果 line 为空说明读完了 
	        	 templine=line.replaceAll(regex, replacement);
	        	 fileWriter.write(templine+"\n");
	             line = reader.readLine();   // 读取下一行  **
	         }
	         fileWriter.close();
	         return file.getParent()+File.separator+"temp"+file.getName();
	    }catch (Exception e) {e.printStackTrace();}
	    return null;
	}
	
	/**
	 * 方法说明：
	 *			是否以大小写敏感方式替换原文件中的字符串
	 *CreateTime Apr 17, 2009 11:55:41 PM
	 * @param fileString 文件绝对路径
	 * @param regex 用来匹配替换的正则表达式
	 * @param replacement 替换后的字符串
	 * @param matchCase 是否大小写敏感
	 * @return 是否替换成功
	 * @throws IOException io异常
	 */
	public static boolean FileReplace_case(String fileString,String regex,String replacement,boolean matchCase) throws IOException {
		FileCopy copyFile=new FileCopy();
		FileHandle handleFile=new FileHandle();
		String tempFile=FileReplaceOutTemp(fileString, regex, replacement,matchCase);
		copyFile.CopyOFFile(tempFile, fileString);
		boolean is=handleFile.deleteFile(tempFile);
		return true;
	}
	
	/**
	 * 方法说明：
	 *			大小写敏感替换原文件中的字符串
	 *CreateTime Apr 17, 2009 11:56:48 PM
	 * @param fileString 文件绝对路径
	 * @param regex 用来匹配替换的正则表达式
	 * @param replacement 替换后的字符串
	 * @return 是否替换成功
	 * @throws IOException IO异常
	 */
	public static boolean FileReplace_case(String fileString,String regex,String replacement) throws IOException {
		FileCopy copyFile=new FileCopy();
		FileHandle handleFile=new FileHandle();
		String tempFile=FileReplaceOutTemp(fileString, regex, replacement);
		copyFile.CopyOFFile(tempFile, fileString);
		boolean is=handleFile.deleteFile(tempFile);
		if (is) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 方法说明：
	 *			大小写敏感替换文件目录中的所有文件中指定的字符串
	 *CreateTime Apr 18, 2009 1:57:37 PM
	 * @param fileName 目录
	 * @param regex 用来匹配替换的正则表达式
	 * @param replacement 替换后的字符串
	 * @return 是否替换成功
	 * @throws IOException IO异常
	 */
	public boolean FolderOfAll(String fileName,String regex,String replacement ) throws IOException{
		FileSearch fileSearch=new FileSearch();
		List list=fileSearch.FileDirectorySearch(fileName);
		for (int i = 0; i < list.size(); i++) {
			boolean is=FileReplace_case(list.get(i).toString(), regex, replacement);
			if (is) {
				return false;
			}
			//System.out.println(list.get(i));
		}
		return true;
	}
	
	/**
	 * 
	 * 方法说明：
	 *			是否以大小写敏感替换某路径下指定类型的文件中的指定字符串
	 *CreateTime Apr 18, 2009 1:55:41 PM
	 * @param fileName 文件绝对路径
	 * @param type 文件类型 如(java,htm,html,jsp)
	 * @param regex 用来匹配替换的正则表达式
	 * @param replacement 替换后的字符串
	 * @param matchCase 是否大小写敏感
	 * @return 是否替换成功
	 * @throws IOException IO异常
	 */
	public boolean FolderOfAll(String fileName,List type,String regex,String replacement,boolean matchCase) throws IOException {
		FileSearch fileSearch=new FileSearch();
		List list=fileSearch.FileDirectorySearch(fileName);
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < type.size(); j++) {
				if (type.get(j).equals(FileStringUtil.getExtension(list.get(i).toString()))) {
					boolean is=FileReplace_case(list.get(i).toString(), regex, replacement, matchCase);
					if (!is) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
