package applicationControllers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GuestWelcomePageController  implements AbstractController{

    @FXML
    private Button cancelBtn;

    @FXML
    private Button continueBtn;
    private Main mainApp;
    public void settingMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
    @FXML
    void cancelOnAction(ActionEvent event) {
    	mainApp.intializer();

    }

    @FXML
    void continueAction(ActionEvent event) {
    	 mainApp.GuestDetails();

    }

}