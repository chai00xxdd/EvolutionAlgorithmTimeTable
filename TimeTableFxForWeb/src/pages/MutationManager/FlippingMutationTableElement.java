package pages.MutationManager;

import evolution.timetable.mutation.FlippingComponent;
import evolution.timetable.mutation.FlippingMutation;
import evolution.timetable.mutation.SizerMutation;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

public class FlippingMutationTableElement {

	private FlippingMutation mutation;
	private double probality;
	private int maxTupples;
	private CheckBox enabledBox = new CheckBox();
	private ComboBox<FlippingComponent> componentComboBox = new ComboBox<>();
	
	private String probalityString;
	private String maxTupplesString;
	
	public FlippingMutationTableElement(FlippingMutation mutation)
	{
		this.mutation = mutation;
		this.probality = mutation.getProbalityForMutation();
		this.maxTupples = mutation.getMaxTupplesToChange();
		probalityString = probality+"";
		maxTupplesString= maxTupples+"";
		enabledBox.selectedProperty().set(mutation.isEnabled());
		for(FlippingComponent flipComponent : FlippingComponent.values())
		{
			componentComboBox.getItems().add(flipComponent);
		}
		componentComboBox.getSelectionModel().select(mutation.getComponentToChange());
		componentComboBox.valueProperty().addListener((observale,oldValue,newValue)->
		{
			mutation.setComponentToChange(componentComboBox.getSelectionModel().getSelectedItem());
		});
		
		enabledBox.selectedProperty().addListener((obser,old,newVal)->
		{
			mutation.setEnabled(newVal);
		});
	}

	public double getProbality() {
		return probality;
	}

	public void setProbality(double probality) throws Exception {
		if(probality<=0 || probality>1)
		{
			throw new Exception("probality must be in range (0,1]");
		}
		this.probality = probality;
		probalityString = probality+"";
		mutation.setProbalityForMutation(probality);
	}
	
   public void setFlippingComponent(FlippingComponent component)
   {
	   componentComboBox.getSelectionModel().select(component);
   }
	public int getMaxTupples() {
		
		return maxTupples;
	}

	public void setMaxTupples(int maxTupples) throws Exception {
		if(maxTupples<1)
		{
			throw new Exception("MaxTupples must be positive!!!");
		}
		mutation.setMaxTupplesToChange(maxTupples);
		this.maxTupples = maxTupples;
		maxTupplesString = maxTupples+"";
	}

	public FlippingMutation getMutation() {
		return mutation;
	}

	public CheckBox getEnabledBox() {
		return enabledBox;
	}

	public ComboBox<FlippingComponent> getComponentComboBox() {
		return componentComboBox;
	}

	public String getProbalityString() {
		return probalityString;
	}

	public void setProbalityString(String probalityString) {
		this.probalityString = probalityString;
	}

	public String getMaxTupplesString() {
		return maxTupplesString;
	}

	public void setMaxTupplesString(String maxTupplesString) {
		this.maxTupplesString = maxTupplesString;
	}
	
	
	
	
	
	
	
}
