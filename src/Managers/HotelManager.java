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
	public ArrayList<Person> guests;
	
	
	//Constructor
	public HotelManager(){
        ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		GridBuilder gridBuilder = new GridBuilder();
		SimulationTimer timer = new SimulationTimer();
		gridBuilder.buildGrid();
		
		//Build array list for guests
		guests = new ArrayList();

	}
	
	public void addGuest(int guestId)
	{
		Person xx = PersonFactory.createPerson("Guest","In de rij staan",true,4,4,GridBuilder.getMaxY() + 1);
		Guest g = (Guest) xx;
		g.setId(guestId);
		guests.add(xx);
		System.out.println("Guest: "+guestId+" added!");
	}

	public void removeGuest(int guestId)
	{
		for(int i = 0; i <guests.size(); i++)
		{
			Guest g = (Guest) guests.get(i);
			
			int objectGuestId = g.getId();
			if (objectGuestId == guestId)
			{
				System.out.println("Guest: "+guestId+" removed!");
				guests.remove(i);
			}
		}
	}
	
	@Override
	public void Notify(HotelEvent event) {
		String tempEvent = event.Type.toString();
		String hashmapContent = event.Data.toString();
		
		
		if (tempEvent == "CHECK_IN")
		{
			String guestId;
			int setGuestIdValue;
			String prefStars;
			
			String[] splitArray = hashmapContent.split("\\s");
			String[] splitArray2 = splitArray[1].split("=");
			
			//Set GuestID
			guestId = splitArray2[0];
			setGuestIdValue = Integer.parseInt(guestId);
			//Set prefStars
			prefStars = splitArray2[1];
			
			System.out.println("Guests id: " + guestId);
			System.out.println("Guests prefStars: " + prefStars);
			
			//Because this command is not running from Java FX I've added this to update UI from a different thread.
			Platform.runLater(
					  () -> {
						  addGuest(setGuestIdValue);
					  }
					);
			
			guestCounter++;
			System.out.println("Added a guest, total guests: " + guestCounter);
		}
		else if (tempEvent == "CHECK_OUT")
		{
			int guestId;
			String[] splitArray = hashmapContent.split("=");
			
			if (splitArray[1].contains("}"))
			{
				String[] splitArray2 = splitArray[1].split("}");
				guestId = Integer.parseInt(splitArray2[0]);
			}
			
			else 
			{
				guestId = Integer.parseInt(splitArray[1]);
			}
			
			guestCounter--;
			removeGuest(guestId);
			System.out.println("Guest: "+guestId+" left, total guests: " + guestCounter);
		}
		else if (tempEvent == "GOTO_FITNESS")
		{
			System.out.println("I'm sending a guest to fitness, selected guest is: " + "guestId");
		}
		else if (tempEvent == "NEED_FOOD")
		{
			System.out.println("I'm sending a guest to the restaurant, selected guest is: " + "guestId");
		}
		else if (tempEvent == "GOTO_CINEMA")
		{
			System.out.println("I'm sending a guest to the cinema, selected guest is: " + "guestId");
		}
		else if (tempEvent == "EVACUATE")
		{
			System.out.println("I'm sending all persons to evacuate");
		}
		else if (tempEvent == "GODZILLA")
		{
			System.out.println("Godzilla event, message: " + hashmapContent);
		}
	}
	

}
