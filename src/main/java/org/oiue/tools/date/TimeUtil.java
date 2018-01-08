package org.oiue.tools.date;

/**
 * 有关日期工具类(extends TimeUtil)
 * 
 * TimeUtil主要功能有：
 * 1.各种日期类型（字符，util.Date，sql.Date，Calendar等）转换
 * 2.获取指定日期的年份，月份，日份，小时，分，秒，毫秒
 * 3.获取当前/系统日期(指定日期格式)
 * 4.获取字符日期一个月的天数
 * 5.获取指定月份的第一天,最后一天
 * 
 * DateUtil主要功能有：
 * 1.日期比较
 * 2.获取2个字符日期的天数差，周数差，月数差，年数差
 * 3.日期添加
 * 4.判断给定日期是不是润年
 */
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.text.*;

@SuppressWarnings({ "unused" })
public class TimeUtil extends StringForDate {
	// ---当前日期的年，月，日，时，分，秒
	public static Calendar now = Calendar.getInstance();

	int year = now.get(Calendar.YEAR);
	int date = now.get(Calendar.DAY_OF_MONTH);
	int month = now.get(Calendar.MONTH) + 1;
	int hour = now.get(Calendar.HOUR);
	int min = now.get(Calendar.MINUTE);
	int sec = now.get(Calendar.SECOND);

	// -------------------------------日期类型转换---------------------------------------------------------------------------
	/**
	 * 字符型日期转化util.Date型日期
	 * 
	 * @param p_strDate 字符型日期
	 * @param p_format
	 *            格式:"yyyy-MM-dd" / "yyyy-MM-dd hh:mm:ss"
	 * @return java.util.Date util.Date型日期
	 * @throws ParseException 转换异常
	 * @author Every
	 * : 2009-12-9
	 */
	public static java.util.Date toUtilDateFromStrDateByFormat(String p_strDate, String p_format) throws ParseException {
		java.util.Date l_date = null;
		java.text.DateFormat df = new java.text.SimpleDateFormat(p_format);
		if (p_strDate != null && (!"".equals(p_strDate)) && p_format != null && (!"".equals(p_format))) {
			l_date = df.parse(p_strDate);
		}
		return l_date;
	}

	/**
	 * 字符型日期转化成sql.Date型日期
	 * 
	 * @param p_strDate
	 *            字符型日期
	 * @return java.sql.Date sql.Date型日期
	 * @throws ParseException  转换异常
	 * @author Every
	 * : 2009-12-9
	 */
	public static java.sql.Date toSqlDateFromStrDate(String p_strDate) throws ParseException {
		java.sql.Date returnDate = null;
		java.text.DateFormat sdf = new java.text.SimpleDateFormat();
		if (p_strDate != null && (!"".equals(p_strDate))) {
			returnDate = new java.sql.Date(sdf.parse(p_strDate).getTime());
		}
		return returnDate;
	}

	/**
	 * util.Date型日期转化指定格式的字符串型日期
	 * 
	 * @param p_utilDate
	 *            Date
	 * @param p_format
	 *            String 格式1:"yyyy-MM-dd"
	 *            格式2:"yyyy-MM-dd hh:mm:ss EE/yyyy-MM-dd HH:mm:ss EE"
	 *            格式3:"yyyy年MM月dd日 hh:mm:ss EE/yyyy年MM月dd日 HH:mm:ss EE" 说明:
	 *            年-月-日 时:分:秒 星期 注意MM/mm大小写
	 * @return String
	 * @throws ParseException  转换异常
	 * @author Every 
	 * : 2011-4-18
	 */
	public static String toStrDateFromUtilDateByFormat(java.util.Date p_utilDate, String p_format) throws ParseException {
		String l_result = "";
		if (p_utilDate != null) {
			// 1. 指定时区为中国上海
			TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
			// System.out.println("基准转换时区: " + tz.getDisplayName() + ", " +
			// tz.getID());
			// 2. 格式化日历
			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat(p_format);
			// 为此"格式化日历"设置时区
			sdf.setTimeZone(tz);
			// 3. 获得时区偏移量(支持夏令时)
			long offset = tz.getOffset(p_utilDate.getTime());
			// System.out.println("时区偏移量: " + offset);
			// 4. 计算准确时间，减去偏移量
			p_utilDate.setTime(p_utilDate.getTime() - offset);
			l_result = sdf.format(p_utilDate);
		}
		return l_result;
	}

