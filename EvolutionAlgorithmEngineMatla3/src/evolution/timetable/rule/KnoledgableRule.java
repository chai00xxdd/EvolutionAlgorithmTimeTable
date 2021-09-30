package evolution.timetable.rule;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import evolution.timetable.TimeTableSolution;
import school.Course;
import school.Leacture;
import school.Teacher;

public class KnoledgableRule extends Rule {

	public KnoledgableRule(double weight, RuleType type) {
		super(weight, type);
		// TODO Auto-generated constructor stub
	}
    
	@Override
	public RuleResult applyOnDNA(TimeTableSolution dna) {
		// TODO Auto-generated method stub
		if(dna.getTeachers().size()==0 ||dna.getLeactures().size()==0)
		{
			return new RuleResult(1,getWeight());
		}
		double maxGradeOfRule=dna.getTeachers().size();
		double gradeOfRule=0;
        Map<Teacher,Double>teacherPercentValueInRule=new HashMap<>();
		Map<Teacher,Integer>TeacherTotalLeacutersCounterMap=new HashMap<>();
		Map<Teacher,Integer>howManyLeactuersTeacherKnowHeTeachMap=new HashMap<>();
		List<Leacture>allLeactures=dna.getLeactures();
		for(Leacture leacture: allLeactures)
		{
			Teacher currentTeacher=leacture.getTeacher();
			Course  currentCourse=leacture.getCourse();
			boolean teacherQualifiedToTeachCourse=false;
			
			increaseMapValueInteger(TeacherTotalLeacutersCounterMap,currentTeacher);
			
			for(Course course: currentTeacher.getTecahingCourses())
			{
				if(course.equals(currentCourse))
				{
					teacherQualifiedToTeachCourse=true;
					break;
				}
			}
			if(teacherQualifiedToTeachCourse)
			{
				increaseMapValueInteger(howManyLeactuersTeacherKnowHeTeachMap,currentTeacher);
			}
			
		}
		for(Teacher teacher:dna.getTeachers())
		{
			int totalTeachersLeactuers= TeacherTotalLeacutersCounterMap.getOrDefault(teacher,0);
			teacherPercentValueInRule.put(teacher,(double)totalTeachersLeactuers/dna.getLeactures().size());
			
		}
		double howManyLeacturesAreCanBeTeached=0;
	   for(Teacher teacher:dna.getTeachers())
	   {
		   
		   double teacherRuleWeight= teacherPercentValueInRule.get(teacher);
		   double teacherGradeOfRule=1;
		   double teacherTotalLeactuers=TeacherTotalLeacutersCounterMap.getOrDefault(teacher,0);
		   if(teacherTotalLeactuers!=0) {
			   double howManyLeactuersTeacherTeachHeKnows=howManyLeactuersTeacherKnowHeTeachMap.getOrDefault(teacher,0);
			   howManyLeacturesAreCanBeTeached+=howManyLeactuersTeacherTeachHeKnows;
			   teacherGradeOfRule = howManyLeactuersTeacherTeachHeKnows/teacherTotalLeactuers;
			   
		   }
		   gradeOfRule+=teacherGradeOfRule*teacherRuleWeight;
	   }
		gradeOfRule=howManyLeacturesAreCanBeTeached/dna.getLeactures().size();//cancel this so it will be not linear
	  return new RuleResult(gradeOfRule,getWeight());
	
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Knowledgeable";
	}
	private void increaseMapValueInteger(Map<Teacher,Integer>teacherMap,Teacher teacherKey)
	{
		Integer value= teacherMap.getOrDefault(teacherKey,0);
		value++;
		teacherMap.put(teacherKey, value);
	}

}
