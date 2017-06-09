package event;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.Scanner;

public class EventManager {
	
	protected static ServerSocket serverSock;
	public static Map<String, Router> sockets = new ConcurrentHashMap<String, Router>();
	@SuppressWarnings("rawtypes")
	protected static Map<String, Function> listeners = new ConcurrentHashMap<String, Function>();
	protected static int lastSocketKey = -1;
	protected static int lastRequsetKey = -1;
	
	public static void start(int port) {
			
		try {
			System.out.print("\nEnter serverIP:");
			Scanner cin = new Scanner(System.in);
			String serverIP = cin.nextLine(); 
			InetAddress inetIP = InetAddress.getByName(serverIP);
			serverSock = new ServerSocket(port,100,inetIP);
			System.out.println("Server started...");
			System.out.println("-------------------------");
			System.out.println("Server information [ hostname / serverIP / port]");
			System.out.println(serverSock.getLocalSocketAddress());
			System.out.println("-------------------------");

			while (true) {
				Socket cSock = serverSock.accept();
				ObjectOutputStream output = new ObjectOutputStream(cSock.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(cSock.getInputStream());
				Object[] obj = new Object[]{true, String.valueOf(++lastSocketKey)};
				output.writeObject(obj);
				
				Router router = new Router(input, output);
				sockets.put(String.valueOf(lastSocketKey), router);
				Thread routerThread = new Thread(router);
				routerThread.start();
			}
		} catch (IOException e) { System.out.println("disconnected..."); }
	}
	public static void start() {
		start(9487);
	}
	
	@SuppressWarnings("rawtypes")
	public static void send(Function onReturn, String api, Object args, String to) {
		try {
			String id = String.valueOf(++lastRequsetKey);
			if(onReturn != null) listeners.put(id, onReturn);
			Object[] obj = new Object[]{id, api, args, to, null};
			sockets.get(to).send(obj);
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
		for (String to : sockets.keySet()) {
			send(onReturn, api, args, to);
		}
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
	public static Function getListener(String id) {
		return listeners.get(id);
	}
	public static boolean hasListener(String id) {
		return listeners.containsKey(id);
	}
}

class Router implements Runnable {
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	public Router(ObjectInputStream input, ObjectOutputStream output) {
		this.input = input;
		this.output = output;
	}
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			while(true) {
				Object[] inputObject = null;
				try {
					inputObject = (Object[])this.input.readObject();
				} catch(IOException e) {
					continue;
				}
				if(inputObject != null) {
					String id = String.valueOf(inputObject[0]);
					String api = String.valueOf(inputObject[1]);
					Object args = inputObject[2];
					String to = String.valueOf(inputObject[3]);
					String from = String.valueOf(inputObject[4]);
										
					if(inputObject[3] == null) { // if to server
						System.out.println("from :"+from+" to:server id:"+id+" api:"+api+" args:"+args);
						if (inputObject[1] instanceof String) { // not return value
							Object returnValue = EventClient.callAPI(api, args);
							Object[] obj = new Object[]{id, true, returnValue, inputObject[4], inputObject[3]};
							this.output.writeObject(obj);
						}
						else if((boolean)inputObject[1] == true && EventManager.hasListener(id)) { // is return value
							EventManager.getListener(id).apply(inputObject[2]);
						}
					}
					else if(inputObject[3] instanceof Boolean) {
						if((boolean)inputObject[3] == true) { // if to all
							System.out.println("from :"+from+" to:all id:"+id+" api:"+api+" args:"+args);
							for (String key : EventManager.sockets.keySet()) {
								EventManager.sockets.get(key).send(inputObject);
							}
						}
						else if((boolean)inputObject[3] == false) { // if to others
							System.out.println("from :"+from+" to:others id:"+id+" api:"+api+" args:"+args);
							for (String key : EventManager.sockets.keySet()) {
								if(!key.equals(from)) EventManager.sockets.get(key).send(inputObject);
							}
						}
					}
					else { // if to client
						System.out.println("from :"+from+" to:"+to+" id:"+id+" api:"+api+" args:"+args);
						EventManager.sockets.get(to).send(inputObject);
					}
				}
			}
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void send(Object obj) {
		try {
			this.output.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
