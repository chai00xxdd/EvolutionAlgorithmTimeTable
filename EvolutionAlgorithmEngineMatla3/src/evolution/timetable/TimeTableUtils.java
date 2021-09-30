package evolution.timetable;

import evolution.algorithm.Mutation;
import evolution.algorithm.crossover.CrossOver;
import evolution.algorithm.crossover.KcutCrossOver;
import evolution.algorithm.selection.Selection;
import evolution.algorithm.selection.TruncationSelection;
import evolution.timetable.crossover.AspectOrientedCrossOver;
import evolution.timetable.mutation.FlippingMutation;
import evolution.timetable.mutation.SizerMutation;

public class TimeTableUtils {

	public static String getMutationConfigurationString(Mutation timeTableMutation)
	{
		
		String configurationString="";
		if(timeTableMutation instanceof FlippingMutation)
		{
			FlippingMutation flipingMutation =(FlippingMutation) (timeTableMutation);
			configurationString = "[MaxTupples="+flipingMutation.getMaxTupplesToChange()
			                     +",Component="+flipingMutation.getComponentToChange().toString()+"]";
		}
		else if(timeTableMutation instanceof SizerMutation)
		{
			SizerMutation sizerMutation = (SizerMutation)timeTableMutation;
			configurationString="[TotalTupples = "+sizerMutation.getTotalTupplesToSizer()+"]";
		}
		return configurationString;
	}
	
	public static String getCrossOverConfigurationString(CrossOver crossover)
	{
		
		String configurationString="[]";
		if(crossover instanceof KcutCrossOver)
		{
			configurationString = "[cutting points = "+((KcutCrossOver)crossover).getCuttingPoints()+"]";
		}
		if(crossover instanceof AspectOrientedCrossOver)
		{
			configurationString = "[Orientation = "+((AspectOrientedCrossOver)crossover).getAspectOrientation().toString()
					+",cutting points = "+((KcutCrossOver)crossover).getCuttingPoints()+"]";;
		}
		return configurationString;
	}
	
	public static String getSelectionConfigurationString(Selection selection)
	{
		
		if(selection instanceof TruncationSelection)
		{
			TruncationSelection truncationSelection= (TruncationSelection)selection;
			return "[TopPrecent="+truncationSelection.getTopPrecentOfPopulationToChoose()+"]";
		}
		
		return "[]";
	}
}
