package org.oiue.tools.exception;

import java.util.Map;

import org.oiue.tools.StatusResult;

/**
 * 自定义文件无法找到异常
 * @author Every(王勤) E-mail/MSN:mwgjkf@hotmail.com
 *               QQ:30130942
 *  ReplaceSqlException.java Apr 25, 2010 TODO
 */
@SuppressWarnings("serial")
public class OIUEException extends RuntimeException {
	private StatusResult sr=null;
	Object rtnObject=null;
	public StatusResult getStatus() {
		return sr;
	}
	public OIUEException(StatusResult status,Object rtnObject,Throwable cause){
		super(cause);
		this.sr=status;
		this.rtnObject=rtnObject;
	}
	public OIUEException(int status,Object rtnObject,Throwable cause){
		super(cause);
		sr = new StatusResult();
		sr.setResult(status);
		this.rtnObject=rtnObject;
	}
	public OIUEException(int status,Map rtnObject,Throwable cause){
		super(cause);
		sr = new StatusResult();
		sr.setResult(status);
		sr.setData(rtnObject);
	}
	public OIUEException(int status,String desc,Throwable cause){
		super(cause);
		sr = new StatusResult();
		sr.setResult(status);
		sr.setDescription(desc);
	}
	public OIUEException(int status,String desc){
		super();
		sr = new StatusResult();
		sr.setResult(status);
		sr.setDescription(desc);
	}
	
	public Object getRtnObject() {
		return rtnObject!=null?rtnObject:sr==null?sr.getData():null;
	}
	@Override
	public String toString() {
		return super.toString()+"\n"+sr.getDescription();
	}
}
