package Managers;

import java.util.ArrayList;
import java.util.List;

import Areas.Area;
import Factories.AreaFactory;
import Factories.PersonFactory;
import Persons.Guest;
import Persons.Person;
import ShortestPath.Dijkstra;
import javafx.application.Platform;
import EventLib.HotelEvent;
import EventLib.HotelEventManager;

public class HotelManager implements EventLib.HotelEventListener{

	//Variables
	int guestCounter = 0;
	ArrayList<Person> guests;
	
	
	//Constructor
	public HotelManager(){
        ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		GridBuilder gridBuilder = new GridBuilder();
		SimulationTimer timer = new SimulationTimer();
		gridBuilder.buildGrid();
		
		//Build array list for guests
		guests = new ArrayList();

		//System.out.println(_ds.Dijkstra(Area.getAreaList().get(0), Area.getAreaList().get(1)));
	}
	
	public void addGuest(){
		Person xx = PersonFactory.createPerson("Guest","In de rij staan",true,4,4,2);
		guests.add(xx);
	}

	@Override
	public void Notify(HotelEvent event) {
		String tempEvent = event.Type.toString();
		
		if (tempEvent == "CHECK_IN")
		{
			//Because this command is not running from Java FX I've added this to update UI from a different thread.
			Platform.runLater(
					  () -> {
						  addGuest();
					  }
					);
			
			guestCounter++;
			System.out.println("Added a guest, total guests: " + guestCounter);
		}
		else if (tempEvent == "CHECK_OUT")
		{
			guestCounter--;
			System.out.println("A guest left, total guests: " + guestCounter);
		}
		else if (tempEvent == "GOTO_FITNESS")
		{
			System.out.println("I'm a Guest and my event is: " + tempEvent);
		}
		else if (tempEvent == "NEED_FOOD")
		{
			System.out.println("I'm a Guest and my event is: " + tempEvent);
		}
		else if (tempEvent == "GOTO_CINEMA")
		{
			System.out.println("I'm a Guest and my event is: " + tempEvent);
		}
		else if (tempEvent == "EVACUATE")
		{
			System.out.println("I'm a Guest and my event is: " + tempEvent);
		}
		else if (tempEvent == "GODZILLA")
		{
			System.out.println("I'm a Guest and my event is: " + tempEvent);
		}
	}
	

}
