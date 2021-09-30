package Utils;

public class AppUtils {

	public static void sleep(int timeToSleep)
	{
		try {
			Thread.sleep(timeToSleep);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
