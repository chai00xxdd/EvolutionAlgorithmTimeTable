package pages.Errors;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import com.sun.jndi.toolkit.ctx.AtomicDirContext;

import FxUtils.ObservableUtils;
import FxUtils.TableUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ErrorsPageController implements Initializable {

    @FXML
    private TableView<ErrorDetails> errorTable;

    @FXML
    private TableColumn<ErrorDetails, String> errorNumberColumn;

    @FXML
    private TableColumn<ErrorDetails, String> errorDescriptionColumn;
    
    @FXML
    private Label errorLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stubObservableUtils
		
		errorNumberColumn.setCellValueFactory(new PropertyValueFactory<>("errorNumber"));
		errorDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("error"));
		
		errorTable.setColumnResizePolicy((param) -> true);
		
	}
	
	public void setErrorLabel(String error)
	{
		errorLabel.setText(error);
	}

	public void populateErrors(List<String> allErrors) {
		
		
		errorTable.setItems(ObservableUtils.CollectionToObseravbleList(createErrorDetailsList(allErrors)));
		errorTable.setVisible(allErrors.size()!=0);
		TableUtils.FitToDataContent(errorTable);
	}
	
	
	
	private List<ErrorDetails> createErrorDetailsList(List<String> allErrors)
	{
		List<ErrorDetails> errorsList=new ArrayList<>();
		int errorNumber=1;
		for(String error: allErrors)
		{
			errorsList.add(new ErrorDetails(errorNumber++,error));
		}
		
		return errorsList;
	}

}
