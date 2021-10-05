package pages.MutationManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import FxUtils.AlertMessage;
import evolution.algorithm.EvolutionAlgorithmEngine;
import evolution.algorithm.Mutation;
import evolution.timetable.mutation.FlippingComponent;
import evolution.timetable.mutation.FlippingMutation;
import evolution.timetable.mutation.SizerMutation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import pages.Main.MainController;

public class MutationPageController implements Initializable {
	
	//FlippingMutation
	 @FXML
	    private TableView<FlippingMutationTableElement> flippingMutationTable;

	    @FXML
	    private TableColumn<FlippingMutationTableElement, String> flippingProbalityColumn;

	    @FXML
	    private TableColumn<FlippingMutationTableElement,String> flippingMaxTupplesColumn;

	    @FXML
	    private TableColumn<FlippingMutationTableElement, ComboBox<FlippingComponent>> flippingComponentColumn;

	    @FXML
	    private TableColumn<FlippingMutationTableElement,CheckBox> flippingEnabledColumn;

	    @FXML
	    private TextField flippingProbalityTextField;

	    @FXML
	    private TextField flippingMaxTuplesField;

	    @FXML
	    private ComboBox<FlippingComponent> flippingComponentComboBox;

	    @FXML
	    private Button flippingAddButton;

	    @FXML
	    private Label flippingStatusLabel;

	//Sizer Mutation
	@FXML 
	private Label sizerStatusLabel;
    @FXML
    private TableView<SizerMutationTableElement> sizerTable;

    @FXML
    private TableColumn<SizerMutationTableElement,String> sizerProbalityColumn;

    @FXML
    private TableColumn<SizerMutationTableElement,String> sizerTotalTupplesColumn;

    @FXML
    private TableColumn<SizerMutationTableElement, CheckBox> sizerEnabledColumn;

    @FXML
    private TextField sizerProbalityTextField;

    @FXML
    private TextField sizerTotalTupples;

    @FXML
    private Button sizerAddButton;
   
    private EvolutionAlgorithmEngine algorithmEngine;
    
    

	public void setAlgorithmEngine(EvolutionAlgorithmEngine algorithmEngine) {
		this.algorithmEngine = algorithmEngine;
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initSizerGui();
		initFlippingGui();
		//flippingProbalityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
	
   }
	private void initFlippingGui()
	{
		
		flippingProbalityColumn.setCellValueFactory(new PropertyValueFactory<>("probalityString"));
		flippingMaxTupplesColumn.setCellValueFactory(new PropertyValueFactory<>("maxTupplesString"));
		flippingComponentColumn.setCellValueFactory(new PropertyValueFactory<>("componentComboBox"));
		flippingEnabledColumn.setCellValueFactory(new PropertyValueFactory<>("enabledBox"));
		
		flippingMutationTable.setOnKeyReleased((keyEvent)->
		{
			if(keyEvent.getCode().equals(KeyCode.DELETE)&& flippingMutationTable.getItems().size() > 0)
			{
			FlippingMutationTableElement flipingElement = flippingMutationTable.getSelectionModel().getSelectedItem();
			if(flipingElement == null)
			{
				return;
			}
			algorithmEngine.getAlgorithmConfiguration().getMutations().remove(flipingElement.getMutation());
			flippingMutationTable.getItems().remove(flipingElement);
			flippingMutationTable.getSelectionModel().clearSelection();
			}
		});
		
		for(FlippingComponent component : FlippingComponent.values())
         {
			flippingComponentComboBox.getItems().add(component);
         } 
		flippingComponentComboBox.getSelectionModel().select(0);
		
	
		flippingProbalityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		flippingMaxTupplesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		//flippingMaxTupplesColumn.seton
		
		flippingProbalityColumn.setOnEditCommit((editData)->
		{
			 FlippingMutationTableElement flippingElement= editData.getRowValue();
			 try
			 {
				 double probality =Double.parseDouble(editData.getNewValue());
				 editData.getTableView().getItems().get(editData.getTablePosition().getRow()).setProbality(probality);
				 flippingStatusLabel.setText("updated probality succesfully!!!");
			 }
			 catch(Exception e)
			 {
				 flippingStatusLabel.setText("probality must be between (0,1]");
				 try {
					editData.getTableView().getItems().get(editData.getTablePosition().getRow()).setProbality(Double.parseDouble(editData.getOldValue()));
					editData.getTableView().refresh();
			
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("wired bug");
					e1.printStackTrace();
				}
			 }
			
		});
		flippingMaxTupplesColumn.setOnEditCommit((editData)->
		{
			
		 FlippingMutationTableElement flippingElement= editData.getRowValue();
		 try
		 {
			 int maxTupples =Integer.parseInt(editData.getNewValue());
			 editData.getTableView().getItems().get(editData.getTablePosition().getRow()).setMaxTupples(maxTupples);
			 flippingStatusLabel.setText("updated max tupples succesfully!!!");
		 }
		 catch(Exception e)
		 {
			 flippingStatusLabel.setText("maxTupples must be atlist 1");
			 try {
				editData.getTableView().getItems().get(editData.getTablePosition().getRow()).setMaxTupples(Integer.parseInt(editData.getOldValue()));
				editData.getTableView().refresh();
				
		
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("wired bug");
				e1.printStackTrace();
			}
		 }
		});
		
	}
	
