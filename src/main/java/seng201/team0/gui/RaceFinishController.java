package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import seng201.team0.services.GameEnvironment;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RaceFinishController implements Initializable {

    @FXML private Label finishReason;
    @FXML private Label placementMessage;
    @FXML private Label resultTitle;
    @FXML private ListView<String> leaderboardListView;
    @FXML private Label moneyEarned;
    @FXML private Button mainMenuButton;
    @FXML private Button newRaceButton;

    protected SceneNavigator sceneNavigator;
    protected GameEnvironment gameEnvironment;


    public RaceFinishController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    public void setSceneNavigator(SceneNavigator sceneNavigator) {
        this.sceneNavigator = sceneNavigator;
    }

    public void setRaceResults(String reason, String placementText, List<String> leaderboard, int earnings) {
        finishReason.setText("Reason: " + reason);
        placementMessage.setText(placementText);
        resultTitle.setText("Final Results");
        leaderboardListView.setItems(FXCollections.observableArrayList(leaderboard));
        moneyEarned.setText("Money Earned: $" + earnings);


        if (gameEnvironment.getRacesRemaining() <= 0) {
            mainMenuButton.setVisible(false);
            mainMenuButton.setManaged(false);
            newRaceButton.setText("View End Screen");
            newRaceButton.setOnAction(event -> { // Lambda for the end screen button
                try {
                    sceneNavigator.switchToFinishGameScene(event);
                } catch (IOException e) {
                    showAlert("Error Loading Scene", "Could not load the End Game Screen: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } else {
            mainMenuButton.setVisible(true);
            mainMenuButton.setManaged(true);
            newRaceButton.setText("New Race");
            newRaceButton.setOnAction(event -> { // Lambda for the new race button
                try {
                    sceneNavigator.switchToSceneCourseAndRoute(event);
                } catch (IOException e) {
                    showAlert("Error Loading Scene", "Could not load the Course and Route Selection: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainMenuButton.setOnAction(event -> { // Lambda for the main menu button
            try {
                sceneNavigator.switchToSceneMainMenu(event);
            } catch (IOException e) {
                showAlert("Error Loading Scene", "Could not load the Main Menu: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}