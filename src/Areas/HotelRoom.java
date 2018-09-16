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
		
		HBox testtt = new HBox();
		testtt.setMaxSize(48 * dimensionW,48 * dimensionH);
		testtt.setMinSize(48 * dimensionW,48 * dimensionH);
//		testtt.setBackground(new Background(new BackgroundFill(Color.web("#fff"), CornerRadii.EMPTY, Insets.EMPTY)));	
//		
		// Paint the room on the grid
		GridBuilder.grid.add(roomImageView,x,y, dimensionW, dimensionH);
		GridBuilder.grid.setHalignment(roomImageView, HPos.LEFT);
		GridBuilder.grid.setValignment(roomImageView, VPos.BOTTOM);
	}
}
