package pages.Classes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import DTO.ClassDTO;
import FxUtils.ObservableUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClassesPageController implements Initializable {

    @FXML
    private TableView<ClassDTO> classesTable;

    @FXML
    private TableColumn<ClassDTO, String> classIdColumn;

    @FXML
    private TableColumn<ClassDTO, String> classNameColumn;

    @FXML
    private TableColumn<ClassDTO, String> requirmentsColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		classIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		classNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		requirmentsColumn.setCellValueFactory(new PropertyValueFactory<>("requirmentsString"));
		
		
	}
	
	public void populateClasses(List<ClassDTO> classes)
	{
		classesTable.setItems(ObservableUtils.CollectionToObseravbleList(classes));
	}

}
