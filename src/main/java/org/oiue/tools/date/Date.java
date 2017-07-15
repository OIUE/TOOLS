package org.oiue.tools.date;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
/**
  *
  * 描述:此类用于取得当前日期相对应的月初，月末，季初，季末，年初，年末，返回值均为String字符串
  *      1、得到当前日期         today()
  *      2、得到当前月份月初      thisMonth()
  *      3、得到当前月份月底      thisMonthEnd()
  *      4、得到当前季度季初      thisSeason()
  *      5、得到当前季度季末      thisSeasonEnd()
  *      6、得到当前年份年初      thisYear()
  *      7、得到当前年份年底      thisYearEnd()
  *      8、判断输入年份是否为闰年 leapYear  
  * 注意事项:  日期格式为：xxxx-yy-zz (eg: 2007-12-05)
  * 实例:
  * @author pure
  */
public class Date {
	private int x;                  // 日期属性：年
	private int y;                  // 日期属性：月
	private int z;                  // 日期属性：日
	private Calendar localTime;     // 当前日期
	
	public Date() {
	    localTime = Calendar.getInstance();
	}
	/**
	 * 功能：得到当前日期 格式为：xxxx-yy-zz (eg: 2007-12-05)
	 * @return String
	 * @author pure
	 */
	public String today() {
		String strY = null;
		String strZ = null;
		x = localTime.get(Calendar.YEAR);
		y = localTime.get(Calendar.MONTH) + 1;
		z = localTime.get(Calendar.DATE);
		strY = y >= 10 ? String.valueOf(y) : ("0" + y);
		strZ = z >= 10 ? String.valueOf(z) : ("0" + z);
		return x + "-" + strY + "-" + strZ;
	}
	/**
	  * 功能：得到当前月份月初 格式为：xxxx-yy-zz (eg: 2007-12-01)
	  * @return String
	  * @author pure
	  */
	public String thisMonth() {
		String strY = null;
		x = localTime.get(Calendar.YEAR);
		y = localTime.get(Calendar.MONTH) + 1;
		strY = y >= 10 ? String.valueOf(y) : ("0" + y);
		return x + "-" + strY + "-01";
	}
	/**
	  * 功能：得到当前月份月底 格式为：xxxx-yy-zz (eg: 2007-12-31)
	  * @return String
	  * @author pure
	  **/
	public String thisMonthEnd() {
		String strY = null;
		String strZ = null;
		boolean leap = false;
		x = localTime.get(Calendar.YEAR);
		y = localTime.get(Calendar.MONTH) + 1;
		if (y == 1 || y == 3 || y == 5 || y == 7 || y == 8 || y == 10 || y == 12) {
			strZ = "31";
		}
		if (y == 4 || y == 6 || y == 9 || y == 11) {
			strZ = "30";
		}
		if (y == 2) {
			leap = leapYear(x);
			if (leap) {
				strZ = "29";
			}else {
				strZ = "28";
			}
		}
		strY = y >= 10 ? String.valueOf(y) : ("0" + y);
		return x + "-" + strY + "-" + strZ;
	}

	/**
	  * 功能：得到当前季度季初 格式为：xxxx-yy-zz (eg: 2007-10-01)<br>
      * @return String
      * @author pure
      */
	public String thisSeason() {
		String dateString = "";
		x = localTime.get(Calendar.YEAR);
		y = localTime.get(Calendar.MONTH) + 1;
		if (y >= 1 && y <= 3) {
			dateString = x + "-" + "01" + "-" + "01";
		}
		if (y >= 4 && y <= 6) {
			dateString = x + "-" + "04" + "-" + "01";
		}
		if (y >= 7 && y <= 9) {
			dateString = x + "-" + "07" + "-" + "01";
		}
		if (y >= 10 && y <= 12) {
			dateString = x + "-" + "10" + "-" + "01";
		}
		return dateString;
	}

	/**
	  * 功能：得到当前季度季末 格式为：xxxx-yy-zz (eg: 2007-12-31)<br>
	  * @return String
	  * @author pure
	  */
	public String thisSeasonEnd() {
		String dateString = "";
		x = localTime.get(Calendar.YEAR);
		y = localTime.get(Calendar.MONTH) + 1;
		if (y >= 1 && y <= 3) {
			dateString = x + "-" + "03" + "-" + "31";
		}
		if (y >= 4 && y <= 6) {
			dateString = x + "-" + "06" + "-" + "30";
		}
		if (y >= 7 && y <= 9) {
			dateString = x + "-" + "09" + "-" + "30";
		}
		if (y >= 10 && y <= 12) {
			dateString = x + "-" + "12" + "-" + "31";
		}
		return dateString;
	}

	/**
	  * 功能：得到当前年份年初 格式为：xxxx-yy-zz (eg: 2007-01-01)<br>
	  * @return String
	  * @author pure
	  */
	public String thisYear() {
		x = localTime.get(Calendar.YEAR);
		return x + "-01" + "-01";
	}
	
	/**
	  * 功能：得到当前年份年底 格式为：xxxx-yy-zz (eg: 2007-12-31)<br>
	  * @return String
	  * @author pure
	  */
	public String thisYearEnd() {
		x = localTime.get(Calendar.YEAR);
		return x + "-12" + "-31";
	}
	
	/**
	  * 功能：判断输入年份是否为闰年<br>
	  * @param year 年
	  * @return 是：true  否：false
	  * @author pure
	  */
	public boolean leapYear(int year) {
		boolean leap;
		if (year % 4 == 0) {
			if (year % 100 == 0) {
				if (year % 400 == 0) leap = true;
					else leap = false;
				}
			else leap = true;
		}
		else leap = false;
		return leap;
	}

	/**
	 * 计算两个时间单位间的转换计算单位
	 * @param oldUnit 原单位
	 * @param newUnit 新单位
	 * @return 转换计算单位
	 */
	public static long TimeUnit2Long(TimeUnit oldUnit,TimeUnit newUnit) {
		return (oldUnit==null||newUnit==null)?0:
			newUnit.convert(1, oldUnit);
	}
	/**
	 * 转换时间，将一个时间转换成另一种时间值
	 * @param sourceDuration 转换的时间
	 * @param oldUnit 原来的时间单位
	 * @param newUnit 新的时间单位
	 * @return 转换后的时间
	 */
	public static long TimeUnit2Long(long sourceDuration,TimeUnit oldUnit,TimeUnit newUnit) {
		return (oldUnit==null||newUnit==null)?0:
			newUnit.convert(sourceDuration, oldUnit);
	}
}