package applicationControllers;

import application.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class adminPage1Controller implements AbstractController {

    @FXML
    private Button avaialbleroombtn;

    @FXML
    private Button billserviceBtn;

    @FXML
    private Button bookRoombtn;

    @FXML
    private Button currentBookingBtn;

    @FXML
    private Button exitbtn;

    @FXML
    private Button searchBtn;
    private Main main;
    
    
    
    //book new room
    

    public adminPage1Controller()
    {
    	main = new Main();
    }
    @FXML
    void availableBtnclicked(ActionEvent event) {
    	main.AvailableRoomPage();

    }

    @FXML
    void billserviceBtnClicked(ActionEvent event) {
    	main.billService();

    }

    @FXML
    void bookroombtnClicked(ActionEvent event) {
    	main.adminRoomBooking();

    }

    @FXML
    void currentbtnclicked(ActionEvent event) {
    	this.main.viewBookingPage();

    }

    @FXML
    void exitbtnclicked(ActionEvent event) {
    	main.intializer();

    }

    @FXML
    void searchBtnclicked(ActionEvent event) {

    }

	@Override
	public void settingMainApp(Main mainApp) {
		this.main = mainApp;
		// TODO Auto-generated method stub
		
	}

}
