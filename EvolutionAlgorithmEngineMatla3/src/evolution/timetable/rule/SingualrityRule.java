package evolution.timetable.rule;


import java.util.Map;
import java.util.HashMap;

import evolution.timetable.TimeTableSolution;
import school.Leacture;
import school.SchoolClass;




public class SingualrityRule extends Rule {

	public SingualrityRule(double weight, RuleType type) {
		super(weight, type);
		// TODO Auto-generated constructor stub
	}
	 public SingualrityRule(SingualrityRule other) {
		 this(other.weight,other.type);
	}

	@Override
	public RuleResult applyOnDNA(TimeTableSolution dna) {
				
			return improved(dna);
		
	}
	
	private RuleResult improved(TimeTableSolution solution)
	{
		if(solution.getClasses().size()==0||solution.getLeactures().size()==0)
		{
			return new RuleResult(1, getWeight());//empty true
		}
		double maximumGradePossibleOfRule=solution.getClasses().size();
		double ruleGrade=0;
		int days=solution.getDays(); 
		int hours=solution.getHours();
		Map<SchoolClass,Double>classesGradeWeightMap=new HashMap<>();
		Map<SchoolClass,Integer>[][]classeLeactuersCounterAtDayAndHour=createDayHourTeachersLeactuersCounterArray(days,hours);
		Map<SchoolClass,Integer>classesTotalLeactuersMap=new HashMap<>();
		Map<SchoolClass,Integer>howManyLeactuersCollitionMap=new HashMap<>();
		for(Leacture leacture:solution.getLeactures())
		{
			SchoolClass currentClass=leacture.getClassToTeach();
			Integer classTotalLeactuersCounter=classesTotalLeactuersMap.getOrDefault(currentClass,0);
			classTotalLeactuersCounter++;
			classesTotalLeactuersMap.put(currentClass, classTotalLeactuersCounter);
			
			Integer classLeactuersAtDayAndHour=classeLeactuersCounterAtDayAndHour[leacture.getDay()][leacture.getHour()]
					                                                                 .getOrDefault(currentClass,0);
			classLeactuersAtDayAndHour++;
			Integer howManyLeactuersCollitionOfClass=howManyLeactuersCollitionMap.getOrDefault(currentClass,0);
			if(classLeactuersAtDayAndHour==2)//first collitoin in day and hour detected
			{
				howManyLeactuersCollitionOfClass+=2;
			}
			else if(classLeactuersAtDayAndHour>2)//another collitoin detected
			{
				
				howManyLeactuersCollitionOfClass++;
			
			}
			howManyLeactuersCollitionMap.put(currentClass, howManyLeactuersCollitionOfClass);
			classeLeactuersCounterAtDayAndHour[leacture.getDay()][leacture.getHour()]
                    .put(currentClass,classLeactuersAtDayAndHour);
		}
		for(SchoolClass sclass:solution.getClasses())
		{
			int totalClassLeactuers= classesTotalLeactuersMap.getOrDefault(sclass,0);
			classesGradeWeightMap.put(sclass,(double)totalClassLeactuers/solution.getLeactures().size());
			
		}
		
		for(SchoolClass sclass:solution.getClasses())
		{
		  
			double classGradeWeight=classesGradeWeightMap.get(sclass);
			double classRuleGrade=1;
		    double classTotalLeactuers=classesTotalLeactuersMap.getOrDefault(sclass,0);//good
		 //  System.out.println(classTotalLeactuers);
		    if(classTotalLeactuers!=0)
		    {
		    	
		    double collitionLeactuersOfClass= howManyLeactuersCollitionMap.getOrDefault(sclass,0);//bad
		    double noCollitionLeactuersOfClass= classTotalLeactuers-collitionLeactuersOfClass;
		   
		    classRuleGrade =(noCollitionLeactuersOfClass / classTotalLeactuers);
		    }
		    ruleGrade += classRuleGrade*classGradeWeight;
		}
	
	//	ruleGrade/=maximumGradePossibleOfRule;
		
		return new RuleResult(ruleGrade,getWeight());
	}
	
	private Map<SchoolClass,Integer> [][] createDayHourTeachersLeactuersCounterArray(int days,int hours)
	{
		Map<SchoolClass,Integer> [] []  classesLeactuersCounterAtDayAndHour=new HashMap [days][hours];
		for(int day=0; day<days; day++)
		{
			for(int hour=0; hour<hours; hour++)
			{
				classesLeactuersCounterAtDayAndHour[day][hour]=new HashMap<>();
			}
		}
		return classesLeactuersCounterAtDayAndHour;
	}

	@Override
	public String toString() {
		
		return "Singualrity";
	}
	
	

}
