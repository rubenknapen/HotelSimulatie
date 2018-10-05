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
	
	public void setId(int guestId){
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
