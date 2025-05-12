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

    public StartMenuController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    @FXML
    private void acceptButton(ActionEvent event) throws IOException {
        //Get chosen difficulty
        //get name
        seasonSlider.getValue();
        nameField.getText();

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





