package MainTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import console.jaxb.schema.timetable.XmlApiTimeTable;
import evolution.algorithm.DNA;
import evolution.algorithm.EvolutionAlgorithmEngine;
import evolution.algorithm.selection.RouleteWheelSelection;
import evolution.algorithm.stopcondition.StopByFitness;
import evolution.algorithm.stopcondition.StopByGenerations;
import evolution.factory.EvolutionAlgorithmDefaultFactory;
import evolution.timetable.BestTimeTableSolutionDetails;
import evolution.timetable.EvolutionTimeTableProblemEngine;
import evolution.timetable.TimeTableProblem;
import evolution.timetable.TimeTableSolution;
import evolution.timetable.rule.DayOffTeacherRule;
import evolution.timetable.rule.RuleResult;
import evolution.timetable.rule.RuleType;
import timers.MyTimer;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
     
	          XmlApiTimeTable xmlApi=new XmlApiTimeTable(2);

          try
          {
        	
        	 ArrayList<Integer>ok=new ArrayList<>();
        	
        	  TimeTableProblem timeTableProblem=xmlApi.loadTimeTableProblem("src/files/ex1-small.xml");
        	  EvolutionTimeTableProblemEngine engine = new EvolutionTimeTableProblemEngine(EvolutionAlgorithmDefaultFactory.createDefaultConfiguration(), timeTableProblem);
        	  System.out.println("started");
        	 
        	 
        	  
        	//  engine.getAlgorithmConfiguration().getSelection().setElitism(10);
        	   engine.getAlgorithmConfiguration().getStopConditoins().add(new StopByGenerations(150));
        	   System.out.println("wired");
        	   engine.RunAndWait();
        	   BestTimeTableSolutionDetails bestSolution=engine.getBestSolution();
         	  System.out.println("generation "+bestSolution.getGeneration());
         	  System.out.println("fitnes "+bestSolution.getSolution().getFitness());
         	  TimeTablePrinter printer =new TimeTablePrinter(bestSolution.getSolution());
         	   TimeTableSolution solutoin=bestSolution.getSolution();
         	   
         	   System.out.println("number of leactuers="+solutoin.getLeactures().size());
         	  printer.printRulesSummery();
         	  printer.printByTeachers();
        	
          }
          catch(Exception e)
          {
        	  List<String>errors=xmlApi.getAllErrors();
        	  System.out.println(e.getMessage());
        	  System.out.println("**errors***");
        	  for(String error:errors)
        	  {
        		  System.out.println(error);
        	  }
          }
       
	}

}
