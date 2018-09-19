package Persons;

import java.util.Observable;
import java.util.Observer;

public abstract class Person implements Observer
{	
	String path = "";
	
	//All functions
	public void evacuate()
	{
		// Evacuates the person from the building
	}
	
	public void move()
	{
		//
	}
	
	public void update(Observable observable, Object arg)
	{
		System.out.print("Ik moet gaan lopen");
	}
}
