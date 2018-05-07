/**
 * 
 */
package org.oiue.tools.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.json.JSONArray;
import org.json.JSONException;
import org.oiue.tools.json.JSONUtil;
import org.oiue.tools.map.MapUtil;

/**
 * 类说明: List操作类
 * @author Every E-mail/MSN:mwgjkf@hotmail.com QQ:30130942 ListUtil 1.0 Apr 22, 2009 5:30:26 PM ListUtil
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ListUtil {
	
	/**
	 * 方法说明： 创建一个二维的ArrayList CreateTime Apr 23, 2009 10:30:06 AM
	 * @param row ArrayList的长度
	 * @param cell ArrayList的维长度
	 * @return 创建的ArrayList
	 */
	public static ArrayList<ArrayList<String>> arrayListInit(int row, int cell) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < row; i++) {
			ArrayList<String> li = new ArrayList<String>();
			for (int j = 0; j < cell; j++) {
				li.add(null);
			}
			list.add(li);
		}
		return list;
	}
	
	/**
	 * 方法说明： 创建一个二维的ArrayList CreateTime Apr 23, 2009 10:30:06 AM
	 * @param row ArrayList的长度
	 * @param cell ArrayList的维长度
	 * @param init ArrayList中初始化化数据
	 * @return 创建的ArrayList
	 */
	public static ArrayList<ArrayList<String>> arrayListInit(int row, int cell, String init) {
		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < row; i++) {
			ArrayList<String> li = new ArrayList<String>();
			for (int j = 0; j < cell; j++) {
				li.add(init);
			}
			list.add(li);
		}
		return list;
	}
	
	/**
	 * 方法说明： 创建一个指定长度的ArrayList CreateTime May 7, 2009 10:36:25 AM
	 * @param cell ArrayList的长度
	 * @return 创建的ArrayList
	 */
	public static ArrayList<String> arrayListInit(int cell) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < cell; i++) {
			list.add(null);
		}
		return list;
	}
	
	/**
	 * 方法说明： 创建一个指定长度的ArrayList CreateTime May 7, 2009 10:36:25 AM
	 * @param cell ArrayList的长度
	 * @param init ArrayList中初始化化数据
	 * @return 创建的ArrayList
	 */
	public static ArrayList<String> arrayListInit(int cell, String init) {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < cell; i++) {
			list.add(init);
		}
		return list;
	}
	
	/**
	 * 方法说明： 清除二维ArrayList中的空维 CreateTime Apr 23, 2009 10:53:42 AM
	 * @param list 源ArrayList
	 * @return 清除二维ArrayList中的空维后的ArrayList
	 */
	public static ArrayList<ArrayList<String>> clearArrayList(ArrayList<ArrayList<String>> list) {
		if (list != null && list.size() > 0) {
			ArrayList<ArrayList<String>> lis = new ArrayList<ArrayList<String>>();
			int k = 0;
			for (int i = 0; i < list.size(); i++) {
				k = 0;
				for (int j = 0; j < list.get(i).size(); j++) {
					if (list.get(i).get(j) == null || list.get(i).get(j).trim().equals("") || list.get(i).get(j).trim().equals("null")) {
						k++;
					}
				}
				if (k < list.get(i).size()) {
					lis.add(list.get(i));
				}
			}
			return lis;
		} else {
			return null;
		}
	}
	
	/**
	 * 方法说明： 判断已存在的数组中是否含有指定的字符串 list.get(i).get(0).toString().trim().equals(str)
	 * @param list 数组
	 * @param str 指定字符串
	 * @return 如果有则返回所在数组下标 ，不含有则返回-1
	 */
	public static int ArraylistHaveStr(ArrayList<ArrayList> list, String str) {
		int ishave = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).get(0) != null && list.get(i).get(0).toString().trim().equals(str)) {
				ishave = i;
				break;
			}
		}
		return ishave;
	}
	
	/**
	 * 方法说明： 添加结构数组， filed.equals(oldList.get(index).toString())
	 * @param oldList 添加的原数组
	 * @param filed 添加匹配字段值
	 * @param index 对应数组单维字段下标
	 * @param newList 添加的数组
	 */
	public static void addList(ArrayList oldList, String filed, int index, ArrayList newList) {
		for (int i = 0; i < oldList.size(); i++) {
			if (i > (index + 1)) {
				addList((ArrayList) oldList.get(i), filed, index, newList);
			} else if (i == index) {
				if (filed.equals(oldList.get(i).toString())) {
					oldList.add(newList);
				}
			}
		}
	}
	
	/**
	 * 方法说明： 获取结构数组， filed.equals(data.get(index).toString())
	 * @param data 列表
	 * @param filed 字段
	 * @param index 下标
	 * @return 结果
	 */
	public static ArrayList getList(ArrayList data, String filed, int index) {
		ArrayList list = null;
		for (int i = 0; i < data.size(); i++) {
			if (i > (index + 1)) {
				list = getList((ArrayList) data.get(i), filed, index);
				if (list != null)
					break;
			} else if (i == index) {
				if (filed.equals(data.get(i).toString())) {
					list = data;
					break;
				}
			}
		}
		return list;
	}
	
	/**
	 * 方法说明： List转换字符串 用连接符连接，实现了类似js的join方法 讲数组的每个元素用split连接
	 * @param list 操作数组
	 * @param split 连接符
	 * @return 连接后的字符串
	 */
	public static String ListJoin(List list, String split) {
		if (list == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			sb.append(object + split);
		}
		if (list.size() == 0) {
			sb.append(split);
		}
		return sb.toString().substring(0, split == null ? sb.toString().length() : (sb.toString().length() - split.length()));
	}
	
	/**
	 * 方法说明： List转换字符串 用连接符连接，实现了类似js的join方法 讲数组的每个元素用split连接
	 * @param list 操作数组
	 * @param split 连接符
	 * @return 连接后的字符串
	 */
	public static String ArrayJoin(Object[] list, String split) {
		if (list == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.length; i++) {
			sb.append(list[i] + split);
		}
		if (list.length == 0) {
			sb.append(split);
		}
		return sb.toString().substring(0, split == null ? sb.toString().length() : (sb.toString().length() - split.length()));
	}
	
	/**
	 * List 排序
	 * @param data 操作的原始对象
	 * @param comperator 排序规则 排序的字段 是否逆序
	 */
	public static void sort(List data, Map<String, Boolean> comperator) {
		ComparatorChain cc = new ComparatorChain();
		for (Iterator iterator = comperator.keySet().iterator(); iterator.hasNext();) {
			String filed = (String) iterator.next();
			cc.addComparator(ComparatorUtils.nullLowComparator(new BeanComparator(filed)), comperator.get(filed));
		}
		Collections.sort(data, cc);
	}
	
	public static List<Object> toList(List<Object> list, Collection<Integer> indexes) {
		if (indexes == null) {
			return null;
		}
		return toList(list, indexes.toArray(new Integer[0]));
	}
	
	public static List<Object> toList(List<Object> list, Integer[] indexes) {
		if ((list == null) || (indexes == null)) {
			return null;
		}
		List<Object> result = new ArrayList<Object>();
		for (int e : indexes) {
			result.add(list.get(e));
		}
		return list;
	}
	
	public static List<Object> toList(List<Object> list, List<Object> indexes) {
		if ((list == null) || (indexes == null)) {
			return null;
		}
		List<Object> result = new ArrayList<Object>();
		for (Object e : indexes) {
			if (e instanceof Number) {
				result.add(list.get(((Number) e).intValue()));
			} else {
				result.add(null);
			}
		}
		return list;
	}
	
	public static String toString(List<Object> list) {
		return new JSONArray(list).toString();
	}
	
	public static Object get(List<Object> list, int index) {
		return list.get(index);
	}
	
	public static void put(List<Object> list, Object object) {
		list.add(object);
	}
	
	public static void put(List<Object> list, int index, Object object) {
		list.add(index, object);
	}
	
	public static List<Object> fromString(String source) {
		try {
			return JSONUtil.toList(new JSONArray(source));
		} catch (JSONException e) {
			return null;
		}
	}
	
	public static boolean contain(List<Object> a, List<Object> b) {
		if (b == null) {
			return true;
		}
		
		if (a == null) {
			return false;
		}
		
		for (int i = 0; i < b.size(); i++) {
			Object av = a.get(i);
			if (av == null) {
				if (b.get(i) == null) {
					continue;
				} else {
					return false;
				}
			} else if (av instanceof Map) {
				if (!MapUtil.contain((Map<String, Object>) av, (Map<String, Object>) b.get(i))) {
					return false;
				}
			} else if (av instanceof List) {
				if (!ListUtil.contain((List<Object>) av, (List<Object>) b.get(i))) {
					return false;
				}
			} else if (!av.equals(b.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean equals(List<Object> a, List<Object> b) {
		if ((a == null) && (b == null)) {
			return true;
		}
		
		if ((a == null) || (b == null)) {
			return false;
		}
		
		if (a.size() != b.size()) {
			return false;
		}
		
		for (int i = 0; i < b.size(); i++) {
			Object av = a.get(i);
			if (av == null) {
				if (b.get(i) == null) {
					continue;
				} else {
					return false;
				}
			} else if (av instanceof Map) {
				if (!MapUtil.equals((Map<String, Object>) av, (Map<String, Object>) b.get(i))) {
					return false;
				}
			} else if (av instanceof List) {
				if (!ListUtil.equals((List<Object>) av, (List<Object>) b.get(i))) {
					return false;
				}
			} else if (!av.equals(b.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static List<Object> clone(List<Object> list) {
		if (list == null) {
			return null;
		}
		
		List<Object> result = new ArrayList<Object>();
		for (Object v : list) {
			if (v instanceof Map) {
				result.add(MapUtil.clone((Map<String, Object>) v));
			} else if (v instanceof List) {
				result.add(ListUtil.clone((List<Object>) v));
			} else {
				result.add(v);
			}
		}
		return result;
	}
	
	public static void mergeTo(List<Object> target, List<Object> source) {
		if ((target == null) || (source == null)) {
			return;
		}
		target.addAll(source);
	}
	
	public static List<Object> merge(List<Object> a, List<Object> b) {
		if ((a == null) || (b == null)) {
			return null;
		}
		
		List<Object> result = new ArrayList<Object>();
		ListUtil.mergeTo(result, a);
		ListUtil.mergeTo(result, b);
		return result;
	}
	
	public static String getString(List<Object> list, int index) {
		Object object = list.get(index);
		return object == null ? null : object.toString();
	}
	
	public static Double getDouble(List<Object> list, int index) {
		Object object = list.get(index);
		return Double.valueOf((object instanceof Number) ? ((Number) object).doubleValue() : Double.parseDouble(object.toString()));
	}
	
	public static boolean getBoolean(List<Object> list, int index) {
		Object object = list.get(index);
		
		return (object.equals(Boolean.TRUE)) || (((object instanceof String)) && (((String) object).equalsIgnoreCase("true"))) || (((String) object).equalsIgnoreCase("1")) || (((object instanceof Number)) && (((Number) object).intValue() == 1));
	}
	
	public static int getInt(List<Object> list, int index) {
		Object object = list.get(index);
		return (object instanceof Number) ? ((Number) object).intValue() : Integer.parseInt(object.toString());
	}
	
	public static long getLong(List<Object> list, int index) {
		Object object = list.get(index);
		return (object instanceof Number) ? ((Number) object).longValue() : Long.parseLong(object.toString());
	}
	
	public static Object get(List<Object> list, String keys) {
		
		String[] ks = keys.split("\\.", 2);
		int key = Integer.valueOf(ks[0]);
		if (ks.length == 1) {
			return list.get(key);
		} else {
			Object ov = list.get(key);
			if (ov instanceof Map) {
				return MapUtil.get((Map) ov, ks[1]);
			} else if (ov instanceof List) {
				return get(((List) ov), ks[1]);
			} else {
				throw new RuntimeException("The type of cannot be parsed");
			}
		}
	}
	
	/**
	 * 不排序二维数据转换成树状结构
	 * @param source source list
	 * @param key tree key
	 * @param parentKey tree parent key
	 * @param childKey child key
	 * @return 树结构
	 */
	public static List<Map> convertToTree(List<Map> source, String key, String parentKey, String childKey) {
		return convertToTree(source, null, childKey, parentKey, childKey, null);
	}
	
	/**
	 * 不排序二维数据转换成树状结构
	 * @param source 原始数据
	 * @param parent 所属父节点
	 * @param key 主键
	 * @param parentKey 父主键
	 * @param childKey 子节点key
	 * @return 树状数据
	 */
	public static List<Map> convertToTree(List<Map> source, Object parent, String key, String parentKey, String childKey) {
		return convertToTree(source, parent, childKey, parentKey, childKey, null);
	}
	
	/**
	 * 二维数据转换成树状结构
	 * @param source 原始数据
	 * @param parent 所属父节点
	 * @param key 主键
	 * @param parentKey 父主键
	 * @param childKey 子节点key
	 * @param sortKey 排序字段
	 * @return 排序好的树状数据
	 */
	public static List<Map> convertToTree(List<Map> source, Object parent, String key, String parentKey, String childKey, String sortKey) {
		if (source != null) {
			List s = new Vector<>(source);
			
			Map<Object, Map> temp = new HashMap<>();
			for (Iterator iterator = s.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				temp.put(map.get(key), map);
			}
			for (Iterator iterator = s.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				Object parentK = map.get(parentKey);
				if (parentK != null) {
					Map tp = temp.get(parentK);
					if (tp != null) {
						Object child = tp.get(childKey);
						if (child == null) {
							List tc = new ArrayList<>();
							tc.add(map);
							tp.put(childKey, tc);
						} else if (child instanceof List) {
							((List) child).add(map);
						} else {
							throw new RuntimeException("childKey[" + childKey + "] error.");
						}
					}
				}
			}
			List<Map> vs = new ArrayList<>(temp.values());
			for (Iterator iterator = vs.iterator(); iterator.hasNext();) {
				Map map = (Map) iterator.next();
				if (parent == null ? parent != map.get(parentKey) : !parent.equals(map.get(parentKey))) {
					iterator.remove();
				}
			}
			if (sortKey != null) {
				Map comperator = new HashMap<>();
				comperator.put(sortKey, false);
				for (Iterator iterator = s.iterator(); iterator.hasNext();) {
					Map map = (Map) iterator.next();
					Object child = map.get(childKey);
					if (child != null && child instanceof List) {
						ListUtil.sort((List) child, comperator);
					}
				}
				ListUtil.sort(vs, comperator);
			}
			return vs;
		}
		return null;
	}
}
