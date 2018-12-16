package Persons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Areas.Area;
import Areas.Cinema;
import Areas.Fitness;
import Areas.Lobby;
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
	private boolean available = true;
	public int roomId;
	private int queueTime = 10;
	public ArrayList<Area> restaurantsToCheck = new ArrayList<Area>();
	
	/**
	 * Constructor that builds a Guest.
	 * @param status Is his current activate status.
	 * @param id Is his unique identificationNumber.
	 * @param visibility Is his current activate visiblity status.
	 * @param x Is his current value for his position on the grid (x).
	 * @param y Is his current value for his position on the grid (y).
	 */
	public Guest(String status, int id, boolean visibility, int x, int y)
	{
		this.id = id;
		this.setStatus(status);
		this.setVisibility(visibility);
		this.setVisible();	
		this.setX(x);
		this.setY(y);

		// Get the right image depending on dimensions
		try {
			setSprite(new FileInputStream("src/Images/guest.png"));	
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		
		// Paint the guest on the grid
		GridBuilder.grid.add(this.personImageView,x,y);
		GridBuilder.grid.setHalignment(this.personImageView, HPos.CENTER);
		GridBuilder.grid.setValignment(this.personImageView, VPos.BOTTOM);
	}
	
	//Functions
	
	public int checkDistanceArea(Area destinationArea){	
		ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		getCurrentPosition().distance = 0;	
	    _ds.Dijkstra(getCurrentPosition(), destinationArea);
	    int foundDistance = destinationArea.distance;
	    clearDistances();		
	    return foundDistance;
	}	
	
	//Check the current status and based on the perform the corresponding action
	@Override
	public void performAction() 
	{
		if(status.equals("GO_OUTSIDE"))
			//
			if(currentRoute.isEmpty()) 
			{
				setInvisible();
				setStatus("EVACUATED");
			}
		
		if(status.equals("GOTO_CINEMA"))
		{
			setVisible();
			if(currentRoute.isEmpty()) 
			{
				
				setStatus("INSIDE_CINEMA");
				setInvisible();
			}
		}
		
		if(status.equals("INSIDE_CINEMA"))
		{
			if(HotelManager.movieTimeRemaining == 0)
			{
				getRoute(HotelManager.getRoomNode(roomId));
				setStatus("GO_BACK_TO_ROOM");
				setVisible();
			}
		}
		
		if(status.equals("GOTO_FITNESS")) {
			setVisible();
			if(currentRoute.isEmpty()) {
				setStatus("INSIDE_FITNESS");
			}
		}
		if(status.equals("INSIDE_FITNESS")) {
			if(fitnessTickAmount == 0) {
				getRoute(HotelManager.getRoomNode(roomId));
				setStatus("GO_BACK_TO_ROOM");
				setVisible();
			} else {
				setInvisible();
				fitnessTickAmount--;
			}
		}
		
		if (status.equals("REENTERED") && currentRoute.isEmpty()) 
		{
			getRoute(HotelManager.getRoomNode(roomId));
			setStatus("GO_BACK_TO_ROOM");
		}
		
		if(status.equals("GO_BACK_TO_ROOM") && currentRoute.isEmpty()) {
			setInvisible();
		} 
		if(status.equals("CHECK_OUT") ) {		
			setVisible();
			getLobbyRoute();
			setStatus("LEAVE_HOTEL");
			HotelManager.guestCounter--;
		}			
		if(status.equals("LEAVE_HOTEL") && currentRoute.isEmpty()) {
			setStatus("LEFT_HOTEL");
			setInvisible();
		}	
		if(status.equals("NEED_FOOD")) {
		setVisible();
			if(status.equals("NEED_FOOD")  && currentRoute.isEmpty()) {
				setStatus("CHECK_RESTAURANT_QUEUE");	
			}
		}
		if(status.equals("CHECK_RESTAURANT_QUEUE")) {
			checkRestaurantQueue();
		}
		if(status.equals("IN_RESTAURANT")) 
		{
			//
		}
		if(status.equals("IN_QUEUE")) {
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
		
	private void goToOtherRestaurant() 
	{
		//
	}

	private void checkRestaurantQueue() {
		if(getCurrentPosition().capacity > 0) {
			setStatus("IN_RESTAURANT");
			getCurrentPosition().capacity--;
		} else {
			setStatus("IN_QUEUE");
		}
	}

	public void getLobbyRoute()
	{
		for (Area object: Area.getAreaList()) {
			if(object instanceof Lobby) {
				//
			}
			getRoute(Area.getAreaList().get(object.id-1));
		}
	}
			
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	public int getSelectedRoom(){
		return roomId;
	}
	
}
