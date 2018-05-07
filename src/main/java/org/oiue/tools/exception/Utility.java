package org.oiue.tools.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.oiue.tools.date.DateUtil;

/**
 * Utility
 * 
 * 类说明:
 * 
 * 
 * 
 * @author Every(王勤) Jan 17, 2011 12:03:20 PM
 */
public class Utility {
	public static String getExceptionString(Throwable ex) {
		StringBuffer exceptionTrace = new StringBuffer(5120);
		StringWriter sWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(sWriter);
		ex.printStackTrace(printWriter);
		exceptionTrace.delete(0, exceptionTrace.length());
		exceptionTrace.append((new StringBuilder()).append(DateUtil.getNowOfDateByFormat("yyyy/MM/dd HH:mm:ss.SSS")).append("\r\n").toString());
		exceptionTrace.append("------------------------------------------------------------------\r\n");
		exceptionTrace.append(sWriter.toString());
		printWriter.close();
		try {
			sWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return exceptionTrace.toString();
	}
}
