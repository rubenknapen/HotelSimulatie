package Managers;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import Persons.Person;
import Scenes.MainMenuScene;


public class SimulationTimer extends Observable{

	//Variables
	private int interval = 500;
	private double delay = 1;
	private int currentTick = 0;
	
	public static Timer timer;
	
	//Constructor
	public SimulationTimer()
	{
		
	}
	
	public void setInterval(int newInterval)
	{
		interval = newInterval;
	}
	
	//Functions
	public void getTimerSettings()
	{
		
	}
	
	public void activateTimer()
	{
		  timer = new Timer();
		   
		  timer.scheduleAtFixedRate(new TimerTask() {
		          public void run() 
		          {
		        	  setChanged();
		        	  notifyObservers();
		              System.out.println("tick: "+currentTick);
		              currentTick++;
		          }
		      }, (long) delay, interval);
	}
}
