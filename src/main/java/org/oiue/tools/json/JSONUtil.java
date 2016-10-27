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

@SuppressWarnings({"unchecked","rawtypes"})
public class JSONUtil {

	public static List<Object> toList(JSONArray array) {
		List<Object> result = new ArrayList<Object>();
		if (array == null) {
			return result;
		}
		for (int i = 0; i < array.length(); i++) {
			Object value = array.opt(i);
			if (value instanceof JSONObject) {
				result.add(toMap((JSONObject) value));
			} else if (value instanceof JSONArray) {
				result.add(toList((JSONArray) value));
			} else {
				if (value.toString().startsWith("{")) {
					result.add(parserToMap(new JSONObject(value.toString())));
				} else if (value.toString().startsWith("[")) {
					result.add(parserToList(new JSONArray(value.toString())));
				} else
					result.add(value);
			}
		}
		return result;
	}

	public static Map<String, Object> toMap(JSONObject json) {
		Map<String, Object> result = new HashMap<String, Object>();
		if ((json == null) || (json.length() == 0)) {
			return result;
		}
		for (String e : JSONObject.getNames(json)) {
			Object value = json.opt(e);
			if (value instanceof JSONObject) {
				result.put(e, toMap((JSONObject) value));
			} else if (value instanceof JSONArray) {
				result.put(e, toList((JSONArray) value));
			} else {
				if (value.toString().startsWith("{")) {
					result.put(e, parserToMap(new JSONObject(value.toString())));
				} else if (value.toString().startsWith("[")) {
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
	 * @param json
	 * @return
	 */
	public static Map<?, Object> parserToMap(JSONObject json) {
		return JSONUtil.toMap(json);
	}

	/**
	 * 将JSONArray转换成list
	 * 
	 * @param json
	 * @return
	 */
	public static List<?> parserToList(JSONArray json) {
		return JSONUtil.toList(json);
	}

	public static Map<?, Object> parserStrToMap(String jsonStr) throws JSONException {
		return parserToMap(new JSONObject(jsonStr));
	}

	public static List<?> parserStrToList(String jsonAry) throws JSONException {
		return parserToList(new JSONArray(jsonAry));
	}

	public static String parserToStr(Map<Object, Object> map) {
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
	        
	        if(object instanceof List<?>){
	            stringBuffer.append(ListUtil.toString((List) object));
	        }else if (object instanceof Iterable<?>) {
//	          boolean first = true;
//	          stringBuffer.append("[");
//	          for (Object obj : (Iterable<?>) object) {
//	              if (first) {
//	                  first = false;
//	              } else {
//	                  stringBuffer.append(",");
//	              }
//	              if (obj instanceof String) {
//	                  stringBuffer.append(getJSONString((String) obj));
//	              } else {
//	                  stringBuffer.append(obj);
//	              }
//	          }
//	          stringBuffer.append("]");
	            stringBuffer.append(JSONUtil.parserToStr((List)object));
	        } else if (object instanceof Map<?, ?>) {
//	          Map<?, ?> hashMap = (Map<?, ?>) object;
//	          boolean first = true;
//	          stringBuffer.append("{");
//	          for (Object obj : hashMap.keySet()) {
//	              if (first) {
//	                  first = false;
//	              } else {
//	                  stringBuffer.append(",");
//	              }
//	              if (obj instanceof String) {
//	                  stringBuffer.append(getJSONString((String) obj));
//	              } else {
//	                  stringBuffer.append(obj);
//	              }
//	              stringBuffer.append(":");
//	              stringBuffer.append(hashMap.get(obj));
//	          }
//	          stringBuffer.append("}");
//	          stringBuffer.append(JSONUtil.parserToStr((Map)object));
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
