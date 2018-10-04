package Managers;

import java.util.ArrayList;
import java.util.List;

import Areas.Area;
import Areas.Cinema;
import Areas.Fitness;
import Areas.HotelRoom;
import Areas.Lobby;
import Areas.Restaurant;
import Factories.AreaFactory;
import Factories.PersonFactory;
import Persons.Cleaner;
import Persons.Guest;
import Persons.Person;
import ShortestPath.Dijkstra;
import javafx.application.Platform;
import javafx.scene.Node;
import EventLib.HotelEvent;
import EventLib.HotelEventManager;

public class HotelManager implements EventLib.HotelEventListener{

	//Variables
	int guestCounter = 0;
	int selectedRoomId;

	public static ArrayList<Person> guests;
	public ArrayList<Person> cleaners;
	ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
	
	
	//Constructor
	public HotelManager(){

		GridBuilder gridBuilder = new GridBuilder();
		gridBuilder.buildGrid();
		
			
		//Build array list for guests
		guests = new ArrayList();
		cleaners = new ArrayList();


		// testing purpose
		
		
//		Person xx = PersonFactory.createPerson("Guest","Go To Room", 133,true,20,10,3);
//		guests.add(xx);
//		xx.getRoute(Area.getAreaList().get(36));
////		xx.getRoute(findClosestFitness());
//		
//		for(Person guest : guests) {
//			if(guest.getId() == 134) {
//				System.out.println(guest.getId());
//				System.out.println("+++++++++++++++++++++++  Fitness comming in ==============================================");
//				guest.currentRoute.clear();
//				guest.getRoute(findClosestFitness());
//				System.out.println("loop je vast?");
//	//			System.out.println(guest.currentRoute);
//			}
//		}
		
		
		SimulationTimer timer = new SimulationTimer();
		timer.activateTimer();		
						
				
	}
	
	public static void moveGuests() {
  		for(Person guest : guests) {
			guest.moveToArea();
  		}
	}
	
	public void addCleaners(int amount)
	{
		for(int i = 1; i <= amount; i++)
		{
			Person xx = PersonFactory.createPerson("Cleaner","Inactive",i,true,0,4,GridBuilder.getMaxY() - 4);
			Cleaner c = (Cleaner) xx;
			c.setId(i);
			cleaners.add(xx);
			System.out.println("Cleaner: " + c.getId() + " added!");
		}
	}

	public void removeGuest(int guestId)
	{
		int availableCleanerId;
		
		for(int i = 0; i < guests.size(); i++)
		{
			Guest g = (Guest) guests.get(i);
			
			if (g.getId() == guestId)
			{
				//Get Roominfo for Housekeeping
				selectedRoomId = g.getSelectedRoom();
				g.deleteSprite();
				
				availableCleanerId = getAvailableCleaner("CLEANING");
				
				//Send housekeeping based on above info
				//this is endPosition for Dijkstra
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
				System.out.println("Sending housekeeping to object ID: "+ a.id);
				return a;
			}
		}
		return null;
	}
	
	
	public int getRoom(int prefStars)
	{
		int starAmount = prefStars;
		System.out.println("Ik zoek een kamer met "+prefStars+" sterren!");
		boolean checkForRoom = true;
		while(checkForRoom)
		{
			for (Area object: Area.getAreaList()) 
			{
				if(object instanceof HotelRoom) 
				{
					if (object.stars == starAmount && object.available == true)
					{					
						object.setAvailability(false);
						System.out.println("Kamer gevonden ik return het object! "+object.id);
						return object.id;
					}
				}
			}
			if (starAmount < 5)
			{
				System.out.println("Aantal sterren niet beschikbaar, hoog op met 1 ster");
				starAmount += 1;
			} else if (starAmount == 5)
			{
				checkForRoom = false;
			}
		}
		return 0;
	}
	
	
	public Area getRoomNode(int roomId) {
		for (Area object: Area.getAreaList()) {
			if(object instanceof HotelRoom) {
				if (object.id == roomId)
				{					
					object.setAvailability(false);
					return object;
				}
			}
		}
		return null;
	}
	
