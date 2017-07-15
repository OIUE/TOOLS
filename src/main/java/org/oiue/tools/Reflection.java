package org.oiue.tools;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Java Reflection 
 *
 * @author Every(王勤)
 *  2008-8-23
 *  v0.1
 */
@SuppressWarnings( { "unchecked", "serial" ,"rawtypes"})
public class Reflection implements Serializable {

	/**
	 * 执行某类的静态方法
	 * @param className 类名
	 * @param methodName  方法名
	 * @param args 参数数组
	 * @return 执行方法返回的结果
	 * @throws Exception 异常
	 */
	public Object invokeStaticMethod(String className, String methodName, Object[] args)
			throws Exception {
		Class ownerClass = Class.forName(className);
		Class[] argsClass = null;
		if (args == null) {
			argsClass = new Class[0];
		} else {
			argsClass = new Class[args.length];
		}
		for (int i = 0, j = args.length; i < j; i++) {
			if (args[i] != null) {
				argsClass[i] = args[i].getClass();
			}
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(ownerClass.newInstance(), args);
	}

	/**
	 * 新建实例
	 * @param className 类名
	 * @param args 构造函数的参数
	 * @return 新建的实例
	 * @throws Exception 异常
	 */
	public Object newInstance(String className, Object... args) throws Exception {
		Class newoneClass = Class.forName(className);
		Class[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Constructor cons = newoneClass.getConstructor(argsClass);
		return cons.newInstance(args);
	}

	/**
	 * 执行某对象方法
	 * @param owner  对象
	 * @param methodName  方法名
	 * @param args   参数
	 * @return 方法返回值
	 * @throws Exception 异常
	 */
	public Object invokeMethod(Object owner, String methodName, Object[] args)
			throws Exception {
		Class ownerClass = owner.getClass();
		Class[] argsClass = null;
		if (args == null) {
			argsClass = new Class[0];
		} else {
			argsClass = new Class[args.length];
		}
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}
	/**
	 * 执行某对象方法
	 * @param owner  对象
	 * @param methodName  方法名
	 * @param args   参数
	 * @param argsClass   参数
	 * @return 方法返回值
	 * @throws Exception 异常
	 */
	public Object invokeMethod(Object owner, String methodName, Object[] args,Class[] argsClass) throws Exception {
		Class ownerClass = owner.getClass();
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}

	/**
	 * 得到某个对象的公共属性
	 * @param owner owner
	 * @param fieldName fieldName
	 * @return 该属性对象
	 * @throws Exception 异常
	 */
	public Object getProperty(Object owner, String fieldName) throws Exception {
		Class ownerClass = owner.getClass();
		Field field = ownerClass.getField(fieldName);
		Object property = field.get(owner);
		return property;
	}

	/**
	 * 得到某类的静态公共属性
	 * @param className   类名
	 * @param fieldName   属性名
	 * @return 该属性对象
	 * @throws Exception 异常
	 */
	public Object getStaticProperty(String className, String fieldName)
			throws Exception {
		Class ownerClass = Class.forName(className);
		Field field = ownerClass.getField(fieldName);
		Object property = field.get(ownerClass);
		return property;
	}

	/**
	 * 判断是不是某个类的实例
	 * @param obj 实例
	 * @param cls 类
	 * @return 如果 obj 是此类的实例，则返回 true
	 */
	public boolean isInstance(Object obj, Class cls) {
		return cls.isInstance(obj);
	}

	/**
	 * 得到数组中的某个元素
	 * @param array 数组
	 * @param index 索引
	 * @return 返回指定数组对象中索引组件的值
	 */
	public Object getByArray(Object array, int index) {
		return Array.get(array, index);
	}
}