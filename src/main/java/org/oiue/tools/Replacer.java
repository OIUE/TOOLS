package org.oiue.tools;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@SuppressWarnings("serial")
public class Replacer  implements Serializable{
	public static final String replace(String regex, Map<String, Object> map) {
		if (map != null) {
			for (Entry<String, Object> e : map.entrySet()) {
				regex = regex.replace("${" + e.getKey() + "}", e.getValue() == null ? "" : e.getValue().toString());
			}
		}
		return regex;
	}

	public static final String replace(String regex, List<Object> list) {
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				regex = regex.replace("${" + (i + 1) + "}", list.get(i) == null ? "" : list.get(i).toString());
			}
		}
		return regex;
	}
}
