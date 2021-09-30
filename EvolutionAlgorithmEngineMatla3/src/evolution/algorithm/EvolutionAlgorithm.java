package evolution.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.sun.org.apache.xerces.internal.parsers.CachingParserPool.SynchronizedGrammarPool;

import evolution.algorithm.crossover.CrossOver;
import evolution.algorithm.selection.SelectedFathers;
import evolution.algorithm.selection.Selection;
import timers.MyTimer;


public class EvolutionAlgorithm {
	
	public List<Mutation> mutations=new ArrayList<>();
	public CrossOver crossover=null;
	public Selection selection=null;
	public List<DNA> population=new ArrayList<DNA>();
	public List<Predicate<EvolutionAlgorithm>> stopConditions =new ArrayList<>(); 
	public int populationSize;
	public int howManyGenerationsCreated=0;
	private List<DNA> bestOfEachGenerations=new ArrayList<>();
	private boolean forceStop=false;
	private boolean isRunnedAtlistOnce = false;
	DNA dnaCreator;
	private MyTimer timer=new MyTimer();
	private AlgorithmState state = AlgorithmState.Stopped;
	private List<Consumer<EvolutionAlgorithm>> OnNewGenerationListeners = new ArrayList<>();
	
	public EvolutionAlgorithm(DNA dnaCreator,EvolutionAlgorithmConfiguration configuration)
	{
		this(configuration.getPopulationSize()
				,configuration.getCrossover(),
				configuration.getSelection(),configuration.getMutations(),dnaCreator);
		this.stopConditions= configuration.getStopConditoins();
		this.OnNewGenerationListeners = configuration.getNewGenerationListeners();
		
		
	}
	
	
	
	
	
	public synchronized AlgorithmState getState() {
		return state;
	}



	public void addOnNewGenerationListener(Consumer<EvolutionAlgorithm> listener)
	{
		OnNewGenerationListeners.add(listener);
	}
	
	public void removeOnNewGenerationListener(Consumer<EvolutionAlgorithm> listener)
	{
		OnNewGenerationListeners.remove(listener);
	}
	
	
	private void invokeOnNewGenerationListeners()
	{
		OnNewGenerationListeners.forEach(listener -> listener.accept(this));
	}
	
	
	public boolean isRunnedAtlistOnce() {
		return isRunnedAtlistOnce;
	}



	public void updateConfiguration(EvolutionAlgorithmConfiguration confuigration)
	{
		this.crossover=confuigration.getCrossover();
		 this.selection=confuigration.getSelection();
		 confuigration.getPopulationSize();
		 updatePopulationSize(confuigration.getPopulationSize());
		 this.mutations=confuigration.getMutations();
		 this.stopConditions= confuigration.getStopConditoins();
		 this.OnNewGenerationListeners = confuigration.getNewGenerationListeners();
		  
	}
	
	private void updatePopulationSize(int newPopulationSize)
	{
		this.populationSize = newPopulationSize;
		List<DNA> newPopulation = new ArrayList<>();
		int index = population.size() -1;
		while(index>=0 && newPopulationSize-- >0)
		{
			newPopulation.add(0,population.get(index--));
		}
		
		while(newPopulationSize-- >0)
		{
			newPopulation.add(0,dnaCreator.createRandomDNA());
		}
		
		population = newPopulation;
	}
	
	public synchronized void pause()
	{
		stop();
		state =AlgorithmState.Paused;
	}

	
	public synchronized void stop()
	{
		forceStop=true;
		state = AlgorithmState.Stopped;
	}
	public EvolutionAlgorithm(int populationSize,CrossOver crossover,Selection selection,List<Mutation> mutations,DNA dnaCreator ) {
	  this.crossover=crossover;
	  this.selection=selection;
	  this.populationSize=populationSize;
	  this.mutations=mutations;
	  this.dnaCreator=dnaCreator;	
	 
	}
	
	public DNA getBestDNACopy()
	{
		return getBestDNA().duplicate();
	}
	public void resume()
	{
		forceStop = false;
		timer.resumeFromLastTime();
		synchronized (this) {
			 state = AlgorithmState.Running;
		}
		while(!stopCondition())
		{
			
			createNewGeneration();
			calculateFitness();
			
			//bestOfEachGenerations.add(getBestDNA().duplicate());
			timer.tick();
			onNewGeneration();// here i will update it haha
			invokeOnNewGenerationListeners();
			
		}
		
		synchronized (this) {
			  if(state != AlgorithmState.Stopped && state!=AlgorithmState.Paused)
			 state = AlgorithmState.Finished;
		}
	}
	
