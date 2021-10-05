package ServerDTO;

import DTO.TimeProblemDTO;


public class TimeTableProblemWrapperDTO {
	
	private String owner;
	private int howManySolving;
	private double bestFitness;
	private TimeProblemDTO timeProblem;
	
	public TimeTableProblemWrapperDTO(TimeTableWrapper timeTableProblemWrapper)
	{
		owner = timeTableProblemWrapper.getOwner();
		howManySolving = timeTableProblemWrapper.getHowManySolvingTheProblem();
		bestFitness = timeTableProblemWrapper.getBestFitness();
		timeProblem =  new TimeProblemDTO(timeTableProblemWrapper.getTimeProblem());
		System.out.println("solving are ="+howManySolving);
	}

	public String getOwner() {
		return owner;
	}

	public int getHowManySolving() {
		return howManySolving;
	}

	public double getBestFitness() {
		return bestFitness;
	}

	public TimeProblemDTO getTimeProblem() {
		return timeProblem;
	}
	
	
	

}
