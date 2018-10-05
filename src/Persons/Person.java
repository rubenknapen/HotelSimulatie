package Persons;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Areas.Area;
import EventLib.HotelEvent;


public abstract class Person{	
	
	ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
	public ArrayList<Area> currentRoute = new ArrayList<Area>();
	private int id;
	private int fitnessTickAmount;
	String path = "";
	String status = "";

	
	//All functions

	public void getRoute(Area destinationArea){
		
	}
	
	public int checkDistanceRestaurant(Area destinationArea){	
		return 0;
	}		
	
	public void moveToArea(){
		
	}
	
	public void evacuate(){
		// Evacuates the person from the building
	}
			
	public void Notify(HotelEvent event){
		
	}
	
	public int getId(){
		return id;
	}
	
<<<<<<< HEAD
	public void clearRoute()
	{
		currentRoute.clear();
	}
	
	public void setId(int guestId)
	{
=======
	public void setId(int guestId){
>>>>>>> ea574325bda46437617656210df0efaad1b0caf3
		this.id = guestId;
	}

	public void performAction() {
		
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
	
}
