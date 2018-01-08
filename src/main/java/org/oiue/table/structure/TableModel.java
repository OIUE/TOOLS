package org.oiue.table.structure;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.oiue.tools.bean.BeanUtils;
import org.oiue.tools.date.DateUtil;

/**
 * @author Every(王勤) 表格Bean模型
 * 
 */
@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
public abstract class TableModel extends Model_map implements Serializable {

	public TableModel() {
		this.clear();
	}

	/**
	 * put
	 * 方法说明:
	 * 
	 * 
	 * 给对象赋值 将map中对应的key的值赋值给当前对象对应的成员变量 暂未考数据类型差异转换
	 * 
	 * @author Every(王勤)
	 *  Feb 21, 2011 12:26:25 PM
	 * @param map 转换对象
	 * @return 当前对象
	 */
	public Object put(Map map) {
		String key;
		Object value;
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry entry = (Entry) iterator.next();
			key = entry.getKey()+"";
		    value = entry.getValue();
		    this.put(key, value);
		}
		
		return this;
	}

//	public static class sqlException extends Thread {
//		private static List<Exception> e = new ArrayList<Exception>();
//		private static sqlException sException = new sqlException();
//
//		public static void addException(Exception ex) {
//			e.add(ex);
//			if (sException.isAlive()) {
//
//			}
//		}
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			do {
//				for (int i = e.size(); i < 0; i--) {
//					String exs = getExceptionString(e.get(0));
//					if ((exs.indexOf("not found") >= 0)) {
//						System.err.println(e.get(0).getMessage());
//					} else {
//						e.get(0).printStackTrace();
//					}
//					e.remove(0);
//				}
//				if (e.size() == 0) {
//					try {
//						this.wait();
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//			} while (true);
//			// } while (e.size()>0);
//		}
//	}

	public static String getExceptionString(Exception ex) {
		StringBuffer exceptionTrace = new StringBuffer(5120);
		StringWriter sWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(sWriter);
		ex.printStackTrace(printWriter);
		exceptionTrace.delete(0, exceptionTrace.length());
		exceptionTrace.append((new StringBuilder()).append(DateUtil.getNowOfDateByFormat("yyyy/MM/dd HH:mm:ss.SSS")).append("\r\n").toString());
		exceptionTrace.append("------------------------------------------------------------------\r\n");
		exceptionTrace.append(sWriter.toString());
		printWriter.close();
		try {
			sWriter.close();
		} catch (Exception e) {
			return null;
		}
		return exceptionTrace.toString();
	}

	/**
	 * 设置属性
	 * 
	 * @param tm 数据对象
	 */
	public void putProperties(TableModel tm) {
		try {
			BeanUtils.copyProperties(this, tm);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取字段集合的值集合
	 * @param fields 字段名称集合
	 * @return 字段值的集合
	 */
	public List<Object> getValueList(String[] fields){
		List rtnLt=new ArrayList();
		for (String field : fields) {
			rtnLt.add(this.getValue(field));
		}
		
		return rtnLt;
	}
	
	public List<Object> getValueListByKey(String key){
		Object fieldsObj=this.getValue(key);
		String[] fields;
		if(fieldsObj instanceof String){
			fields=((String) fieldsObj).split(",");
		}else if(fieldsObj instanceof String[]){
			fields=(String[]) fieldsObj;
//		}else if(fieldsObj instanceof List){
		}else{
			throw new RuntimeException("the key["+key+"]'s value can not convert to String[]!");
		}
		
		return this.getValueList(fields);
	}
}
