package evolution.timetable.rule;

import java.util.Arrays;

import Utils.Entity;
import evolution.timetable.TimeTableSolution;
import school.Leacture;
import school.SchoolClass;
import school.Teacher;

public class DayOffClassRule extends Rule {

	public DayOffClassRule(double weight, RuleType type) {
		super(weight, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RuleResult applyOnDNA(TimeTableSolution dna) {
		// TODO Auto-generated method stub
		if(dna.getClasses().size()==0)
		{
			return new RuleResult(1, getWeight());
		}
		double ruleGrade=0;
		Entity.sortById(dna.getClasses());
		int maxClassId=dna.getClasses().get(dna.getClasses().size()-1).getId();
		int days=dna.getDays();
		int [] [] howManyHoursClassesStudyAtEachDay=new int [maxClassId+1][days];
		for(int sclass=0; sclass<maxClassId+1; sclass++)
		{
			for(int day=0; day<days; day++)
			{
				howManyHoursClassesStudyAtEachDay[sclass][day]=0;
			}
		}
		for(Leacture leacture : dna.getLeactures())
		{
			int currentClassId=leacture.getClassToTeach().getId();
			int currentDay=leacture.getDay();
			howManyHoursClassesStudyAtEachDay[currentClassId][currentDay]++;
		}
		for(SchoolClass sclass : dna.getClasses())
		{
			int [] howManyHoursClassStudyAtEachDay = howManyHoursClassesStudyAtEachDay[sclass.getId()];
		   
			long  classOffDays=Arrays.stream(howManyHoursClassStudyAtEachDay).filter(hoursOfStduyAtDay -> hoursOfStduyAtDay==0).count();
			double classGrade=0;
			if(classOffDays>0)
			{
			 
				classGrade= (double)1/dna.getClasses().size();
			}
			ruleGrade+=classGrade;
			
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
		return "DayOffClass";
	}

}
