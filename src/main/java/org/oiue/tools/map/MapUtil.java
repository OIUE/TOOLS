package org.oiue.tools.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;
import org.oiue.tools.StatusResult;
import org.oiue.tools.exception.OIUEException;
import org.oiue.tools.json.JSONUtil;
import org.oiue.tools.list.ListUtil;
import org.oiue.tools.string.StringUtil;

/**
 *
 * @author Every
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MapUtil {
	/**
	 * 将对象的key转换成
	 * @param object 对象
	 * @return 结果对象
	 */
	public static Object toLowerCaseKey(Object object) {
		if (object instanceof Iterable<?>) {
			List rtnL = new ArrayList();
			for (Iterator iterator = ((Iterable) object).iterator(); iterator.hasNext();) {
				Object type = iterator.next();
				rtnL.add(toLowerCaseKey(type));
			}
			return rtnL;
		} else if (object instanceof Map<?, ?>) {
			Map rtnM = new HashMap();
			for (Iterator iterator = ((Map) object).keySet().iterator(); iterator.hasNext();) {
				Object key = iterator.next();
				rtnM.put((key instanceof String) ? (key != null ? key.toString().toLowerCase() : key) : key, toLowerCaseKey(((Map) object).get(key)));
			}
			return rtnM;
		} else {
			return object;
		}
	}
	
	/**
	 * 忽略键大小写获取map对应的值
	 * @param map map
	 * @param key key
	 * @return 值
	 */
	public static Object getVauleMatchCase(Map map, Object key) {
		if (map == null) {
			return null;
		}
		if (key == null || !(key.getClass().getName().equals("String") || key.getClass().getName().equals("java.lang.String"))) {
			return map.get(key);
		}
		if (map.get(key) != null) {
			return map.get(key);
		}
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			Object keys = iterator.next();
			if (keys != null && (keys.getClass().getName().equals("String") || keys.getClass().getName().equals("java.lang.String")) && ((String) keys).equalsIgnoreCase(key + "")) {
				return map.get(keys);
			}
		}
		return null;
	}
	
	/**
	 * 忽略键大小写移除map对应的值
	 * @param map map
	 * @param key key
	 * @return 值
	 */
	public static Object removeMatchCase(Map map, Object key) {
		if (map == null) {
			return null;
		}
		if (key == null || !(key.getClass().getName().equals("String") || key.getClass().getName().equals("java.lang.String"))) {
			map.remove(key);
			return map;
		}
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			Object keys = iterator.next();
			if (keys != null && (keys.getClass().getName().equals("String") || keys.getClass().getName().equals("java.lang.String")) && ((String) keys).equalsIgnoreCase(key + "")) {
				map.remove(keys);
				break;
			}
		}
		return map;
	}
	
	/**
	 * 方法说明： 将map的key按指定的分隔符分割拼接成字符串
	 * @param map 源Map对象
	 * @param split 分隔符
	 * @return 字符串
	 */
	public static String mapKeyStr(Map map, String split) {
		if (map == null) {
			return "";
		}
		List keyList = new ArrayList();
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			keyList.add(key);
		}
		return ListUtil.ListJoin(keyList, split);
	}
	
	/**
	 * 方法说明: 将map的value按mysql sql的方式拼接
	 * @param map map
	 * @param split 分隔符
	 * @return String 值
	 */
	public static String mapValueStrForMysql(Map map, String split) {
		if (map == null) {
			return "";
		}
		List keyList = new ArrayList();
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object value = map.get(key);
			keyList.add("'" + value + "'");
		}
		return ListUtil.ListJoin(keyList, split);
	}
	
	/**
	 * 方法说明: 将map以key=value按mysql sql的方式拼接
	 * @param map map
	 * @param split 分隔符
	 * @return String 值
	 */
	public static String mapKeyValueForMysql(Map map, String split) {
		if (map == null) {
			return "";
		}
		List keyList = new ArrayList();
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			Object value = map.get(key);
			keyList.add(key + "='" + value + "'");
		}
		return ListUtil.ListJoin(keyList, split);
	}
	
	/**
	 * 将map转换成字符串
	 * @param map 转换的map
	 * @param firstSeparator 一维分割符
	 * @param secondSeparator 二维分割符
	 * @param thrs 字符串数组
	 * @return 值
	 */
	public static String map2Str(Map map, String firstSeparator, String secondSeparator, String... thrs) {
		if (map == null || StringUtil.isEmpty(firstSeparator) || StringUtil.isEmpty(secondSeparator)) {
			throw new OIUEException(StatusResult._data_error, "请检查所传参数");
		}
		StringBuffer sb = new StringBuffer();
		Object value;
		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (sb.length() != 0) {
				sb.append(firstSeparator);
			}
			value = map.get(key);
			if (value instanceof String) {
				sb.append(key + secondSeparator + value);
			} else if (value instanceof String[]) {
				sb.append(key + secondSeparator + ListUtil.ArrayJoin((String[]) value, thrs == null || thrs.length == 0 ? "" : thrs[0]));
			} else {
				sb.append(key + secondSeparator + value);
			}
		}
		return sb.toString();
	}
	
	public static Map grantSum(Object grant, Map<String, String> gm, String split, boolean type) {
		if (gm == null) {
			gm = new HashMap();
		}
		if (grant == null) {
			return gm;
		}
		if (grant instanceof ArrayList) {
			String item = "";
			String[] its = null;
			String[] gms = null;
			String gmvalue = "";
			List grantList = ((ArrayList) grant);
			for (int i = 0; i < grantList.size(); i++) {
				item = (String) grantList.get(i);
				its = item.split(split);
				if (its.length > 1) {
					gmvalue = gm.get(its[0]);
					if (gmvalue == null) {
						gmvalue = "0:0:0:0";
					}
					gms = gmvalue.split(":");
					try {
						if (gms.length > Integer.parseInt(its[1])) {
							gms[Integer.parseInt(its[1])] = (type ? 1 : -1) + "";
						}
					} catch (Throwable e) {
						// TODO: handle exception
					}
					gm.put(its[0], ListUtil.ArrayJoin(gms, ":"));
				}
			}
			
		} else {
			
		}
		return gm;
	}
	
	public static Object get(Map map, String keys) {
		if (map == null)
			return null;
		Object rtn = map.get(keys);
		if (rtn != null) {
			if (rtn instanceof String)
				rtn = ((String) rtn).trim();
			return rtn;
		}
		String[] ks = keys.split("\\.", 2);
		if (ks.length != 1) {
			Object ov = map.get(ks[0]);
			if (ov instanceof Map) {
				return get((Map) ov, ks[1]);
			} else if (ov instanceof Dictionary) {
				return get((Dictionary) ov, ks[1]);
			} else if (ov instanceof List) {
				// ks=ks[1].split("\\.",2);
				// int key=Integer.valueOf(ks[0]);
				// if(ks.length==1){
				// return ((List)ov).get(key);
				// }else{
				// Object ove=((List)ov).get(key);
				// if(ove instanceof Map){
				// return get((Map) ove, ks[1]);
				// }
				// }
				return ListUtil.get((List) ov, ks[1]);
			} else {
				throw new RuntimeException("The type of cannot be parsed");
			}
		} else
			return null;
	}
	
	public static Object get(Dictionary map, String keys) {
		if (map == null)
			return null;
		Object rtn = map.get(keys);
		if (rtn != null) {
			if (rtn instanceof String)
				rtn = ((String) rtn).trim();
			return rtn;
		}
		String[] ks = keys.split("\\.", 2);
		if (ks.length != 1) {
			Object ov = map.get(ks[0]);
			if (ov instanceof Map) {
				return get((Map) ov, ks[1]);
			} else if (ov instanceof Dictionary) {
				return get((Dictionary) ov, ks[1]);
			} else if (ov instanceof List) {
				// ks=ks[1].split("\\.",2);
				// int key=Integer.valueOf(ks[0]);
				// if(ks.length==1){
				// return ((List)ov).get(key);
				// }else{
				// Object ove=((List)ov).get(key);
				// if(ove instanceof Map){
				// return get((Map) ove, ks[1]);
				// }
				// }
				return ListUtil.get((List) ov, ks[1]);
			} else {
				throw new RuntimeException("The type of cannot be parsed");
			}
		} else
			return null;
	}
	
	public static void put(List list, String keys, Object object) {
		if (list == null) {
			return;
		}
		String[] ks = keys.split("\\.", 2);
		if (ks.length == 1) {
			Object ov = list.get(Integer.valueOf(ks[0]));
			if (ov instanceof List) {
				((List)ov).add(object);
			}
		} else {
			Object ov = list.get(Integer.valueOf(ks[0]));
			if (ov instanceof Map) {
				put((Map) ov, ks[1], object);
			} else if (ov instanceof List) {
				put((List) ov, ks[1], object);
			}
		}
	}
	public static void put(Map map, String keys, Object object) {
		if (map == null) {
			return;
		}
		String[] ks = keys.split("\\.", 2);
		if (ks.length == 1) {
			map.put(keys, object);
		} else {
			Object ov = map.get(ks[0]);
			if (ov instanceof Map) {
				put((Map) ov, ks[1], object);
			} else if (ov instanceof List) {
				put((List) ov, ks[1], object);
			} else{
				map.put(keys, object);
			}
		}
	}
	
	public static List<Object> toList(Map<String, Object> map, Collection<String> keys) {
		if (keys == null) {
			return null;
		}
		return toList(map, keys.toArray(new String[0]));
	}
	
	public static List<Object> toList(Map<String, Object> map, String[] keys) {
		if ((map == null) || (keys == null)) {
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		for (String e : keys) {
			list.add(get(map, e));
		}
		return list;
	}
	
	public static List<Object> toList(Map<String, Object> map, List<Object> keySteps) {
		if ((map == null) || (keySteps == null)) {
			return null;
		}
		List<Object> result = new ArrayList<Object>();
		for (Object e : keySteps) {
			if (e instanceof List) {
				result.add(get(map, (List<Object>) e));
			} else {
				result.add(get(map, e + ""));
			}
		}
		return result;
	}
	
	public static Map<String, Object> toMap(Map<String, Object> map, Map<String, String> transMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Entry<String, String> e : transMap.entrySet()) {
			result.put(e.getValue(), get(map, e.getKey()));
		}
		return result;
	}
	
	public static Map<String, Object> toMap(Map<String, Object> map, Collection<String> keys) {
		if (keys == null) {
			return null;
		}
		return toMap(map, keys.toArray(new String[0]));
	}
	
	public static Map<String, Object> toMap(Map<String, Object> map, String[] keys) {
		if ((map == null) || (keys == null)) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		for (String e : keys) {
			result.put(e, get(map, e));
		}
		return result;
	}
	
	// public static Object get(Map<String, Object> map, String key) {
	// if (map == null) {
	// return null;
	// }
	// return map.get(key);
	// }
	
	// public static void put(Map<String, Object> map, String key, Object object) {
	// if (map == null) {
	// return;
	// }
	// map.put(key, object);
	// }
	
	public static Object get(Map<String, Object> map, List<Object> keyStep) {
		return get(map, keyStep.toArray(new Object[0]));
	}
	
	public static void put(Map<String, Object> map, List<Object> keyStep, Object object) {
		put(map, keyStep.toArray(new Object[0]), object);
	}
	
	public static Object get(Map<String, Object> map, Object[] keyStep) {
		if ((map == null) || (keyStep == null)) {
			return null;
		}
		
		Map<String, Object> cursor = map;
		for (int i = 0; i < keyStep.length - 1; i++) {
			Object tc = cursor.get(keyStep[i]);
			if (tc instanceof Map) {
				cursor = (Map<String, Object>) tc;
			} else {
				cursor = null;
				break;
			}
		}
		if (cursor == null) {
			return null;
		} else {
			return cursor.get(keyStep[keyStep.length - 1]);
		}
	}
	
	public static void put(Map<String, Object> map, Object[] keyStep, Object object) {
		if ((map == null) || (keyStep == null)) {
			return;
		}
		
		Map<String, Object> cursor = map;
		for (int i = 0; i < keyStep.length - 1; i++) {
			Object tc = cursor.get(keyStep[i]);
			if (tc instanceof Map) {
				cursor = (Map<String, Object>) tc;
			} else {
				HashMap<String, Object> t = new HashMap<String, Object>();
				cursor.put((String) keyStep[i], t);
				cursor = t;
			}
		}
		cursor.put((String) keyStep[keyStep.length - 1], object);
	}
	
	public static String toString(Map<String, Object> map) {
		return new JSONObject(map).toString();
	}
	
	public static Map<String, Object> fromString(String source) {
		try {
			return JSONUtil.toMap(new JSONObject(source));
		} catch (JSONException e) {
			return null;
		}
	}
	
	public static boolean contain(Map<String, Object> large, Map<String, Object> small) {
		if (small == null) {
			return true;
		}
		
		if (large == null) {
			return false;
		}
		
		for (Entry<String, Object> e : small.entrySet()) {
			String k = e.getKey();
			Object v = e.getValue();
			if (large.containsKey(k)) {
				Object lv = large.get(k);
				if (lv == null) {
					if (v == null) {
						continue;
					} else {
						return false;
					}
				} else if (lv instanceof Map) {
					if (v instanceof Map) {
						if (!MapUtil.contain((Map<String, Object>) lv, (Map<String, Object>) v)) {
							return false;
						}
					} else {
						return false;
					}
				} else if (lv instanceof List) {
					if (v instanceof List) {
						if (!ListUtil.contain((List<Object>) lv, (List<Object>) v)) {
							return false;
						}
					} else {
						return false;
					}
				} else if (!lv.equals(v)) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
	
	public static boolean equals(Map<String, Object> a, Map<String, Object> b) {
		if ((a == null) && (b == null)) {
			return true;
		}
		
		if ((a == null) || (b == null)) {
			return false;
		}
		
		if (a.size() != b.size()) {
			return false;
		}
		
		for (Entry<String, Object> e : b.entrySet()) {
			String k = e.getKey();
			if (a.containsKey(k)) {
				Object av = a.get(k);
				if (av == null) {
					if (e.getValue() == null) {
						continue;
					} else {
						return false;
					}
				} else if (av instanceof Map) {
					if (!MapUtil.equals((Map<String, Object>) av, (Map<String, Object>) e.getValue())) {
						return false;
					}
				} else if (av instanceof List) {
					if (!ListUtil.equals((List<Object>) av, (List<Object>) e.getValue())) {
						return false;
					}
				} else if (!av.equals(e.getValue())) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
	
	public static void mergeTo(Map<String, Object> target, Map<String, Object> source) {
		if ((target == null) || (source == null)) {
			return;
		}
		
		for (Entry<String, Object> e : source.entrySet()) {
			String k = e.getKey();
			Object v = e.getValue();
			if (target.containsKey(k)) {
				Object tv = target.get(k);
				if (tv instanceof Map) {
					if (v instanceof Map) {
						MapUtil.mergeTo((Map<String, Object>) tv, (Map<String, Object>) v);
					}
				} else if (tv instanceof List) {
					if (v instanceof List) {
						ListUtil.mergeTo((List<Object>) tv, (List<Object>) v);
					} else {
						((List<Object>) tv).add(v);
					}
				} else {
					List<Object> list = new ArrayList<Object>();
					list.add(tv);
					list.add(v);
					target.put(k, list);
				}
			} else {
				target.put(k, v);
			}
		}
	}
	
	public static void mergeDifference(Map<String, Object> target, Map<String, Object> source) {
		if ((target == null) || (source == null)) {
			return;
		}
		
		for (Entry<String, Object> e : source.entrySet()) {
			String k = e.getKey();
			Object v = e.getValue();
			if (target.containsKey(k)) {
				Object tv = target.get(k);
				if (tv instanceof Map) {
					if (v instanceof Map) {
						MapUtil.mergeDifference((Map<String, Object>) tv, (Map<String, Object>) v);
					}
				} else if (tv instanceof List) {
					if (v instanceof List) {
						ListUtil.mergeTo((List<Object>) tv, (List<Object>) v);
					} else {
						((List<Object>) tv).add(v);
					}
				} else {}
			} else {
				target.put(k, v);
			}
		}
	}
	
	public static Map<String, Object> merge(Map<String, Object> a, Map<String, Object> b) {
		if ((a == null) || (b == null)) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		MapUtil.mergeTo(result, a);
		MapUtil.mergeTo(result, b);
		return result;
	}
	
	public static Map<String, Object> clone(Map<String, Object> map) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Entry<String, Object> e : map.entrySet()) {
			Object v = e.getValue();
			if (v instanceof Map) {
				result.put(e.getKey(), MapUtil.clone((Map<String, Object>) v));
			} else if (v instanceof List) {
				result.put(e.getKey(), ListUtil.clone((List<Object>) v));
			} else {
				result.put(e.getKey(), v);
			}
		}
		return result;
	}
	
	public static String getString(Map<String, Object> map, String key) {
		Object object = get(map, key);
		return object == null ? null : object.toString();
	}
	
	public static String getString(Map<String, Object> map, String key, String defaultVal) {
		Object object = get(map, key);
		return object == null ? defaultVal : object.toString();
	}
	
	public static String getString(Dictionary map, String key, String defaultVal) {
		Object object = get(map, key);
		return object == null ? defaultVal : object.toString();
	}
	
	public static Double getDouble(Map<String, Object> map, String key) {
		Object object = get(map, key);
		return Double.valueOf((object instanceof Number) ? ((Number) object).doubleValue() : Double.parseDouble(object.toString()));
	}
	
	public static Double getDouble(Map<String, Object> map, String key, Double defaultVal) {
		Object object = get(map, key);
		return object == null ? defaultVal : Double.valueOf((object instanceof Number) ? ((Number) object).doubleValue() : Double.parseDouble(object.toString()));
	}
	
	public static boolean getBoolean(Map<String, Object> map, String key) {
		Object object = get(map, key);
		return (object.equals(Boolean.TRUE)) || (((object instanceof String)) && ((((String) object).equalsIgnoreCase("true")) || (((String) object).equalsIgnoreCase("1")))) || (((object instanceof Number)) && (((Number) object).intValue() == 1));
	}
	
	public static boolean getBoolean(Map<String, Object> map, String key, Boolean defaultVal) {
		Object object = get(map, key);
		return object == null ? defaultVal : (object.equals(Boolean.TRUE)) || (((object instanceof String)) && ((((String) object).equalsIgnoreCase("true")) || (((String) object).equalsIgnoreCase("1")))) || (((object instanceof Number)) && (((Number) object).intValue() == 1));
	}
	
	public static boolean getBoolean(Dictionary map, String key, Boolean defaultVal) {
		Object object = get(map, key);
		return object == null ? defaultVal : (object.equals(Boolean.TRUE)) || (((object instanceof String)) && ((((String) object).equalsIgnoreCase("true")) || (((String) object).equalsIgnoreCase("1")))) || (((object instanceof Number)) && (((Number) object).intValue() == 1));
	}
	
	public static int getInt(Map<String, Object> map, String key) {
		Object object = get(map, key);
		return (object instanceof Number) ? ((Number) object).intValue() : Integer.parseInt(object.toString());
	}
	
	public static int getInt(Map<String, Object> map, String key, Integer defaultVal) {
		Object object = get(map, key);
		return object == null ? defaultVal : (object instanceof Number) ? ((Number) object).intValue() : Integer.parseInt(object.toString());
	}
	
	public static int getInt(Dictionary map, String key, Integer defaultVal) {
		Object object = get(map, key);
		return object == null ? defaultVal : (object instanceof Number) ? ((Number) object).intValue() : Integer.parseInt(object.toString());
	}
	
	public static long getLong(Map<String, Object> map, String key) {
		Object object = get(map, key);
		return (object instanceof Number) ? ((Number) object).longValue() : Long.parseLong(object.toString());
	}
	
	public static long getLong(Map<String, Object> map, String key, Long defaultVal) {
		Object object = get(map, key);
		return object == null ? defaultVal : (object instanceof Number) ? ((Number) object).longValue() : Long.parseLong(object.toString());
	}
	
	public static long getLong(Dictionary map, String key, Long defaultVal) {
		Object object = get(map, key);
		return object == null ? defaultVal : (object instanceof Number) ? ((Number) object).longValue() : Long.parseLong(object.toString());
	}
}