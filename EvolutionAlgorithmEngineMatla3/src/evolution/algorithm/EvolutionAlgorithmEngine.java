package evolution.algorithm;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface EvolutionAlgorithmEngine {

	public BestDnaDetails getBestDnaDetails();
	public void runAlgorithm();
	public void stopAlgorithm();
	public void resumeAlgorithm();
	public void pauseAlgorithm();
	public EvolutionAlgorithmConfiguration getAlgorithmConfiguration();
	public boolean isAlgorithmRunning();
	public boolean isAlgorithmReachedStopConditions();
	public int getGenerationSkip();
	public void setGenerationSkip(int newGenerationSkip);
	public void AddOrUpdateStopCondition(Predicate<EvolutionAlgorithm> stopCondition);
	public void removeStopCondition(Class stopConditionClass);
	public boolean isAlgorithmRunnedAtListOnce();
	public int getNumberOfGenerations();
	public  List<AlgorithmProgress> getAlgorithmProgressGraph();
	
	public void addOnNewGenerationListener(Consumer<EvolutionAlgorithmEngine> listener);
	public void removeOnNewGenerationListener(Consumer<EvolutionAlgorithmEngine> listener);
	
	public List<Consumer<EvolutionAlgorithmEngine>> getOnAlgorithmStartListeners();
	public List<Consumer<EvolutionAlgorithmEngine>> getOnAlgorithmStopListeners();
	
	
	public AlgorithmProgress getAlgorithmProgress();
}
