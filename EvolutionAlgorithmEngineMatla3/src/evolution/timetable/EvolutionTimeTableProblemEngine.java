package evolution.timetable;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.sun.javafx.css.parser.StopConverter;

import DTO.AlgorithmConfuigrationDTO;
import DTO.StopConditionDTO;
import evolution.algorithm.AlgorithmProgress;
import evolution.algorithm.AlgorithmState;
import evolution.algorithm.BestDnaDetails;
import evolution.algorithm.EvolutionAlgorithm;
import evolution.algorithm.EvolutionAlgorithmConfiguration;
import evolution.algorithm.EvolutionAlgorithmEngine;
import evolution.algorithm.stopcondition.StopConditionName;

public class EvolutionTimeTableProblemEngine implements EvolutionAlgorithmEngine {
	
	private EvolutionAlgorithmConfiguration algorithmConfiguration=null;
	private TimeTableProblem timeTableProblem=null;
	private EvolutionAlgorithm algorithm =null;
	private boolean algorithmRunnedAtListOnce=false;
	private boolean saveEachGeneration = false;
	private Thread algorithmThread=new Thread();
	private BestTimeTableSolutionDetails bestSolutionDetails=null;
	private List<BestTimeTableSolutionDetails>bestOfEachGeneration=new ArrayList<>();
	private List<Consumer<EvolutionAlgorithmEngine>> onNewGenerationListeners = new ArrayList<>();
	private List<Consumer<EvolutionAlgorithmEngine>> onAlgorithmStartListeners = new ArrayList<>();
	private List<Consumer<EvolutionAlgorithmEngine>> onAlgorithmStopListeners = new ArrayList<>();
	private List<AlgorithmProgress> algorithmProgressGraph = new ArrayList<>();
	private int generationSkip = 1;
	//private AlgorithmProgress algorithmProgress = new AlgorithmProgress(0,0,0,AlgorithmState.Stopped) ;
	public EvolutionTimeTableProblemEngine(EvolutionAlgorithmConfiguration algorithmConfiguration,TimeTableProblem timeTableProblem)
	{
		this.algorithmConfiguration=algorithmConfiguration;
		this.timeTableProblem=timeTableProblem;
	}
	
	
	
	public synchronized List<AlgorithmProgress> getAlgorithmProgressGraph() {
		return new ArrayList<>(algorithmProgressGraph);
	}



	private void notifyListeners(List<Consumer<EvolutionAlgorithmEngine>> listeners)
	{
		listeners.forEach(listener -> listener.accept(this));
	}
	
	private  void notifyOnNewGenerationListeners()
	{
		
		notifyListeners(onNewGenerationListeners);
	}
	
	private void notifyOnAlgorithmStartListeners()
	{
		
		notifyListeners(onAlgorithmStartListeners);
	}
	private void notifyOnAlgorithmStopListeners()
	{
		
		notifyListeners(onAlgorithmStopListeners);
	}
	
	
	

	public int getGenerationSkip() {
		return generationSkip;
	}



	public void setGenerationSkip(int generationSkip) {
		if(generationSkip < 1)
		{
			throw new IllegalArgumentException("generation skips must be atlist 1");
		}
		this.generationSkip = generationSkip;
	}

   public synchronized BestDnaDetails getBestDnaDetails()
   {
	   BestTimeTableSolutionDetails bestTimeTableDetails = getBestSolution();
	   if(bestTimeTableDetails == null)
	   {
		   return null;
	   }
	   return new BestDnaDetails(bestTimeTableDetails.getSolution(),bestTimeTableDetails.getGeneration(),bestTimeTableDetails.getTimePassed());
   }
   
   

	public synchronized BestTimeTableSolutionDetails getBestSolutionByGeneration(int generationNumber)
	{
		if(generationNumber < 0 || generationNumber >= bestOfEachGeneration.size())
		{
			return null;
		}
		
		return bestOfEachGeneration.get(generationNumber);
	}
    public synchronized List<BestTimeTableSolutionDetails> getBestOfEachGenerations()
    {
    	return new ArrayList<>(bestOfEachGeneration);
    }
	public EvolutionAlgorithmConfiguration getAlgorithmConfiguration() {
		return algorithmConfiguration;
	}
	
