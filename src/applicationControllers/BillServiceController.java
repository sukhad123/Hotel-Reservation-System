package applicationControllers;

import java.util.ArrayList;

import application.Main;
import applicationDatabase.JDBCDA;
import applicationModels.DisplayBooking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class BillServiceController implements AbstractController {

    @FXML
    private TableView<DisplayBooking> booking;

    @FXML
    private TableColumn<DisplayBooking, Integer> bookingId;

    @FXML
    private Button continueBtn;

    @FXML
    private TableColumn<DisplayBooking, String> customerName;

    @FXML
    private Text noOfBooking;

    @FXML
    private TableColumn<DisplayBooking, Integer> numberOfDays;

    @FXML
    private TableColumn<DisplayBooking, Integer> numberOfRooms;

    @FXML
    private TableColumn<DisplayBooking, String> roomType;

    private Main mainApp;
    private ObservableList<DisplayBooking> observableList;
    @FXML
    private Button delete;
    // Constructor should not handle UI initialization directly
    public BillServiceController() {
        // Constructor should not perform database calls or UI updates
    }
    private JDBCDA database = new JDBCDA();

    // Called after FXML is loaded
    public void initialize() {
        // Initialize table columns
        if (bookingId != null) {
            bookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            numberOfDays.setCellValueFactory(new PropertyValueFactory<>("noOfDays1"));
            numberOfRooms.setCellValueFactory(new PropertyValueFactory<>("noOfRooms"));
            roomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        }

        // Load bookings from database
       
        ArrayList<DisplayBooking> bookings = database.getBooking();
        observableList = FXCollections.observableArrayList(bookings);
        
       
        booking.setItems(observableList);
        noOfBooking.setText(String.valueOf(observableList.size()));

    }

    @FXML
    void continueBtnClicked(ActionEvent event) {
        mainApp.AdminFirstPage();
    }
    
    @FXML
    void deletebooking(ActionEvent event) {
    	DisplayBooking selectedItem = booking.getSelectionModel().getSelectedItem();
    	// Check if an item is selected
    	if (selectedItem != null) {
    		 Alert alert = new Alert(AlertType.CONFIRMATION);
    	        alert.setTitle("Confirmation Dialog");
    	        alert.setHeaderText("Booking Details Click ok to offer Discount");
    	        String formattedRate = String.format("%.2f", selectedItem.getOneDay());
    	        String formattedTotal = String.format("%.2f", selectedItem.getTotal());
    	        // Create booking information string
    	        String bookingInfo = "Hotel Reservation System\n" +
    	                             "\nBooking ID: " + selectedItem.getBookingId() + 
    	                             "\nGuest Name: " + selectedItem.getCustomerName() + 
    	                             "\nNo of rooms booked: " + selectedItem.getNoOfRooms() +
    	                             "\nType of rooms: " + selectedItem.getRoomType() + 
    	                             "\nRate per night: " + formattedRate +
    	                             "\nTotal: " + formattedTotal;
    	        
    	        // Set content text for the booking info and the confirmation message
    	        alert.setContentText(bookingInfo + "\nClick OK to confirm or Cancel to abort.");

    	        // Show the alert and wait for user action
    	        alert.showAndWait().ifPresent(response -> {
    	            if (response == ButtonType.OK) {
    	            	  Dialog<ButtonType> dialog = new Dialog<>();
    	                  dialog.setTitle("Enter Discount");

    	                  // Create a VBox layout to hold the TextField
    	                  VBox vbox = new VBox();
    	                  TextField discountField = new TextField();
    	                  discountField.setPromptText("Enter discount (0 to 25%)");

    	                  // Add TextField to the layout
    	                  vbox.getChildren().add(discountField);
    	                  dialog.getDialogPane().setContent(vbox);

    	                  // Set header text for the dialog
    	                  dialog.setHeaderText("Please enter the discount percentage (0-25%)");

    	                  // Add OK and Cancel buttons to the dialog
    	                  dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
    	                  dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

    	                  // Show the dialog and wait for user input
    	                  dialog.showAndWait().ifPresent(response1 -> {
    	                      if (response1 == ButtonType.OK) {
    	                          try {
    	                              // Try to parse the discount value entered by the user
    	                              double discount = Double.parseDouble(discountField.getText());

    	                              // Validate discount input (must be between 0 and 25)
    	                              if (discount >= 0 && discount <= 25) {
    	                                  //display a ticket and share the details and si
    	                            	  displayBill(discount,selectedItem);
    	                            	  
    	                              } else {
    	                                  // Show an error alert if the discount is out of range
    	                                  Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    	                                  errorAlert.setTitle("Invalid Discount");
    	                                  errorAlert.setHeaderText("Discount must be between 0 and 25%");
    	                                  errorAlert.showAndWait();
    	                              }
    	                          } catch (NumberFormatException e) {
    	                              // Handle invalid input (non-numeric values)
    	                              Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    	                              errorAlert.setTitle("Invalid Input");
    	                              errorAlert.setHeaderText("Please enter a valid number for the discount.");
    	                              errorAlert.showAndWait();
    	                          }
    	                      } 
    	                  });
    	            } 
    	        });
    	       
    	}  

    }
     void displayBill(double x, DisplayBooking selectedItem)
    {
    	 
    	 Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirmation Dialog");
    alert.setHeaderText("Bill Information");
    String formattedRate = String.format("%.2f", (selectedItem.getOneDay() - (x/100)*selectedItem.getOneDay()));
    String formattedTotal = String.format("%.2f",  (selectedItem.getTotal() - (x/100)*selectedItem.getTotal()));
    // Create booking information string
    String bookingInfo = "Hotel Reservation System\n" +
                         "\nBooking ID: " + selectedItem.getBookingId() + 
                         "\nGuest Name: " + selectedItem.getCustomerName() + 
                         "\nNo of rooms booked: " + selectedItem.getNoOfRooms() +
                         "\nType of rooms: " + selectedItem.getRoomType() + 
                         "\nRate per night: " + formattedRate +
                         "\nTotal: " + formattedTotal+
                         "\n Discount: " + x;
    
    // Set content text for the booking info and the confirmation message
    alert.setContentText(bookingInfo + "\nDone");

    // Show the alert and wait for user action
    alert.showAndWait();
    	 
    	
    }

    @Override
    public void settingMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
