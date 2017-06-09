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
		
		String other = queue.poll();
		if(other == null) queue.add(key);
		else {
			pair.put(key, other);
			pair.put(other, key);
			EventManager.send("GameClient::setPair($...)", new Object[]{other, true}, key);
			EventManager.send("GameClient::setPair($...)", new Object[]{key, false}, other);
		}
	}
	
}
