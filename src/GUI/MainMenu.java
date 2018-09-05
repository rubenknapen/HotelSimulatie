package GUI;

import Scenes.MainMenuScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainMenu extends Application{
		
		//Constructor
	    public static void main(String[]args) 
	    {
	        launch(args);
	    }

	    //Method die hoofdmenu start.
	    
	    public void start(Stage stage)
	    {
	    	new MainMenuScene(stage);
	    }
}
