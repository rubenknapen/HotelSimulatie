package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Managers.GridBuilder;
import Scenes.SimulationScene;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

public class HotelRoom extends Area {


	//Variables
	
	
	//Constructor
	public HotelRoom(int dimensionW, int dimensionH, int stars, int x, int y)
	{
		this.x = x;
		this.y = y;		
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.stars = stars;
				
		// Get the right image depending on dimensions
		try {
			getImageForStars();
			createSprite(new FileInputStream("src/Images/"+imageLocation));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		// Paint the room on the grid
		GridBuilder.grid.add(roomImageView,x,y, dimensionW, dimensionH);
		GridBuilder.grid.setHalignment(roomImageView, HPos.LEFT);
		GridBuilder.grid.setValignment(roomImageView, VPos.BOTTOM);
	}
}
