package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Scenes.SimulationScene;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HotelRoom extends Area {


	//Variables
	public boolean available = true;
	public int roomNumber = 0;
	public int stars = 1;
	public int cleaningTime = 1;
	public boolean cleaningEmergency = false;
	
	
	//Constructor
	public HotelRoom(int dimensionW, int dimensionH, int x, int y)
	{
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		
		
		// Get the right image depending on dimensions
		try {
			createSprite(new FileInputStream("src/Images/door.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		// Paint the room on the grid
		SimulationScene.grid.add(roomImageView,x,y, dimensionW, dimensionH);
		SimulationScene.grid.setHalignment(roomImageView, HPos.LEFT);
		SimulationScene.grid.setValignment(roomImageView, VPos.BOTTOM);
	}
}
