package Managers;

import Areas.Area;
import Factories.AreaFactory;
import ShortestPath.Dijkstra;

import EventLib.HotelEventManager;

public class HotelManager {


	
	//Variables
	
	//Constructor
	public HotelManager()
	{
        ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		GridBuilder gridBuilder = new GridBuilder();
		SimulationTimer timer = new SimulationTimer();
		EventLib.HotelEventManager eventManager = new EventLib.HotelEventManager();
		gridBuilder.createGrid();
		gridBuilder.createHotelBackground();
		gridBuilder.createRooms();
		gridBuilder.addPersons();
		timer.activateTimer();
		gridBuilder.addPersons();
		gridBuilder.createStairway();
		gridBuilder.createEdges();
		eventManager.start();
		


		
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
