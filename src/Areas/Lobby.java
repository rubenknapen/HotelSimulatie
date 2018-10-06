package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import Managers.GridBuilder;
import Scenes.SimulationScene;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class Lobby extends Area {

	//Constructor
	public Lobby(int id, int dimensionW, int dimensionH, int x, int y)
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
			createSprite(new FileInputStream("src/Images/floor_bg_lobby.png"));
			
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
		
		HBox lobbyBackground = new HBox();
		lobbyBackground.setMinHeight(48 * 2);
		lobbyBackground.setBackground(new Background(new BackgroundFill(Color.web("#fff"), CornerRadii.EMPTY, Insets.EMPTY)));	
		
		
		lobbyBackground.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent mouseEvent) {
		        System.out.println("mouse click detected! " + mouseEvent.getSource());
		    }
		});
		
		
		Label label = new Label(Integer.toString(id));
		
		lobbyBackground.getChildren().addAll(label);
		
		// Paint the room on the grid
		GridBuilder.grid.add(lobbyBackground,x,y, dimensionW, dimensionH);
		GridBuilder.grid.setHalignment(lobbyBackground, HPos.LEFT);
		GridBuilder.grid.setValignment(lobbyBackground, VPos.BOTTOM);
        roomImageView.setFitHeight(96);
        roomImageView.setFitWidth(480);
	}
	
	//Functions
	
	
}
