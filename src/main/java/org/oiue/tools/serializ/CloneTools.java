/**
 * 
 */
package org.oiue.tools.serializ;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @ClassName: CloneTools 
 * @Description: <B>类说明:</B><br/>
 * <pre>
 * 
 * </pre>
 * @author Every(王勤)
 * @version 
 * @date Mar 24, 2011 7:01:51 PM 
 */
@SuppressWarnings("serial")
public class CloneTools implements Serializable {
	Object o;

	public Object getO() {
		return o;
	}

	public void setO(Object o) {
		this.o = o;
	}
	
	/**
	 * Method 复制对象
	 */
	public Object deepCopy() throws Throwable {
		// 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(this);
		// 将流序列化成对象
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		bos=null;
		oos=null;
		bis=null;
		return ois.readObject();
	}
	
	/**
	 * 底层克隆对象
	 * 利用序列化反序列化深层克隆对象
	 * @param o
	 * @return
	 * @throws Throwable
	 */
	public static Object clone(Object o) throws Throwable{
		CloneTools clone = new CloneTools();
		clone.setO(o);
		return ((CloneTools)clone.deepCopy()).getO();
	}
}
