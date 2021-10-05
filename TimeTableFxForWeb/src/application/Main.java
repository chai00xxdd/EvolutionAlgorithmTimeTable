package application;
	

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;
import com.sun.org.apache.xml.internal.security.algorithms.Algorithm;

import DTO.AlgorithmConfuigrationDTO;
import FxUtils.AlertMessage;
import ServerDTO.TimeProblemShortDTO;
import ServerDTO.TimeTableProblemWrapperDTO;
import clientServer.HttpClient;
import clientServer.HttpClientETT;
import clientServer.ServerErrorException;
import clientServer.ServletUrls;
import console.jaxb.schema.timetable.XmlApiTimeTable;
import evolution.algorithm.AlgorithmOperation;
import evolution.algorithm.stopcondition.StopByFitness;
import evolution.algorithm.stopcondition.StopByGenerations;
import evolution.algorithm.stopcondition.StopByTime;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Callback;
import page.Teachers.TeachersPageController;
import pages.PageName;
import pages.AlgorithmConfiguration.AlgorithmConfigurationController;
import pages.AlgorithmOperation.AlgorithmOperationController;
import pages.BestSolution.BestSolutionPageController;
import pages.Classes.ClassesPageController;
import pages.EntityView.EntityPageController;
import pages.Errors.ErrorsPageController;
import pages.Main.MainController;
import pages.MutationManager.MutationPageController;
import pages.Rules.RulesPageController;
import pages.ShecdulePage.ShecdulePageController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;



public class Main extends Application {
	//Static Field Cus Static is For Life
	//MainPage
	private  MainController mainPageController;
	private  Parent mainPageLayout;
	
	
	
	@Override
	public void start(Stage primaryStage) {
		
	
		try {
			
			loadPages();
		    primaryStage.setTitle("Evolution Algorithm TimeTable Application");
			
			
			Scene scene = new Scene(mainPageLayout,1250,1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			//AlertMessage.showMessage("error", "hello");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void loadPages() throws IOException
	{
		//MainPage
		FXMLLoader mainLoader = getLoaderOfPage(MainController.class,"Main.fxml");
		mainPageLayout =mainLoader.load();
		mainPageController = mainLoader.getController();
		
	
	
	}
	
	public static  FXMLLoader getLoaderOfPage(Class classOfPageController, String pageName)
	{
		FXMLLoader loader=new FXMLLoader();
		URL url =classOfPageController.getResource(pageName);
		loader.setLocation(url);
		
		return loader;
		
	}
	public static void main(String[] args) {
		launch(args);
		try {
			
			
		/*
			HttpClientETT client = new HttpClientETT();
			
			System.out.println("trying to login dude");
			client.login("or40");
			List<TimeProblemShortDTO> dto =client.getShortProblemsDTO();
			//System.out.println(dto);
			System.out.println(dto.get(0).getDays());
			TimeTableProblemWrapperDTO wrapperDto = client.getTimeProblemWrapperDTO("1");
			System.out.println(wrapperDto.getTimeProblem().getRules().size());
			AlgorithmConfuigrationDTO config = client.getAlgorithmConfigDTO("or35", "1");
			System.out.println(config.getUserName());
			client.getTimeProblemWrapperDTO("1");
			
		   client.operateAlgorithm("1",AlgorithmOperation.Stop);
		   System.out.println("wired");
		   System.out.println(client.getAlgorithmProgressOfAllUsers("1").size());
		   System.exit(0);
			*/
			
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	 
	
}
	
}
