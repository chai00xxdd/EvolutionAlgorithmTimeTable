package pages.MutationManager;

import evolution.timetable.EvolutionTimeTableProblemEngine;
import evolution.timetable.mutation.SizerMutation;
import javafx.scene.control.CheckBox;

public class SizerMutationTableElement {

	private SizerMutation mutation;
	private double probality;
	private int totalTupples;
	private CheckBox enabledBox = new CheckBox();
	
	private String probalityString;
	private String totalTupplesString;
	
	
	
	
	
	public String getProbalityString() {
		return probalityString;
	}


	public String getTotalTupplesString() {
		return totalTupplesString;
	}


	public SizerMutationTableElement(SizerMutation mutation) {
		super();
		this.mutation = mutation;
		probality= mutation.getProbalityForMutation();
		totalTupples = mutation.getTotalTupplesToSizer();
		probalityString = probality + "";
		totalTupplesString = totalTupples+"";
		enabledBox.selectedProperty().set(mutation.isEnabled());
		enabledBox.selectedProperty().addListener((observable,oldValue,newValue)->
		{
			mutation.setEnabled(newValue);
		});
	}
	
	
	public double getProbality() {
		return probality;
	}
	public void setProbality(double probality) throws Exception {
		if(probality <0 || probality > 1)
		{
			throw new Exception("probality must be between (0,1]");
		}
		mutation.setProbalityForMutation(probality);
		probalityString = probality+"";
	}
	
	public void enabled(boolean isEnabled)
	{
		enabledBox.selectedProperty().set(isEnabled);
	}
	public int getTotalTupples() {
		return totalTupples;
	}
	public void setTotalTupples(int totalTupples) {
	    mutation.setTotalTupplesToSizer(totalTupples);
		this.totalTupples = totalTupples;
		totalTupplesString = totalTupples+"";
	}


	public CheckBox getEnabledBox() {
		return enabledBox;
	}


	public SizerMutation getMutation() {
		return mutation;
	}
	
	
	
	
	
	
}
