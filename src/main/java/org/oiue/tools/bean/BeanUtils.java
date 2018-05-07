package org.oiue.tools.bean;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.oiue.tools.StatusResult;
import org.oiue.tools.exception.OIUEException;

/**
 * @author VoLand URL:http://www.voland.com.cn/javabean-properties-to-achieve-high -performance-replication-tool-beanutils/comment-page-1#comment-2910
 */
@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
public class BeanUtils {
	
	private static HashMap<Class, Method[]> beanMethodCache = new HashMap<Class, Method[]>();
	private static HashMap<Class, HashMap<String, Method>> fromBeanMethodCache = new HashMap<Class, HashMap<String, Method>>();
	
	public static void copyProperties(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
		Class[] classes = new Class[0];
		Object[] objects = new Object[0];
		try {
			Class beanClass = dest.getClass();
			Class formBeanClass = orig.getClass();
			
			Method[] beanMethods = beanMethodCache.get(beanClass);
			if (beanMethods == null) {
				beanMethods = beanClass.getMethods();
				beanMethodCache.put(beanClass, beanMethods);
			}
			
			HashMap<String, Method> fromBeanMethods = fromBeanMethodCache.get(formBeanClass);
			if (fromBeanMethods == null) {
				fromBeanMethods = new HashMap<String, Method>();
				Method[] methods = formBeanClass.getMethods();
				String getMethodName = null;
				for (Method method : methods) {
					getMethodName = method.getName();
					if (getMethodName.startsWith("get") || getMethodName.startsWith("is"))
						fromBeanMethods.put(getMethodName, method);
				}
				fromBeanMethodCache.put(formBeanClass, fromBeanMethods);
			}
			
			String methodName = null;
			String getMethodName = null;
			Class[] paramsType = null;
			Class paramType = null;
			for (Method method : beanMethods) {
				
				methodName = method.getName();
				if (methodName.startsWith("set")) {
					paramsType = method.getParameterTypes();
					if (paramsType.length != 1)
						continue;
					
					paramType = paramsType[0];
					if (paramType.isInstance(boolean.class)) {
						getMethodName = "is" + methodName.substring(3);
					} else {
						getMethodName = "get" + methodName.substring(3);
					}
					Method formBeanGetMethod = fromBeanMethods.get(getMethodName);
					if (formBeanGetMethod != null) {
						if (paramType.equals(formBeanGetMethod.getReturnType())) {
							Object value = formBeanGetMethod.invoke(orig, objects);
							if (value != null) {
								method.invoke(dest, new Object[] { value });
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * JavaBean属性拷贝扩展方法 支持类型转换
	 * @author Every
	 * @param target 拷贝对象
	 * @param source 源对象
	 */
	public static void copyPropertiesExt(Object target, Object source) {
		/**
		 * 分别获得源对象和目标对象的Class类型对象,Class对象是整个反射机制的源头和灵魂！ Class对象是在类加载的时候产生,保存着类的相关属性，构造器，方法等信息
		 */
		Class sourceClz = source.getClass();
		Class targetClz = target.getClass();
		// 得到Class对象所表征的类的所有属性(包括私有属性)
		Field[] srcFields = sourceClz.getDeclaredFields();
		Field[] tarFields = sourceClz.getDeclaredFields();
		boolean initiative = srcFields.length > tarFields.length;
		Field[] fields = initiative ? tarFields : srcFields;
		
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			Field passivityField = null;
			try {
				passivityField = (initiative ? sourceClz : targetClz).getDeclaredField(fieldName);
			} catch (SecurityException e) {
				e.printStackTrace();
				break;
			} catch (NoSuchFieldException e) {
				continue;
			}
			
			if (fields[i].getType() == passivityField.getType()) {
				// 由属性名字得到对应get和set方法的名字
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				// 由方法的名字得到get和set方法的Method对象
				Method getMethod;
				try {
					getMethod = sourceClz.getDeclaredMethod(getMethodName, new Class[] {});
					Method setMethod = targetClz.getDeclaredMethod(setMethodName, fields[i].getType());
					// 调用source对象的getMethod方法
					Object result = getMethod.invoke(source, new Object[] {});
					// 调用target对象的setMethod方法
					setMethod.invoke(target, result);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			} else {
				throw new OIUEException(StatusResult._blocking_errors, "同名属性类型不匹配！");
			}
		}
	}
	
}