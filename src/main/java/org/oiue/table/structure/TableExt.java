package org.oiue.table.structure;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.oiue.table.structure.TableModel;
import org.oiue.tools.list.ListUtil;

/**
 * @author Every{王勤}
 *
 */
@SuppressWarnings({"serial","unchecked","rawtypes"})
public class TableExt extends Table implements Serializable {
//	Integer resclassID=0;  //分类id
//	Integer resTableID=0;  //表id
	String parentAutoCode=""; //当前表的父节点autocode

	public TableModel convertTM(TableModel tm){
		tm.put("filedName",this.getName());
//		tm.put("resClassID",this.getResclassID());
		tm.put("autoCode",this.getParentAutoCode());
		tm.put("name",this.getComment());
		tm.put("description",this.getComment());
		return tm;
	}
	public String getParentAutoCode() {
		return parentAutoCode;
	}

	public void setParentAutoCode(String parentAutoCode) {
		this.parentAutoCode = parentAutoCode;
	}

//	public Integer getResclassID() {
//		return resclassID;
//	}
//
//	public void setResclassID(Integer resclassID) {
//		this.resclassID = resclassID;
//	}
//
//	public Integer getResTableID() {
//		return resTableID;
//	}
//
//	public void setResTableID(Integer resTableID) {
//		this.resTableID = resTableID;
//	}

	public TableExt() {
		this("", "");
	}

	public TableExt(String name) {
		this(name, "");
	}

	public TableExt(String name, String id) {
		this(name, id, "");
	}

	public TableExt(String name, String id, String idGenerator) {
		super(name, id, idGenerator);
	}

	/**
	 * 调试
	 * @return String
	 */
	public String toString() {
		return "TABLE\tname=" + this.getTableName() 
			+ ";field number=" + this.getFields().size() 
			+ ";pk field number=" + this.getPrimaryField().size()
			+ ";id=" + this.getId() 
			+ ";generator=" + this.getIdGenerator() ;
	}
	
	/**
	 * 为表添加字段
	 * @param key String
	 * @param field field
	 */
	public void addField(String key, Field field) {
		super.getFields().put(key, field);
	}
	/**
	 * 根据字段名称移除字段
	 * @param fieldName String
	 */
	public void removeField(String fieldName) {
		super.getFields().remove(fieldName);
	}
	/**
	 * 根据字段名查找字段
	 * @param fieldName 字段名
	 * @return Filed 字段 
	 */
	public Field findField(String fieldName) {
		return (Field) super.getFields().get(fieldName);
	}
	/**
	 * 根据字段的名字查找字段
	 * 此方式忽略字符串大小写
	 * @param fieldName 忽略字段
	 * @return 字段
	 */
	public Field findFieldIgnoreCase(String fieldName) {
		if (fieldName==null) {
			return null;
		}
		Iterator it=super.getFields().values().iterator();
		while(it.hasNext()){
			Field field=(Field)it.next();
			if(fieldName.equalsIgnoreCase(field.getName()))return field;
		}
		return null;
	}
	/**
	 * 获取指定的列字段
	 * @param popertyName 指定字段
	 * @return 字段
	 */
	public Field getField(String popertyName) {
		Field filed=findField(popertyName);
		if(filed==null)filed=findForeignFiled(popertyName);
		return filed;
	}
	/**
	 * 获取所有的字段 这些字段中不仅有表的字段
	 * 还包含关系字段
	 * @return 所有字段
	 */
	public Map<String, Field> getAllFields(){
		Map<String, Field> ret=new HashMap<String, Field>(super.getFields());
		ret.putAll(super.getPrimaryField());
		return ret;
	}
//	/**
//	 * 添加外键
//	 * @param name
//	 */
//	public void addForeignFiled(String name){
//		try {
//			super.getForeignField().put(name, new ForeignField(this.getField(name),"",""));
//			super.getFields().remove(name);
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	/**
	 * 添加外键
	 * @param name 名字
	 * @param Field 字段
	 */
	public void addForeignFiled(String name, ForeignField Field) {
		super.getForeignField().put(name, Field);
	}
	
	/**
	 * 查找外键
	 * @param popertyName 名字
	 * @return 外键
	 */
	public ForeignField findForeignFiled(String popertyName) {
		return (ForeignField) super.getForeignField().get(popertyName);
	}
	
	/**
	 * 添加主键
	 * @param name 名字
	 */
	public void addPrimaryField(String name){
		if(super.getPrimaryField().get(name)==null&&this.getFields().get(name)!=null){
			super.getPrimaryField().put(name, this.getFields().get(name));
			super.getFields().remove(name);
		}
	}
	/**
	 * 添加主键
	 * @param name 名字
	 * @param field 字段
	 */
	public void addPrimaryField(String name,Field field) {
		super.getPrimaryField().put(name, field);
	}
	
	/**
	 * 获取主键字符串 多主键用“,”分割
	 * @return String 字段
	 */
	public String getPrimaryStr() {
		if (getPrimaryField().size()>1) {
			return ListUtil.ArrayJoin(getPrimaryField().keySet().toArray(), ",");
		}
		return getId();
	}

	public Field getKeyField() {
		return (Field) super.getFields().get(super.getId());
	}

	public void setLazy(String lazy) {
		if (lazy!=null&&lazy.equalsIgnoreCase("true")) {
			super.setLazy(true);
		} else
			super.setLazy(false);
	}
	/**
	 * 合并表格
	 * @param table 表对象
	 */
	public void concat(Table table){
		super.getFields().putAll(table.getFields());
		super.getForeignField().putAll(table.getForeignField());
		if("".equals(super.getName()))super.setName(table.getName());
		if("".equals(super.getId()))super.setId(table.getId());
		if("".equals(super.getIdGenerator()))super.setIdGenerator(table.getIdGenerator());
	}

	/**
	 * 根据操作码取得对应操作字段
	 * @param key key
	 * @return 操作结果
	 */
	public Map getFields(int key) {
		Map fieldMap = new java.util.LinkedHashMap();
		for (Iterator iterator = super.getFields().keySet().iterator(); iterator.hasNext();) {
			String fieldName = (String) iterator.next();
			Field field=(Field)super.getFields().get(fieldName);
			if (field.getCmdKey()==key){
				fieldMap.put(fieldName, field);
			}else if (key<0&&field.getCmdKey()>0) {
				fieldMap.put(fieldName, field);
			}
		}
		return fieldMap;
	}
//	/**
//	 * 初始化字段数据类型
//	 * @param dbkey
//	 * @param typeMap
//	 */
//	public void initTable(int dbkey,HashMap typeMap) {
//		switch (dbkey) {
//			case 1://根据自定义类型初始化字段sql类型
//				for (Iterator iterator = fields.keySet().iterator(); iterator.hasNext();) {
//					String key = (String) iterator.next();
//					Field field=(Field)fields.get(key);
//					field.setDbType((String)typeMap.get(field.getFieldType()));
//				}
//				break;
//				
//			case 2://根据自定义类型初始化java对象类型
//				for (Iterator iterator = fields.keySet().iterator(); iterator.hasNext();) {
//					String key = (String) iterator.next();
//					Field Field=(Field)fields.get(key);
//					Field.setType((String)typeMap.get(Field.getFieldType()));
//				}
//			default:
//				break;
//		}
//	}

}