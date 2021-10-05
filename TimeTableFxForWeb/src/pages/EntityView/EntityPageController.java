package pages.EntityView;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import DTO.SubjectDTO;
import FxUtils.ObservableUtils;
import Utils.Entity;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EntityPageController implements Initializable {

    @FXML
    private Label pageTitleLabel;

    @FXML
    private TableView<SubjectDTO> entityTable;

    @FXML
    private TableColumn<SubjectDTO, String> entityIdColumn;

    @FXML
    private TableColumn<SubjectDTO, String> entityNameColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		entityIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		entityNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
	}
	
	public void populateEntities(List<SubjectDTO> subjects)
	{
		
		entityTable.setItems(ObservableUtils.CollectionToObseravbleList(subjects));
	}
	
	public void setPageTitle(String title)
	{
		pageTitleLabel.setText(title);
	}
	
	

}
