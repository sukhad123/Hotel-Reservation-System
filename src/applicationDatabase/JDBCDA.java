package applicationDatabase;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.cj.jdbc.ha.ReplicationMySQLConnection;

import applicationModels.DeluxeRoom;
import applicationModels.DisplayBooking;
import applicationModels.DoubleRoom;
import applicationModels.GuestClass;
import applicationModels.PentHouse;
import applicationModels.Reservation;
import applicationModels.Room;
import applicationModels.SingleRoom;
import javafx.collections.FXCollections;
public class JDBCDA {
	private static final String DB_URL="jdbc:mysql://localhost:3306/new_schema?allowPublicKeyRetrieval=true&useSSL=false";
	private static final  String DB_USERNAME = "sukhad";
	private static final String DB_PASSWORD = "asdf@123";
	
	private static final String DELETEROOM  = "Delete from admin.bookedroom where bookedId =?";
	private static final String DELETEBOOKING = "Delete from admin.booking where bookingId = ?";
	private static final String SELECT_QRY_ADMIN ="SELECT * FROM admin.admin WHERE userName = ? AND password = ?";
	private static final String INSERT_BOOKEDROOM =  "Insert into admin.bookedroom(roomId,name, rate,bookedId ) values(?,?,?,?)";
	private static final String SELECT_QRY_FOR_HOTELROOM = "SELECT * FROM admin.HotelRoom";
	private static final String SELECT_QRY_BOOKEDROOM = "SELECT * FROM admin.bookedroom";
	private static final String INSERT_INTO_BOOKING_TABLE= "INSERT INTO ADMIN.BOOKING(checkIn, checkOut, GuestFullName, GuestAddress,GuestPhone,GuestNumbers, Total, TotalWithTaxes) values(?,?,?,?,?,?,?,?)";
	private static final String SELECT_QRY_BOOKING = "Select * from admin.booking";
	
	private int bookingId;
	private ArrayList<Room> hotelRooms = new ArrayList<>();
	
	
	
	//check admin credentails
	public boolean checkAdmin(String username, String Password)
	{
		boolean result = false;
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(SELECT_QRY_ADMIN))	
				{
					 ps.setString(1, username);	 
					 ps.setString(2, Password);
					 ResultSet rs =ps.executeQuery();
					 if (!rs.isBeforeFirst()) {
						   //
						} else {
						    // There are rows in the result set
						    while (rs.next()) {
						       result = true;
						    }
						}
					  
				}
				
