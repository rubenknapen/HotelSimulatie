package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import Managers.GridBuilder;
import Managers.HotelManager;
import Managers.SettingBuilder;
import Scenes.SimulationScene;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Cinema extends Area {

	//Variables
	public boolean moviePlaying = false;
	
	//Constructor
	public Cinema(int id, int dimensionW, int dimensionH, int x, int y, String areaType) {
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		this.areaType = areaType;
		
        neighbours = new HashMap<>();
        distance = Integer.MAX_VALUE;
        latest = null;
        this.id = id;

		
		// Get the right image depending on dimensions
		try {
			createSprite(new FileInputStream("src/Images/door_cinema.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		HBox cinemaBg = new HBox();
		cinemaBg.setBackground(new Background(new BackgroundFill(Color.web("yellow"), CornerRadii.EMPTY, Insets.EMPTY)));
		
		Label label = new Label(Integer.toString(id));
		
		cinemaBg.getChildren().addAll(roomImageView, label);
		
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
	public void startMovie(){
		HotelManager.moviePlaying = true;
	}
	
	public void stopMovie(){
		HotelManager.moviePlaying = false;
		HotelManager.movieTimeRemaining = SettingBuilder.movieTime;
	}
}
