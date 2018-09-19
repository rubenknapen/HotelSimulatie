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

public class Lobby extends Area {

	//Variables
	String field = "";
	
	//Constructor
	public Lobby(int dimensionW, int dimensionH, int x, int y)
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
		
		HBox lobbyBackground = new HBox();
		lobbyBackground.setMaxSize(48 * dimensionW,48 * dimensionH);
		lobbyBackground.setMinSize(48 * dimensionW,48 * dimensionH);
		lobbyBackground.setBackground(new Background(new BackgroundFill(Color.web("#fff"), CornerRadii.EMPTY, Insets.EMPTY)));	
		
		// Paint the room on the grid
		GridBuilder.grid.add(lobbyBackground,x,y, dimensionW, dimensionH);
		GridBuilder.grid.setHalignment(lobbyBackground, HPos.LEFT);
		GridBuilder.grid.setValignment(lobbyBackground, VPos.BOTTOM);
	}
	
	//Functions
	
	
}