	public Area getAreaNode(int x, int y)
	{
		for (Area object: Area.getAreaList()) 
		{
			//System.out.println("object x: " + object.getX() + " & object y: " + object.getY());
			if (object.getX() == x && object.getY() == y)
			{
				object.distance = 0;
				return object;
			}
		}
		return null;
	}
	
//	public Area getClosestArea(String type, int guestId)
//	{
//		int distance = 0;
//		int shortestDistance = 1000;
//		int shortestDistanceObjectId = 0;
//		Area start = null;
//		
//		for(int i = 0; i < guests.size(); i++)
//		{
//			Guest g = (Guest) guests.get(i);
//			
//			//System.out.println("I'm looking for id: " + guestId);
//			//System.out.println("I'm comparing with id: " + g.getId());
//			
//			if (g.getId() == guestId)
//			{
//				start = getAreaNode(g.getX(),g.getY());
//				//System.out.println("I found startNode: " + start);
//			}
//			else
//			{
//				//System.out.println("No guest was found for id: " + guestId);
//			}
//		}
//		
//		if (type == "Restaurant")
//		{
//			for (Area object: Area.getAreaList()) 
//			{
//				if(object instanceof Restaurant) 
//				{
//					System.out.println("I found a restaurant for Dijkstra");
//					//System.out.println("###TESTVALUE### X: " + object.getX());
//					//System.out.println("###TESTVALUE### Y: " + object.getY());
//					//_ds.Dijkstra(start,object);
//					
//					if (_ds.distance < shortestDistance)
//					{
//						shortestDistance = _ds.distance;
//						shortestDistanceObjectId = object.id;
//					}
//				}
//			}
//			
//			System.out.println("Restaurant object ID: " + shortestDistanceObjectId + " is the chosen one!");
//			for (Area finalObject: Area.getAreaList()) 
//			{
//				if(finalObject instanceof Restaurant) 
//				{
//					if(finalObject.id == shortestDistanceObjectId)
//					{
//						System.out.println("selected destination X-Coord: " + finalObject.x);
//						System.out.println("selected destination Y-Coord: " + finalObject.y);
//						return finalObject;
//					}
//				}
//			}
//		}
//		
//		else if (type == "Fitness")
//		{
//			for (Area object: Area.getAreaList()) 
//			{
//				if(object instanceof Fitness) 
//				{
//					System.out.println("I found a Fitness room for Dijkstra");
//					
//					//_ds.Dijkstra(start,object);
//					
//					if (_ds.distance < shortestDistance)
//					{
//						shortestDistance = _ds.distance;
//						shortestDistanceObjectId = object.id;
//					}
//				}
//			}
//			System.out.println("Fitness object ID: " + shortestDistanceObjectId + " is the chosen room!");
//			for (Area finalObject: Area.getAreaList()) 
//			{
//				if(finalObject instanceof Fitness) 
//				{
//					if(finalObject.id == shortestDistanceObjectId)
//					{
//						System.out.println("selected destination X-Coord: " + finalObject.x);
//						System.out.println("selected destination Y-Coord: " + finalObject.y);
//						return finalObject;
//					}
//				}
//			}
//		}
//		
//		else if (type == "Cinema")
//		{
//			for (Area object: Area.getAreaList()) 
//			{
//				if(object instanceof Cinema) 
//				{
//					System.out.println("I found a Cinema for Dijkstra");
//					//_ds.Dijkstra(start,object);
//					
//					if (_ds.distance < shortestDistance)
//					{
//						shortestDistance = _ds.distance;
//						shortestDistanceObjectId = object.id;
//					}
//				}
//			}
//			
//			System.out.println("Cinema object ID: " + shortestDistanceObjectId + " is the chosen cinema!");
//			for (Area finalObject: Area.getAreaList()) 
//			{
//				if(finalObject instanceof Cinema) 
//				{
//					if(finalObject.id == shortestDistanceObjectId)
//					{
//						System.out.println("selected destination X-Coord: " + finalObject.x);
//						System.out.println("selected destination Y-Coord: " + finalObject.y);
//						return finalObject;
//					}
//				}
//			}
//		}
//		return null;
//	}
	
