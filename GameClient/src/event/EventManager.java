package event;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager {
	
	protected static ServerSocket serverSock;
	public static Map<String, Router> sockets = new ConcurrentHashMap<String, Router>();
	protected static int lastSocketKey = -1;
	
	public static void start() {
		try {
			serverSock = new ServerSocket(9487);
			System.out.print("Server started...");
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
	
}

class Router implements Runnable {
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	public Router(ObjectInputStream input, ObjectOutputStream output) {
		this.input = input;
		this.output = output;
	}
	
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
					System.out.println("from :"+from+" to:"+to+" id:"+id+" api:"+api+" args:"+args);
					if(inputObject[3] == null) {
						Object returnValue = EventClient.callAPI(api, args);
						System.out.println("Debug");
						Object[] obj = new Object[]{id, true, returnValue};
						this.output.writeObject(obj);
					}
					else {
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