				catch(SQLException ex)
				{
					System.out.println(ex);
				}
		return result;
		
	}
	
	
	//delete booking 
	public void DeleteBooking(int bookingId)
	{
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(DELETEROOM);
				PreparedStatement ps1 = conn.prepareStatement(DELETEBOOKING))
				
				{
			
					ps.setInt(1, bookingId);
					ps.execute();
					ps1.setInt(1, bookingId);
					ps1.execute();
					 
				}
				
				catch(SQLException ex)
				{
					System.out.println(ex);
				}
	}
	
	//retrieve booking
	public ArrayList<DisplayBooking> getBooking()
	{

		ArrayList<DisplayBooking> bookings = new ArrayList<>();
		
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(SELECT_QRY_BOOKING);
				PreparedStatement ps1 = conn.prepareStatement("Select * from admin.bookedroom where bookedId =?"))
				
				{
					 	ResultSet rs = ps.executeQuery();
					 	while(rs.next())
					 	{
					 		ArrayList<Room> rooms = new ArrayList<>();
					 		ps1.setInt(1, rs.getInt("bookingId"));
					 		ResultSet rs1 = ps1.executeQuery();
					 		while(rs1.next())
					 		{
					 			if(rs1.getString("name").equals("Single Room"))
								 {
									 rooms.add(new SingleRoom(rs1.getInt("roomId")));
								 }
								 else if(rs1.getString("name").equals("Double Room"))
								 {
									 rooms.add(new DoubleRoom(rs1.getInt("roomId")));
								 }
								 else if(rs1.getString("name").equals("Deluxe Room"))
								 {
									 rooms.add(new DeluxeRoom(rs1.getInt("roomId")));
								 }
								 else
								 {
									 rooms.add(new PentHouse(rs1.getInt("roomId")));
								 }
					 		}
					 		 
					 		
					 		bookings.add(new DisplayBooking(rs.getInt("bookingId"), rs.getString("GuestFullName"),rooms,rs.getDate("checkIn"), rs.getDate("checkOut")));
					 	}
					 
				}
				
				catch(SQLException ex)
				{
					System.out.println(ex);
				}
		return bookings;
		
	}
	//add booking 
	public void addBooking(GuestClass guest, Reservation reservation,int GuestNumber  , double total)
	{
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement(INSERT_INTO_BOOKING_TABLE, Statement.RETURN_GENERATED_KEYS))
				
				{
					 	ps.setDate(1, Date.valueOf(reservation.getCheckIn()));
					 	
						ps.setDate(2,Date.valueOf(reservation.getCheckOut()));
						ps.setString(3,guest.getFullName());
						ps.setString(4, guest.getAddress());
						ps.setInt(5, guest.getPhone());
						ps.setInt(6, GuestNumber);
						ps.setDouble(7,total);
						ps.setDouble(8, total*1.13);
						ps.executeUpdate();
						ResultSet rs = ps.getGeneratedKeys();
						if (rs.next()) {
					        bookingId = rs.getInt(1);  // Retrieve the generated bookingId
					    }

					    // Ensure the bookingId is valid
					    if (bookingId == 0) {
					        throw new SQLException("Failed to retrieve generated bookingId.");
					    }
					 
				}
				
				catch(SQLException ex)
				{
					System.out.println(ex);
				}
	}
	public ArrayList<Room> getHotelRooms()
	{
		return this.hotelRooms;
		
	}
	public void addBookedRoom(ArrayList<Room> rooms)
	{
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				//PreparedStatement ps = conn.prepareStatement(INSERT_BOOKEDROOM);
				PreparedStatement ps = conn.prepareStatement(INSERT_BOOKEDROOM))
				{
				 
					for(Room room: rooms)
					{
						ps.setInt(1, room.getId());
						ps.setString(2, room.getRoomType());
						ps.setDouble(3, room.getRate());
						 ps.setInt(4,bookingId);
						ps.execute();
					}
		 
				}
				
				catch(SQLException ex)
				{
					System.out.println(ex);
				}
		
	}
	
	//get Booked Room list
	public ArrayList<Room> getBookedRoom()
	{
		ArrayList<Room> rooms = new ArrayList<>();

		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		PreparedStatement ps = conn.prepareStatement(SELECT_QRY_BOOKEDROOM))
		{
			 ResultSet rs = ps.executeQuery();
			 while(rs.next())
			 {
				 if(rs.getString("name").equals("Single Room"))
				 {
					 rooms.add(new SingleRoom(rs.getInt("roomId")));
				 }
				 else if(rs.getString("name").equals("Double Room"))
				 {
					 rooms.add(new DoubleRoom(rs.getInt("roomId")));
				 }
				 else if(rs.getString("name").equals("Deluxe Room"))
				 {
					 rooms.add(new DeluxeRoom(rs.getInt("roomId")));
				 }
				 else
				 {
					 rooms.add(new PentHouse(rs.getInt("roomId")));
				 }
				 
			 }
 
		}
		
		catch(SQLException ex)
		{
			System.out.println(ex);
		}
		return rooms;
	}
	public JDBCDA() 
	{
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		PreparedStatement ps = conn.prepareStatement( SELECT_QRY_FOR_HOTELROOM))
		{
			 ResultSet rs = ps.executeQuery();
			 while(rs.next())
			 {
				 if(rs.getString("name").equals("Single Room"))
				 {
					 hotelRooms.add(new SingleRoom(rs.getInt("roomId")));
				 }
				 else if(rs.getString("name").equals("Double Room"))
				 {
					 hotelRooms.add(new DoubleRoom(rs.getInt("roomId")));
				 }
				 else if(rs.getString("name").equals("Deluxe Room"))
				 {
					 hotelRooms.add(new DeluxeRoom(rs.getInt("roomId")));
				 }
				 else
				 {
					 hotelRooms.add(new PentHouse(rs.getInt("roomId")));
				 }
				 
			 }
			/*
			 ps.setString(1, "adnan");
			    ResultSet rs = ps.executeQuery();

			    if (rs.next()) { // Move to the first row, if exists
			        System.out.println(rs.getString("userName"));
			    } else {
			        System.out.println("No data found.");
			    }
	*/
			
		}
		
		catch(SQLException ex)
		{
			System.out.println(ex);
		}
	}
	
	
	
	
	//clear previous data
	public void clear()
	{
		try(Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
				PreparedStatement ps = conn.prepareStatement("Truncate table new_schema.part");
				PreparedStatement ps1 = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 0");
				PreparedStatement ps2 = conn.prepareStatement("Truncate table new_schema.product");
				PreparedStatement ps3 = conn.prepareStatement("SET FOREIGN_KEY_CHECKS = 1");
				)
				{
					ps.execute();
					ps1.execute();
					ps2.execute();
					ps3.execute();	
				}
				
				catch(SQLException ex)
				{
					System.out.println(ex);
				}
		
	}		
	}