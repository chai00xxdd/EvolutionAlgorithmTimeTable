package evolution.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import DTO.CrossOverDTO;
import DTO.MutationDTO;
import DTO.SelectionDTO;
import DTO.StopConditionDTO;
import evolution.algorithm.crossover.CrossOver;
import evolution.algorithm.selection.RouleteWheelSelection;
import evolution.algorithm.selection.Selection;
import evolution.algorithm.selection.TruncationSelection;
import evolution.algorithm.stopcondition.StopCondition;
import evolution.timetable.crossover.DayTimeOrientedCrossOver;

public class EvolutionAlgorithmConfiguration {

	private CrossOver crossover;
	private List<Mutation> mutations=new ArrayList<>();
	private List<Predicate<EvolutionAlgorithm>>stopConditoins=new ArrayList<>();
	private List<Consumer<EvolutionAlgorithm>> onNewGenerationListeners = new ArrayList<>();
	private Selection selection;
	private int populationSize;
	private int maxGenerations=10;
	private int timeToStop=-1;
	
	
	public EvolutionAlgorithmConfiguration(CrossOver crossover, List<Mutation> mutations, Selection selection,
			int populationSize) {
		super();
		this.crossover = crossover;
		this.mutations = mutations;
		this.selection = selection;
		this.populationSize = populationSize;
		
	}
	public synchronized CrossOver getCrossover() {
		return crossover;
	}
	public synchronized void setCrossover(CrossOver crossover) {
		this.crossover = crossover;
	}
	
	public synchronized List<Mutation> getMutations() {
		return mutations;
	}
	public synchronized  void setMutations(ArrayList<Mutation> mutations) {
		this.mutations = mutations;
	}
	public synchronized Selection getSelection() {
		return selection;
	}
	public synchronized int getPopulationSize() {
		return populationSize;
	}
	public synchronized void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}
	public synchronized void setSelection(Selection selection) {
		this.selection = selection;
	}
	public  final int getMaxGenerations() {
		return maxGenerations;
	}
	
	public final void setMaxGenerations(int maxGenerations) {
		this.maxGenerations = maxGenerations;
	}
	public synchronized List<Predicate<EvolutionAlgorithm>> getStopConditoins() {
		return stopConditoins;
	}
	public synchronized void setStopConditoins(List<Predicate<EvolutionAlgorithm>> stopConditoins) {
		this.stopConditoins = stopConditoins;
	}
	public synchronized void setMutations(List<Mutation> mutations) {
		this.mutations = mutations;
	}
	public int getTimeToStop() {
		return timeToStop;
	}
	public void setTimeToStop(int timeToStop) {
		this.timeToStop = timeToStop;
	}
	
	public List<Consumer<EvolutionAlgorithm>> getNewGenerationListeners()
	{
		return onNewGenerationListeners;
	}
	
	public List<EvolutionAlgorithmAttribute> getAlgorithmAttributes()
	{
		List<EvolutionAlgorithmAttribute> algorithmAttributes = new ArrayList<>();
		String populationString = populationSize + "";
		algorithmAttributes.add(new EvolutionAlgorithmAttribute("population size", populationString));
		
		return algorithmAttributes;
	}
	
	public synchronized EvolutionAlgorithmConfiguration shallowCopy()
	{
		EvolutionAlgorithmConfiguration copyConfig = new EvolutionAlgorithmConfiguration
				(crossover, new ArrayList<>(mutations),selection,populationSize);
		copyConfig.stopConditoins = new ArrayList<>(stopConditoins);
		copyConfig.onNewGenerationListeners = new ArrayList<>(onNewGenerationListeners);
		
		return copyConfig;
	}
	
	public synchronized List<MutationDTO> getMutationsDTO()
	{
		return  mutations.stream().map( mutation -> new MutationDTO(mutation)).collect(Collectors.toList());
	}
	
	public synchronized List<StopConditionDTO> getStopConditionsDTO()
	{
		return stopConditoins.stream().map(stopCondition -> new StopConditionDTO(stopCondition)).collect(Collectors.toList());
	}
	
	public synchronized CrossOverDTO getCrossOverDTO()
	{
		return new CrossOverDTO(crossover);
		
	}
	
	public synchronized SelectionDTO getSelectionDTO()
	{
		return new SelectionDTO(selection);
		
	}
	
	public synchronized void removeMutationById(String mutationId)
	{
		mutations = mutations.stream().filter(mutation -> !(mutation.getId()+"").equals(mutationId))
				                      .collect(Collectors.toList());
	}
	
	public synchronized void addMutation(Mutation mutation)
	{
		mutations.add(mutation);
	}
	
	
	public synchronized void addOrUpdateStopCondition(Predicate<EvolutionAlgorithm> newStopCondition)
	{
		int indexToInsert = 0;
		List<Predicate<EvolutionAlgorithm>> stopConditionsWithOutStopConditoinType = new ArrayList<>();
		for(int i=0; i< stopConditoins.size(); i++)
		{
			if(stopConditoins.get(i).getClass().equals(newStopCondition.getClass()))
			{
				indexToInsert = i;
			}
			else
			{
				stopConditionsWithOutStopConditoinType.add(stopConditoins.get(i));
			}
		}
		
		stopConditoins = stopConditionsWithOutStopConditoinType;
		stopConditoins.add(indexToInsert,newStopCondition);
		
	}
	public synchronized void updateStopConditionsProgress(EvolutionAlgorithm algorithm) {
		
		stopConditoins.forEach(stopCondition -> stopCondition.test(algorithm));
		
	}
	public synchronized void clearStopConditionsProgress() {
		// TODO Auto-generated method stub
		  stopConditoins.forEach(stopConditoin -> ((StopCondition)stopConditoin).clearProgress());
	}
	
	
	
	
	
}
