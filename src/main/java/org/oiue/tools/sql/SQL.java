package org.oiue.tools.sql;

import java.io.Serializable;
import java.util.Collection;

public class SQL implements Serializable {
	private static final long serialVersionUID = 1519346971295873784L;
	public String sql;
	public Collection<Object> pers;
}
