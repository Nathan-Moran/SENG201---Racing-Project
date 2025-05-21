package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import seng201.team0.services.GameEnvironment; // Assuming you have this for navigation

import java.io.IOException;

/**
 * Controller for the Tutorial Screen. Displays information about how to play the game
 * and provides a button to navigate back to the main menu.
 */
public class TutorialController {

    /**
     * The game environment, providing access to game state and services.
     */
    /**
     * The scene navigator for handling transitions between different scenes.
     */
    private SceneNavigator sceneNavigator;

    /**
     * Constructs a TutorialController with injected dependencies.
     * @param sceneNavigator The scene navigator instance for navigation.
     */
    public TutorialController(SceneNavigator sceneNavigator) {
        this.sceneNavigator = sceneNavigator;
    }

    /**
     * Handles the action when the "Back to Menu" button is clicked.
     * Switches the scene back to the Main Menu.
     * @param event The ActionEvent that triggered this method.
     * @throws IOException If an error occurs during FXML loading.
     */
    @FXML
    private void onMenuButtonClicked(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }
}
