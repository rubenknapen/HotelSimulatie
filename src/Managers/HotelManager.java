package Managers;

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
		gridBuilder.createGridBackground();
		gridBuilder.createRooms();
		gridBuilder.addPersons();
		currentMap = gridBuilder.get2DArray();
		timer.activateTimer();
	}
	
	//Functions

}
