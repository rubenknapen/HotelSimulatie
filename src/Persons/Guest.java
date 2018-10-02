package Persons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

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
	private int prefStars; // Preference for stars of hotelroom
	private int x; // x coordinate
	private int y; // y coordinate
	private ImageView guestImageView;
	private int selectedRoom;
	private int id;
	
	//Constructor
	public Guest(String status, boolean visibility, int prefStars, int x, int y)
	{
		this.setStatus(status);
		this.setVisibility(visibility);
		this.setPrefStars(prefStars);		
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
		
	public void setId(int guestId)
	{
		this.id = guestId;
	}
	
	public int getId()
	{
		return id;
	}
		
	public void setSelectedRoom(int roomNumber)
	{
		this.selectedRoom = roomNumber;
	}
	
	public int getSelectedRoom()
	{
		return selectedRoom;
	}
		
	public void checkInRoom(){
		
	}
	
	public void checkOutRoom(){
		
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

	public int getPrefStars() {
		return prefStars;
	}

	public void setPrefStars(int prefStars) {
		this.prefStars = prefStars;
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
