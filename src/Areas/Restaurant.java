package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import Managers.GridBuilder;
import Scenes.SimulationScene;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Restaurant extends Area {

	//Constructor
	public Restaurant(int id, int dimensionW, int dimensionH, long capacity, int x, int y, String areaType)
	{
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		this.capacity = capacity;
		this.areaType = areaType;
		
        neighbours = new HashMap<>();
        distance = Integer.MAX_VALUE;
        latest = null;
        this.id = id;
		
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
		
		Label label = new Label(Integer.toString(id));
		
		restaurantBg.getChildren().addAll(roomImageView, label);
		
		// Paint the room on the grid
		GridBuilder.grid.add(restaurantBg,x,y, dimensionW, dimensionH);
		GridBuilder.grid.setHalignment(restaurantBg, HPos.LEFT);
		GridBuilder.grid.setValignment(restaurantBg, VPos.BOTTOM);		
		
	}
	
}
