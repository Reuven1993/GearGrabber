package base;

public abstract class  PeriodicLoop {
	
	private static long time = 0;
	
	/*
	 * Override this function and write here all the code that you would like to be 
	 * executed periodically
	 */
	public void execute() {
		time += PeriodicScheduler.periodicInterval;
		
	}
	
	public static long elapsedTime() {
		return time;
	}

}
