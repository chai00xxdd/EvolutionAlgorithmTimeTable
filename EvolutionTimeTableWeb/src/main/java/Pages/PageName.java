package Pages;

public class PageName {
	
	private static final String  startUrl = "/EvolutionTimeTableWeb/";
	private static String createPageUrl(String path)
	{
		return startUrl +path;
	}
	public static final String LoginPage = createPageUrl("pages/LoginPage/login.html");
	public static final String UserPage = createPageUrl("pages/UserPage/user.html");

}
