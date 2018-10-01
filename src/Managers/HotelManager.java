package Managers;

import Areas.Area;
import Factories.AreaFactory;
import ShortestPath.Dijkstra;

import SimpleEvents.HotelEventManager;

public class HotelManager {


	//Variables
	
	//Constructor
	public HotelManager()
	{
        ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		GridBuilder gridBuilder = new GridBuilder();
		SimulationTimer timer = new SimulationTimer();
		HotelEventManager eventManager = new HotelEventManager();
		gridBuilder.createGrid();
		gridBuilder.createHotelBackground();
		gridBuilder.createRooms();
		gridBuilder.addPersons();
		//currentMap = gridBuilder.get2DArray();
		timer.activateTimer();
//		gridBuilder.addPersons();
		gridBuilder.createStairway();
		gridBuilder.createEdges();     
		


		
//		Area startArea = Area.getAreaList().get(0);
//		Area endArea = Area.getAreaList().get(27);
//		
//
//		System.out.println(_ds.Dijkstra(startArea, endArea));
//		
//        System.out.println(_ds.Dijkstra(Area.getAreaList().get(0), Area.getAreaList().get(1)));
        
        

//		currentMap = gridBuilder.get2DArray();
	}
	
	//Functions

}
