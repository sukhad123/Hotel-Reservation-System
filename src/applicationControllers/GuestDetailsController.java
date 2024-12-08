package applicationControllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import application.Main;
import applicationModels.Reservation;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GuestDetailsController implements Initializable,AbstractController{

    @FXML
    private Button cancelBtn;
    @FXML
    private DatePicker checkIn;
    @FXML
    private DatePicker checkout;
    @FXML
    private Button continueBtn;
    @FXML
    private Label noOfGuestTxt;
    @FXML
    private ComboBox<Integer> noOfGuests;
    private ObservableList<Integer> dbTypeList = FXCollections.observableArrayList(1, 2, 3,4,5,6,7,8);
    private Main main;
    
    //binding
    private SimpleIntegerProperty guests = new SimpleIntegerProperty(0);
    
    //temporary Datas
    private Integer totalGuests;
    private LocalDate checkInn;
    private LocalDate checkOutt;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {if (noOfGuestTxt != null) {
        noOfGuestTxt.setText("0");
    } 
    	  
    	noOfGuests.setItems(dbTypeList);
    	noOfGuestTxt.textProperty().bind(
                Bindings.createStringBinding(
                    () -> "" + (noOfGuests.getValue() != null ? noOfGuests.getValue() : 0),
                    noOfGuests.valueProperty()
                )
            );
    	noOfGuests.setValue(0);

    	 
    	
    }
    @FXML
    void cancelOnAction(ActionEvent event) {
    	main.GuestWelcomePage();

    }

  

    @FXML
    void continueAction(ActionEvent event) {
    	
    	
    	Alert error = new Alert(AlertType.ERROR);
    	error.setTitle("Invalid Inputs");
		checkInn = checkIn.getValue();  
	    checkOutt = checkout.getValue();
	    totalGuests = noOfGuests.getValue();
	    int a = 0;
    	if( totalGuests == 0)
    	{  
    		error.setContentText(error.getContentText() + "Select the Number of Guests");
    		a++;
    	}
    	else
    	{
    		if(checkInn == null)
    		{
    			error.setContentText(error.getContentText() + "\nSelect the CheckIn Date");
    			a++;
    		}
    		else 
    		{
    			if(checkOutt == null)
        		{
        			error.setContentText(error.getContentText() + "\nSelect the CheckOut Date");
        			a++;
        		}
    			else
    			{
 
    			    if (checkOutt.isBefore(checkInn)) {
    			    	a++;
    			    	error.setContentText(error.getContentText() + "\nInvalid Dates; Checkout Date cannot be before checkIn Date");
    			    	
    	            } 
    			    else
    			    {
    			    	 continueGetAvailableRooms();
    			    }
    			}
    		}
    		
    	}
    	
    	if(a > 0)
		{
			error.showAndWait();
		}
    	
    	
    }
    public void continueGetAvailableRooms()
    {
    	Reservation tmp = new Reservation(checkInn, checkOutt);
    	main.RoomSelectionPage(totalGuests, tmp);
    	
    }
    
	@Override
	public void settingMainApp(Main mainApp) {
		this.main = mainApp;
		// TODO Auto-generated method stub
		
	}

}
