package Managers;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import Persons.Person;
import Scenes.MainMenuScene;


public class SimulationTimer extends Observable{

	//Variables
	private boolean pause = false;
	private int delay = 1;
	private int tickRate = 1;
	private int interval = tickRate*100;  // iterate every sec.
	private int currentTick = 0;
	
	//Constructor
	public SimulationTimer()
	{
		//ShortestPath test = new ShortestPath();
	}
	
	public void pause()
	{
		if(!pause)
			pause = true;
		else if (pause)
			pause = false;
	}
	
	//Functions
	public void setInterval(int newInterval)
	{
		interval = newInterval;
	}
	
	public void getTimerSettings()
	{
		
	}
	
	public void activateTimer()
	{
		  Timer timer = new Timer();
		   
		  timer.scheduleAtFixedRate(new TimerTask() {
		          public void run() 
		          {
		        	  if(!pause)
		        	  {
			        	  setChanged();
			        	  notifyObservers();
			              System.out.println("tick: "+currentTick);
			              currentTick++;
			              //HotelManager.moveCharacters();
			      		  //HotelManager.personsPerformActions();
		        	  }
		          }
		      }, delay, interval);
	}
}
