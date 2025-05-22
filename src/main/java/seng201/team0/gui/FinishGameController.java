package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Game Finish Screen. Displays a summary of the player's
 * performance at the end of the game season, including total races completed,
 * average placement, and total prize money. Provides options to return to the
 * main menu or quit the game.
 */
public class FinishGameController implements Initializable {

    /**
     * Label displaying the player's chosen username.
     */
    @FXML
    private Label userNameLabel;

    /**
     * Label displaying the total length of the season (number of races).
     */
    @FXML
    private Label seasonLengthLabel;

    /**
     * Label displaying the number of races completed by the player.
     */
    @FXML
    private Label racesCompletedLabel;

    /**
     * Label displaying the player's average placement across all races.
     */
    @FXML
    private Label averagePlacementLabel;

    /**
     * Label displaying the total prize money earned by the player.
     */
    @FXML
    private Label totalPrizeMoneyLabel;

    /**
     * The game environment, providing access to game state and services.
     */
    private final GameEnvironment gameEnvironment;
    /**
     * The scene navigator for handling transitions between different scenes.
     */
    private final SceneNavigator sceneNavigator;

    /**
     * Constructs a FinishGameController.
     * @param gameEnvironment The current game environment.
     * @param sceneNavigator The scene navigator for scene transitions.
     */
    public FinishGameController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    /**
     * Updates the end screen UI elements with the final game statistics from the GameEnvironment.
     */
    void updateEndScreen() {
        if (gameEnvironment != null) {
            userNameLabel.setText(gameEnvironment.getName());
            seasonLengthLabel.setText(String.valueOf(gameEnvironment.getSeasonLength()));
            racesCompletedLabel.setText(String.valueOf(gameEnvironment.getRacesCompleted()));
            averagePlacementLabel.setText(String.format("%.2f", gameEnvironment.getAveragePlacement()));
            totalPrizeMoneyLabel.setText("$" + gameEnvironment.getTotalPrizeMoney());
        }
    }

    /**
     * Initializes the controller. This method is automatically called by the FXMLLoader
     * after the FXML file has been loaded. It triggers the update of the end screen display.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateEndScreen();
    }

    /**
     * Switches the scene back to the Main Menu.
     * @param event The ActionEvent that triggered this method.
     * @throws IOException If an error occurs during FXML loading.
     */
    @FXML
    void switchToMainMenu(ActionEvent event) throws IOException {
        if (sceneNavigator != null) {
            sceneNavigator.switchToSceneMainMenu(event);
        } else {
            System.err.println("SceneNavigator not set in EndOfSeasonController.");
        }
    }

    /**
     * Handles the action of quitting the game.
     * @param event The ActionEvent that triggered this method.
     */
    @FXML
    public void quitGame(ActionEvent event) {
        gameEnvironment.quit();
    }
}