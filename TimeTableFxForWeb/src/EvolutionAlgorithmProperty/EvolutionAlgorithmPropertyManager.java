package EvolutionAlgorithmProperty;
import java.util.List;
import java.util.function.Predicate;

import DTO.StopConditionDTO;
import evolution.algorithm.AlgorithmProgress;
import evolution.algorithm.AlgorithmState;
import evolution.algorithm.BestDnaDetails;
import evolution.algorithm.EvolutionAlgorithm;
import evolution.algorithm.EvolutionAlgorithmConfiguration;
import evolution.algorithm.EvolutionAlgorithmEngine;
import evolution.algorithm.stopcondition.StopByFitness;
import evolution.algorithm.stopcondition.StopByGenerations;
import evolution.algorithm.stopcondition.StopByTime;
import evolution.algorithm.stopcondition.StopConditionName;
import evolution.timetable.BestTimeTableSolutionDetails;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class EvolutionAlgorithmPropertyManager {

	private IntegerProperty generations =new SimpleIntegerProperty(0);
	private IntegerProperty algorithmRunTimeInMs = new SimpleIntegerProperty(0);
	private DoubleProperty bestFitness = new SimpleDoubleProperty(0);
	
	private BooleanProperty isAlgorithmRunning = new SimpleBooleanProperty(false);
	private BooleanProperty isAlgorithmRunnedAtlistOnceProperty = new SimpleBooleanProperty();
	private BooleanProperty isThereAnyStopConditions = new SimpleBooleanProperty();
	
	private DoubleProperty timeLimit = new SimpleDoubleProperty(0);
	private DoubleProperty generationsLimit = new SimpleDoubleProperty(0);
	private DoubleProperty fitnessLimit = new SimpleDoubleProperty(0);
	
	private BooleanProperty isStopByFitnessExists = new SimpleBooleanProperty(false);
	private BooleanProperty isStopByGenerationsExists = new SimpleBooleanProperty(false);
	private BooleanProperty isStopByTimeExists = new SimpleBooleanProperty(false);
	private BooleanProperty algorithmReachedStopCondition = new SimpleBooleanProperty(false);
	
	
	public BooleanProperty algorithmReachedStopConditionProperty()
	{
		return algorithmReachedStopCondition;
	}
	
	public void setAlgorithmRunnedAtlistOnce(boolean isRunnedAtlistOnce)
	{
		 isAlgorithmRunnedAtlistOnceProperty.set(isRunnedAtlistOnce);
	}
	
	public void setIsAlgorithmRunning(boolean running)
	{
		 IsAlgorithmRunningProperty().set(running);
	}
	
	
	public BooleanProperty IsStopByFitnessExistsProperty() {
		return isStopByFitnessExists;
	}

	public BooleanProperty IsStopByGenerationsExistsProperty() {
		return isStopByGenerationsExists;
	}

	public BooleanProperty IsStopByTimeExistsProperty() {
		return isStopByTimeExists;
	}

	public DoubleProperty timeLimitInMsProperty() {
		return timeLimit;
	}

	public DoubleProperty generationsLimitProperty() {
		return generationsLimit;
	}

	public DoubleProperty fitnessLimitProperty() {
		return fitnessLimit;
	}
 
	public void update(EvolutionAlgorithmEngine engine)
	{
		
			generations.set(engine.getNumberOfGenerations());
			algorithmRunTimeInMs.set(engine.getBestDnaDetails().getTimeCreated());
			if(engine.getBestDnaDetails() != null)
			{
			bestFitness.set(engine.getBestDnaDetails().getFitness());
			}
			
			isAlgorithmRunning.set(engine.isAlgorithmRunning());
			isAlgorithmRunnedAtlistOnceProperty.set(engine.isAlgorithmRunnedAtListOnce());
			isThereAnyStopConditions.set(engine.getAlgorithmConfiguration().getStopConditoins().size() > 0);
			 
	} 
	
	public void updateProgress(BestDnaDetails bestDna)
	{
		isAlgorithmRunnedAtlistOnceProperty.set(true);
		generations.set(bestDna.getGeneration());
		bestFitness.set(bestDna.getDna().getFitness());
		algorithmRunTimeInMs.set(bestDna.getTimeCreated());
	}
	
	public void clear()
	{
		
			generations.set(0);
			algorithmRunTimeInMs.set(0);
			bestFitness.set(0);			
			isAlgorithmRunning.set(false);
			isAlgorithmRunnedAtlistOnceProperty.set(false);
			isThereAnyStopConditions.set(false);
			clearStopConditionsExists();
	}
	
	private void clearStopConditionsExists()
	{	
		
		timeLimit.set(0);
		generationsLimit.set(0);
		fitnessLimit.set(0);
		isStopByFitnessExists.set(false);
		isStopByGenerationsExists.set(false);
		isStopByTimeExists.set(false);
		
	}
	
	
	
	public BooleanProperty isThereAnyStopConditionsProperty()
	{
		return isThereAnyStopConditions;
	}
	public void updateConfiguration (EvolutionAlgorithmConfiguration algorithmConfiguration)
	{
		clearStopConditionsExists();
		isThereAnyStopConditions.set(algorithmConfiguration.getStopConditoins().size() > 0);
		for(Predicate<EvolutionAlgorithm> stopCondition : algorithmConfiguration.getStopConditoins())
		{
			if(stopCondition instanceof StopByFitness)
			{
				isStopByFitnessExists.set(true);
				fitnessLimit.set(((StopByFitness)stopCondition).getFitness());
			}
			else if(stopCondition instanceof StopByGenerations)
			{
				int generationsUpperBound = ((StopByGenerations)stopCondition).getGenerationsLimit();
				generationsLimit.set(generationsUpperBound);
				isStopByGenerationsExists.set(true);
			}
			else if(stopCondition instanceof StopByTime)
			{
				int timeToStop = ((StopByTime)stopCondition).getTimeLimitInMs();
				timeLimit.set(timeToStop);
				isStopByTimeExists.set(true);
			}
		}
		
	}
	
	
	public IntegerProperty generationsProperty()
	{
		return generations;
	}

	public IntegerProperty algorithmRunTimeInMsProperty() {
		return algorithmRunTimeInMs;
	}

	public DoubleProperty bestFitnessProperty() {
		return bestFitness;
	}

	public BooleanProperty IsAlgorithmRunningProperty() {
		return isAlgorithmRunning;
	}

	public BooleanProperty IsAlgorithmRunnedAtlistOnceProperty() {
		return isAlgorithmRunnedAtlistOnceProperty;
	}
	
	public void updateProperties(AlgorithmProgress progress)
	{
		generations.set(progress.getGeneration());
		algorithmRunTimeInMs.set(progress.getTimePassed());
		bestFitness.set(progress.getFitness());
		isAlgorithmRunning.set(progress.getAlgorithmState() == AlgorithmState.Running);
		isAlgorithmRunnedAtlistOnceProperty.set(progress.getFitness() != 0);
		isThereAnyStopConditions.set(!progress.isNoStopConditions());
		algorithmReachedStopCondition.set(progress.isStopConditionReached());
		
	   List<StopConditionDTO> stopConditoins = progress.getStopConditionsProgress();
	   boolean fitnesExists = false;
	   boolean generationExists = false;
	   boolean timeExists = false;
	   
	     for(StopConditionDTO stopCond : stopConditoins)
	     {
		   String name = stopCond.getName();
		   double stopCondProgress = stopCond.getProgress();
		   double stopCondLimit = Double.parseDouble(stopCond.getValue());
		   if(name.toUpperCase().equals(StopConditionName.Fitness.toString().toUpperCase()))
		   {
			   fitnesExists = stopCond.isEnabled();
			   fitnessLimit.set(stopCondLimit);
			  
					   
		   }
		   
		   else if(name.toUpperCase().equals(StopConditionName.Time.toString().toUpperCase()))
		   {
			   timeExists = stopCond.isEnabled();   
			   timeLimit.set(stopCondLimit);
		   }
		   
		   else if (name.toUpperCase().equals(StopConditionName.Generations.toString().toUpperCase()))
		   {
			   generationExists = stopCond.isEnabled();
			   generationsLimit.set(stopCondLimit);
			   
		   }
		   
	     }
	     
	     isStopByFitnessExists.set(fitnesExists);
	     isStopByGenerationsExists.set(generationExists);
          isStopByTimeExists.set(timeExists);	  
		
		
	}
	
	
}
