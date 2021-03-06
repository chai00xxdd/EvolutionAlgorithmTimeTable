package evolution.timetable.rule;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Math.HomoGraphicFunction;
import evolution.timetable.TimeTableSolution;
import school.Course;
import school.Leacture;
import school.SchoolClass;

public class SatisFactory extends Rule {

	public SatisFactory(double weight, RuleType type) {
		super(weight, type);
		
	}

	@Override
	public RuleResult applyOnDNA(TimeTableSolution dna) {
		
		if(dna.getClasses().size()==0 )
		{
			return new RuleResult(1,getWeight());//empty true
		}
		double maxRuleGrade= dna.getClasses().size();
		double ruleGrade=0;
		Map<SchoolClass,Map<Course,Integer>> allClassesCourseRequirmentsDistanceMap=new HashMap<>();
		int sumOfDistances=0;
		
	
		for(SchoolClass sclass:dna.getClasses())
		{
			allClassesCourseRequirmentsDistanceMap.put(sclass,new HashMap<>(sclass.getCoursHoursMap()));
			
		}
		List<Leacture>allLeacture=dna.getLeactures();
		for(Leacture leacture:allLeacture)
		{
			SchoolClass currentClass= leacture.getClassToTeach();
			Map<Course,Integer>currentClassCourseRequirmentCounter=allClassesCourseRequirmentsDistanceMap.get(currentClass);
			Course currentCourse=leacture.getCourse();
			Integer hoursCounterOfCourse=currentClassCourseRequirmentCounter.getOrDefault(currentCourse,0);
			/*
			 * 
			 * two aproches one says i got atlist the lessons i want other says hell no its all or nothing
			 * 
			 */
			if(hoursCounterOfCourse!=null)//course learned by class
			{
				
				hoursCounterOfCourse--;
				currentClassCourseRequirmentCounter.put(currentCourse, hoursCounterOfCourse);
				
			}
		}
		for(SchoolClass sclass :dna.getClasses())
		{
			Map<Course,Integer>distancesFromCoursesRequirment= allClassesCourseRequirmentsDistanceMap.get(sclass);
		    int coursesRequestByClass=sclass.getCoursHoursMap().size();	
		    double classGrade=0;
		    if(distancesFromCoursesRequirment.size()==0)
		    {
		    	classGrade=1;
		    }
		    else
		    {
			final double courseRequirmentGradeWeight= (double)1/distancesFromCoursesRequirment.size();
			for(Course course:distancesFromCoursesRequirment.keySet())
			{
				double distanceFromCourseRequirment=distancesFromCoursesRequirment.get(course);
				distanceFromCourseRequirment=Math.abs(distanceFromCourseRequirment);
				sumOfDistances+=distanceFromCourseRequirment;
				double gradeOfCurrentCourseRequirment = new SatisFactoryFunction().apply(distanceFromCourseRequirment);
				gradeOfCurrentCourseRequirment*=courseRequirmentGradeWeight;
				classGrade+=gradeOfCurrentCourseRequirment;
			}
		    }
		
			ruleGrade+= classGrade;
			
		}
		
		ruleGrade/=maxRuleGrade;
		
	   
		 if(sumOfDistances>=dna.getHowManyLeacturesNeeded())//not linear
		{
		   	
			ruleGrade= new SatisFactoryFunction().apply((double)sumOfDistances);
			
		}
		else//linear
		{
			ruleGrade= (double)(dna.getHowManyLeacturesNeeded()-sumOfDistances)/dna.getHowManyLeacturesNeeded();
		}
		return new RuleResult(ruleGrade,getWeight());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "SatisFactory";
	}
	

	
	private static class SatisFactoryFunction extends HomoGraphicFunction
	{

		public SatisFactoryFunction() {
			super(0, 1, 1, 1);
			
		}
		
	}
	

}
