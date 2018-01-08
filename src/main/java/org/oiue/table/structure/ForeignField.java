package org.oiue.table.structure;

import java.io.Serializable;

import org.oiue.table.structure.TableModel;

@SuppressWarnings({ "serial"})
public class ForeignField extends FieldExt implements Serializable{
	public ForeignField(TableModel tm) {
		super(tm);
	}
	public ForeignField(TableModel tm,String pkTableName,String columnName){
		super(tm);
//		this.setName(field.getName());
//		this.setComment(field.getComment());
//		this.setDisplaySize(field.getDisplaySize());
//		this.setPrecision(field.getPrecision());
//		this.setStereoType(field.getStereoType());
//		this.setDefauleValue(field.getDefauleValue());
//		this.setNullAble(field.isNullAble());
		this.putProperties(tm);
		this.setPkTableName(this.getTableName());
		this.setColumn(columnName);
	}
	private String column;            //引用字段名
	private String pkTableName;         //引用表名
	private String key;               //
	
	
	public String getColumn() {
		return column;
	}
	public void setColumn(String column) {
		this.column = column;
	}
	public String getPkTableName() {
		return pkTableName;
	}
	public void setPkTableName(String pkTableName) {
		this.pkTableName = pkTableName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
