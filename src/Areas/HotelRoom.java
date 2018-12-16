package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import Managers.GridBuilder;
import Scenes.SimulationScene;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

/**
 * This is the HotelRoom area.
 *
 */

public class HotelRoom extends Area {

	//Constructor
	public HotelRoom(int id, int dimensionW, int dimensionH, int stars, int x, int y, String areaType)
	{
		this.x = x;
		this.y = y;		
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.stars = stars;
		this.areaType = areaType;
		
        neighbours = new HashMap<>();
        distance = Integer.MAX_VALUE;
        latest = null;
        this.id = id;
				
		// Get the right image depending on dimensions
		try {
			getImageForStars();
			createSprite(new FileInputStream("src/Images/"+imageLocation));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		HBox roomBg = new HBox();

		roomBg.setStyle("-fx-padding: 0;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" + 
                "-fx-border-radius: 5;" + 
                "-fx-background-color: green;" + 
                "-fx-border-color: blue;");
		roomBg.setAlignment(Pos.BOTTOM_LEFT);
		
		
		Label label = new Label(Integer.toString(id) + " x: " +  (Integer.toString(x)));

		
		roomBg.getChildren().addAll(label);
		
		// Paint the room on the grid
		GridBuilder.grid.add(roomBg,x,y, dimensionW, dimensionH);
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
	
}
