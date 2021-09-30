package evolution.algorithm.stopcondition;

import java.util.function.Predicate;

import evolution.algorithm.EvolutionAlgorithm;

public class StopByGenerations implements Predicate<EvolutionAlgorithm>,StopCondition {

private int maximumGenerationNumber;
private boolean enabled = true;
private double progress = 0;


public StopByGenerations(int maximumGenerationNumber)
{
	this.maximumGenerationNumber=maximumGenerationNumber;
}
	@Override
	public boolean test(EvolutionAlgorithm t) {
		// TODO Auto-generated method stub
		progress = ((double)t.getHowManyGenerationsCreated())/maximumGenerationNumber;
		progress=Math.min(progress, 1);
		return (t.getHowManyGenerationsCreated() >= maximumGenerationNumber) &&enabled;
	}
	
	public int getGenerationsLimit()
	{
		return maximumGenerationNumber;
	}
	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		this.enabled = enabled;
		
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}
	@Override
	public String toString() {
		return "Generations";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return maximumGenerationNumber + "";
	}
	@Override
	public double getProgress() {
		// TODO Auto-generated method stub
		return progress;
	}
	@Override
	public void clearProgress() {
		// TODO Auto-generated method stub
		progress = 0;
	}
	
	
	
	

	

}
