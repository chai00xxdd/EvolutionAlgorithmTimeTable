package pages.AlgorithmConfiguration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import DTO.AlgorithmConfuigrationDTO;
import DTO.CrossOverDTO;
import DTO.MutationDTO;
import DTO.SelectionDTO;
import DTO.StopConditionDTO;
import ServerDTO.Operation;
import clientServer.HttpClientETT;
import evolution.algorithm.selection.SelectionName;
import evolution.timetable.crossover.AspectOrientation;
import evolution.timetable.crossover.CrossOverName;
import evolution.timetable.mutation.FlippingComponent;
import evolution.timetable.mutation.MutationName;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pages.Main.MainController;

public class AlgorithmConfigurationController implements Initializable {

	
	@FXML
	private Button selectUpdateButton;
    @FXML
    private Label stopCondErrorLabel;
    
    @FXML
    private TextField populationTextBox;

    @FXML
    private Button populationUpdateButton;

    @FXML
    private ComboBox<SelectionName> selectionComboBox;

    @FXML
    private TextField ellitismTextBox;

    @FXML
    private TextField selectionParameterTextBox;

    @FXML
    private Label selectionErrorLabel;

    @FXML
    private Label populationErrorLabel;

    @FXML
    private ComboBox<CrossOverName> crossoverComboBox;

    @FXML
    private TextField cuttingPointsTextBox;

    @FXML
    private ComboBox<AspectOrientation> aspectComboBox;

    @FXML
    private Button crossoverUpdateButton;

    @FXML
    private Label crossoverErrorLabel;

    @FXML
    private TableView<MutationWrapper> mutationsTable;

    @FXML
    private TableColumn<MutationWrapper, String> mutationNameColumn;

    @FXML
    private TableColumn<MutationWrapper, String> mutationProbalityColumn;

    @FXML
    private TableColumn<MutationWrapper, String> mutationsParamsColumn;

    @FXML
    private TableColumn<MutationWrapper, Button> mutationDeleteColumn;

    @FXML
    private ComboBox<MutationName> mutationComboBox;

    @FXML
    private TextField mutationProbalityTextBox;

    @FXML
    private ComboBox<FlippingComponent> mutationFlippingComponentComboBox;

    @FXML
    private TextField mutationParameterTextBox;

    @FXML
    private Button addMutationButton;

    @FXML
    private Label mutationsErrorLabel;

    @FXML
    private TableView<StopConditionWrapper> stopConditionTable;

    @FXML
    private TableColumn<StopConditionWrapper, String> stopCondNameColumn;

    @FXML
    private TableColumn<StopConditionWrapper, TextField> stopCondValueColumn;

    @FXML
    private TableColumn<StopConditionWrapper, RadioButton> stopCondEnabledColumn;
    
    @FXML
    private AnchorPane pageAnchorPane;

    //SERVER
    private  MainController  mainController;
    private HttpClientETT client;
    public void setMainController(MainController mainController)
    {
    	this.mainController = mainController;
    	this.client = mainController.getClient();
    }

    @FXML
    void addMutationButtonOnAction(ActionEvent event) {
    	try
    	{
            String mutationName = mutationComboBox.getSelectionModel().getSelectedItem().toString();
            double probality = -1 ;
            try
            {
            	probality = Double.parseDouble(mutationProbalityTextBox.getText());
            }
            catch(Exception e) {}
            String problemId = mainController.getCurrentProblemLoadedId().getString();
            String component = mutationFlippingComponentComboBox.getSelectionModel().getSelectedItem().toString();
            String parameter = mutationParameterTextBox.getText();
            MutationDTO mutation = new MutationDTO(mutationName, probality, parameter);
            try {
				client.AddMutation(problemId, mutation, component, Operation.Add);
				prepare();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				mutationsErrorLabel.setText(e.getMessage());
			}
            
    	}
    	catch(Exception e) {e.printStackTrace();}
    }

