package Persons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import Areas.Area;
import Areas.Stairway;
import EventLib.HotelEvent;
import Managers.GridBuilder;
import Scenes.SimulationScene;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Guest extends Person{

	//Variables
	private String status; // Status of the person "evacuate, check in, etc."
	private boolean visibility = true; // Hide or shows the person visually 
	private int x = 10; // x coordinate
	private int y = 9; // y coordinate
	private ImageView guestImageView;
	private int roomId;
	private int id;
	private int translateXVal;
	private int translateYVal;
	
	//Constructor
	public Guest(String status, boolean visibility, int roomId, int x, int y)
	{
		this.setStatus(status);
		this.setVisibility(visibility);
		this.setSelectedRoom(roomId);		
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
		getCurrentPosition().distance = 0;
	    currentRoute = _ds.Dijkstra(getCurrentPosition(), destinationArea);
	}
	
	public Area getCurrentPosition() {
		for (Area object: Area.getAreaList()) {
			if(object.getX() == x && object.getY() == y) {
				return object;
			} else if(object.getXEnd() == x && object.getY() == y) {
				return object;
			}
		}
		return null;
	}
	
	public void moveToArea(){
		if(getLastArea() == null) {
			System.out.println("Reached end of route");
		} else if((getLastArea().getX() - x == 1) && getLastArea().getY() == y ) {
			x = getLastArea().getX();
			translateXVal += GridBuilder.colSize;
			guestImageView.setTranslateX(translateXVal);
			if(getLastArea().dimensionW > 1) {
				
			} else {
				currentRoute.remove(getLastArea());
			}
		} else if((getLastArea().getXEnd() - x == 1) && getLastArea().getY() == y ) {
			x = getLastArea().getXEnd();
			translateXVal += GridBuilder.colSize;
			guestImageView.setTranslateX(translateXVal);
			currentRoute.remove(getLastArea());
		} else if ((getLastArea().getX() - x == -1) && getLastArea().getY() == y ) {
			x = getLastArea().getX();
			translateXVal -= GridBuilder.colSize;
			guestImageView.setTranslateX(translateXVal);
			currentRoute.remove(getLastArea());			
		} else if((getLastArea().getXEnd() - x == -1) && getLastArea().getY() == y ) {
			x = getLastArea().getXEnd();
			translateXVal -= GridBuilder.colSize;
			guestImageView.setTranslateX(translateXVal);		} else if(getLastArea().getY() != y ) {
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
			currentRoute.remove(getLastArea());
		}	
	}	
	
	public Area getLastArea() {
		if(currentRoute.size() == 0) {
			return null;
		}else {
			return currentRoute.get(currentRoute.size() - 1) ;
		}
	}	
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int guestId)
	{
		this.id = guestId;
	}
		
	public void setSelectedRoom(int roomNumber)
	{
		this.roomId = roomNumber;
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