	/**
	 * util.Date型日期转化转化成Calendar日期
	 * 
	 * @param p_utilDate
	 *            Date
	 * @return Calendar
	 * @author Every
	 * : 2009-12-9
	 */
	public static Calendar toCalendarFromUtilDate(java.util.Date p_utilDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(p_utilDate);
		return c;
	}

	/**
	 * util.Date型日期转化sql.Date(年月日)型日期
	 * 
	 * @param p_utilDate util.Date型日期
	 * @return  java.sql.Date sql.Date型日期
	 * @author Every
	 * : 2009-12-9
	 */
	public static java.sql.Date toSqlDateFromUtilDate(java.util.Date p_utilDate) {
		java.sql.Date returnDate = null;
		if (p_utilDate != null) {
			returnDate = new java.sql.Date(p_utilDate.getTime());
		}
		return returnDate;
	}

	/**
	 * util.Date型日期转化sql.Time(时分秒)型日期
	 * 
	 * @param p_utilDate util.Date型日期
	 * @return  java.sql.Time sql.Time型日期
	 * @author Every
	 * : 2009-12-9
	 */
	public static java.sql.Time toSqlTimeFromUtilDate(java.util.Date p_utilDate) {
		java.sql.Time returnDate = null;
		if (p_utilDate != null) {
			returnDate = new java.sql.Time(p_utilDate.getTime());
		}
		return returnDate;
	}

	/**
	 * util.Date型日期转化sql.Date(时分秒)型日期
	 * 
	 * @param p_utilDate util.Date型日期
	 * @return  java.sql.Timestamp sql.Timestamp型日期
	 * @author Every
	 * : 2009-12-9
	 */
	public static java.sql.Timestamp toSqlTimestampFromUtilDate(java.util.Date p_utilDate) {
		java.sql.Timestamp returnDate = null;
		if (p_utilDate != null) {
			returnDate = new java.sql.Timestamp(p_utilDate.getTime());
		}
		return returnDate;
	}

	/**
	 * sql.Date型日期转化util.Date型日期
	 * 
	 * @param p_sqlDate sql.Date型日期
	 * @return  java.util.Date util.Date型日期
	 * @author Every
	 * : 2009-12-9
	 */
	public static java.util.Date toUtilDateFromSqlDate(java.sql.Date p_sqlDate) {
		java.util.Date returnDate = null;
		if (p_sqlDate != null) {
			returnDate = new java.util.Date(p_sqlDate.getTime());
		}
		return returnDate;
	}

