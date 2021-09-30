package evolution.timetable.rule;

import java.math.BigDecimal;
import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import Utils.Configurable;
import evolution.timetable.TimeTableSolution;
import school.Course;
import school.Leacture;

public class SequentialityRule extends Rule implements Configurable {

	private int totalHoursToTeachCourseInARow;
	public SequentialityRule(double weight, RuleType type,int totalHoursToTeachCourseInARow) {
		super(weight, type);
		this.totalHoursToTeachCourseInARow=totalHoursToTeachCourseInARow;
		// TODO Auto-generated constructor stub
	}

	@Override
	public RuleResult applyOnDNA(TimeTableSolution dna) {
		// TODO Auto-generated method stub
	    double ruleGrade=0;
		if(dna.getLeactures().size()==0||dna.getCourses().size()==0)
		{
		return new RuleResult(1,getWeight());
		}
		List<Course>courses=dna.getCourses();
		List<Leacture>[][]leacturesShecdule=dna.getLeactuersShecdule();
		//need to work with big decimal dude
		double courseGradeWeight=(double)1/dna.getCourses().size();
		  double dayGradeWeight=(double)1/dna.getDays();
		  int counter=0;
		for(Course course:courses)
		{
			
		  double courseGrade=0;	
		  for(int day=0; day<dna.getDays(); day++)
		  {
			  double courseGradeInDay=getCourseGradeInADay(leacturesShecdule[day],course);
			  
			courseGrade+= courseGradeInDay*dayGradeWeight;
			    
		  }
		  if(courseGrade!=0)
		  {
			  counter++;
		  }
		  ruleGrade+= courseGrade*courseGradeWeight;
		}
		
		if(ruleGrade==0.9999999999999999)//better to work with bigdeicmal
		{
			ruleGrade=1;
		}
		return new RuleResult(ruleGrade,getWeight());
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Sequentiality";
	}
	
	private  double getCourseGradeInADay(List<Leacture>[]leactuersInDay,Course course)
	{
		int hours= leactuersInDay.length;
		int totalLeactuersOfCourseInDay=0;
		int totalLeacutersOfCoursePartOfABadSequence=0;
		
		for(int hour=0; hour<hours; hour++)
		{
			int sequenceOfCourse=0;
			int howManyTimeCourseAppearInCurrentHour=(int) leactuersInDay[hour].stream()
					.filter(currentCourse->currentCourse.getCourse().equals(course)).count();
			
			totalLeactuersOfCourseInDay+=howManyTimeCourseAppearInCurrentHour;
			while(howManyTimeCourseAppearInCurrentHour>0)
			{
				sequenceOfCourse++;
				if(sequenceOfCourse>getTotalHoursToTeachCourseInARow())
				{
					return 0;
				}
				hour++;
				if(hour>=hours)
				{
					break;
				}
				howManyTimeCourseAppearInCurrentHour=(int) leactuersInDay[hour].stream()
						.filter(currentCourse->currentCourse.getCourse().equals(course)).count();
				
			}
			
			
		}
		return 1;	
	}

	public int getTotalHoursToTeachCourseInARow() {
		return totalHoursToTeachCourseInARow;
	}

	public void setTotalHoursToTeachCourseInARow(int totalHoursToTeachCourseInARow) {
		this.totalHoursToTeachCourseInARow = totalHoursToTeachCourseInARow;
	}

	@Override
	public String getParemetersString() {
		// TODO Auto-generated method stub
		return "TotalHours = "+totalHoursToTeachCourseInARow;
	}

	@Override
	public Parameter getParemter() {
		// TODO Auto-generated method stub
		return new Parameter("TotalHours",totalHoursToTeachCourseInARow);
	}
	
	
	
	
	
	
	

}
