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
 * Controller for the main menu screen.
 * This class provides navigation to other parts of the game such as the garage, shop, and race setup.
 * It also displays key game information like the player's money and the current season length.
 * @author Nathan Moran
 */
public class MainMenuController implements Initializable {
    /**
     * The game environment, providing access to game state and services.
     */
    protected GameEnvironment gameEnvironment;
    /**
     * The scene navigator, used for switching between different scenes in the application.
     */
    protected SceneNavigator sceneNavigator;

    /**
     * Constructs a MainMenuController with the given game environment and scene navigator.
     *
     * @param gameEnvironment The game environment instance.
     * @param sceneNavigator  The scene navigator instance.
     */
    public MainMenuController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    /**
     * Label to display the player's current amount of money.
     */
    @FXML
    private Label moneyLabel;

    /**
     * Label to display the configured length of the current game season.
     */
    @FXML
    private Label seasonLengthLabel;

    /**
     * Handles the action of navigating to the garage screen.
     *
     * @param event The action event triggered by the "Go to Garage" button.
     * @throws IOException If an I/O error occurs during scene transition.
     */
    @FXML
    void goToGarage(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneGarage(event);
    }

    /**
     * Handles the action of navigating to the shop screen (buy section).
     *
     * @param event The action event triggered by the "Go to Shop" button.
     * @throws IOException If an I/O error occurs during scene transition.
     */
    @FXML
    void goToShop(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneShopBuy(event);
    }

    /**
     * Handles the action of navigating to the course and route selection screen to start a race.
     *
     * @param event The action event triggered by the "Start Race" button.
     * @throws IOException If an I/O error occurs during scene transition.
     */
    @FXML
    void startRace(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneCourseAndRoute(event);
    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method updates the money label and season length label with current game data.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
        seasonLengthLabel.setText(String.valueOf(gameEnvironment.getSeasonLength()));
    }
}
