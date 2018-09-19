package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Managers.GridBuilder;
import Scenes.SimulationScene;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

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
			createSprite(new FileInputStream("src/Images/door_cinema.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		

		HBox cinemaBg = new HBox();
		cinemaBg.setBackground(new Background(new BackgroundFill(Color.web("yellow"), CornerRadii.EMPTY, Insets.EMPTY)));
		
		// Paint the room on the grid
		GridBuilder.grid.add(cinemaBg,x,y, dimensionW, dimensionH);
		GridBuilder.grid.setHalignment(cinemaBg, HPos.LEFT);
		GridBuilder.grid.setValignment(cinemaBg, VPos.BOTTOM);			
		
		
//		// Paint the room on the grid
//		GridBuilder.grid.add(roomImageView,x,y, dimensionW, dimensionH);
//		GridBuilder.grid.setHalignment(roomImageView, HPos.LEFT);
//		GridBuilder.grid.setValignment(roomImageView, VPos.BOTTOM);
			
	}
	
	//Functions
	public void startMovie()
	{
		
	}
}
