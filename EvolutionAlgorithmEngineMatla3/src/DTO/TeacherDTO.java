package DTO;

import java.util.List;
import java.util.stream.Collectors;

import school.Teacher;

public class TeacherDTO {
	
	private int id;
	private String name;
	private String coursesString="";
	private int workingHoursPrefernce = 0;
	List<SubjectDTO> courses ;
	
	public TeacherDTO(Teacher teacher)
	{
		id = teacher.getId();
		name = teacher.getName();
		workingHoursPrefernce = teacher.getWorkingHoursPrefernce();
		courses = teacher.getTecahingCourses().stream().map(course -> new SubjectDTO(course)).collect(Collectors.toList());
		
		boolean first = true;
		for(SubjectDTO course : courses)
		{
			if(!first)
			{
				coursesString += ",";
			}
			
			coursesString +=course.getName();
			first = false;
		}
		
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<SubjectDTO> getCourses() {
		return courses;
	}

	public int getWorkingHoursPrefernce() {
		return workingHoursPrefernce;
	}

	public String getCoursesString() {
		return coursesString;
	}

	@Override
	public String toString() {
		return "Teacher "+getId();
	}
	
	
	
	
	
	

}
