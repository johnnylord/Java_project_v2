import event.EventClient;

public class UnitTest {
	
	public static void main(String[] args) {
		start();
	}
	
	public static void start() {
		/*ExampleObject eo = new ExampleObject();
		eo.route(8);
		String key = EventClient.addReference(eo);
		System.out.println(key);
		Object obj = EventClient.getObject(key);*/
		/*System.out.println(obj.getClass().getName());
		EventClient.callMethod(obj,"route");
		ExampleObject.test(new Integer(1));
		Class c = ExampleObject.class;
		EventClient.callMethod(eo,"route", new Object[]{new Integer(2)});
		EventClient.callStaticMethod(c,"test", new Object[]{new Integer(3)});
		System.out.println(EventClient.parseCallable("event.ExampleObject::route()"));
		System.out.println(EventClient.parseCallable("event.ExampleObject:${dfg}"));
		System.out.println(EventClient.parseCallable("event.ExampleObject@${udp}"));
		System.out.println(EventClient.parseRun("::route(123)"));
		System.out.println(EventClient.parseRun("[${abc}]"));
		System.out.println(EventClient.parseArgs("${dfr}, hgf, 123"));
		System.out.println(EventClient.parseArgs("$1, ${dfr}, hgf, 123,"));
		EventClient.callStaticMethod(event.ExampleObject.class,"test", new Object[]{new Integer(1)});
		EventClient.callAPI("event.EventManager::test($...)", new Object[]{1});
		ExampleObject.test(1);*/
		
		/*ExampleObject em = new ExampleObject();
		String key = EventClient.addReference(em,"1");
		System.out.println(key);
		EventClient.callAPI(key+"::route($...)", new Object[]{1});
		System.out.println(EventClient.getReference(em));
		EventClient.callAPI(em, "::route($...)", new Object[]{1});
		EventClient.callAPI(em, "::route(1,$...)", new Object[]{1,2});
		em.route(1);*/
		
		/*
		EventClient.callAPI("pakage.Classname::static_methodname($)", arg1);
		EventClient.callAPI("pakage.Classname::static_methodname($...)", arg1);
		EventClient.callAPI("pakage.Classname::static_methodname($...)", new Object[]{arg1, arg2, ...});
		
		ExampleObject obj = new ExampleObject();
		String key = EventClient.addReference(obj,"name");
		EventClient.callAPI(key+"::methodname($...)", arg1);
		EventClient.callAPI(key+"::methodname($...)", arg1);
		EventClient.callAPI(key+"::methodname($...)", new Object[]{arg1, arg2, ...});
		*/
		
		//EventClient.send("pakage.Classname:name::func($...)", Object[]{arg1, arg2}, to);
		
		EventClient.initialize();
		EventClient.send((o)->response(o), "event.ExampleObject::test(1)", EventClient.getKey());
	}
	
	public static Object response(Object obj) {
		System.out.println(obj);
		return null;
	}
	
}
