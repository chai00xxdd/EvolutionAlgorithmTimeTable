package evolution.timetable.rule;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import evolution.timetable.TimeTableSolution;
import school.Teacher;

public class WorkingHoursPreferenceRule extends Rule {

	public WorkingHoursPreferenceRule(double weight, RuleType type) {
		super(weight, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RuleResult applyOnDNA(TimeTableSolution dna) {
		// TODO Auto-generated method stub
		
		if(dna.getTeachers().size() ==0)
		{
			return new RuleResult(1, weight);
		}
		

		Map<Teacher, AtomicInteger> howManyhoursEachTeacherTeachAtAWeek = new HashMap<>();
		dna.getTeachers().forEach(teacher ->
		{
			howManyhoursEachTeacherTeachAtAWeek.put(teacher,new AtomicInteger(0));
		});
		dna.getLeactures().forEach(leacture -> 
		{
		 howManyhoursEachTeacherTeachAtAWeek.get(leacture.getTeacher()).incrementAndGet();
		});
		double ruleGrade = 0;
		for(Teacher teacher : dna.getTeachers())
		{
			double howManyHoursTeacherTeachAtAWeek = howManyhoursEachTeacherTeachAtAWeek.get(teacher).doubleValue();
			double teacherPreffercHoursDiffrence = Math.abs(howManyHoursTeacherTeachAtAWeek - teacher.getWorkingHoursPrefernce());
			double teacherGrade = 1/(1 + teacherPreffercHoursDiffrence);
			teacherGrade *= ((double)1/dna.getTeachers().size());
			ruleGrade += teacherGrade;
		
		}
		return new RuleResult(ruleGrade,weight);
		
		}

	@Override
	public String toString() {
		return "WorkingHoursPreference";
	}
	
	
		
		
	}

