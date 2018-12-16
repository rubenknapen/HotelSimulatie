package Scenes;

import java.io.File;
import javax.swing.JFileChooser;

import Areas.Area;
import Factories.PersonFactory;
import Managers.GridBuilder;
import Managers.HotelManager;
import Managers.SettingBuilder;
import Managers.SimulationTimer;
import Persons.Person;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuScene {
	
	static Stage mainMenuStage;
	static Scene mainMenuScene;
	private VBox layout;
	private Button closeButton;
	private Button startButton;
	private Button layoutSelectButton;
	private Button layoutSettingsButton;
	ShortestPath.Dijkstra _ds = new ShortestPath.Dijkstra();
	
	public static EventLib.HotelEventManager eventManager;
	public static String baseFolder = System.getProperty("user.dir");
	public static String fileLocation = "\\src\\layout\\hotel4.layout";
	public static String selectedLayout = baseFolder+fileLocation;
		
	/**
	 * constructor will set the stage and create all elements that we put into the stage.
	 * @param stage The stage where we put the elements in.
	 */
	
	public MainMenuScene(Stage stage) 
	{
		System.out.println(selectedLayout);
		setStage(stage);
		setPane();
		createButtons();
		addButtons();
		setScene();
		showMainMenuStage();
	}

	public Scene getScene(){
		return mainMenuScene;
	}
	
	private void setScene(){
		mainMenuScene = new Scene(layout, 400, 400);
		mainMenuStage.setScene(mainMenuScene);
	}

	private void setStage(Stage stage) {
		mainMenuStage = stage;
		stage.setTitle("Hotel Simulatie");
		stage.setResizable(false);
	}
	
	public void showMainMenuStage(){
		mainMenuStage.show();
	}

	public Stage getStage() {
		return mainMenuStage;
	}

	private void setPane(){
		layout = new VBox(10);
		layout.setAlignment(Pos.CENTER);

	}
	private void createButtons(){
		// Start button
        startButton = new Button("Start Simulatie");
        startButton.setPrefSize(150, 36);
        startButton.setLayoutX(75);
        startButton.setLayoutY(1);
        startButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				if (selectedLayout == "0"){
					System.out.println("Selecteer eerst een layout!");
				}
				else{
					startSimulation();
				}
			}
		});
		
		// Close button
		closeButton = new Button("Afsluiten");
		closeButton.setPrefSize(150, 36);
		closeButton.setLayoutX(75);
		closeButton.setLayoutY(150);
		closeButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				Platform.exit();
			}
		});
		
		//Layout Select Button
		layoutSelectButton = new Button("Kies Layout");
		layoutSelectButton.setPrefSize(150, 36);
		layoutSelectButton.setLayoutX(75);
		layoutSelectButton.setLayoutY(50);
		layoutSelectButton.setOnAction(new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent e) {
				System.out.println("Working Directory = " +
			              System.getProperty("user.dir"));

				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
				int result = fileChooser.showOpenDialog(fileChooser);
				if (result == JFileChooser.APPROVE_OPTION) 
				{
				    File selectedFile = fileChooser.getSelectedFile();
				    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
				    selectedLayout = selectedFile.getAbsolutePath();
				}
			}
		});
		
		
		layoutSettingsButton = new Button("Instellingen");
		layoutSettingsButton.setPrefSize(150, 36);
		layoutSettingsButton.setLayoutX(75);
		layoutSettingsButton.setLayoutY(100);
		layoutSettingsButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				SettingsScene settingScene = new SettingsScene();
				settingScene.buildScene();
			}
		});
	}
	
	private void addButtons()
	{
		layout.getChildren().addAll(startButton,layoutSelectButton, layoutSettingsButton, closeButton);
	}
	
	public void startSimulation()
	{
	    eventManager = new EventLib.HotelEventManager();
	    HotelManager hotelManager = new HotelManager();
	    eventManager.register(hotelManager);
	    
	    int defaultTick = SettingBuilder.defaultTickSpeed;
	    int enteredTick = SettingBuilder.tickSpeed;
	    double factor = (Double.valueOf(enteredTick) / Double.valueOf(defaultTick));
	    
	    eventManager.changeSpeed(factor);
	    
	    
	    System.out.println("defaultTick is: " + defaultTick);
	    System.out.println("enteredTick is: " + enteredTick);
	    System.out.println("factor is: " + factor);
	    
	    eventManager.start();
	    new SimulationScene();
	    
	}
}
