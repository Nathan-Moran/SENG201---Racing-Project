package seng201.team0.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import seng201.team0.models.Difficulty;
import seng201.team0.services.BalanceManager;
import seng201.team0.services.ControllerLogicManager;
import seng201.team0.services.GameEnvironment;


public class StartMenuController {
    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;
    private ControllerLogicManager controllerLogicManager;

    String name;

    public StartMenuController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        this.controllerLogicManager = gameEnvironment.getControllerLogicManager();
    }

    @FXML
    private void setSeasonLength(MouseEvent event) {
        displaySeasonLengthLabel.setText(String.valueOf(seasonSlider.getValue()));
    }


    @FXML
    void setName(KeyEvent event) {
        displayNameLabel.setText(nameField.getText());
    }

    @FXML
    private void acceptButton(ActionEvent event) throws IOException {


        gameEnvironment.setSeasonLength((int) seasonSlider.getValue());
        name = nameField.getText();

        nameField.clear();
        nameField.setPromptText(controllerLogicManager.nameChecker(name));
        nameField.setStyle("-fx-prompt-text-fill: red;");
        sceneNavigator.switchToSceneCarSelector(event);


    }

    @FXML
    private void setdifficultyEasy(ActionEvent event) throws IOException {
        displayDifficultyLabel.setText("Easy");
        gameEnvironment.setDifficulty(Difficulty.EASY);
    }

    @FXML
    void setdifficultyMedium(ActionEvent event) {
        displayDifficultyLabel.setText("Medium");
        gameEnvironment.setDifficulty(Difficulty.MEDIUM);
    }

    @FXML
    void setdifficultyHard(ActionEvent event) {
        displayDifficultyLabel.setText("Hard");
        gameEnvironment.setDifficulty(Difficulty.HARD);
    }


    @FXML private TextField nameField;

    @FXML private Slider seasonSlider;

    @FXML
    private Label displayDifficultyLabel;

    @FXML
    private Label displayNameLabel;

    @FXML
    private Label displaySeasonLengthLabel;
}





