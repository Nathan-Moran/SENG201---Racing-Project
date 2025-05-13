package seng201.team0.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


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
        Boolean invalidName = false;

        gameEnvironment.setSeasonLength((int)seasonSlider.getValue());
        name = (String)nameField.getText();

        //add name to main menu
        if (name.length() >= 3 && name.length() <= 15) {
            for (int i = 0; i < nameField.getText().length(); i++) {
                // Checks each character in Name to check if it is a special character
                if (!Character.isLetterOrDigit(name.charAt(i)) && !Character.isWhitespace(name.charAt(i))) {
                    invalidName = true;
                }
            }
        } else {
            invalidName = true;
        }
        if (invalidName) {
            nameField.set
        }
    }

        sceneNavigator.switchToSceneCarSelector(event);

    }




        @FXML
        private Button easyButton;

        @FXML
        private Button hardButton;

        @FXML
        private Button mediumButton;

        @FXML
        private TextField nameField;


        @FXML
        private Slider seasonSlider;



}





