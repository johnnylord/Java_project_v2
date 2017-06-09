package event;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class API {
/*
	public void callAPI() {
	    try {
	    	Class c = getClass("event.EventManager");
			Class[] oParam = new Class[0];
			Constructor constructor = c.getConstructor(oParam);
			Object[] paramObjs = new Object[0];
			Object obj = constructor.newInstance(paramObjs);
	        System.out.println(obj);
	        
	        Class[] mParam = new Class[0];
			Method setName = c.getMethod("route", mParam);
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
	
	public static Class getClass(String classname) throws ClassNotFoundException {
		Class c = null;
		c = Class.forName(classname);
		return c;
	}

	public static Class getObjectById(String classname, int id) {
		Class c = getClass(classname);
		return c;
	}*/
}
