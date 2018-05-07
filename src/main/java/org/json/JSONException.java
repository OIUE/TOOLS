package org.json;

import java.io.IOException;

public class JSONException extends RuntimeException {
	public JSONException(String string) {
		super(string);
	}
	
	public JSONException(IOException e) {
		super(e);
	}
	
	private static final long serialVersionUID = 1L;
}
