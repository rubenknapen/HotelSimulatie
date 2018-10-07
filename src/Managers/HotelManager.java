package Managers;

import java.util.ArrayList;
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

	public static SimulationTimer timer;
	public static ArrayList<Person> guests;
	public static ArrayList<Person> cleaners;
	ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
	
	
	//Constructor
	public HotelManager(){

		GridBuilder gridBuilder = new GridBuilder();
		gridBuilder.buildGrid();
		
			
		//Build array list for guests
		guests = new ArrayList();
		cleaners = new ArrayList();

		addCleaners(2);

		// testing purpose
		
		
//		Person xx = PersonFactory.createPerson("Guest","Go To Room", 133,true,36,5,5);
//		guests.add(xx);
		
//		Person xx2 = PersonFactory.createPerson("Guest","Go To Room", 134,true,30,6,4);
//		guests.add(xx2);
//		xx2.getRoute(Area.getAreaList().get(29));
////		xx.getRoute(findClosestFitness());
//		
//		for(Person guest : guests) {
//			if(guest.getId() == 133) {
//				System.out.println(guest.getId());
//				System.out.println("+++++++++++++++++++++++  Fitness comming in ==============================================");
//				guest.currentRoute.clear();
//				guest.getRoute(findClosestFitness());
//				System.out.println("loop je vast?");
//	//			System.out.println(guest.currentRoute);
//			}
//		}
		
		timer = new SimulationTimer();
		timer.addObserver(this);
		timer.setInterval(SettingBuilder.tickSpeed);
		timer.activateTimer();
			
						
				
	}
	public static void personsPerformActions(){
		for(Person guest : guests) {
			guest.performAction();
		}
		for(Person cleaner : cleaners) {
			cleaner.performAction();
		}
	}
	
	public static void moveCharacters() {
  		for(Person guest : guests) {
			guest.moveToArea();
  		}  		
  		for(Person cleaner : cleaners) {
  			cleaner.moveToArea();
  		}
	}
	
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
	
	public Area roomToClean(int roomId){
		for (Area a : Area.getAreaList()) {
			if(a.id == roomId) {
				System.out.println("Sending housekeeping to object ID: "+ a.id);
				return a;
			}
		}
		return null;
	}
	
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

	
	public void sendGuestToFitness(int guestId, int hte) {
		String status = "GOTO_FITNESS";
		for(Person guest : guests) {
			if(guest.getId() == guestId) {
				guest.currentRoute.clear();
				guest.setStatus(status);
				guest.setFitnessTickAmount(hte);
				guest.getRoute(findClosestFitness());
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
		for(Person guest : guests) {
			if(guest.getId() == guestId) {
				guest.currentRoute.clear();
				guest.setStatus(status);
				guest.getRoute(findClosestRestaurant(guest));
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
		for(Person guest : guests) {
			if(guestId == guest.getId()) {
				guest.setStatus(tempEvent);
			}
		}	
	}	
	
	public void addRoomToClean(int guestId) {
		for(Person guest : guests) {
			if(guestId == guest.getId()) {
				Cleaner.getRoomCleaningList().add(getRoomNodeAfterCheckIn(guest.getSelectedRoom()));
				//System.out.println("Aangevuld normaal = " + Cleaner.getRoomCleaningList());
			}
		}		
	}
	
	public void addEmergencyRoomToClean(int guestId) {
		for(Person guest : guests) {
			if(guestId == guest.getId()) {
				Cleaner.getEmergencyRoomCleaningList().add(getRoomNodeAfterCheckIn(guest.getSelectedRoom()));
				//System.out.println("Aangevuld emergency = " + Cleaner.getEmergencyRoomCleaningList());
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
			
			//System.out.println("Guests id: " + guestId);
			//System.out.println("Guests prefStars: " + prefStars);
			
			

			
			//Because this command is not running from Java FX I've added this to update UI from a different thread.
			Platform.runLater(
					  () -> {

						  selectedRoomId = getRoom(prefStarsInt);
						  
						  if (selectedRoomId != 0)
						  {
							  //System.out.println("gekozen room ID: "+selectedRoomId);
							  Person xx = PersonFactory.createPerson("Guest",tempEvent, setGuestIdValue,true,10,3);
							  xx.setRoomId(selectedRoomId);
							  guests.add(xx);
							  xx.getRoute(getRoomNodeAfterCheckIn(selectedRoomId));
//							  System.out.println("Mijn kamer =========== " + getRoomNodeAfterCheckIn(selectedRoomId).id);
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
	}
}
