package Persons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Areas.Area;
import Areas.Fitness;
import Areas.Stairway;
import EventLib.HotelEvent;
import Managers.GridBuilder;
import Managers.HotelManager;
import Managers.SettingBuilder;
import Scenes.SimulationScene;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Guest extends Person{

	//Variables
	private String status; // Status of the person "evacuate, check in, etc."
	private boolean visibility = true; // Hide or shows the person visually 
	private boolean moveAllowed = true;
	private int x = 10; // x coordinate
	private int y = 9; // y coordinate
	private ImageView guestImageView;
	public int roomId;
	private int stairsWaitTime = 0;
	private int id;
	private int translateXVal;
	private int translateYVal;
	private int fitnessTickAmount;
	private int queueTime = 10;
	public ArrayList<Area> restaurantsToCheck = new ArrayList<Area>();
	
	//Constructor
	public Guest(String status, int id, boolean visibility, int x, int y)
	{
		this.id = id;
		this.setStatus(status);
		this.setVisibility(visibility);
		this.setVisible();	
		this.setX(x);
		this.setY(y);

		// Get the right image depending on dimensions
		try 
		{
			setSprite(new FileInputStream("src/Images/guest.png"));	
        } catch (FileNotFoundException e) 
		{
            e.printStackTrace();
        }
		
		// Paint the guest on the grid
		
		GridBuilder.grid.add(this.guestImageView,x,y);
		GridBuilder.grid.setHalignment(this.guestImageView, HPos.CENTER);
		GridBuilder.grid.setValignment(this.guestImageView, VPos.BOTTOM);
		}
	
		public void setSprite(FileInputStream sprite)
		{
		
	        Image guestImage = new Image(sprite);
	        guestImageView = new ImageView();
	    	guestImageView.setFitHeight(21);
	    	guestImageView.setFitWidth(16); 
	    	guestImageView.setImage(guestImage);
    	
		}
	
	//Functions
	
	public void getRoute(Area destinationArea){	
		currentRoute.clear();
		ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		getCurrentPosition().distance = 0;	
	    currentRoute = _ds.Dijkstra(getCurrentPosition(), destinationArea);    
	    clearDistances();		
	}
	
	public int checkDistanceRestaurant(Area destinationArea){	
		ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		getCurrentPosition().distance = 0;	
	    _ds.Dijkstra(getCurrentPosition(), destinationArea);
	    int foundDistance = destinationArea.distance;
	    clearDistances();		
	    return foundDistance;
	}	
	
	private void clearDistances() {
		for (Area a : Area.getAreaList()) {
			a.distance = Integer.MAX_VALUE;;
			a.latest = null;
		}		
	}
	
	public Area getCurrentPosition() {
		for (Area object: Area.getAreaList()) {
			if(object.getX() == x && object.getRealY() == y) {
				return object;
			} 
			else if(object.getXEnd() == x && object.getRealY() == y) {
				translateXVal -= GridBuilder.colSize;
				x = object.getX();
				return object;
			}
		}
		return null;
	}
	
	//Check the current status and based on the perform the corresponding action
	@Override
	public void performAction() 
	{
		if(status.equals("GOTO_FITNESS")) {
			setVisible();
			if(currentRoute.isEmpty()) {
				setStatus("INSIDE_FITNESS");
			}
		}
		if(status.equals("INSIDE_FITNESS")) {
			if(fitnessTickAmount == 0) {
				getRoute(HotelManager.getRoomNode(roomId));
				System.out.println("Next room = " + getNextArea().id);
				setStatus("GO_BACK_TO_ROOM");
				setVisible();
			} else {
				setInvisible();
				fitnessTickAmount--;
			}
		}
		if(status.equals("GO_BACK_TO_ROOM") && currentRoute.isEmpty()) {
			setInvisible();
		} 
		if(status.equals("CHECK_OUT") ) {		
			setVisible();
			getLobbyRoute();
			setStatus("LEAVE_HOTEL");
		}			
		if(status.equals("LEAVE_HOTEL") && currentRoute.isEmpty()) {
			setStatus("LEFT_HOTEL");
			setInvisible();
		}	
		if(status.equals("NEED_FOOD")) {
		setVisible();
		System.out.println("Array's zijn: " + restaurantsToCheck);
			if(status.equals("NEED_FOOD")  && currentRoute.isEmpty()) {
				setStatus("CHECK_RESTAURANT_QUEUE");	
			}
		}
		if(status.equals("CHECK_RESTAURANT_QUEUE")) {
			checkRestaurantQueue();
		}
		if(status.equals("IN_RESTAURANT")) {
			System.out.println("Ik zit in een restaurant!");
		}
		if(status.equals("IN_QUEUE")) {
			System.out.println("Ik kan het restaurant niet in!!!!!!!!!!!!!!!!!!!");
			checkRestaurantQueue();
			queueTime--;
			if(queueTime < 0) {
				goToOtherRestaurant();
			}
		}		
		if(status.equals("GO_TO_CINEMA"))
		{
			setVisible();
		}
	}
	
	
	private void goToOtherRestaurant() {
		
	}

	private void checkRestaurantQueue() {
		if(getCurrentPosition().capacity > 0) {
			setStatus("IN_RESTAURANT");
			getCurrentPosition().capacity--;
		} else {
			setStatus("IN_QUEUE");
		}
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
			if(stairsWaitTime == SettingBuilder.getStairTime()-1)
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
			if(getLastArea() == null) 
			{
				//Reached end of route
			} 
//			else if( ((getLastArea().getX() - x == 0) && getLastArea().getRealY() == y ) && getNextArea().getXEnd() - x == -1) {
//				currentRoute.remove(getLastArea());
//			}
			else if((getLastArea().getX() - x == 1) && getLastArea().getRealY() == y ) {
				
				// Right movement
							
				x = getLastArea().getX();
				translateXVal += GridBuilder.colSize;
				guestImageView.setTranslateX(translateXVal);
				if(getLastArea().dimensionW > 1) {
					
					if(currentRoute.size() == 1) {
						currentRoute.remove(getLastArea());
					}
				} else {
					currentRoute.remove(getLastArea());
				}
				
				System.out.println("rechts 1");
			} 
			else if((getLastArea().getXEnd() - x == 1) && getLastArea().getRealY() == y ) {
				
				// Right movement
				
				
				x = getLastArea().getXEnd();
				translateXVal += GridBuilder.colSize;
				guestImageView.setTranslateX(translateXVal);
				currentRoute.remove(getLastArea());
				
				System.out.println("rechts 2");
				
			} else if((getLastArea().getXEnd() - x == -1) && getLastArea().getRealY() == y ) {
				
				// Left movement
				
				x = getLastArea().getXEnd();
				translateXVal -= GridBuilder.colSize;
				guestImageView.setTranslateX(translateXVal);
				if(getLastArea().dimensionW > 1) {
					
				} else {
					currentRoute.remove(getLastArea());
				}
			
			} 
			else if ((getLastArea().getX() - x == -1) && getLastArea().getRealY() == y ) {
	
				// Left movement
				
				x = getLastArea().getX();
				translateXVal -= GridBuilder.colSize;
				guestImageView.setTranslateX(translateXVal);	
				currentRoute.remove(getLastArea());
	
			}
			else if(((getLastArea().getXEnd() - x == 0) && getLastArea().getRealY() == y ) && x > getLastArea().getX()) {
				System.out.println("rechts 3");
				currentRoute.remove(getLastArea());
			}
			else if(getLastArea().getY() != y ) {
				
				// Up and down movement
				
				if(getLastArea().getY() - y == -1) {
					y = getLastArea().getY();
					translateYVal -= GridBuilder.rowSize;
					guestImageView.setTranslateY(translateYVal);
					currentRoute.remove(getLastArea());
				} else {
					y = getLastArea().getY();
					translateYVal += GridBuilder.rowSize;
					guestImageView.setTranslateY(translateYVal);
					currentRoute.remove(getLastArea());				
				}
				
			}
			else {
				//System.out.println("Ik voer deze uit");
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
	public Area getNextArea() {
		if(currentRoute.size() == 0) {
			return null;
		}else {
			return currentRoute.get(currentRoute.size() - 2) ;
		}
	}
	
	public void getLobbyRoute(){
		getRoute(Area.getAreaList().get(39));
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int guestId)
	{
		this.id = guestId;
	}
			
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public int getSelectedRoom()
	{
		return roomId;
	}

	public void setElevatorDirection(){
		
	}
	
	public void setElevatorFloor(){
		
	}
	
	public void die(){
		
	}
	
	public int getFitnessTickAmount() {
		return fitnessTickAmount;
	}

	public void setFitnessTickAmount(int fitnessTickAmount) {
		this.fitnessTickAmount = fitnessTickAmount;
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
	
	public void setVisible()
	{
		Platform.runLater(
				  () -> {
						guestImageView.setVisible(true);
						visibility = true;
				  });
	}
	
	public void setInvisible()
	{
		Platform.runLater(
				  () -> {
					guestImageView.setVisible(false);
					visibility = false;
				  });
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
