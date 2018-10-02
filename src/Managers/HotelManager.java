package Managers;

import java.util.ArrayList;
import java.util.List;

import Areas.Area;
import Areas.HotelRoom;
import Areas.Lobby;
import Areas.Restaurant;
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
	int selectedRoomId;
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
		Person xx = PersonFactory.createPerson("Guest","In de rij staan",true,selectedRoomId,4,GridBuilder.getMaxY() + 1);
		Guest g = (Guest) xx;
		g.setId(guestId);
		guests.add(xx);
		System.out.println("Guest: " + guestId + " added!");
	}

	public void removeGuest(int guestId)
	{
		for(int i = 0; i < guests.size(); i++)
		{
			Guest g = (Guest) guests.get(i);
			
			if (g.getId() == guestId)
			{
				//Get Roominfo for Housekeeping
				selectedRoomId = g.getSelectedRoom();
				
				//Send housekeeping based on above info
				roomToClean(selectedRoomId);
				
				//Clear the room for a new guest
				freeRoom(selectedRoomId);
			}
			
			//logging info
			int objectGuestId = g.getId();
			if (objectGuestId == guestId)
			{
				System.out.println("Guest: "+guestId+" removed!");
				guests.remove(i);
			}
		}
	}
	
	public Area roomToClean(int roomId){
		
		for (Area a : Area.getAreaList()) {
			if(a.id == roomId) {
				System.out.println("Sending housekeeping to: "+ a);
				return a;
			}
		}

		//Send housekeeping to room
		return null;
	}
	
	public void findRoom(int prefStars) {
		for (Area object: Area.getAreaList()) {
			if(object instanceof HotelRoom) {
				if (object.stars == prefStars && object.available == true)
				{
					selectedRoomId = object.id;
					System.out.println("My room ID: " + object.id);
					System.out.println("My X-Coord: " + object.x);
					System.out.println("My Y-Coord: " + object.y);
					((HotelRoom) object).setAvailability(false);
					break;
				}
			}
		}
	}
	
	public void assignRoom(String type, int roomId)
	{
		if (type == "Restaurant")
		{
			for (Area object: Area.getAreaList()) {
				if(object instanceof Restaurant) {
					if (object.id == roomId)
					{
						object.setAvailability(true);
						System.out.print("Room ID: " + object.id + " is now available!");
						System.out.print("");
						break;
					}
				}
			}
		}
	}
	
	public void freeRoom(int roomId) {
		for (Area object: Area.getAreaList()) {
			if(object instanceof HotelRoom) {
				if (object.id == roomId)
				{
					object.setAvailability(true);
					System.out.print("Room ID: " + object.id + " is now available!");
					System.out.print("");
					break;
				}
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
			int prefStarsInt = Integer.parseInt(prefStars);
			
			System.out.println("Guests id: " + guestId);
			System.out.println("Guests prefStars: " + prefStars);
			
			findRoom(prefStarsInt);
			
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
			assignRoom("Restaurant", guestId);
			System.out.println("I'm sending a guest to the restaurant, selected guest is: " + guestId);
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
