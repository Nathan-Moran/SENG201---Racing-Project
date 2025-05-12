package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RaceController {

    @FXML private ImageView routeImage;
    @FXML private Label currentDistanceLabel;
    @FXML private Label raceLengthLabel;
    @FXML private ProgressBar fuelGauge;

    // Popups
    @FXML private VBox fuelStopPopup;
    @FXML private VBox breakdownPopup;
    @FXML private VBox travelerPopup;
    @FXML private VBox weatherPopup;

    @FXML private Button stopForFuelButton;
    @FXML private Button continueWithoutFuelButton;
    @FXML private Button withdrawButton;
    @FXML private Button repairButton;
    @FXML private Button pickUpButton;
    @FXML private Button drivePastButton;
    @FXML private Button continueAfterWeatherButton;

    private double playerDistance = 0;
    private final double raceLength = 100;
    private boolean isRacing = true;

    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    public RaceController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }


    public void initialize() {
        fuelGauge.setProgress(1);
        updateUI();

        stopForFuelButton.setOnAction(event -> handleFuelStop(true));
        continueWithoutFuelButton.setOnAction(event -> handleFuelStop(false));
        repairButton.setOnAction(event -> handleRepair(true));
        withdrawButton.setOnAction(event -> handleRepair(false));
        pickUpButton.setOnAction(event -> handleTraveler(true));
        drivePastButton.setOnAction(event -> handleTraveler(false));
        continueAfterWeatherButton.setOnAction(event -> handleWeatherContinue());
    }

    private void updateUI() {
        currentDistanceLabel.setText("Current distance: " + (int) playerDistance + " km");
        raceLengthLabel.setText("Length: " + (int) raceLength + " km");
        fuelGauge.setProgress(playerDistance / raceLength);
    }

    public void advanceRace(double distance) {
        if (!isRacing) return;

        playerDistance += distance;
        updateUI();

        if (playerDistance >= 30 && playerDistance < 35) {
            fuelStopPopup.setVisible(true);
        } else if (playerDistance >= 50 && playerDistance < 55) {
            breakdownPopup.setVisible(true);
        }

        if (playerDistance >= raceLength) {
            finishRace();
        }
    }

    private void handleFuelStop(boolean refuel) {
        fuelStopPopup.setVisible(false);
        if (refuel) {
            // Add refueling logic
        }
    }

    private void handleRepair(boolean pay) {
        breakdownPopup.setVisible(false);
        if (pay) {
            // Deduct money, add delay
        } else {
            isRacing = false;
            // Show DNF screen
        }
    }

    private void handleTraveler(boolean pickUp) {
        travelerPopup.setVisible(false);
        if (pickUp) {
            // Add money, delay
        }
    }

    private void handleWeatherContinue() {
        weatherPopup.setVisible(false);
        isRacing = false;
        // Refund player, end race
    }

    private void finishRace() {
        isRacing = false;
        // Show result screen
    }
}
