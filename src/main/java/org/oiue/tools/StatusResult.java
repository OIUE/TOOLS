package org.oiue.tools;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StatusResult implements Serializable{
    public static final int _ncriticalAbnormal = -120;//关键异常

    public static final int _permissionDenied = -110;// 权限不足
    public static final int _pleaseLogin = -101; // 尚未登录

    public static final int _NoncriticalAbnormal = -1;// 非关键异常

    public static final int _SUCCESS = 1;// 成功
    public static final int _SUCCESS_OVER = 10;// 成功并结束

	private int result;
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return "result:"+result+",description:"+description;
	}
}