	public void removeStopCondition(Class stopConditionsClassToRemove)
	{
		List<Predicate<EvolutionAlgorithm>> conditionsToRemove = new ArrayList<>();
		for(Predicate<EvolutionAlgorithm> stop : algorithmConfiguration.getStopConditoins())
		{
			if(stop.getClass().equals(stopConditionsClassToRemove))
			{
				conditionsToRemove.add(stop);
			}
		}
		
		for(Predicate<EvolutionAlgorithm> stopConditionToRemove : conditionsToRemove)
		{
			algorithmConfiguration.getStopConditoins().remove(stopConditionToRemove);
		}
	}
	
	public void AddOrUpdateStopCondition(Predicate<EvolutionAlgorithm> stopCondition)
	{
		algorithmConfiguration.addOrUpdateStopCondition(stopCondition);
	}

	public void setAlgorithmConfiguration(EvolutionAlgorithmConfiguration algorithmConfiguration) {
		this.algorithmConfiguration = algorithmConfiguration;
	}
	
	public boolean isAlgorithmReachedStopConditions()
	{
		if(algorithm ==null)
		{
			return false;
		}
		
		return algorithm.isFinished();
	}

	public TimeTableProblem getTimeTableProblem() {
		return timeTableProblem;
	}

	public void setTimeTableProblem(TimeTableProblem timeTableProblem) {
		this.timeTableProblem = timeTableProblem;
	}
	public boolean isAlgorithmRunning()
	{
		return algorithmThread.isAlive();
	}
	
	
	public synchronized void runAlgorithm() 
	{
		
		if(isAlgorithmRunning())
		{
			return;
		}
		
		synchronized (this) {
			bestOfEachGeneration.clear();
			bestSolutionDetails=null;
			algorithmProgressGraph.clear();
		}
		
		
		
		
		
		algorithm=new EvolutionAlgorithm(timeTableProblem.createTimeTableDNA(), algorithmConfiguration.shallowCopy())
				{

					@Override
					public void onNewGeneration() {
						// TODO Auto-generated method stub
					
						synchronized (EvolutionTimeTableProblemEngine.this) {
						 // System.out.println("hello sir");
							algorithmRunnedAtListOnce=true;
							TimeTableSolution bestSoltuion= (TimeTableSolution)algorithm.getBestDNACopy();
							int generatoinSolutionCreated= algorithm.getHowManyGenerationsCreated();
							bestSolutionDetails=new BestTimeTableSolutionDetails(bestSoltuion, generatoinSolutionCreated, this.getTimePassed());
							
							notifyOnNewGenerationListeners();
							
							if(saveEachGeneration)
							{
							bestOfEachGeneration.add(bestSolutionDetails);
							}
							
						}
						
						//update config here
						algorithm.updateConfiguration(algorithmConfiguration.shallowCopy());
					}
			
				};
		
		algorithmThread = new Thread(()-> {
			EvolutionTimeTableProblemEngine.this.notifyOnAlgorithmStartListeners();
			algorithm.run();
			EvolutionTimeTableProblemEngine.this.notifyOnAlgorithmStopListeners();
		});		
		algorithmThread.setDaemon(true);
		algorithmThread.start();
		
	}
	
