package applicationControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import application.Main;
import applicationDatabase.JDBCDA;
import applicationModels.DoubleRoom;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class RoomSelectorDialogController implements AbstractController  {
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
    	mainApp.GuestDetails();
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
    			System.out.println(guesttmp);
    		}
    	}
    	if( guesttmp < 1)
    	{
    		Alert info = new Alert(AlertType.CONFIRMATION);
    	   	info.setTitle("Confirm Room");
    	   	for(Room i: selectedRooms)
    	   	{
    	   		info.setContentText(info.getContentText()+i.getRoomType() + " Room Id: " + i.getId() + "\n");
    	   	}
    	   	info.setHeaderText("Selected Rooms");
    	   	info.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                	 ArrayList<Room> arrayList = new ArrayList<>(selectedRooms);
                	mainApp.gettmpBooking().setRooms(arrayList);
                    mainApp.GuestDetailsForm();
                } else {
                    // Handle other responses like Cancel
                    System.out.println("Action canceled.");
                }
            });
    	}
    	else
    	{
    		Alert info = new Alert(AlertType.ERROR);
    	   	info.setTitle("Select More Rooms");
    	   	info.setHeaderText("Voilating selected rooms guest capacity");
    	   	info.showAndWait();
    	   	 
            
    	
    }}
    //check as the room is selected properly acoording to the no of Guests
    public Boolean RoomSelectionChecker(int guests, ArrayList<Room> room)
    {
    	if(guests == 1|| guests == 2)
		{
    		
		}
    	
    	return true;
    	
    }
    
    //default constructor
    public RoomSelectorDialogController()
    {
    	selectedRooms =  FXCollections.observableArrayList();
    }
	public RoomSelectorDialogController(ObservableList<Room> rooms, Main mainApp)
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
