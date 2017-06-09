package event;

public class ExampleObject {

	public void route(Number i) {
		System.out.println("route" + i);
	}
	public void route(Number i1, Number i2) {
		System.out.println("1route" + i1);
		System.out.println("2route" + i2);
	}
	public void route(Number i1, Number i2, Number i3) {
		System.out.println("1route" + i1);
		System.out.println("2route" + i2);
		System.out.println("3route" + i3);
	}
	
	public static void test(Number i) {
		System.out.println("test" + i);
	}
	
}