	public synchronized boolean isFinished()
	{
		
		return stopConditions.stream().filter(stopCondition -> stopCondition.test(this) ).count() >0;
	}
	public List<DNA> initalizePopulation()
	{
		isRunnedAtlistOnce = true;
		population=new ArrayList<DNA>();
        for(int i=0; i<populationSize; i++)
        {
        	
        	population.add(dnaCreator.createRandomDNA());
        	
        	
        }
     
		return population;
	}
	public void prepare()
	{
		timer.clear();
		forceStop=false;
		bestOfEachGenerations.clear();
		howManyGenerationsCreated=0;
		state = AlgorithmState.Running;
	}
	public void run()
	{
		
		prepare();
		initalizePopulation();
		calculateFitness();
        onNewGeneration();
		//bestOfEachGenerations.add(getBestDNA().duplicate());
		timer.tick();
		while(!stopCondition())
		{
		 
			createNewGeneration();
			calculateFitness();
			
			if(howManyGenerationsCreated%10==0)
			{
				//System.out.println("generations created "+howManyGenerationsCreated);
			}
			//bestOfEachGenerations.add(getBestDNA().duplicate());
			
			timer.tick();
			invokeOnNewGenerationListeners();
			onNewGeneration();
		 
			
		}
		
		synchronized (this) {
			  if(state != AlgorithmState.Stopped && state!=AlgorithmState.Paused)
			 state = AlgorithmState.Finished;
		}
		
		
	
	}
	
	public int getTimePassed()
	{
		return timer.getTimePassed();
	}
	
	public DNA getBestDNA()
	{
		
		if(population.size()==0)
		{
			return null;
		}
		return population.get(population.size()-1);
	}
	public SelectedFathers naturalSelect() {
	
	    return selection.selectFathersOfNextGeneration(population);
		
	}
	
	private void sortPopulation()
	{
		Collections.sort(population,new Comparator<DNA>() {

			@Override
			public int compare(DNA o1, DNA o2) {
				// TODO Auto-generated method stub
				if(o1.getFitness()==o2.getFitness())
				{
					return 0;
				}
				else if(o1.getFitness()<o2.getFitness())
					return -1;
				return 1;
			}
		});
	}
	public void calculateFitness() {
	
		for(DNA dna:population)
		{
			dna.calculateFitness();
		}
		//population.stream().sorted((dna1,dna2) -> dna1.getFitness()== dna2.getFitness()
			//	?0 :(dna1.getFitness()>dna2.getFitness()?1:-1));
		sortPopulation();
	}
	public List<DNA> createNewGeneration() {
		
		howManyGenerationsCreated++;
		
		List<DNA>newGeneration=new ArrayList<DNA>();
		SelectedFathers selectedFathers=naturalSelect();
		List<DNA>fathersForNewGeneration=selectedFathers.getFathers();
		int elites=selectedFathers.getElite().size();
		
		for(int i=0; i<populationSize-elites&&newGeneration.size()<populationSize-elites; i++)
		{
		  	int dna1Index= (int)(Math.random()*fathersForNewGeneration.size());
			int dna2Index= (int)(Math.random()*fathersForNewGeneration.size());
			DNA father1=fathersForNewGeneration.get(dna1Index);
			DNA father2=fathersForNewGeneration.get(dna2Index);
			//make the fitness get down also
			//List<DNA> diffrentFatthers= TemplateFunctions.getRandomDiffrentElements(fathersForNewGeneration, 2);
			//father1=diffrentFatthers.get(0);
			//father1=diffrentFatthers.get(2);
			
			List<DNA>childs=crossover.createOffSprings(father1, father2);
			
			for(DNA child : childs)
			{
				
				for(Mutation mutation :mutations)
				{
					
					mutation.mutate(child);
					
				}
				
			}
			
			newGeneration.addAll(childs);
		}
		
		//System.exit(0);
		while(newGeneration.size()>populationSize-elites)//fix size if needed
		{
			
			newGeneration.remove(0);
		}
		newGeneration.addAll(selectedFathers.getElite());
		
		population=newGeneration;
		return newGeneration;
	}
	
	public synchronized boolean stopCondition() {
		
	  return isFinished()||forceStop;
      
    }
	public List<DNA> getBestOfEachGenerations() {
		return bestOfEachGenerations;
	}
	public void onNewGeneration()
	{
		
	}
	
	public int getHowManyGenerationsCreated() {
		return howManyGenerationsCreated;
	}
	public List<Predicate<EvolutionAlgorithm>> getStopConditions() {
		return stopConditions;
	}
	public void setStopConditions(List<Predicate<EvolutionAlgorithm>> stopConditions) {
		
		this.stopConditions = stopConditions;
	}
	
	
	
}