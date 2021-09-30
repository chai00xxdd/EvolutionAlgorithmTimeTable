package Validations;

import java.util.function.Predicate;

import evolution.algorithm.EvolutionAlgorithm;
import evolution.algorithm.Mutation;
import evolution.algorithm.crossover.CrossOver;
import evolution.algorithm.selection.RouleteWheelSelection;
import evolution.algorithm.selection.Selection;
import evolution.algorithm.selection.SelectionName;
import evolution.algorithm.selection.TournemantSelection;
import evolution.algorithm.selection.TruncationSelection;
import evolution.algorithm.stopcondition.StopByFitness;
import evolution.algorithm.stopcondition.StopByGenerations;
import evolution.algorithm.stopcondition.StopByTime;
import evolution.algorithm.stopcondition.StopCondition;
import evolution.algorithm.stopcondition.StopConditionName;
import evolution.timetable.crossover.AspectOrientation;
import evolution.timetable.crossover.AspectOrientedCrossOver;
import evolution.timetable.crossover.CrossOverName;
import evolution.timetable.crossover.DayTimeOrientedCrossOver;
import evolution.timetable.mutation.FlippingComponent;
import evolution.timetable.mutation.FlippingMutation;
import evolution.timetable.mutation.MutationName;
import evolution.timetable.mutation.SizerMutation;


public class EvolutionAlgorithmValidator {
	
	private static int MaxPopultion = 1000;
	
	public static int getValidPopulation(String populationString) throws Exception
	{
		int population = 2;
		try
		{
		 population = Integer.parseInt(populationString);
		
		if(population  < 2 || population > MaxPopultion)
		{
			throw new Exception();
		}
		
		}
		catch(Exception e)
		{
			throw new Exception("population must be  in range [2,1000]");
			
		}
		
		return population;
	}
	
