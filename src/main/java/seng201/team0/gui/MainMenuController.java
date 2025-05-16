package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

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
        sceneNavigator.switchToSceneShopBuy(event);
    }

    @FXML
    void startRace(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneCourseAndRoute(event);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
        seasonLengthLabel.setText(String.valueOf(gameEnvironment.getSeasonLength()));
    }
}