	private void initSizerGui()
	{
		sizerProbalityColumn.setCellValueFactory(new PropertyValueFactory<>("probalityString"));
		sizerTotalTupplesColumn.setCellValueFactory(new PropertyValueFactory<>("totalTupplesString"));
		sizerEnabledColumn.setCellValueFactory(new PropertyValueFactory<>("enabledBox"));
		sizerStatusLabel.setText("");
	
		
		sizerTable.setOnKeyReleased((keyEvent)->
		{
			if(keyEvent.getCode().equals(KeyCode.DELETE) && sizerTable.getItems().size() > 0)
			{
			SizerMutationTableElement sizerElement = sizerTable.getSelectionModel().getSelectedItem();
			if(sizerElement ==null)
				return;
			algorithmEngine.getAlgorithmConfiguration().getMutations().remove(sizerElement.getMutation());
			sizerTable.getItems().remove(sizerElement);
			sizerTable.getSelectionModel().clearSelection();
			}
		});
		
		
		
		
		sizerProbalityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		sizerTotalTupplesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		//flippingMaxTupplesColumn.seton
		
		sizerProbalityColumn.setOnEditCommit((editData)->
		{
			 SizerMutationTableElement SizerElement= editData.getRowValue();
			 try
			 {
				 double probality =Double.parseDouble(editData.getNewValue());
				 editData.getTableView().getItems().get(editData.getTablePosition().getRow()).setProbality(probality);
				 sizerStatusLabel.setText("updated probality succesfully!!!");
			 }
			 catch(Exception e)
			 {
				 sizerStatusLabel.setText("probality must be between (0,1]");
				 try {
					editData.getTableView().getItems().get(editData.getTablePosition().getRow()).setProbality(Double.parseDouble(editData.getOldValue()));
					editData.getTableView().refresh();
			
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("wired bug");
					e1.printStackTrace();
				}
			 }
			
		});
		sizerTotalTupplesColumn.setOnEditCommit((editData)->
		{
			
		 try
		 {
			 int totalTupples =Integer.parseInt(editData.getNewValue());
			 editData.getTableView().getItems().get(editData.getTablePosition().getRow()).setTotalTupples(totalTupples);
			 sizerStatusLabel.setText("updated TotalTupples succesfully!!!");
		 }
		 catch(Exception e)
		 {
			 sizerStatusLabel.setText("TotalTupples must be atlist 1");
			 try {
				editData.getTableView().getItems().get(editData.getTablePosition().getRow()).setTotalTupples(Integer.parseInt(editData.getOldValue()));
				editData.getTableView().refresh();
				
		
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				System.out.println("wired bug");
				e1.printStackTrace();
			}
		 }
		});
	}
	
