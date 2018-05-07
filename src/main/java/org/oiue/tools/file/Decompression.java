package org.oiue.tools.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.apache.tools.tar.TarOutputStream;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.oiue.tools.StatusResult;
import org.oiue.tools.exception.OIUEException;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

/**
 * 类说明: 压缩、解压文件公用类
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942 创建时间：Feb 26, 2009 6:01:11 PM
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Decompression {
	private static final int BUFFEREDSIZE = 1024;
	
	public Decompression() {
	}
	
	/**
	 * 解压zip格式的压缩文件到当前文件夹
	 * @param zipFileName 文件名
	 * @author Every
	 */
	public static void unzipFile(String zipFileName) {
		try {
			File f = new File(zipFileName);
			ZipFile zipFile = new ZipFile(zipFileName);
			if ((!f.exists()) && (f.length() <= 0)) {
				throw new Exception("要解压的文件不存在!");
			}
			String strPath, gbkPath, strtemp;
			File tempFile = new File(f.getParent());
			strPath = tempFile.getAbsolutePath();
			java.util.Enumeration e = zipFile.getEntries();
			while (e.hasMoreElements()) {
				org.apache.tools.zip.ZipEntry zipEnt = (ZipEntry) e.nextElement();
				gbkPath = zipEnt.getName();
				if (zipEnt.isDirectory()) {
					strtemp = strPath + File.separator + gbkPath;
					File dir = new File(strtemp);
					dir.mkdirs();
					continue;
				} else {
					// 读写文件
					InputStream is = zipFile.getInputStream(zipEnt);
					BufferedInputStream bis = new BufferedInputStream(is);
					gbkPath = zipEnt.getName();
					strtemp = strPath + File.separator + gbkPath;
					
					// 建目录
					String strsubdir = gbkPath;
					for (int i = 0; i < strsubdir.length(); i++) {
						if (strsubdir.substring(i, i + 1).equalsIgnoreCase(File.separator)) {
							String temp = strPath + File.separator + strsubdir.substring(0, i);
							File subdir = new File(temp);
							if (!subdir.exists())
								subdir.mkdir();
						}
					}
					FileOutputStream fos = new FileOutputStream(strtemp);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					int c;
					while ((c = bis.read()) != -1) {
						bos.write((byte) c);
					}
					bos.close();
					fos.close();
				}
			}
		} catch (Exception e) {
			throw new OIUEException(StatusResult._blocking_errors, zipFileName, e);
		}
	}
	
	/**
	 * 解压zip格式的压缩文件到指定位置
	 * @param zipFileName 压缩文件
	 * @param extPlace 解压目录
	 * 
	 * @author Every
	 */
	public static void unzip(String zipFileName, String extPlace) {
		try {
			(new File(extPlace)).mkdirs();
			File f = new File(zipFileName);
			ZipFile zipFile = new ZipFile(zipFileName);
			if ((!f.exists()) && (f.length() <= 0)) {
				throw new Exception("要解压的文件不存在!");
			}
			String strPath, gbkPath, strtemp;
			File tempFile = new File(extPlace);
			strPath = tempFile.getAbsolutePath();
			java.util.Enumeration e = zipFile.getEntries();
			while (e.hasMoreElements()) {
				org.apache.tools.zip.ZipEntry zipEnt = (ZipEntry) e.nextElement();
				gbkPath = zipEnt.getName();
				if (zipEnt.isDirectory()) {
					strtemp = strPath + File.separator + gbkPath;
					File dir = new File(strtemp);
					dir.mkdirs();
					continue;
				} else {
					// 读写文件
					InputStream is = zipFile.getInputStream(zipEnt);
					BufferedInputStream bis = new BufferedInputStream(is);
					gbkPath = zipEnt.getName();
					strtemp = strPath + File.separator + gbkPath;
					
					// 建目录
					String strsubdir = gbkPath;
					for (int i = 0; i < strsubdir.length(); i++) {
						if (strsubdir.substring(i, i + 1).equalsIgnoreCase(File.separator)) {
							String temp = strPath + File.separator + strsubdir.substring(0, i);
							File subdir = new File(temp);
							if (!subdir.exists())
								subdir.mkdir();
						}
					}
					FileOutputStream fos = new FileOutputStream(strtemp);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					int c;
					while ((c = bis.read()) != -1) {
						bos.write((byte) c);
					}
					bos.close();
					fos.close();
				}
			}
		} catch (Exception e) {
			throw new OIUEException(StatusResult._blocking_errors, zipFileName, e);
		}
	}
	
	/**
	 * 解压zip格式的压缩文件到指定位置
	 * @param zipFileName 压缩文件
	 * @param extPlace 解压目录
	 * @param recursive 是否
	 * 
	 * @author Every
	 */
	public static void unzip(String zipFileName, String extPlace, boolean recursive) {
		try {
			(new File(extPlace)).mkdirs();
			File f = new File(zipFileName);
			ZipFile zipFile = new ZipFile(zipFileName);
			if ((!f.exists()) && (f.length() <= 0)) {
				throw new Exception("要解压的文件不存在!");
			}
			String strPath, gbkPath, strtemp;
			File tempFile = new File(extPlace);
			strPath = tempFile.getAbsolutePath();
			java.util.Enumeration e = zipFile.getEntries();
			while (e.hasMoreElements()) {
				org.apache.tools.zip.ZipEntry zipEnt = (ZipEntry) e.nextElement();
				gbkPath = zipEnt.getName();
				if (zipEnt.isDirectory()) {
					strtemp = strPath + File.separator + gbkPath;
					File dir = new File(strtemp);
					dir.mkdirs();
					continue;
				} else {
					// 读写文件
					InputStream is = zipFile.getInputStream(zipEnt);
					BufferedInputStream bis = new BufferedInputStream(is);
					gbkPath = zipEnt.getName();
					strtemp = strPath + File.separator + gbkPath;
					
					// 建目录
					String strsubdir = gbkPath;
					for (int i = 0; i < strsubdir.length(); i++) {
						if (strsubdir.substring(i, i + 1).equalsIgnoreCase(File.separator)) {
							String temp = strPath + File.separator + strsubdir.substring(0, i);
							File subdir = new File(temp);
							if (!subdir.exists())
								subdir.mkdir();
						}
					}
					FileOutputStream fos = new FileOutputStream(strtemp);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					int c;
					while ((c = bis.read()) != -1) {
						bos.write((byte) c);
					}
					bos.close();
					fos.close();
				}
			}
		} catch (Exception e) {
			throw new OIUEException(StatusResult._blocking_errors, zipFileName, e);
		}
	}
	
	/**
	 * 压缩zip格式的压缩文件
	 * @param inputFilename 压缩的文件或文件夹及详细路径
	 * @param zipFilename 输出文件名称及详细路径
	 * @throws IOException IO异常
	 * @author Every
	 */
	public static void zip(String inputFilename, String zipFilename) throws IOException {
		zip(new File(inputFilename), zipFilename);
	}
	
	/**
	 * 压缩zip格式的压缩文件
	 * @param inputFile 需压缩文件
	 * @param zipFilename 输出文件及详细路径
	 * @throws IOException IO异常
	 * @author Every
	 */
	public static void zip(File inputFile, String zipFilename) throws IOException {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilename));
		try {
			zip(inputFile, out, "");
		} catch (IOException e) {
			throw e;
		} finally {
			out.close();
		}
	}
	
	/**
	 * 压缩zip格式的压缩文件
	 * @param inputFile 需压缩文件
	 * @param out 输出压缩文件
	 * @param base 结束标识
	 * @throws IOException IO异常
	 * @author Every
	 */
	private static void zip(File inputFile, ZipOutputStream out, String base) throws IOException {
		if (inputFile.isDirectory()) {
			File[] inputFiles = inputFile.listFiles();
			out.putNextEntry(new ZipEntry(base + File.separator));
			base = base.length() == 0 ? "" : base + File.separator;
			for (int i = 0; i < inputFiles.length; i++) {
				zip(inputFiles[i], out, base + inputFiles[i].getName());
			}
		} else {
			if (base.length() > 0) {
				out.putNextEntry(new ZipEntry(base));
			} else {
				out.putNextEntry(new ZipEntry(inputFile.getName()));
			}
			FileInputStream in = new FileInputStream(inputFile);
			try {
				int c;
				byte[] by = new byte[BUFFEREDSIZE];
				while ((c = in.read(by)) != -1) {
					out.write(by, 0, c);
				}
			} catch (IOException e) {
				throw e;
			} finally {
				in.close();
			}
		}
	}
	
	public static String bytes2Str(byte[] b) {
		StringBuffer bs = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			bs.append(b[i] + " ");
		}
		return bs.toString();
	}
	
	/**
	 * 解压tar格式的压缩文件到指定目录下
	 * @param tarFileName 压缩文件
	 * @param extPlace 解压目录
	 * 
	 * @author Every
	 */
	public static void untar(String tarFileName, String extPlace) {
		InputStream inputstream = null;
		OutputStream outputstream = null;
		TarInputStream zis = null;
		try {
			File file = new File(tarFileName);
			File tempFile = null;
			inputstream = new FileInputStream(file);
			zis = new TarInputStream(inputstream);
			/*
			 * 关键在于这个TarEntry 的理解， 实际你的tar包里有多少文件就有多 少TarEntry
			 */
			TarEntry tarEntry = null;
			File f = new File(extPlace);
			if (!f.exists()) {
				f.mkdir();
			}
			while ((tarEntry = zis.getNextEntry()) != null) {
				tempFile = new File(extPlace + File.separator + tarEntry.getName());
				tempFile.createNewFile();
				outputstream = new FileOutputStream(tempFile);
				// 定一个缓存池 可以根据实际情况调整大小（事实证明很有用）
				byte[] buffer = new byte[1024 * 50];
				while (true) {
					int readsize = zis.read(buffer);
					outputstream.write(buffer);
					if (readsize < 1024 * 50) {
						break;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputstream.flush();
				inputstream.close();
				zis.close();
				outputstream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 压缩tar格式的压缩文件
	 * @param inputFilename 压缩文件
	 * @param tarFilename 输出路径
	 * @throws IOException IO异常
	 * @author Every
	 */
	public static void tar(String inputFilename, String tarFilename) throws IOException {
		tar(new File(inputFilename), tarFilename);
	}
	
	/**
	 * 压缩tar格式的压缩文件
	 * @param inputFile 压缩文件
	 * @param tarFilename 输出路径
	 * @throws IOException IO异常
	 * @author Every
	 */
	public static void tar(File inputFile, String tarFilename) throws IOException {
		TarOutputStream out = new TarOutputStream(new FileOutputStream(tarFilename));
		try {
			tar(inputFile, out, "");
		} catch (IOException e) {
			throw e;
		} finally {
			out.close();
		}
	}
	
	/**
	 * 压缩tar格式的压缩文件
	 * @param inputFile 压缩文件
	 * @param out 输出文件
	 * @param base 结束标识
	 * @throws IOException IO异常
	 * @author Every
	 */
	private static void tar(File inputFile, TarOutputStream out, String base) throws IOException {
		if (inputFile.isDirectory()) {
			File[] inputFiles = inputFile.listFiles();
			out.putNextEntry(new TarEntry(base + File.separator));
			base = base.length() == 0 ? "" : base + File.separator;
			for (int i = 0; i < inputFiles.length; i++) {
				tar(inputFiles[i], out, base + inputFiles[i].getName());
			}
		} else {
			if (base.length() > 0) {
				out.putNextEntry(new TarEntry(base));
			} else {
				out.putNextEntry(new TarEntry(inputFile.getName()));
			}
			FileInputStream in = new FileInputStream(inputFile);
			try {
				int c;
				byte[] by = new byte[BUFFEREDSIZE];
				while ((c = in.read(by)) != -1) {
					out.write(by, 0, c);
				}
			} catch (IOException e) {
				throw e;
			} finally {
				in.close();
			}
		}
	}
	
	/**
	 * 解压rar格式的压缩文件到指定目录下
	 * @param rarFileName 压缩文件
	 * @param extPlace 解压目录
	 * @param repeatFile 重复路径
	 * @param del 解压后是否删除文件
	 * 
	 * @author Every
	 */
	@SuppressWarnings("hiding")
	public static void unrar(String rarFileName, String extPlace, List repeatFile, boolean del) {
		
		File f = new File(rarFileName);
		FileOutputStream os = null;
		Archive a = null;
		try {
			try {
				a = new Archive(f);
			} catch (RarException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			extPlace = (extPlace == null || extPlace.trim().equals("")) ? f.getParent() + File.separator + f.getName().replace(".", "#").split("#")[0] : extPlace;
			File ext = new File(extPlace);
			if (!ext.exists()) {
				ext.mkdirs();
			}
			if (a != null) {
				a.getMainHeader().print();
				FileHeader fh = a.nextFileHeader();
				while (fh != null) {
					try {
						File out = new File(extPlace + File.separator + fh.getFileNameString().trim());
						if (out.exists() && out.isDirectory()) {
							
						} else if (out.exists() && repeatFile != null) {
							File rFile = new File(extPlace + File.separator + "tempFile");
							if (!rFile.exists())
								rFile.mkdirs();
							repeatFile.add(fh.getFileNameString().trim());
							out = new File(extPlace + File.separator + "tempFile" + File.separator + fh.getFileNameString().trim());
							File pp = new File(out.getParent());
							if (!pp.exists()) {
								pp.mkdirs();
							}
							pp = null;
							os = new FileOutputStream(out);
						} else {
							File pp = new File(out.getParent());
							if (!pp.exists()) {
								pp.mkdirs();
							}
							pp = null;
							os = new FileOutputStream(out);
						}
						a.extractFile(fh, os);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (RarException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (os != null) {
							os.close();
							os = null;
						}
					}
					fh = a.nextFileHeader();
				}
			}
		} catch (Throwable e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (a != null)
				;
			try {
				a.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (del) {
				f.delete();
			}
		}
	}
	
	/**
	 * 解压rar格式的压缩文件到指定目录下
	 * @param rarFileName 压缩文件
	 * @param extPlace 解压目录
	 * @param repeatFile 重复路径
	 * 
	 * @author Every
	 */
	public void unrar(String rarFileName, String extPlace, List repeatFile) {
		unrar(rarFileName, extPlace, repeatFile, false);
	}
	
	/**
	 * 解压rar格式的压缩文件到当前文件所在目录下
	 * @param rarFileName 压缩文件
	 * @param repeatFile 重复路径
	 * 
	 * @author Every
	 */
	public void unrar(String rarFileName, List repeatFile) {
		unrar(rarFileName, null, repeatFile, false);
	}
	
	/**
	 * 解压rar格式的压缩文件到当前文件所在目录下
	 * @param rarFileName 压缩文件
	 * @param extPlace 解压目录
	 * 
	 * @author Every
	 */
	public void unrar(String rarFileName, String extPlace) {
		unrar(rarFileName, extPlace, null, false);
	}
	
	/**
	 * 解压rar格式的压缩文件到当前文件所在目录下
	 * @param rarFileName 压缩文件
	 * 
	 * @author Every
	 */
	public void unrar(String rarFileName) {
		unrar(rarFileName, null, null, false);
	}
	
}
