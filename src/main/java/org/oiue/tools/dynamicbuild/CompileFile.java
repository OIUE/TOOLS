package org.oiue.tools.dynamicbuild;

import java.io.File;

import org.oiue.tools.Application;

/**
 * 编译已知的java类文件 编译后的class类文件会出现在当前位置对应的创建包路径下 如 ： 文件为c:\test\dnyBuild.java, dnyBuild类中package org.oiue.tools.dynamicbuild; 则生成文件c:\test\org\oiue\tools\dynamicbuild\dnyBuild.class
 *
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942 创建时间：Feb 26, 2009 6:01:11 PM
 *
 */
@SuppressWarnings({ "unused" })
public class CompileFile extends Thread {
	/** 编译器 */
	// private static com.sun.tools.javac.Main javac=new com.sun.tools.javac.Main();
	private String filePath = null;
	private String compPath = null;
	private String classPath = Application.getClassRootPath();
	private String encoding = "UTF-8";
	private File file = null;
	
	/**
	 * 编译java类文件，不指定编译路径默认为项目Class根目录
	 * @param filePath 类文件全路径
	 * @param compPath 类编译全路径
	 * @param classPath 编译相对路径
	 * @param encoding 编码
	 */
	public CompileFile(String filePath, String compPath, String classPath, String encoding) {
		this.filePath = filePath;
		this.compPath = compPath;
		this.classPath = classPath;
		this.encoding = encoding;
	}
	
	/**
	 * 编译java类文件
	 * @param filePath 文件路径
	 * @param compPath 类编译全路径
	 */
	public CompileFile(String filePath, String compPath) {
		this.filePath = filePath;
		this.compPath = compPath;
	}
	
	/**
	 * 编译指定文件
	 * @param file 文件
	 */
	public CompileFile(File file) {
		this.file = file;
	}
	
	@Override
	public void run() {
		// 编译代码文件 编译路径 文件绝对路径
		System.out.println("编译文件：" + filePath + "\t" + compPath);
		System.out.println("classPath:" + classPath);
		if (file == null)
			file = new File(filePath);
		// 编译代码文件
		String[] args = new String[] { "-encoding", encoding, "-cp", classPath, "-d", compPath, file.getAbsolutePath() };
		// 返回编译的状态代码
		// int status=javac.compile(args);
	}
}
