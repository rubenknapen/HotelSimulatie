package Scenes;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;

import Areas.HotelRoom;
import Persons.PersonFactory;
import Persons.iPerson;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class SimulationScene {

	public static GridPane grid;
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
		grid.setGridLinesVisible(true);
		grid.setMaxSize(500, 500);
		
		int cols = 10;
		int rows = 10;
		

		
		for (int i = 0; i < cols; i++) {
		      ColumnConstraints colConst = new ColumnConstraints();
		      colConst.setMinWidth(48);
		      grid.getColumnConstraints().add(colConst);
		}
		for (int i = 0; i < rows; i++) {
		    RowConstraints rowConst = new RowConstraints();
		    rowConst.setMinHeight(48);
		    grid.getRowConstraints().add(rowConst);         
		} 			
		
		for (int i = 0; i<10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				grid.add(new Label(i+","+j), i, j);
			}
		}		

		Image img = new Image("file:src/Images/floor_bg.png");
		ImageView imgView = new ImageView(img);
		imgView.setFitWidth(336);
		imgView.setFitHeight(48);

		grid.add(imgView,1,2,7,1);
	
		
		
		
		// create some rooms
		HotelRoom room = new HotelRoom(1,1,3,2);
		HotelRoom room2 = new HotelRoom(1,1,4,2);
		HotelRoom room3 = new HotelRoom(1,1,5,2);
		HotelRoom room4 = new HotelRoom(1,1,6,2);
		
		
		//create a guest
		iPerson guest1 = PersonFactory.createPerson("Guest","In de rij staan",true,4,1,2);

		//create a cleaner
		iPerson cleaner1 = PersonFactory.createPerson("Cleaner","Schoonmaken",true,4,2,2);

		
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
