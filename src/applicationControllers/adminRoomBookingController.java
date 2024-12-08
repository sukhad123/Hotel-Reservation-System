package applicationControllers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class adminRoomBookingController implements AbstractController {

    @FXML
    private Button cancelBtn;

    @FXML
    private Button continueBtn;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField guestNumber;
    private Main mainApp;
    @FXML
    void cancelOnAction(ActionEvent event) {
    	mainApp.AdminFirstPage();
    }

    @FXML
    void continueAction(ActionEvent event) {
    	if(inputChecker())
    	{
    		//share the available rooms
    		mainApp.adminRoomBookingController1( Integer.parseInt(guestNumber.getText().trim()) , null);
    	}
    	

    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public boolean inputChecker() {
        
        if (guestNumber.getText().isEmpty() || !(guestNumber.getText().trim().matches("\\d{1}"))) {
            showAlert("Invalid Input", "Enter Only numbers");
            return false;
        }
       
        
        
        return true; 
    }

	@Override
	public void settingMainApp(Main mainApp) {
		this.mainApp = mainApp;
		// TODO Auto-generated method stub
		
	}

}
