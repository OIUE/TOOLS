package org.oiue.tools.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期的加减运算、日期的运算处理 有关日期工具类
 * 
 * yyyy-MM-dd HH:mm:ss[.fff] 格式1:"yyyy-MM-dd" 格式2:"yyyy-MM-dd HH:mm:ss EE" 格式3:"yyyy年MM月dd日 hh:mm:ss EE"
 */
@SuppressWarnings({ "unused" })
public class DateUtil extends TimeUtil {
	
	public DateUtil() {}
	
	// ----------------------日期计算---------------------------------------------------------------------------------
	
	/**
	 * 是否开始日期在结束日期之前(不包括相等)
	 * @param p_startDate 开始时间
	 * @param p_endDate 结束时间
	 * @return boolean 在结束日期前:ture;否则：false
	 * @throws ParseException 转换出错
	 * @author zhuqx : 2006-10-31
	 */
	public static boolean isStartDateBeforeEndDate(Date p_startDate, Date p_endDate) throws ParseException {
		long l_startTime = getMillisOfDate(p_startDate);
		long l_endTime = getMillisOfDate(p_endDate);
		return (l_startTime - l_endTime > (long) 0) ? true : false;
	}
	
	/**
	 * 获取2个字符日期的天数差
	 * @param p_startDate 开始
	 * @param p_endDate 结束
	 * @return 天数差
	 * @throws ParseException 转换出错
	 * @author zhuqx : 2006-10-31
	 */
	public static long getDaysOfTowDiffDate(String p_startDate, String p_endDate) throws ParseException {
		Date l_startDate = toUtilDateFromStrDateByFormat(p_startDate, "yyyy-MM-dd");
		Date l_endDate = toUtilDateFromStrDateByFormat(p_endDate, "yyyy-MM-dd");
		long l_startTime = getMillisOfDate(l_startDate);
		long l_endTime = getMillisOfDate(l_endDate);
		long betweenDays = (long) ((l_endTime - l_startTime) / (1000 * 60 * 60 * 24));
		return betweenDays;
	}
	
	/**
	 * 获取2个字符日期的周数差
	 * @param p_startDate 开始
	 * @param p_endDate 结束
	 * @return 周数差
	 * @throws ParseException 转换出错
	 * @author zhuqx : 2006-10-31
	 */
	public static long getWeeksOfTowDiffDate(String p_startDate, String p_endDate) throws ParseException {
		return getDaysOfTowDiffDate(p_startDate, p_endDate) / 7;
	}
	
	/**
	 * 获取2个字符日期的月数差
	 * @param p_startDate 开始
	 * @param p_endDate 结束
	 * @return 月数差
	 * @throws ParseException 转换出错
	 * @author zhuqx : 2006-10-31
	 */
	public static long getMonthsOfTowDiffDate(String p_startDate, String p_endDate) throws ParseException {
		return getDaysOfTowDiffDate(p_startDate, p_endDate) / 30;
	}
	
	/**
	 * 获取2个字符日期的年数差
	 * @param p_startDate 开始
	 * @param p_endDate 结束
	 * @return 年数差
	 * @throws ParseException 转换出错
	 * @author zhuqx : 2006-10-31
	 */
	public static long getYearsOfTowDiffDate(String p_startDate, String p_endDate) throws ParseException {
		return getDaysOfTowDiffDate(p_startDate, p_endDate) / 365;
	}
	
	/**
	 * 在给定的日期基础上添加年，月，日、时，分，秒 例如要再2006－10－21（uitl日期）添加3个月，并且格式化为yyyy-MM-dd格式， 这里调用的方式为 addDate(2006－10－21,3,Calendar.MONTH,"yyyy-MM-dd")
	 * @param p_startDate 给定的日期
	 * @param p_count 时间的数量
	 * @param p_field 添加的域
	 * @param p_format 时间转化格式，例如：yyyy-MM-dd hh:mm:ss 或者yyyy-mm-dd等
	 * @return 添加后格式化的时间
	 * @throws ParseException 转换出错 : 2006-10-31
	 */
	public static String addDate(Date p_startDate, Integer p_count, Integer p_field, String p_format) throws ParseException {
		if (p_startDate == null) {
			p_startDate = new Date();
		}
		// 年，月，日、时，分，秒
		int l_year = getYearOfDate(p_startDate);
		int l_month = getMonthOfDate(p_startDate) - 1;
		int l_day = getDayOfDate(p_startDate);
		int l_hour = getHourOfDate(p_startDate);
		int l_minute = getMinuteOfDate(p_startDate);
		int l_second = getSecondOfDate(p_startDate);
		Calendar l_calendar = new GregorianCalendar(l_year, l_month, l_day, l_hour, l_minute, l_second);
		l_calendar.add(p_field, p_count);
		return DateUtil.toStrDateFromUtilDateByFormat(l_calendar.getTime(), p_format);
	}
	
