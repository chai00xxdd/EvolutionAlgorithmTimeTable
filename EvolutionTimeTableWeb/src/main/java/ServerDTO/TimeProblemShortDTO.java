package ServerDTO;

import java.util.concurrent.atomic.AtomicLong;

import Server.TimeTableWrapper;

public class TimeProblemShortDTO {
	
	private int teachers;
	private int classes;
	private int subjects;
	private int days;
	private int hours;
	private String owner;
	private int howManySolving;
	private int id;
	private int softRules;
	private int hardRules;
	private double bestFitness = 0;
	private String algorithmState;
	
	public TimeProblemShortDTO(TimeTableWrapper timeTableWrapper)
	{
		this.teachers = timeTableWrapper.getTimeProblem().getSchool().getTeachers().size();
		this.classes = timeTableWrapper.getTimeProblem().getSchool().getClasses().size();
		this.subjects = timeTableWrapper.getTimeProblem().getSchool().getCourses().size();
		this.hours = timeTableWrapper.getTimeProblem().getHours();
		this.days = timeTableWrapper.getTimeProblem().getDays();
		this.owner = timeTableWrapper.getOwner();
		this.bestFitness = timeTableWrapper.getBestFitness();
		this.id = timeTableWrapper.getTimeProblem().getId();
		this.softRules = timeTableWrapper.getTimeProblem().getSoftRulesCount();
		this.hardRules = timeTableWrapper.getTimeProblem().getHardRulesCount();
		howManySolving = timeTableWrapper.getHowManySolvingTheProblem();
	
	}
	public int getTeachers() {
		return teachers;
	}
	public int getClasses() {
		return classes;
	}
	public int getSubjects() {
		return subjects;
	}
	public String getOwner() {
		return owner;
	}
	public int getHowManySolving() {
		return howManySolving;
	}
	public int getDays() {
		return days;
	}
	public int getHours() {
		return hours;
	}
	public int getId() {
		return id;
	}
	public int getSoftRules() {
		return softRules;
	}
	public int getHardRules() {
		return hardRules;
	}
	public String getAlgorithmState() {
		return algorithmState;
	}
	public void setAlgorithmState(String algorithmState) {
		this.algorithmState = algorithmState;
	}
	
	
	
	
	
	
	
	
	
	
	

}
