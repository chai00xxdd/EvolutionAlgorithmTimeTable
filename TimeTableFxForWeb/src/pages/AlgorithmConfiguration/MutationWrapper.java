package pages.AlgorithmConfiguration;

import DTO.MutationDTO;
import javafx.scene.control.Button;

public class MutationWrapper {
	
	private String name;
	private double probality;
	private String parameterString;
	private Button deleteMutationButton;
	private String id;
	
	public MutationWrapper(MutationDTO mutation, Button deleteBuutton)
	{
		this.deleteMutationButton = deleteBuutton;
		this.name = mutation.getName();
		this.probality = mutation.getProbality();
		this.parameterString = mutation.getParameterString();
		this.id = mutation.getId() +"";
	}

	public String getName() {
		return name;
	}

	public double getProbality() {
		return probality;
	}

	public String getParameterString() {
		return parameterString;
	}

	
	
	

	public Button getDeleteMutationButton() {
		return deleteMutationButton;
	}

	public String getId() {
		return id;
	}
	
	

}
