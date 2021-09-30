package school;

import evolution.algorithm.DnaComponent;

public class Leacture implements Cloneable,DnaComponent,Comparable<Leacture> {
	public Teacher teacher;
	public Course course;
	public SchoolClass classToTeach;
	public int hour;
	public int day;
	public Leacture() {
		
	}
	public Leacture(Leacture otherLeacture)
	{
		
		
		this.teacher=new Teacher(otherLeacture.teacher);
		this.course=new Course (otherLeacture.course);
		this.classToTeach=new SchoolClass(otherLeacture.classToTeach);
		this.day=otherLeacture.day;
		this.hour=otherLeacture.hour;
		
		
		
	}
	public Leacture(Teacher teacher, Course course, SchoolClass classToTeach,int day ,int hour) {
		super();
		this.teacher = teacher;
		this.course = course;
		this.classToTeach = classToTeach;
		this.hour=hour;
		this.day=day;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public SchoolClass getClassToTeach() {
		return classToTeach;
	}
	public void setClassToTeach(SchoolClass classToTeach) {
		this.classToTeach = classToTeach;
	}
	
	public Leacture shallowClone()
	{
		return new Leacture(teacher,course,classToTeach,day,hour);
	}
	@Override
	public Leacture clone() 
	{
		
		return new Leacture(this);
		
	}
	@Override
	public Leacture duplicate()
	{
	  return shallowClone();
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	@Override
	public String toString() {
		return "[D="+day+",H="+hour+",C="+classToTeach.getId()+","+"T="+teacher.getId()+",S="+course.getId()+"]";
	}
	@Override
	public int compareTo(Leacture leacture2) {
		// TODO Auto-generated method stub
		Leacture leacture1=this;
		
		int [] [] compareBy= {
				{leacture1.getDay(),leacture1.getHour(),leacture1.getClassToTeach().getId(),
					leacture1.getTeacher().getId(),leacture1.getCourse().getId()},
				{leacture2.getDay(),leacture2.getHour(),leacture2.getClassToTeach().getId(),
						leacture2.getTeacher().getId(),leacture2.getCourse().getId()}
				
		};
	   for(int i=0; i<compareBy[0].length; i++)
	   {
		   if(compareBy[0][i]<compareBy[1][i])
		   {
			   return -1;
		   }
		   else if(compareBy[0][i]>compareBy[1][i])
		   {
			   return 1;
		   }
	   }
	   return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classToTeach == null) ? 0 : classToTeach.hashCode());
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + day;
		result = prime * result + hour;
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Leacture other = (Leacture) obj;
		if (classToTeach == null) {
			if (other.classToTeach != null)
				return false;
		} else if (!classToTeach.equals(other.classToTeach))
			return false;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (day != other.day)
			return false;
		if (hour != other.hour)
			return false;
		if (teacher == null) {
			if (other.teacher != null)
				return false;
		} else if (!teacher.equals(other.teacher))
			return false;
		return true;
	}
	
	
	

}
