package DTO;

import java.util.function.Predicate;

import evolution.algorithm.EvolutionAlgorithm;
import evolution.algorithm.stopcondition.StopCondition;

public class StopConditionDTO {
	
	private String name ;
	private String value;
	private boolean enabled ;
	private double progress = 0;
	private boolean reached = false;
	
	
	public StopConditionDTO(Predicate<EvolutionAlgorithm> stopCondition)
	{
		this.name = stopCondition.toString();
		StopCondition stopConditionInterface = (StopCondition) stopCondition;
		this.progress = stopConditionInterface.getProgress();
		this.value = stopConditionInterface.getValue();
		this.enabled = stopConditionInterface.isEnabled();
		this.reached = progress >=1 && enabled;
	}
	
	public StopConditionDTO(String name,String parameter,boolean enabled)
	{
		this.name = name;
		this.value = parameter;
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public double getProgress() {
		return progress;
	}

	public boolean isReached() {
		return reached;
	}
	
	
	
	

}
