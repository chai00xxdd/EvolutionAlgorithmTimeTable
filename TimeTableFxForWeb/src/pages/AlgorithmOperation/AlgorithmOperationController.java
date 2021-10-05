package pages.AlgorithmOperation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;


import EvolutionAlgorithmProperty.EvolutionAlgorithmPropertyManager;
import FxUtils.ObservableUtils;
import ServerDTO.UserAlgorithmProgress;
import clientServer.HttpClientETT;
import evolution.algorithm.AlgorithmOperation;
import evolution.algorithm.AlgorithmProgress;
import evolution.algorithm.EvolutionAlgorithm;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import pages.Main.MainController;

public class AlgorithmOperationController implements Initializable {

    @FXML
    private JFXButton ResumeButton;

    @FXML
    private JFXButton StopButton;

    @FXML
    private JFXProgressBar algorithmRunningProgressBar;

    @FXML
    private Label algorithmStatusLabel;

    @FXML
    private Label chooseConditionsLabel;

    @FXML
    private JFXButton RunButton;

    @FXML
    private HBox resultsHbox;

    @FXML
    private Label GenerationsPassedLabel;

    @FXML
    private Label BestFitnessLabel;

    @FXML
    private Label runningTimeLabel;

    @FXML
    private AnchorPane generationConditionPane;

    @FXML
    private ProgressIndicator generationsProgressBar;

    @FXML
    private AnchorPane fitnessConditionAnchor;

    @FXML
    private ProgressIndicator fitnessProgressBar;

    @FXML
    private AnchorPane TimerConditionAnchor;

    @FXML
    private ProgressIndicator timeProgressBar;
    
    //usersProgressTable
    @FXML
    private TableView<AlgorithmProgress> usersProgressTable;

    @FXML
    private TableColumn<AlgorithmProgress,String> usernameColumn;

    @FXML
    private TableColumn<AlgorithmProgress, String> generationColumn;

    @FXML
    private TableColumn<AlgorithmProgress, String> bestFitnessColumn;
    
    //ViewButtonsTable
    @FXML
    private TableView<ViewButtons> algorithmViewTable;

    @FXML
    private TableColumn<ViewButtons, Button> bestSolutionButtonColumn;

    @FXML
    private TableColumn<ViewButtons, Button> configButtonColumn;


    
    
    private EvolutionAlgorithmPropertyManager algorithmPropertyManager = new EvolutionAlgorithmPropertyManager();
    private HttpClientETT client = null;
    private MainController mainController = null;
    private AlgorithmProgressThread progressThread = null;
    private Thread updateUsersProgressThread = null;
    //Graph
    @FXML
    private LineChart<Number, Number> BestfitnessChart;

    @FXML
    private NumberAxis generationAxis;

    @FXML
    private NumberAxis FitnessAxis;
    
    private  XYChart.Series graphSeries = new XYChart.Series();
    
    
    public void setMainController(MainController mainController)
    {
    	this.client = mainController.getClient();
    	this.mainController = mainController;
    }
    
    public void prepare()
    {
    	if(progressThread == null)
    	{
    		progressThread = new AlgorithmProgressThread(algorithmPropertyManager, client, mainController.getCurrentProblemLoadedId(),graphSeries);
    		progressThread.start();
    	}
    	if(updateUsersProgressThread == null)
    	{
    		updateUsersProgressThread = new Thread(()->
    		{
    			 String prevProblemId = "";
    				while(true)
    				{
    					try {
    						String problemId = mainController.getCurrentProblemLoadedId().getString();
							List<UserAlgorithmProgress> usersProgress = client.getAlgorithmProgressOfAllUsers(problemId);
							if(usersProgress == null)
							{
								continue;
							}
							boolean PopulateViewButtons = !problemId.equals(prevProblemId) ;
							prevProblemId = problemId;
							pouplateUsersProgress(usersProgress,PopulateViewButtons);
							Thread.sleep(100);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
							//e.printStackTrace();
						}
    				}
    		});
    		updateUsersProgressThread.setDaemon(true);
    		
    		updateUsersProgressThread.start();
    	}
    }
    
