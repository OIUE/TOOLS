package org.oiue.tools.exception;
/**
 * 自定义文件无法找到异常
 * @author Every(王勤) E-mail/MSN:mwgjkf@hotmail.com
 *               QQ:30130942
 * @version ReplaceSqlException.java Apr 25, 2010 TODO
 */
@SuppressWarnings("serial")
public class ReplaceSqlException extends Exception {
	public ReplaceSqlException(String message, Throwable cause){
		super(message,cause);
	}
}
