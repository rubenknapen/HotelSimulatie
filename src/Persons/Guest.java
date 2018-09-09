package Persons;

public class Guest implements iPerson {

	//Variables
	private String status; // Status of the person "evacuate, check in, etc."
	private boolean visibility = true; // Hide or shows the person visually 
	private int prefStars; // Preference for stars of hotelroom
	private int x; // x coordinate
	private int y; // y coordinate
	
	//Constructor
	public Guest(String status, boolean visibility, int prefStars, int x, int y){
			this.setStatus(status);
			this.setVisibility(visibility);
			this.setPrefStars(prefStars);		
			this.setX(x);
			this.setY(y);
	}
	
	//Functions
	public void checkInRoom(){
		
	}
	
	public void checkOutRoom(){
		
	}

	public void setElevatorDirection(){
		
	}
	
	public void setElevatorFloor(){
		
	}
	
	public void die(){
		
	}
	
	public void test(){
		System.out.println("Ik ben nu aanwezig en mijn status is: " + status);
	}

	@Override
	public void evacuate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getFastestRoute() {
		// TODO Auto-generated method stub
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isVisibility() {
		return visibility;
	}

	public void setVisibility(boolean visibility) {
		this.visibility = visibility;
	}

	public int getPrefStars() {
		return prefStars;
	}

	public void setPrefStars(int prefStars) {
		this.prefStars = prefStars;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
