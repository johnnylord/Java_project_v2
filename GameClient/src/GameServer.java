import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import event.*;

public class GameServer {
	
	protected static Queue<String> queue = new ConcurrentLinkedQueue<String>();
	protected static Map<String, String> pair = new ConcurrentHashMap<String, String>();
	
	public static void main(String[] args) {
		EventManager.start();
	}
	
	public static void match(String key) {
		System.out.println("match"+key);
		System.out.println(queue);
		String other = queue.poll();
		System.out.println(other);
		if(other == null) queue.add(key);
		else {
			System.out.println("Start put");
			pair.put(key, other);
			pair.put(other, key);
			System.out.println("End put");
			EventManager.send("GameClient::setPair($...)", new Object[]{other, true}, key);
			EventManager.send("GameClient::setPair($...)", new Object[]{key, false}, other);
		}
	}
	
}
