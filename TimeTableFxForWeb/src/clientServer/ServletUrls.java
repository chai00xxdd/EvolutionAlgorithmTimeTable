package clientServer;

public class ServletUrls {
	
	private static String PREFIX_SERVLET_URL ="http://127.0.0.1:8080//EvolutionTimeTableWeb";
	private static String createUrl(String servletName)
	{
		return PREFIX_SERVLET_URL + servletName;
	}
	
	public static final String LoginServlet = createUrl("/LoginServlet");
	public static final String TimeProblemsShortServletUrl = createUrl("/GetTimeProblemsShortServlet");
	public static final String TimeProblemWrapperDTOServlet = createUrl("/GetTimeProblemDetails");
	public static final String BestSolutionServlet = createUrl("/GetBestSolutionServlet");
	public static final String UsersServlet = createUrl("/GetUsersServlet");
	public static final String ChatServlet = createUrl("/ChatServlet");
	
	//Evolution Algorithm
	public static final String AlgorithmConfigServlet = createUrl("/AlgorithmConfuigrationServlet");
	public static final String AlgorithmOperationServlet = createUrl("/AlgorithmOperationServlet");
	public static final String CrossOverServlet = createUrl("/CrossOverServlet");
	public static final String MutationServlet = createUrl("/MutationsServlet");
	public static final String PopulationServlet = createUrl("/PopulationServlet");
	public static final String SelectionServlet = createUrl("/SelectionServlet");
	public static final String StopConditionsServlet = createUrl("/StopConditionsServlet");
	public static final String UploadTimeTableProblemServlet = createUrl("/UploadTimeTableProblemServlet");
	public static final String AlgoirthmProgressOfAllUsersServlet = createUrl("/AlgorithmProgressOfAllServlet");
	
	

}
