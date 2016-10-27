package org.json;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

@SuppressWarnings({ "rawtypes", "serial","unchecked" })
public class JSONArray extends ArrayList{

    public JSONArray() {
        super();
    }
	public JSONArray(String jsonAry) throws JSONException{
		super(JSON.parseArray(jsonAry));
	}

	public JSONArray(List<?> list) {
		super(list);
	}

	public int length() {
		return this.size();
	}

	public Object opt(int i) {
		return this.get(i);
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
	 public JSONArray put(Object value) {
	     super.add(value);
	     return this;
	 }
}
