package pages.BestSolution;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import DTO.BestTimeTableSolutionDTO;
import DTO.ClassDTO;
import DTO.LeactureDTO;
import DTO.ShecduleDTO;
import DTO.SubjectDTO;
import DTO.TeacherDTO;
import FxUtils.ObservableUtils;
import FxUtils.TableUtils;
import application.Main;
import evolution.timetable.BestTimeTableSolutionDetails;
import evolution.timetable.EvolutionTimeTableProblemEngine;
import evolution.timetable.rule.RuleResult;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;
import pages.Main.MainController;
import pages.ShecdulePage.ShecduleData;
import pages.ShecdulePage.ShecdulePageController;
import school.Course;
import school.Leacture;
import school.RawLeacture;
import school.SchoolClass;
import school.Teacher;

public class BestSolutionPageController implements Initializable {

	//Rules
	 @FXML
	    private TableView<RuleResult> rulesTable;

	    @FXML
	    private TableColumn<RuleResult,String> ruleNameColumn;

	    @FXML
	    private TableColumn<RuleResult,String> ruleGradeColumn;

	    @FXML
	    private TableColumn<RuleResult,String> ruleEffectColumn;

	    @FXML
	    private TableColumn<RuleResult,String> ruleMaxEffectColumn;
	
	//RAW
    @FXML
    private TableView<RawLeacture> RawLeactuersTable;

    @FXML
    private TableColumn<RawLeacture, String> RawDayColumn;

    @FXML
    private TableColumn<RawLeacture,String> HourDayColumn;

    @FXML
    private TableColumn<RawLeacture, String> RawClassColumn;

    @FXML
    private TableColumn<RawLeacture, String> RawTeacherColumn;

    @FXML
    private TableColumn<RawLeacture, String> RawSubjectColumn;

    @FXML
    private Label fitnessLabel;
    
    @FXML
    private Label generationLabel;
    
    @FXML 
    private TableView<ObservableList<String>> classTable;
    
    @FXML
    private ComboBox<ShecduleDTO> classesComboBox;
    
    @FXML
    private ComboBox<ShecduleDTO> teacherComboBox;
    
    @FXML private TableView<ObservableList<String>> teachersTable;
    
    
    
    @FXML private Label leactuersNumberLabel;

    @FXML
    private Label userSolutionLabel;



    
    private EvolutionTimeTableProblemEngine timeTableEngine = null;
    private ObservableList<RawLeacture> rawLeactuersList = FXCollections.observableArrayList();
    private BestTimeTableSolutionDTO currentBestSolution;
    
    
    
  

	private void initRawSolutionGui()
    {
    	RawDayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
		HourDayColumn.setCellValueFactory(new PropertyValueFactory<>("hour"));
		RawTeacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
		RawSubjectColumn.setCellValueFactory(new PropertyValueFactory<>("subjectId"));
		RawClassColumn.setCellValueFactory(new PropertyValueFactory<>("classId"));
    }
    private void initRulesGui()
    {
    	ruleNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    	ruleGradeColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
    	ruleEffectColumn.setCellValueFactory(new PropertyValueFactory<>("effectOnFitness"));
    	ruleMaxEffectColumn.setCellValueFactory(new PropertyValueFactory<>("maxEffectOnFitness"));
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		generationLabel.setText("");
		fitnessLabel.setText("");
		userSolutionLabel.setText("");
		initRawSolutionGui();
		initRulesGui();
		
		
		classTable.getItems().clear();
		classTable.getColumns().clear();
        
		classesComboBox.valueProperty().addListener((observable,oldValue,classSelected)->
		{
			if(classSelected == null)
			{
				return;
			}
			populateClass(classSelected);
		   
		});
		
		teacherComboBox.valueProperty().addListener((observable,oldValue,teacherSelected)->
		{
			if(teacherSelected == null)
			{
				return;
			}
		  populateTeacher(teacherSelected);
		});
		
	}
	
	public void populateSolution(BestTimeTableSolutionDTO bestCurrentSolution)
	
	{
		
		if(bestCurrentSolution==null)
			return;
		 
		currentBestSolution  = bestCurrentSolution;
		userSolutionLabel.setText("Solution of user "+bestCurrentSolution.getUserName());
		generationLabel.setText("Solution generation :"+bestCurrentSolution.getGeneration());
		fitnessLabel.setText("Soltuion fitness is:"+bestCurrentSolution.getFitness());
		leactuersNumberLabel.setText("Number of leactuers : "+bestCurrentSolution.getNumberOfLeactuers());
		
	    populateComboBoxes();
	   
		populateRaw();
		
		populateRules();
		
		//populateClass(currentBestSolution.getSolution().getClasses().get(0));
	}
	private void populateRules()
	{
	//	System.out.println(currentBestSolution.getSolution().getRulesResults().size());
		rulesTable.setItems(ObservableUtils.CollectionToObseravbleList(currentBestSolution.getRuleResults()));
		TableUtils.FitToDataContent(rulesTable);
	}
	private void populateRaw()
	{
		rawLeactuersList = ObservableUtils.CollectionToObseravbleList(currentBestSolution.getRawLeactuers());
		RawLeactuersTable.setItems(rawLeactuersList);
	}
	