	private Area findClosestFitness() {
		for (Area object: Area.getAreaList()) {
			if(object instanceof Fitness) {
				return object;
			}
		}
		return null;
	}
	
	public void freeRoom(int roomId) {
		for (Area object: Area.getAreaList()) {
			if(object instanceof HotelRoom) {
				if (object.id == roomId)
				{
					object.setAvailability(true);
					System.out.println("Room ID: " + object.id + " is now available!");
					System.out.println("Room X: " + object.x + " Y: " + object.y);
					break;
				}
			}
		}
	}
	
	public int getAvailableCleaner(String type)
	{
		int availableCleanerId = 0;
		
		if (type == "CLEANING EMERGENCY")
		{
			for(int i = 0; i < cleaners.size(); i++)
			{
				Cleaner c = (Cleaner) cleaners.get(i);
				if (c.getStatus() != "CLEANING EMERGENCY")
				{
					availableCleanerId = c.getId();
					System.out.println("### TESTVALUE ### I've change availableCleanerId from 0 -> "+c.getId());
					c.setStatus("CLEANING EMERGENCY");
					break;
				}		
			}
		}
		else if (type == "CLEANING")
		{
			for(int i = 0; i < cleaners.size(); i++)
			{
				Cleaner c = (Cleaner) cleaners.get(i);
				if (c.getStatus() == "Inactive")
				{
					availableCleanerId = c.getId();
					c.setStatus("CLEANING");
				}		
			}
		}
		return availableCleanerId;
	}
	
	
	public int getRoomOfGuest(int guestId)
	{
		int guestRoomId = 0;
		
		for(int i = 0; i < guests.size(); i++)
		{
			Guest g = (Guest) guests.get(i);
			if (g.getId() == guestId)
			{
				System.out.println("Guest found for cleaning emergency");
				guestRoomId = g.getSelectedRoom();
			}
		}
		return guestRoomId;
	}
	
	public void sendGuestToFitness(int guestId) {
		for(Person guest : guests) {
			if(guest.getId() == guestId) {
				System.out.println(guest.getId());
				System.out.println("+++++++++++++++++++++++  Fitness comming in ==============================================");
				guest.currentRoute.clear();
				System.out.println("fitness: " + findClosestFitness());
				guest.getRoute(findClosestFitness());
				
				
				
				System.out.println("loop je vast?");
//				System.out.println(guest.currentRoute);
			}
  		}
	}
	
