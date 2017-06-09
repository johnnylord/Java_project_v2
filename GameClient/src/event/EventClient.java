package event;

import java.io.*;
import java.net.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventClient {
	
	protected static Map<String, Object> references = new ConcurrentHashMap<String, Object>();
	protected static Map<String, Object> namedReferences = new ConcurrentHashMap<String, Object>();
	@SuppressWarnings("rawtypes")
	protected static Map<String, Function> listeners = new ConcurrentHashMap<String, Function>();
	protected static Socket connectionSock;
	protected static ObjectInputStream input;
	protected static ObjectOutputStream output;
	protected static String thisKey = null;
	protected static int lastRequsetKey = -1;
	
	public static void initialize(String ip, int port) {
		try {
			// Connecting to server on port 9487
			connectionSock = new Socket(ip, port);
			input = new ObjectInputStream(connectionSock.getInputStream());
			output = new ObjectOutputStream(connectionSock.getOutputStream());
			// Connection made, sending name
			System.out.println("connected ...");
			while (true) {
				Object[] returnObject = (Object[]) input.readObject();
				if (returnObject != null && (boolean)returnObject[0] == true) {
					thisKey = returnObject[1].toString();
					break;
				}
			}
			
			Receiver receiver = new Receiver(input, output);
			Thread receiverThread = new Thread(receiver);
			receiverThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void initialize(String ip) {
		initialize(ip, 9487);
	}
	public static void initialize() {
		initialize("127.0.0.1");
	}
	
	public static String getKey() {
		return thisKey;
	}
	@SuppressWarnings("rawtypes")
	public static Function getListener(String id) {
		return listeners.get(id);
	}
	public static boolean hasListener(String id) {
		return listeners.containsKey(id);
	}
	
	@SuppressWarnings("rawtypes")
	public static void send(Function onReturn, String api, Object args, String to) {
		if(thisKey == null) initialize();
		try {
			String id = String.valueOf(++lastRequsetKey);
			if(onReturn != null) listeners.put(id, onReturn);
			// Connection made
			Object[] obj = new Object[]{id, api, args, to, thisKey};
			output.writeObject(obj);
		} catch (Exception e) { e.printStackTrace(); }
	}
	@SuppressWarnings("rawtypes")
	public static void send(Function onReturn, String api, String to) {
		send(onReturn, api, new Object[0], to);
	}
	public static void send(String api, Object args, String to) {
		send(null, api, args, to);
	}
	public static void send(String api, String to) {
		send(null, api, new Object[0], to);
	}
	
	@SuppressWarnings("rawtypes")
	public static void sendAll(Function onReturn, String api, Object args) {
		if(thisKey == null) initialize();
		try {
			String id = String.valueOf(++lastRequsetKey);
			if(onReturn != null) listeners.put(id, onReturn);
			// Connection made
			Object[] obj = new Object[]{id, api, args, true, thisKey};
			output.writeObject(obj);
		} catch (Exception e) { e.printStackTrace(); }
	}
	@SuppressWarnings("rawtypes")
	public static void sendAll(Function onReturn, String api) {
		sendAll(onReturn, api, new Object[0]);
	}
	public static void sendAll(String api, Object args) {
		sendAll(null, api, args);
	}
	public static void sendAll(String api) {
		sendAll(null, api, new Object[0]);
	}
	
	@SuppressWarnings("rawtypes")
	public static void sendOthers(Function onReturn, String api, Object args) {
		if(thisKey == null) initialize();
		try {
			String id = String.valueOf(++lastRequsetKey);
			if(onReturn != null) listeners.put(id, onReturn);
			// Connection made
			Object[] obj = new Object[]{id, api, args, false, thisKey};
			output.writeObject(obj);
		} catch (Exception e) { e.printStackTrace(); }
	}
	@SuppressWarnings("rawtypes")
	public static void sendOthers(Function onReturn, String api) {
		sendOthers(onReturn, api, new Object[0]);
	}
	public static void sendOthers(String api, Object args) {
		sendOthers(null, api, args);
	}
	public static void sendOthers(String api) {
		sendOthers(null, api, new Object[0]);
	}
	
	public static String addReference(Object obj) {
		String key = obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode());
		references.put(key, obj);
		return key;
	}
	public static String addReference(Object obj, String name) {
		addReference(obj);
		String key = obj.getClass().getName() + ":" + name;
		namedReferences.put(key, obj);
		return key;
	}
	
	public static String getReference(Object value) {
		for (String o : references.keySet()) {
			if (references.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}
	public static String getNameReference(Object value, String name) {
		for (String o : namedReferences.keySet()) {
			if (namedReferences.get(o).equals(value)) {
				return o;
			}
		}
		return addReference(value, name);
	}
	public static String getNameReference(Object value) {
		for (String o : namedReferences.keySet()) {
			if (namedReferences.get(o).equals(value)) {
				return o;
			}
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getClass(String classname) throws ClassNotFoundException {
		Class c = null;
		c = Class.forName(classname);
		return c;
	}

	public static Object getObject(String key) {
		return references.get(key);
	}
	public static Object getObjectById(String classname, int id) {
		return references.get(classname + "@" + Integer.toHexString(id));
	}
	public static Object getObjectByName(String classname, String name) {
		return namedReferences.get(classname + ":" + name);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object callMethod(Object obj, String methodname, Object[] args) {
		//System.out.println(methodname);
		Object returnValue = null;
		try {
			Class[] param = new Class[args.length];
			for(int i = 0; i < args.length; i++) {
				param[i] = args.getClass();
			}
			Method method = obj.getClass().getMethod(methodname, param);
			returnValue = method.invoke(obj, args);
		} catch (NoSuchMethodException e) {
			Method methods[] = obj.getClass().getMethods();
	         for(int i = 0; i < methods.length; i++) {
	            if(methods[i].getName().equals(methodname) && methods[i].getParameterCount() == args.length) {
	            	boolean match = true;
	            	for(int j = 0; j < args.length; j++) {
	    				Class argClass = args[j].getClass();
	            		Class paramClass = methods[i].getParameterTypes()[j];
	    				if(!paramClass.isAssignableFrom(argClass)) {
	    					match = false;
	    					break;
	    				}
	    			}
	            	if(match) {
	            		Method method = null;
						try {
							method = obj.getClass().getMethod(methodname, methods[i].getParameterTypes());
						} catch (NoSuchMethodException e1) {
							e1.printStackTrace();
						} catch (SecurityException e1) {
							e1.printStackTrace();
						}
	        			try {
	        				returnValue = method.invoke(obj, args);
						} catch (IllegalAccessException e1) {
							e1.printStackTrace();
						} catch (IllegalArgumentException e1) {
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							e1.printStackTrace();
						}
	            	}
	            }
	         }
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	public static Object callMethod(Object obj, String methodname) {
		return callMethod(obj, methodname, new Object[0]);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object callStaticMethod(Class cla, String methodname, Object[] args) {
		Object returnValue = null;
		try {
			Class[] param = new Class[args.length];
			for(int i = 0; i < args.length; i++) {
				param[i] = args[i].getClass();
			}
			Method method = cla.getMethod(methodname, param);
			returnValue = method.invoke(null, args);
		} catch (NoSuchMethodException e) {
			Method methods[] = cla.getMethods();
	         for(int i = 0; i < methods.length; i++) {
	        	 if(methods[i].getName().equals(methodname) && methods[i].getParameterCount() == args.length) {
	        		boolean match = true;
	            	for(int j = 0; j < args.length; j++) {
	    				Class argClass = args[j].getClass();
	            		Class paramClass = methods[i].getParameterTypes()[j];
	    				if(!paramClass.isAssignableFrom(argClass)) {
	    					match = false;
	    					break;
	    				}
	    			}
	            	if(match) {
	            		Method method = null;
						try {
							method = cla.getMethod(methodname, methods[i].getParameterTypes());
						} catch (NoSuchMethodException e1) {
							e1.printStackTrace();
						} catch (SecurityException e1) {
							e1.printStackTrace();
						}
	        			try {
	        				returnValue = method.invoke(null, args);
						} catch (IllegalAccessException e1) {
							e1.printStackTrace();
						} catch (IllegalArgumentException e1) {
							e1.printStackTrace();
						} catch (InvocationTargetException e1) {
							e1.printStackTrace();
						}
	            	}
	            }
	         }
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	@SuppressWarnings("rawtypes")
	public static Object callStaticMethod(Class cla, String methodname) {
		return callStaticMethod(cla, methodname, new Object[0]);
	}

	public static Object callAPI(String api, Object args) {
		Object response = null;
		
		Map<String, String> match = parseCallable(api);
		if(match.isEmpty()) return null;
		if(match.containsKey("class") && match.get("class").equals("")) {
			final StackTraceElement[] ste = new Throwable().getStackTrace();
			match.put("class", ste[ste.length - 1].getClassName());
		}
		
		Object callable = null;
		boolean isObject = false;
		
		if(match.containsKey("type") && match.get("type").equals(":")) { //find by name or array
			isObject = true;
			String name = callBinding(match, "name", args).toString();
			callable = getObjectByName(match.get("class"), name);
		}
		else if(match.containsKey("type") && match.get("type").equals("::")) { // class static function
			try {
				String ARGS = "";
				if(match.containsKey("args")) ARGS = match.get("args");
				callable = callStaticMethod(Class.forName(match.get("class")),
											match.get("func"),
											callArgs(ARGS, args));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		else if(match.containsKey("type") && match.get("type").equals("@")) { //find by id
			isObject = true;
			int id = Integer.parseInt((String)callBinding(match, "id", args), 16);
			
			callable = getObjectById(match.get("class"), id);
		}
		
		if(callable != null && match.containsKey("left")) {
			if(match.get("left").equals("") && isObject) response = callRun(callable, "::view()", args);
			else response = callRun(callable, match.get("left"), args);
		}
		
		return response;
	}
	public static Object callAPI(String api) {
		return callAPI(api, new Object[0]);
	}
	public static Object callAPI(Object obj, String api, Object args) {
		return callAPI(EventClient.getReference(obj)+api, args);
	}
	public static Object callAPI(Object obj, String api) {
		return callAPI(obj, api, new Object[0]);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object[] callArgs(String str, Object args) {
		ArrayList<Map<String, String>> matches = parseArgs(str);
		ArrayList arr = new ArrayList();
		
		for(Map<String, String> arg: matches)
		{
			if(arg.containsKey("var") && arg.get("var").equals("...")) {
				Collection al = new ArrayList();
				if(args instanceof Object[]) for(Object o: (Object[])args) al.add(o);
				else if(args instanceof Collection) al = (Collection)args;
				else al = Arrays.asList(args);
				arr.addAll(al);
			}
			else arr.add(callBinding(arg, "value", args));
		}
		return arr.toArray();
	}
	@SuppressWarnings("rawtypes")
	public static Object callBinding(Map<String, String> match, String self, Object args) {
		Object returnValue = null;
		if(match.containsKey("bind")) returnValue = callAPI(match.get("bind"), args);
		else if(match.containsKey("var")) {
			String var = match.get("var");
			if(var.equals("")) {
				returnValue = args;
				
			}
			else if(match.containsKey("var")) {
				if(args instanceof Map) returnValue = ((Map)args).get(var);
				else if(args instanceof List) returnValue = ((List)args).get(Integer.parseInt(var));
				else if(args instanceof Object[]) returnValue = ((Object[])args)[Integer.parseInt(var)];
				else returnValue = args;
			}
		}
		else if(match.containsKey(self)) {
			try {
				JSONObject json = new JSONObject(match.get(self));
				returnValue = json;
			} catch(JSONException e) {
				try {
					JSONArray json = new JSONArray(match.get(self));
					returnValue = json;
				} catch(JSONException e1) {
					returnValue = JSONObject.stringToValue((String)match.get(self));
					if(returnValue instanceof String) {
						Matcher m = Pattern.compile("^\\\"(.*)\\\"$").matcher((String)returnValue);
						if(m.find()) returnValue = m.group(1);
					}
				}
			}
		}
		
		return returnValue;
	}
	@SuppressWarnings("rawtypes")
	public static Object callRun(Object callable, String str, Object args) {
		Object returnValue = null;
		Map<String, String> match = parseRun(str);
		if(match.isEmpty()) return callable;
		if(match.containsKey("func")) {
			returnValue = callRun(callMethod(callable, match.get("func"), callArgs(match.get("args"), args)),
					match.get("left"), args);
		}
		else if(match.containsKey("bind") || match.containsKey("var") || match.containsKey("offset")) {
			String offset = (String) callBinding(match, "offset", args);
			Object item = null;
			if(callable instanceof Map) item = ((Map)args).get(offset);
			else if(callable instanceof List) item = ((List)args).get(Integer.parseInt(offset));
			else if(callable instanceof Object[]) item = ((Object[])args)[Integer.parseInt(offset)];
			else item = callable;
			returnValue = callRun(item, match.get("left"), args);
		}
		else if(!match.get("left").equals("")) returnValue = callable;
		return returnValue;
	}
	
	public static Map<String, String> parseCallable(String str)
	{
		// 0(all): all
		// 1(class): namespace and class
		// 2(type): id, if id, gives @
		// 3(is_var): id, contains $ if var
		// 4(is_bind): id, is binding (check  { )
		// 5(bind): id, string in ${} (if is binding)
		// 6(var): id, string after $
		// 7(id): id, if no var
		// 8(type): if class static function, give ::
		// 9(func): class static function
		// 10(args): class static function arguments(can be var)
		// 11(type): name, if name, gives :
		// 12(is_var): name, contains $ if var
		// 13(is_bind): name, is binding (check  { )
		// 14(bind): name, string in ${} (if is binding)
		// 15(var): name, string after $
		// 16(name): name, if no var
		// 17(left): left str
		
		final String regex = "^([^\\[:@]*)(?:(?:(@)(?:\\s*(\\$)(?:(?:(\\{)(.*)\\})|([^\\[:]*))\\s*|([^@\\[:]*)))|(?:(::)([^\\(\\):]+)\\((.*)\\))|(?:(:)(?:\\s*(\\$)(?:(?:(\\{)(.*)\\})|([^\\[:]*))\\s*|([^\\[:]*))))(.*)";
		
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(str);
		
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		if(!matcher.find()) return map; // find the next subsequence of the input
		
		if(matcher.group(0) != null) {
			map.put("all", matcher.group(0));
			if(matcher.group(1) != null) map.put("class", matcher.group(1));
			if(matcher.group(2) != null) {
				map.put("type", matcher.group(2));
				if(matcher.group(3) != null) {
					if(matcher.group(4) != null) map.put("bind", matcher.group(5));
					else if(matcher.group(6) != null) map.put("var", matcher.group(6));
				}
				else map.put("id", matcher.group(7));
			}
			else if(matcher.group(11) != null) {
				map.put("type", matcher.group(11));
				if(matcher.group(12) != null) {
					if(matcher.group(13) != null) map.put("bind", matcher.group(14));
					else if(matcher.group(15) != null) map.put("var", matcher.group(15));
				}
				else map.put("name", matcher.group(16));
			}
			else if(matcher.group(8) != null) {
				map.put("type", matcher.group(8));
				map.put("func", matcher.group(9));
				map.put("args", matcher.group(10));
			}
			map.put("left", matcher.group(17));
		}
		
		return map;
	}
	public static Map<String, String> parseRun(String str)
	{
		// 0(all): all
		// 1(func): function name
		// 2(args): args(can be var)
		// 3(is_var): offset key, contains $ if var
		// 4(is_bind): offset key, is binding (check  { )
		// 5(bind): offset key, string in ${} (if is binding)
		// 6(var): offset key, string after $
		// 7(offset): offset key, if no var
		// 8(left): left str
		
		//System.out.println(str);
		final String regex = "^(?:(?:::([^\\(\\):]+)\\((.*)\\))|(?:\\[(?:\\s*(\\$)(?:(?:(\\{)(.*)\\})|(.*))\\s*|(.*))\\]))(.*)";
		
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(str);
		
		Map<String, String> map = new ConcurrentHashMap<String, String>();
		if(!matcher.find()) return map; // find the next subsequence of the input
		
		if(matcher.group(0) != null) {
			map.put("all", matcher.group(0));
			if(matcher.group(1) != null) {
				map.put("func", matcher.group(1));
				map.put("args", matcher.group(2));
			}
			else {
				if(matcher.group(3) != null) {
					if(matcher.group(4) != null) map.put("bind", matcher.group(5));
					else if(matcher.group(6) != null) map.put("var", matcher.group(6));
				}
				else map.put("offset", matcher.group(7));
			}
			map.put("left", matcher.group(8));
		}
		
		return map;
	}
	public static ArrayList<Map<String, String>> parseArgs(String str)
	{
		// 0(all): all
		// 1(is_var): contains $
		// 2(is_bind): is binding (check  { )
		// 3(bind): string in ${} (if is binding)
		// 4(var): string after $
		// 5(value): pure value
		
		final String regex = "\\s*(\\$)(?:(?:(\\{)([^,]*)\\})|([^,]*))\\s*(?:,|$)|\\s*([^\\$]+?)\\s*(?:,|$)";
		
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(str);
		
		ArrayList<Map<String, String>> maps = new ArrayList<Map<String, String>>();
		while(matcher.find()) { // find the next subsequence of the input
			Map<String, String> map = new ConcurrentHashMap<String, String>();
			
			if(matcher.group(0) != null) {
				map.put("all", matcher.group(0));
				if(matcher.group(1) != null) {
					if(matcher.group(2) != null) map.put("bind", matcher.group(3));
					else if(matcher.group(4) != null) map.put("var", matcher.group(4));
				}
				else map.put("value", matcher.group(5));
			}
			maps.add(map);
		}
		
		return maps;
	}
	
}

class Receiver implements Runnable {
	private ObjectInputStream input;
	private ObjectOutputStream output;
	Receiver(ObjectInputStream input, ObjectOutputStream output) {
		this.input = input;
		this.output = output;
	}
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			ObjectInputStream input = this.input;
			ObjectOutputStream output = this.output;
			while(true) {
				Object[] inputObject = null;
				try {
					inputObject = (Object[])input.readObject();
				} catch(IOException e) {
					continue;
				}
				if (inputObject != null) {
					String id = String.valueOf(inputObject[0]);
					if (inputObject[1] instanceof String) {
						String api = String.valueOf(inputObject[1]);
						Object returnValue = EventClient.callAPI(api, inputObject[2]);
						Object[] obj = new Object[]{id, true, returnValue, inputObject[4], inputObject[3]};
						output.writeObject(obj);
					}
					else if((boolean)inputObject[1] == true && EventClient.hasListener(id)) {
						EventClient.getListener(id).apply(inputObject[2]);
					}
				}
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
}
