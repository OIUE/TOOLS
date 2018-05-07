/**
 * 
 */
package org.oiue.tools.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.oiue.tools.string.StringUtil;

/**
 * 类说明:
 * 
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942 OIUEFile 1.0 Apr 18, 2009 4:06:03 PM StringForDate
 */
public class StringForDate {
	
	/**
	 * 
	 */
	public StringForDate() {
	}
	
	/**
	 * 方法说明： 格式化日期到 Mysql 数据库日期格式字符串的显示.yyyy-MM-dd HH:mm:ss CreateTime Apr 18, 2009 4:25:43 PM
	 * @param date 需格式化的时间对象
	 * @return String 格式化后的时间 yyyy-MM-dd HH:mm:ss
	 */
	public static String formatDateToMysqlString(Date date) {
		if (date == null) {
			return "";
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return dateFormat.format(date);
	}
	
	/**
	 * 方法说明： 格式化日期到 Mysql 数据库日期格式字符串的显示.yyyy-MM-dd HH:mm:ss CreateTime Apr 18, 2009 4:25:43 PM
	 * @param date 需格式化的时间对象
	 * @return String 格式化后的时间 yyyy-MM-dd HH:mm:ss
	 */
	public static String formatTimestampToMysqlString(Timestamp date) {
		if (date == null) {
			return "";
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return dateFormat.format(date);
	}
	
	/**
	 * 方法说明： 格式化日期到日时分秒时间格式的显示. d日 HH:mm:ss CreateTime Apr 18, 2009 4:07:51 PM
	 * @param date 需格式化的时间对象
	 * @return - String 格式化后的时间 d日 HH:mm:ss
	 */
	public static String formatDateToDHMSString(Date date) {
		if (date == null) {
			return "";
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("d日 HH:mm:ss");
		
		return dateFormat.format(date);
	}
	
	/**
	 * 方法说明： 格式化日期到时分秒时间格式的显示. HH:mm:ss CreateTime Apr 18, 2009 4:10:53 PM
	 * @param date 需格式化的时间对象
	 * @return - String 格式化后的时间 HH:mm:ss
	 */
	public static String formatDateToHMSString(Date date) {
		if (date == null) {
			return "";
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		
		return dateFormat.format(date);
	}
	
	/**
	 * 方法说明： 返回时间字符串, 可读形式的, yy年M月d日HH:mm CreateTime Apr 18, 2009 4:34:10 PM
	 * @param date 需格式化的时间对象
	 * @return - String 格式化后的时间 yy年M月d日HH:mm
	 */
	public static String formatDateToyyMMddHHmm(Date date) {
		if (date == null) {
			return "";
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy年M月d日HH:mm");
		
		return dateFormat.format(date);
	}
	
	/**
	 * 方法说明： 将日期转换为中文表示方式的字符串(格式为 yyyy年MM月dd日 HH:mm:ss). CreateTime Apr 18, 2009 4:58:10 PM
	 * @param date Date 日期对象
	 * @return - String 格式化后的时间 yyyy年MM月dd日 HH:mm:ss
	 */
	public static String dateToChineseString(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return dateFormat.format(date);
	}
	
	/**
	 * 方法说明： 返回时间字符串, 可读形式的, M月d日 HH:mm CreateTime Apr 18, 2009 4:32:49 PM
	 * @param date 需格式化的时间对象
	 * @return - String 格式化后的时间 M月d日 HH:mm
	 */
	public static String formatDateToMMddHHmm(Date date) {
		if (date == null) {
			return "";
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("M月d日 HH:mm");
		
		return dateFormat.format(date);
	}
	
	/**
	 * 方法说明： 生成一个 18 位的日期字符串 格式.yyyyMMddHHmmss.SSS CreateTime Apr 18, 2009 4:35:55 PM
	 * @param date 需格式化的时间对象
	 * @return - String 格式化后的时间 yyyyMMddHHmmss.SSS
	 */
	public static String dateTo18String(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
		return df.format(date);
	}
	
	/**
	 * 方法说明： 生成一个 14 位的日期字符串 格式.yyyyMMddHHmmss CreateTime Apr 18, 2009 4:38:42 PM
	 * @param date 需格式化的时间对象
	 * @return - String 格式化后的时间 yyyyMMddHHmmss
	 */
	public static String dateTo14String(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date);
	}
	
	/**
	 * 方法说明： 将 14 位的字符串(格式为yyyyMMddHHmmss)转换为日期. CreateTime Apr 18, 2009 4:40:54 PM
	 * @param sourceStr String需转换的源字符串
	 * @return Date 日期对象
	 * @throws ParseException 转换异常
	 */
	public static Date string14ToDate(String sourceStr) throws ParseException {
		if (StringUtil.isEmpty(sourceStr)) {
			return null;
		}
		
		if (sourceStr.length() != 14) {
			return null;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		
		return dateFormat.parse(sourceStr);
	}
	
	/**
	 * 方法说明： 将 Mysql 数据库日期格式字符串转换为日期. yyyy-MM-dd HH:mm:ss CreateTime Apr 18, 2009 4:29:13 PM
	 * @param sourceStr String需转换的源字符串
	 * @return Date 日期对象
	 * @throws ParseException 转换异常
	 */
	public static Date parseStringToMysqlDate(String sourceStr) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.parse(sourceStr);
	}
	
	/**
	 * 方法说明： 将 Mysql 数据库日期格式字符串转换为日期. yyyy-MM-dd HH:mm:ss CreateTime Apr 18, 2009 4:29:13 PM
	 * @param sourceStr String需转换的源字符串
	 * @return Date 日期对象
	 * @throws ParseException 转换异常
	 */
	public static Date parseStringToYMD(String sourceStr) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.parse(sourceStr);
	}
	
	/**
	 * 方法说明： 将时分秒时间格式的字符串转换为日期. HH:mm:ss(Date) CreateTime Apr 18, 2009 4:23:08 PM
	 * @param sourceStr String需转换的源字符串
	 * @return Date 日期对象HH:mm:ss(Date)
	 * @throws ParseException 转换异常
	 */
	public static Date parseHMSStringToDate(String sourceStr) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.parse(sourceStr);
	}
	
}
