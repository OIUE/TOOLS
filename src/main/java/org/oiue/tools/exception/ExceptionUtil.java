/**
 * 
 */
package org.oiue.tools.exception;

/**
 * @author every
 *
 */
public class ExceptionUtil {
	public static String getCausedBySrcMsg(Throwable e) {
		return (e.getCause() == null) ? e.getMessage() : getCausedBySrcMsg(e.getCause());
	}
}
