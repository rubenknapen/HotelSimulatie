package Areas;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Area {

	// Variables for Dijkstra

    public HashMap<Area, Integer> neighbours;
    public int distance;
    public Area latest;
    public int id;
    public static ArrayList<Area> areaList = new ArrayList<Area>();
    	
	// Variables
	int x = 0;
	int y = 0;
	int dimensionW;
	int dimensionH;	
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
	
    public static ArrayList<Area> getAreaList(){
    	return areaList;
    }
    
	public int getDistance() {
		return dimensionW;
	}	   
    
	public int getX() {
		return x;
	}

	public int getXEnd() {
		return x + dimensionW - 1;
	}
	
	public int getRealY() {
		return (y - 1) + dimensionH;
	}

	public int getY() {
		return y;
	}

	
}
