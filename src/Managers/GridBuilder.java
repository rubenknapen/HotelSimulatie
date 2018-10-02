package Managers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import Areas.Area;
import Areas.Lobby;
import Areas.Stairway;
import Factories.AreaFactory;
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
	private static int maxY = 0;
	private int maxX = 0;
	private	int roomNumber = 1; // Give room numbers
    int[][] isOcupied = new int[25][25];
    int objectNumber = 1;
    ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
    
    
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
		
	public void createRooms(){


		//		EXPERIMENTAL JSON PARSER IMPLEMENTATION
			
		try {
		
			FileReader reader = new FileReader(MainMenuScene.selectedLayout);
	
	
			JSONParser jsonParser = new JSONParser();
			
			JSONArray jsonArr = (JSONArray) jsonParser.parse(reader);
			
			
			// Get max Y coordinate first !! CODE NEEDS CLEANING UP THOUGH !!
						
			for (Object o: jsonArr){
				int x = 0;
				int y = 0;
	
				
				int dimensionW = 0;
				int dimensionH = 0;
				
				JSONObject obj = (JSONObject)o;
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
				
				if(x + dimensionW > maxX) {
					maxX = x + dimensionW;
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
				
				//Check if capacity is available
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
					
					// Increment the roomNumber each loop
					int id = roomNumber;
					roomNumber++;
					
					Area tempRoom = AreaFactory.createArea(id, areaType,dimensionW,dimensionH,stars,capacity, x + xOffset, (getMaxY() - y + 1));
				    Area.getAreaList().add(tempRoom);
												
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
	
	public void createStairway() {
		for(int i = 0; i < (getMaxY() + 1); i++) {
			Area stairway = AreaFactory.createArea(roomNumber, "Stairway",2,1,0,0,getMaxX(),1 + i);
			Area.getAreaList().add(stairway);
			roomNumber++;
		}	      		
	}
	
	
	public void createHotelBackground() {
		
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
	
	

	
	public void addElevator()
	{
		Area lift = AreaFactory.createArea(1, "Elevator",1,1,0,0,2,2);
	}
	
	public void addLobby()
	{
		Area lobby = AreaFactory.createArea(roomNumber, "Lobby",10,2,0,0,(xOffset - 1),(getMaxY() + 1));
		Area.getAreaList().add(lobby);
	}
	
	public void createEdges() {

		for (Area object: Area.getAreaList()) {
			for (Area object2: Area.getAreaList()) {		 
				// Check left for neighbours
			    if ((object.getX() - 1 == object2.getX() || object.getX() - 1 == object2.getXEnd()) && object.getRealY() == object2.getRealY() ) {
			    	 //System.out.println("Ik " + object.id + " ben buurtjes met " + object2.id + " met gewicht: " + object2.getDistance());
			    	 object.neighbours.put(object2, object2.getDistance());
		        }
			    // Check right for neighbours
			    if (object.getXEnd() + 1 == object2.getX() && object.getRealY() == object2.getRealY() ) {
			    	//System.out.println("Ik " + object.id + " ben buurtjes met " + object2.id + " met gewicht: " + object.getDistance());
			    	object.neighbours.put(object2, object2.getDistance());
			    }
				if(object instanceof Stairway) {
					if ((object.getY() == object2.getY()-1) && object.getX() == object2.getX()) {
				    	//System.out.println("Ik " + object.id + " ben buurtjes met " + object2.id + " met gewicht: " + SettingBuilder.stairTime);
				    	object.neighbours.put(object2, SettingBuilder.stairTime);
				    }
					if ((object.getY() == object2.getY()+1) && object.getX() == object2.getX()) {
				    	//System.out.println("Ik " + object.id + " ben buurtjes met " + object2.id + " met gewicht: " + SettingBuilder.stairTime);
				    	object.neighbours.put(object2, SettingBuilder.stairTime);
				    }					
				}
				if(object instanceof Lobby) {
					if((object.getXEnd() == object2.getX() && object.getY() == object2.getY())){
						object.neighbours.put(object2, 7); // value 7 moet nog dynamisch gemaakt worden aan de hand van entrance point
						object2.neighbours.put(object, 7); // value 7 moet nog dynamisch gemaakt worden aan de hand van entrance point
					}
				}
		    }
		}					  
	}
	
	public void clearGrid() {
		grid.getChildren().clear();
	}

	public GridPane getGrid(){
		return grid;
	}
	
	public static int getMaxY() {
		return maxY;
	}
	
	public int getMaxX() {
		return maxX + xOffset;
	}
	
	public void buildGrid() {
		createGrid();
		createHotelBackground();
		createRooms();
		addElevator();
		createStairway();
		addLobby();
		createEdges();
	}
	
}
