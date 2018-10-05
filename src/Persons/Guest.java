package Persons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import Areas.Area;
import Areas.Fitness;
import Areas.Stairway;
import EventLib.HotelEvent;
import Managers.GridBuilder;
import Managers.HotelManager;
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
	private int fitnessTickAmount;
	
	//Constructor
	public Guest(String status, int id, boolean visibility, int roomId, int x, int y)
	{
		this.id = id;
		this.setStatus(status);
		this.setVisibility(visibility);
		this.setVisible();
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
			} else if(object.getXEnd() == x && object.getRealY() == y) {
				return object;
			}
		}
		return null;
	}
	
	@Override
	public void performAction() {
		if(status.equals("GOTO_FITNESS") && currentRoute.isEmpty() ) {
			setStatus("INSIDE_FITNESS");
		}
		if(status.equals("INSIDE_FITNESS")) {
			if(fitnessTickAmount == 0) {
				setStatus("GO_BACK_TO_ROOM");
			} else {
				setInvisible();
				fitnessTickAmount--;
			}
		}
		if(status.equals("GO_BACK_TO_ROOM")) {
			setVisible();
			setStatus("WALKING_BACK_TO_ROOM");
			getRoute(HotelManager.getRoomNode(roomId));
		}if(status.equals("GO_BACK_TO_ROOM")) {
			
		}
	}
	
	public void moveToArea(){
		if(getLastArea() == null) {
//			System.out.println("Reached end of route");
		} 
		else if((getLastArea().getX() - x == 1) && getLastArea().getRealY() == y ) {
			
			// Right movement
			
			x = getLastArea().getX();
			translateXVal += GridBuilder.colSize;
			guestImageView.setTranslateX(translateXVal);
			if(getLastArea().dimensionW > 1) {
				
			} else {
				currentRoute.remove(getLastArea());
			}
		} 
		else if((getLastArea().getXEnd() - x == 1) && getLastArea().getRealY() == y ) {
			
			// Right movement
			
			x = getLastArea().getXEnd();
			translateXVal += GridBuilder.colSize;
			guestImageView.setTranslateX(translateXVal);
			currentRoute.remove(getLastArea());
		} 
		else if((getLastArea().getXEnd() - x == -1) && getLastArea().getRealY() == y ) {
			
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
