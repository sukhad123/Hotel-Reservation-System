package applicationModels;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GuestClass {
	private SimpleIntegerProperty guestId;
	private SimpleStringProperty fullName;
	private SimpleStringProperty address;
	private SimpleIntegerProperty phone;
	private SimpleStringProperty email;
	private SimpleIntegerProperty noOfGuests;
	
	
	public GuestClass(String fullname, String address, int phone, String email, int guest)
	{
		this.address = new SimpleStringProperty(address);
		this.fullName = new SimpleStringProperty(fullname);
		this.phone = new SimpleIntegerProperty(phone);
		this.email = new SimpleStringProperty(email);
		//setting guestId null for now
		this.guestId = new SimpleIntegerProperty();
		this.noOfGuests = new SimpleIntegerProperty( guest);
	}
	public GuestClass()
	{this.address = new SimpleStringProperty( );
	this.fullName = new SimpleStringProperty( );
	this.phone = new SimpleIntegerProperty( );
	this.email = new SimpleStringProperty( );
	//setting guestId null for now
	this.guestId = new SimpleIntegerProperty();
		
	}
	
	public int getGuestId() {
		return guestId.getValue();
	}
	public void setGuestId(int guestId) {
		this.guestId= new SimpleIntegerProperty(guestId);
	}
	 
	public String getFullName() {
		return fullName.getValue();
	}
	public void setFullName(String name) {
		this.fullName = new SimpleStringProperty(name);
	}
	public String getAddress() {
		return address.getValue();
	}
	public void setAddress(String address) {
		this.address = new SimpleStringProperty(address);
	}
	public int getPhone() {
		return phone.getValue();
	}
	public void setPhone(int phone) {
		this.phone = new SimpleIntegerProperty(phone);
	}
	public String getEmail() {
		return email.getValue();
	}
	public void setEmail(String email) {
		this.email = new SimpleStringProperty(email);
	}
	public void setGuestNo(int guests)
	{
		this.noOfGuests = new SimpleIntegerProperty( guests);
	}
	
	public int getGuests()
	{
		return this.noOfGuests.getValue();
	}
	
	

}
