package evolution.algorithm;

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
	
	
	
	
	
	

}
