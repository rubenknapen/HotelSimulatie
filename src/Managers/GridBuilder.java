package Managers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Areas.Area;
import Areas.Elevator;
import Areas.Entrance;
import Areas.Fitness;
import Areas.Lobby;
import Areas.Restaurant;
import Areas.Stairway;
import Factories.AreaFactory;
import Factories.PersonFactory;
import Persons.Person;
import Scenes.MainMenuScene;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import simple.JSONArray;
import simple.JSONObject;
import simple.parser.JSONParser;
import simple.parser.ParseException;


public class GridBuilder {

	//Variables
	public static GridPane grid;
	static int xOffset = 3;
	private int maxY = 0;

	ArrayList<Area> HotelRooms = new ArrayList<Area>();
    ArrayList<Elevator> Elevators = new ArrayList<Elevator>();
    ArrayList<Entrance> Entrances = new ArrayList<Entrance>();
    ArrayList<Fitness> Fitnesses = new ArrayList<Fitness>();
    ArrayList<Lobby> Lobbies = new ArrayList<Lobby>();
    ArrayList<Restaurant> Restaurants = new ArrayList<Restaurant>();
    ArrayList<Stairway> Stairways = new ArrayList<Stairway>();
    int[][] isOcupied = new int[25][25];
    int objectNumber = 1;
    
	//Constructor
	public GridBuilder(){
	}
	
	public void createGrid(){
		
		grid = new GridPane();
		grid.setGridLinesVisible(true);
		grid.setMaxSize(500, 500);
		
		
		int cols = 14;
		int rows = 11;
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
		grid.add(elevatorRopeImageView, xOffset - 1, 1, 1, 1);	
				
			
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
		grid.add(elevatorBackground, xOffset - 1,1,1,8);
		grid.setBackground(new Background(new BackgroundFill(Color.web("#102860"), CornerRadii.EMPTY, Insets.EMPTY)));	
		
	}
	

	public int getMaxY() {
		return maxY;
	}
	
	public void createRooms(){


		//		EXPERIMENTAL JSON PARSER IMPLEMENTATION
			
		try {
		
			FileReader reader = new FileReader(MainMenuScene.selectedLayout);
	
	
			JSONParser jsonParser = new JSONParser();
			
			JSONArray jsonArr = (JSONArray) jsonParser.parse(reader);
			
			
			// Get max Y coordinate first !! CODE NEEDS CLEANING UP THOUGH !!
						
			for (Object o: jsonArr){
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
				
				if(y + dimensionH > maxY) {
					maxY = y + (dimensionH - 1);
				}

			}
			
			// For-each loop to create rooms via the AreaFactory
			
			for (Object o: jsonArr){
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
				
				//Debug logging - parsing in console:
				/*
				System.out.println("type: "+areaType);
				System.out.println("x: "+x);
				System.out.println("y: "+y);
				System.out.println("stars: "+stars);
				System.out.println("capacity: "+capacity);
				System.out.println("");
				*/
				
				//Create room based on parameters in object
				if(isOcupied[x][y] == 1)
				{
					System.out.println("x: "+x+" y: "+y);
					System.out.println("Hier staat al iets, deze sla ik over");
					System.out.println("object: "+objectNumber);
					System.out.println("");
					objectNumber += 1;
				}
				else if(isOcupied[x][y] != 1)
				{

					
					if (dimensionH > 1) {
						y += 1;
					}
					
					Area tempRoom = AreaFactory.createArea(areaType,dimensionW,dimensionH,stars,capacity, x + xOffset, (getMaxY() - y + 1));
												
					for (int xOcupied = x ; xOcupied < x+dimensionW ; xOcupied++) 
					{
			            for (int yOcupied = y ; yOcupied > y-dimensionH ; yOcupied--) 
			            {
			            	if(areaType == "Cinema")
			            	{
			            		isOcupied[xOcupied][yOcupied] = 6;
			            	}
			            	else
			            	{
			            		isOcupied[xOcupied][yOcupied] = 1;
			            	}
			            }
					}
					System.out.println("areaType: "+areaType);
	            	System.out.println("x: "+x+" & y: "+y+" Added");
	            	System.out.println("W: "+dimensionW+"H: "+dimensionH);
	            	System.out.println("");

					objectNumber += 1;
				}
			}
			System.out.println("Added stairs to Array");
			isOcupied[7][0] = 9;
			isOcupied[7][1] = 9;
			isOcupied[7][2] = 9;
			isOcupied[7][3] = 9;
			isOcupied[7][4] = 9;
			isOcupied[7][5] = 9;
			isOcupied[7][6] = 9;
			isOcupied[7][7] = 9;
			isOcupied[7][8] = 9;
			isOcupied[7][9] = 9;
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
	
	}
	
	public void createGridBackground() {
		
		// Create Hbox to contain background images of floors
		HBox floorBackground = new HBox();
		floorBackground.setMaxSize(48 * 8,48 * 8);
		floorBackground.setMinSize(48 * 8,48 * 8);
		
		// Create top-floor background image
		Image topFloorImage = new Image("file:src/Images/top_floor.png");
		ImageView topFloorImageView = new ImageView(topFloorImage);
		topFloorImageView.setFitWidth(384);
		topFloorImageView.setFitHeight(48);
		
		BackgroundImage myBI= new BackgroundImage(new Image("file:src/Images/floor_bg.png",384,48,false,false),
		        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
		          BackgroundSize.DEFAULT);
		floorBackground.setBackground(new Background(myBI));
		floorBackground.getChildren().add(topFloorImageView);
		
		// Add the floor background to the grid
		grid.add(floorBackground, xOffset+1, 1, 8, 8);
	}
	
	public int[][] get2DArray()
	{
		return isOcupied;
	}
	
	public void addPersons() {
		
		SimulationTimer simulationTimer = new SimulationTimer();
		SettingBuilder settingBuilder = new SettingBuilder();
		
		//create a guest
		Person guest1 = PersonFactory.createPerson("Guest","In de rij staan",true,4,4,2);

		//create a cleaner
		Person cleaner1 = PersonFactory.createPerson("Cleaner","Schoonmaken",true,4,5,2);
		
		Area lift = AreaFactory.createArea("Elevator",1,1,0,0,2,2);
		Area lobby = AreaFactory.createArea("Lobby",1,1,0,0,1,1);
		
		simulationTimer.addObserver(guest1);
		simulationTimer.addObserver(cleaner1);
		simulationTimer.activateTimer();
	}
	
	public void clearGrid() {
		grid.getChildren().clear();
	}

	public GridPane getGrid(){
		return grid;
	}
	
	
}
