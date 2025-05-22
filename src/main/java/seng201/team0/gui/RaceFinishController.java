package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import seng201.team0.services.GameEnvironment;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the Race Finish Screen. Displays the results of a completed race,
 * including the reason for finishing, player placement, leaderboard, and money earned.
 * Also handles navigation to the main menu or starting a new race/viewing end screen.
 */
public class RaceFinishController {

    /**
     * Label displaying the reason the race concluded.
     */
    @FXML private Label finishReason;
    /**
     * Label displaying the player's placement message (e.g., "🏆 You finished 1st!").
     */
    @FXML private Label placementMessage;
    /**
     * Label for the title of the results section.
     */
    @FXML private Label resultTitle;
    /**
     * ListView for displaying the race leaderboard.
     */
    @FXML private ListView<String> leaderboardListView;
    /**
     * Label displaying the money earned from the race.
     */
    @FXML private Label moneyEarned;
    /**
     * Button to navigate back to the main menu.
     */
    @FXML private Button mainMenuButton;
    /**
     * Button to start a new race or view the end game screen if the season is over.
     */
    @FXML private Button newRaceButton;

    /**
     * The scene navigator for handling transitions between different scenes.
     */
    protected SceneNavigator sceneNavigator;
    /**
     * The game environment, providing access to game state and services.
     */
    protected GameEnvironment gameEnvironment;

    /**
     * Constructs a RaceFinishController.
     * @param gameEnvironment The current game environment.
     * @param sceneNavigator The scene navigator for scene transitions.
     */
    public RaceFinishController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    /**
     * Sets the SceneNavigator instance. This method might be used if the navigator
     * is not injected via the constructor.
     * @param sceneNavigator The SceneNavigator instance to set.
     */
    public void setSceneNavigator(SceneNavigator sceneNavigator) {
        this.sceneNavigator = sceneNavigator;
    }

    /**
     * Sets and displays the race results on the screen. This method populates
     * the labels and list view with the provided race data and configures
     * the "New Race" button based on whether the season has ended.
     * @param reason The reason the race finished (e.g., "Finished the race!", "Out of fuel!").
     * @param placementText A formatted string indicating the player's placement.
     * @param leaderboard A list of strings representing the race leaderboard.
     * @param earnings The amount of money earned from the race.
     */
    public void setRaceResults(String reason, String placementText, List<String> leaderboard, int earnings) {
        if (this.gameEnvironment != null && this.gameEnvironment.getShopInventory() != null) {
            this.gameEnvironment.getShopInventory().setShopInventory();
        }

        finishReason.setText("Reason: " + reason);
        placementMessage.setText(placementText);
        resultTitle.setText("Final Results");
        leaderboardListView.setItems(FXCollections.observableArrayList(leaderboard));
        moneyEarned.setText("Money Earned: $" + earnings);


        if (gameEnvironment.getRacesRemaining() <= 0) {
            mainMenuButton.setVisible(false);
            mainMenuButton.setManaged(false);
            newRaceButton.setText("View End Screen");
            newRaceButton.setOnAction(event -> {
                try {
                    sceneNavigator.switchToFinishGameScene(event);
                } catch (IOException e) {
                    showAlert("Could not load the End Game Screen: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        } else {
            mainMenuButton.setVisible(true);
            mainMenuButton.setManaged(true);
            newRaceButton.setText("New Race");
            newRaceButton.setOnAction(event -> {
                try {
                    sceneNavigator.switchToSceneCourseAndRoute(event);
                } catch (IOException e) {
                    showAlert("Could not load the Course and Route Selection: " + e.getMessage());
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Initializes the controller. This method is automatically called by the FXMLLoader
     * after the FXML file has been loaded. It sets up the action for the main menu button.
     */
    @FXML
    private void initialize() {
        mainMenuButton.setOnAction(event -> {
            try {
                sceneNavigator.switchToSceneMainMenu(event);
            } catch (IOException e) {
                showAlert("Could not load the Main Menu: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Displays an alert dialog with a specified title and content.
     *
     * @param content The main message content of the alert dialog.
     */
    private void showAlert(String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Loading Scene");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Handles the action of quitting the game.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    void quitGame(ActionEvent event) {
        gameEnvironment.quit();
    }
}