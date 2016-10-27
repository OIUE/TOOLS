/**
 * 
 */
package org.oiue.table.structure;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Every(王勤) 层次表根对象
 */
@SuppressWarnings({ "unused", "rawtypes", "unchecked", "serial" })
public class StructureTable extends TableModel implements Serializable {

	private int _havaNum = 0;
	private boolean _havaChild = true;
	private int _childNum = 0;
	private boolean _isUpperCaseKey = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.oiue.table.structure.Model_root#clear()
	 */
	@Override
	public void clear() {
		if (this.getDyn_data() != null)
			this.getDyn_data().clear();
		this.setDyn_data(new HashMap());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.oiue.table.structure.Model_root#set(java.sql.ResultSet)
	 */
	@Override
	public boolean set(ResultSet rs) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		int sum = rsmd.getColumnCount();
		for (int i = 1; i < sum + 1; i++) {
			Object value = rs.getObject(i);
			if (value instanceof BigDecimal) {
				value = ((BigDecimal) value).intValue();
			}
			String key = rsmd.getColumnLabel(i);
			if (this.fieldContains(key)) {
				this.put(key, value);
			} else {
				this.getDyn_data().put(_isUpperCaseKey ? key.toUpperCase() : key, value == null ? "" : value);
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.oiue.table.structure.Model_root#toString(java.lang.String)
	 */
	@Override
	public String toString(String specDelimiterRecord) {
		return this.getMapData()+"";
	}

	public int getHavaNum() {
		return _havaNum;
	}

	public void setHavaNum(int havaNum) {
		this._havaNum = havaNum;
	}

	public boolean isHavaChild() {
		return _havaChild;
	}

	public void setHavaChild(boolean havaChild) {
		this._havaChild = havaChild;
	}

	public int getChildNum() {
		return _childNum;
	}

	public void setChildNum(int childNum) {
		this._childNum = childNum;
	}

}
