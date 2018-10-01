package Persons;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import EventLib.HotelEvent;
import Managers.GridBuilder;
import Scenes.SimulationScene;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cleaner extends Person{

	//Variables
	private String status; // Status of the person "evacuate, check in, etc."
	private boolean visibility = true; // Hide or shows the person visually 
	private int x; // x coordinate
	private int y; // y coordinate
	private ImageView cleanerImageView;
	
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

	//Functions
	public void cleanRoom(){
		
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

	@Override
	public void Notify(HotelEvent event)
	{
		String tempEvent = event.Type.toString();
		
		if (tempEvent == "CLEANING_EMERGENCY")
		{
			cleanRoom();
			System.out.println("I'm a Cleaner this is my event: "+event);
		}
		else if (tempEvent == "EVACUATE")
		{
			evacuate();
			System.out.println("I'm a Guest and my event is: " + event);
		}
		else if (tempEvent == "GODZILLA")
		{
			System.out.println("I'm a Guest and my event is: " + event);
		}
	}
}
