package org.oiue.tools.unarranged;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.oiue.table.structure.TableModel;
import org.oiue.tools.date.DateUtil;
import org.oiue.tools.map.MapUtil;

@SuppressWarnings({ "unchecked", "unused", "serial", "rawtypes" })
public class DefBOs extends TableModel implements Serializable {

	private int isUpperCaseKey = 1;
	/**
	 * 存储数据记录
	 */
	private HashMap hashtable = new HashMap();

	public DefBOs() {
		hashtable = new HashMap();
	}

	public DefBOs(HashMap h) {
		hashtable = h;
	}

	/**
	 * 
	 * @param bean
	 * @param list
	 *            移除字段集合ArrayList
	 */
	public DefBOs(Object bean, List list) {
		hashtable = new HashMap();
		Class c = bean.getClass();
		Object fieldValue = null;
		String fieldName = null;
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			fieldName = field.getName();
			if (list != null && list.contains(fieldName))
				continue;
			if (field.getModifiers() == Modifier.PUBLIC) {
				try {
					fieldValue = field.get(this);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			} else {
				fieldValue = invokeGet(bean, fieldName);
				if (fieldValue != null && (fieldValue.getClass().getName().equals("java.util.Date") || fieldValue.getClass().getName().equals("Date"))) {
					fieldValue = DateUtil.formatDateToMysqlString((Date) fieldValue);
				} else if (fieldValue != null && (fieldValue.getClass().getName().equals("java.sql.Date") || fieldValue.getClass().getName().equals("Date"))) {
					fieldValue = null;
				} else if (fieldValue != null && (fieldValue.getClass().getName().equals("java.sql.Timestamp") || fieldValue.getClass().getName().equals("Timestamp"))) {
					fieldValue = DateUtil.formatTimestampToMysqlString((Timestamp) fieldValue);
				} else if (fieldValue != null && (fieldValue.getClass().getName().equals("java.lang.String") || fieldValue.getClass().getName().equals("String"))) {
					fieldValue = ((String) fieldValue).replace("'", "\\'");
				}
			}
			hashtable.put(fieldName, fieldValue == null ? "" : fieldValue);
		}
	}

	public String getJson() {
//		return JSONObject.fromObject(hashtable).toString();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.by.boss.db.table.structure.TableBean#clear()
	 */
	@Override
	public void clear() {
		hashtable = new HashMap();
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.by.boss.db.table.structure.TableBean#set(java.sql.ResultSet)
	 */
	@Override
	public boolean set(ResultSet rs)  {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int sum = rsmd.getColumnCount();
			// Hashtable row = new Hashtable();
			for (int i = 1; i < sum + 1; i++) {
				Object value = rs.getObject(i);
				if (value instanceof BigDecimal) {
					value = ((BigDecimal) value).intValue();
				}
				String key = rsmd.getColumnLabel(i);
				// System.out.println(key+"&"+value);
				hashtable.put(isUpperCaseKey == 1 ? key.toUpperCase() : isUpperCaseKey == 1 ? key : key.toLowerCase(), value == null ? "" : value);
			}
			return true;
			
		} catch (Exception e) {
			return false;
		}
	}

	public Map getMapRemoveID() {
		ArrayList list = new ArrayList();
		list.add("havaNum");
		list.add("childNum");
		list.add("resClassGrantID");
		list.add("havaChild");
		list.add("key");
		list.add("grant");
		list.add("dataWhere");
		list.add("isUpperCaseKey");
		Map fieldMap = (Map) hashtable.clone();
		if (this.getTableIDFieldName() != null && !this.getTableIDFieldName().trim().equals("")) {
			fieldMap.remove(this.getTableIDFieldName());
			return fieldMap;
		} else if (this.getTableIDFieldNames() != null && this.getTableIDFieldName().length() > 0) {
			for (int i = 0; i < this.getTableIDFieldNames().size(); i++) {
				fieldMap.remove(this.getTableIDFieldName());
			}
			return fieldMap;
		} else if (this.getTableName() != null && !this.getTableName().trim().equals("")) {
			fieldMap.remove(this.getTableName().toLowerCase().startsWith("res_data_") ? (this.getTableName() + "_ID") : (this.getTableName() + "ID"));
			return fieldMap;
		} else {
			try {
				throw new Exception("表名和主键名不能为空！使用SQLModel对象时，请指定表名和主键名。");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			String name = (String) iterator.next();
			fieldMap.remove(list);
		}
		return fieldMap;
	}

	@Override
	public Map getMapRemoveStructure() {
		Map hm = getMapRemoveID();
		if (this.getStructureFieldNames() == null || this.getStructureFieldNames().size() == 0) {
			hm.remove("name");
			hm.remove("autoCode");
			hm.remove("position");
			return hm;
		}
		for (int i = 0; i < this.getStructureFieldNames().size(); i++) {
			hm.remove(this.getStructureFieldNames().get(i));
		}
		return hm;
	}

	@Override
	public String getKeyRemoveStruture() {
		if (_fieldValue == null) {
			_fieldValue = getMapRemoveStructure();
		}
		return MapUtil.mapKeyStr(_fieldValue, ",");
	}

	@Override
	public Object put(Map map) {
		hashtable.putAll(map);
		return this;
	}

	@Override
	public Object getValue(String fieldName) {

		return hashtable.get(fieldName) == null ? hashtable.get(fieldName.toUpperCase()) : hashtable.get(fieldName);
	}

	@Override
	public String toString(String specDelimiterRecord) {
		// TODO Auto-generated method stub
		StringBuffer tmpStr = new StringBuffer();
		for (Iterator iterator = hashtable.keySet().iterator(); iterator.hasNext();) {
			Object key = iterator.next();
			tmpStr.append(key.toString() + this.getDelimiterNameValue() + hashtable.get(key)).append(specDelimiterRecord);
		}
		return tmpStr.toString();
	}

	public HashMap getHashtable() {
		return hashtable;
	}

	@Override
	public HashMap getMapData() {
		return this.getHashtable();
	}

	public void setHashtable(HashMap hashtable) {
		this.hashtable = hashtable;
	}

	public int getIsUpperCaseKey() {
		return isUpperCaseKey;
	}

	public void setIsUpperCaseKey(int isUpperCaseKey) {
		this.isUpperCaseKey = isUpperCaseKey;
	}
}
