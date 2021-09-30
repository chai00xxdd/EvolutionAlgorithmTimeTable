package evolution.timetable.rule;

import java.util.Arrays;



import Utils.Entity;
import evolution.timetable.TimeTableSolution;
import school.Leacture;
import school.Teacher;

public class DayOffTeacherRule extends Rule {

	public DayOffTeacherRule(double weight, RuleType type) {
		super(weight, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RuleResult applyOnDNA(TimeTableSolution dna) {
		// TODO Auto-generated method stub
		if(dna.getTeachers().size()==0)
		{
			return new RuleResult(1, getWeight());
		}
		double ruleGrade=0;
		Entity.sortById(dna.getTeachers());
		int maxTeacherId=dna.getTeachers().get(dna.getTeachers().size()-1).getId();
		int days=dna.getDays();
		int [] [] howManyHoursTeachersWorkAtDays=new int [maxTeacherId+1][days];
		for(int teacher=0; teacher<maxTeacherId+1; teacher++)
		{
			for(int day=0; day<days; day++)
			{
				howManyHoursTeachersWorkAtDays[teacher][day]=0;
			}
		}
		for(Leacture leacture : dna.getLeactures())
		{
			int currentTeacherId=leacture.getTeacher().getId();
			int currentDay=leacture.getDay();
			howManyHoursTeachersWorkAtDays[currentTeacherId][currentDay]++;
		}
		for(Teacher teacher : dna.getTeachers())
		{
			int [] howManyHoursTeacherWorkAtEachDay = howManyHoursTeachersWorkAtDays[teacher.getId()];
		   
			long  teacherOffDays=Arrays.stream(howManyHoursTeacherWorkAtEachDay).filter(dayHours -> dayHours==0).count();
			double teacherGrade=0;
			if(teacherOffDays>0)
			{
			 
				teacherGrade= (double)1/dna.getTeachers().size();
			}
			ruleGrade+=teacherGrade;
			
		}
		if(ruleGrade== 0.9999999999999999)
		{
			ruleGrade=1;
		}
		return new RuleResult(ruleGrade, getWeight());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "DayOffTeacher";
	}

}
