package evolution.factory;

import java.util.ArrayList;
import java.util.List;

import evolution.algorithm.EvolutionAlgorithmConfiguration;
import evolution.algorithm.Mutation;
import evolution.algorithm.crossover.CrossOver;
import evolution.algorithm.selection.RouleteWheelSelection;
import evolution.algorithm.selection.Selection;
import evolution.algorithm.stopcondition.StopByFitness;
import evolution.algorithm.stopcondition.StopByGenerations;
import evolution.algorithm.stopcondition.StopByTime;
import evolution.algorithm.stopcondition.StopCondition;
import evolution.timetable.crossover.DayTimeOrientedCrossOver;

public class EvolutionAlgorithmDefaultFactory {

	private static final int defaultPopulationSize = 100;
	public static EvolutionAlgorithmConfiguration createDefaultConfiguration()
	{
		CrossOver crossoOver = new DayTimeOrientedCrossOver(1);
		List<Mutation> mutations = new ArrayList<>();
		Selection selection = new RouleteWheelSelection();
		EvolutionAlgorithmConfiguration configuration = new EvolutionAlgorithmConfiguration(crossoOver, mutations, selection,defaultPopulationSize);
		try {
			configuration.getStopConditoins().add(new StopByFitness(1.0f));
			
			StopByGenerations stopByGeneratoins = new StopByGenerations(100);
			stopByGeneratoins.setEnabled(false);
			configuration.getStopConditoins().add(stopByGeneratoins);
			StopByTime stopByTime = new StopByTime(1000*100);
			stopByTime.setEnabled(false);
			configuration.getStopConditoins().add(stopByTime);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return configuration;
	}
}
