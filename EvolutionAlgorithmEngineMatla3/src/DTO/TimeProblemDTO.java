package DTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import evolution.timetable.TimeProblemAttribute;
import evolution.timetable.TimeTableProblem;

public class TimeProblemDTO {
	
	private int id;
	private List<TeacherDTO> teachers;
	private List<SubjectDTO> subjects;
	private List<ClassDTO> classes;
	private List<RuleDTO> rules;
	private Collection<TimeProblemAttribute> attributes;
	
    
	public TimeProblemDTO(TimeTableProblem timeProblem)
	{
		id = timeProblem.getId();
		teachers = timeProblem.getSchoolShallowClone().getTeachers().stream().map(teacher -> new TeacherDTO(teacher))
				.collect(Collectors.toList());
		
		subjects = timeProblem.getSchoolShallowClone().getCourses().stream().map(course -> new SubjectDTO(course))
				.collect(Collectors.toList());
		
		classes = timeProblem.getSchoolShallowClone().getClasses().stream().map(sclass -> new ClassDTO(sclass))
				.collect(Collectors.toList());
		
		rules = new ArrayList<>(timeProblem.getRules()).stream().map(rule -> new RuleDTO(rule))
				.collect(Collectors.toList());
		
		attributes = new ArrayList<>(timeProblem.getAttributes());
		
		
	
	}

	public int getId() {
		return id;
	}

	public List<TeacherDTO> getTeachers() {
		return teachers;
	}

	public List<SubjectDTO> getSubjects() {
		return subjects;
	}

	public List<ClassDTO> getClasses() {
		return classes;
	}

	public List<RuleDTO> getRules() {
		return rules;
	}

	public Collection<TimeProblemAttribute> getAttributes() {
		return attributes;
	}
	
	
	
	
 
}
