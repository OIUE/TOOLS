/**
 * 
 */
package org.oiue.table.structure;

import java.io.Serializable;

/**
 * @author Every{王勤}
 *
 */
@SuppressWarnings({ "unused", "serial" })
public class Property implements Serializable {
	private int position; // 位置
	private String name; // 名称
	
	private String description; // 描述
	private String autoCode; // 层次编码
	private String mapCode; // 映射编码
	private String code; // 简码
	private String unionCode; // 联合编码
	
	private String comment; // 注释
	private String dataType; // 数据类型
	private int length; // 长度
	private int precision; // 精度
	private String stereoType; // 格式
	private String defauleValue; // 默认值
	
	private int inputType; // 表单类型
	private String inputValue; // 表单值
	private String inputDesc; // 表单描述
	
	private boolean nullAble = true; // 允许空
	private boolean primaryKey = false; // 主键
	private boolean uniqueKey = false; // 唯一
	private boolean foreignKey = false; // 外键
	private boolean mandatory = false; // 强制不允许修改
	
	private String tabGrpCode; // 表单分组编码
	private String tabGrpDesc; // 表单分组描述
	private boolean activeFlag = true; // 是否激活
	
	private boolean createType = false; // 是否为系统创建
	private boolean createPKID = false; // 系统是否创建表主键
}