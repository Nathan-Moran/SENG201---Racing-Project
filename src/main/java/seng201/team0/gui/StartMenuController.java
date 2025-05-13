package seng201.team0.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
    private void acceptButton(ActionEvent event) throws IOException {
        //Get chosen difficulty
        Boolean invalidNameLength = false;
        Boolean invalidNameCharacters = false;

        gameEnvironment.setSeasonLength((int) seasonSlider.getValue());
        name = (String) nameField.getText();

        //add name to main menu and change to display error in dedicated errorbox
        if (name.length() >= 3 && name.length() <= 15) {
            for (int i = 0; i < nameField.getText().length(); i++) {
                // Checks each character in Name to check if it is a special character
                if (!Character.isLetterOrDigit(name.charAt(i))) {
                    invalidNameCharacters = true;
                }
            }
        } else {
            invalidNameLength = true;
        }
        if (invalidNameLength) {
            nameField.setText("Name must be between 3 and 15 characters");
        } else if (invalidNameCharacters) {
            nameField.setText("Name must not contain special characters");
        } else if (invalidNameCharacters && invalidNameLength) {
            nameField.setText("Name must be between 3 and 15 characters and not contain special characters");
        } else {
            sceneNavigator.switchToSceneCarSelector(event);
        }
    }

    @FXML
    private void setdifficultyEasy(ActionEvent event) throws IOException {
        System.out.println("Easy");
        gameEnvironment.setDifficulty(Difficulty.EASY);
    }

    @FXML
    void setdifficultyMedium(ActionEvent event) {
        System.out.println("Medium");
        gameEnvironment.setDifficulty(Difficulty.MEDIUM);
    }

    @FXML
    void setdifficultyHard(ActionEvent event) {
        System.out.println("Hard");
        gameEnvironment.setDifficulty(Difficulty.HARD);
    }


    @FXML private Button easyButton;

    @FXML private Button hardButton;

    @FXML private Button mediumButton;

    @FXML private TextField nameField;

    @FXML private Slider seasonSlider;
}





