package ServerDTO;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import evolution.timetable.TimeTableProblem;

public class TimeTableWrapper {
	
	private TimeTableProblem timeProblem;
	private AtomicInteger howManySolvingTheProblem = new AtomicInteger(0);
    private double bestFitness = 0;
	private String owner;
	public TimeTableWrapper(String owner,TimeTableProblem timeProblem) {
		super();
		this.owner = owner;
		this.timeProblem = timeProblem;
		
	}
	
	public synchronized double getBestFitness() {
		return bestFitness;
	}


	public void increaseSolving()
	{
		howManySolvingTheProblem.incrementAndGet();
		
	}
	
	public void decreaseSolving()
	{
		howManySolvingTheProblem.decrementAndGet();
		
	}
	
	public int getHowManySolvingTheProblem()
	{
		return howManySolvingTheProblem.get();
	}

	public String getOwner() {
		return owner;
	}

	public TimeTableProblem getTimeProblem() {
		return timeProblem;
	}
	
	public synchronized void updateIfNeedBestFitness(double mightBebestFitness)
	{
		bestFitness = Math.max(mightBebestFitness, bestFitness);
	}
	
	
	
	
	
	

}
