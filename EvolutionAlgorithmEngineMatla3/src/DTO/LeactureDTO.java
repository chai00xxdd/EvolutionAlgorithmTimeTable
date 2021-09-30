package DTO;

import school.Leacture;

public class LeactureDTO {
	
	private TeacherDTO teacher;
	private ClassDTO sclass;
	private SubjectDTO subject;
	private int day;
	private int hour;
	
	public LeactureDTO(Leacture leacture)
	{
		teacher = new TeacherDTO (leacture.getTeacher());
		sclass = new ClassDTO(leacture.getClassToTeach());
		subject = new SubjectDTO(leacture.getCourse());
		day = leacture.getDay();
		hour = leacture.getHour();
	}
	public TeacherDTO getTeacher() {
		return teacher;
	}
	public ClassDTO getSclass() {
		return sclass;
	}
	public SubjectDTO getSubject() {
		return subject;
	}
	public int getDay() {
		return day;
	}
	public int getHour() {
		return hour;
	}
	
	
	
	
	
	
	

}
