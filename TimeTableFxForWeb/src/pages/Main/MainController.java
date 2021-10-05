package pages.Main;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXHamburger;

import DTO.AlgorithmConfuigrationDTO;
import DTO.BestTimeTableSolutionDTO;
import DTO.ClassDTO;
import DTO.SubjectDTO;
import DTO.TeacherDTO;
import MainTesting.TimeTablePrinter;
import ServerDTO.ChatRecord;
import ServerDTO.Response;
import Utils.Entity;
import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import animatefx.animation.JackInTheBox;
import animatefx.animation.Jello;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideInUp;
import animatefx.animation.ZoomIn;
import application.Main;
import clientServer.HttpClientETT;
import clientServer.ServerErrorException;
import console.jaxb.schema.timetable.XmlApiTimeTable;
import evolution.algorithm.AlgorithmProgress;
import evolution.algorithm.stopcondition.StopByFitness;
import evolution.timetable.EvolutionTimeTableProblemEngine;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import page.Teachers.TeachersPageController;
import pages.PageName;
import pages.AlgorithmConfiguration.AlgorithmConfigurationController;
import pages.AlgorithmOperation.AlgorithmOperationController;
import pages.BestSolution.BestSolutionPageController;
import pages.Classes.ClassesPageController;
import pages.EntityView.EntityPageController;
import pages.Errors.ErrorsPageController;
import pages.Login.LoginController;
import pages.Rules.RulesPageController;
import pages.TimeProblems.TimeProblemsPageController;
import school.Teacher;
import wrappers.AtomicString;

public class MainController implements Initializable {

	//Pages
	 private  Map<PageName,Parent> pagesMap=new HashMap<>();
	//SubPagesLayouts
		//ErrorPage
	 private  ErrorsPageController errorPageController;
		//Teachers Page
	 private  TeachersPageController teachersPageController;
		
		//Entity Page /Subjects Page
	 private  EntityPageController entityPageController;
		
		//SchoolClasses Page
	 private   ClassesPageController classesPageController;
		
		//Rules PAge
	 private  RulesPageController rulesPageController;
		
		//AlgorithmOperatoin
		
	 private  AlgorithmOperationController algorithmOperationController;
		
		//Algorithm Config
	 private  AlgorithmConfigurationController algorithmConfigurationController;
		
		//bestSolutionPage
		
	 private  BestSolutionPageController bestSolutionPageController;
	 
	 //loginPageConroller
	 private LoginController loginPageController;
	 
	 //TimeProblemContoller
	 private TimeProblemsPageController timeProblemsController;
	
	private EvolutionTimeTableProblemEngine timeTableEngine = null;
	@FXML 
	BorderPane mainPageBorderPane;

    @FXML
    private AnchorPane mainContentPane;

    @FXML
    private JFXButton studentsButton;

    @FXML
    private JFXButton subjectsButton;

    @FXML
    private JFXButton rulesButton;

    @FXML
    private JFXButton algorithmConfigButton;

    @FXML
    private JFXButton algorithmOperationButton;

    @FXML
    private JFXButton teachersButton;

    @FXML
    private JFXButton bestSolutionButton;
    
    @FXML
    private Button loadFxmlButton;
    
    @FXML
    private JFXCheckBox animationsCheckBox;
    
    private BooleanProperty isProblemLoaded = new SimpleBooleanProperty(false);
    private BooleanProperty isLogined = new SimpleBooleanProperty(false);
    private BooleanProperty isServerUp = new SimpleBooleanProperty(false);
    
    private HttpClientETT client = new HttpClientETT();
    private AtomicString currentProblemLoadedId = new AtomicString("");
    @FXML
    private JFXButton timeProblemsButton;
    
   
    @FXML
    private Label pageTitle;
   
    //CHAT
    
    @FXML
    private AnchorPane chatPanel;
    @FXML
    private TextArea chatTextArea;

    @FXML
    private TextField chatTextBox;
    
    private Thread chatThread = null;
    private int amountOfMessagesInChat = 0;

    public BooleanProperty serverUpProperty()
    {
    	return isServerUp;
    }
    
    public HttpClientETT getClient() {
		return client;
	}

