package school;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import Utils.Entity;

public class SchoolClass extends Entity {
	
	private HashMap<Course,Integer> CoursHoursMap= new HashMap<>();
	public SchoolClass(String name,int id) {
		super(id,name);
		
	}
	public SchoolClass (SchoolClass other)
	{
		super(other.id,other.name);
		CoursHoursMap=new HashMap<>(other.getCoursHoursMap());
	}
	public HashMap<Course, Integer> getCoursHoursMap() {
		return CoursHoursMap;
	
	}
	
	public Collection<CourseRequirment> getCoursesRequirments()
	{
		Collection<CourseRequirment> coursesRequirments = new ArrayList<>();
		for(Entry<Course, Integer> requirment : getCoursHoursMap().entrySet())
		{
			coursesRequirments.add(new CourseRequirment(requirment.getKey().getId(),requirment.getKey().getName(),(int)requirment.getValue()));
		}
		
		return coursesRequirments;
	}
	@Override
	public String toString() {
		return "Class "+id;
	}
	
	
	
	
	

}
