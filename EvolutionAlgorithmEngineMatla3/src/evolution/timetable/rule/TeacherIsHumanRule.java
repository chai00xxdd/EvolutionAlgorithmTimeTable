package evolution.timetable.rule;


import java.util.Map;
import java.util.HashMap;
import evolution.timetable.TimeTableSolution;
import school.Leacture;
import school.Teacher;

public class TeacherIsHumanRule extends Rule {

	public TeacherIsHumanRule(double weight, RuleType type) {
		super(weight, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RuleResult applyOnDNA(TimeTableSolution timeTable) {
		// TODO Auto-generated method stub
		
		
			return improved(timeTable);
		
	    
	
	}
	private RuleResult improved (TimeTableSolution solution)
	{
		if(solution.getTeachers().size()==0 ||solution.getLeactures().size()==0)
		{
			return new RuleResult(1,getWeight());
		}
		double maximumGradePossibleOfRule=solution.getTeachers().size();
		double ruleGrade=0;
		int days=solution.getDays(); 
		int hours=solution.getHours();
		Map<Teacher,Double>teacherGradeWeightMap=new HashMap<>();
		Map<Teacher,Integer>[][]teacherLeactuersCounterAtDayAndHour=createDayHourTeachersLeactuersCounterArray(days,hours);
		Map<Teacher,Integer>teachersTotalLeactuersMap=new HashMap<>();
		Map<Teacher,Integer>howManyLeactuersCollitionMap=new HashMap<>();
		for(Leacture leacture:solution.getLeactures())
		{
			Teacher currentTeacher=leacture.getTeacher();
			Integer teacherTotalLeactuersCounter=teachersTotalLeactuersMap.getOrDefault(currentTeacher,0);
			teacherTotalLeactuersCounter++;
			teachersTotalLeactuersMap.put(currentTeacher, teacherTotalLeactuersCounter);
			
			Integer teacherLeactuersAtDayAndHour=teacherLeactuersCounterAtDayAndHour[leacture.getDay()][leacture.getHour()]
					                                                                 .getOrDefault(currentTeacher,0);
			teacherLeactuersAtDayAndHour++;
			Integer howManyLeactuersCollitionOfTeacher=howManyLeactuersCollitionMap.getOrDefault(currentTeacher,0);
			if(teacherLeactuersAtDayAndHour==2)//first collitoin in day and hour detected
			{
				howManyLeactuersCollitionOfTeacher+=2;
			}
			else if(teacherLeactuersAtDayAndHour>2)//another collitoin detected
			{
				
				howManyLeactuersCollitionOfTeacher++;
			
			}
			howManyLeactuersCollitionMap.put(currentTeacher, howManyLeactuersCollitionOfTeacher);
			teacherLeactuersCounterAtDayAndHour[leacture.getDay()][leacture.getHour()]
                    .put(currentTeacher,teacherLeactuersAtDayAndHour);
		}
		
		for(Teacher teacher:solution.getTeachers())
		{
			int totalTeachersLeactuers= teachersTotalLeactuersMap.getOrDefault(teacher,0);
			teacherGradeWeightMap.put(teacher,(double)totalTeachersLeactuers/solution.getLeactures().size());
			
		}
		for(Teacher teacher:solution.getTeachers())
		{
		  
			double teacherGradeWeight= teacherGradeWeightMap.get(teacher);
			double teacherRuleGrade=1;
		    double teacherTotalLeactuers=teachersTotalLeactuersMap.getOrDefault(teacher,0);//good
		    if(teacherTotalLeactuers!=0)
		    {
		    double collitionLeactuersOfTeacher= howManyLeactuersCollitionMap.getOrDefault(teacher,0);//bad
		    double noCollitionLeactuersOfTeacher= teacherTotalLeactuers-collitionLeactuersOfTeacher;
		    
		    teacherRuleGrade =(noCollitionLeactuersOfTeacher / teacherTotalLeactuers);
		    
		    }
		    ruleGrade += teacherRuleGrade*teacherGradeWeight;
		}
		if(maximumGradePossibleOfRule!=0)
		{
		//ruleGrade/=maximumGradePossibleOfRule;
		}
		return new RuleResult(ruleGrade,getWeight());
	
		
	}
	private Map<Teacher,Integer> [][] createDayHourTeachersLeactuersCounterArray(int days,int hours)
	{
		Map<Teacher,Integer> [] []  teacherLeactuersCounterAtDayAndHour=new HashMap [days][hours];
		for(int day=0; day<days; day++)
		{
			for(int hour=0; hour<hours; hour++)
			{
				teacherLeactuersCounterAtDayAndHour[day][hour]=new HashMap<>();
			}
		}
		return teacherLeactuersCounterAtDayAndHour;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TeacherIsHuman";
	}
	
	

}
