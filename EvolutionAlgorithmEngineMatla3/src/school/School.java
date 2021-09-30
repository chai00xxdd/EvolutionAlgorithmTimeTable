package school;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class School implements Cloneable {
	private List<Course> courses=new ArrayList<>();
	private List<Teacher> teachers=new ArrayList<>();
	private List<SchoolClass> classes=new ArrayList<>();
	
	public School(List<Course> courses, List<Teacher> teachers, List<SchoolClass> classes) {
		super();
		this.courses = courses;
		this.teachers = teachers;
		this.classes = classes;
	}
	public School(School other)
	{
	   this.courses=new ArrayList<>(other.getCourses());
	   this.teachers=new ArrayList<>(other.getTeachers());
	   this.classes=new ArrayList<>(other.getClasses());
	}
	
	@Override
	public Object clone()  {
		// TODO Auto-generated method stub
		return new School(this);
		
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public List<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
	public List<SchoolClass> getClasses() {
		return classes;
	}
	public void setClasses(List<SchoolClass> classes) {
		this.classes = classes;
	}
	
	
	
	
}