    private void initStopConditions()
    {
    	
		
		chooseConditionsLabel.visibleProperty().bind(algorithmPropertyManager.isThereAnyStopConditionsProperty().not());
		
		
		fitnessConditionAnchor.visibleProperty().bind(algorithmPropertyManager.IsStopByFitnessExistsProperty());
		fitnessConditionAnchor.managedProperty().bind(algorithmPropertyManager.IsStopByFitnessExistsProperty());
		
		generationConditionPane.visibleProperty().bind(algorithmPropertyManager.IsStopByGenerationsExistsProperty());
		generationConditionPane.managedProperty().bind(algorithmPropertyManager.IsStopByGenerationsExistsProperty());
		
		TimerConditionAnchor.visibleProperty().bind(algorithmPropertyManager.IsStopByTimeExistsProperty());
		TimerConditionAnchor.managedProperty().bind(algorithmPropertyManager.IsStopByTimeExistsProperty());
		
	//	initStopConditionsProgressBar();
		//ProgressBar
		timeProgressBar.progressProperty().bind(Bindings.divide(algorithmPropertyManager.algorithmRunTimeInMsProperty(),algorithmPropertyManager.timeLimitInMsProperty()));
    	fitnessProgressBar.progressProperty().bind(Bindings.divide(algorithmPropertyManager.bestFitnessProperty(),algorithmPropertyManager.fitnessLimitProperty()));
    	generationsProgressBar.progressProperty().bind(Bindings.divide(algorithmPropertyManager.generationsProperty(),algorithmPropertyManager.generationsLimitProperty()));

    }
  

   private void initGraph()
   {

		 BestfitnessChart.setCreateSymbols(false);
		 BestfitnessChart.setLegendVisible(false);
		FitnessAxis.setTickUnit(25);
		BestfitnessChart.getData().add(graphSeries);
		graphSeries.getData().clear();
   }
   
   private void initViewButtonsTable()
   {
	   bestSolutionButtonColumn.setCellValueFactory(new PropertyValueFactory<>("bestSolutionButton"));
	   configButtonColumn.setCellValueFactory(new PropertyValueFactory<>("algorithmConfigButton"));
   }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 algorithmStatusLabel.setText("");
		 algorithmRunningProgressBar.setVisible(false);
	     initStopConditions();
	     initGraph();
	     initViewButtonsTable();
	  // ShowOperationButonInStack(RunButton);

	    algorithmStatusLabel.visibleProperty().bind(algorithmPropertyManager.isThereAnyStopConditionsProperty());
		GenerationsPassedLabel.textProperty().bind(Bindings.concat("Generations Number:"+System.lineSeparator(),algorithmPropertyManager.generationsProperty()));
		BestFitnessLabel.textProperty().bind(Bindings.concat("Best Fitness:",algorithmPropertyManager.bestFitnessProperty().asString("%.3f")));
		runningTimeLabel.textProperty().bind(Bindings.concat("Running Time:",System.lineSeparator(),algorithmPropertyManager.algorithmRunTimeInMsProperty().divide(1000)));
		algorithmRunningProgressBar.visibleProperty().bind(algorithmPropertyManager.IsAlgorithmRunningProperty());
		
		//AlgorithmProgressTable
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username") );
		generationColumn.setCellValueFactory(new PropertyValueFactory<>("generation"));
		bestFitnessColumn.setCellValueFactory(new PropertyValueFactory<>("fitness"));
		
