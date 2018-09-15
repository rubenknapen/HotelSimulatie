package Scenes;

import java.io.File;

import javax.swing.JFileChooser;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuScene {
	
	public Stage mainMenuStage;
	public Scene mainMenuScene;
	public Pane layout;
	public BorderPane bPane;
	public Image image;
	public ImageView imageView;
	public Button closeButton;
	public Button startButton;
	public Button layoutSelectButton;
	public static String selectedLayout = "0";
	
	/**
	 * constructor die zorgt dat de stage gebouwt en weergegeven wordt.
	 * @param stage
	 */
	public MainMenuScene(Stage stage) 
	{
		setStage(stage);
		setPane();
		createButtons();
		addButtons();
		setScene();
		showMainMenuStage();
	}

	public Scene getScene(){
		return this.mainMenuScene;
	}
	
	public void setScene()
	{
		mainMenuScene = new Scene(layout, 300, 300);
		mainMenuStage.setScene(mainMenuScene);
	}

	public void setStage(Stage stage) {
		this.mainMenuStage = stage;
		stage.setTitle("Hotel Simulatie");
		stage.setResizable(false);
	}
	
	public void showMainMenuStage()
	{
		mainMenuStage.show();
	}

	public Stage getStage() {
		return mainMenuStage;
	}

	public void setPane()
	{
		layout = new Pane();
		layout.setPadding(new Insets(0, 0, 0, 0));
	}
	public void createButtons()
	{
		// Start button
        startButton = new Button("Start Simulatie");
        startButton.setPrefSize(150, 36);
        startButton.setLayoutX(75);
        startButton.setLayoutY(1);
        startButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				if (selectedLayout == "0")
				{
					System.out.println("Selecteer eerst een layout!");
				}
				else
				{
					startSimulation();
				}
			}
		});
		
		// Close button
		closeButton = new Button("Afsluiten");
		closeButton.setPrefSize(150, 36);
		closeButton.setLayoutX(75);
		closeButton.setLayoutY(50);
		closeButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {

				Platform.exit();
			}
		});
		
		//Layout Select Button
		layoutSelectButton = new Button("Kies Layout");
		layoutSelectButton.setPrefSize(150, 36);
		layoutSelectButton.setLayoutX(75);
		layoutSelectButton.setLayoutY(100);
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
	}
	
	public void addButtons()
	{
		layout.getChildren().addAll(startButton,closeButton,layoutSelectButton);
	}
	
	public void startSimulation()
	{
	    new SimulationScene();
	}
}
