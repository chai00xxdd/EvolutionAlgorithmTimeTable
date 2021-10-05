package pages.AlgorithmConfiguration;

import DTO.StopConditionDTO;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class StopConditionWrapper {
	
	private RadioButton enabledButton;
	private String name;
	private TextField valueTextField;
	private String value;	
	
	public StopConditionWrapper(StopConditionDTO stopCond,TextField valueTextField,RadioButton enabledButton)
	{
		this.name = stopCond.getName();
		this.enabledButton = enabledButton;
		this.valueTextField = valueTextField;
		this.valueTextField.setText(stopCond.getValue());
		this.value = stopCond.getValue();
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public RadioButton getEnabledButton() {
		return enabledButton;
	}


	public String getName() {
		return name;
	}


	
	
	public TextField getValueTextField() {
		return valueTextField;
	}


	public StopConditionDTO getStopCondDTO()
	{
		return new StopConditionDTO(name, valueTextField.getText(), enabledButton.isSelected());
	}
	

}
