package applicationControllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import application.Main;
import applicationDatabase.JDBCDA;
import applicationModels.DoubleRoom;
import applicationModels.GuestClass;
import applicationModels.Reservation;
import applicationModels.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class adminRoomBookingController1 implements AbstractController  {
	private Main mainApp;
    @FXML
    private TableView<Room> availableRoomTable;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button continueBtn;

    @FXML
    private Label errorMsg;

    @FXML
    private TableColumn<Room, Integer> roomId1;

    @FXML
    private TableColumn<Room, Integer> roomId2;

    @FXML
    private TableColumn<Room, Double> roomRate1;

    @FXML
    private TableColumn<Room, Double> roomRate2;

    @FXML
    private TableColumn<Room, String> roomType1;

    @FXML
    private TableColumn<Room, String> roomType2;

    @FXML
    private TableView<Room> selectedRoomTable;
   @FXML 
   private Button selectedRoomBtn;
   @FXML
   private Button removeRoom;
   private ObservableList<Room> availableRooms;
   private ObservableList<Room> selectedRooms;
   

   @FXML
   private Button recommendationBtn;


   @FXML
   void getRecommendation(ActionEvent event) {
	   Alert info = new Alert(AlertType.INFORMATION);
	   	info.setTitle("System Recommendation Rooms");
	   	info.setContentText(recommendRooms(mainApp.gettmpBooking().getGuestClass().getGuests()));
	   	info.setContentText(info.getContentText() + "\n Consider PentHouse and Deluxe for Luxury!\n");
	   	info.showAndWait();

   }

   public String recommendRooms(int numGuests) {
	    String tmp = "";
	    if (numGuests == 1 || numGuests == 2) {
	        System.out.println("Recommended Room(s):");
	        tmp = "One Single Room\n";
	    }
	    // For 3 or 4 adults
	    else if (numGuests > 2 && numGuests <= 4) {
	        tmp = "One Double Room\n";
	        
	    }
	    // For more than 4 adults
	    else {
	        int doubleRoomCount = numGuests / 4;
	        for (int i = 1; i <= doubleRoomCount; i++) {
	            tmp = i + " Double Room\n";
	        }
	        int remainingGuests = numGuests % 2;
	        if (remainingGuests > 0) {
	            tmp += "One Single Room\n";
	        }
	    }
	    return tmp;
	}

    @FXML
    void onSelectRoom(ActionEvent event) {
    	
    	
    	
    	if(availableRoomTable.getSelectionModel().getSelectedItem() != null)
    	{
    		boolean alreadyThere = true;
    		//check the room already there
    		for(Room r: selectedRooms)
    		{
    			Room tmp =  availableRoomTable.getSelectionModel().getSelectedItem();
    			if(r.getId() ==tmp.getId())
    			{
    				alreadyThere = false;
    			}
    		}
    		if(alreadyThere)
    		{
    			selectedRooms.add(availableRoomTable.getSelectionModel().getSelectedItem());
    		}
    	}

    }

    @FXML
    void removeSelectedRoom(ActionEvent event) {
    	if(selectedRoomTable.getSelectionModel().getSelectedItem() != null)
    	{
    	selectedRooms.remove(selectedRoomTable.getSelectionModel().getSelectedItem());
    	}

    }

    @FXML
    void cancelOnAction(ActionEvent event) {
    	mainApp.adminRoomBooking();
    }

    @FXML
    void continueAction(ActionEvent event) {
    	//ensuring that it following the guideline of the room capacity
    	int guesttmp = mainApp.gettmpBooking().getGuestClass().getGuests();
    	 
    	for(Room i: selectedRooms)
    	{
    		if(i.getClass().equals(DoubleRoom.class))
    		{
    			guesttmp -= 4;
    		}
    		else
    		{
    			guesttmp -= 2;
    			//System.out.println(guesttmp);
    		}
    	}
    	if( guesttmp < 1)
    	{
    		
    		
    		 Alert info1 = new Alert(Alert.AlertType.CONFIRMATION);
    	        info1.setTitle("Reservation Details");

    	        // Create UI components for date and number of days
    	        DatePicker datePicker = new DatePicker();
    	        datePicker.setPromptText("Select a date");

    	        TextField daysField = new TextField();
    	        daysField.setPromptText("Enter number of days");

    	        // Create UI components for guest details
    	        TextField fullNameField = new TextField();
    	        fullNameField.setPromptText("Full Name");

    	        TextField contactField = new TextField();
    	        contactField.setPromptText("Contact Number");

    	        TextField addressField = new TextField();
    	        addressField.setPromptText("Address");

    	        // Add components to a VBox
    	        VBox content = new VBox(10); // 10px spacing
    	        content.getChildren().addAll(
    	                new Label("Guest Details:"),
    	                fullNameField,
    	                contactField,
    	                addressField,
    	                new Label("Reservation Details:"),
    	                datePicker,
    	                daysField
    	        );

    	        // Set the VBox as the content of the alert
    	        info1.getDialogPane().setContent(content);

    	        // Show the alert and wait for user input
    	        info1.showAndWait().ifPresent(response -> {
    	            if (response == ButtonType.OK) {
    	                String fullName = fullNameField.getText();
    	                String contact = contactField.getText();
    	                String address = addressField.getText();
    	                String selectedDate = datePicker.getValue() != null ? datePicker.getValue().toString() : null;
    	                String numberOfDays = daysField.getText();

    	                // Validate inputs
    	                if (fullName.isEmpty() || contact.isEmpty() || address.isEmpty() || selectedDate == null || numberOfDays.isEmpty()) {
    	                	Alert info = new Alert(AlertType.ERROR);
    	        	   	   	info.setTitle("Invalid Input");
    	        	   	   	info.setHeaderText("Enter all Details");
    	        	   	   	info.showAndWait();
    	                } else {
    	                    try {
    	                        int daysToAdd = Integer.parseInt(numberOfDays);
    	                        LocalDate checkoutDate = LocalDate.parse(selectedDate).plusDays(daysToAdd);
    	                       
    	                        //String fullname, String address, int phone, String email, int guest
    	                        Reservation tmp = new Reservation(LocalDate.parse(selectedDate), LocalDate.parse(selectedDate).plusDays(Integer.parseInt(numberOfDays)));
    	        	            mainApp.getBooking().setReservation(tmp);
    	        	            mainApp.getBooking().setGuestClass(new GuestClass(fullName,address,Integer.parseInt(contact),null,mainApp.getBooking().getGuestClass().getGuests()));
    	        	            
    	        	            //set the price 
    	        	            Alert info111 = new Alert(Alert.AlertType.CONFIRMATION);
    	        	            info111.setTitle("Set the price for night");

    	        	            // Create a VBox for all UI components
    	        	            VBox content1 = new VBox(10); // 10px spacing between elements

    	        	            for (Room room : selectedRooms) {
    	        	                // Create a TextField for each room to enter the night charge
    	        	                TextField roomField = new TextField();
    	        	                roomField.setPromptText("Enter Night Charge for " + room.getRoomType()); // Assuming 'getRoomType' provides the room type

    	        	                // Add the room details and the input field to the VBox
    	        	                content1.getChildren().addAll(
    	        	                    new Label("Room: " + room.getRoomType() + " Room Number: " + room.getId()),  // Assuming 'getRoomType' and 'getId' exist in Room class
    	        	                    roomField
    	        	                );
    	        	            }

    	        	            // Set the VBox as the content of the alert
    	        	            info111.getDialogPane().setContent(content1);

    	        	            // Show the alert and wait for user input
    	        	            
    	            	        info111.showAndWait().ifPresent(response1 -> {
    	        	                if (response1 == ButtonType.OK) {
    	        	                    // Handle the input from the user (store or process the night charges)
    	        	                    for (int i = 0; i < selectedRooms.size(); i++) {
    	        	                        Room room = selectedRooms.get(i);
    	        	                        TextField input11 = (TextField) content1.getChildren().get(i * 2 + 1); // Getting the corresponding TextField for the room
    	        	                        String nightChargeInput = input11.getText().trim();

    	        	                        try {
    	        	                        	if (isValidInteger(nightChargeInput)) {
    	        	                        	    int nightCharge = Integer.parseInt(nightChargeInput);
    	        	                        	    room.setRate(nightCharge);
    	        	                    
    	        	                        	    //System.out.println(mainApp.getBooking().getGuestClass().getGuests());
    	        	                        	    if(i == selectedRooms.size() -1)
    	        	                        	    {
    	        	                        	    	mainApp.getBooking().setRooms(new ArrayList<>(selectedRooms));
    	    	        	                        JDBCDA database = new JDBCDA();
    	    	        	                          database.addBooking(mainApp.getBooking().getGuestClass(), mainApp.getBooking().getReservation(), mainApp.getBooking().getGuestClass().getGuests(), mainApp.getBooking().getTotal());
    	    	        	                          database.addBookedRoom(mainApp.getBooking().getRooms());
    	    	        	                         Alert info = new Alert(AlertType.CONFIRMATION);
      	        	                        	    info.setTitle("ROOM BOOKED");
      	        	                        	    info.setHeaderText("Booking Done");
      	        	                        	    info.showAndWait();
      	        	                        	    mainApp.AdminFirstPage();
    	        	                        	    }
    	        	                        	    // Continue with your logic to process the valid nightCharge
    	        	                        	} else {
    	        	                        	    Alert info = new Alert(AlertType.ERROR);
    	        	                        	    info.setTitle("Invalid Input");
    	        	                        	    info.setHeaderText("Please enter a valid number for night charge.");
    	        	                        	    info.showAndWait();
    	        	                        	}

    	        	                          
    	        	                          
    	        	                          
    	        	                           // System.out.println("Room " + room.getRoomType() + " (ID: " + room.getId() + ") Night Charge: " + nightCharge);
    	        	                        } catch (NumberFormatException e) {
    	        	                            // Handle invalid input
    	        	                        	System.out.println(e);
    	        	                            System.out.println("Invalid price input for " + room.getRoomType() + " (ID: " + room.getId() + "). Please enter a valid number.");
    	        	                        }
    	        	                    }
    	        	                }
    	        	            });


    	        	            
    	                    } catch (NumberFormatException e) {
    	                    	Alert info = new Alert(AlertType.ERROR);
    	            	   	   	info.setTitle("Invalid Input");
    	            	   	   	info.setHeaderText("Number of days must be valid integer");
    	            	   	   	info.showAndWait();
    	                    }
    	                }
    	            }
    	        });
    	}
    	        else
    	 	       { Alert info = new Alert(AlertType.ERROR);
    	   	   	info.setTitle("Invalid Input");
    	   	   	info.setHeaderText("Voilating Hotel Occupancy Policy");
    	   	   	info.showAndWait();
    	        
 	       
 	
    }}
    public boolean isValidInteger(String input) {
        return input.matches("\\d+"); // Checks if the input is a valid integer (only digits)
    }
    
    void saveReservation()
    {
    	
    }
    //check as the room is selected properly acoording to the no of Guests
    public Boolean RoomSelectionChecker(int guests, ArrayList<Room> room)
    {
    	if(guests == 1|| guests == 2)
		{
    		
		}
    	
    	return true;
    	
    }
    
    //default constructor
    public adminRoomBookingController1()
    {
    	selectedRooms =  FXCollections.observableArrayList();
    }
	public adminRoomBookingController1(ObservableList<Room> rooms, Main mainApp)
    {
    	settingMainApp(mainApp);
    	this.availableRooms = rooms;
    	
    	addTable();
    	

    }
    
    public void addTable()
    {
    	//System.out.println(availableRooms);
    	JDBCDA x = new JDBCDA();
		roomId1.setCellValueFactory(new PropertyValueFactory<Room, Integer>("roomId"));
    	roomType1.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
    	roomRate1.setCellValueFactory(new PropertyValueFactory<Room, Double>("rate"));
    	ArrayList<Room> rooms = new ArrayList<>();
		final ArrayList<Room> bookedRooms = new ArrayList<>(x.getBookedRoom());
		rooms = x.getHotelRooms();
		Iterator<Room> roomIterator = rooms.iterator();
		while (roomIterator.hasNext()) {
		    Room room1 = roomIterator.next();
		    for (Room room2 : bookedRooms) {
		        if (room1.getId() == room2.getId()) {
		            roomIterator.remove();  
		            break;  
		        }
		    }
		}

		//rooms.removeIf(room -> bookedRooms.contains(room));
		availableRooms = FXCollections.observableArrayList(rooms);
    	//availableRooms = FXCollections.observableArrayList(observableList);
    	 availableRoomTable.setItems(availableRooms);
    	 
    	 //Selected Room Table
    	 roomId2.setCellValueFactory(new PropertyValueFactory<Room, Integer>("roomId"));
     	roomType2.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
     	roomRate2.setCellValueFactory(new PropertyValueFactory<Room, Double>("rate"));
     	 selectedRoomTable.setItems(selectedRooms);
    }
	@Override
	public void settingMainApp(Main mainApp) {
		this.mainApp = mainApp;
		// TODO Auto-generated method stub
		addTable();
		
	}



}
