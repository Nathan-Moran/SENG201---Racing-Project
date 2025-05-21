package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import seng201.team0.services.GameEnvironment; // Assuming you have this for navigation

import java.io.IOException;

public class TutorialController {

    private GameEnvironment gameEnvironment; // Reference to your GameEnvironment
    private SceneNavigator sceneNavigator;

    // Constructor to inject GameEnvironment (if your app setup uses this pattern)
    public TutorialController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    // Default constructor if your FXML Loader needs one (e.g., if not using ControllerFactory)
    public TutorialController() {
        // You'll need to set the gameEnvironment later if you use this,
        // or ensure your FXML loader handles dependency injection.
    }


    @FXML
    private void onMenuButtonClicked(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }
}