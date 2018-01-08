/**
 * 
 */
package org.oiue.table.structure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.oiue.tools.string.StringUtil;

/**
 * @author Every
 *
 */
@SuppressWarnings({ "rawtypes" })
public abstract class Model_root implements Serializable {

    private static final long serialVersionUID = 1231231231231212221l;

    // 操作编码
    public int _cmdKey;
    // 表名
    private String _tableName = "";
    // 表主键集合
    private List<String> _tableIDFieldNames = null;
    // 层次结构字段集合
    private List<String> _structureFieldNames = null;

    // name和value之间的分隔符
    private String _delimiterNameValue = "=";
    // 每对 name value之间的分隔符
    private String _delimiterRecord = "\r\n";
    // 对象修改影响的记录数
    private int _rowNum;
    // 异常信息
    private String _msg;
    // 用户自定义的参数
    private Map _udfParMap;
    // 授权信息
    private String _grant;
    // 数据过滤条件
    private String _dataWhere;
    // 数据
    private Map _dyn_data;

    // 对象字段集合
    private List<String> _obj_fieldNames;
    // 对象方法集合
    private List<String> _obj_methodNames;
    {
        Class c = this.getClass();

        Field[] fields = c.getDeclaredFields();
        _obj_fieldNames = new Vector<String>();
        String fieldName = null;
        for (Field field : fields) {
            fieldName = field.getName();

            if (!fieldName.startsWith("_"))
                _obj_fieldNames.add(fieldName);
        }

        Method[] methods = c.getDeclaredMethods();
        _obj_methodNames = new Vector<String>();
        String methodName = null;
        for (Method method : methods) {
            methodName = method.getName();
            _obj_methodNames.add(methodName);
        }
        _dyn_data = new HashMap();
    }

    public boolean fieldContains(String fieldName) {
        return _obj_fieldNames.contains(fieldName);
    }

    public boolean methodContains(String methodName) {
        return _obj_methodNames.contains(methodName);
    }

