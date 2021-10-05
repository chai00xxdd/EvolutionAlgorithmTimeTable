package clientServer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.javafx.scene.traversal.Algorithm;

import DTO.AlgorithmConfuigrationDTO;
import DTO.BestTimeTableSolutionDTO;
import DTO.CrossOverDTO;
import DTO.MutationDTO;
import DTO.SelectionDTO;
import DTO.StopConditionDTO;
import ServerDTO.ChatRecord;
import ServerDTO.Operation;
import ServerDTO.Response;
import ServerDTO.TimeProblemShortDTO;
import ServerDTO.TimeTableProblemWrapperDTO;
import ServerDTO.UserAlgorithmProgress;
import ServerDTO.UserDTO;
import evolution.algorithm.AlgorithmOperation;
import evolution.algorithm.AlgorithmProgress;

public class HttpClientETT {
	
	static final String COOKIES_HEADER = "Set-Cookie";
	//CookieManager bitch
	private CookieManager cookieManager = new CookieManager();
	private String username = null;
	
	private HttpClient getHttpClient(String servletUrl) throws MalformedURLException
	{
		HttpClient client = new HttpClient(servletUrl);
		
		synchronized (this) {
			
		client.setCookies(cookieManager);
		
		}
		return client;
	}
	
	
	
	public String getUsername() {
		return username;
	}



	public boolean isWebServerUp()
	{
		final boolean serverUp = true;
		try
		{
		login("");
	
		}
		catch(ConnectException e )
		{
			return !serverUp;
		}
		catch(Exception e)
		{
			
		}
		
		return serverUp;
	}
	public void login(String username) throws Exception
	{
		
		try {
			HttpClient loginClient = getHttpClient(ServletUrls.LoginServlet);
			loginClient.addParameter(Attributes.Username, username);
			loginClient.sendRequest();
			if(username !=null && !username.equals(""))
			{
			this.username = username;
			}
			Map<String, List<String>> headerFields = loginClient.getHeaderFields();
			List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
			synchronized(this)
			{

			if (cookiesHeader != null) {
			    for (String cookie : cookiesHeader) {
			    	//System.out.println(HttpCookie.parse(cookie).toString().replace("[","").replace("]",""));
			    	cookieManager.getCookieStore().add(null,HttpCookie.parse(cookie).get(0));
			    }               
			}
			}
			
			//handle cookies mother facker
		} 
		 catch (ServerErrorException e) {
			// TODO Auto-generated catch block
			 
			 throw e;
		     
		} 
		catch(Exception e)
		{
			throw e;
		}
	}
	
