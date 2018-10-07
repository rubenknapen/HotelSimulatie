package Managers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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

public class HotelManager implements EventLib.HotelEventListener, Observer{

	//Variables
	int guestCounter = 0;
	int selectedRoomId;
	
	public static int currentGuestAmount = 0;
	public static int currentGuestAmountInRoom = 0;
	public static int currentGuestAmountInFitness = 0;
	public static int currentGuestAmountInRestaurant = 0;
	public static int currentGuestAmountInCinema = 0;
	
	public static int currentCleanerAmount = 0;
	public static int currentCleanerAmountInCleaning = 0;
	public static int currentCleanerAmountInEmergencyCleaning = 0;

	public static SimulationTimer timer;
	//public static ArrayList<Person> guests;
	public static List<Person> cleaners =  Collections.synchronizedList(new ArrayList<Person>());
	public static List<Person> guests =  Collections.synchronizedList(new ArrayList<Person>());
	//public static ArrayList<Person> cleaners;
	ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
	
	
	//Constructor
	public HotelManager(){

		GridBuilder gridBuilder = new GridBuilder();
		gridBuilder.buildGrid();
		
			
		//Build array list for guests
//		guests = new ArrayList();
//		cleaners = new ArrayList();

		addCleaners(2);

		timer = new SimulationTimer();
		timer.addObserver(this);
		timer.setInterval(SettingBuilder.tickSpeed);
		timer.activateTimer();
			
						
				
	}
	
	public void setRealtimeStatistics()
	{
		currentGuestAmount = guests.size();
		currentGuestAmountInRoom = 0;
		currentGuestAmountInFitness = 0;
		currentGuestAmountInRestaurant = 0;
		currentGuestAmountInCinema = 0;
		
		currentCleanerAmountInEmergencyCleaning = 0;
		currentCleanerAmountInCleaning = 0;
		synchronized (guests) {
			for(Person guest : guests) 
			{
				if(guest.getStatus().equals("GO_BACK_TO_ROOM") && guest.currentRoute.isEmpty())
				{
					currentGuestAmountInRoom++;
				}
				
				else if(guest.getStatus().equals("INSIDE_FITNESS"))
				{
					currentGuestAmountInFitness++;
				}
				
				else if(guest.getStatus().equals("NEED_FOOD") && guest.currentRoute.isEmpty())
				{
					currentGuestAmountInRestaurant++;
				}
				else if(guest.getStatus().equals("GO_TO_CINEMA") && guest.currentRoute.isEmpty())
				{
					currentGuestAmountInCinema++;
				}
			}
		}
		
		currentCleanerAmount = cleaners.size();
		
		synchronized (cleaners) {
			for(Person cleaner : cleaners) 
			{
				if(cleaner.getStatus().equals("EMERGENCY"))
				{
					currentCleanerAmountInEmergencyCleaning++;
				}
				
				else if(cleaner.getStatus().equals("GOTODIRTYROOM"))
				{
					currentCleanerAmountInCleaning++;
				}
			}
		}
	}
	
	//call performAction for every object in arraylist guests
	public static void personsPerformActions(){
		synchronized (guests) {
			for(Person guest : guests) {
				guest.performAction();
			}
		}		
		synchronized (cleaners) {
			for(Person cleaner : cleaners) {
				cleaner.performAction();
			}
		}
	  }
	
	//call moveToArea for every object in arraylist guests
	public static void moveCharacters() {
		synchronized (guests) {
	  		for(Person guest : guests) {
				guest.moveToArea();
	  		}  			
		}
		synchronized (cleaners) {
			for(Person cleaner : cleaners) {
				cleaner.moveToArea();
			} 			
		}		
	}
	
	//Add a cleaner
	public void addCleaners(int amount){
		for(int i = 1; i <= amount; i++)
		{
			Person xx = PersonFactory.createPerson("Cleaner","INACTIVE",i,true,4,GridBuilder.getMaxY() - 8);
			Cleaner c = (Cleaner) xx;
			c.setId(i);
			cleaners.add(xx);
			System.out.println("Cleaner: " + c.getId() + " added!");
		}
	}
	
	//Get Area object of roomToClean (input is roomnumber)
	public Area roomToClean(int roomId){
		for (Area a : Area.getAreaList()) {
			if(a.id == roomId) {
				System.out.println("Sending housekeeping to object ID: "+ a.id);
				return a;
			}
		}
		return null;
	}
	
	//return the room object with the input of prefStars, room must be available
	public int getRoom(int prefStars){
		int starAmount = prefStars;
		boolean checkForRoom = true;
		while(checkForRoom){
			for (Area object: Area.getAreaList()) {
				if(object instanceof HotelRoom) {
					if (object.stars == starAmount && object.available == true){					
						object.setAvailability(false);
						return object.id;
					}
				}
			}
			if (starAmount < 5){
				starAmount += 1;
			} 
			else if (starAmount == 5){
				checkForRoom = false;
			}
		}
		return 0;
	}
	
	//Return area of the room (input roomnumber)
	public static Area getRoomNode(int roomId) {
		for (Area object: Area.getAreaList()) {
			if(object instanceof HotelRoom) {
				if (object.id == roomId){					
					return object;
				}
			}
		}
		return null;
	}
	
	//Return area of the room (input roomnumber) and set the availability to false
	public static Area getRoomNodeAfterCheckIn(int roomId) {
		for (Area object: Area.getAreaList()) {
			if(object instanceof HotelRoom) {
				if (object.id == roomId){					
					object.setAvailability(false);
					return object;
				}
			}
		}
		return null;
	}

