package seng201.team0.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import seng201.team0.models.Difficulty;
import seng201.team0.services.ControllerService;
import seng201.team0.services.GameEnvironment;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import java.net.URL;

/**
 * Controller for the start menu screen.
 * This class handles player input for game setup, including player name, season length, and difficulty.
 * It validates the input and then proceeds to the car selection screen.
 * @author Nathan Moran
 */
public class StartMenuController {

    /**
     * TextField for the player to enter their name.
     */
    @FXML private TextField nameField;
    /**
     * Slider for the player to select the season length.
     */
    @FXML private Slider seasonSlider;
    /**
     * Label to display the currently selected difficulty.
     */
    @FXML private Label displayDifficultyLabel;
    /**
     * Label to display the name entered by the player.
     */
    @FXML private Label displayNameLabel;
    /**
     * Label to display the currently selected season length.
     */
    @FXML private Label displaySeasonLengthLabel;
    /**
     * The game environment, used to configure game settings like difficulty and season length.
     */
    protected GameEnvironment gameEnvironment;
    /**
     * The scene navigator, used for switching to the next scene (car selector) after setup.
     */
    protected SceneNavigator sceneNavigator;
    /**
     * Manager for handling controller-specific logic, such as name validation.
     */
    private final ControllerService controllerLogicManager;
    /**
     * Stores the player's chosen name.
     */
    String name;

    /**
     * Constructs a StartMenuController with the given game environment and scene navigator.
     *
     * @param gameEnvironment The game environment instance.
     * @param sceneNavigator  The scene navigator instance.
     */
    public StartMenuController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        this.controllerLogicManager = gameEnvironment.getControllerService();
        gameEnvironment.getMusicManager().initializeMusic("/music/MenuMusic.mp3");
    }

    /**
     * Sets the season length display label based on the current value of the season slider.
     * This method is typically called when the slider's value changes (e.g., on mouse drag or release).
     *
     * @param event The mouse event that triggered this action (e.g., slider value changed).
     */
    @FXML
    private void setSeasonLength(MouseEvent event) {
        displaySeasonLengthLabel.setText(String.valueOf(seasonSlider.getValue()));
    }

    /**
     * Sets the display name label based on the text entered in the name field.
     * This method is called as the user types in the name field.
     *
     * @param event The key event that triggered this action (e.g., key typed in name field).
     */
    @FXML
    void setName(KeyEvent event) {
        displayNameLabel.setText(nameField.getText());
    }

    /**
     * Handles the "Accept" button action.
     * It finalizes the game settings (season length, player name, difficulty),
     * validates the player's name, and if all inputs are valid, switches to the car selector scene.
     * If the name is invalid, it provides feedback to the user.
     *
     * @param event The action event triggered by the "Accept" button.
     * @throws IOException If an I/O error occurs during scene transition.
     */
    @FXML
    private void acceptButton(ActionEvent event) throws IOException {
        int seasonLength = (int) seasonSlider.getValue();
        gameEnvironment.setSeasonLength(seasonLength);
        gameEnvironment.setRacesRemaining(seasonLength);
        name = nameField.getText();

        boolean difficultySelected = !displayDifficultyLabel.getText().equals("-");
        boolean seasonLengthSelected = !displaySeasonLengthLabel.getText().equals("-");


        nameField.setPromptText(controllerLogicManager.nameChecker(name));
        if (!nameField.getPromptText().equals("Valid Name")) {
            nameField.setStyle("-fx-prompt-text-fill: red;");
            nameField.clear();
        } else if (!difficultySelected || !seasonLengthSelected) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please ensure a difficulty and season length are selected");
            alert.showAndWait();
        } else {
            gameEnvironment.setName(name);
            gameEnvironment.setBalance();
            sceneNavigator.switchToSceneCarSelector(event);
        }
    }

    /**
     * Sets the game difficulty to Easy and updates the difficulty display label.
     *
     * @param event The action event triggered by the "Easy" difficulty button.
     */
    @FXML
    private void setdifficultyEasy(ActionEvent event) {
        displayDifficultyLabel.setText("Easy");
        gameEnvironment.setDifficulty(Difficulty.EASY);
    }

    /**
     * Sets the game difficulty to Medium and updates the difficulty display label.
     *
     * @param event The action event triggered by the "Medium" difficulty button.
     */
    @FXML
    void setdifficultyMedium(ActionEvent event) {
        displayDifficultyLabel.setText("Medium");
        gameEnvironment.setDifficulty(Difficulty.MEDIUM);
    }

    /**
     * Sets the game difficulty to Hard and updates the difficulty display label.
     *
     * @param event The action event triggered by the "Hard" difficulty button.
     */
    @FXML
    void setdifficultyHard(ActionEvent event) {
        displayDifficultyLabel.setText("Hard");
        gameEnvironment.setDifficulty(Difficulty.HARD);
    }
}





