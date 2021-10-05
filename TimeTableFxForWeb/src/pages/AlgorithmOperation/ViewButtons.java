package pages.AlgorithmOperation;

import javafx.scene.control.Button;

public class ViewButtons {
	
	private Button algorithmConfigButton;
	private Button bestSolutionButton;
	public ViewButtons(Button algorithmConfigButton, Button bestSolutionButton) {
		super();
		this.algorithmConfigButton = algorithmConfigButton;
		this.bestSolutionButton = bestSolutionButton;
	}
	public Button getAlgorithmConfigButton() {
		return algorithmConfigButton;
	}
	public Button getBestSolutionButton() {
		return bestSolutionButton;
	}
	
	
	
	

}
