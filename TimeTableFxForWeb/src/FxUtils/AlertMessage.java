package FxUtils;

import javafx.scene.control.Alert;

public class AlertMessage {

	public static void showMessage(String title,String message)
	{
		 Alert alert = new Alert(Alert.AlertType.INFORMATION);
	      //Setting the title
	      alert.setTitle(title);
	      alert.setHeaderText(message);	      
	      alert.showAndWait();
	}
}
