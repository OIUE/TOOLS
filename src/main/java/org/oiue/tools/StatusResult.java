package org.oiue.tools;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class StatusResult implements Serializable{
	public static final int _repeat_user_name_= -150;  //用户名已存在
	public static final int _login_error = -152;  //用户名或密码错误
	public static final int _repeat_data= -180;  //数据已存在
	public static final int _more_data= -181;  //数据存在多条
	public static final int _no_data= -182;  //数据不存在
	public static final int _mismatch_type= -185;  //数据类型不匹配
	public static final int _to_long= -186;  //数据超出长度
	public static final int _format_error= -187;  //数据格式错误
	public static final int _reference_error= -189;  //数据被引用不可修改
	public static final int _data_error= -190;  //数据错误
	public static final int _no_more_conn= -251;  //数据库连接池已满，无法获取连接
	public static final int _conn_error= -252;  //数据库连接异常
	
	public static final int _service_can_not_found = -300;//服务无法找到
	public static final int _url_can_not_found = -404;//地址无法找到

	public static final int _blocking_errors= -500;  //阻断性异常

	public static final int _ncriticalAbnormal = -120;//关键异常

	public static final int _permissionDenied = -110;// 权限不足
	public static final int _pleaseLogin = -101; // 尚未登录

	public static final int _NoncriticalAbnormal = -1;// 非关键异常

	public static final int _SUCCESS = 1;// 成功
	public static final int _SUCCESS_OVER = 10;// 成功并结束
	public static final int _SUCCESS_CONTINUE = 100;// 成功并结束当前过程，不影响接下来的过程

	private int result;
	private String description;
	private Map<?, ?> data;

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
		return "result:"+result+",description:"+description +(data!=null?",data:"+data:"");
	}

	public Map getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}
}
