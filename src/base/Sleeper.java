package base;

public class Sleeper {
	public static void sleep(long miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}

	}

}
