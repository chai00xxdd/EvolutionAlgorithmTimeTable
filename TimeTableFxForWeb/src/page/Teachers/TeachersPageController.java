package page.Teachers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import DTO.TeacherDTO;
import FxUtils.ObservableUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TeachersPageController implements Initializable {

    @FXML
    private TableView<TeacherDTO> teachersTable;

    @FXML
    private TableColumn<TeacherDTO, String> teacherIdColumn;

    @FXML
    private TableColumn<TeacherDTO, String> teacherNameColumn;

    @FXML
    private TableColumn<TeacherDTO, String> courseColumn;

    @FXML
    private TableColumn<TeacherDTO, String> workingPrefernceColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		teacherIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		courseColumn.setCellValueFactory(new PropertyValueFactory<>("coursesString"));
		workingPrefernceColumn.setCellValueFactory(new PropertyValueFactory<>("workingHoursPrefernce"));
		
	}
	
	public void populateTeachers(List<TeacherDTO> teachers)
	{
		teachersTable.setItems(ObservableUtils.CollectionToObseravbleList(teachers));
	}

}