	//
	public void sendGuestToFitness(int guestId, int hte) {
		String status = "GOTO_FITNESS";
		synchronized (guests) {
			for(Person guest : guests) {
				if(guest.getId() == guestId) {
					guest.currentRoute.clear();
					guest.setStatus(status);
					guest.setFitnessTickAmount(hte);
					guest.getRoute(findClosestFitness());
				}
	  		}
		}
	}
		
	private Area findClosestFitness() {
		for (Area object: Area.getAreaList()) {
			if(object instanceof Fitness) {
				return object;
			}
		}
		return null;
	}
	
	public void sendGuestToRestaurant(int guestId) {
		String status = "NEED_FOOD";
		synchronized (guests) {
			for(Person guest : guests) {
				if(guest.getId() == guestId) {
					guest.currentRoute.clear();
					guest.setStatus(status);
					guest.getRoute(findClosestRestaurant(guest));
				}
	  		}
		}
	}	
		
	private Area findClosestRestaurant(Person guest) {
		Person currentGuest = guest;

		Area closestRestaurant = null;
		int closestDistance = 100;
		
		for (Area object: Area.getAreaList()) {
			if(object instanceof Restaurant) {

				if(currentGuest.checkDistanceRestaurant(object) < closestDistance) {
					closestRestaurant = object;
					closestDistance = currentGuest.checkDistanceRestaurant(object);
					System.out.println("ClosestDistance is now: " + closestDistance + "    with id: " + object.id);
				}
				
			}
		}
		
		System.out.println("Destination id =    " + closestRestaurant.id);
		System.out.println("I'm sending a guest to the restaurant, selected guest is: " + guest.getId() + "     to restaurant: " + closestRestaurant.id);
		
		return closestRestaurant;		
	}
		
	
	public void removeGuest(int guestId, String tempEvent) {
		synchronized (guests) {
			for(Person guest : guests) {
				if(guestId == guest.getId()) {
					guest.setStatus(tempEvent);
				}
			}	
		}
	}	
	
	//add room to clean to queue
	public void addRoomToClean(int guestId) {
		synchronized (guests) {
			for(Person guest : guests) {
				if(guestId == guest.getId()) {
					Cleaner.getRoomCleaningList().add(getRoomNodeAfterCheckIn(guest.getSelectedRoom()));
				}
			}		
		}
	}
	
	//add room to clean to emergency clean queue
	public void addEmergencyRoomToClean(int guestId) {
		synchronized (guests) {
			for(Person guest : guests) {
				if(guestId == guest.getId()) {
					Cleaner.getEmergencyRoomCleaningList().add(getRoomNodeAfterCheckIn(guest.getSelectedRoom()));
					//System.out.println("Aangevuld emergency = " + Cleaner.getEmergencyRoomCleaningList());
				}
			}	
		}
	}
	
	
	//This is where we receive the event from the HotelEventManager and decides based on the event type what to do
	@Override
	public void Notify(HotelEvent event) {
		String tempEvent = event.Type.toString();
		String hashmapContent = event.Data.toString();
		
		//Check in: Create guest, assign room, add to array, get route to room
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
			
			//Because this command is not running from Java FX I've added this to update UI from a different thread.
			Platform.runLater(
					  () -> {

						  selectedRoomId = getRoom(prefStarsInt);
						  
						  if (selectedRoomId != 0)
						  {
							  Person xx = PersonFactory.createPerson("Guest","GO_BACK_TO_ROOM", setGuestIdValue,true,10,3);
							  xx.setRoomId(selectedRoomId);
							  guests.add(xx);
							  xx.getRoute(getRoomNodeAfterCheckIn(selectedRoomId));
						  }
						  else
						  {
							  System.out.println("No room available, guest will leave the hotel!");
						  }
					   }
					);
			
			
			guestCounter++;
		} 
		
		//Check out for guest, send cleaner to room, remove the guest
		else if (tempEvent == "CHECK_OUT"){
			int guestId;
			String[] splitArray = hashmapContent.split("=");
			
			if (splitArray[1].contains("}")) {
				String[] splitArray2 = splitArray[1].split("}");
				guestId = Integer.parseInt(splitArray2[0]);
			}		
			else {
				guestId = Integer.parseInt(splitArray[1]);
			}
			addRoomToClean(guestId);
			removeGuest(guestId, tempEvent);
		}
		
		//Send guest to fitness
		else if (tempEvent == "GOTO_FITNESS"){

			String guestId;
			int setGuestIdValue;
			int HTE;
			String prefStars;
			
			String[] splitArray = hashmapContent.split("\\s");
			String[] splitArray2 = splitArray[1].split("=");
			
			//Set GuestID
			guestId = splitArray2[0];
			setGuestIdValue = Integer.parseInt(guestId);
			HTE = Integer.parseInt(splitArray2[1]);
			
			sendGuestToFitness(setGuestIdValue, HTE);

		}
		
		//Send guest to restaurant
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
			
			sendGuestToRestaurant(guestId);


		}
	
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
		//Add room to clean to the queue.
		else if (tempEvent == "CLEANING_EMERGENCY")
		{
			int guestId;
			int availableCleanerId;
			int emergencyRoomId;
			
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
			
			addEmergencyRoomToClean(guestId);

			System.out.println("Emergency voor guest: " + guestId);
			
		}
		
//		else if (tempEvent == "EVACUATE")
//		{
//			System.out.println("I'm sending all persons to evacuate");
//		}
//		else if (tempEvent == "GODZILLA")
//		{
//			System.out.println("Godzilla event, message: " + hashmapContent);
//		}
		
	}
	@Override
	public void update(Observable o, Object arg) {
		moveCharacters();
		personsPerformActions();
		setRealtimeStatistics();		
	}
}
