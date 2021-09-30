package school;

import java.util.ArrayList;
import java.util.List;

import Utils.Entity;

public class Teacher extends Entity  {

List<Course>tecahingCourses=new ArrayList<Course>();
private int workingHoursPrefernce = 0;
public Teacher(int id, String name, List<Course> tecahingCourses) {
	super(id,name);
	this.tecahingCourses = tecahingCourses;
}
public Teacher(int id,String Name, List<Course>teachingCourses,int preferdHoursToWorkInAWeek)
{
	this(id,Name,teachingCourses);
	this.workingHoursPrefernce = preferdHoursToWorkInAWeek;
	
}
public Teacher(int id, String name) {
	super(id,name);
	this.id = id;
	this.name = name;
}
public Teacher() {
	super(-1,"NULL");
}
public Teacher(Teacher other)
{
	
	super(other.id,other.name);
	tecahingCourses=new ArrayList<>(other.tecahingCourses);
	
}

public List<Course> getTecahingCourses() {
	return tecahingCourses;
}
public void setTecahingCourses(List<Course> tecahingCourses) {
	this.tecahingCourses = tecahingCourses;
}


public int getWorkingHoursPrefernce() {
	return workingHoursPrefernce;
}
public void setWorkingHoursPrefernce(int workingHoursPrefernce) {
	this.workingHoursPrefernce = workingHoursPrefernce;
}
@Override
public String toString() {
	return "Teacher "+id;
}








}
