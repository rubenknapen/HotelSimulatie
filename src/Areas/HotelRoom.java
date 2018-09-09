package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Scenes.SimulationScene;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HotelRoom {


	//Variables
	public boolean available = true;
	public int roomNumber = 0;
	public int stars = 1;
	public int cleaningTime = 1;
	public boolean cleaningEmergency = false;
	
	public ImageView roomImageView;
	public Image roomImage;
	private int dimensionW;
	private int dimensionH;
	private int x;
	private int y;

	
	//Constructor
	public HotelRoom(int dimensionW, int dimensionH, int x, int y){
		
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		
		
		// Get the right image depending on dimensions
		try {
			setSprite(new FileInputStream("src/Images/door.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		// Paint the room on the grid
		SimulationScene.grid.add(this.roomImageView,x,y, dimensionW, dimensionH);
		SimulationScene.grid.setHalignment(this.roomImageView, HPos.LEFT);
		SimulationScene.grid.setValignment(this.roomImageView, VPos.BOTTOM);


	}
	
	public void setSprite(FileInputStream sprite){
        Image roomImage = new Image(sprite);
        roomImageView = new ImageView();
        roomImageView.setFitWidth(16);
        roomImageView.setFitHeight(37);
        roomImageView.setImage(roomImage);
    }
	
	
}
