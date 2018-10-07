package Persons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Areas.Area;
import EventLib.HotelEvent;
import Managers.GridBuilder;
import Managers.SettingBuilder;
import Scenes.SimulationScene;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cleaner extends Person{

	//Variables
	private String status; // Status of the person "evacuate, check in, etc."
	private boolean visibility = true; // Hide or shows the person visually
	private boolean moveAllowed = true;
	private int cleaningTime = SettingBuilder.cleaningTime;
	private int cleaningTimeRemaining;
	private int waitInFrontOfDoor = 0;
	private int x; // x coordinate
	private int y; // y coordinate
	private int id;
	private ImageView cleanerImageView;
	private int stairsWaitTime = 0;
	private int translateXVal;
	private int translateYVal;
	private Area currentRoomToClean;
	public static ArrayList<Area> roomCleaningList = new ArrayList<Area>();
	public static ArrayList<Area> EmergencyRoomCleaningList = new ArrayList<Area>();
	
	//Constructor
	public Cleaner(String status, boolean visibility, int x, int y){
		
		this.setStatus(status);
		this.setVisibility(visibility);	
		this.setX(x);
		this.setY(y);
		this.setCleaningTime();

		// Get the right image depending on dimensions
		try {
			setSprite(new FileInputStream("src/Images/cleaner.png"));	
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		
		// Paint the guest on the grid
		GridBuilder.grid.add(this.cleanerImageView,x,y);
		GridBuilder.grid.setHalignment(this.cleanerImageView, HPos.CENTER);
		GridBuilder.grid.setValignment(this.cleanerImageView, VPos.BOTTOM);
	}
	
	public void setSprite(FileInputStream sprite){
		
        Image cleanerImage = new Image(sprite);
        cleanerImageView = new ImageView();
        cleanerImageView.setFitHeight(21);
        cleanerImageView.setFitWidth(16); 
        cleanerImageView.setImage(cleanerImage);
    }
	
	//Get route to inserted Area
	public void getRoute(Area destinationArea){	
		ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		getCurrentPosition().distance = 0;	
	    currentRoute = _ds.Dijkstra(getCurrentPosition(), destinationArea);
	    clearDistances();
	}
	
	private void clearDistances() {
		for (Area a : Area.getAreaList()) {
			a.distance = Integer.MAX_VALUE;;
			a.latest = null;
		}		
	}
	
	//return area object of current position
	public Area getCurrentPosition() {
		for (Area object: Area.getAreaList()) {
			if(object.getX() == x && object.getRealY() == y) {
				return object;
			} else if(object.getXEnd() == x && object.getRealY() == y) {
				translateXVal -= GridBuilder.colSize;
				x = object.getX();
				return object;
			}
		}
		return null;
	}
	
	public void moveAllowed()
	{
		if(moveAllowed)
		for (Area object: Area.getAreaList()) 
		{
			if(object.getX() == x && object.getRealY() == y) 
			{
				if(object.areaType == "Stairs")
				{
					moveAllowed = false;
				}
			}
		}
		else if(!moveAllowed)
		{
			if(stairsWaitTime == SettingBuilder.getStairTime())
			{
				moveAllowed = true;
				stairsWaitTime = 0;
			}
		}
	}

	
	public void moveToArea()
	{
		moveAllowed();
		if(!moveAllowed)
		{
			stairsWaitTime++;
		}
		
		else if (moveAllowed)
		{
			if(getLastArea() == null) {
				//Reached end of route
			} 
			else if((getLastArea().getX() - x == 1) && getLastArea().getRealY() == y ) {
				
				// Right movement
							
				x = getLastArea().getX();
				translateXVal += GridBuilder.colSize;
				cleanerImageView.setTranslateX(translateXVal);
				if(getLastArea().dimensionW > 1) {
					
				} else {
					currentRoute.remove(getLastArea());
				}
			} 
			else if((getLastArea().getXEnd() - x == 1) && getLastArea().getRealY() == y ) {
				
				// Right movement
				
				x = getLastArea().getXEnd();
				translateXVal += GridBuilder.colSize;
				cleanerImageView.setTranslateX(translateXVal);
				currentRoute.remove(getLastArea());
			} else if((getLastArea().getXEnd() - x == -1) && getLastArea().getRealY() == y ) {
				
				// Left movement
				
				x = getLastArea().getXEnd();
				translateXVal -= GridBuilder.colSize;
				cleanerImageView.setTranslateX(translateXVal);
				if(getLastArea().dimensionW > 1) {
					
				} else {
					currentRoute.remove(getLastArea());
				}
			
			} 
			else if ((getLastArea().getX() - x == -1) && getLastArea().getRealY() == y ) {
	
				// Left movement
				
				x = getLastArea().getX();
				translateXVal -= GridBuilder.colSize;
				cleanerImageView.setTranslateX(translateXVal);	
				currentRoute.remove(getLastArea());
	
			}
			else if(((getLastArea().getXEnd() - x == 0) && getLastArea().getRealY() == y ) && x > getLastArea().getX()) {
				//System.out.println("Ik loop naar rechts 3");
				currentRoute.remove(getLastArea());
			}
			else if(getLastArea().getY() != y ) {
				
				// Up and down movement
				
				if(getLastArea().getY() - y == -1) {
					//System.out.println("Ik loop naar boven");
					y = getLastArea().getY();
					translateYVal -= GridBuilder.rowSize;
					cleanerImageView.setTranslateY(translateYVal);
					currentRoute.remove(getLastArea());
				} else {
					//System.out.println("Ik loop naar beneden");
					y = getLastArea().getY();
					translateYVal += GridBuilder.rowSize;
					cleanerImageView.setTranslateY(translateYVal);
					currentRoute.remove(getLastArea());				
				}
				
			}
			else {
				currentRoute.remove(getLastArea());
			}		
		}
	}		
	
	public Area getLastArea() {
		if(currentRoute.size() == 0) {
			return null;
		}else {
			return currentRoute.get(currentRoute.size() - 1) ;
		}
	}
	
	//Check if there is an emergency call open, otherwise check normal cleaning
	private void checkCleaningList() {
		if (!EmergencyRoomCleaningList.isEmpty()){
			assignEmergencyRoomToClean(EmergencyRoomCleaningList.get(0));
		}
		else if(roomCleaningList.isEmpty()) {
			//System.out.println("er zit niks in de cleaning list");
		} else {
			assignRoomToClean(roomCleaningList.get(0));
		}
	}
	
	private void assignEmergencyRoomToClean(Area roomToClean) {
		currentRoomToClean = roomToClean;
		status = "EMERGENCY";
		getRoute(roomToClean);
		EmergencyRoomCleaningList.remove(0);
	}
	
	private void assignRoomToClean(Area roomToClean) {
		currentRoomToClean = roomToClean;
		status = "GOTODIRTYROOM";
		getRoute(roomToClean);
		roomCleaningList.remove(0);
		//System.out.println("minus normaal = " + Cleaner.getRoomCleaningList());
	}
	
	//Check the status and based on that perform the corresponding action
	@Override
	public void performAction()
	{
		if(status.equals("INACTIVE")){
			checkCleaningList();
		} if(status.equals("INACTIVE") && currentRoute.isEmpty())
		{
			checkCleaningList();
		}
		else if(status.equals("GOTODIRTYROOM") && currentRoute.isEmpty()){
			cleanRoom();
		}
		else if(status.equals("EMERGENCY") && currentRoute.isEmpty()){
			cleanRoom();
		}
		
	}

	//Functions
	public void cleanRoom(){
		if(visibility){
			if (waitInFrontOfDoor == 0){
				waitInFrontOfDoor++;
			}
			else if (waitInFrontOfDoor == 1){
				setInvisible();
				cleaningTimeRemaining = cleaningTime;
			}
		}
		else if (!visibility){
			if(cleaningTimeRemaining == 0){
				currentRoomToClean.setAvailability(true);
				setVisible();
				getLobbyRoute();
			}
			else {
				cleaningTimeRemaining -= 1;
				System.out.println("Cleaning time remaining: "+cleaningTimeRemaining);
			}
		}
	}
	
	public void setVisible(){
		Platform.runLater(
				  () -> {
						cleanerImageView.setVisible(true);
						visibility = true;
				  });
	}
	
	public void setInvisible(){
		Platform.runLater(
				  () -> {
					cleanerImageView.setVisible(false);
					visibility = false;
				  });
	}

	public void getLobbyRoute(){
		getRoute(Area.getAreaList().get(49));
		setStatus("INACTIVE");
	}
	
	@Override
	public void evacuate() {
		// TODO Auto-generated method stub
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getId()
	{
		return id;
	}

	public int getX() 
	{
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public int getY() 
	{
		return y;
	}

	public void setY(int y) 
	{
		this.y = y;
	}
	
	public static ArrayList<Area> getRoomCleaningList() 
	{
		return roomCleaningList;
	}

	public void setRoomCleaningList(ArrayList<Area> roomCleaningList) 
	{
		this.roomCleaningList = roomCleaningList;
	}

	public static ArrayList<Area> getEmergencyRoomCleaningList() 
	{
		return EmergencyRoomCleaningList;
	}

	public static void setEmergencyRoomCleaningList(ArrayList<Area> roomEmergencyCleaningList) 
	{
		Cleaner.EmergencyRoomCleaningList = roomEmergencyCleaningList;
	}
	
	private void setCleaningTime()
	{
		cleaningTime = SettingBuilder.getCleaningTime();
	}
	
}
