package Scenes;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import Areas.HotelRoom;
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
	public static HotelRoom room;
	
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
		simulationStage.setResizable(true);
	}
	
	public void setBorderPane(){
		bPane = new BorderPane();
		//bPane.setStyle("-fx-background-color: #333333;");
		bPane.setCenter(grid);
		bPane.setMaxSize(600, 600);
	}
	
	
	public void setGrid(){
		grid = new GridPane();
		grid.setGridLinesVisible(false);
		grid.setMaxSize(500, 500);
		
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
		
		HotelRoom room = new HotelRoom(1,1);
		HotelRoom room2 = new HotelRoom(2,2);
		HotelRoom room3 = new HotelRoom(1,1);
		
		grid.add(room.mensImageView,8,8);
		grid.add(room2.mensImageView,1,1,2,2);
		grid.add(room3.mensImageView,5,5);
		
//		grid.setStyle("-fx-background-image: url('/Images/bg.png')");
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
		simulationScene = new Scene(bPane, 600, 600);
		simulationStage.setScene(simulationScene);
	}

	public Scene getSimulationScene(){
		return simulationScene;
	}
}
