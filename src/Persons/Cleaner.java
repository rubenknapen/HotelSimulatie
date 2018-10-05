package Persons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import Areas.Area;
import EventLib.HotelEvent;
import Managers.GridBuilder;
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
	private int cleaningTime = 3;
	private int cleaningTimeRemaining;
	private int x; // x coordinate
	private int y; // y coordinate
	private int id;
	private ImageView cleanerImageView;
	private int translateXVal;
	private int translateYVal;
	
	//Constructor
	public Cleaner(String status, boolean visibility, int x, int y){
		
		this.setStatus(status);
		this.setVisibility(visibility);	
		this.setX(x);
		this.setY(y);

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
	
	public void moveToArea(){
		if(getLastArea() == null) {
//			System.out.println("Reached end of route");
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
		} 
		else if((getLastArea().getXEnd() - x == -1) && getLastArea().getRealY() == y ) {
			
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
		else if(getLastArea().getY() != y ) {
			
			// Up and down movement
			
			if(getLastArea().getY() - y == -1) {
				y = getLastArea().getY();
				translateYVal -= GridBuilder.rowSize;
				cleanerImageView.setTranslateY(translateYVal);
				currentRoute.remove(getLastArea());
			} else {
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
	
	public Area getLastArea() {
		if(currentRoute.size() == 0) {
			return null;
		}else {
			return currentRoute.get(currentRoute.size() - 1) ;
		}
	}
	
	@Override
	public void performAction()
	{
		if(status.equals("CLEANING") &&currentRoute.isEmpty())
		{
			cleanRoom();
		}
	}

	//Functions
	public void cleanRoom()
	{
		if(visibility)
		{
			setInvisible();
			cleaningTimeRemaining = cleaningTime;
		}
		else if (!visibility)
		{
			if(cleaningTimeRemaining == 0)
			{
				setVisible();
				status = "Inactive";
			}
			else
			{
				cleaningTimeRemaining -= 1;
				System.out.println("I'm still cleaning, time left: " + cleaningTimeRemaining);
			}
		}
		
	}
	
	public void setVisible()
	{
		Platform.runLater(
				  () -> {
						cleanerImageView.setVisible(true);
						visibility = true;
				  });
	}
	
	public void setInvisible()
	{
		Platform.runLater(
				  () -> {
					cleanerImageView.setVisible(false);
					visibility = false;
				  });
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
