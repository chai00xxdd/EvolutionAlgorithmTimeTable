package MainTesting;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import evolution.timetable.TimeTableSolution;
import evolution.timetable.rule.Rule;
import evolution.timetable.rule.RuleResult;
import school.*;
import Utils.Entity;


public class TimeTablePrinter {
	
	private TimeTableSolution timeTable;
	
	public TimeTablePrinter(TimeTableSolution timeTable)
	{
		this.timeTable=timeTable;
	}
	public void printByTeacher(Teacher teacher)
	{
		
		List<Leacture>[][]teacherLeactuers=timeTable.getTeacherShecdule(teacher);
		CommandLineTable teacherTable=createCommandLineTimeTable(teacherLeactuers,PrintBy.TEACHER);
		
		System.out.println("***********Teacher "+teacher.getId()+" timeTable:******");
		teacherTable.print();
		
	  
	}
	public void printBySubject(Course course)
	{
		List<Leacture>[][]subjectLeacture=timeTable.getCourseShecdule(course);
		CommandLineTable courseTable=createCommandLineTimeTable(subjectLeacture,PrintBy.TEACHER);
		System.out.println("***********Subject "+course.getId()+" timeTable:******");
		courseTable.print();
	}
	public void printBySubjects()
	{
		for(Course course:timeTable.getCourses())
		{
			printBySubject(course);
		}
	}
	private CommandLineTable createEmptyCommandLineTimeTable()
	{
		CommandLineTable teacherTable=new CommandLineTable();
		teacherTable.addHeader("Hour");
		for(int day=0; day<timeTable.getDays(); day++)
		{
			teacherTable.addHeader("day "+(day+1));
		}
		return teacherTable;
			
	}

	
	private CommandLineTable createCommandLineTimeTable(List<Leacture>[][]leactures,PrintBy printBy)
	{
		CommandLineTable commandLineTimeTable=createEmptyCommandLineTimeTable();
		commandLineTimeTable.setShowVerticalLines(true);
		commandLineTimeTable.setSeperateRows(true);
		for(int hour=0; hour<timeTable.getHours(); hour++)
		{
			List<String>timeTableRow=new ArrayList<>();
			timeTableRow.add("hour "+(hour+1));
			
			for(int day=0; day<timeTable.getDays(); day++)
			{
				String leacturesAtHourAndDayString="";
				
				for(Leacture leacture: leactures[day][hour])
				{
					String leactureString="";
					if(printBy==PrintBy.TEACHER)
					{
						leactureString="[C"+leacture.getClassToTeach().getId();
					}
					else if(printBy==PrintBy.CLASS)
					{
						leactureString="[T"+leacture.getTeacher().getId();
					}
					leactureString+=",S"+leacture.getCourse().getId()+"]";
					if(!leacturesAtHourAndDayString.equals(""))
					{
						leacturesAtHourAndDayString+=",";
					}
					leacturesAtHourAndDayString+=leactureString;
				}
				timeTableRow.add(leacturesAtHourAndDayString);
				
			}
			
			String[] rowArray=new String[timeTableRow.size()];
			for(int i=0; i<rowArray.length; i++)
				rowArray[i]=timeTableRow.get(i);
			commandLineTimeTable.addRow(rowArray);
			
		}
		return commandLineTimeTable;
	}
	public void printByClass(SchoolClass sclass)
	{
		List<Leacture>[][]classLeactuers=timeTable.getClassShecdule(sclass);
		CommandLineTable classTable=createCommandLineTimeTable(classLeactuers,PrintBy.CLASS);
		System.out.println("***********Class "+sclass.getId()+" timeTable:******");
		classTable.print();
	}
	public void printByClasses()
	{
		List<SchoolClass> classes=timeTable.getClasses();
		Entity.sortById(classes);
		for(SchoolClass sclass:classes)
		{
			printByClass(sclass);
			
		}
		
	}
	public void printByTeachers()
	{
	  
		List<Teacher> teachers=timeTable.getTeachers();
		Entity.sortById(teachers);
		for(Teacher teacher:teachers)
		{
			printByTeacher(teacher);
		}
	}
	private ArrayList<Leacture> createRawLeactures()
	{
		ArrayList<Leacture> leactures=new ArrayList<Leacture>(timeTable.getLeactures());
		Collections.sort(leactures, new Comparator<Leacture>() {
		    @Override
		    public int compare(Leacture o1, Leacture o2) {
		       
		    	return compareLeactures(o1, o2);
		    }
		});
		return leactures;
		
	}
	public void printRaw()
	{
		ArrayList<Leacture>leactures=createRawLeactures();
		for(Leacture leacture:leactures)
		{
			System.out.println(leacture);
		}
	}
	public void printRulesSummery()
	{
		List<Rule>rules=timeTable.getRules();
		CommandLineTable rulesSummeryTable=new CommandLineTable();
		rulesSummeryTable.setHeaders("rule number","name","type","grade","effect on fitness","max effect on fitness");
		int ruleNumber=1;
		for(Rule rule:rules)
		{
			RuleResult ruleResult= rule.applyOnDNA(timeTable);
			double grade = ruleResult.getScore();
			double effectOnFitness= 0;
			double maxEffectoOnFitnessPossibleOfRule=0;
			if(timeTable.getMaximumScorePossibleFromRules()!=0)
			{
				maxEffectoOnFitnessPossibleOfRule=ruleResult.getWeight()/timeTable.getMaximumScorePossibleFromRules();
				effectOnFitness=ruleResult.getWeightedScore()/timeTable.getMaximumScorePossibleFromRules();
			}
			rulesSummeryTable.addRow(""+ruleNumber++,rule.toString(),
					rule.getType().toString(),grade+"",effectOnFitness+"",maxEffectoOnFitnessPossibleOfRule+"");
			
		}
		
		
		rulesSummeryTable.print();
		DecimalFormat df = new DecimalFormat();
		final int presicsionAfterThePoint=1;
		df.setMaximumFractionDigits(presicsionAfterThePoint);
		System.out.println("hard rules average score between [0,100]  is "+df.format(timeTable.getAverageHardRules()*100));
		System.out.println("soft rules average score between [0,100]  is "+df.format(timeTable.getAverageSoftRules()*100));
	}
	private int compareLeactures(Leacture leacture1,Leacture leacture2)
	{
		int [] [] compareBy= {
				{leacture1.getDay(),leacture1.getHour(),leacture1.getClassToTeach().getId(),
					leacture1.getTeacher().getId(),leacture1.getCourse().getId()},
				{leacture2.getDay(),leacture2.getHour(),leacture2.getClassToTeach().getId(),
						leacture2.getTeacher().getId(),leacture2.getCourse().getId()}
				
		};
	   for(int i=0; i<compareBy[0].length; i++)
	   {
		   if(compareBy[0][i]<compareBy[1][i])
		   {
			   return -1;
		   }
		   else if(compareBy[0][i]>compareBy[1][i])
		   {
			   return 1;
		   }
	   }
	   return 0;
	}
	

}
