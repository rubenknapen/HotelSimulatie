package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import Managers.GridBuilder;
import Persons.Person;
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

public class Fitness extends Area {

	//Variables
	    
	//Constructor
	public Fitness(int id, int dimensionW, int dimensionH, int x, int y)
	{
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		
        neighbours = new HashMap<>();
        distance = Integer.MAX_VALUE;
        latest = null;
        this.id = id;
        
		
		// Get the right image depending on dimensions
		try {
			createSprite(new FileInputStream("src/Images/door_fitness.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		HBox fitnessBg = new HBox();
		fitnessBg.setBackground(new Background(new BackgroundFill(Color.web("blue"), CornerRadii.EMPTY, Insets.EMPTY)));	
		
		Label label = new Label(Integer.toString(id));
		
		fitnessBg.getChildren().addAll(roomImageView, label);
		
		// Paint the room on the grid
		GridBuilder.grid.add(fitnessBg,x,y, dimensionW, dimensionH);
		GridBuilder.grid.setHalignment(fitnessBg, HPos.LEFT);
		GridBuilder.grid.setValignment(fitnessBg, VPos.BOTTOM);	
		
//		// Paint the room on the grid
//		GridBuilder.grid.add(roomImageView,x,y, dimensionW, dimensionH);
//		GridBuilder.grid.setHalignment(roomImageView, HPos.LEFT);
//		GridBuilder.grid.setValignment(roomImageView, VPos.BOTTOM);
	}
	
	//Functions
	
}
