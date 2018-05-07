package org.oiue.table.structure;

import java.io.Serializable;

import org.oiue.table.structure.TableModel;
import org.oiue.tools.model.COLUMN;

/**
 * @author Every{王勤}
 *
 */
@SuppressWarnings("serial")
public class FieldExt extends Field implements Serializable {
	
	private int resFieldID = 0;
	
	public int getResFieldID() {
		return resFieldID;
	}
	
	public void setResFieldID(int resFieldID) {
		this.resFieldID = resFieldID;
	}
	
	public FieldExt(TableModel tm) {
		this.setName(tm.getValue("name") + "");
		this.setComment(tm.getValue("comment") + "");
		this.setType(COLUMN.resType2DBType((Integer) tm.getValue("type"), (Integer) tm.getValue("displaySize")));
	}
	
	public FieldExt() {}
	// public static int getDataType(int code){
	// return code==1?Types.INTEGER:
	// code==3?Types.DATE:
	// code==8?Types.TIME:
	// code==9?Types.TIMESTAMP:
	// code==10?Types.DOUBLE:
	// Types.VARCHAR;
	// }
	
	public TableModel convertTM(TableModel tm) {
		tm.put("filedName", this.getName());
		tm.put("description", this.getComment());
		tm.put("activeFlag", 1 + "");
		tm.put("isNull", this.isNullAble() ? 0 : 1);
		tm.put("isUnique", this.isUnique() ? 0 : 1);
		tm.put("length", this.getDisplaySize());
		tm.put("type", COLUMN.DBType2ResType(this.getType(), this.getDisplaySize()));
		tm.put("defaultValue", this.getDefauleValue());
		return tm;
	}
}