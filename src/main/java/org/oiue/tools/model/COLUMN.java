package org.oiue.tools.model;

import java.sql.Types;


/**
 * 字段操作相关工具
 * @author Every{王勤}
 *
 */
public class COLUMN {
	static String default_connType="Oracle";
//	public class Types{
//	    public static final int BIT = -7;
//	    public static final int TINYINT = -6;
//	    public static final int SMALLINT = 5;
//	    public static final int INTEGER = 4;
//	    public static final int BIGINT = -5;
//	    public static final int FLOAT = 6;
//	    public static final int REAL = 7;
//	    public static final int DOUBLE = 8;
//	    public static final int NUMERIC = 2;
//	    public static final int DECIMAL = 3;
//	    public static final int CHAR = 1;
//	    public static final int VARCHAR = 12;
//	    public static final int LONGVARCHAR = -1;
//	    public static final int DATE = 91;
//	    public static final int TIME = 92;
//	    public static final int TIMESTAMP = 93;
//	    public static final int BINARY = -2;
//	    public static final int VARBINARY = -3;
//	    public static final int LONGVARBINARY = -4;
//	    public static final int NULL = 0;
//	    public static final int OTHER = 1111;
//	    public static final int JAVA_OBJECT = 2000;
//	    public static final int DISTINCT = 2001;
//	    public static final int STRUCT = 2002;
//	    public static final int ARRAY = 2003;
//	    public static final int BLOB = 2004;
//	    public static final int CLOB = 2005;
//	    public static final int REF = 2006;
//	    public static final int DATALINK = 70;
//	    public static final int BOOLEAN = 16;
//	    public static final int ROWID = -8;
//	    public static final int NCHAR = -15;
//	    public static final int NVARCHAR = -9;
//	    public static final int LONGNVARCHAR = -16;
//	    public static final int NCLOB = 2011;
//	    public static final int SQLXML = 2009;
//	}
	
//	1	number	         数字
//	2	varchar	         字符
//	3	date	         日期
//	4	rulePattern	     正则表达式
//	5	ipAddress	    IP地址
//	6	netmask	         子网掩码
//	7	emailAddress	邮件地址
//	8	time	         时间
//	9	dateTime	    日期时间组合
//	10	double	         浮点类型
//	11	percent      	%
//	12	blob	         文件类型
//	13	stringSerial	字符串序列


