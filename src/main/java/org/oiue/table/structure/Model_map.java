package org.oiue.table.structure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.oiue.tools.StatusResult;
import org.oiue.tools.date.DateUtil;
import org.oiue.tools.exception.OIUEException;
import org.oiue.tools.map.MapUtil;
import org.oiue.tools.string.StringUtil;

@SuppressWarnings( { "unchecked","unused", "serial" ,"rawtypes"})
public abstract class Model_map extends Model_root implements Serializable{

	/**
	 * 获取对象ID的Map映射
	 * @return 映射对象
	 */
	public Map getMapID() {
    	Class c = this.getClass();
		Map fieldMap = new HashMap<String, String>();
		if(this.getTableIDFieldNames()!=null){
			for (String IDFieldName : this.getTableIDFieldNames()) {
				fieldMap.put(IDFieldName,invokeGet(this, IDFieldName));
			}
		}
		
		return fieldMap;
	}
	/**
	 * getMapData
	 * 方法说明:
	 * 
	 * 获取当前对象的Map成员变量与值的映射
	 * 
	 * @author Every(王勤)
	 * @return 对象数据
	 */
	public Map getMapData(){
		Class c = this.getClass();
		Object fieldValue = null;
		String fieldName = null;
		Field[] fields = c.getDeclaredFields();
		Map fieldMap = new HashMap();
		for (Field field : fields) {
			fieldName = field.getName();
			if(fieldName.startsWith("_"))
				continue;
			if (field.getModifiers() == Modifier.PUBLIC) {
				try {
					fieldValue = field.get(this);
				} catch (Throwable e) {e.printStackTrace();}
			} else {
				fieldValue = invokeGet(this, fieldName);
				if (fieldValue != null && (fieldValue.getClass().getName().equals("java.util.Date") || fieldValue.getClass().getName().equals("Date"))) {
					fieldValue = DateUtil.formatDateToMysqlString((Date) fieldValue);
				} else if (fieldValue != null && (fieldValue.getClass().getName().equals("java.sql.Date") || fieldValue.getClass().getName().equals("Date"))) {
					fieldValue = null;
				} else if (fieldValue != null && (fieldValue.getClass().getName().equals("java.sql.Timestamp") || fieldValue.getClass().getName().equals("Timestamp"))) {
					fieldValue = null;
				}
			}
			fieldMap.put(fieldName, fieldValue == null ? "" : fieldValue);
		}
		fieldMap.putAll(this.getDyn_data());
		return fieldMap;
		
	}
	/**
	 * getMapRemoveID
	 * 方法说明:
	 * 
	 * 获取当前对象的Map成员变量与值的映射 映射中移除主键
	 * 
	 * @author Every(王勤)
	 * @return 除ID外的对象数据
	 */
	public Map getMapRemoveID(){
		Map fieldMap = this.getMapData();
		if(this.getTableIDFieldNames()!=null&&this.getTableIDFieldNames().size()>0) {
			for (int i = 0; i < this.getTableIDFieldNames().size(); i++) {
				fieldMap=(Map) MapUtil.removeMatchCase(fieldMap,this.getTableIDFieldNames().get(i));
			}
		}
		return fieldMap;
	}
	/**
	 * getMapRemoveStructure
	 * 方法说明:
	 * 获取当前对象的Map成员变量与值的映射 映射中移除了表格ID及结构字段
	 * 当设定主键字段为空时，会查找主键集合字段 ；
	 * 当主键字段与主键集合字段都为空时，会按照规范的表名及主键名格式自动生成主键名；
	 * 当表格结构字段集合为空时，会自动按照规范移除结构字段。
	 * 此方法不会导致对象设定的主键、主键集合及结构字段集合的改变
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 3:33:46 PM 
	 * @return MapRemoveStructure
	 */
	public Map getMapRemoveStructure() {
		Map hm = getMapRemoveID();
		if(this.getStructureFieldNames()!=null&&this.getStructureFieldNames().size()>0) {
			for (int i = 0; i < this.getStructureFieldNames().size(); i++) {
				hm.remove(this.getStructureFieldNames().get(i));
			}
		}
		return hm;
	}

