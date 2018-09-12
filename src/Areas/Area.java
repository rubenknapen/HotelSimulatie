package Areas;

import java.io.FileInputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Area {

	//Variables
	int x = 0;
	int y = 0;
	int dimensionW;
	int dimensionH;
	
	int position = 0;
	int dimension = 0;
	int occupants = 0;
	
	public ImageView roomImageView;
	public Image roomImage;
	
	//Functions
	
	public void createSprite(FileInputStream sprite)
	{
		Image roomImage = new Image(sprite);
        roomImageView = new ImageView();
        roomImageView.setFitWidth(16);
        roomImageView.setFitHeight(37);
        roomImageView.setImage(roomImage);
	}
}
