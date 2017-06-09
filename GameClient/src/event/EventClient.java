package event;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventClient {
	
	/*protected socket;
	protected queue;
	protected returns;
	public send(event object);
	public addListener(api, func);*/
	
	protected static Map<String, Object> references = new ConcurrentHashMap<String, Object>();
	
	public static void callAPI(String classname, String methodname) {
	    try {
	    	Class c = getClass(classname);
			Class[] oParam = new Class[0];
			Constructor constructor = c.getConstructor(oParam);
			Object[] paramObjs = new Object[0];
			Object obj = constructor.newInstance(paramObjs);
	        System.out.println(obj);
	        
	        Class[] mParam = new Class[0];
			Method setName = c.getMethod(methodname, mParam);
			Object[] mParamObjs = new Object[0];
			setName.invoke(obj, mParamObjs);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public static void callMethod(Object obj, String methodname) {
		try {
			Class[] mParam = new Class[0];
			Method setName;
			setName = obj.getClass().getMethod(methodname, mParam);
			Object[] mParamObjs = new Object[0];
			setName.invoke(obj, mParamObjs);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO 自動產生的 catch 區塊
			e.printStackTrace();
		}
	}
	
	public static Class getClass(String classname) throws ClassNotFoundException {
		Class c = null;
		c = Class.forName(classname);
		return c;
	}

	public static Object getObject(String key) {
		return references.get(key);
	}
	
	public static Object getObjectById(String classname, int id) {
		return references.get(classname + "@" + id);
	}
	
	public static String addReference(Object obj) {
		String key = obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
		references.put(key, obj);
		return key;
	}
}
