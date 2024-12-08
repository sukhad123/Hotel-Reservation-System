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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class viewBookingController implements AbstractController {

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
    public viewBookingController() {
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
            alert.setHeaderText("Are you sure you want to delete the booking?");
            alert.setContentText("Click OK to confirm or Cancel to abort.");

            // Show the alert and wait for the user's response
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    database.DeleteBooking(selectedItem.getBookingId());
                    initialize();
                } 
            });
    	}  

    }

    @Override
    public void settingMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}