	public static int getEllitism(String ellitismString) throws Exception
	{
		int ellitism = 0;
		
		try
		{
			ellitism = Integer.parseInt(ellitismString);
			if(ellitism < 0)
			{
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			throw new Exception ("Ellitism must be atlist 0 (integer)");
		}
		
		
		return ellitism;
	}
	

	
	public static int getTopPercent(String topPercentString) throws Exception
	{
		
      int topPercent = 0;
		
		try
		{
			topPercent = Integer.parseInt(topPercentString);
			if(topPercent <=0 || topPercent >100)
			{
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			throw new Exception ("TopPercent must be integer in range [1,100]");
		}
		
		
		return topPercent;
		
	}
	
	public static double getPreDefinedQualifer(String PreDefindQualiferString) throws Exception
	{
		
		 double PTE = 0;
			
			try
			{
				PTE = Double.parseDouble(PreDefindQualiferString);
				if(PTE <0 || PTE >1)
				{
					throw new Exception();
				}
			}
			catch(Exception e)
			{
				throw new Exception ("PTE must be in range [0,1]");
			}
			
			
			return PTE;
		
	}
	
	public static Selection getSelection(String selectionNameString,String ellitisemString,String parameter) throws Exception
	{
		int ellitism = EvolutionAlgorithmValidator.getEllitism(ellitisemString);
		SelectionName selectionName = SelectionName.getByName(selectionNameString);
	
		
		Selection newSelection = null;
		if(selectionName == SelectionName.RouletteWheel)
		{
			newSelection = new RouleteWheelSelection();
			
		}
		
		else if(selectionName ==SelectionName.Tournemant)
		{
			double pte = EvolutionAlgorithmValidator.getPreDefinedQualifer(parameter);
			newSelection = new TournemantSelection(pte);
		}
		
		else if(selectionName == SelectionName.Truncation)
		{
			int topPercent = EvolutionAlgorithmValidator.getTopPercent(parameter);
			newSelection = new TruncationSelection(topPercent);
		}
		else
		{
			throw new Exception("Selection Name Doesnt Supported!!!");
		}
		
		newSelection.setElitism(ellitism);
		
		return newSelection;
	}
	
	//CrossOver
	
	public static int getValidCuttingPoints(String cuttingPointsString) throws Exception
	{
		int cuttingPoints = 0;
		
		try
		{
			cuttingPoints = Integer.parseInt(cuttingPointsString);
			if(cuttingPoints <= 0)
			{
				throw new Exception();
			}
		}
		catch(Exception e)
		{
			throw new Exception ("Cutting Points must be atlist 1 (integer)");
		}
		
		return cuttingPoints;
	}

	public static CrossOver getValidCrossOver(String crossoverNameString, String cuttingPointsString, String parameter) throws Exception {
		// TODO Auto-generated method stub
		CrossOver crossover = null;
		int cuttingPoints = getValidCuttingPoints(cuttingPointsString);
		CrossOverName crossOverName = CrossOverName.getByName(crossoverNameString);
		
		
		if(crossOverName ==CrossOverName.DayTimeOriented)
		{
			crossover = new DayTimeOrientedCrossOver(cuttingPoints);
		}
		else if(crossOverName == CrossOverName.AspectOriented)
		{
		   AspectOrientation orientation = AspectOrientation.getAspectByName(parameter);
		   if(orientation == null)
		   {
			   throw new Exception("orientation name doesnt supported dude!!!!");
		   }
		   crossover = new AspectOrientedCrossOver(cuttingPoints, orientation);
		}
		
		else
		{
			throw new Exception("crossover name not supported!!!");
		}
		
		return crossover;
	}

	public static int getValidTotalTupplesToSizer(String totalTupplesString) throws Exception
	{
		
           int totalTupples = 0;
		
		try
		{
			totalTupples = Integer.parseInt(totalTupplesString);
			if(totalTupples == 0)
			{
				throw new Exception();
			}
	
		}
		catch(Exception e)
		{
			throw new Exception ("TotalTupples must be integer != 0");
		}
		
		return totalTupples;
		
	}
	
	public static int getMaxTupples(String maxTupplesString) throws Exception
	{

        int maxTupples = 0;
		
		try
		{
			maxTupples = Integer.parseInt(maxTupplesString);
			
			if(maxTupples < 1)
			{
				throw new Exception();
			}
			
	
		}
		catch(Exception e)
		{
			throw new Exception ("MaxTupples must be atlist 1 (integer)");
		}
		
		return maxTupples;
	}
	
	public static double getValidProbality(String probalityString) throws Exception
	{
		 double probality = 0;
			
			try
			{
				probality = Double.parseDouble(probalityString);
				if(probality <0 || probality >1)
				{
					throw new Exception();
				}
			}
			catch(Exception e)
			{
				throw new Exception ("Probality must be in range [0,1]");
			}
			
			
			return probality;
		
	}
	
	public static Mutation getValidMutation(String mutationNameString, String probalityString, String parameter,String component) throws Exception {
		
		Mutation mutation = null;
		MutationName mutationName = MutationName.getByName(mutationNameString);
		double probality = getValidProbality(probalityString);
		
		
		if(mutationName == MutationName.Sizer )
		{
			int totalTupples = getValidTotalTupplesToSizer(parameter);
			mutation = new SizerMutation(totalTupples, probality);
			
			 
		}
		else if(mutationName == MutationName.Flipping)
		{
			FlippingComponent flippingComponent  = FlippingComponent.getFlippingComponentByChar(component.substring(0,1));
			if(flippingComponent == null)
			{
				throw new Exception("Flipping Component Doesnt Supported");
			}
			
			int maxTupplesToFlip = getMaxTupples(parameter);
			
			mutation = new FlippingMutation(maxTupplesToFlip, probality, flippingComponent);
			
		}
		else
		{
			throw new Exception("mutationName doesnt supported");
		}
		
		return mutation;
	}

	//StopConditoins
	public static double getValidFitness(String fitnessString) throws Exception
	{
		
		try
		{
			double fitness = 0;
			fitness = getValidProbality(fitnessString);
			return fitness;
		}
		catch(Exception e)
		{
			throw new Exception("Fitness must be in range [0,1]");
		}
		
		
	}
	public static int getValidGenerations(String generationsString) throws Exception
	{
		try
		{
			int generations = 0;
			generations = Integer.parseInt(generationsString);
			if(generations < 1)
			{
				throw new Exception();
			}
			
			return generations;
		}
		catch(Exception e)
		{
			throw new Exception("Generations must be atlist 1 (integer)");
		}
		
		
	}
	
	public static int getValidTimeSeconds (String timeString) throws Exception
	{
		try
		{
			int timeInMs = Integer.parseInt(timeString);
			if(timeInMs < 1)
			{
				throw new Exception();
			}
			
			return timeInMs;
		}
		catch(Exception e)
		{
			throw new Exception("Time  must be atlist 1 seconds ");
		}
	}
	
	public static boolean getValidEnabled(String enabledString) throws Exception
	{
		try
		{
			boolean enabled = Boolean.parseBoolean(enabledString);
			return enabled;
		}
		catch(Exception e)
		{
			throw new Exception("Enabled must be true/false");
		}
	}
	
	public static Predicate<EvolutionAlgorithm> getValidStopCondition(String stopConditionNameString, String paramter,
			String enabledString) throws Exception {
		
		final int MiliSecondsInSeconds = 1000;
		Predicate<EvolutionAlgorithm> stopCondition = null;
		StopConditionName stopConditionName = StopConditionName.getByName(stopConditionNameString);
		boolean enabled = getValidEnabled(enabledString);
		
		if(stopConditionName == StopConditionName.Fitness)
		{
			double fitness = getValidFitness(paramter);
			stopCondition = new StopByFitness(fitness);
		}
		else if(stopConditionName == StopConditionName.Generations)
		{
			int generationsLimit = getValidGenerations(paramter);
			stopCondition = new StopByGenerations(generationsLimit);
		}
		else  if (stopConditionName == StopConditionName.Time)
		{
			int timeToStop = getValidTimeSeconds(paramter) *MiliSecondsInSeconds;
			stopCondition = new StopByTime((int)timeToStop);
		}
		else
		{
			throw new Exception("StopCondition Name not Supported!!!");
		}
		
		((StopCondition) stopCondition).setEnabled(enabled);
		
		
		return stopCondition;
	}
	
	

}
