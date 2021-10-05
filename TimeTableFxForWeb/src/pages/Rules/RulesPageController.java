package pages.Rules;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import DTO.RuleDTO;
import DTO.TimeProblemDTO;
import FxUtils.ObservableUtils;
import FxUtils.TableUtils;
import Utils.Configurable;
import evolution.timetable.EvolutionTimeTableProblemEngine;
import evolution.timetable.TimeProblemAttribute;
import evolution.timetable.TimeTableProblem;
import evolution.timetable.rule.Rule;
import evolution.timetable.rule.RuleDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RulesPageController implements Initializable {

	@FXML
	private TableView<TimeProblemAttribute> attributesTable;
    @FXML
    private TableColumn<TimeProblemAttribute,String> attributeColumn;

    @FXML
    private TableColumn<TimeProblemAttribute,String> valueColumn;
    
    @FXML
    private TableView<RuleDTO> rulesTables;

    @FXML
    private TableColumn<RuleDTO,String> ruleNameColumn;

    @FXML
    private TableColumn<RuleDTO,String> ruleTypeColumn;
    
    @FXML
    private TableColumn<RuleDTO,String> ruleParemetersColumn;

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//rules Table
		ruleNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		ruleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		ruleParemetersColumn.setCellValueFactory(new PropertyValueFactory<>("paremeters"));
		
		//attributes Table
		attributeColumn.setCellValueFactory(new PropertyValueFactory<>("attribute"));
		valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
		
	}
	
	public void populateRulesAndAttributes(TimeProblemDTO timeProblem)
	{
		rulesTables.setItems(ObservableUtils.CollectionToObseravbleList(timeProblem.getRules()));
		//TableUtils.FitToDataContent(rulesTables);
		attributesTable.setItems(ObservableUtils.CollectionToObseravbleList(timeProblem.getAttributes()));
	}
	
	

}
