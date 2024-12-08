package applicationModels;

import java.util.ArrayList;

public class Booking {
	//private Integer bookingId;
	private Reservation reservation;
	private GuestClass guestClass;
	private ArrayList<Room> rooms;
	private double total;
	
	public Booking()
	{
		//this.bookingId  = null;
		this.reservation = new Reservation();
		rooms  =new ArrayList<>();
		guestClass = new GuestClass();
		total = 0;
	}
	
	public Reservation getReservation() {
		return reservation;
	}
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	public GuestClass getGuestClass() {
		return guestClass;
	}
	public void setGuestClass(GuestClass guestClass) {
		this.guestClass = guestClass;
	}
	public void setRooms(ArrayList<Room> rooms)
	{
		this.rooms = rooms;
	}
	
	public ArrayList<Room> getRooms()
	{
		return this.rooms;
	}
	//calculate and setting total
	public void setTotal()
	{
		for(Room i: rooms)
		{
			total += i.getRate();
		}
	}
	public double getTotal()
	{
		setTotal();
		return this.total;
	}
	public double getTotalWithTaxes()
	{
		return this.total *1.13;
	}
}