	public void prepare()
	{
		sizerStatusLabel.setText("");
		flippingStatusLabel.setText("");
		pouplateSizerTable();
		populateFlippingTable();
	}
	private void populateFlippingTable()
	{
		List<Mutation> flippingMutations = algorithmEngine.getAlgorithmConfiguration().getMutations()
				.stream().filter(mutation -> mutation instanceof FlippingMutation).collect(Collectors.toList());
		ObservableList<FlippingMutationTableElement> observaleSizerMutations = FXCollections.observableArrayList();
		
		for(Mutation mutation : flippingMutations)
		{
			observaleSizerMutations.add(new FlippingMutationTableElement((FlippingMutation)mutation));
		}
		
		flippingMutationTable.setItems(observaleSizerMutations);
	}
	private void pouplateSizerTable()
	{
		List<Mutation> sizerMutations = algorithmEngine.getAlgorithmConfiguration().getMutations()
				.stream().filter(mutation -> mutation instanceof SizerMutation).collect(Collectors.toList());
		ObservableList<SizerMutationTableElement> observaleSizerMutations = FXCollections.observableArrayList();
		
		for(Mutation mutation : sizerMutations)
		{
			observaleSizerMutations.add(new SizerMutationTableElement((SizerMutation)mutation));
		}
		
		sizerTable.setItems(observaleSizerMutations);
	}
	
	public void flippingAddButtonOnAction()
	{
		FlippingMutationTableElement newFlipping = new FlippingMutationTableElement(new FlippingMutation(1, 0.5,FlippingComponent.CLASS));
		//System.out.println("here dude");
		double probality;
		int MaxTuuples;
		try
		{
			probality = Double.parseDouble(flippingProbalityTextField.getText());
		}
		catch(Exception e)
		{
			flippingStatusLabel.setText("probality must be between (0,1]");
			return;
		}
		try
		{
			MaxTuuples = Integer.parseInt(flippingMaxTuplesField.getText());
		}
		catch(Exception e)
		{
			flippingStatusLabel.setText("MaxTuuples must be positive!!!");
			return;
		}
		try {
			newFlipping.setProbality(probality);
			newFlipping.setMaxTupples(MaxTuuples);
			newFlipping.setFlippingComponent(flippingComponentComboBox.getSelectionModel().getSelectedItem());
			algorithmEngine.getAlgorithmConfiguration().getMutations().add(newFlipping.getMutation());
			flippingMutationTable.getItems().add(newFlipping);
			flippingProbalityTextField.setText("");
			flippingProbalityTextField.setText("");
			flippingMaxTuplesField.setText("");
			flippingStatusLabel.setText("added muttation succesfuly!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			flippingStatusLabel.setText(e.getMessage());
		}
	}
	public void sizerAddButtonOnAction()
	{
		SizerMutationTableElement newSizer = new SizerMutationTableElement(new SizerMutation(0, 0.5));
		double probality;
		int totalTupples;
		try
		{
			probality = Double.parseDouble(sizerProbalityTextField.getText());
		}
		catch(Exception e)
		{
			sizerStatusLabel.setText("probality must be between (0,1]");
			return;
		}
		try
		{
			totalTupples = Integer.parseInt(sizerTotalTupples.getText());
		}
		catch(Exception e)
		{
			sizerStatusLabel.setText("totalTupples must be a int number");
			return;
		}
		try {
			newSizer.setProbality(probality);
			newSizer.setTotalTupples(totalTupples);
			algorithmEngine.getAlgorithmConfiguration().getMutations().add(newSizer.getMutation());
			sizerTable.getItems().add(newSizer);
			sizerProbalityTextField.setText("");
			sizerTotalTupples.setText("");
			sizerStatusLabel.setText("added muttation succesfuly!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			sizerStatusLabel.setText(e.getMessage());
		}
	}
	

}
