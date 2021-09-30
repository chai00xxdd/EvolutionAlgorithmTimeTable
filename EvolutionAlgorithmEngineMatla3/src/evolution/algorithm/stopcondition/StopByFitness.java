package evolution.algorithm.stopcondition;

import java.util.function.Predicate;

import evolution.algorithm.EvolutionAlgorithm;

public class StopByFitness implements Predicate<EvolutionAlgorithm>,StopCondition {

	private double fitness;
	private boolean enabled = true;
	private double progress = 0;
	public StopByFitness(double fitness) throws Exception
	{
		setFitness(fitness);
	}
	@Override
	public boolean test(EvolutionAlgorithm t) {
		// TODO Auto-generated method stub
		progress = t.getBestDNA().getFitness() / getFitness();
		if(progress == Double.NaN)
			progress = 0;
		progress = Math.min(progress,1);
		return (t.getBestDNA().getFitness() >= getFitness()) &&enabled;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) throws Exception {
		if(fitness<0||fitness>1)
			throw new Exception("fitness must be between [0,1]");
		this.fitness = fitness;
		
		
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
		return "Fitness";
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return fitness +"";
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