	@Override
	public void Notify(HotelEvent event) {
		String tempEvent = event.Type.toString();
		String hashmapContent = event.Data.toString();
		
		
//		//Debug purpose for loop
//		for(int i = 0; i < cleaners.size(); i++)
//		{
//			Cleaner c = (Cleaner) cleaners.get(i);
//			System.out.println("### TESTVALUE ### cleaner "+ c.getId() +" status : " + c.getStatus());
//		}
//		
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
			//System.out.println("Guests prefStars: " + prefStars);
			
			

			
			//Because this command is not running from Java FX I've added this to update UI from a different thread.
			Platform.runLater(
					  () -> {

						  selectedRoomId = getRoom(prefStarsInt);
						  
						  if (selectedRoomId != 0)
						  {
							  //System.out.println("gekozen room ID: "+selectedRoomId);
							  Person xx = PersonFactory.createPerson("Guest","Go To Room", setGuestIdValue,true,selectedRoomId,10,3);
							  guests.add(xx);
							  xx.getRoute(getRoomNode(selectedRoomId));
//							  System.out.println("room node : " + getRoomNode(selectedRoomId).id);
//							  System.out.println(" ****************************** Guest array = " + guests);
						  }
						  else
						  {
							  System.out.println("No room available, guest will leave the hotel!");
						  }
					   }
					);
			
			
			guestCounter++;
//			System.out.println("Added a guest, total guests: " + guestCounter);
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
//		else if (tempEvent == "GOTO_FITNESS")
//		{
//			String guestId;
//			int setGuestIdValue;
//			String prefStars;
//			
//			String[] splitArray = hashmapContent.split("\\s");
//			String[] splitArray2 = splitArray[1].split("=");
//			
//			//Set GuestID
//			guestId = splitArray2[0];
//			setGuestIdValue = Integer.parseInt(guestId);
//			
//			System.out.println("Guest ID for fitness: " + setGuestIdValue);
//			
//			sendGuestToFitness(setGuestIdValue);
//
////			for(Person guest : guests) {
////				if(guest.getId() == setGuestIdValue) {
////					System.out.println(guest.getId());
////					System.out.println("+++++++++++++++++++++++  Fitness comming in ==============================================");
////					guest.currentRoute.clear();
////					guest.getRoute(Area.getAreaList().get(5));
////					System.out.println("loop je vast?");
//////					System.out.println(guest.currentRoute);
////				}
////	  		}
////					
//
//		}
//		else if (tempEvent == "NEED_FOOD")
//		{
//			int guestId;
//			String[] splitArray = hashmapContent.split("=");
//			
//			if (splitArray[1].contains("}"))
//			{
//				String[] splitArray2 = splitArray[1].split("}");
//				guestId = Integer.parseInt(splitArray2[0]);
//			}
//			
//			else 
//			{
//				guestId = Integer.parseInt(splitArray[1]);
//			}
//
//			System.out.println("I'm sending a guest to the restaurant, selected guest is: " + guestId);
//			assignRoom("Restaurant", guestId);
//		}
//		
//		else if (tempEvent == "GOTO_CINEMA")
//		{
//			int guestId;
//			String[] splitArray = hashmapContent.split("=");
//			
//			if (splitArray[1].contains("}"))
//			{
//				String[] splitArray2 = splitArray[1].split("}");
//				guestId = Integer.parseInt(splitArray2[0]);
//			}
//			
//			else 
//			{
//				guestId = Integer.parseInt(splitArray[1]);
//			}
//			assignRoom("Cinema", guestId);
//			System.out.println("I'm sending a guest to the cinema, selected guest is: " + guestId);
//		}
//		
//		else if (tempEvent == "CLEANING_EMERGENCY")
//		{
//			int guestId;
//			int availableCleanerId;
//			int emergencyRoomId;
//			
//			String[] splitArray = hashmapContent.split("=");
//			
//			if (splitArray[1].contains("}"))
//			{
//				String[] splitArray2 = splitArray[1].split("}");
//				guestId = Integer.parseInt(splitArray2[0]);
//			}
//			
//			else 
//			{
//				guestId = Integer.parseInt(splitArray[1]);
//			}
//			
//			emergencyRoomId = getRoomOfGuest(guestId);
//			availableCleanerId = getAvailableCleaner("CLEANING EMERGENCY");
//			
//			if (availableCleanerId == 0)
//			{
//				System.out.println("All cleaners in emergency cleaning status already");
//			}
//			
//			System.out.println("The room of guest ID: "+guestId+" must be cleaned!");
//			System.out.println("His Room ID is: "+emergencyRoomId);
//			
//			//Send housekeeping based on above info
//			//this is endPosition for Dijkstra
//			roomToClean(emergencyRoomId);
//			
//		}
//		
//		else if (tempEvent == "EVACUATE")
//		{
//			System.out.println("I'm sending all persons to evacuate");
//		}
//		else if (tempEvent == "GODZILLA")
//		{
//			System.out.println("Godzilla event, message: " + hashmapContent);
//		}
	}
	

}
