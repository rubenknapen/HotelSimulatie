package Persons;

public class PersonFactory {

	public static iPerson createPerson(String personType, String status, boolean visibility, int prefStars, int x, int y){

	      if(personType == null){
	         return null;
	      }
	      if("GUEST".equalsIgnoreCase(personType)) {
	    	  return new Guest(status, visibility, prefStars, x, y);
	      } 
	      else if(personType.equalsIgnoreCase("CLEANER")){
	         return new Cleaner();
	      }
	      
	      return null;
	   }
}