		initOperationsButtons();
		
		
		//Button
		//ResumeButton.disableProperty().bind(Bindings.or(null, null));
		
	}
	
	private void initOperationsButtons()
	{
		BooleanProperty algorithmRunning = algorithmPropertyManager.IsAlgorithmRunningProperty();
		BooleanProperty algorithmReachedStopCond = algorithmPropertyManager.algorithmReachedStopConditionProperty();
		BooleanProperty isThereAnyStopCond = algorithmPropertyManager.isThereAnyStopConditionsProperty();
		BooleanBinding runOrReachedStopCond = Bindings.or(algorithmRunning, algorithmReachedStopCond);
		BooleanBinding runOrReachedStopCondOrStopCondNotExists=Bindings.or(runOrReachedStopCond, isThereAnyStopCond.not());
		//isThereAnyStopCond.addListener((obser)->{StopButton.fire();});
		   
		RunButton.disableProperty().bind(Bindings.or(algorithmRunning,isThereAnyStopCond.not()));
		ResumeButton.disableProperty().bind(runOrReachedStopCondOrStopCondNotExists);
		StopButton.disableProperty().bind(isThereAnyStopCond.not());
		
		isThereAnyStopCond.addListener((obser,oldval,newval)->{
			
			if(newval == false)
			{
				stopAlgorithm();
                   
			}
		});
	}
	
	public void clearGraph()
	{
		Platform.runLater(()->
		{
		  graphSeries.getData().clear();
		});
	}
    

    @FXML
    void ResumeButtonOnAction(ActionEvent event) {
    	
    	String problemId = mainController.getCurrentProblemLoadedId().getString();
    	String username = client.getUsername();
    	
    	try {
			client.operateAlgorithm(problemId, AlgorithmOperation.Resume);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    private void pouplateUsersProgress(List<UserAlgorithmProgress> usersProgress,boolean forceUpdateButtonsView)
    {
    	List<AlgorithmProgress> usersProgressFixed = new ArrayList<>();
    	boolean reloadViewButtons = usersProgress.size() != algorithmViewTable.getItems().size() | forceUpdateButtonsView;
    	List<ViewButtons> viewButtonsFixed = new ArrayList<>();
    	for(UserAlgorithmProgress progress : usersProgress)
    	{
    		AlgorithmProgress fixedProgress = progress.getAlgorithmProgress();
    		fixedProgress.setUsername(progress.getUserName());
    		usersProgressFixed.add(fixedProgress);
    		
    		Button configButton = new Button("Show Config!!!");
    		Button bestSolutionButton = new Button("Show Solution!!!");
    		viewButtonsFixed.add(new ViewButtons(configButton, bestSolutionButton));
    		
    		configButton.setOnAction((action)->{
    			String userName = progress.getUserName();
    			String problemId = mainController.getCurrentProblemLoadedId().getString();
    			mainController.viewAlgorithmConfig(userName, problemId);
    		});
    		
    		bestSolutionButton.setOnAction((action)->{
    			String userName = progress.getUserName();
    			String problemId = mainController.getCurrentProblemLoadedId().getString();
    			mainController.viewBestSolution(userName, problemId);
    		});
    	}
    	Platform.runLater(()->{
    		usersProgressTable.setItems(ObservableUtils.CollectionToObseravbleList(usersProgressFixed));
    		if(reloadViewButtons)
    		{
    		algorithmViewTable.setItems(ObservableUtils.CollectionToObseravbleList(viewButtonsFixed));
    		}
    		int index = 0;
    		for(UserAlgorithmProgress progress : usersProgress)
    		{
    			algorithmViewTable.getItems().get(index).getBestSolutionButton().setDisable(progress.getAlgorithmProgress().getGeneration() == 0);
    		    index++;
    		}
    	});
    	
    	
    	
    	
    		
    }

    @FXML
    void RunButtonOnAction(ActionEvent event) {
    	
    	String problemId = mainController.getCurrentProblemLoadedId().getString();
    	try {
			client.operateAlgorithm(problemId, AlgorithmOperation.Run);
			clearGraph();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



    }
   private void stopAlgorithm()
   {
	   String problemId = mainController.getCurrentProblemLoadedId().getString();
   	String username = client.getUsername();
   	try {
   		
			client.operateAlgorithm(problemId, AlgorithmOperation.Stop);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

   }
    @FXML
    void StopButtonOnAction(ActionEvent event) {
    	
         stopAlgorithm();
    }

	public void populateGraph(List<AlgorithmProgress> algorithmGraph) {
		// TODO Auto-generated method stub
		System.out.println("wtf is happening");
		Platform.runLater(()->{
			graphSeries.getData().clear();
			List<XYChart.Data>graphData= algorithmGraph.stream().map(progress -> new XYChart.Data(progress.getGeneration(),progress.getFitness()))
			.collect(Collectors.toList());
			System.out.println("why not working dude");
			graphSeries.getData().addAll(graphData);
			
		});
	}

}