    @FXML
    void crossoverUpdateButtonOnAction(ActionEvent event) {
    	try
    	{
          String crossoverName = crossoverComboBox.getSelectionModel().getSelectedItem().toString();
          System.out.println(crossoverName);
          String parameter = aspectComboBox.getSelectionModel().getSelectedItem().toString();
          String problemId = mainController.getCurrentProblemLoadedId().getString();
          String cuttingPoints = cuttingPointsTextBox.getText();
          try {
			client.AddOrUpdateCrossOver(problemId, new CrossOverDTO(crossoverName, cuttingPoints, parameter));
			clearErrors();
			crossoverErrorLabel.setText("updated crossover succesfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			crossoverErrorLabel.setText(e.getMessage());
		}
          
    	}
    	catch(Exception e) {e.printStackTrace();}
    }
    
    public void disable()
    {
    	Node [] nodesToDisable = {crossoverUpdateButton,populationUpdateButton,selectUpdateButton,ellitismTextBox,cuttingPointsTextBox,
    			addMutationButton,aspectComboBox,mutationFlippingComponentComboBox,mutationProbalityTextBox,selectionComboBox,
    			crossoverComboBox,mutationComboBox,populationTextBox,mutationParameterTextBox,selectionParameterTextBox,
    			mutationsTable,stopConditionTable
    			};
    	
    	for(Node node :nodesToDisable)
    	{
    		node.setDisable(true);
    	}
    	
    	
    }
    public void activate()
    {
    	Node [] nodesToDisable = {crossoverUpdateButton,populationUpdateButton,selectUpdateButton,ellitismTextBox,cuttingPointsTextBox,
    			addMutationButton,aspectComboBox,mutationFlippingComponentComboBox,mutationProbalityTextBox,selectionComboBox,
    			crossoverComboBox,mutationComboBox,populationTextBox,mutationParameterTextBox,selectionParameterTextBox,
    			mutationsTable,stopConditionTable
    			};
    	
    	for(Node node :nodesToDisable)
    	{
    		node.setDisable(false);
    	}
    	
    }
    private void clearErrors()
    {
    	populationErrorLabel.setText("");
    	crossoverErrorLabel.setText("");
    	mutationsErrorLabel.setText("");
    	selectionErrorLabel.setText("");
    	stopCondErrorLabel.setText("");
    }

    @FXML
    void populationUpdateButtonOnAction(ActionEvent event) {
           String newPopulation = populationTextBox.getText();
           try {
			client.AddOrUpdatePopulation(mainController.getCurrentProblemLoadedId().getString(), newPopulation);
			prepare();
			populationErrorLabel.setText("updated Population Succesfully");
		} catch (Exception e) {
			populationErrorLabel.setText(e.getMessage());
		}
    }

    @FXML
    void selectUpdateButtonOnAction(ActionEvent event) {
    	
    	String problemId = mainController.getCurrentProblemLoadedId().getString();
    	String selectionName = selectionComboBox.getSelectionModel().getSelectedItem().toString();
    	String parameter = selectionParameterTextBox.getText();
    	String ellitism = ellitismTextBox.getText();
    	
    	
    	try {
			client.AddOrUpdateSelection(problemId, new SelectionDTO(selectionName,ellitism,parameter));
			selectionErrorLabel.setText("updated selection Succesfully");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			selectionErrorLabel.setText(e.getMessage());
		}

    }
    private void initStopCondGui()
    {
    	stopCondNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	stopCondValueColumn.setCellValueFactory(new PropertyValueFactory<>("valueTextField"));
    	stopCondEnabledColumn.setCellValueFactory(new PropertyValueFactory<>("enabledButton"));
    	
    	
    }
    private void initMutationGui()
    {
    	mutationNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	mutationProbalityColumn.setCellValueFactory(new PropertyValueFactory<>("probality"));
    	mutationsParamsColumn.setCellValueFactory(new PropertyValueFactory<>("parameterString"));
    	mutationDeleteColumn.setCellValueFactory(new PropertyValueFactory<>("deleteMutationButton"));
    	mutationsErrorLabel.setText("");
    	
    	mutationFlippingComponentComboBox.getItems().addAll(FlippingComponent.values());
    	mutationComboBox.getItems().addAll(MutationName.values());
    	
    	
    	mutationComboBox.valueProperty().addListener((observable,oldValue,selectedMutation)->
		{
		   mutationFlippingComponentComboBox.setVisible(selectedMutation == MutationName.Flipping);
		   mutationFlippingComponentComboBox.setManaged(selectedMutation == MutationName.Flipping);
		   
		   if(selectedMutation == MutationName.Flipping)
		   {
			   mutationParameterTextBox.setPromptText("Max Tupples");
		   }
		   else
		   {
			   mutationParameterTextBox.setPromptText("Total Tupples");
		   }
		     
		});
    	
    	
    	mutationComboBox.getSelectionModel().select(1);
    	mutationFlippingComponentComboBox.getSelectionModel().select(0);
    	
    	
    	
    }
    
   
    
    private void initSelectionGui()
    {
    	selectionComboBox.getItems().addAll(SelectionName.values());
    	selectionErrorLabel.setText("");
    	
    	selectionComboBox.valueProperty().addListener((observable,oldValue,selectedSelection)->
		{
		   selectionParameterTextBox.setVisible(selectedSelection != SelectionName.RouletteWheel);
		   selectionParameterTextBox.setManaged(selectedSelection != SelectionName.RouletteWheel);
		   
		   if(selectedSelection == SelectionName.Tournemant)
		   {
			   selectionParameterTextBox.setPromptText("PTE");
		   }
		   
		   if(selectedSelection == SelectionName.Truncation)
		   {
			   selectionParameterTextBox.setPromptText("Top Percent");
		   }
		     
		});
    }
    
    private void initCrossOverGui()
    {
    	crossoverComboBox.getItems().addAll(CrossOverName.values());
    	aspectComboBox.getItems().addAll(AspectOrientation.values());
    	crossoverErrorLabel.setText("");
    	
    	crossoverComboBox.valueProperty().addListener((observable,oldValue,crossOverSelected)->
		{
		  aspectComboBox.setVisible(crossOverSelected == CrossOverName.AspectOriented);
		  aspectComboBox.setManaged(crossOverSelected == CrossOverName.AspectOriented);
		   
		});
    	aspectComboBox.getSelectionModel().select(0);
    	
    	
    }
    private void initPopulationGui()
    {
    	populationErrorLabel.setText("");
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		initStopCondGui();
		initMutationGui();
		initSelectionGui();
		initCrossOverGui();
		initPopulationGui();
		
	}
	
	
	public void populateConfiguration(AlgorithmConfuigrationDTO config)
	{
		if(config == null)
		{
			System.out.print("config null wired");
			return;
		}
		populationTextBox.setText(config.getPopulationSize()+"");
		populateCrossover(config.getCrossover());
		populateSelection(config.getSelection());
		populateStopConditions(config.getStopConditions());
		populateMutations(config.getMutations());
	}
	private void populateMutations(List<MutationDTO> mutations) {
	
	    mutationsTable.getItems().clear();
	    for(MutationDTO mutation : mutations)
	    {
	    	Button deleteMutationButton = new Button("Delete Mutation");
	    	MutationWrapper mutationWrapper = new MutationWrapper(mutation, deleteMutationButton);
	    	deleteMutationButton.setOnAction((action)->{
	    	    try {
					client.DeleteMutation(mainController.getCurrentProblemLoadedId().getString(), mutationWrapper.getId());
	    	        mutationsTable.getItems().remove(mutationWrapper);
	    	    	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					mutationsErrorLabel.setText(e.getMessage());
				}
	    	});
	    	mutationsTable.getItems().add(mutationWrapper);
	    
	    }
	}
	
	

	private void populateStopConditions(List<StopConditionDTO> stopConditions)
	{
		
		
		stopConditionTable.getItems().clear();
		//int currentAmountOfStopCond = stopConditionTable.getItems().size();
		for(StopConditionDTO stopCond : stopConditions)
		{
			RadioButton enabledButton = new RadioButton();
			TextField valueTextField = new TextField();
			enabledButton.setSelected(stopCond.isEnabled());
			
		
			StopConditionWrapper stopCondWrapper = new StopConditionWrapper(stopCond,valueTextField, enabledButton);
			enabledButton.selectedProperty().addListener((obser,oldvalue,newvalue) ->{
				try {
					client.AddOrUpdateStopCondition(mainController.getCurrentProblemLoadedId().getString(), stopCondWrapper.getStopCondDTO());
					//prepare();
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
				
						//prepare();
						stopCondErrorLabel.setText(e.getMessage());
					
				}
			});
			
			valueTextField.focusedProperty().addListener((obser,oldValue,newValue)->{
				
				
				try {
					client.AddOrUpdateStopCondition(mainController.getCurrentProblemLoadedId().getString(), stopCondWrapper.getStopCondDTO());
					stopCondWrapper.setValue(valueTextField.getText());
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
				
					valueTextField.setText(stopCondWrapper.getValue());
					stopCondErrorLabel.setText(e.getMessage());
					
				}
				
			});
			
			
		     
			
			stopConditionTable.getItems().add(stopCondWrapper);
				
		}
		
	
	}
	
	
	private void populateCrossover(CrossOverDTO crossover)
	{
		cuttingPointsTextBox.setText(crossover.getCuttingPoints());
		CrossOverName crossoverName = CrossOverName.getByName(crossover.getName());
		crossoverComboBox.getSelectionModel().select(crossoverName);
		if(crossover.getParameter() != null)
		{
			String aspectString =  crossover.getParameter()._value.toString();
			AspectOrientation aspectOrienation = AspectOrientation.getAspectByName(aspectString);
			aspectComboBox.getSelectionModel().select(aspectOrienation);
			
		}
		
	}
	
	private void populateSelection(SelectionDTO selection)
	{
		SelectionName selectionName = SelectionName.getByName(selection.getName());
		ellitismTextBox.setText(selection.getEllitism());
		selectionComboBox.getSelectionModel().select(selectionName);
		
		if(selection.getParameter() != null)
		{
			
			String parameterString = selection.getParameter()._value.toString();
			System.out.println(selectionName);
			if(selectionName == SelectionName.Truncation)
			{
				try
				{
					int topPercent = (int) Double.parseDouble(parameterString);
				   parameterString = topPercent +"";
				}
				catch(Exception e) {e.printStackTrace();}
			}
			selectionParameterTextBox.setText(parameterString);
		}
		
	}

	public void populateConfigReadOnly(AlgorithmConfuigrationDTO config)
	{
		if(config == null)
		{
			System.out.println("config is null");
			return;
		}
		clearErrors();
		populateConfiguration(config);
		disable();
	}
	{
		
	}
	public void prepare() {
	
		try
		{
			
			clearErrors();
		AlgorithmConfuigrationDTO config = client.getAlgorithmConfigDTO(client.getUsername(), mainController.getCurrentProblemLoadedId().getString());
		populateConfiguration(config);
		activate();
		
		
		}
		catch(Exception e)
		{
			System.out.println("algorithm config prepare failed");
			e.printStackTrace();
		}
		
	}



}
