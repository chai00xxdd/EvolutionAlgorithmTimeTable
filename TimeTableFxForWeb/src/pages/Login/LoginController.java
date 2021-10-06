package pages.Login;

import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import clientServer.HttpClientETT;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import pages.Main.MainController;

public class LoginController implements Initializable {

    @FXML
    private Label loginErrorLabel;

    @FXML
    private TextField usernameTextField;

    @FXML
    private JFXButton loginButton;
    
    private MainController mainController;
    
    private Media musicMedia = null;
    private MediaPlayer musicPlayer = null;
    
    public void setLoginMusic(Media musicMedia)
    {
    	this.musicMedia = musicMedia;
    }
    
    public void playMusic()
    {
    	
    	if(musicPlayer != null)
    	{
    		musicPlayer.stop();
    	}
    	if(musicMedia != null)
    	{
    	musicPlayer = new MediaPlayer(musicMedia);
    	musicPlayer.play();
    	}
    }
    
    public void stopMusic()
    {
    	if(musicPlayer != null)
    	{
    		musicPlayer.stop();
    	}
    }
    
    public void prepare()
    {
    	//play music if not null and so on
    }
    
    public void setMainController(MainController mainController)
    {
    	this.mainController = mainController;
    	usernameTextField.disableProperty().bind(mainController.serverUpProperty().not());
		loginButton.disableProperty().bind(mainController.serverUpProperty().not());
    	
    }
    
    

    @FXML
    void loginButtonOnAction(ActionEvent event) {
         String username = usernameTextField.getText();
        HttpClientETT client = mainController.getClient();
        try {
			client.login(username);
			loginErrorLabel.setText("");
			mainController.login(username);
			
		}
        catch(ConnectException e)
        {
        	loginErrorLabel.setText("No Connection To Server!!!");
        }
        catch (Exception e) {
			loginErrorLabel.setText(e.getMessage());
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		loginErrorLabel.setText("");
		usernameTextField.setOnAction((action)->{
			loginButton.fire();
		});
	
		
	     
	}

}
