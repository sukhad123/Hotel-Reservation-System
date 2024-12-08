package applicationModels;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SingleRoom extends Room {

    private final SimpleIntegerProperty roomId;
    private final SimpleStringProperty name = new SimpleStringProperty("Single Room");
    private SimpleDoubleProperty rate = new SimpleDoubleProperty(200);

    // Constructor remains unchanged
    public SingleRoom(int roomId) {
        this.roomId = new SimpleIntegerProperty(roomId);
    }

    // Getter for rate - returns the value of rate as a double
    @Override
    public double getRate() {
        return rate.doubleValue();
    }

    // Getter for room type - returns the name of the room
    @Override
    public String getRoomType() {
        return name.get(); // Use .get() to fetch the value of the SimpleStringProperty
    }

    // Getter for room ID - returns the value of roomId
    @Override
    public int getId() {
        return roomId.intValue(); // Use .intValue() to fetch the value of the SimpleIntegerProperty
    }

    // JavaFX property getter methods for binding
    public SimpleIntegerProperty roomIdProperty() {
        return roomId;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public SimpleDoubleProperty rateProperty() {
        return rate;
    }

	@Override
	public void setRate(int rate) {
		 this.rate = new SimpleDoubleProperty(200);;
		
	}
}
