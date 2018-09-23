package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

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

public class Stairway extends Area {

	public Stairway(String _name, int dimensionW, int dimensionH, int x, int y)
	{
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		this.x = x;
		this.y = y;
		
        neighbours = new HashMap<>();
        distance = Integer.MAX_VALUE;
        latest = null;
        name = _name;

		HBox stairwayBg = new HBox();
		stairwayBg.setBackground(new Background(new BackgroundFill(Color.web("purple"), CornerRadii.EMPTY, Insets.EMPTY)));
		
		// Paint the room on the grid
		GridBuilder.grid.add(stairwayBg,x,y, dimensionW, dimensionH);
		GridBuilder.grid.setHalignment(stairwayBg, HPos.LEFT);
		GridBuilder.grid.setValignment(stairwayBg, VPos.BOTTOM);			
		
		
//		// Paint the room on the grid
//		GridBuilder.grid.add(roomImageView,x,y, dimensionW, dimensionH);
//		GridBuilder.grid.setHalignment(roomImageView, HPos.LEFT);
//		GridBuilder.grid.setValignment(roomImageView, VPos.BOTTOM);
			
	}
	
}
