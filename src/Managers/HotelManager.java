package Managers;

import Areas.Area;
import Factories.AreaFactory;
import ShortestPath.Dijkstra;

import SimpleEvents.HotelEventManager;

public class HotelManager {


	int[][] currentMap;
	//Variables
	
	//Constructor
	public HotelManager()
	{
		GridBuilder gridBuilder = new GridBuilder();
		SimulationTimer timer = new SimulationTimer();
		HotelEventManager eventManager = new HotelEventManager();
		gridBuilder.createGrid();
		gridBuilder.createHotelBackground();
		gridBuilder.createRooms();
		gridBuilder.addPersons();
		gridBuilder.createStairway();
		
        ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		
        
        Area tempRoom1 = AreaFactory.createArea("A", "Restaurant",2,1,0,20, 1, 1);
        Area tempRoom2 = AreaFactory.createArea("B", "Restaurant",2,1,0,20, 1, 3);
        
        tempRoom1.distance = 0;
        tempRoom1.neighbours.put(tempRoom2, 4);
        
        System.out.println(_ds.Dijkstra(tempRoom1, tempRoom2));
        
		currentMap = gridBuilder.get2DArray();
	}
	
	//Functions

}