	public void RunAndWait()
	{
		if(isAlgorithmRunning())
		{
			return;
		}
	
		try {
			runAlgorithm();
			algorithmThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized BestTimeTableSolutionDetails getBestSolution()
	{
		return  bestSolutionDetails;
	}
	public void stopAlgorithm()
	{
		if(isAlgorithmRunning())
		{
			if(algorithm!=null)
			algorithm.stop();
			try {
				algorithmThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public synchronized  int getNumberOfGenerations()
	{
		return bestOfEachGeneration.size();
	}
	public boolean isAlgorithmRunnedAtListOnce() {
		return algorithmRunnedAtListOnce;
	}
	public EvolutionAlgorithm getAlgorithm() {
		return algorithm;
	}
	public void resumeAlgorithmAndWait() {
	
		
		if(isAlgorithmRunning()||algorithm==null)
		{
			return;
		}
		resumeAlgorithm();
		try
		{
		algorithmThread.join();
		}
		catch(Exception e)
		{
			
		}
		
	
	}
	
	public synchronized void resumeAlgorithm()
	{
		if(isAlgorithmRunning() || algorithm ==null || !algorithmRunnedAtListOnce )
		{
			return;
		}
	algorithm.updateConfiguration(algorithmConfiguration.shallowCopy());	
	algorithmThread = new Thread(()-> {
		 EvolutionTimeTableProblemEngine.this.notifyOnAlgorithmStartListeners();
		algorithm.resume();
		 EvolutionTimeTableProblemEngine.this.notifyOnAlgorithmStopListeners();
		});
	algorithmThread.setDaemon(true);
		algorithmThread.start();
	}
	
	public synchronized BestTimeTableSolutionDetails nextBestSolutionByGenerationsSkip(int generationNumber)
	{
		if(generationNumber < 0 || generationNumber >= bestOfEachGeneration.size()-1)
		{
			return null;
		}
		BestTimeTableSolutionDetails bestNextSolution = null;
		if(generationNumber + generationSkip > bestOfEachGeneration.size() - 1)
		{
		  bestNextSolution = bestOfEachGeneration.get(bestOfEachGeneration.size()-1);
		}
		else
		{
			bestNextSolution = bestOfEachGeneration.get(generationNumber+generationSkip);
		}
		
		return bestNextSolution;
		
	}
	
	public synchronized BestTimeTableSolutionDetails previousBestSolutionByGenerationsSkip(int generationNumber)
	{
		if(generationNumber <= 0 || generationNumber > bestOfEachGeneration.size()-1)
		{
			return null;
		}
		
		BestTimeTableSolutionDetails bestPreviousSolution = null;
		if(generationNumber % generationSkip!=0)
		{
			generationNumber -= generationNumber%generationSkip;
		}
		else
		{
			generationNumber -= generationSkip;
		}
		
		bestPreviousSolution = bestOfEachGeneration.get(generationNumber);
		
		return bestPreviousSolution;
	}



	@Override
	public void addOnNewGenerationListener(Consumer<EvolutionAlgorithmEngine> listener) {
		// TODO Auto-generated method stub
		  onNewGenerationListeners.add(listener);
	}



	@Override
	public void removeOnNewGenerationListener(Consumer<EvolutionAlgorithmEngine> listener) {
		// TODO Auto-generated method stub
		 onNewGenerationListeners.remove(listener);
	}


   private synchronized void updateStopConditionsProgress()
   {
	 
	   if(algorithm!=null && !isAlgorithmRunning())
	   {
		   algorithmConfiguration.updateStopConditionsProgress(algorithm);
		 
	   }
	   else if(algorithm == null)
	   {
		   algorithmConfiguration.clearStopConditionsProgress();
		  
	   }
	   
	   
   }
	@Override
	public synchronized AlgorithmProgress getAlgorithmProgress() {
            AlgorithmState algorithmState = AlgorithmState.Stopped;
            if(algorithm != null)
            {
            	algorithmState = algorithm.getState();
            
            	
            }
        	updateStopConditionsProgress();
            AlgorithmProgress progress = new AlgorithmProgress(0,0,0,algorithmState,algorithmConfiguration.getStopConditionsDTO());
            if(bestSolutionDetails != null)
            {
            	
            	//System.out.println(bestSolutionDetails.getGeneration());
            	progress.setFitness(bestSolutionDetails.getSolution().getFitness());
            	progress.setTimePassed(bestSolutionDetails.getTimePassed());
            	progress.setGeneration(bestSolutionDetails.getGeneration());
            	
            	AlgorithmProgress lastProgress = null;
            	if(algorithmProgressGraph.size() != 0)
            	{
            		lastProgress = algorithmProgressGraph.get(algorithmProgressGraph.size()-1);
            	}
            	
            	if(progress.getFitness() != 0)
            	{
            		boolean addToGraph = lastProgress == null || lastProgress.getGeneration() != progress.getGeneration();
            		if(addToGraph)
            		{
            			algorithmProgressGraph.add(progress);
            		}
            	}
            }
           return progress;
	}





	@Override
	public  void pauseAlgorithm() {
		
		if(algorithm==null &&!isAlgorithmRunnedAtListOnce())
		{
			return;
		}
		
		if(isAlgorithmRunning())
		{
			if(algorithm!=null)
			algorithm.pause();
			try {
				algorithmThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	@Override
	public List<Consumer<EvolutionAlgorithmEngine>> getOnAlgorithmStartListeners() {
		// TODO Auto-generated method stub
		return onAlgorithmStartListeners;
	}

	@Override
	public List<Consumer<EvolutionAlgorithmEngine>> getOnAlgorithmStopListeners() {
		// TODO Auto-generated method stub
		return onAlgorithmStopListeners;
	}
	
	
	//New Confuigration features
	
	public AlgorithmConfuigrationDTO getConfuigrationDTO()
	{
		return new AlgorithmConfuigrationDTO(algorithmConfiguration.shallowCopy());
		
	}
	
}
