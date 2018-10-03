package Persons;

import java.util.Observable;
import java.util.Observer;

import EventLib.HotelEvent;

public abstract class Person{	
	
	String path = "";
	String status = "";
	
	//All functions
	public void evacuate()
	{
		// Evacuates the person from the building
	}
	
	public void move()
	{
		//
	}
	
	public void Notify(HotelEvent event)
	{
		
	}
}