	private void populateComboBoxes()
	{
		classesComboBox.getItems().clear();
		teacherComboBox.getItems().clear();
		//System.out.println("why dyude");
		if(currentBestSolution.getClassesShecdule() == null)
		{
			System.out.println(currentBestSolution.getGeneration());
			System.out.println("its null wired");
		}
		classesComboBox.getItems().addAll(currentBestSolution.getClassesShecdule());
		//System.out.println("all good in here");
		classesComboBox.getSelectionModel().select(0);
		//System.out.println("not good here dude");
		teacherComboBox.getItems().addAll(currentBestSolution.getTeachersShecdule());
		teacherComboBox.getSelectionModel().select(0);
		
	}
	private void populateTeacher(ShecduleDTO teacherShecdule)
	{
		int days = teacherShecdule.getDays();
		 int hours = teacherShecdule.getHours();
		 List<String> columnNames = new ArrayList<String>();
		 teachersTable.getItems().clear();
		 teachersTable.getColumns().clear();
		 columnNames.add("Hour");
		 for(int day=0; day<days; day++)
		 {
			 columnNames.add("Day "+(day+1));
		 }
		 int index = 0;
		 for(String column : columnNames)
			{
				final int j =index++;
				 TableColumn col = new TableColumn(column);
				 //col.setResizable(false);
				 col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
		                if (param.getValue().get(j) != null) {
		                    return new SimpleStringProperty(param.getValue().get(j).toString());
		                } else {
		                    return null;
		                }
		            });
				 teachersTable.getColumns().add(col);
			}
		List<LeactureDTO>[][] leactuers= teacherShecdule.getLeactures();
		 for(int hour=0; hour<hours; hour++)
		 {
			 ObservableList<String> row = FXCollections.observableArrayList();
			 row.add("Hour "+(hour+1));
			 for(int day=0; day<days; day++)
			 {
				 List<LeactureDTO> leactuersAtDayAndHour = leactuers[day][hour];
				 if(leactuersAtDayAndHour.size() > 0)
				 {
					 LeactureDTO firstLeacture = leactuersAtDayAndHour.get(0);
					 SubjectDTO course = firstLeacture.getSubject();
					 ClassDTO classToTeach = firstLeacture.getSclass();
					 String leactureString = "Class Name :"+classToTeach.getName()+System.lineSeparator()+"Class id :"+classToTeach.getId()+System.lineSeparator();
					  leactureString += System.lineSeparator()+"Subject Name: "+course.getName()+System.lineSeparator()+"Subject id:" +course.getId();
					  if(leactuersAtDayAndHour.size() > 1)
					  {
						  leactureString += System.lineSeparator()+"Number Of Collitions : "+(leactuersAtDayAndHour.size()-1);
					  }
					  row.add(leactureString);
				 }
				 else row.add(" ");
				
			 }
			 teachersTable.getItems().add(row);
		 }
	}
	private void populateClass(ShecduleDTO classShecdule)
	{
		if(classShecdule == null)
		{
			System.out.println("help me god its null class shecdule");
		}
		int days = classShecdule.getDays();
		 int hours = classShecdule.getHours();
		 List<String> columnNames = new ArrayList<String>();
		 classTable.getItems().clear();
		 classTable.getColumns().clear();
		 columnNames.add("Hour");
		 for(int day=0; day<days; day++)
		 {
			 columnNames.add("Day "+(day+1));
		 }
		 int index = 0;
		 for(String column : columnNames)
			{
				final int j =index++;
				 TableColumn col = new TableColumn(column);
				 //col.setResizable(false);
				 col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param -> {
		                if (param.getValue().get(j) != null) {
		                    return new SimpleStringProperty(param.getValue().get(j).toString());
		                } else {
		                    return null;
		                }
		            });
			    classTable.getColumns().add(col);
			}
		List<LeactureDTO>[][] leactuers= classShecdule.getLeactures();
		 for(int hour=0; hour<hours; hour++)
		 {
			 ObservableList<String> row = FXCollections.observableArrayList();
			 row.add("Hour "+(hour+1));
			 for(int day=0; day<days; day++)
			 {
				 List<LeactureDTO> leactuersAtDayAndHour = leactuers[day][hour];
				 if(leactuersAtDayAndHour.size() > 0)
				 {
					 LeactureDTO firstLeacture = leactuersAtDayAndHour.get(0);
					 SubjectDTO course = firstLeacture.getSubject();
					 TeacherDTO teacher = firstLeacture.getTeacher();
					 String leactureString = "Teacher Name :"+teacher.getName()+System.lineSeparator()+"Teacher id :"+teacher.getId()+System.lineSeparator();
					  leactureString += System.lineSeparator()+"Subject Name: "+course.getName()+System.lineSeparator()+"Subject id:" +course.getId();
					  if(leactuersAtDayAndHour.size() > 1)
					  {
						  leactureString += System.lineSeparator()+System.lineSeparator()+"Number Of Collitions : "+(leactuersAtDayAndHour.size()-1);
					  }
					  
					  row.add(leactureString);
				 }
				 else row.add(" ");
				
			 }
			 classTable.getItems().add(row);
		 }
	}
	
	

}
