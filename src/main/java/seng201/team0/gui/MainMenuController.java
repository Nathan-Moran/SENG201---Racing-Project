package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;

public class MainMenuController {

    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    public MainMenuController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }


    @FXML
    private Label moneyLabel;

    @FXML
    private Label seasonLengthLabel;

    @FXML
    void goToGarage(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneGarage(event);
    }

    @FXML
    void goToShop(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneShopSell(event);
    }

    @FXML
    void startRace(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneCourseAndRoute(event);
        }
}
