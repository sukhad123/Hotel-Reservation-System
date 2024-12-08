package applicationControllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;

//import java.lang.classfile.components.ClassPrinter.Node;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HomepageController implements AbstractController  {
	

    @FXML
    private Button adminBtn;

    @FXML
    private Button reserveCustBtn;
    private Main mainApp;
    public void settingMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
    @FXML
    void adminActionBtn(ActionEvent event) {
    	mainApp.adminLoginPage();
    }

    @FXML
    void reserveCustomer(ActionEvent event) {
    	mainApp.GuestWelcomePage();

    }

}