	public void AddMutation(String problemId,MutationDTO mutation,String component,Operation operation) throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.MutationServlet);
		client.setHttpMethod(HttpMethod.POST);
		client.addParameter(Attributes.Id, problemId);
		client.addParameter(Attributes.Probality, mutation.getProbality()+"");
		client.addParameter(Attributes.Operation, operation.toString());
		client.addParameter(Attributes.Name, mutation.getName());
		client.addParameter(Attributes.Parameter, mutation.getParameterString());
		client.addParameter(Attributes.Component, component);
		
		client.sendRequest();
		
	}
	
	public void DeleteMutation(String problemId,String mutationId) throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.MutationServlet);
		client.setHttpMethod(HttpMethod.POST);
		client.addParameter(Attributes.Operation, Operation.Delete.toString());
		client.addParameter(Attributes.Id, problemId);
		client.addParameter(Attributes.MutationId, mutationId);
		client.sendRequest();
		
	}
	
	public List<TimeProblemShortDTO> getShortProblemsDTO() throws Exception 
	{
		
		
			HttpClient client = getHttpClient(ServletUrls.TimeProblemsShortServletUrl);
			client.sendRequest();
			String jsonString = client.getResponseString();
			Type listType = new TypeToken<ArrayList<TimeProblemShortDTO>>(){}.getType();
			return (List<TimeProblemShortDTO>)new Gson().fromJson(jsonString,listType);
			
		
	}
	
	public TimeTableProblemWrapperDTO getTimeProblemWrapperDTO(String problemId) throws Exception
	{
		
		
			HttpClient client = getHttpClient(ServletUrls.TimeProblemWrapperDTOServlet);
			client.addParameter(Attributes.Id, problemId);
			client.sendRequest();
			String jsonString = client.getResponseString();
			Type type = new TypeToken<TimeTableProblemWrapperDTO>(){}.getType();
			return (TimeTableProblemWrapperDTO) new Gson().fromJson(jsonString,type);
			
		
	}
	
	public BestTimeTableSolutionDTO getBestSolutionDTO(String username,String problemId) throws Exception
	{
		
		
			HttpClient client = getHttpClient(ServletUrls.BestSolutionServlet);
			client.addParameter(Attributes.Id, problemId);
			client.addParameter(Attributes.Username, username);
			client.sendRequest();
			String jsonString = client.getResponseString();
			Type type = new TypeToken<BestTimeTableSolutionDTO>(){}.getType();
			return (BestTimeTableSolutionDTO) new Gson().fromJson(jsonString,type);
			
		
	}
	
	public AlgorithmConfuigrationDTO getAlgorithmConfigDTO(String username,String problemId)
	{
		try
		{
		HttpClient client = getHttpClient(ServletUrls.AlgorithmConfigServlet);
		client.addParameter(Attributes.Username, username);
		client.addParameter(Attributes.Id, problemId);
		client.sendRequest();
		String jsonString = client.getResponseString();
		Type type = new TypeToken<AlgorithmConfuigrationDTO>(){}.getType();
		return (AlgorithmConfuigrationDTO) new Gson().fromJson(jsonString,type);
		
		}
		catch(Exception e)
		{
			return null;
		}
		
	}
	
	public void AddOrUpdateCrossOver(String problemId,CrossOverDTO crossover) throws Exception
	{
		
		HttpClient client = getHttpClient(ServletUrls.CrossOverServlet);
		client.setHttpMethod(HttpMethod.POST);
		client.addParameter(Attributes.Id, problemId);
		client.addParameter(Attributes.Parameter, crossover.getParemterString());
		client.addParameter(Attributes.CuttingPoints, crossover.getCuttingPoints());
		client.addParameter(Attributes.Name, crossover.getName());
		client.sendRequest();
		
	}
	
	public void AddOrUpdateSelection(String problemId,SelectionDTO selection) throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.SelectionServlet);
		client.setHttpMethod(HttpMethod.POST);
		client.addParameter(Attributes.Id, problemId);
		client.addParameter(Attributes.Parameter, selection.getParameterString());
		client.addParameter(Attributes.Elittism, selection.getEllitism());
		client.addParameter(Attributes.Name, selection.getName());
		client.sendRequest();
		
	}
	
	public void AddOrUpdatePopulation(String problemId,String population) throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.PopulationServlet);
		client.setHttpMethod(HttpMethod.POST);
		client.addParameter(Attributes.Population, population);
		client.addParameter(Attributes.Id, problemId);
		client.sendRequest();
		
	}
	
	public void AddOrUpdateStopCondition(String problemId,StopConditionDTO stopCondition) throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.StopConditionsServlet);
		client.setHttpMethod(HttpMethod.POST);
		client.addParameter(Attributes.Name, stopCondition.getName());
		client.addParameter(Attributes.Id, problemId);
		client.addParameter(Attributes.Enabled, stopCondition.isEnabled()+"");
		client.addParameter(Attributes.Parameter, stopCondition.getValue());
		client.sendRequest();
	}
	
	public void operateAlgorithm(String problemId,AlgorithmOperation operation) throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.AlgorithmOperationServlet);
		client.setHttpMethod(HttpMethod.POST);
		client.addParameter(Attributes.Operation,operation.toString().toLowerCase());
		client.addParameter(Attributes.Id, problemId);
		
		client.sendRequest();
	}
	
	public AlgorithmProgress getAlgorithmProgress(String problemId) throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.AlgorithmOperationServlet);
		client.addParameter(Attributes.Id, problemId);
		client.sendRequest();
		String jsonString = client.getResponseString();
		Type type = new TypeToken<AlgorithmProgress>(){}.getType();
		return (AlgorithmProgress) new Gson().fromJson(jsonString,type);
		
	}
	
	public List<UserAlgorithmProgress> getAlgorithmProgressOfAllUsers(String problemId) throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.AlgoirthmProgressOfAllUsersServlet);
		client.addParameter(Attributes.Id, problemId);
		client.sendRequest();
		String jsonString = client.getResponseString();
		Type type = new TypeToken<ArrayList<UserAlgorithmProgress>>(){}.getType();
		return (ArrayList<UserAlgorithmProgress>) new Gson().fromJson(jsonString,type);
		
	}
	
	public List<ChatRecord> getAllChatRecords() throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.ChatServlet);
		client.setTimeOutInMs(200);
		client.sendRequest();
		String jsonString = client.getResponseString();
		Type type = new TypeToken<ArrayList<ChatRecord>>(){}.getType();
		return (ArrayList<ChatRecord>) new Gson().fromJson(jsonString,type);
	}
	
	public void addChatMessage(String message) throws Exception
	{
		
		HttpClient client = getHttpClient(ServletUrls.ChatServlet);
		client.setHttpMethod(HttpMethod.POST);
		client.addParameter(Attributes.Message, message);
		client.sendRequest();
	}
	
	public List<AlgorithmProgress> getAlgorithmGraph(String problemId) throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.AlgorithmOperationServlet);
		client.addParameter(Attributes.Id, problemId);
		client.addParameter(Attributes.GetGraph, "true");
		client.sendRequest();
		String jsonString = client.getResponseString();
		Type type = new TypeToken<ArrayList<AlgorithmProgress>>(){}.getType();
		return (ArrayList<AlgorithmProgress>) new Gson().fromJson(jsonString,type);
		
	}
	
	public Response uploadXmlFile(File ettXmlFile) throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.UploadTimeTableProblemServlet); 
		client.setHttpMethod(HttpMethod.POST);
		client.sendMultiPartsFile("xmlFile", ettXmlFile);
		String jsonString = client.getResponseString();
		Type type = new TypeToken<Response>(){}.getType();
		return (Response) new Gson().fromJson(jsonString,type);
		
	}
	
	public List<UserDTO> getAllUsersConnected() throws Exception
	{
		HttpClient client = getHttpClient(ServletUrls.UsersServlet);
		client.sendRequest();
		String jsonString = client.getResponseString();
		Type type = new TypeToken<ArrayList<UserDTO>>(){}.getType();
		return (ArrayList<UserDTO>) new Gson().fromJson(jsonString,type);
		
	}
	
	
	

}
