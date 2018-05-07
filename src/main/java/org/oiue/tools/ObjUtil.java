package org.oiue.tools;

public class ObjUtil {
	public static Double toDouble(Object object) {
		return object instanceof Number ? ((Number) object).doubleValue() : Double.parseDouble(object.toString());
	}
	
	public static int toInt(Object object) {
		return object instanceof Number ? ((Number) object).intValue() : Integer.parseInt(object.toString());
	}
	
	public static long toLong(Object object) {
		return object instanceof Number ? ((Number) object).longValue() : Long.parseLong(object.toString());
	}
	
	public static String toString(Object object) {
		return object == null ? null : object.toString();
	}
	
	public static boolean toBoolean(Object object) {
		if (object.equals(Boolean.TRUE) || (object instanceof String && (((String) object).equalsIgnoreCase("true")) || ((String) object).equalsIgnoreCase("1")) || ((object instanceof Number) && (((Number) object).intValue() == 1))) {
			return true;
		}
		return false;
	}
}
