package Managers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Areas.Area;
import Areas.Elevator;
import Areas.Entrance;
import Areas.Fitness;
import Areas.HotelRoom;
import Areas.Lobby;
import Areas.Restaurant;
import Areas.Stairway;
import Factories.AreaFactory;
import Factories.PersonFactory;
import Persons.Person;
import simple.JSONArray;
import simple.JSONObject;
import simple.parser.JSONParser;
import simple.parser.ParseException;

public class GridBuilder {


	//Variables
	
	//Constructor
	public GridBuilder()
	{
		buildStartUpGrid();
	}
	
	private void buildStartUpGrid()
	{
	//Functions

	//
	//
	//
	//		EXPERIMENTAL JSON PARSER IMPLEMENTATION
	//
	//
	//
	
	ArrayList<Area> HotelRooms = new ArrayList<Area>();
    ArrayList<Elevator> Elevators = new ArrayList<Elevator>();
    ArrayList<Entrance> Entrances = new ArrayList<Entrance>();
    ArrayList<Fitness> Fitnesses = new ArrayList<Fitness>();
    ArrayList<Lobby> Lobbies = new ArrayList<Lobby>();
    ArrayList<Restaurant> Restaurants = new ArrayList<Restaurant>();
    ArrayList<Stairway> Stairways = new ArrayList<Stairway>();
    int[][] isOcupied = new int[25][25];
	
	
	try {
	
		FileReader reader = new FileReader("src/layout/hotel2.layout");

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
				System.out.println("");
			}
			else if(isOcupied[x][y] != 1)
			{
				Area tempRoom = AreaFactory.createArea(areaType,dimensionW,dimensionH,stars,capacity, x, y);
								
				for (int xOcupied = x ; xOcupied < x+dimensionW ; xOcupied++) 
				{
		            for (int yOcupied = y ; yOcupied < y+dimensionH ; yOcupied++) 
		            {
		            	isOcupied[xOcupied][yOcupied] = 1;
		            	System.out.println("x: "+xOcupied+" & y: "+yOcupied+" Toegevoegd aan array");
		            }
				}
			}
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
}
