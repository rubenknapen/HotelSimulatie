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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Areas.Area;
import Areas.HotelRoom;
import Factories.AreaFactory;
import Factories.PersonFactory;
import Persons.Person;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import simple.JSONArray;
import simple.JSONObject;
import simple.parser.JSONParser;
import simple.parser.ParseException;

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
		
		
		//
		//
		//
		//		EXPERIMENTAL JSON PARSER IMPLEMENTATION
		//
		//
		//
		
		try {
		
			FileReader reader = new FileReader("src/layout/hotel.layout");

			JSONParser jsonParser = new JSONParser();
			
			JSONArray jsonArr = (JSONArray) jsonParser.parse(reader);
			
			for (Object o: jsonArr)
			{
				int stars = 0;
				long capacity = 0;
				int x = 0;
				int y = 0;
				
				int dimensionW = 0;
				int dimensionH = 0;
				
				JSONObject obj = (JSONObject)o;
				String areaType = (String) obj.get("AreaType");
				String dimension = (String) obj.get("Dimension");
				
				//Spliting dimension and putting it in ints
				String[] parts = dimension.split(",");
				dimensionW = Integer.parseInt(parts[0]);
				String[] partsAfterSpace = parts[1].split("\\s+");
				dimensionH = Integer.parseInt(partsAfterSpace[1]);
				
				//Splitting position in x & y
				String position = (String) obj.get("Position");
				parts = position.split(",");
				x = Integer.parseInt(parts[0]);
				partsAfterSpace = parts[1].split("\\s+");
				y = Integer.parseInt(partsAfterSpace[1]);
				
				//Check if classification is available
				if(obj.containsKey("Classification"))
				{
					String classification = (String) obj.get("Classification");
					stars = Integer.parseInt(classification.charAt(0) + "");
				}
				
				//Check if capacity if available
				if(obj.containsKey("Capacity"))
				{
					capacity = (long) obj.get("Capacity");
				}
				
				System.out.println("type: "+areaType);
				System.out.println("x: "+x);
				System.out.println("y: "+y);
				System.out.println("stars: "+stars);
				System.out.println("capacity: "+capacity);
				System.out.println("");
				
				Area tempRoom = AreaFactory.createArea(areaType, x,y,dimensionW,dimensionH,stars,capacity);
			
			}
			
		}	
		
		catch (FileNotFoundException ex) 
		{
			ex.printStackTrace();
		} 	
		
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}	 
		
		catch (ParseException ex)
		{
			ex.printStackTrace();
		} 
		
		catch (NullPointerException ex) 
		{
			ex.printStackTrace();
		}
		
		
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