	/**
	 * 存储对象临时map
	 */
	public Map _fieldValue;
	/**
	 * getKeyRemoveID
	 * 方法说明:
	 * 
	 * 获取当前对象字段名拼接串 字段中移除了表格ID
	 * 当设定主键字段为空时，会查找主键集合字段 
	 * 当主键字段与主键集合字段都为空时，会按照规范的表名及主键名格式自动生成主键名
	 * 此方法不会改变对象设定的主键及主键集合
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 3:32:18 PM 
	 * @return KeyRemoveID
	 */
	public String getKeyRemoveID(){
		if(_fieldValue==null){
			_fieldValue=getMapRemoveID();
		}
		return MapUtil.mapKeyStr(_fieldValue,this.getDelimiterRecord());
	}
	/**
	 * getKeyRemoveID
	 * 方法说明:
	 * 
	 * 获取当前对象字段名拼接串 字段中移除了表格ID
	 * 根据传入的主键
	 * 当设定主键字段为空时，会查找主键集合字段 
	 * 当主键字段与主键集合字段都为空时，会按照规范的表名及主键名格式自动生成主键名
	 * 此方法会改变对象设定的主键及主键集合
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 3:31:50 PM 
	 * @param split 分割符号
	 * @return getKeyRemoveID
	 */
	public String getKeyRemoveID(String split){
		if(_fieldValue==null) {
			_fieldValue=getMapRemoveID();
		}
		return MapUtil.mapKeyStr(_fieldValue,split);
	}
	/**
	 * getKeyRemoveStruture
	 * 方法说明:
	 * 获取当前对象字段名拼接串 映射中移除了表格ID及结构字段
	 * 当设定主键字段为空时，会查找主键集合字段 ；
	 * 当主键字段与主键集合字段都为空时，会按照规范的表名及主键名格式自动生成主键名；
	 * 当表格结构字段集合为空时，会自动按照规范移除结构字段。
	 * 此方法不会导致对象设定的主键、主键集合及结构字段集合的改变
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 3:42:16 PM 
	 * @return getKeyRemoveStruture
	 */
	public String getKeyRemoveStruture() {
		if(_fieldValue==null) {
			_fieldValue=getMapRemoveStructure();
		}
		return MapUtil.mapKeyStr(_fieldValue,this.getDelimiterRecord());
	}
	/**
	 * getKeyRemoveStruture
	 * 方法说明:
	 * 获取当前对象的Map成员变量与值的映射 映射中移除了表格ID及结构字段
	 * 当设定主键字段为空时，会查找主键集合字段 ；
	 * 当主键字段与主键集合字段都为空时，会按照规范的表名及主键名格式自动生成主键名；
	 * 当表格结构字段集合为空时，会自动按照规范移除结构字段。
	 * 此方法会导致对象设定的主键、主键集合及结构字段集合的改变
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 3:42:21 PM 
	 * @param split 分隔符
	 * @return getKeyRemoveStruture
	 */
	public String getKeyRemoveStruture(String split) {
		if(_fieldValue==null) {
			_fieldValue=getMapRemoveStructure();
		}
		return MapUtil.mapKeyStr(_fieldValue,split);
	}
	/**
	 * getValueStr
	 * 方法说明:
	 * 将字段对应值拼接成字符串
	 * 此方法依赖于 getKeyRemoveID、getKeyRemoveStruture
	 * 必须先调用 上述方法以创建键值map
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 4:10:29 PM 
	 * @return getValueStr
	 */
	public String getValueStr() {
		if (_fieldValue==null) {
			throw new OIUEException(StatusResult._blocking_errors,"");
		}
		return MapUtil.mapValueStrForMysql(_fieldValue, this.getDelimiterRecord());
	}
	/**
	 * getValueStr
	 * 方法说明:
	 * 将字段对应值按指定的分隔符拼接成字符串
	 * 此方法依赖于 getKeyRemoveID、getKeyRemoveStruture
	 * 必须先调用 上述方法以创建键值map
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 4:10:35 PM 
	 * @param split 分隔符
	 * @return getValueStr
	 */
	public String getValueStr(String split) {
		if (_fieldValue==null) {
			throw new OIUEException(StatusResult._blocking_errors,"");
		}
		return MapUtil.mapValueStrForMysql(_fieldValue, split);
	}
	/**
	 * getKeyValue
	 * 方法说明:
	 * 获取 sql语句新增修改键值串 包含ID 格式为 key1=value1,key2=value2
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 5:19:29 PM 
	 * @return getKeyValue
	 */
	public String getKeyValue(){
		return MapUtil.mapKeyValueForMysql(this.getMapData(), this.getDelimiterRecord());
	}
	/**
	 * getKeyValueRemoveID
	 * 方法说明:
	 * 获取 sql语句新增修改键值串 不含ID
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 6:31:07 PM 
	 * @return getKeyValueRemoveID
	 */
	public String getKeyValueRemoveID() {
		return MapUtil.mapKeyValueForMysql(getMapRemoveID(),this.getDelimiterRecord());
	}
	/**
	 * getKeyValueRemoveStructure
	 * 方法说明:
	 * 
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 6:31:12 PM 
	 * @return getKeyValueRemoveStructure
	 */
	public String getKeyValueRemoveStructure() {
		return MapUtil.mapKeyValueForMysql(getMapRemoveStructure(), this.getDelimiterRecord());
	}
	/**
	 * getKeyValueRemoveID
	 * 方法说明:
	 * 
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 6:31:16 PM 
	 * @param split 分隔符
	 * @return getKeyValueRemoveID
	 */
	public String getKeyValueRemoveID(String split) {
		return MapUtil.mapKeyValueForMysql(getMapRemoveID(), split);
	}
	/**
	 * getKeyValueRemoveStructure
	 * 方法说明:
	 * 
	 * 
	 * @author Every(王勤)
	 *  
	 *  Jan 4, 2011 6:31:21 PM 
	 * @param split 分隔符
	 * @return getKeyValueRemoveStructure
	 */
	public String getKeyValueRemoveStructure(String split) {
		return MapUtil.mapKeyValueForMysql(getMapRemoveStructure(), split);
	}
	
	protected static Object invokeGet(Object entity, String fieldName) {
		try{
			Method method;
			try {
				method = entity.getClass().getMethod("get" + StringUtil.firstToUpper(fieldName));
			}  catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				if (fieldName.toLowerCase().startsWith("is")) {
					method = entity.getClass().getMethod("" + fieldName);
				}else
					method = entity.getClass().getMethod("is" + StringUtil.firstToUpper(fieldName));
			}
			return method.invoke(entity);
		}catch(Throwable e){
			e.printStackTrace();
		}
		return null;
	}
}
