package Managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SettingBuilder {

	//Variables
	ArrayList<String> settingList;
	public static int tickSpeed = 1;
	public static int dieInLineTime = 1;
	public static int movieTime = 1;
	public static int stairTime = 1;
	
	//Constructor
	public SettingBuilder(){
		getSettings();
		showSettings();
	}
	
	//Functions
	public void getSettings(){					
		String pathBase = System.getProperty("user.dir");
		String pathFile = "\\src\\settings\\settings.txt";
		String fullPath = pathBase+pathFile;
		System.out.println(fullPath);
		
		File file = new File(fullPath);
		Scanner input = null;
		try 
		{
			input = new Scanner(file);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		settingList = new ArrayList<String>();

		while (input.hasNextLine()) 
		{
		    settingList.add(input.nextLine());
		}
	}
	
	
	public void showSettings(){
		for(int i = 0; i < settingList.size(); i++)
		{
			System.out.println(settingList.get(i));
		}
	}
	
	
	public static int getTickSpeed() {
		return tickSpeed;
	}

	public void setTickSpeed(int TickSpeed) {
		tickSpeed = TickSpeed;
	}

	public int getDieInLineTime() {
		return dieInLineTime;
	}

	public void setDieInLineTime(int DieInLineTime) {
		dieInLineTime = DieInLineTime;
	}

	public static int getMovieTime() {
		return movieTime;
	}

	public void setMovieTime(int MovieTime) {
		movieTime = MovieTime;
	}
	
	public static int getstairTime() {
		return stairTime;
	}

	public void setStairTime(int StairTime) {
		stairTime = StairTime;
	}
	
}
