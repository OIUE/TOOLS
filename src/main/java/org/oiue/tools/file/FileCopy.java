/**
 * 
 */
package org.oiue.tools.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 类说明: 复制文件公用类
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942 WMWFileCopyFile 1.0 Apr 17, 2009 11:17:06 PM
 */
public class FileCopy {
	private static final int BUFFEREDSIZE = 1024;
	
	/**
	 * 
	 */
	public FileCopy() {}
	
	/**
	 * 方法说明： 复制文件或文件夹 CreateTime Apr 17, 2009 11:22:21 PM
	 * @param sourceDir 原文件
	 * @param targetDir 复制到路径
	 * @throws IOException IO异常
	 */
	public static void CopyOFFile(String sourceDir, String targetDir) throws IOException {
		File file = new File(sourceDir);
		(new File(targetDir)).mkdirs();
		if (file.isFile()) {
			copyFile(file, new File(targetDir));
		} else {
			// 获取源文件夹当前下的文件或目录
			File[] files = (new File(sourceDir)).listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					// 复制文件
					copyFile(files[i], new File(targetDir + File.separator + files[i].getName()));
				}
				if (files[i].isDirectory()) {
					// 复制目录
					String sourcesDir = sourceDir + File.separator + files[i].getName();
					String targetsDir = targetDir + File.separator + files[i].getName();
					copyDirectiory(sourcesDir, targetsDir);
				}
			}
		}
	}
	
	/**
	 * 方法说明： 复制文件 CreateTime Apr 17, 2009 11:23:15 PM
	 * @param sourceFile 源文件
	 * @param targetFile 新文件
	 * @throws IOException IO异常
	 */
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);
		
		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);
		
		// 缓冲数组
		byte[] b = new byte[BUFFEREDSIZE * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();
		
		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}
	
	/**
	 * 方法说明： 复制文件夹 CreateTime Apr 17, 2009 11:24:53 PM
	 * @param sourceDir 源目录
	 * @param targetDir 复制到的目录
	 * @throws IOException IO异常
	 */
	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}
	
}
