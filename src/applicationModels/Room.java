package applicationModels;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Room{
	//private  int roomId;
	public Room()
	{
		
	}/*
	public Room(int id)
	{
		this.roomId = (id);
	}
	public int getId()
	{
		return roomId;
	}
	*/
	public abstract int getId();
	public abstract double getRate();
	public abstract String getRoomType();
	public abstract void setRate(int rate);
	
	//set the room

	 
	
	 
	 
}