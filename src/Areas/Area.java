package Areas;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import Persons.Person;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public abstract class Area {

	// Variables for Dijkstra

    public HashMap<Area, Integer> neighbours;
    public int distance;
    public Area latest;
    public long capacity;
    public int id;
    public static List<Area> areaList =  Collections.synchronizedList(new ArrayList<Area>());	
    
	// Variables
	public int x = 0;
	public int y = 0;
	public int dimensionW;
	public int dimensionH;	
	public boolean available = true;
	public int stars = 1;
	public int cleaningTime = 1;
	public boolean cleaningEmergency = false;
	
	int position = 0;
	int dimension = 0;
	long occupants = 0;
	
	public ImageView roomImageView;
	public Image roomImage;
	
	String imageLocation;
	public HBox roomBg = new HBox();

		
	//Functions
	
	public void createSprite(FileInputStream sprite){
		Image roomImage = new Image(sprite);
        roomImageView = new ImageView();
        roomImageView.setFitWidth(16);
        roomImageView.setFitHeight(37);
        roomImageView.setImage(roomImage);
	}
	
	public void getImageForStars(){
		if (stars == 1){
			imageLocation = "door_1.png";
		} else if (stars == 2){
			imageLocation = "door_2.png";
		} else if (stars == 3){
			imageLocation = "door_3.png";
		} else if (stars == 4){
			imageLocation = "door_4.png";
		} else if (stars == 5){
			imageLocation = "door_5.png";
		}
	}
	
    public static List<Area> getAreaList(){
    	return areaList;
    }
    
	public int getDistance() {
		return dimensionW;
	}	   
    
	public int getX() {
		return x;
	}

	public int getXEnd() {
		return x + dimensionW -1;
	}
	
	public int getRealY() {
		return (y - 1) + dimensionH;
	}

	public int getY() {
		return y;
	}

	public void setAvailability(boolean b) {
		this.available = b;
		
	}
	
	public void makeRoomDirty(){
		
	}

	
}
