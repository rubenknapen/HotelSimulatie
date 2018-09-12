package Scenes;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import Areas.HotelRoom;
import Factories.PersonFactory;
import Persons.Person;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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
		grid.setGridLinesVisible(false);
		grid.setMaxSize(500, 500);
		
		int cols = 14;
		int rows = 10;
		int colSize = 48;
		int rowSize= 48;
		

		
		for (int i = 0; i < cols; i++) {
		      ColumnConstraints colConst = new ColumnConstraints();
		      colConst.setMinWidth(colSize);
		      grid.getColumnConstraints().add(colConst);
		}
		for (int i = 0; i < rows; i++) {
		    RowConstraints rowConst = new RowConstraints();
		    rowConst.setMinHeight(rowSize);
		    grid.getRowConstraints().add(rowConst);         
		} 			
		
//		for (int i = 0; i<10; i++)
//		{
//			for (int j = 0; j < 10; j++)
//			{
//				grid.add(new Label(i+","+j), i, j);
//			}
//		}		
		
		// Create elevatorcabin image
		Image elevatorRopeImage = new Image("file:src/Images/elevator_rope.png");
		ImageView elevatorRopeImageView = new ImageView(elevatorRopeImage);
		elevatorRopeImageView.setFitWidth(24);
		elevatorRopeImageView.setFitHeight(48);
		elevatorRopeImageView.setTranslateX(13);
		grid.add(elevatorRopeImageView, 2, 1, 1, 1);	
		
		// Create elevatorcabin image
		Image elevatorCabinImage = new Image("file:src/Images/elevator_cabin.png");
		ImageView elevatorCabinImageView = new ImageView(elevatorCabinImage);
		elevatorCabinImageView.setFitWidth(24);
		elevatorCabinImageView.setFitHeight(48);
		elevatorCabinImageView.setTranslateX(13);
		grid.add(elevatorCabinImageView, 2, 2, 1, 1);

		// Create top-floor background image
		Image topFloorImage = new Image("file:src/Images/top_floor.png");
		ImageView topFloorImageView = new ImageView(topFloorImage);
		topFloorImageView.setFitWidth(384);
		topFloorImageView.setFitHeight(48);
		
			
		// Create Hbox to contain background images of floors
		HBox floorBackground = new HBox();
		floorBackground.setMaxSize(colSize * 8,rowSize * 8);
		floorBackground.setMinSize(colSize * 8,rowSize * 8);
		BackgroundImage myBI= new BackgroundImage(new Image("file:src/Images/floor_bg.png",384,48,false,false),
		        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		floorBackground.setBackground(new Background(myBI));
		floorBackground.getChildren().add(topFloorImageView);
		
		// Add the floor background to the grid
		grid.add(floorBackground, 4, 1, 8, 8);

		// Create elevator background image
		Image elevatorImage = new Image("file:src/Images/elevator_top.png");
		ImageView elevatorImageImageView = new ImageView(elevatorImage);
		elevatorImageImageView.setFitWidth(96);
		elevatorImageImageView.setFitHeight(48);
		
		
		// Create Hbox to contain background images of elevator
		HBox elevatorBackground = new HBox();
		elevatorBackground.setMaxSize(colSize * 2,rowSize * 8);
		elevatorBackground.setMinSize(colSize * 2,rowSize * 8);
		BackgroundImage elevatorBG= new BackgroundImage(new Image("file:src/Images/elevator_bg.png",96,48,false,false),
		        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		elevatorBackground.setBackground(new Background(elevatorBG));
		elevatorBackground.getChildren().add(elevatorImageImageView);
			
		// Add the elevator background to the grid
		grid.add(elevatorBackground, 2,1,1,8);
		grid.setBackground(new Background(new BackgroundFill(Color.web("#102860"), CornerRadii.EMPTY, Insets.EMPTY)));	
		
		// create some rooms
		HotelRoom room1 = new HotelRoom(1,1,4,2);
		HotelRoom room2 = new HotelRoom(1,1,5,2);
		HotelRoom room3 = new HotelRoom(1,1,6,2);
		HotelRoom room4 = new HotelRoom(1,1,7,2);
		HotelRoom room5 = new HotelRoom(1,1,8,2);
		HotelRoom room6 = new HotelRoom(1,1,9,2);
		HotelRoom room7 = new HotelRoom(1,1,10,2);
		
//		HotelRoom room5 = new HotelRoom(1,1,4,3);
//		HotelRoom room6 = new HotelRoom(1,1,5,3);
//		HotelRoom room7 = new HotelRoom(1,1,6,3);
//		HotelRoom room8 = new HotelRoom(1,1,7,3);
		
		//create a guest
		Person guest1 = PersonFactory.createPerson("Guest","In de rij staan",true,4,4,2);

		//create a cleaner
		Person cleaner1 = PersonFactory.createPerson("Cleaner","Schoonmaken",true,4,5,2);

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
		simulationScene = new Scene(bPane, 800, 600);
		simulationStage.setScene(simulationScene);
	}

	public Scene getSimulationScene(){
		return simulationScene;
	}
}
