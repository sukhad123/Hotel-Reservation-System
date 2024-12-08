package application;

import java.util.ArrayList;

import applicationControllers.*;
import applicationDatabase.JDBCDA;
import applicationModels.Booking;
import applicationModels.DeluxeRoom;
import applicationModels.DoubleRoom;
import applicationModels.HotelRoom;
import applicationModels.PentHouse;
import applicationModels.Reservation;
import applicationModels.Room;
import applicationModels.SingleRoom;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
 


public class Main extends Application {
	private BorderPane rootLayout;
	//stage
	private Stage ps;
	private Booking temp = new Booking();
	private int tmpGuests;
	
	private ObservableList<Room> availableRooms;
	
	
	//this variable contains all the room of the hotel
	
	//hotelRoom
	private  HotelRoom x;
	//private JDBCDA database;
	
	public Booking getBooking()
	{
		return temp;
	}
	public void start(Stage arg0) throws Exception {
		this.ps = arg0;
		 
		x = new HotelRoom();
		//billService();
		intializer();
		
		
		
	}
	
	
	//billservice
	public void billService()
	{
		BillServiceController rc = null;
		pageRedirector("/applicationViews/BillServiceadmin.fxml",rc);
	}
	//admin room booking page
	public void adminRoomBooking(){
		adminRoomBookingController rc = new adminRoomBookingController();
		pageRedirector("/applicationViews/adminRoomBooking.fxml",rc);
	}
	//page redirector function
	public void pageRedirector(String path, AbstractController controller)
	{try
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource(path));
		rootLayout = loader.load();
		
		Scene scene = new Scene(rootLayout);
		ps.setScene(scene);
		 
		  controller  = loader.getController();
		controller.settingMainApp(this);
		ps.show();
		
		
	}catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	//initializer
	public void intializer()
	{
		HomepageController rc = null;
		pageRedirector("/applicationViews/HomePage.fxml",rc);
	}
	//available room page
	public void AvailableRoomPage()
	{
		availableRoomPageController rc = new availableRoomPageController();
		pageRedirector("/applicationViews/availableRoomPage.fxml",rc);
	}

	//guest details form
	public void GuestDetailsForm()
	{
		GuestDetailsFormController rc = null;
		pageRedirector("/applicationViews/GuestDetailsForm.fxml",rc);
	}

	//admin page1
	public void AdminFirstPage()
	{
		adminPage1Controller rc = null;
		pageRedirector("/applicationViews/adminPage1.fxml",rc);
	}
	
	//booking page
	public void viewBookingPage()
	{
		viewBookingController rc = null;
		pageRedirector("/applicationViews/currentBooking.fxml",rc);
	}
	 
	public void adminRoomBookingController1(int guests, Reservation reservation)
	{
		
		temp.setReservation(reservation);
		temp.getGuestClass().setGuestNo(guests);
		System.out.println(temp.getGuestClass().getGuests());
		availableRooms = FXCollections.observableArrayList((x.recommendRooms(guests)));
		RoomSelectorDialogController rc = null;
		pageRedirector("/applicationViews/adminRoomSelction.fxml",rc);
	}
	//pass the data,
	public void RoomSelectionPage(int guests, Reservation reservation)
	{
		temp.setReservation(reservation);
		temp.getGuestClass().setGuestNo(guests);
		System.out.println(temp.getGuestClass().getGuests());
		availableRooms = FXCollections.observableArrayList((x.recommendRooms(guests)));
		RoomSelectorDialogController rc = null;
		pageRedirector("/applicationViews/RoomSelectorDialog.fxml",rc);
	}
	public void RoomSelectionPage()
	{
		availableRooms = FXCollections.observableArrayList((x.recommendRooms(tmpGuests)));
		RoomSelectorDialogController rc = null;
		pageRedirector("/applicationViews/RoomSelectorDialog.fxml",rc);
	}
	 public Booking gettmpBooking()
	 {
		return temp;
		 
	 }

	public int getGuests()
	{
		return this.tmpGuests;
	}
	//Guest Welcome page
	public void GuestWelcomePage()
	{ 
		GuestWelcomePageController rc = null;
		pageRedirector("/applicationViews/GuestWelcomePage.fxml",rc);
	}
	//Guest Details
	public void GuestDetails()
	{
		GuestDetailsController rc = null;
		pageRedirector("/applicationViews/GuestDetails.fxml",rc);
	}
	public void adminLoginPage()
	{
		adminLoginController rc = null;
		pageRedirector("/applicationViews/adminLogin.fxml",rc);
	}
	
	 
	public static void main(String[] args) {
		launch(args);
	}
	
	public ObservableList<Room> getAvailableRooms()
	{
		return availableRooms;
	}

	
 
}
