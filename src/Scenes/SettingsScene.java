package Scenes;

import Managers.SettingBuilder;
import Scenes.MainMenuScene;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class SettingsScene {

	private GridPane settingsGrid;
	private Scene settingsScene;
	
	public SettingsScene() {
		
	}
	
	public void buildScene() {
		GridPane settingsGrid = new GridPane();
		settingsScene = new Scene(settingsGrid, 400, 400);
		MainMenuScene.mainMenuStage.setScene(settingsScene);
		
		
		Label setting1Label = new Label("Tick speed: ");
		TextField setting1TextField = new TextField (Integer.toString(SettingBuilder.getTickSpeed()));
		settingsGrid.add(setting1Label, 0, 0, 1, 1);
		settingsGrid.add(setting1TextField, 1, 0, 1, 1);
		
		
		
		
		
		
		Button cancelButton = new Button("Cancel");
		settingsGrid.add(cancelButton, 0, 6, 1, 1);
		
		Button saveButton = new Button("Save");
		settingsGrid.add(saveButton, 1, 6, 1, 1);
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent e) {
				MainMenuScene.mainMenuStage.setScene(MainMenuScene.mainMenuScene);
			}
		});
		
		
	}
	
}
