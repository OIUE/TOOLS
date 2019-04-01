package org.oiue.table.structure;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Map;

import org.oiue.tools.string.StringUtil;

/**
 * 数据库表格对象
 * @author Every{王勤}
 *
 */
@SuppressWarnings({ "serial" })
public class Table extends TableModel implements Serializable {
	public Table() {}
	
	/**
	 * 所有者即数据库服务器
	 */
	private String schema;
	
	/**
	 * 库名
	 */
	private String catalog;
	
	/**
	 * 连接别名
	 */
	private String dbAlias;
	
	/**
	 * 表名
	 */
	private String name;
	
	/**
	 * 表描述
	 */
	private String comment;
	
	/**
	 * 旧表名
	 */
	private String oName;
	
	/**
	 * 表主键
	 */
	private String id;
	
	/**
	 * 表主键
	 */
	private final Map<String, Field> primaryField = new java.util.LinkedHashMap<String, Field>();
	
	/**
	 * 表主键生成器
	 */
	private String idGenerator;
	
	/**
	 * 表字段，一张表有多个字段，这是原始的JDBC封装使用的所
	 */
	private final Map<String, Field> fields = new java.util.LinkedHashMap<String, Field>();
	
	/**
	 * po类型字段，用于扩充对OneToOne、ManyToOne等对象之间关系的映射
	 */
	private final Map<String, ForeignField> foreignField = new java.util.LinkedHashMap<String, ForeignField>();
	
	/**
	 * lazy load （延迟加载）
	 */
	private boolean lazy = false;
	/**
	 * 加载数据信息来源于
	 */
	private boolean loadFromByConn = true;
	/**
	 * 是否覆盖创建
	 */
	private boolean createCoverage = false;
	
	/**
	 * 是否创建表主键
	 */
	private boolean createPK = false;
	
	/**
	 * 
	 * @param name 表名
	 * @param id id
	 * @param idGenerator 序列
	 */
	public Table(String name, String id, String idGenerator) {
		this.setName(name);
		this.id = id;
		this.idGenerator = idGenerator;
	}
	
	public String getCatalog() {
		return catalog;
	}
	
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
		this.setTableIDFieldName(id);
	}
	
	public String getIdGenerator() {
		return idGenerator;
	}
	
	public void setIdGenerator(String idGenerator) {
		this.idGenerator = idGenerator;
	}
	
	public String getName() {
		return StringUtil.isEmpty(name) ? this.getTableName() : name;
	}
	
	public void setName(String name) {
		this.name = name;
		this.setTableName(name);
	}
	
	public String getOName() {
		return oName;
	}
	
	public void setOName(String name) {
		oName = name;
	}
	
	public String getSchema() {
		return schema;
	}
	
	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	public boolean isLazy() {
		return lazy;
	}
	
	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}
	
	public Map<String, Field> getFields() {
		return fields;
	}
	
	public Map<String, Field> getPrimaryField() {
		return primaryField;
	}
	
	public Map<String, ForeignField> getForeignField() {
		return foreignField;
	}
	
	@Override
	public void clear() {
		
	}
	
	@Override
	public boolean set(ResultSet rs) {
		return false;
	}
	
	@Override
	public String toString(String specDelimiterRecord) {
		return null;
	}
	
	public boolean isCreateCoverage() {
		return this.createCoverage;
	}
	
	public void setCreateCoverage(boolean createCoverage) {
		this.createCoverage = createCoverage;
	}
	
	public boolean isCreatePK() {
		return createPK;
	}
	
	public void setCreatePK(boolean createPK) {
		this.createPK = createPK;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getDbAlias() {
		return dbAlias;
	}
	
	public void setDbAlias(String dbAlias) {
		this.dbAlias = dbAlias;
	}
	
	public boolean isLoadFromByConn() {
		return loadFromByConn;
	}
	
	public void setLoadFromByConn(boolean loadFromByConn) {
		this.loadFromByConn = loadFromByConn;
	}
	
	// private Vector types = new Vector();
	//
	// private Vector params = new Vector();
	
}
