package evolution.algorithm;

import java.util.ArrayList;
import java.util.List;

import DTO.StopConditionDTO;

public class AlgorithmProgress {
	
	private double fitness;
	private int generation;
	private int timePassed;
	private AlgorithmState algorithmState;
	private boolean stopConditionReached = false;
	private boolean noStopConditions = false;
	private List<StopConditionDTO> stopConditionsProgress;
	private String username = "";
	
	public AlgorithmProgress(AlgorithmProgress other)
	{
	   fitness = other.fitness;
	   generation = other.generation;
	   timePassed = other.timePassed;
	   algorithmState = other.algorithmState;
	   stopConditionReached = other.stopConditionReached;
	   noStopConditions = other.noStopConditions;
	   stopConditionsProgress = new ArrayList<>(other.stopConditionsProgress);
	   username = other.username;
	}
	
	public AlgorithmProgress(double fitness, int generation, int timePassed, AlgorithmState algorithmState,List<StopConditionDTO> stopCondProgress) {
		super();
		this.fitness = fitness;
		this.generation = generation;
		this.timePassed = timePassed;
		this.algorithmState = algorithmState;
		this.stopConditionsProgress = stopCondProgress;
		stopConditionReached = stopCondProgress.stream().anyMatch(stopCond -> stopCond.isReached());
		noStopConditions = stopCondProgress.stream().allMatch(stopCond -> !stopCond.isEnabled());
	}

	public double getFitness() {
		return fitness;
	}
	
	public int getGeneration() {
		return generation;
	}
	
	public int getTimePassed() {
		return timePassed;
	}
	
	public AlgorithmState getAlgorithmState() {
		return algorithmState;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public void setGeneration(int generation) {
		this.generation = generation;
	}

	public void setTimePassed(int timePassed) {
		this.timePassed = timePassed;
	}

	public boolean isStopConditionReached() {
		return stopConditionReached;
	}

	public boolean isNoStopConditions() {
		return noStopConditions;
	}

	public List<StopConditionDTO> getStopConditionsProgress() {
		return stopConditionsProgress;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
	
	
	
	
	
	

}
