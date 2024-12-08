package applicationModels;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DisplayBooking {
    private int bookingId;
    private String customerName;
    private int noOfRooms ;
    private String roomType = "";
    private int noOfDays1;
    private double total;

    public DisplayBooking(int bookingId, String customerName, ArrayList<Room> rooms, Date checkIn, Date CheckOut) {
        this.bookingId= bookingId;
        this.customerName = customerName;
        this.noOfRooms =0;
        this.total = 0;
        
        // Combine room types
        for (Room room : rooms) {
            roomType += room.getRoomType() + " ";
            noOfRooms += 1;
            total += room.getRate();
            //System.out.println(room.getRoomType() + "\n");
        }
        
        // Convert java.sql.Date to LocalDate
        LocalDate checkInDate = checkIn.toLocalDate();
        LocalDate checkOutDate = CheckOut.toLocalDate();
        
        // Calculate the number of days
        this.noOfDays1 = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        this.total = total *this.noOfDays1;
    }

    public double getOneDay()
    {
    	return this.total/this.noOfDays1;
    }
    public double getTotal()
   
    {
    	return this.total;
    }
	public int getBookingId() {
		return bookingId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public int getNoOfRooms() {
		return noOfRooms;
	}

	public String getRoomType() {
		return roomType;
	}

	public int getNoOfDays1() {
		return noOfDays1;
	}
}
