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
	
	static int xOffset = 2;

	public static Area createArea(String areaType, int dimensionW, int dimensionH, int stars, long capacity, int x, int y){

	      if(areaType == null)
	      {
	         return null;
	      }
	      
	      if(areaType.equalsIgnoreCase("Cinema")) 
	      {
	    	  return new Cinema(dimensionW, dimensionH, x+xOffset, y);
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
	         return new Fitness(dimensionW, dimensionH, x+xOffset, y);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Floor"))
	      {
	         return new Floor();
	      }
	      
	      else if(areaType.equalsIgnoreCase("Hotel"))
	      {
	         return new Hotel();
	      }
	      
	      else if(areaType.equalsIgnoreCase("Room"))
	      {
	         return new HotelRoom(dimensionW, dimensionH, stars, x+xOffset, y);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Lobby"))
	      {
	         return new Lobby(dimensionW, dimensionH, x+xOffset, y);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Restaurant"))
	      {
	         return new Restaurant(dimensionW, dimensionH, x+xOffset, y);
	      }
	      
	      else if(areaType.equalsIgnoreCase("Stairway"))
	      {
	         return new Stairway();
	      }
	      
	      return null;
	   }
}
