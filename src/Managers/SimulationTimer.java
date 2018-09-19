package Managers;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import ShortestPath.ShortestPath;

public class SimulationTimer extends Observable{

	//Variables
	int delay = 1;
	int tickRate = 1;
	int interval = tickRate*1000;  // iterate every sec.
	int currentTick = 0;
	
	//Constructor
	public SimulationTimer()
	{
		ShortestPath test = new ShortestPath();
	}
	
	//Functions
	public void getTimerSettings()
	{
		
	}
	
	public void activateTimer()
	{
		  Timer timer = new Timer();
		   
		  timer.scheduleAtFixedRate(new TimerTask() {
		          public void run() 
		          {
		        	  setChanged();
		        	  notifyObservers();
		              System.out.println("tick: "+currentTick);
		              currentTick++;
		          }
		      }, delay, interval);
	}
}