	/**
	 * 根据jdbc参数 获取资源数据类型
	 * @param columnType 类型
	 * @param columnDisplaySize 大小
	 * @param precision 精度
	 * @return 值
	 */
	public static int getType(int columnType,int columnDisplaySize,int precision){
		return columnType==12?columnDisplaySize==1?Types.BIT:columnDisplaySize<21277?Types.VARCHAR:Types.LONGNVARCHAR:
				columnType==4?Types.INTEGER:
				columnType==1?Types.CHAR:
				columnType==-1?Types.CLOB:
				columnType==-4?Types.LONGVARBINARY:
				columnType==7?Types.REAL:
				columnType==8?Types.DOUBLE:
				columnType==91?Types.DATE:
				columnType==92?Types.TIME:
				columnType==93?Types.TIMESTAMP:
						Types.VARCHAR;
	}
	/**
	 * 将资源中的数据类型翻译成SQL的数据类型
	 * @param columnType res中的数据类型值
	 * @param columnDisplaySize 数据长度
	 * @return 值
	 */
	public static int resType2DBType(int columnType,int columnDisplaySize){
		return resType2DBType(columnType, columnDisplaySize, default_connType);
	}
	/**
	 * 将资源中的数据类型翻译成指定SQL的数据类型
	 * @param columnType res中的数据类型值
	 * @param columnDisplaySize 数据长度
	 * @param dbType 数据库类型
	 * @return 值
	 */
	public static int resType2DBType(int columnType,int columnDisplaySize,String dbType){
		return columnType==1?Types.INTEGER:
				columnType==2&&columnDisplaySize<21277?Types.VARCHAR:
					columnType==2&&columnDisplaySize>21277?Types.CLOB:
				columnType==3?Types.DATE:
				columnType==4?Types.VARCHAR:
				columnType==5?Types.VARCHAR:
				columnType==6?Types.VARCHAR:
				columnType==7?Types.VARCHAR:
				columnType==8?Types.TIME:
				columnType==9?Types.TIMESTAMP:
				columnType==10?Types.DOUBLE:
				columnType==11?Types.DOUBLE:
				columnType==12?Types.BLOB:
				columnType==13?Types.VARCHAR:
					Types.VARCHAR;
	}
	/**
	 * 将资源中的数据类型翻译成SQL的数据类型
	 * @param columnType res中的数据类型值
	 * @param columnDisplaySize 数据长度
	 * @return 值
	 */
	public static int DBType2ResType(int columnType,int columnDisplaySize){
		return DBType2ResType(columnType, columnDisplaySize, default_connType);
	}
	/**
	 * 将资源中的数据类型翻译成指定SQL的数据类型
	 * @param columnType res中的数据类型值
	 * @param columnDisplaySize 数据长度
	 * @param dbType 数据库类型
	 * @return 值
	 */
	public static int DBType2ResType(int columnType,int columnDisplaySize,String dbType){
		return columnType==Types.INTEGER?1:
			columnType==Types.DATE?3:
			columnType==Types.VARCHAR?2:
			columnType==Types.TIME?8:
			columnType==Types.TIMESTAMP?9:
			columnType==Types.DOUBLE?10:
			columnType==Types.BLOB?12:2;
	}
	/**
	 * 返回对应的mysql类型
	 * @param sqlType mysql类型
	 * @return 值
	 */
	public static String toMysql(int sqlType){
		return sqlType==Types.DATE?"date":
			sqlType==Types.TIME?"time":
			sqlType==Types.TIMESTAMP?"datetime":
			sqlType==Types.BLOB?"blob":
			sqlType==Types.VARCHAR?"varchar":
			sqlType==Types.CHAR?"char":
			sqlType==Types.CLOB?"text":
			sqlType==Types.LONGVARBINARY?"longblob":
			sqlType==Types.REAL?"float":
			sqlType==Types.DOUBLE?"double":
			sqlType==Types.TINYINT?"tinyint":
			sqlType==Types.SMALLINT?"smallint":
			sqlType==Types.INTEGER?"int":
			sqlType==Types.BIGINT?"bigint":
			sqlType==Types.VARBINARY?"varbinary":
			sqlType==Types.BIT?"bit":
			sqlType==Types.DECIMAL?"decimal":"varchar";
	}
	/**
	 * 返回对应的java类型
	 * @param sqlType 类型
	 * @return 值
	 */
	public static String toJava(int sqlType){
		return sqlType==Types.DATE?"Date":
			sqlType==Types.TIME?"Time":
			sqlType==Types.TIMESTAMP?"Date":
			sqlType==Types.BLOB?"byte[]":
			sqlType==Types.LONGVARBINARY?"byte[]":
			sqlType==Types.VARCHAR?"String":
			sqlType==Types.CHAR?"Char":
			sqlType==Types.LONGNVARCHAR?"String":
			sqlType==Types.DOUBLE?"Double":
			sqlType==Types.TINYINT?"Character":
			sqlType==Types.SMALLINT?"Integer":
			sqlType==Types.INTEGER?"Integer":
			sqlType==Types.BIGINT?"Long":
			sqlType==Types.VARBINARY?"byte[]":
			sqlType==Types.BIT?"Boolean":
			sqlType==Types.DECIMAL?"decimal":"String";
	}
	/**
	 * 返回对应java类型的sqlType值
	 * @param obj 类型
	 * @return 值
	 */
	public static int toSqlType(Object obj){
		return (obj instanceof java.sql.Date)?Types.DATE:
			(obj instanceof java.sql.Time)?Types.TIME:
			(obj instanceof java.sql.Timestamp)?Types.TIMESTAMP:
			(obj instanceof java.lang.Byte)?Types.BLOB:
			(obj instanceof java.lang.String)?Types.VARCHAR:
			(obj instanceof java.lang.Character)?Types.CHAR:
			(obj instanceof java.lang.String)?Types.LONGNVARCHAR:
			(obj instanceof java.lang.Double)?Types.DOUBLE:
			(obj instanceof java.lang.Character)?Types.TINYINT:
			(obj instanceof java.lang.Integer)?Types.SMALLINT:
			(obj instanceof java.lang.Integer)?Types.INTEGER:
			(obj instanceof java.lang.Long)?Types.BIGINT:
			(obj instanceof java.lang.Byte)?Types.VARBINARY:
			(obj instanceof java.lang.Boolean)?Types.BIT:
			(obj instanceof java.math.BigDecimal)?Types.DECIMAL:Types.VARCHAR;
	}
	/**
	 * 是否需要设定长度
	 * @param sqlType 类型
	 * @return 值
	 */
	public static int setSize(int sqlType){
		return sqlType==Types.DATE||
			sqlType==Types.TIME||
			sqlType==Types.TIMESTAMP||
			sqlType==Types.BLOB||
			sqlType==Types.LONGNVARCHAR||
			sqlType==Types.VARBINARY?0:
				
			sqlType==Types.VARCHAR||
			sqlType==Types.CHAR||
			sqlType==Types.TINYINT||
			sqlType==Types.SMALLINT||
			sqlType==Types.INTEGER||
			sqlType==Types.BIGINT||
			sqlType==Types.BIT?1:
				
			sqlType==Types.DOUBLE||
			sqlType==Types.FLOAT||
			sqlType==Types.DECIMAL?2:0;
	}
}
