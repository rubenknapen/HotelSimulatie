package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Managers.GridBuilder;
import Scenes.SimulationScene;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Restaurant extends Area {

	//Variables
	public int capacity = 0;
	
	//Constructor
	public Restaurant(int dimensionW, int dimensionH, int x, int y)
	{
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		
		
		// Get the right image depending on dimensions
		try {
			createSprite(new FileInputStream("src/Images/door_restaurant.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
//		// Paint the room on the grid
//		GridBuilder.grid.add(roomImageView,x,y, dimensionW, dimensionH);
//		GridBuilder.grid.setHalignment(roomImageView, HPos.LEFT);
//		GridBuilder.grid.setValignment(roomImageView, VPos.BOTTOM);
//		
		HBox restaurantBg = new HBox();
		restaurantBg.setBackground(new Background(new BackgroundFill(Color.web("red"), CornerRadii.EMPTY, Insets.EMPTY)));	
		
		// Paint the room on the grid
		GridBuilder.grid.add(restaurantBg,x,y, dimensionW, dimensionH);
		GridBuilder.grid.setHalignment(restaurantBg, HPos.LEFT);
		GridBuilder.grid.setValignment(restaurantBg, VPos.BOTTOM);		
		
	}
	
	//Functions
	public void setCapacity()
	{
		
	}
}
