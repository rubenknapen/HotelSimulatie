package Scenes;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class SimulationScene {

	public GridPane grid;
	public BorderPane bPane;
	public Scene simulationScene;
	public Stage simulationStage;
	
	public SimulationScene()
	{
		setStage();
		setGrid();
		setBorderPane();
		setSimulationScene();
		showSimulationStage();
	}
	
	private void showSimulationStage() 
	{
		simulationStage.show();
	}

	private void setStage()
	{
		simulationStage = new Stage();
		simulationStage.setTitle("Hotel Simulatie");
		simulationStage.setResizable(false);
	}
	
	public void setBorderPane(){
		bPane = new BorderPane();
		//bPane.setStyle("-fx-background-color: #333333;");
		bPane.setCenter(grid);
		bPane.setMaxSize(800, 800);
	}
	
	
	public void setGrid(){
		grid = new GridPane();
		grid.setGridLinesVisible(true);
		grid.setMaxSize(800, 800);
		
		int cols = 10;
		int rows = 10;
						
		for (int i = 0; i < cols; i++) {
		      ColumnConstraints colConst = new ColumnConstraints();
		      colConst.setPercentWidth(100.0 / cols);
		      grid.getColumnConstraints().add(colConst);
		}
		for (int i = 0; i < rows; i++) {
		    RowConstraints rowConst = new RowConstraints();
		    rowConst.setPercentHeight(100.0 / rows);
		    grid.getRowConstraints().add(rowConst);         
		} 		
		
		
		for (int i = 0; i<10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				grid.add(new Label(i+","+j), i, j);
			}
		}		
		
		//grid.setStyle("-fx-background-image: url('/img/ground.png')");
	}
	
	/**
	 * method die het gridveld leeg maakt.
	 */
	public void clearGrid() {
		grid.getChildren().clear();
	}

	public GridPane getGrid(){
		return grid;
	}
	
	/**
	 * method die een scene opbouwd voor het spel
	 */	
	public void setSimulationScene(){
		simulationScene = new Scene(bPane, 1000, 1000);
		simulationStage.setScene(simulationScene);
	}

	public Scene getSimulationScene(){
		return simulationScene;
	}
}
