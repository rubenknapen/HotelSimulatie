package Factories;

import Persons.Cleaner;
import Persons.Guest;
import Persons.Person;

public class PersonFactory {

	public static Person createPerson(String personType, String status, int id, boolean visibility, int roomId, int x, int y){

	      if(personType == null){
	         return null;
	      }
	      if(personType.equalsIgnoreCase("GUEST")) {
	    	  return new Guest(status, id, visibility, roomId, x, y);
	      } 
	      else if(personType.equalsIgnoreCase("CLEANER")){
	         return new Cleaner(status, visibility, x, y);
	      }
	      
	      return null;
	   }
}

