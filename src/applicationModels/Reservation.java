package applicationModels;

import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;

public class Reservation {
	private static int lastId = 0;
	private final int bookId;
	private LocalDate date;
	private LocalDate checkIn;
	private LocalDate checkOut;
	
	public Reservation()
	{
		this.bookId = ++lastId;
		this.date = LocalDate.now();
		this.checkIn = null;
		this.checkOut = null;
	}
	public Reservation(LocalDate checkIn, LocalDate checkOut)
	{
		this.bookId = ++lastId;
		this.date = LocalDate.now();
		this.checkIn = checkIn;
		this.checkOut = checkOut;
	}
	public int getBookId() {
		return bookId;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalDate getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}
	public LocalDate getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

}
