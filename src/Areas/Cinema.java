package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Managers.GridBuilder;
import Scenes.SimulationScene;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

public class Cinema extends Area {

	//Variables
	public boolean moviePlaying = false;
	
	//Constructor
	public Cinema(int dimensionW, int dimensionH, int x, int y)
	{
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		
		
		// Get the right image depending on dimensions
		try {
			createSprite(new FileInputStream("src/Images/door_transparant.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		// Paint the room on the grid
		GridBuilder.grid.add(roomImageView,x,y, dimensionW, dimensionH);
		GridBuilder.grid.setHalignment(roomImageView, HPos.LEFT);
		GridBuilder.grid.setValignment(roomImageView, VPos.BOTTOM);
	}
	
	//Functions
	public void startMovie()
	{
		
	}
}
