package evolution.algorithm.stopcondition;

import java.util.function.Predicate;

import evolution.algorithm.EvolutionAlgorithm;

public class StopByTime implements Predicate<EvolutionAlgorithm>,StopCondition {

	private int timeLimitInMs = Integer.MAX_VALUE;
	private boolean enabled = true;
	private double progress = 0;
	@Override
	public boolean test(EvolutionAlgorithm algorithm) {
		// TODO Auto-generated method stub
		
			progress = (((double)algorithm.getTimePassed())/timeLimitInMs);
			progress = Math.min(progress,1);
		
		return (algorithm.getTimePassed() > timeLimitInMs) &&enabled;
	}
	public int getTimeLimitInMs() {
		return timeLimitInMs;
	}
	public void setTimeLimitInMs(int timeLimitInMs) {
		this.timeLimitInMs = timeLimitInMs;
	}
	public StopByTime(int timeLimitInMs) {
		super();
		this.timeLimitInMs = timeLimitInMs;
	}
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	@Override
	public String toString() {
		return "Time";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return (timeLimitInMs/1000) + "";
	}
	@Override
	public double getProgress() {
		// TODO Auto-generated method stub
		return progress;
	}
	@Override
	public void clearProgress() {
		progress = 0;
		
	}
	
	
	
	
	
	
	
	

}
