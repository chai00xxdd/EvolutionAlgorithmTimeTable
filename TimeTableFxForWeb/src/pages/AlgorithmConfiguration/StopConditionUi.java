package pages.AlgorithmConfiguration;

import javafx.scene.control.CheckBox;

public class StopConditionUi {

	private String name;
	private String value;
	private CheckBox box =new CheckBox();
	
	public StopConditionUi()
	{
		box.setSelected(false);
	}
	
	public StopConditionUi(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public CheckBox getBox() {
		return box;
	}
	
	
	
}
