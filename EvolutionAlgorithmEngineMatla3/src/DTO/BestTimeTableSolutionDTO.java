package DTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import evolution.timetable.BestTimeTableSolutionDetails;
import evolution.timetable.TimeTableSolution;
import evolution.timetable.rule.RuleResult;
import school.RawLeacture;
import school.Teacher;

public class BestTimeTableSolutionDTO {
	
	private double fitness;
	private int generation;
	private int numberOfLeactuers;
	private List<RawLeacture> rawLeactuers = new ArrayList<>();
	private List<TeacherDTO> teachers = new ArrayList<>();
	private List<ClassDTO> classes = new ArrayList<>();
	private List<RuleResult> ruleResults = new ArrayList<>();
	private List<ShecduleDTO> teachersShecdule = new ArrayList<>();
	private List<ShecduleDTO> classesShecdule = new ArrayList<>();
	private String userName;
	
	public BestTimeTableSolutionDTO(BestTimeTableSolutionDetails solutionDetails)
	{
		fitness = solutionDetails.getSolution().getFitness();
		generation = solutionDetails.getGeneration();
		numberOfLeactuers = solutionDetails.getSolution().getLeactures().size();
		
	    rawLeactuers = solutionDetails.getRawSolution();
	    teachers = solutionDetails.getTeachersDTO();
	    classes = solutionDetails.getClassesDTO();
		
		TimeTableSolution solution  = solutionDetails.getSolution();
		
		List<Teacher> fullTeachers = new ArrayList<>(solution.getTeachers());
		fullTeachers.forEach(teacher ->
		{teachersShecdule.add(new ShecduleDTO(teacher.getName(),teacher.getId()+"", solution.getTeacherShecdule(teacher)));});
		
		
		classesShecdule = solution.getClasses().stream()
				       .map(sclass -> new ShecduleDTO(sclass.getName(),sclass.getId()+"",solution.getClassShecdule(sclass)))
				       .collect(Collectors.toList());
		
		ruleResults = solution.getRulesResults();
		
		
		
		
	}

	public void setUserName(String username) {
		this.userName = username;
	}
	
	
	
	
	

}
