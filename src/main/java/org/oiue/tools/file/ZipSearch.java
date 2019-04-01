/**
 * 
 */
package org.oiue.tools.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;

import org.apache.tools.zip.ZipFile;

/**
 * 类说明: ZIP压缩文件检查
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942 ZipSearch 1.0 Apr 18, 2009 12:46:22 AM ZipSearch
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ZipSearch {
	private static List list = new ArrayList();
	
	/**
	 * 
	 */
	public ZipSearch() {}
	
	public static List zipLibSearch(String fileName, int searchlibNum) {
		return zipLibSearch(fileName, searchlibNum, "UTF-8");
	}
	/**
	 * 方法说明： 获取zip压缩文件中一层的目录 CreateTime May 18, 2009 3:21:47 PM
	 * @param fileName zip压缩文件
	 * @param searchlibNum 第几层
	 * @param encoding 编码
	 * @return 返回一层中的文件
	 */
	public static List zipLibSearch(String fileName, int searchlibNum,String encoding) {
		
		ZipFile slf_file = null;
		try {
			slf_file = new ZipFile(fileName,encoding);
		} catch (Exception e) {}
		Enumeration e = slf_file.getEntries();
		while (e.hasMoreElements()) {
			ZipEntry slf_zipEntry = (ZipEntry) e.nextElement();
			if (slf_zipEntry.isDirectory()) {
				StringTokenizer st = new StringTokenizer(slf_zipEntry.getName(), "/");
				int k = st.countTokens();
				if (k >= 1) {
					List slf_temp = new ArrayList();
					while (st.hasMoreTokens()) {
						slf_temp.add(st.nextToken());
					}
					if (slf_temp.size() > searchlibNum) {
						if (list.contains(slf_temp.get(searchlibNum)) == false) {
							list.add(slf_temp.get(searchlibNum));
							// System.out.println(slf_temp.get(searchlibNum));
						}
					}
				}
			} else {
				if (slf_zipEntry.getSize() < 0) {
					// System.out.println("压缩包内的文件大小不符合规范");
					try {
						slf_file.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					return null;
				}
			}
		}
		try {
			slf_file.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 方法说明： 获取zip中的所有文件列表 CreateTime May 18, 2009 3:22:57 PM
	 * @param fileName zip压缩文件
	 * @return 所有文件列表
	 */
	public static List getZipFile(String fileName) {
		ZipFile slf_zipFile = null;
		try {
			slf_zipFile = new ZipFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Enumeration e = slf_zipFile.getEntries();
		while (e.hasMoreElements()) {
			ZipEntry slf_zipEntry = (ZipEntry) e.nextElement();
			if (!slf_zipEntry.isDirectory()) {
				String filename = slf_zipEntry.getName().substring(slf_zipEntry.getName().lastIndexOf("/") + 1, slf_zipEntry.getName().length());
				list.add(filename);
			}
		}
		return list;
	}
	
	/**
	 * 方法说明： 目录比对 CreateTime May 18, 2009 3:24:00 PM
	 * @param list 列表
	 * @param userIndex 开始索引
	 * @param fileList 比较列表
	 * @return 比较结果
	 */
	public static List compare(List list, int userIndex, List fileList) {
		List slf_noTreeList = new ArrayList();
		String[] all = (String[]) list.get(userIndex);
		List slf_hasTreeList = new ArrayList();
		for (int userIndex2 = 0; userIndex2 < all.length; userIndex2++)
			getUserLib: {
				for (int fileTree = 0; fileTree < fileList.size(); fileTree++) {
					// 获取文件包中的目录
					if (fileList.get(fileTree).toString().matches(all[userIndex2])) {
						fileList.remove(all[userIndex2]);// 如果目录与用户的要求匹配,则下面不需再匹配这个目录
						slf_hasTreeList.add(all[userIndex2]);
						break getUserLib;
					} else {
						if (slf_noTreeList.contains(all[userIndex2]) == false) {
							slf_noTreeList.add(all[userIndex2]);
						}
					}
				}
			}
		
		for (int i = 0; i < slf_hasTreeList.size(); i++) {
			if (slf_noTreeList.contains(slf_hasTreeList.get(i))) {
				slf_noTreeList.remove(slf_hasTreeList.get(i));
			}
		}
		return slf_noTreeList;
	}
	
	/**
	 * 方法说明： 文件名比对 CreateTime May 18, 2009 3:25:26 PM
	 * @param fileName 文件名
	 * @param fileRoot 目录
	 * @return 结果
	 */
	public static List fileNameCheck(String[] fileName, String fileRoot) {
		List slf_dirFilesList = new ArrayList();
		List slf_fileList = new ArrayList();
		slf_fileList = getZipFile(fileRoot);
		String str = null;
		for (int i = 0; i < fileName.length; i++) {
			if (slf_fileList.contains(fileName[i]) == false) {
				str = fileName[i] + "不存在";
				slf_dirFilesList.add(str);
			}
		}
		return slf_dirFilesList;
	}
	
	/**
	 * 方法说明： 结果集处理 CreateTime May 18, 2009 3:26:39 PM
	 * @param userInfoSeach 列表
	 * @param fileName 文件名
	 * @return 结果
	 */
	public static List SeacherResult(List userInfoSeach, String fileName) {
		int needTreeNum = userInfoSeach.size();
		String slf_result = "";
		List list = new ArrayList();
		List resultlist = new ArrayList();
		
		List slf_noTreeList = new ArrayList();
		for (int nowSeachTree = 1; nowSeachTree <= needTreeNum; nowSeachTree++)
			b: {
				// System.out.println(needTreeNum);
				list = zipLibSearch(fileName, nowSeachTree - 1);
				
				if (list != null) {
					if (list.size() == 0) {
						slf_result = "此文件目录不完整,请检查......";
						resultlist.add(slf_result);
						return resultlist;// fileSeach.fileTreeChoose(fileName, nowSeachTree);
					} else {
						slf_noTreeList = compare(userInfoSeach, nowSeachTree - 1, list);
						if (!slf_noTreeList.isEmpty()) {
							for (int i = 0; i < slf_noTreeList.size(); i++) {
								slf_result = "第" + nowSeachTree + "层的" + slf_noTreeList.get(i) + "目录不存在";
								resultlist.add(slf_result);
							}
						} else {
							if (nowSeachTree == needTreeNum && needTreeNum == 1) {
								return null;
							} else {
								resultlist = null;
								break b;
							}
						}
					}
				} else {
					slf_result = "压缩包内文件大小不符合规范,请检查.......";
					resultlist.add(slf_result);
					return resultlist;
				}
			}
		if (resultlist == null) {
			return null;
		} else {
			return resultlist;
		}
	}
	
}
