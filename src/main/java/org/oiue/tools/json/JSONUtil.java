package org.oiue.tools.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.oiue.tools.list.ListUtil;
import org.oiue.tools.map.MapUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class JSONUtil {
	
	public static List<Object> toList(JSONArray array) {
		return toList(array, true);
	}
	
	public static List<Object> toList(JSONArray array, boolean force) {
		List<Object> result = new ArrayList<Object>();
		if (array == null) {
			return result;
		}
		for (int i = 0; i < array.length(); i++) {
			Object value = array.opt(i);
			if (value instanceof JSONObject) {
				result.add(toMap((JSONObject) value, force));
			} else if (value instanceof JSONArray) {
				result.add(toList((JSONArray) value, force));
			} else {
				if (value.toString().startsWith("{") && force) {
					result.add(parserToMap(new JSONObject(value.toString())));
				} else if (value.toString().startsWith("[") && force) {
					result.add(parserToList(new JSONArray(value.toString())));
				} else
					result.add(value);
			}
		}
		return result;
	}
	
	public static Map<String, Object> toMap(JSONObject json) {
		return toMap(json, true);
	}
	
	public static Map<String, Object> toMap(JSONObject json, boolean force) {
		Map<String, Object> result = new HashMap<String, Object>();
		if ((json == null) || (json.length() == 0)) {
			return result;
		}
		for (String e : JSONObject.getNames(json)) {
			Object value = json.opt(e);
			if (value instanceof JSONObject) {
				result.put(e, toMap((JSONObject) value, force));
			} else if (value instanceof JSONArray) {
				result.put(e, toList((JSONArray) value, force));
			} else {
				if (value == null) {
					result.put(e, value);
				} else if (value.toString().startsWith("{") && force) {
					result.put(e, parserToMap(new JSONObject(value.toString())));
				} else if (value.toString().startsWith("[") && force) {
					result.put(e, parserToList(new JSONArray(value.toString())));
				} else
					result.put(e, value);
			}
		}
		return result;
	}
	
	/**
	 * 将JSONObject对象转换成map
	 *
	 * @param json 对象
	 * @return map
	 */
	public static Map<?, Object> parserToMap(JSONObject json) {
		return parserToMap(json,true);
	}
	public static Map<?, Object> parserToMap(JSONObject json, boolean force) {
		return JSONUtil.toMap(json,force);
	}
	
	/**
	 * 将JSONArray转换成list
	 *
	 * @param json 对象
	 * @return list
	 */
	public static List<?> parserToList(JSONArray json) {
		return parserToList(json, true);
	}
	
	public static List<?> parserToList(JSONArray json, boolean force) {
		return JSONUtil.toList(json, force);
	}
	
	public static Map<?, Object> parserStrToMap(String jsonStr) throws JSONException {
		return parserStrToMap(jsonStr, true);
	}
	public static Map<?, Object> parserStrToMap(String jsonStr, boolean force) throws JSONException {
		try {
			return parserToMap(new JSONObject(jsonStr),force);
		} catch (Throwable e) {
			GsonBuilder gb = new GsonBuilder();
			Gson g = gb.create();
			Map map = g.fromJson(jsonStr, new TypeToken<Map>() {}.getType());
			return map;
		}
	}
	
	public static List<?> parserStrToList(String jsonAry) throws JSONException {
		return parserStrToList(jsonAry, true);
	}
	
	public static List<?> parserStrToList(String jsonAry, boolean force) throws JSONException {
		return parserToList(new JSONArray(jsonAry), force);
	}
	
	public static String parserToStr(Map map) {
		return new JSONObject(map).toString();
	}
	
	public static String parserToStr(List list) {
		return new JSONArray(list).toString();
	}
	
	public static String getJSONString(String string) {
		if (string == null || string.length() == 0) {
			return "\"\"";
		}
		
		if (string.startsWith("{") || string.startsWith("[")) {
			return string;
		}
		
		char b;
		char c = 0;
		int len = string.length();
		StringBuffer sb = new StringBuffer(len + 4);
		String t;
		
		sb.append('"');
		for (int i = 0; i < len; i += 1) {
			b = c;
			c = string.charAt(i);
			switch (c) {
				case '\\':
				case '"':
					sb.append('\\');
					sb.append(c);
					break;
				case '/':
					if (b == '<') {
						sb.append('\\');
					}
					sb.append(c);
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\t':
					sb.append("\\t");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\r':
					sb.append("\\r");
					break;
				default:
					if (c < ' ' || (c >= '\u0080' && c < '\u00a0') || (c >= '\u2000' && c < '\u2100')) {
						t = "000" + Integer.toHexString(c);
						sb.append("\\u" + t.substring(t.length() - 4));
					} else {
						sb.append(c);
					}
			}
		}
		sb.append('"');
		return sb.toString();
	}
	
	public static String getJSONString(Object object) {
		
		StringBuffer stringBuffer = new StringBuffer();
		
		if (object instanceof List<?>) {
			stringBuffer.append(ListUtil.toString((List) object));
		} else if (object instanceof Iterable<?>) {
			// boolean first = true;
			// stringBuffer.append("[");
			// for (Object obj : (Iterable<?>) object) {
			// if (first) {
			// first = false;
			// } else {
			// stringBuffer.append(",");
			// }
			// if (obj instanceof String) {
			// stringBuffer.append(getJSONString((String) obj));
			// } else {
			// stringBuffer.append(obj);
			// }
			// }
			// stringBuffer.append("]");
			stringBuffer.append(JSONUtil.parserToStr((List) object));
		} else if (object instanceof Map<?, ?>) {
			// Map<?, ?> hashMap = (Map<?, ?>) object;
			// boolean first = true;
			// stringBuffer.append("{");
			// for (Object obj : hashMap.keySet()) {
			// if (first) {
			// first = false;
			// } else {
			// stringBuffer.append(",");
			// }
			// if (obj instanceof String) {
			// stringBuffer.append(getJSONString((String) obj));
			// } else {
			// stringBuffer.append(obj);
			// }
			// stringBuffer.append(":");
			// stringBuffer.append(hashMap.get(obj));
			// }
			// stringBuffer.append("}");
			// stringBuffer.append(JSONUtil.parserToStr((Map)object));
			stringBuffer.append(MapUtil.toString((Map) object));
		} else if (object instanceof String) {
			stringBuffer.append("{\"v\":");
			stringBuffer.append(getJSONString((String) object));
			stringBuffer.append("}");
		} else {
			if (object != null) {
				stringBuffer.append(object);
			} else {
				stringBuffer.append("{\"v\":null}");
			}
		}
		stringBuffer.trimToSize();
		return stringBuffer.toString();
	}
	
}
