package Persons;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Areas.Area;
import EventLib.HotelEvent;


public abstract class Person{	
	
	ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
	ArrayList<Area> currentRoute = new ArrayList<Area>();
	private int id;
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
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int guestId)
	{
		this.id = guestId;
	}
	
}
