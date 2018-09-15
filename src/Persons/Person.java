package Persons;

import java.util.Observable;
import java.util.Observer;

public abstract class Person implements Observer
{	
	//All functions
	public void evacuate()
	{
		// Evacuates the person from the building
	}
	
	public void getFastestRoute()
	{
		// Calculate the fastest route to their destination
	}
	
	public void update(Observable observable, Object arg)
	{
		System.out.print("Ik moet gaan lopen");
	}
}
