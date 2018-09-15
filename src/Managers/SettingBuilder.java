package Managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SettingBuilder {

	//Variables
	ArrayList<String> settingList;
	public int tickSpeed = 0;
	public int dieInLineTime = 0;
	public int movieTime = 0;
	
	//Constructor
	public SettingBuilder()
	{
		getSettings();
		showSettings();
	}
	
	//Functions
	public void getSettings()
	{					
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
	
	public void showSettings()
	{
		for(int i = 0; i < settingList.size(); i++)
		{
			System.out.println(settingList.get(i));
		}
	}
}
