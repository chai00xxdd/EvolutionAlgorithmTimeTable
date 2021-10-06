package pages.TimeProblems;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import FxUtils.ObservableUtils;
import ServerDTO.TimeProblemShortDTO;
import ServerDTO.TimeTableProblemWrapperDTO;
import ServerDTO.UserDTO;
import clientServer.HttpClientETT;
import clientServer.ServerErrorException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pages.Main.MainController;
import wrappers.AtomicString;
import wrappers.ButtonWrapper;

public class TimeProblemsPageController implements Initializable {

    @FXML
    private TableView<TimeProblemShortDTO> timeProblemsTable;

    @FXML
    private TableColumn<TimeProblemShortDTO, String> solvingColumn;

    @FXML
    private TableColumn<TimeProblemShortDTO, String> daysColumn;

    @FXML
    private TableColumn<TimeProblemShortDTO, String> hoursColumn;

    @FXML
    private TableColumn<TimeProblemShortDTO, String> teachersColumn;

    @FXML
    private TableColumn<TimeProblemShortDTO, String> classColumn;

    @FXML
    private TableColumn<TimeProblemShortDTO, String> hardRulesColumn;

    @FXML
    private TableColumn<TimeProblemShortDTO, String> softRulesColumn;

    @FXML
    private TableColumn<TimeProblemShortDTO, String> ownerColumn;

    @FXML
    private TableColumn<TimeProblemShortDTO, String> bestFitnessColumn;

    @FXML
    private TableColumn<TimeProblemShortDTO, String> statusColumn;

    @FXML
    private TableView<ButtonWrapper> loadProblemsTable;
   
    @FXML
    private TableColumn<ButtonWrapper, Button> loadColumn;
    
    @FXML
    private Label timeProblemErrorLabel;
    
    //UsersTable
    
    @FXML
    private TableView<UserDTO> usersTable;

    @FXML
    private TableColumn<UserDTO, String> usernameColumn;
    
    private HttpClientETT client = null;
    private AtomicString timeProblemLoadedId;
    private MainController mainController;
    private Thread pullTimeProblemsThread = null;
    
     public void setMainController(MainController mainController)
     {
    	 client = mainController.getClient();
    	 timeProblemLoadedId = mainController.getCurrentProblemLoadedId();
    	 this.mainController = mainController;
     }
    
    public synchronized void populateProblemsFromServer()
    {
    	try {
    		
			List<TimeProblemShortDTO> timeProblems = client.getShortProblemsDTO();
			List<UserDTO> usersOnline = client.getAllUsersConnected();
			Platform.runLater(()->
			{
				if(timeProblems != null)
				{
					populateTimeProblems(timeProblems);	
					
				}
				
				if(usersOnline != null)
				{
					populateUsers(usersOnline);
				}
				
			});
			
		} catch (Exception e) {
			
		}
    }
    
   
    public void populateTimeProblems(List<TimeProblemShortDTO> timeProblems)
    {
    	boolean updateButtons = timeProblems.size() != timeProblemsTable.getItems().size();
    	timeProblemsTable.setItems(ObservableUtils.CollectionToObseravbleList(timeProblems));
    	if(updateButtons)
    	{
    		List<ButtonWrapper> loadButtons  =new ArrayList<>();
    		timeProblems.forEach(problem ->
    		{
    		   Button loadButton = new Button();
    		   loadButton.setText("Load Me!!!");
    		   
    		   loadButton.setOnAction((action)->{
    			   mainController.updateCurrentTimeProblemId(problem.getId()+"");
    			   timeProblemErrorLabel.setText("problem #"+problem.getId()+" loaded");
    			   System.out.println("loaded problem with id = "+problem.getId());
    			   loadButton.setDisable(true);
    			   
    			   
    		   });
    		   
    		   loadButtons.add(new ButtonWrapper(loadButton));
    		});
    		
    		loadProblemsTable.setItems(ObservableUtils.CollectionToObseravbleList(loadButtons));
    		
    	}
    	
    	String loadedProblemId = mainController.getCurrentProblemLoadedId().getString();
    	for(int i = 0 ; i < timeProblems.size(); i++)
    	{
    		String problemId = timeProblemsTable.getItems().get(i).getId()+"";
    		boolean disableCurrnetProblemLoadButton = problemId.equals(loadedProblemId);
  
    		loadProblemsTable.getItems().get(i).getButton().setDisable(disableCurrnetProblemLoadButton);
    		
    		
    	}
    	
    }
    
     


   public void prepare()
   {
	   timeProblemErrorLabel.setText("");
	   if(pullTimeProblemsThread == null)
	   {
		   pullTimeProblemsThread = new Thread(()->{
			  while(true)
			  {
				  populateProblemsFromServer();
				  try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		   });
		   
		   pullTimeProblemsThread.setDaemon(true);
		   pullTimeProblemsThread.start();
	   }
	   
   }
  

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		// teacherIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		solvingColumn.setCellValueFactory(new PropertyValueFactory<>("howManySolving"));
		daysColumn.setCellValueFactory(new PropertyValueFactory<>("days"));
		hoursColumn.setCellValueFactory(new PropertyValueFactory<>("hours"));
		teachersColumn.setCellValueFactory(new PropertyValueFactory<>("teachers"));
		classColumn.setCellValueFactory(new PropertyValueFactory<>("classes"));
		hardRulesColumn.setCellValueFactory(new PropertyValueFactory<>("hardRules"));
		softRulesColumn.setCellValueFactory(new PropertyValueFactory<>("softRules"));
		ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
		bestFitnessColumn.setCellValueFactory(new PropertyValueFactory<>("bestFitness"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("algorithmState"));
		loadColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
		timeProblemErrorLabel.setText("");
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("userName"));
		
		
	}
	
	private void populateUsers(List<UserDTO> users)
	{
		usersTable.getItems().clear();
		usersTable.setItems(ObservableUtils.CollectionToObseravbleList(users));
	}

}
