package applicationControllers;

import java.util.ArrayList;
import java.util.Iterator;

import application.Main;
import applicationDatabase.JDBCDA;
import applicationModels.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class availableRoomPageController implements AbstractController{

    @FXML
    private Button continueBtn;

    @FXML
    private TableView<Room> room;

    @FXML
    private TableColumn<Room, Double> roomPrice;

    @FXML
    private TableColumn<Room, String> roomType;

    @FXML
    private TableColumn<Room, Integer> roomnumber;
    private Main mainApp;
    private ObservableList<Room> observableList; 
    @FXML
    void continueBtnClicked(ActionEvent event) {
    	mainApp.AdminFirstPage();

    }

	public availableRoomPageController()
    {
    	JDBCDA database = new JDBCDA();
    	
    		ArrayList<Room> rooms = new ArrayList<>();
    		final ArrayList<Room> bookedRooms = new ArrayList<>(database.getBookedRoom());
    		rooms = database.getHotelRooms();
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
    		 observableList = FXCollections.observableArrayList(rooms);
    		initialize();
    }
    public void initialize( )
    { if (roomnumber != null) {

   	 	roomnumber.setCellValueFactory(new PropertyValueFactory<Room, Integer>("roomId"));
		roomType.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
		roomPrice.setCellValueFactory(new PropertyValueFactory<Room, Double>("rate"));
		room.setItems(observableList);
    } else {
        //System.out.println("roomnumber column is null, initialization failed.");
    }
    }

	@Override
	public void settingMainApp(Main mainApp) {
		this.mainApp = mainApp;
		// TODO Auto-generated method stub
		
	}
	
   

}
