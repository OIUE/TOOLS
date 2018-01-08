package org.oiue.table.structure;

import java.io.Serializable;
import java.sql.ResultSet;

import org.oiue.table.structure.TableModel;
import org.oiue.tools.model.COLUMN;

/**
 * @author Every{王勤}
 *
 */
@SuppressWarnings("serial")
public abstract class Field extends TableModel implements Serializable{
	private int position ;                                        //位置
    private String name ;                                         //名称
    private String comment ;                                      //注释
    private int	type ;                                            //数据类型
    private int displaySize ;                                     //长度
    private int precision ;                                       //精度
    private int scale ;                                           //小数点后的位数
    
    private String stereoType ;                                   //格式
    private String defauleValue ;                                 //默认值
    
    private boolean nullAble = true ;                             //允许空
    private boolean unique = false;                               //是否唯一
    private boolean addComment=false;                             //添加描述信息
    
    private int cmdKey=10;   //操作编码
    
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean set(ResultSet rs)  {
		// TODO Auto-generated method stub
		return false;
	}
	public String toString() {
		return this.toString("");
	}
	@Override
	public String toString(String specDelimiterRecord) {
		// TODO Auto-generated method stub
		
		return name+" "+COLUMN.toMysql(this.getType())
		+(COLUMN.setSize(this.getType())==2?"("+this.getDisplaySize()+","+this.getScale()+")":
			COLUMN.setSize(this.getType())==1?"("+this.getDisplaySize()+")":"")
		+(nullAble?"":" NOT NULL")
		+(defauleValue==null||defauleValue.trim().equals("")||defauleValue.trim().equalsIgnoreCase("null")?"":" DEFAULT '"+defauleValue+"'")
		+((comment!=null&&!comment.equals(""))?" COMMENT '"+comment+"'":"");
//		return name+" "+COLUMN.toMysql(this.getType())
//		+(COLUMN.setSize(this.getType())==2?"("+this.getDisplaySize()+","+this.getScale()+")":
//			COLUMN.setSize(this.getType())==1?"("+this.getDisplaySize()+")":"")
//			+(nullAble?"":" NOT NULL")
//			+(defauleValue==null||defauleValue.trim().equals("")||defauleValue.trim().equalsIgnoreCase("null")?"":" DEFAULT '"+defauleValue+"'")
//			+((comment!=null&&!comment.equals(""))?" COMMENT '"+comment+"'":"");
	}
	
	public String toType() {
		// TODO Auto-generated method stub
		
		return (COLUMN.setSize(this.getType())==2?"("+this.getDisplaySize()+","+this.getScale()+")":
			COLUMN.setSize(this.getType())==1?"("+this.getDisplaySize()+")":"")
			+(nullAble?"":" NOT NULL")
			+(defauleValue==null&&defauleValue.equals("")?"":" DEFAULT '"+defauleValue+"'")
			+((comment!=null&&!comment.equals(""))?" COMMENT '"+comment+"'":"");
	}
	
	/**
	 * 比较字段
	 * @param field 字段
	 * @return 名字 类型 长度 都相同则返回true 否则 false
	 */
	public boolean equals(Field field) {
		if(field==null)return false;
		return this.getName().equals(field.getName())&&
				this.getType()==field.getType()?
				this.getDisplaySize()==field.getDisplaySize():false;
	}
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public String getStereoType() {
		return stereoType;
	}
	public void setStereoType(String stereoType) {
		this.stereoType = stereoType;
	}
	public String getDefauleValue() {
		return defauleValue;
	}
	public void setDefauleValue(String defauleValue) {
		this.defauleValue = defauleValue;
	}
	public boolean isNullAble() {
		return nullAble;
	}
	public void setNullAble(boolean nullAble) {
		this.nullAble = nullAble;
	}
	public boolean isUnique() {
		return unique;
	}
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	public int getCmdKey() {
		return cmdKey;
	}
	public void setCmdKey(int cmdKey) {
		this.cmdKey = cmdKey;
	}
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public int getDisplaySize() {
		return displaySize;
	}
	public void setDisplaySize(int displaySize) {
		this.displaySize = displaySize;
	}
	public boolean isAddComment() {
		return addComment;
	}
	public void setAddComment(boolean addComment) {
		this.addComment = addComment;
	}
}
