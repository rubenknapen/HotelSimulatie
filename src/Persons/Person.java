package Persons;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import Areas.Area;
import EventLib.HotelEvent;
import Managers.GridBuilder;


public abstract class Person
{	
	ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
	public ArrayList<Area> currentRoute = new ArrayList<Area>();
	public static ArrayList<Area> roomCleaningList = new ArrayList<Area>();
	public ArrayList<Area> restaurantsToCheck = new ArrayList<Area>();
	private int id;
	public int roomId;
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
	
	public Area getCurrentPosition() {
		return null;

	}
	
	public void clearRoute()
	{
		currentRoute.clear();
	}
	
	public void setId(int guestId){
		this.id = guestId;
	}

	public void performAction() {
		
	}
	
	public void setInvisible() 
	{
		
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
	
	public void setSelectedRoom(int roomNumber)
	{

	}
	
	public void setRoomId(int roomId) {
	
	}
	
	public int getSelectedRoom()
	{
		return 0;
	}
	
	public int getX() {
		return 0;
	}

	public void getLobbyRoute() 
	{
		//
	}

	public boolean getAvailability() 
	{
		//
		return false;
	}

	public void setAvailability(boolean b) 
	{
		// 
	}

	public void setVisible() 
	{
		//
	}
	
}
