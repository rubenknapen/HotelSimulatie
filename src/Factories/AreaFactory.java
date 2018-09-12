package Factories;

import Areas.Cinema;
import Areas.Elevator;
import Areas.Entrance;
import Areas.Fitness;
import Areas.Floor;
import Areas.Hotel;
import Areas.HotelRoom;
import Areas.Lobby;
import Areas.Restaurant;
import Areas.Stairway;
import Areas.Area;

public class AreaFactory {

	public static Area createArea(String areaType, int x, int y, int dimensionW, int dimensionH, int stars, long capacity){

	      if(areaType == null)
	      {
	         return null;
	      }
	      
	      if(areaType.equalsIgnoreCase("Cinema")) 
	      {
	    	  return new Cinema(x, y, dimensionW, dimensionH);
	      } 
	      
	      else if(areaType.equalsIgnoreCase("Elevator"))
	      {
	         return new Elevator();
	      }
	      
	      else if(areaType.equalsIgnoreCase("Entrance"))
	      {
	         return new Entrance();
	      }
	      
	      else if(areaType.equalsIgnoreCase("Fitness"))
	      {
	         return new Fitness(x, y, dimensionW, dimensionH);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Floor"))
	      {
	         return new Floor();
	      }
	      
	      else if(areaType.equalsIgnoreCase("Hotel"))
	      {
	         return new Hotel();
	      }
	      
	      else if(areaType.equalsIgnoreCase("HotelRoom"))
	      {
	         return new HotelRoom(x, y, dimensionW, dimensionH, stars);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Lobby"))
	      {
	         return new Lobby(x, y, dimensionW, dimensionH);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Restaurant"))
	      {
	         return new Restaurant(x, y, dimensionW, dimensionH);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Stairway"))
	      {
	         return new Stairway();
	      }
	      
	      return null;
	   }
}
