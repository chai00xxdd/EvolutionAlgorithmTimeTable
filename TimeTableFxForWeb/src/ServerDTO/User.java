package ServerDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import evolution.algorithm.EvolutionAlgorithmConfiguration;
import evolution.algorithm.EvolutionAlgorithmEngine;
import evolution.factory.EvolutionAlgorithmDefaultFactory;
import evolution.timetable.EvolutionTimeTableProblemEngine;
import evolution.timetable.TimeTableProblem;

public class User {
	
	private static final Object createEngineLock = new Object();
	private String username;
	private List<EvolutionTimeTableProblemEngine> engines = new ArrayList<>();
	private boolean isLoggedIn = false;
	
	public User(String username)
	{
		this.username = username;
		
	}

	public String getUsername() {
		return username;
	}
	
	public synchronized String getTimeProblemState(String timeProblemId)
	{
		String timeProblemState ="UNSET";
		EvolutionAlgorithmEngine engineOfProblem = getEngine(timeProblemId);
		if(engineOfProblem !=null)
		{
			timeProblemState="IDLE";
			
			if(engineOfProblem.isAlgorithmRunning())
			{
				timeProblemState="RUNNING";
			}
		}
		
		return timeProblemState;
	}
	
	public synchronized EvolutionTimeTableProblemEngine getEngine(String timeProblemId)
	{
		EvolutionTimeTableProblemEngine engineThatMatchTheProblem = null;
		
		try
		{
			engineThatMatchTheProblem= engines.stream().filter(problem -> (problem.getTimeTableProblem().getId()+"").equals(timeProblemId)).findFirst().get();
		} catch(Exception e) {}
		
		return engineThatMatchTheProblem;
	}
	
	public synchronized EvolutionTimeTableProblemEngine getOrDefaultEngine(TimeTableProblem timeProblem)
	{
		EvolutionTimeTableProblemEngine engineThatMatchTheProblem = null;
		List<EvolutionTimeTableProblemEngine> possibleEnginesThatMatchProblem =
				engines.stream().filter(engine -> engine.getTimeTableProblem().equals(timeProblem)).collect(Collectors.toList());
		if(possibleEnginesThatMatchProblem.size() ==0)
		{
			EvolutionAlgorithmConfiguration defaultConfiguration = EvolutionAlgorithmDefaultFactory.createDefaultConfiguration();
			engineThatMatchTheProblem = new EvolutionTimeTableProblemEngine(defaultConfiguration, timeProblem);
			engines.add(engineThatMatchTheProblem);
		}
		else
		{
			final int first = 0;
			engineThatMatchTheProblem = possibleEnginesThatMatchProblem.get(first);
		}
		
		return engineThatMatchTheProblem;
	}
	
	public synchronized List<EvolutionTimeTableProblemEngine> getTimeTableEngines()
	{
		return new ArrayList<>(engines);
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(username, other.username);
	}

	public synchronized boolean isLoggedIn() {
		return isLoggedIn;
	}

	public synchronized void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	
	
	
	
	
	

}
