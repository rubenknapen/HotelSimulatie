package Areas;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HotelRoom {


	//Variables
	public boolean available = true;
	public int roomNumber = 0;
	public int stars = 1;
	public int cleaningTime = 1;
	public boolean cleaningEmergency = false;
	
	public ImageView mensImageView;
	public Image mensImage;
	private int dimensionW;
	private int dimensionH;

	
	//Constructor
	public HotelRoom(int dimensionW, int dimensionH){
		
		this.dimensionW = dimensionW;
		this.dimensionH = dimensionH;
		
		try {
			if(dimensionW == 1 && dimensionH ==1) {
				setSprite(new FileInputStream("src/Images/room_1.png"));
			} else if(dimensionW == 2 && dimensionH == 2)  {
				setSprite(new FileInputStream("src/Images/room_2_2.png"));
			}
        } catch (FileNotFoundException e) {
            //
            e.printStackTrace();
        }
        

	}
	
	public void setSprite(FileInputStream sprite){
        Image mensImage = new Image(sprite);
        mensImageView = new ImageView();
        if(dimensionW == 1 && dimensionH ==1) {
        	mensImageView.setFitHeight(50);
        	mensImageView.setFitWidth(50);
        } else if(dimensionW == 2 && dimensionH == 2)  {
        	mensImageView.setFitHeight(100);
        	mensImageView.setFitWidth(100);
        }
        mensImageView.setImage(mensImage);
    }
	
	
}
