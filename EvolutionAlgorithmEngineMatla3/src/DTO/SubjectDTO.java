package DTO;

import java.util.List;

import school.Course;

public class SubjectDTO {
	
	private int id;
	private String name;
	

   public SubjectDTO(Course course)
   {
	   id = course.getId();
	   name = course.getName();
   }

public int getId() {
	return id;
}

public String getName() {
	return name;
}
   
   

}