    /**
     *  Method 复制对象
     * @return 复制的对象
     * @throws IOException  io
     * @throws ClassNotFoundException class not found
     */
    public Object deepCopy() throws IOException, ClassNotFoundException {
        // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        // 将流序列化成对象
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

    /**
     * 清除数据的方法
     */
    public abstract void clear();

    /**
     * 根据结果集设定表单的单一记录数据的初始化
     * 
     * @param rs 输入结果集
     * @return 设置是否成功
     */
    public abstract boolean set(ResultSet rs) ;

    /**
     * 将表数据按照指定的分隔符输出
     * 
     * @param specDelimiterRecord 数据间特殊的分隔符
     * @return 转换成字符串
     */
    public abstract String toString(String specDelimiterRecord);

    @Override
    public String toString() {
        return toString(_delimiterRecord);
    }

    /**
     * 操作编码
     * 
     * @return cmd key
     */
    public int getCmdKey() {
        return _cmdKey;
    }

    /**
     * 操作编码
     * 
     * @param cmdKey cmd key
     */
    public void setCmdKey(int cmdKey) {
        this._cmdKey = cmdKey;
    }

    /**
     * 表名
     * 
     * @return 表名
     */
    public String getTableName() {
        if (_tableName == null || _tableName.equals("")) {
            _tableName = this.getClass().getSimpleName();
        }
        return _tableName;
    }

    /**
     * 表名
     * 
     * @param tableName 表名
     */
    public void setTableName(String tableName) {
        this._tableName = tableName;
    }

    /**
     * 表主键
     * 
     * @return 获取表主键
     */
    public String getTableIDFieldName() {
        if (_tableIDFieldNames != null && _tableIDFieldNames.size() == 1) {
            return _tableIDFieldNames.get(0);
        } else if (_tableIDFieldNames == null) {
            throw new RuntimeException("tableIDFieldNames is null");
        } else {
            throw new RuntimeException("tableIDFieldNames not only or is null");
        }
    }

    /**
     * 表主键
     * 
     * @param tableIDFieldName 表主键
     */
    public void setTableIDFieldName(String tableIDFieldName) {
        _tableIDFieldNames = new Vector<String>();
        _tableIDFieldNames.add(tableIDFieldName);
    }

    /**
     * 表主键集合
     * 
     * @return 表主键集合
     */
    public List<String> getTableIDFieldNames() {
        return _tableIDFieldNames;
    }

    /**
     * 表主键集合
     * 
     * @param tableIDFieldNames 表主键
     */
    public void setTableIDFieldNames(List<String> tableIDFieldNames) {
        this._tableIDFieldNames = tableIDFieldNames;
    }

    /**
     * 层次结构字段集合
     * 
     * @return 字段集合
     */
    public List<String> getStructureFieldNames() {
        return _structureFieldNames;
    }

    /**
     * 层次结构字段集合
     * 
     * @param structure 字段
     */
    public void setStructureFieldNames(List<String> structure) {
        this._structureFieldNames = structure;
    }

    /**
     * name和value之间的分隔符
     * 
     * @return 值
     */
    public String getDelimiterNameValue() {
        return _delimiterNameValue;
    }

    /**
     * name和value之间的分隔符
     * 
     * @param delimiterNameValue 分隔符
     */
    public void setDelimiterNameValue(String delimiterNameValue) {
        this._delimiterNameValue = delimiterNameValue;
    }

    /**
     * 每对 name value之间的分隔符
     * 
     * @return 分割后的值
     */
    public String getDelimiterRecord() {
        return _delimiterRecord;
    }

    /**
     * 每对 name value之间的分隔符
     * 
     * @param delimiterRecord 分隔符
     */
    public void setDelimiterRecord(String delimiterRecord) {
        this._delimiterRecord = delimiterRecord;
    }

    /**
     * 对象修改影响的记录数
     * 
     * @return 记录数
     */
    public int getRowNum() {
        return _rowNum;
    }

    /**
     * 对象修改影响的记录数
     * 
     * @param rowNum 记录数
     */
    public void setRowNum(int rowNum) {
        this._rowNum = rowNum;
    }

    /**
     * 异常信息
     * 
     * @return 消息
     */
    public String getMsg() {
        return _msg;
    }

    /**
     * 异常信息
     * 
     * @param msg 消息
     */
    public void setMsg(String msg) {
        this._msg = msg;
    }

    /**
     * 用户自定义的参数
     * 
     * @return 参数
     */
    public Map getUdfParMap() {
        return _udfParMap;
    }

    /**
     * 用户自定义的参数 未初始化则自动初始化
     * 
     * @return 参数
     */
    public Map getUdfParMapInitialize() {
        if (_udfParMap == null) {
            _udfParMap = new HashMap();
        }
        return _udfParMap;
    }

    /**
     * 用户自定义的参数
     * 
     * @param udfParMap 参数
     */
    public void setUdfParMap(Map udfParMap) {
        this._udfParMap = udfParMap;
    }

    /**
     * 授权信息
     * 
     * @return 授权
     */
    public String getGrant() {
        return _grant;
    }

    /**
     * 授权信息
     * 
     * @param grant 授权
     */
    public void setGrant(String grant) {
        this._grant = grant;
    }

    /**
     * 数据过滤条件
     * 
     * @return 授权
     */
    public String getDataWhere() {
        return _dataWhere;
    }

    /**
     * 数据过滤条件
     * 
     * @param dataWhere 条件
     */
    public void setDataWhere(String dataWhere) {
        this._dataWhere = dataWhere;
    }

    public Map getDyn_data() {
        return _dyn_data;
    }

    public void setDyn_data(Map dyn_data) {
        this._dyn_data = dyn_data;
    }

    public Object getValue(String felidName) {
        try {
            if (this.fieldContains(felidName)) {
                Field fd = this.getClass().getField(felidName);
                if (fd.getModifiers() == Modifier.PUBLIC) {
                    return fd.get(this);
                }
                Method fm;
                if (this.methodContains("get" + StringUtil.firstToUpper(felidName))) {
                    fm = this.getClass().getMethod("get" + StringUtil.firstToUpper(felidName));
                    return fm.invoke(this);
                } else if (this.methodContains("is" + StringUtil.firstToUpper(felidName))) {
                    fm = this.getClass().getMethod("is" + StringUtil.firstToUpper(felidName));
                    return fm.invoke(this);
                } else if (this.methodContains(felidName)) {
                    fm = this.getClass().getMethod(felidName);
                    return fm.invoke(this);
                }
            }
            Object value = this.getDyn_data().get(felidName);
            if (value == null)
                value = this.getUdfParMapInitialize().get(felidName);
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * put
     * 方法说明:
     * 
     *               
     * 设定对象属性值 暂未考数据类型差异转换
     * 
     * @author Every(王勤)
     * 
     *  Feb 21, 2011 12:26:26 PM
     * @param felidName  字段
     * @param value 值
     */
    @SuppressWarnings("unchecked")
    public void put(String felidName, Object value) {
        try {
            if (this.fieldContains(felidName)) {
                Field fd = this.getClass().getField(felidName);
                if (fd.getModifiers() == Modifier.PUBLIC) {
                    fd.set(this, value);
                    return;
                }
                Method fm;
                Class[] pt = { value.getClass() };
                Object[] args = { value };
                if (this.methodContains("set" + StringUtil.firstToUpper(felidName))) {
                    fm = this.getClass().getMethod("set" + StringUtil.firstToUpper(felidName), pt);
                    fm.invoke(this, args);
                    return;
                } else if (this.methodContains(felidName)) {
                    fm = this.getClass().getMethod(felidName, pt);
                    fm.invoke(this, args);
                    return;
                }
                throw new RuntimeException(felidName + " is not public,methods have not " + "set" + StringUtil.firstToUpper(felidName) + " and " + felidName + ",can not setvalue!");
            } else {
                _dyn_data.put(felidName, value);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
