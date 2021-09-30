package Server;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.apache.coyote.http11.upgrade.UpgradeServletInputStream;

import com.sun.javafx.scene.traversal.Algorithm;

import Chat.ChatRecord;
import DTO.AlgorithmConfuigrationDTO;
import DTO.BestTimeTableSolutionDTO;
import ServerDTO.TimeProblemShortDTO;
import ServerDTO.TimeTableProblemWrapperDTO;
import ServerDTO.UserAlgorithmProgress;
import ServerDTO.UserDTO;
import evolution.algorithm.AlgorithmProgress;
import evolution.algorithm.BestDnaDetails;
import evolution.algorithm.EvolutionAlgorithm;
import evolution.algorithm.EvolutionAlgorithmEngine;
import evolution.timetable.BestTimeTableSolutionDetails;
import evolution.timetable.EvolutionTimeTableProblemEngine;
import evolution.timetable.TimeProblemAttribute;
import evolution.timetable.TimeTableProblem;

public class ServerManager {

	private final List<User> users = new ArrayList<>();
	private final List<TimeTableWrapper> timeProblmes = new ArrayList<>();
	private final List<ChatRecord> chat = new ArrayList<>();
	
	public ServerManager()
	{
		
	}
	
	public  boolean isUserExists(String username)
	{
		return users.stream().filter(user -> user.equals(new User(username))).count() != 0;
	}
	
	public void addChatRecord(ChatRecord chatRecord)
	{
		synchronized (chat) {
			chat.add(chatRecord);
		}
	}
	
	public List<UserAlgorithmProgress> getAllUsersAlgorithmProgressOfProblem(String problemId)
	{
		List<UserAlgorithmProgress> progressOfAllUsersSolvingTheProblem = new ArrayList<>();
		TimeTableWrapper problemWrapper = getTimeProblemWrapperById(problemId);
		if(problemWrapper != null)
		{
			synchronized (users) {
				
				for(User user : users)
				{
					if(user.getEngine(problemId) != null)
					{
						AlgorithmProgress progress = user.getEngine(problemId).getAlgorithmProgress();
						if(progress != null)
						{
							progressOfAllUsersSolvingTheProblem.add(new UserAlgorithmProgress(user.getUsername(), progress));
						}
						
					}
				}
				
			}
		}
		
		return progressOfAllUsersSolvingTheProblem;
	}
	
	public AlgorithmProgress getAlgorithmProgress (String userName,String timeProblemId)
	{
		AlgorithmProgress progress = null;
		User user = getUserByUserName(userName);
		TimeTableWrapper timeWrapper = getTimeProblemWrapperById(timeProblemId);
		if(user !=null && timeWrapper!=null)
		{
		   return getOrDefaultEngine(userName,timeProblemId).getAlgorithmProgress();
		}
		
		return progress;
	}
	
	public List<AlgorithmProgress> getAlgorithmProgressGraph(String userName,String timeProblemId)
	{
		List<AlgorithmProgress> progressGraph = null;
		User user = getUserByUserName(userName);
		TimeTableWrapper timeWrapper = getTimeProblemWrapperById(timeProblemId);
		if(user !=null && timeWrapper!=null)
		{
			progressGraph = getOrDefaultEngine(userName,timeProblemId).getAlgorithmProgressGraph();
		}
		
		return progressGraph;
		
		
	}
	
	public synchronized EvolutionAlgorithmEngine getOrDefaultEngine (String userName,String timeProblemId)
	{
		EvolutionAlgorithmEngine engine = null;
		User user = getUserByUserName(userName);
		TimeTableWrapper timeWrapper = getTimeProblemWrapperById(timeProblemId);
		if(user!=null && timeWrapper!= null)
		{
			boolean newEngineCreated = user.getEngine(timeProblemId) == null;
			engine = user.getOrDefaultEngine(timeWrapper.getTimeProblem());
			
			if(newEngineCreated)
			{
				
				engine.addOnNewGenerationListener((EvolutionAlgorithmEngine algorithmEngine) ->{
					BestDnaDetails bestDNA = algorithmEngine.getBestDnaDetails();
					
					if(bestDNA != null)
					{
						
						timeWrapper.updateIfNeedBestFitness(bestDNA.getFitness());
					}
				});
				
				engine.getOnAlgorithmStartListeners().add(evolutionEngine -> timeWrapper.increaseSolving());
				engine.getOnAlgorithmStopListeners().add(evolutionEngine -> timeWrapper.decreaseSolving() );
				
			}
			
		}
		if(engine == null)
		{
			System.out.println("engine is null very wired");
			System.out.println("because :");
			if(user ==null)
			{
				System.out.println("user is null");
				
			}
			if(timeWrapper == null)
			{
				System.out.println("wrapper is null");
			}
		}
		
		return engine;
	}
	
	
	
	public List<ChatRecord> getAllChatRecords()
	{
		synchronized (chat) {
			
			return new ArrayList<>(chat);
			
		}
	}
	
