package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Managers.GridBuilder;
import Scenes.SimulationScene;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Elevator extends Area {

	//Variables
	public boolean broken = true;
	public int currentFloor = 0;
	private ImageView elevatorCabinImageView;
	
	//Constructor
	public Elevator(int dimensionW, int dimensionH, int x, int y)
	{
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		
		
		// Get the right image depending on dimensions
		try {
			createSprite(new FileInputStream("src/Images/elevator_cabin.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		// Paint the room on the grid
		GridBuilder.grid.add(roomImageView,x,y, dimensionW, dimensionH);
		GridBuilder.grid.setHalignment(roomImageView, HPos.LEFT);
		GridBuilder.grid.setValignment(roomImageView, VPos.BOTTOM);
		roomImageView.setTranslateX(13);
		
	}
	
	public void createSprite(FileInputStream sprite)
	{
		Image roomImage = new Image(sprite);
        roomImageView = new ImageView();
        roomImageView.setFitWidth(24);
        roomImageView.setFitHeight(48);
        roomImageView.setImage(roomImage);
	}
	
	
	//Functions
	public void goToFloor()
	{
		
	}
}