	// -----------------获取指定日期的年份，月份，日份，小时，分，秒，毫秒----------------------------
	/**
	 * 获取指定日期的年份
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return int 年份
	 * @author Every
	 * : 2009-12-9
	 */
	public static int getYearOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.get(java.util.Calendar.YEAR);
	}

	/**
	 * 获取指定日期的月份
	 * 
	 * @param p_date
	 *            util.Date日期
	 * @return int 月份
	 * @author Every
	 * : 2009-12-9
	 */
	public static int getMonthOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.get(java.util.Calendar.MONTH) + 1;
	}

	/**
	 * 获取指定日期的日份
	 * 
	 * @param p_date util.Date日期
	 * @return int 日份
	 * @author Every
	 * : 2009-12-9
	 */
	public static int getDayOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.get(java.util.Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期的小时
	 * 
	 * @param p_date util.Date日期
	 * @return int 日份
	 * @author Every
	 * : 2009-12-9
	 */
	public static int getHourOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.get(java.util.Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取指定日期的分钟
	 * 
	 * @param p_date util.Date日期
	 * @return int 分钟
	 * @author Every
	 * : 2009-12-9
	 */
	public static int getMinuteOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.get(java.util.Calendar.MINUTE);
	}

	/**
	 * 获取指定日期的秒钟
	 * 
	 * @param p_date util.Date日期
	 * @return int 秒钟
	 * @author Every
	 * : 2009-12-9
	 */
	public static int getSecondOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.get(java.util.Calendar.SECOND);
	}

	/**
	 * 获取指定日期的毫秒
	 * 
	 * @param p_date util.Date日期
	 * @return long 毫秒
	 * @author Every
	 * : 2009-12-9
	 */
	public static long getMillisOfDate(java.util.Date p_date) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(p_date);
		return c.getTimeInMillis();
	}

	// -----------------获取当前/系统日期(指定日期格式)-----------------------------------------------------------------------------------
	/**
	 * 获取指定日期格式当前日期的字符型日期
	 * 
	 * @param p_format
	 *            日期格式 格式1:"yyyy-MM-dd" 格式2:"yyyy-MM-dd hh:mm:ss EE"
	 *            格式3:"yyyy年MM月dd日 hh:mm:ss EE" 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写
	 * @return String 当前时间字符串
	 * @author Every
	 * : 2009-12-9
	 */
	public static String getNowOfDateByFormat(String p_format) {
		if (!TimeZone.getDefault().getID().equals("Asia/Shanghai")) {
			System.out.println("时区不是中国标准时区，是" + TimeZone.getDefault().getDisplayName());
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(p_format);
		String dateStr = sdf.format(d);
		return dateStr;
	}
	/**
	 * 获取指定utc时间的格式化字符串
	 * @param utc 指定的时间
	 * @param format format
	 * @return 格式化串
	 */
	public static String getStrTimeByUTC(long utc,String format) {
		return new SimpleDateFormat(format).format(new Date(utc));
	}
	/**
	 * 获取指定日期格式昨天的字符型日期
	 * 
	 * @param p_format
	 *            日期格式 格式1:"yyyy-MM-dd" 格式2:"yyyy-MM-dd hh:mm:ss EE"
	 *            格式3:"yyyy年MM月dd日 hh:mm:ss EE" 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写
	 * @return String 昨天的字符串日期
	 * @author Every
	 * : 2009-12-9
	 */
	public static String getYesterdayOfDateByFormat(String p_format) {
		if (!TimeZone.getDefault().getID().equals("Asia/Shanghai")) {
			System.out.println("时区不是中国标准时区，是" + TimeZone.getDefault().getDisplayName());
		}
		Date p_startDate = new Date();

		// 年，月，日、时，分，秒
		int l_year = getYearOfDate(p_startDate);
		int l_month = getMonthOfDate(p_startDate) - 1;
		int l_day = getDayOfDate(p_startDate);
		int l_hour = getHourOfDate(p_startDate);
		int l_minute = getMinuteOfDate(p_startDate);
		int l_second = getSecondOfDate(p_startDate);
		Calendar l_calendar = new GregorianCalendar(l_year, l_month, l_day, l_hour, l_minute, l_second);
		l_calendar.add(3, -1);

		SimpleDateFormat sdf = new SimpleDateFormat(p_format);
		String dateStr = sdf.format(l_calendar.getTime());
		return dateStr;
	}

	/**
	 * 获取指定日期格式系统日期的字符型日期
	 * 
	 * @param p_format
	 *            日期格式 格式1:"yyyy-MM-dd" 格式2:"yyyy-MM-dd hh:mm:ss EE"
	 *            格式3:"yyyy年MM月dd日 hh:mm:ss EE" 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写
	 * @return String 系统时间字符串
	 * @author Every
	 * : 2009-12-9
	 */
	public static String getSystemOfDateByFormat(String p_format) {
		long time = System.currentTimeMillis();
		Date d2 = new Date();
		Date d = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat(p_format);
		String dateStr = sdf.format(d);
		return dateStr;
	}

	/**
	 * 获取字符日期一个月的天数
	 * 
	 * @param p_date date
	 * @return 天数
	 * @throws ParseException 转换异常
	 * @author Every
	 * : 2009-12-9
	 */
	@SuppressWarnings("static-access")
	public static long getDayOfMonth(Date p_date) throws ParseException {
		int year = getYearOfDate(p_date);
		int month = getMonthOfDate(p_date) - 1;
		int day = getDayOfDate(p_date);
		int hour = getHourOfDate(p_date);
		int minute = getMinuteOfDate(p_date);
		int second = getSecondOfDate(p_date);
		Calendar l_calendar = new GregorianCalendar(year, month, day, hour, minute, second);
		return l_calendar.getActualMaximum(l_calendar.DAY_OF_MONTH);
	}

	// -----------------获取指定月份的第一天,最后一天
	// ---------------------------------------------------------------------------
	/**
	 * 获取指定月份的第一天
	 * 
	 * @param p_strDate
	 *            指定月份
	 * @param p_format
	 *            日期格式
	 * @return String 时间字符串
	 * @throws ParseException 转换异常
	 * @author Every
	 * : 2009-12-9
	 */
	public static String getDateOfMonthBegin(String p_strDate, String p_format) throws ParseException {
		java.util.Date date = toUtilDateFromStrDateByFormat(p_strDate, p_format);
		return toStrDateFromUtilDateByFormat(date, "yyyy-MM") + "-01";
	}

	/**
	 * 获取指定月份的最后一天
	 * 
	 * @param p_strDate 指定月份
	 * @param p_format 日期格式
	 * @return String 时间字符串
	 * @throws ParseException 转换异常
	 * @author Every
	 * : 2009-12-9
	 */
	public static String getDateOfMonthEnd(String p_strDate, String p_format) throws ParseException {
		java.util.Date date = toUtilDateFromStrDateByFormat(getDateOfMonthBegin(p_strDate, p_format), p_format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		return toStrDateFromUtilDateByFormat(calendar.getTime(), p_format);
	}

	// ----------------------计算两个时刻之间的时差--------------------------------------------------------------------

	/**
	 * 计算两个时刻之间的时间差
	 * 
	 * @param p_startDate 起始时间
	 * @param p_endDate 结束时间
	 * @return 时差 小时
	 * @throws ParseException  Parse Exception
	 */
	public static long getHoursOfTowDiffDate(String p_startDate, String p_endDate) throws ParseException {
		return (long) getMillisOfTowDiffDate(p_startDate, p_endDate) / (1000 * 60 * 60);
	}

	/**
	 * 计算两个时刻之间的时间差
	 * 
	 * @param p_startDate 起始时间
	 * @param p_endDate 结束时间
	 * @return 时差 分钟
	 * @throws ParseException Parse Exception
	 */
	public static long getMinutesOfTowDiffDate(String p_startDate, String p_endDate) throws ParseException {
		return (long) getMillisOfTowDiffDate(p_startDate, p_endDate) / (1000 * 60);
	}

	/**
	 * 计算两个时刻之间的时间差
	 * 
	 * @param p_startDate 起始时间
	 * @param p_endDate 结束时间
	 * @return 时差 秒
	 * @throws ParseException Parse Exception
	 */
	public static long getSecondsOfTowDiffDate(String p_startDate, String p_endDate) throws ParseException {
		return (long) getMillisOfTowDiffDate(p_startDate, p_endDate) / 1000;
	}

	/**
	 * 计算两个时刻之间的时间差
	 * 
	 * @param p_startDate 起始时间
	 * @param p_endDate 结束时间
	 * @return 时差 毫秒
	 * @throws ParseException Parse Exception
	 */
	public static long getMillisOfTowDiffDate(String p_startDate, String p_endDate) throws ParseException {
		Date l_startDate = toUtilDateFromStrDateByFormat(p_startDate, "yyyy-MM-dd HH:mm:ss");
		Date l_endDate = toUtilDateFromStrDateByFormat(p_endDate, "yyyy-MM-dd HH:mm:ss");
		long l_startTime = getMillisOfDate(l_startDate);
		long l_endTime = getMillisOfDate(l_endDate);
		return (long) (l_endTime - l_startTime);
	}
	/**
	 * 计算两个时刻之间的时间差
	 * 
	 * @param p_startDate 起始时间
	 * @param p_endDate 结束时间
	 * @return 时差 毫秒
	 */
	public static long getMillisOfTowDiffDate(Date p_startDate, Date p_endDate) {
		long l_startTime = getMillisOfDate(p_startDate);
		long l_endTime = getMillisOfDate(p_endDate);
		return (long) (l_endTime - l_startTime);
	}
	
	/**
	 * 获取当天的秒数
	 * @return long
	 * @throws ParseException Parse Exception
	 */
	public static long getSeconds() throws ParseException{
		return getMillisOfTowDiffDate(getNowOfDateByFormat("yyyy-MM-dd 00:00:00"),getNowOfDateByFormat("yyyy-MM-dd HH:mm:ss"));
	}
}