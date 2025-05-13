package seng201.team0.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import seng201.team0.Difficulty;


public class StartMenuController {
    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    String name;

    public StartMenuController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    @FXML
    void setSeasonLength(DragEvent event) {
        displaySeasonLengthLabel.setText(String.valueOf(seasonSlider.getValue()));
    }

    @FXML
    private void acceptButton(ActionEvent event) throws IOException {
        //Get chosen difficulty
        Boolean invalidNameLength = false;
        Boolean invalidNameCharacters = false;

        gameEnvironment.setSeasonLength((int) seasonSlider.getValue());
        System.out.println(seasonSlider.getValue());
        name = (String) nameField.getText();

        if (!(name.length() >= 3 && name.length() <= 15)) {
            invalidNameLength = true;
        }
        for (int i = 0; i < nameField.getText().length(); i++) {
            // Checks each character in Name to check if it is a special character
            if (!Character.isLetterOrDigit(name.charAt(i))) {
                invalidNameCharacters = true;
            }
        }
        //Maybe remove if both
        if (invalidNameCharacters && invalidNameLength) {
            nameField.clear();
            nameField.setPromptText("Name must be between 3 and 15 characters and not contain special characters");
            nameField.setStyle("-fx-prompt-text-fill: red;");
        }
        else if (invalidNameLength) {
            nameField.clear();
            nameField.setPromptText("Name must be between 3 and 15 characters");
            nameField.setStyle("-fx-prompt-text-fill: red;");
        } else if (invalidNameCharacters) {
            nameField.clear();
            nameField.setPromptText("Name must not contain special characters");
            nameField.setStyle("-fx-prompt-text-fill: red;");
        } else {
            sceneNavigator.switchToSceneCarSelector(event);
        }
    }

    @FXML
    private void setdifficultyEasy(ActionEvent event) throws IOException {
        gameEnvironment.setDifficulty(Difficulty.EASY);
    }

    @FXML
    void setdifficultyMedium(ActionEvent event) {
        gameEnvironment.setDifficulty(Difficulty.MEDIUM);
    }

    @FXML
    void setdifficultyHard(ActionEvent event) {
        gameEnvironment.setDifficulty(Difficulty.HARD);
    }


    @FXML private Button easyButton;

    @FXML private Button hardButton;

    @FXML private Button mediumButton;

    @FXML private TextField nameField;

    @FXML private Slider seasonSlider;

    @FXML
    private Label displayDifficultyLabel;

    @FXML
    private Label displayNameLabel;

    @FXML
    private Label displaySeasonLengthLabel;
}





