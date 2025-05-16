package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

import java.io.IOException;
import java.util.List;

public class RaceFinishController {

    @FXML private Label finishReason;
    @FXML private Label placementMessage;
    @FXML private Label resultTitle;
    @FXML private ListView<String> leaderboardListView;
    @FXML private Label moneyEarned;
    @FXML private Button mainMenuButton;
    @FXML private Button newRaceButton;

    private SceneNavigator sceneNavigator;

    public void setSceneNavigator(SceneNavigator sceneNavigator) {
        this.sceneNavigator = sceneNavigator;
    }

    public void setRaceResults(String reason, String placement, List<String> leaderboard, int earnings) {
        finishReason.setText("Reason: " + reason);
        placementMessage.setText(placement);
        resultTitle.setText("Final Results");
        leaderboardListView.setItems(FXCollections.observableArrayList(leaderboard));
        moneyEarned.setText("Money Earned: $" + earnings);
    }

    @FXML
    private void initialize() {
        mainMenuButton.setOnAction(event -> {
            try {
                sceneNavigator.switchToSceneMainMenu(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        newRaceButton.setOnMouseClicked(event -> {
            try {
                sceneNavigator.switchToSceneRace(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
