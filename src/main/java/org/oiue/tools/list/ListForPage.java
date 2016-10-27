package org.oiue.tools.list;

import java.util.List;

public class ListForPage {

	/**
	 * java二维数组转js数组
	 * @param javaList
	 * @param jsObjName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String List2JsArray(List javaList,String jsObjName ) {
		StringBuffer sbJs=new StringBuffer();
		sbJs.append("var "+jsObjName+"=new Array(");
		for(int i=0;i<javaList.size();i++){
			sbJs.append("new Array(");
			
			List list=(List)javaList.get(i);
			for(int j=0;j<list.size();j++){
				sbJs.append("\""+(String)list.get(j)+"\"");
				sbJs.append(j==list.size()-1?"":",");
			}
			sbJs.append(")");
			sbJs.append(i==javaList.size()-1?"":",");
		}

		sbJs.append(");");
		return sbJs.toString();
	}
}
