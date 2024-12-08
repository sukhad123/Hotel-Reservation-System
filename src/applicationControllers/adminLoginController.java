package applicationControllers;

import application.Main;
import applicationDatabase.JDBCDA;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class adminLoginController implements AbstractController   {

    @FXML
    private PasswordField adminPassword;

    @FXML
    private TextField adminUserName;

    @FXML
    private Label alertLabel;

    @FXML
    private Button continueBtn;
    private Main main;
    private JDBCDA x;

    public adminLoginController()
    {
    	this.main = new Main();
    	 x = new JDBCDA();
    }
    @FXML
    void continueBtnClicked(ActionEvent event) {
    	if(inputChecker())
    	{
    		//check the credentials
    		if(x.checkAdmin(adminUserName.getText().trim(), adminPassword.getText().trim()))
    		{
    			main.AdminFirstPage();
    		}
    		else
    		{
    			showAlert("Access Denied", "Invalid Credentails");
    		}	
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
        
        if (adminUserName.getText().isEmpty() || !adminUserName.getText().matches("[a-zA-Z\\s]+")) {
            showAlert("Invalid User Name", "User Name must not be empty and should contain only alphabets.");
            return false;
        }
       
        
        if (adminPassword.getText().isEmpty() ) {
            showAlert("Invalid Password", "Password must not be empty.");
            return false;
        }
        return true; 
    }

	@Override
	public void settingMainApp(Main mainApp) {
		this.main = mainApp;
		// TODO Auto-generated method stub
		
	}

	 

}
