package Persons;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Areas.Area;
import EventLib.HotelEvent;
import Managers.GridBuilder;
import Managers.SettingBuilder;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public abstract class Person
{	
	ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
	public ArrayList<Area> currentRoute = new ArrayList<Area>();
	public static ArrayList<Area> roomCleaningList = new ArrayList<Area>();
	public ArrayList<Area> restaurantsToCheck = new ArrayList<Area>();
	protected ImageView personImageView;
	protected boolean visibility = true; // Hide or shows the person visually
	private int exitCounter = 0;
	protected int id;
	public int roomId;
	protected int stairsWaitTime = 0;
	protected int x = 10; // x coordinate
	protected int y = 9; // y coordinate
	protected int translateXVal;
	protected int translateYVal;
	protected int fitnessTickAmount;
	protected boolean moveAllowed;
	protected String status;

	
	//All functions
	
	public void setSprite(FileInputStream sprite){
        Image personImage = new Image(sprite);
        personImageView = new ImageView();
        personImageView.setFitHeight(21);
        personImageView.setFitWidth(16); 
        personImageView.setImage(personImage);
    }
	
	public synchronized void getRoute(Area destinationArea){	
		currentRoute.clear();
		ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
		getCurrentPosition().distance = 0;	
	    currentRoute = _ds.Dijkstra(getCurrentPosition(), destinationArea);
	    for(Area a : currentRoute) 
	    {
	    	//
	    }
	    clearDistances();		
	}

	protected void clearDistances() {
		for (Area a : Area.getAreaList()) {
			a.distance = Integer.MAX_VALUE;;
			a.latest = null;
		}		
	}
	
	public int checkDistanceRestaurant(Area destinationArea){
		return 0;
	}
	
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
		System.out.println("Je bent er voorbij!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("person x: " + x + "  y: " + y);
		return null;
	}
	
	public Area getLastArea() {
		if(currentRoute.size() == 0) {
			return null;
		}else {
			return currentRoute.get(currentRoute.size() - 1) ;
		}
	}
	
	public void moveToArea(){
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
			else if(getLastArea().dimensionW > 6) {
				if((status.equals("LEAVE_HOTEL") || status.equals("GO_OUTSIDE")))
				{
					if (x > 2)
					{
					x--;
					translateXVal -= GridBuilder.colSize;
					personImageView.setTranslateX(translateXVal);
					
					exitCounter++;
					
					}
					else if (x <= 2 && status.equals("LEAVE_HOTEL"))
					{
						currentRoute.remove(getLastArea());
					}
				} 
				
				else {
					x++;
					translateXVal += GridBuilder.colSize;
					personImageView.setTranslateX(translateXVal);
				}

				if(getLastArea().getXEnd() == x) {
					//x = getLastArea().getXEnd();
					if(status.equals("LEAVE_HOTEL"))
						{
							//Do nothing
						}
					else
					{
						currentRoute.remove(getLastArea());
					}
				}
			}
			else if( ((getLastArea().getX() - x == 0) && getLastArea().getRealY() == y ) && getNextArea().getXEnd() - x == -1) {
				if(status.equals("LEAVE_HOTEL") || status.equals("GO_OUTSIDE"))
				{
					//Do nothing
				}
				else
				{
					currentRoute.remove(getLastArea());
				}
			}
			else if((getLastArea().getX() - x == 1) && getLastArea().getRealY() == y ) {
				
				
				// Right movement
							
				x = getLastArea().getX();
				translateXVal += GridBuilder.colSize;
				personImageView.setTranslateX(translateXVal);
				if(getLastArea().dimensionW > 1) {
					
					if(currentRoute.size() == 1) {
						currentRoute.remove(getLastArea());
					}
				} else {
					currentRoute.remove(getLastArea());
				}
				
			} 
			else if((getLastArea().getXEnd() - x == 1) && getLastArea().getRealY() == y ) {
				
				// Right movement
				
				
				x = getLastArea().getXEnd();
				translateXVal += GridBuilder.colSize;
				personImageView.setTranslateX(translateXVal);
				currentRoute.remove(getLastArea());
				
				
			} else if((getLastArea().getXEnd() - x == -1) && getLastArea().getRealY() == y ) {
				
				// Left movement
				
				x = getLastArea().getXEnd();
				translateXVal -= GridBuilder.colSize;
				personImageView.setTranslateX(translateXVal);
				if(getLastArea().dimensionW > 1) {
					
				} else {
					currentRoute.remove(getLastArea());
				}
			
			} 
			else if ((getLastArea().getX() - x == -1) && getLastArea().getRealY() == y ) {
	
				// Left movement
				
				x = getLastArea().getX();
				translateXVal -= GridBuilder.colSize;
				personImageView.setTranslateX(translateXVal);	
				currentRoute.remove(getLastArea());
	
			}
			else if(((getLastArea().getXEnd() - x == 0) && getLastArea().getRealY() == y ) && x > getLastArea().getX()) {
				currentRoute.remove(getLastArea());
			}
			else if(getLastArea().getY() != y ) {
				
				// Up and down movement
				
				if(getLastArea().getY() - y == -1) {
					y = getLastArea().getY();
					translateYVal -= GridBuilder.rowSize;
					personImageView.setTranslateY(translateYVal);
					currentRoute.remove(getLastArea());
				} else {
					y = getLastArea().getY();
					translateYVal += GridBuilder.rowSize;
					personImageView.setTranslateY(translateYVal);
					currentRoute.remove(getLastArea());				
				}
				
			}
			else {
				currentRoute.remove(getLastArea());
			}
			
			if(exitCounter == 9)
			{
				currentRoute.clear();
				if((status.equals("LEAVE_HOTEL")) || status.equals("GO_OUTSIDE"))
						{
							//moveAllowed = false;
						}
			}
		}
	}	
	
	public Area getNextArea() {

		if(currentRoute == null){
			return null;
		}
		if(currentRoute.size() == 0) {
			return null;
		}else {
			int minusFactor = 2;
			int currentSize = currentRoute.size();
			
			if (currentSize == 1)
			{
				minusFactor = 1;
			}
			return currentRoute.get(currentRoute.size() - minusFactor);
		}
	}
	
	public void setInvisible(){
		Platform.runLater(
				  () -> {
					personImageView.setVisible(false);
					visibility = false;
				  });
	}
	
	public void moveAllowed(){
		if(moveAllowed)
		for (Area object: Area.getAreaList()) {
			if(object.getX() == x && object.getRealY() == y) {
				if(object.areaType == "Stairs"){
					moveAllowed = false;
				}
			}
		}
		else if(!moveAllowed){
			if(stairsWaitTime == SettingBuilder.getStairTime()){
				moveAllowed = true;
				stairsWaitTime = 0;
			}
		}
	}	
	
	public int getId(){
		return id;
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
	
	public void clearRoute(){
		currentRoute.clear();
	}
	
	public void setId(int guestId){
		this.id = guestId;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	public int getFitnessTickAmount() {
		return fitnessTickAmount;
	}

	public void setFitnessTickAmount(int fitnessTickAmount) {
		this.fitnessTickAmount = fitnessTickAmount;
	}
	
	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}
	
	public void performAction() {
		
	}

	public void setRoomId(int roomId) {
	
	}
	
	public int getSelectedRoom(){
		return 0;
	}
	
	public void getLobbyRoute() {
		//
	}

	public boolean getAvailability() {
		return false;
	}

	public void setAvailability(boolean b) {
		// 
	}

	public void setVisible(){
		Platform.runLater(
		  () -> {
			  	personImageView.setVisible(true);
				visibility = true;
		  });
	}
}