	public  boolean addUserIfNotExists(String username)
	{
		boolean insertedNewUser = true;
		
		synchronized (users) {
			
		
		if(isUserExists(username))
		{
			return !insertedNewUser;
		}
		
		users.add(new User(username));
		
		}
		
		return insertedNewUser;
	}
	
	public  void addTimeProblem(String owner,TimeTableProblem timeProblem)
	{
		synchronized (timeProblmes) {
			timeProblmes.add(new TimeTableWrapper(owner, timeProblem));
		}
	}
	
	public List<TimeTableProblem> getTimeTableProblems()
	{
		//DTO
		return null;
	}
	
	public List<UserDTO> getAllUserNames()
	{
		synchronized (users) {
		return users.stream().map(user -> new UserDTO(user)).collect(Collectors.toList());
		}
	}
	
	public List<UserDTO> getAllUsersOnline()
	{
		synchronized (users) {
			return users.stream().filter(user-> user.isLoggedIn()).map(user -> new UserDTO(user)).collect(Collectors.toList());
			}
		
	}
	
	public User getUserByUserName(String username)
	{
		User requestedUser = null;
		try
		{
			synchronized (users) {
				
			requestedUser = users.stream().filter(user -> user.getUsername().equals(username)).findFirst().get();
			}
		}
		
		catch(Exception e) {}
		
		return requestedUser;
    }
	
	public void login(String userName)
	{
		setUserLoggedIn(userName,true);
	}
	
	public void logout(String userName)
	{
		
	  setUserLoggedIn(userName,false);
		
	}
	
	public boolean IsUserLoggedIn(String userName)
	{
		synchronized (users) {
		
		User user = getUserByUserName(userName);
		return user !=null && user.isLoggedIn();
		
		}
	}
	
	private void setUserLoggedIn(String userName,boolean loggedIn)
	{
		User user = getUserByUserName(userName);
		if(user!=null)
		{
		synchronized (user) {
			user.setLoggedIn(loggedIn);
		}
		}
		
	}
	
	
	public List<TimeProblemShortDTO> getAllTimeProblemsShortDTO()
	{
		synchronized (timeProblmes) {
			return timeProblmes.stream().map(problem -> new TimeProblemShortDTO(problem)).collect(Collectors.toList());
		}
	}
	
	public List<TimeProblemShortDTO> getAllTimeProblemsShortDTO(String username)
	{
		User user = getUserByUserName(username);
		List<TimeProblemShortDTO> timeProblemDTO = getAllTimeProblemsShortDTO();
		if(user != null)
		{
			timeProblemDTO.forEach(problemDTO ->  problemDTO.setAlgorithmState(user.getTimeProblemState(problemDTO.getId()+"")));
		}
		
		return timeProblemDTO;
	}
	
	private TimeTableWrapper getTimeProblemWrapperById(String problemId)
	{
		TimeTableWrapper problemWrapper = null;
		synchronized (timeProblmes) {
		
			try
			{
				problemWrapper = timeProblmes.stream().filter(problem -> (problem.getTimeProblem().getId()+"").equals(problemId)).findFirst().get();
			}
			catch(Exception e)
			{
				
			}
		}
		
		return problemWrapper;
	}
	
	
	public TimeTableProblemWrapperDTO getTimeProblemDtoById(String problemId)
	{
		synchronized (timeProblmes) {
		
			
			TimeTableWrapper problemWrapper = timeProblmes.stream().filter(problem ->
			{
				return (problem.getTimeProblem().getId() +"").equals(problemId);
			}).findFirst().get();
			
			return  new TimeTableProblemWrapperDTO(problemWrapper);
			
		}
	}
	
	

	public BestTimeTableSolutionDTO pullBestSolutionDTO(String userNameToViewHisBestSolution, String problemIdToView) {
		BestTimeTableSolutionDTO bestSolutionOfUserOfProblem = null;
		User user = getUserByUserName(userNameToViewHisBestSolution);
		if(user!=null)
		{
			EvolutionTimeTableProblemEngine engine = user.getEngine(problemIdToView);
			if(engine !=null && engine.getBestSolution()!=null)
			{
				bestSolutionOfUserOfProblem = new BestTimeTableSolutionDTO(engine.getBestSolution());
				bestSolutionOfUserOfProblem.setUserName(userNameToViewHisBestSolution);
			}
			
		}
		
		return bestSolutionOfUserOfProblem;
	}
	
	
	public AlgorithmConfuigrationDTO pullAlgorithmConfigDTO(String userNameToViewHisConfig,String problemId)
	{
		AlgorithmConfuigrationDTO configOfUserOfProblem = null;
		
			EvolutionTimeTableProblemEngine engine = (EvolutionTimeTableProblemEngine) getOrDefaultEngine(userNameToViewHisConfig, problemId);
			if(engine !=null)
			{
				configOfUserOfProblem = engine.getConfuigrationDTO();
				configOfUserOfProblem.setUserName(userNameToViewHisConfig);
			}
			
		
		
		return configOfUserOfProblem;
	}
	
	
	
	
	

}