	/**
	 * 在给定的日期基础上添加年，月，日、时，分，秒 例如要再2006－10－21（uitl日期）添加3个月，并且格式化为yyyy-MM-dd格式， 这里调用的方式为 addDate(2006－10－21,3,Calendar.MONTH,"yyyy-MM-dd")
	 * @param p_startDate 给定的日期
	 * @param p_count 时间的数量
	 * @param p_field 添加的域
	 * @return 添加后格式化的时间
	 * @throws ParseException 转换出错 : 2006-10-31
	 */
	public static Date addDate(Date p_startDate, Integer p_count, Integer p_field) throws ParseException {
		if (p_startDate == null) {
			p_startDate = new Date();
		}
		// 年，月，日、时，分，秒
		int l_year = getYearOfDate(p_startDate);
		int l_month = getMonthOfDate(p_startDate) - 1;
		int l_day = getDayOfDate(p_startDate);
		int l_hour = getHourOfDate(p_startDate);
		int l_minute = getMinuteOfDate(p_startDate);
		int l_second = getSecondOfDate(p_startDate);
		Calendar l_calendar = new GregorianCalendar(l_year, l_month, l_day, l_hour, l_minute, l_second);
		l_calendar.add(p_field, p_count);
		return l_calendar.getTime();
	}
	
	/**
	 * 判断给定日期是不是润年
	 * @param p_date 给定日期
	 * @return boolean 如果给定的年份为闰年，则返回 true；否则返回 false。 : 2006-10-31
	 */
	public static boolean isLeapYear(Date p_date) {
		int l_year = getYearOfDate(p_date);
		GregorianCalendar l_calendar = new GregorianCalendar();
		return l_calendar.isLeapYear(l_year);
	}
	
	/**
	 * 取得某天相加(减)後的那一天
	 * 
	 * @param date 日期
	 * @param num(可正可负)
	 * @return 结果
	 * @author Every : 2009-12-9
	 */
	public static Date getAnotherDate(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, num);
		return c.getTime();
	}
	
	/**
	 * 取得某月的的最后一天
	 * 
	 * @param year 年
	 * @param month 月
	 * @return 最后一天的日期
	 * @author Every : 2009-12-9
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);// 年
		cal.set(Calendar.MONTH, month - 1);// 月，因为Calendar里的月是从0开始，所以要减1
		cal.set(Calendar.DATE, 1);// 日，设为一号
		cal.add(Calendar.MONTH, 1);// 月份加一，得到下个月的一号
		cal.add(Calendar.DATE, -1);// 下一个月减一为本月最后一天
		return cal.getTime();// 获得月末是几号
	}
	
	/**
	 * 取得某月的的最后一天
	 * @param date 某月
	 * @return 最后一天
	 * @author Every
	 * @throws ParseException 转换错误 : 2010-7-21
	 */
	public static Date getLastDayOfMonth(Date date) throws ParseException {
		String strDate = DateUtil.addDate(date, 1, Calendar.MONTH, "yyyy-MM") + "-01 00:00:00";
		strDate = DateUtil.addDate(DateUtil.parseStringToMysqlDate(strDate), -1, Calendar.DAY_OF_MONTH, "yyyy-MM-dd HH:mm:ss");
		return DateUtil.parseStringToMysqlDate(strDate);
	}
	
	/**
	 * 取得某天是一年中的多少周
	 * 
	 * @param date 日期
	 * @return 周数
	 * @author Every : 2009-12-9
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 取得某天所在周的第一天
	 * 
	 * @param date 日期
	 * @return 第几天
	 * @author Every : 2009-12-9
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
		return c.getTime();
	}
	
	/**
	 * 取得某天所在周的最后一天
	 * 
	 * @param date 日期
	 * @return 最后一天
	 * @author Every : 2009-12-9
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		return c.getTime();
	}
	
	/**
	 * 取得某一年共有多少周
	 * 
	 * @param year 年
	 * @return 周数
	 * @author Every : 2009-12-9
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		return getWeekOfYear(c.getTime());
	}
	
	/**
	 * 取得某年某周的第一天 对于交叉:2008-12-29到2009-01-04属于2008年的最后一周,2009-01-05为2009年第一周的第一天
	 * 
	 * @param year 年
	 * @param week 周
	 * @return 第一天
	 * @author Every : 2009-12-9
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar calFirst = Calendar.getInstance();
		calFirst.set(year, 0, 7);
		Date firstDate = getFirstDayOfWeek(calFirst.getTime());
		
		Calendar firstDateCal = Calendar.getInstance();
		firstDateCal.setTime(firstDate);
		
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));
		
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, (week - 1) * 7);
		firstDate = getFirstDayOfWeek(cal.getTime());
		
		return firstDate;
	}
	
	/**
	 * 取得某年某周的最后一天 对于交叉:2008-12-29到2009-01-04属于2008年的最后一周, 2009-01-04为 2008年最后一周的最后一天
	 * 
	 * @param year 年
	 * @param week 周
	 * @return 最后一天
	 * @author Every : 2009-12-9
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar calLast = Calendar.getInstance();
		calLast.set(year, 0, 7);
		Date firstDate = getLastDayOfWeek(calLast.getTime());
		
		Calendar firstDateCal = Calendar.getInstance();
		firstDateCal.setTime(firstDate);
		
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, firstDateCal.get(Calendar.DATE));
		
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, (week - 1) * 7);
		Date lastDate = getLastDayOfWeek(cal.getTime());
		
		return lastDate;
	}
}