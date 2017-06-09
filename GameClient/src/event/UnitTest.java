package event;

public class UnitTest {

	public static void main(String[] args) {
		start();
	}
	
	public static void start() {
		EventManager em = new EventManager();
		em.route();
		String key = EventClient.addReference(em);
		System.out.println(key);
		Object obj = EventClient.getObject(key);
		System.out.println(obj.getClass().getName());
		EventClient.callMethod(obj,"route");
	}
	
}
