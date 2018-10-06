package Scenes;

import javafx.scene.layout.BorderPane;
import Managers.GridBuilder;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SimulationScene {

	public static GridPane grid;
	public BorderPane bPane;
	public static Scene simulationScene;
	public Stage simulationStage;

	
	public SimulationScene()
	{
		setBorderPane();
		setSimulationScene();
	}
	

	public void setBorderPane(){
		bPane = new BorderPane();
		bPane.setCenter(GridBuilder.grid);
		bPane.setMaxSize(600, 600);
	}
	
	/**
	 * method die een scene opbouwd voor het spel
	 */	
	public void setSimulationScene(){
		// TO DO auto - resize 
		simulationScene = new Scene(bPane, 800, 600);

		MainMenuScene.mainMenuStage.setScene(simulationScene);
	}

	public Scene getSimulationScene(){
		return simulationScene;
	}
}
