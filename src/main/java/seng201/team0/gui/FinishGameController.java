package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FinishGameController implements Initializable {

    @FXML
    private Label userNameLabel;

    @FXML
    private Label seasonLengthLabel;

    @FXML
    private Label racesCompletedLabel;

    @FXML
    private Label averagePlacementLabel;

    @FXML
    private Label totalPrizeMoneyLabel;

    private GameEnvironment gameEnvironment;
    private SceneNavigator sceneNavigator;

    public FinishGameController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    void updateEndScreen() {
        if (gameEnvironment != null) {
            userNameLabel.setText(gameEnvironment.getName());
            seasonLengthLabel.setText(String.valueOf(gameEnvironment.getSeasonLength()));
            racesCompletedLabel.setText(String.valueOf(gameEnvironment.getRacesCompleted()));
            averagePlacementLabel.setText(String.format("%.2f", gameEnvironment.getAveragePlacement()));
            totalPrizeMoneyLabel.setText("$" + gameEnvironment.getTotalPrizeMoney());
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateEndScreen();
    }

    @FXML
    void switchToMainMenu(ActionEvent event) throws IOException {
        if (sceneNavigator != null) {
            sceneNavigator.switchToSceneMainMenu(event);
        } else {
            System.err.println("SceneNavigator not set in EndOfSeasonController.");
        }
    }

    @FXML
    void quitGame(ActionEvent event) {
        gameEnvironment.quit();
    }
}