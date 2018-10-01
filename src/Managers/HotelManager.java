package Managers;

import Areas.Area;
import Factories.AreaFactory;
import Factories.PersonFactory;
import Persons.Person;
import ShortestPath.Dijkstra;
import EventLib.HotelEvent;
import EventLib.HotelEventManager;

public class HotelManager implements EventLib.HotelEventListener{


	
	//Variables
	
	//Constructor
	public HotelManager()
	{
        ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		GridBuilder gridBuilder = new GridBuilder();
		SimulationTimer timer = new SimulationTimer();
		gridBuilder.createGrid();
		gridBuilder.createHotelBackground();
		gridBuilder.createRooms();
		gridBuilder.addPersons();
		Person guest1 = PersonFactory.createPerson("Guest","In de rij staan",true,4,4,2);
		EventLib.HotelEventManager eventManager = new EventLib.HotelEventManager();
		//timer.activateTimer();
		
		
		eventManager.register(guest1);
		
		
		
		gridBuilder.createStairway();
		gridBuilder.createEdges();
		eventManager.start();
		
		//Observers toevoegen
		


		
//		Area startArea = Area.getAreaList().get(0);
//		Area endArea = Area.getAreaList().get(27);
//		
//
//		System.out.println(_ds.Dijkstra(startArea, endArea));
//		
//        System.out.println(_ds.Dijkstra(Area.getAreaList().get(0), Area.getAreaList().get(1)));
        
        

//		currentMap = gridBuilder.get2DArray();
	}

	@Override
	public void Notify(HotelEvent event) 
	{
		if (event.Type.toString() == "START_CINEMA")
		{
			System.out.println("The movie has started");
			//startMovie(); has to be implemented
		}
		else if (event.Type.toString() == "EVACUATE")
		{
			System.out.println(event.Message);
			System.out.println(event.Data.toString());
		}
		else if (event.Type.toString() == "GODZILLA")
		{
			System.out.println(event.Data.toString());
		}
	}
	
	//Functions

}
