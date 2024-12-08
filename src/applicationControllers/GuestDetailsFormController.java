package applicationControllers;

import java.util.ArrayList;

import application.Main;
import applicationDatabase.JDBCDA;
import applicationModels.GuestClass;
import applicationModels.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GuestDetailsFormController implements AbstractController{

    @FXML
    private TextField addressTxt;
    private Main mainApp;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button continueBtn;

    @FXML
    private TextField emailTxt;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField fullNameTxt;

    
    @FXML
    private TextField phoneNumTxt;
    private GuestClass guest;
    private JDBCDA database = new JDBCDA();
    @FXML
    void cancelOnAction(ActionEvent event) {
    	mainApp.RoomSelectionPage();
    }

    @FXML
    void continueAction(ActionEvent event) {
    	if (inputChecker()) {
           createGuest();
            
           this.mainApp.gettmpBooking().setGuestClass(guest);
           this.mainApp.gettmpBooking().setTotal();
           confirmation();
        } 
    	

    }
    
    public void createGuest()
    {
    	int noOfGuest = this.mainApp.gettmpBooking().getGuestClass().getGuests();
    	guest = new GuestClass(fullNameTxt.getText().trim(), addressTxt.getText().trim(),  (int) Long.parseLong(phoneNumTxt.getText().trim()), emailTxt.getText().trim(),noOfGuest);
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    
   //confirmation alert
    public void confirmation()
    {
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Booking Confirmation");
        alert.setHeaderText("Please confirm your booking details:");

        VBox content = new VBox(10); 
        content.getChildren().addAll(
            new Text("Full Name: " + guest.getFullName()),
            new Text("Address: " + guest.getAddress()),
            new Text("Email: " + guest.getEmail()),
            new Text("Phone: " + guest.getPhone()),
            new Text("Number of Guests: " + this.mainApp.gettmpBooking().getGuestClass().getGuests()),
            new Text("Selected Rooms: " + getRooms()),
            new Text("Total: $" + String.format("%.2f", mainApp.gettmpBooking().getTotal())),
            new Text("Total with Taxes: $" + String.format("%.2f", mainApp.gettmpBooking().getTotalWithTaxes()))
        );

        alert.getDialogPane().setContent(content);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                confirmation.setTitle("Booking Confirmed");
                confirmation.setHeaderText(null);
                confirmation.setContentText("Your booking has been successfully confirmed!");
                
                //save the data in database
                //update bookedroom
                database.addBooking(guest, mainApp.gettmpBooking().getReservation(),this.mainApp.gettmpBooking().getGuestClass().getGuests(), mainApp.gettmpBooking().getTotal());
                database.addBookedRoom(mainApp.gettmpBooking().getRooms());
                confirmation.showAndWait();

                mainApp.intializer();
            } else {
                System.out.println("Booking action canceled.");
            }
        });
         
    }
    public String getRooms()
    {
    	String tmp = "\n";
    	for(Room i: mainApp.gettmpBooking().getRooms())
    	{
    		tmp += i.getRoomType() + " \n";
    	}
    	return tmp;
    	
    }
    public boolean inputChecker() {
        
        if (fullNameTxt.getText().isEmpty() || !fullNameTxt.getText().matches("[a-zA-Z\\s]+")) {
            showAlert("Invalid Full Name", "Full Name must not be empty and should contain only alphabets.");
            return false;
        }

        if (!phoneNumTxt.getText().trim().matches("\\d{10}")) {
            showAlert("Invalid Phone Number", "Phone Number must be exactly 10 digits.");
            return false;
        }

        if (!emailTxt.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            showAlert("Invalid Email", "Please enter a valid email address.");
            return false;
        }

        if (addressTxt.getText().isEmpty()) {
            showAlert("Invalid Address", "Address cannot be empty.");
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
