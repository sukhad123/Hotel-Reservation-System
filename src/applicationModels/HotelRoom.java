package applicationModels;

import java.util.ArrayList;

import applicationDatabase.JDBCDA;


public class HotelRoom {
	private final ArrayList<Room> hotelRooms;
	private final ArrayList<Room> occupiedRooms;
	private JDBCDA database;
	public HotelRoom()
	{
		
		 
		database = new JDBCDA();
		occupiedRooms = database.getBookedRoom();
		this.hotelRooms = database.getHotelRooms();
	}
	
	public void addRoom(Room x)
	{
		hotelRooms.add(x);
	}
	public void updateRoom(ArrayList<Room> selectedRooms)
	{
		for(Room i: selectedRooms) {
			occupiedRooms.add(i);	
		}
		
	}
	public ArrayList<Room> recommendRooms(int numGuests) {
        ArrayList<Room> tmp = new ArrayList<>();
		// For 1 or 2 adults
        if (numGuests == 1 || numGuests == 2) {
            System.out.println("Recommended Room(s):");
            getAvailableSingleRoom(tmp);
            getAvailableDeluxeRoom(tmp);
            getAvailablePenthouseRoom(tmp);
        }
        // For 3 or 4 adults
        else if (numGuests > 2 && numGuests <= 4) {
            System.out.println("Recommended Room(s):");
            getAvailableDoubleRoom(tmp);
            if (numGuests == 3 || numGuests == 4) {
                getAvailableSingleRoom(tmp); // Also check for available SingleRooms if needed
                getAvailableDeluxeRoom(tmp);
                getAvailablePenthouseRoom(tmp);
            }
        }
        // For more than 4 adults
        else {
            System.out.println("Recommended Room(s):");
            // Recommend multiple DoubleRooms or a combination of Double and Single Rooms
            int doubleRoomCount = numGuests / 2; 
            for (int i = 0; i < doubleRoomCount; i++) {
                getAvailableDoubleRoom(tmp); 
                getAvailableDeluxeRoom(tmp);
                getAvailablePenthouseRoom(tmp);
            }
            int remainingGuests = numGuests % 2;
            if (remainingGuests > 0) {
                getAvailableSingleRoom(tmp); // Offer SingleRooms for leftover guests
            }
        }
        return tmp;
    }
	private void getAvailableSingleRoom(ArrayList<Room> x) {
        for (Room i : hotelRooms) {
            if (i.getClass().equals(SingleRoom.class) && !occupiedRooms.contains(i)) {
            	x.add(i);
                System.out.println("Available Single Room ID: " + i.getId());
            }
        }
    }

    // Get available Double Rooms
    private void getAvailableDoubleRoom(ArrayList<Room> x) {
        for (Room i : hotelRooms) {
            if (i.getClass().equals(DoubleRoom.class) && !occupiedRooms.contains(i)) {
            	x.add(i);
                System.out.println("Available Double Room ID: " + i.getId());
            }
        }
    }
    // Get available Deluxe Rooms
    private void getAvailableDeluxeRoom(ArrayList<Room> x) {
        for (Room i : hotelRooms) {
            if (i.getClass().equals(DeluxeRoom.class) && !occupiedRooms.contains(i)) {
            	x.add(i);
                System.out.println("Available Deluxe Room ID: " + i.getId() + " (Higher Price)");
            }
        }
    }

    // Get available Penthouse Rooms
    private void getAvailablePenthouseRoom(ArrayList<Room> x) {
        for (Room i : hotelRooms) {
            if (i.getClass().equals(PentHouse.class) && !occupiedRooms.contains(i)) {
            	x.add(i);
                System.out.println("Available Penthouse Room ID: " + i.getId() + " (Premium Price)");
            }
        }
    }


	public void getSingleRoom()
	{
		for(Room i: hotelRooms)
		{
			if(i.getClass().equals(SingleRoom.class))
			{
				System.out.println(i.getId());
			}
		}
	}
	
	
}