    @FXML
    void chatTextBoxOnAction(ActionEvent event) {
        
    	try {
    	//	System.out.println("meow");
    		if(chatTextBox.getText().equals(""))
    		{
    			return;
    		}
			client.addChatMessage(chatTextBox.getText());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	
    	chatTextBox.setText("");
    }
    
    private void setIsServerUp(boolean serverUp)
    {
    	Platform.runLater(()->{isServerUp.set(serverUp);});
    	
    }
    private void disconnectedFromServerAction()
    {
    	setLogin(false);
    	setIsProblemLoaded(false);
    	pageTitle.setText("No Connection To Server");
    	setIsServerUp(false);
    	if(getContent() != pagesMap.get(PageName.LoginPage))
    	{
    		
    		loadPage(PageName.LoginPage);
    		Alert disconnectedAlert = new Alert(AlertType.WARNING);
    		disconnectedAlert.setHeaderText("Disconnected From Server");
    		disconnectedAlert.setContentText("");
    		disconnectedAlert.show();
    	
    	}
    	
    }
    
    @FXML
    void timeProblemButtonOnAction(ActionEvent event) {
    //	System.out.println("here");
    	timeProblemsController.prepare();
    	timeProblemsController.populateProblemsFromServer();
        setContent(pagesMap.get(PageName.TimeProblemsPage));
    }
    
    public void viewBestSolution(String username,String problemId)
    {
    	try {
			BestTimeTableSolutionDTO bestSolutionOfUser = client.getBestSolutionDTO(username, problemId);
			bestSolutionPageController.populateSolution(bestSolutionOfUser);
	    	loadPage(PageName.BestSolutionView);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void viewAlgorithmConfig(String username,String problemId)
    {
    	try
    	{
    		AlgorithmConfuigrationDTO config = client.getAlgorithmConfigDTO(username, problemId);
    		algorithmConfigurationController.populateConfigReadOnly(config);
    		loadPage(PageName.AlgorithmConfiguration);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public void updateCurrentTimeProblemId(String problemId)
    {
    	getCurrentProblemLoadedId().setString(problemId);
    	setIsProblemLoaded(true);
    	List<AlgorithmProgress> algorithmGraph = new ArrayList<>();
    	
    	try {
			algorithmGraph = client.getAlgorithmGraph(problemId);
			System.out.println(algorithmGraph.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	algorithmOperationController.populateGraph(algorithmGraph);
    	
    }
    
    public AtomicString getCurrentProblemLoadedId()
    {
    	return currentProblemLoadedId;
    }
    
    public void setTitle(String newTitle)
    {
    	Platform.runLater(()->
    	{
    	    pageTitle.setText(newTitle);
    	});
    }

     
     

	public EvolutionTimeTableProblemEngine getTimeTableEngine()
    {
    	return timeTableEngine;
    	
    }

    private void loadPages() throws IOException
	{
    	
    	//timeProblemsController
    	FXMLLoader timeProblemsPageLoader = Main.getLoaderOfPage(TimeProblemsPageController.class, "TimeProblemsPage.fxml");
    	pagesMap.put(PageName.TimeProblemsPage, timeProblemsPageLoader.load());
    	timeProblemsController = timeProblemsPageLoader.getController();
    	timeProblemsController.setMainController(this);
    	
		//LoginPage
    	FXMLLoader loginPageLoader = Main.getLoaderOfPage(LoginController.class,"Login.fxml");
    	pagesMap.put(PageName.LoginPage, loginPageLoader.load());
    	loginPageController = loginPageLoader.getController();
    	loginPageController.setMainController(this);
		
		//ErrorPage
		FXMLLoader errorPageLoader =Main.getLoaderOfPage(ErrorsPageController.class, "ErrorsPage.fxml");
		pagesMap.put(PageName.ErrorPage,errorPageLoader.load());
		errorPageController = errorPageLoader.getController();
		
		//Teachers Page
		FXMLLoader teachersPageLoader = Main.getLoaderOfPage(TeachersPageController.class, "TeachersPage.fxml");
		pagesMap.put(PageName.TeacherView, teachersPageLoader.load());
		teachersPageController  = teachersPageLoader.getController();
		
		//EntityPage/SubjectsPage
		FXMLLoader entityPageLoader = Main.getLoaderOfPage(EntityPageController.class, "EntityPage.fxml");
		pagesMap.put(PageName.SubjectView,entityPageLoader.load());
		entityPageController = entityPageLoader.getController();
		
		//classesPage
		FXMLLoader classesPageLoader = Main.getLoaderOfPage(ClassesPageController.class,"ClassesPage.fxml");
		pagesMap.put(PageName.ClassView,classesPageLoader.load());
		classesPageController = classesPageLoader.getController();
		
		//Rules Page
		FXMLLoader rulesPageLoader = Main.getLoaderOfPage(RulesPageController.class,"RulesPage.fxml");
		pagesMap.put(PageName.RulesView,rulesPageLoader.load());
		rulesPageController = rulesPageLoader.getController();
		
		//Algorithm Operation
		FXMLLoader operationPageLoader = Main.getLoaderOfPage(AlgorithmOperationController.class,"AlgorithmOperationPage.fxml");
		pagesMap.put(PageName.AlgorithmOperation,operationPageLoader.load());
		algorithmOperationController = operationPageLoader.getController();
		algorithmOperationController.setMainController(this);
		
		//Algorithm Config
		FXMLLoader algorithmConfigPageLoader = Main.getLoaderOfPage(AlgorithmConfigurationController.class, "AlgorithmConfiguration.fxml");
		pagesMap.put(PageName.AlgorithmConfiguration, algorithmConfigPageLoader.load());
		algorithmConfigurationController= algorithmConfigPageLoader.getController();
		algorithmConfigurationController.setMainController(this);
		
		//BestSOlution Page
		
		FXMLLoader bestSolutionPageLoader = Main.getLoaderOfPage(BestSolutionPageController.class, "BestSolutionPage.fxml");
		pagesMap.put(PageName.BestSolutionView, bestSolutionPageLoader.load());
		bestSolutionPageController = bestSolutionPageLoader.getController();
		
		
		
		
	//	getLoaderOfPage(ShecdulePageController.class, "ShecduleLeacturePage.fxml").load();
		
		
		
	}
    
    
    @FXML
    void loadFxmlButtonOnAction(ActionEvent event)
    {
    	
    	FileChooser fileChooser=new FileChooser();
		File selectedFile=fileChooser.showOpenDialog(null);
		if(selectedFile!=null)
		{
			try {
			    Response response = client.uploadXmlFile(selectedFile);
			    if(response == null)
			    {
			    	throw new Exception("");
			    }
			   
			    if(getContent() != pagesMap.get(PageName.TimeProblemsPage) && !response.isError() )
			    {
				  timeProblemsButton.fire();
			    }
			    else if(response.isError())
			    {
			    	String errorMessage = response.getError();
			    	List<String> uploadFileErrors =  (List<String>) response.getData();
			    	errorPageController.setErrorLabel(errorMessage);
			    	errorPageController.populateErrors(uploadFileErrors);
			    	loadPage(PageName.ErrorPage);
			    	
			    }
				
			} catch (Exception e) {
				e.printStackTrace();
			    displayMessageScreen("Something Went Wrong!!!");
				
			}
		}
		
    }
    
    public void displayMessageScreen(String message)
    {
    	Label label = new Label(message);
    	label.setFont(new Font(30));
    	label.setMinWidth(800);
    	StackPane box=new StackPane();
    	box.setAlignment(Pos.TOP_CENTER);
    	box.getChildren().add(label);
    	box.setPadding(new Insets(10));
    	setContent(box);
    }
    
    private EvolutionTimeTableProblemEngine loadTimeTableEngine(File selectedFile) throws Exception
	{
    	XmlApiTimeTable xmlApi=null;
		try
		
		{
		    xmlApi=new XmlApiTimeTable(2);
			//timeTableEngine=xmlApi.load(selectedFile.getPath());
			timeTableEngine.getAlgorithmConfiguration().getStopConditoins().add(new StopByFitness(1.0));
			return timeTableEngine;
			
		}
		catch(Exception e)
		{
		
			errorPageController.populateErrors(xmlApi.getAllErrors());
			errorPageController.setErrorLabel(e.getMessage());
			throw e;
		}
	}
    
    @FXML
    void algorithmConfigButtonOnAction(ActionEvent event) {
    	if(!client.isWebServerUp())
    	{
    		displayMessageScreen("Server is down");
    		return;
    	}
    	System.out.println("algorithm config button clicked");
    	algorithmConfigurationController.prepare();	
    	setContent(pagesMap.get(PageName.AlgorithmConfiguration));
    	if(animationsCheckBox.isSelected())
    	{
    		new SlideInRight(pagesMap.get(PageName.AlgorithmConfiguration)).play();;
    	}
    }

    @FXML
    void algorithmOperationButtonOnAction(ActionEvent event) {
    	if(!client.isWebServerUp())
    	{
    		displayMessageScreen("Server is down");
    		return;
    	}
    	algorithmOperationController.prepare();
    	setContent(pagesMap.get(PageName.AlgorithmOperation));
    	if(animationsCheckBox.isSelected())
    	{
    	new FadeIn(pagesMap.get(PageName.AlgorithmOperation)).play();
    	}
    }

    @FXML
    void bestSolutionButtonOnAction(ActionEvent event) {
    	if(!client.isWebServerUp())
    	{
    		displayMessageScreen("Server is down");
    		return;
    	}
    	
    	try
    	{
    		System.out.println("username? = " + client.getUsername());
    		BestTimeTableSolutionDTO bestSolutionDTO = client.getBestSolutionDTO(client.getUsername(),currentProblemLoadedId.getString());
    	    if(bestSolutionDTO == null)
    	    {
    	    	displayMessageScreen("Something Went Wrong");
    	    }
    	bestSolutionPageController.populateSolution(bestSolutionDTO);
    	loadPage(PageName.BestSolutionView);
    	if(animationsCheckBox.isSelected())
    	{
    		new BounceIn(pagesMap.get(PageName.BestSolutionView)).play();;
    	}
    	
    	}
    	catch(Exception e)
    	{
    		System.out.println("i am here");
    		e.printStackTrace();
    		displayMessageScreen(e.getMessage());
    	}
    }

    @FXML
    void rulesButtonOnAction(ActionEvent event) {
    	if(!client.isWebServerUp())
    	{
    		displayMessageScreen("Server is down");
    		return;
    	}
    	try
    	{
    	rulesPageController.populateRulesAndAttributes(client.getTimeProblemWrapperDTO(currentProblemLoadedId.getString()).getTimeProblem());
    	setContent(pagesMap.get(PageName.RulesView));
    	
    	if(animationsCheckBox.isSelected())
    	{
    		
    		new JackInTheBox(pagesMap.get(PageName.RulesView)).play();
    	}
    	}
    	catch(Exception e)
    	{
    		displayMessageScreen("Something Went Wrong");
    	}
    }

    @FXML
    void studentsButtonOnAction(ActionEvent event) {
    	if(!client.isWebServerUp())
    	{
    		displayMessageScreen("Server is down");
    		return;
    	}
    	try
    	{
    	List<ClassDTO> classes = client.getTimeProblemWrapperDTO(currentProblemLoadedId.getString()).getTimeProblem().getClasses();
    	classesPageController.populateClasses(classes);
    	setContent(pagesMap.get(PageName.ClassView));
    	
    	if(animationsCheckBox.isSelected())
    	{
  
    		new ZoomIn(pagesMap.get(PageName.ClassView)).play();
    	}
    	
    	}
    	catch(Exception e)
    	{
    		displayMessageScreen("Something Went Wrong");
    	}
    	
    }

    @FXML
    void subjectsButtonOnAction(ActionEvent event) {
    	if(!client.isWebServerUp())
    	{
    		displayMessageScreen("Server is down");
    		return;
    	}
    	try
    	{
    	List<SubjectDTO> subjects = client.getTimeProblemWrapperDTO(currentProblemLoadedId.getString()).getTimeProblem().getSubjects();
    	entityPageController.populateEntities(subjects);
    	setContent(pagesMap.get(PageName.SubjectView));
    	
    	if(animationsCheckBox.isSelected())
    	{
    		new Jello(pagesMap.get(PageName.SubjectView)).play();
    	}
    	
    	}
    	catch(Exception e)
    	{
    		displayMessageScreen("Something Went Wrong");
    	}

    }

    @FXML
    void teachrsButtonOnAction(ActionEvent event) {

    	if(!client.isWebServerUp())
    	{
    		displayMessageScreen("Server is down cant show teachers");
    		return;
    	}
    	try
    	{
    	List<TeacherDTO> teachers = client.getTimeProblemWrapperDTO(currentProblemLoadedId.getString()).getTimeProblem().getTeachers();
    	teachersPageController.populateTeachers(teachers);
    	setContent(pagesMap.get(PageName.TeacherView));
    	if(animationsCheckBox.isSelected())
    	{
    		new SlideInUp(pagesMap.get(PageName.TeacherView)).play();
    	}
    	}
    	catch(Exception e)
    	{
    		displayMessageScreen("Something Went Wrong");
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		try
		{
		setTitle("");
		loadPages();
		setContent(pagesMap.get(PageName.LoginPage));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("failed to create pages in MainController");
			System.exit(0);
		}
		Node [] componentsThatCantBeUsedTillFileLoaded = new Node[] 
				{teachersButton,subjectsButton,studentsButton,algorithmConfigButton,bestSolutionButton,algorithmOperationButton,rulesButton};
		for(Node component : componentsThatCantBeUsedTillFileLoaded)
		{
			component.disableProperty().bind(isProblemLoaded.not());
		}
		
		timeProblemsButton.disableProperty().bind(isLogined.not());
		chatPanel.managedProperty().bind(isLogined);
		chatPanel.visibleProperty().bind(isLogined);
		loadFxmlButton.disableProperty().bind(isLogined.not());
		chatThread = new Thread(()->
		{
			boolean connectedToServer = true;
			try
			{
			while(true)
			{
			try
			{
				
				List<ChatRecord> chatRecords = client.getAllChatRecords();
				
				if(!connectedToServer)
				{
					
					Platform.runLater(()->{
						pageTitle.setText("");
					});
					setIsServerUp(true);
				connectedToServer = true;
				}
				if(chatRecords == null)
				{
					continue;
				}
				Platform.runLater(()->{
					populateChat(chatRecords);
				});
				Thread.sleep(300);
			}
			catch(ConnectException | SocketTimeoutException serverDown  )
			{
				
				
				if(connectedToServer )
				{
					
					Platform.runLater(()->{
						disconnectedFromServerAction();	
						
					});
					connectedToServer = false;
					
				}
				
			}
			catch(ServerErrorException e)
			{
				
				if(!connectedToServer)
				{
					
					Platform.runLater(()->{
						pageTitle.setText("");
					});
				}
				
				setIsServerUp(true);
				connectedToServer = true;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				
			}
			}//while true ends
			
			} catch(Exception e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		});
		chatThread.setDaemon(true);
		chatThread.start();
		//chatTextBox.visibleProperty().bind(isLogined);
	//	bestSolutionPageController.bindToAlgorithmRunning(algorithmOperationController.IsAlgorithmRunningProperty());
	//	algorithmConfigButton.disableProperty().bind(Bindings.or(isFxmlLoaded.not(), algorithmOperationController.IsAlgorithmRunningProperty()));
		//loadFxmlButton.disableProperty().bind(algorithmOperationController.IsAlgorithmRunningProperty());
		//bestSolutionButton.disableProperty().bind(Bindings.or(isFxmlLoaded.not(),algorithmOperationController.algorithmRunnedAtlistOnceProperty().not()));
	}
	
	public void populateChat(List<ChatRecord> chatRecords)
	{
	   if(chatRecords.size() == amountOfMessagesInChat)
	   {
		   return;
	   }
		chatTextArea.clear();
		for(ChatRecord chatMsg : chatRecords)
		{
			chatTextArea.appendText(chatMsg.getFullMessage()+System.lineSeparator());
		}
		
		amountOfMessagesInChat = chatRecords.size();
	}
	
	public void loadPage(PageName pageName)
	{
		Parent pageLayout = pagesMap.get(pageName);
		if(pageLayout != null)
		{
			setContent(pageLayout);
		}
	}
	
	private Node getContent()
	{
		return mainPageBorderPane.getCenter();
	}
	public boolean isInLoginPage()
	{
		return getContent() == pagesMap.get(PageName.LoginPage);
	}
	
	public void setContent(Parent content)
	{
		//mainContentPane.getChildren().clear();
		//mainContentPane.getChildren().add(content);
		mainPageBorderPane.setCenter(content);
		
		//mainContentPane.getChildren().get(0).set
	}
	
	public void setIsProblemLoaded(boolean loaded)
	{
		Platform.runLater(()->{isProblemLoaded.set(loaded);});
	}

	public  void setLogin(boolean login) {
		// TODO Auto-generated method stub
		
		Platform.runLater(()-> {isLogined.set(login);});
		
	}
	
	public void login(String username)
	{
		setLogin(true);
		setTitle("Welcome "+username);
		Platform.runLater(()->{
			timeProblemsButton.fire();
		});
		
	}
}