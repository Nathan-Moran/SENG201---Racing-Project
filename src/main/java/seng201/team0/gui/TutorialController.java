package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import seng201.team0.services.GameEnvironment; // Assuming you have this for navigation

import java.io.IOException;

public class TutorialController {

    private GameEnvironment gameEnvironment;
    private SceneNavigator sceneNavigator;


    public TutorialController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    public TutorialController() {
    }

    @FXML
    private void onMenuButtonClicked(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneMainMenu(event);
    }
}