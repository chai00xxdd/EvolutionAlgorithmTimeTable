package evolution.timetable;

import java.util.ArrayList;
import java.util.List;

import DTO.ClassDTO;
import DTO.SubjectDTO;
import DTO.TeacherDTO;
import school.Leacture;
import school.RawLeacture;
import school.Teacher;

public class BestTimeTableSolutionDetails {
private TimeTableSolution solution;
private int generation;
private int timePassed = 0;
public BestTimeTableSolutionDetails(TimeTableSolution solution,int generation, int timePassed)
{
	this.solution=solution;
	this.generation=generation;
	this.timePassed = timePassed;
}
public TimeTableSolution getSolution() {
	return solution;
}
public int getGeneration() {
	return generation;
}



public int getTimePassed() {
	return timePassed;
}
public List<RawLeacture> getRawSolution()
{
	List<RawLeacture> rawLeactures = new ArrayList<>();
	for (Leacture leacture : solution.getLeactures())
	{
		rawLeactures.add(new RawLeacture(leacture));
	}
	
	return rawLeactures;
}

public List<TeacherDTO> getTeachersDTO()
{
	List<Teacher> fullTeachers = new ArrayList<>(solution.getTeachers());
	List<TeacherDTO> teachers = new ArrayList<TeacherDTO>();
	fullTeachers.forEach(teacher -> teachers.add(new TeacherDTO(teacher)));
	
	return teachers;
}

public List<SubjectDTO> getSubjectsDTO ()
{
	List<SubjectDTO> subjects = new ArrayList<SubjectDTO>();
	solution.getCourses().forEach(subject -> subjects.add(new SubjectDTO(subject)));
	
	return subjects;
}

public List<ClassDTO> getClassesDTO()
{
	List<ClassDTO> classes = new ArrayList<ClassDTO>();
	solution.getClasses().forEach(sclass -> classes.add(new ClassDTO(sclass)));
	
	return classes;
}


}
