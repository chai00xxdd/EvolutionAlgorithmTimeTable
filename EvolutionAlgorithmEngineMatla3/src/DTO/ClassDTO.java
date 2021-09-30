package DTO;

import java.util.List;
import java.util.stream.Collectors;

import school.CourseRequirment;
import school.SchoolClass;

public class ClassDTO {
	
	private int id;
	private String name;
	private String requirmentsString ="";
	List<CourseRequirment> subjectsRequirments;
	
	public ClassDTO(SchoolClass sclass)
	{
		id = sclass.getId();
		name = sclass.getName();
		subjectsRequirments = sclass.getCoursesRequirments().stream().collect(Collectors.toList());
		
		boolean first = true;
		
		for(CourseRequirment requirment : subjectsRequirments)
		{
			if(!first)
			{
				requirmentsString+=",";
			}
			
			first = false;
			requirmentsString+="["+requirment.getCourseName()+" : "+requirment.getHoursRequired()+"]";
			
		}
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<CourseRequirment> getSubjectsRequirments() {
		return subjectsRequirments;
	}
	
	
	

}
