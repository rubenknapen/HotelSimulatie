package Managers;

public class HotelManager {


	//Variables
	
	//Constructor
	public HotelManager()
	{
		GridBuilder gridBuilder = new GridBuilder();
		SimulationTimer timer = new SimulationTimer();
		gridBuilder.createGrid();
		gridBuilder.createGridBackground();
		gridBuilder.createRooms();
		gridBuilder.addPersons();
	}
	
	//Functions

}
