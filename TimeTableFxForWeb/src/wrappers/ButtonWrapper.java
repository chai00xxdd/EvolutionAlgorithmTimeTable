package wrappers;

import javafx.scene.control.Button;

public class ButtonWrapper {

	private Button button;
	public ButtonWrapper(Button button)
	{
		this.button = button;
	}
	public Button getButton() {
		return button;
	}
	public void setButton(Button button) {
		this.button = button;
	}
	
	
}
