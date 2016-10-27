/**
 * 
 */
package org.oiue.tools;

import java.io.Serializable;

/**
 * @author Every(王勤)
 *
 */
public interface ITask extends Serializable {

	/**
	 * 调用方法
	 * @param objects
	 * @return
	 */
	public Object invoke(Object...objects);
	
	/**
	 * 清除此方法
	 */
	public void clear();
}
