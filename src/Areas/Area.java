package Areas;

import java.io.FileInputStream;

import Scenes.SimulationScene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public abstract class Area {

	//Variables
	int x = 0;
	int y = 0;
	int dimensionW;
	int dimensionH;	
	public boolean available = true;
	public int roomNumber = 0;
	public int stars = 1;
	public int cleaningTime = 1;
	public boolean cleaningEmergency = false;
	
	int position = 0;
	int dimension = 0;
	long occupants = 0;
	
	public ImageView roomImageView;
	public Image roomImage;
	
	String imageLocation;
	//Functions
	
	public void createSprite(FileInputStream sprite)
	{
		Image roomImage = new Image(sprite);
        roomImageView = new ImageView();
        roomImageView.setFitWidth(16);
        roomImageView.setFitHeight(37);
        roomImageView.setImage(roomImage);
	}
	
	public void getImageForStars()
	{
		if (stars == 1)
		{
			imageLocation = "door_1_transparant.png";
		}
		
		else if (stars == 2)
		{
			imageLocation = "door_2_transparant.png";
		}
		
		else if (stars == 3)
		{
			imageLocation = "door_3_transparant.png";
		}
		
		else if (stars == 4)
		{
			imageLocation = "door_4_transparant.png";
		}
		
		else if (stars == 5)
		{
			imageLocation = "door_5_transparant.png";
		}
	}
}
