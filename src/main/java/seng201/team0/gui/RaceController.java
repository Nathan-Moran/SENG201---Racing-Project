package seng201.team0.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
    private double fuelLevel = 1.0; // 1.0 = full, 0.0 = empty
    private Timeline raceTimeline;
    private final double speed = 0.5; // km per tick
    private final double fuelConsumptionRate = 0.005;

    public void initialize() {
        fuelGauge.setProgress(1);
        raceTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> advanceRace()));
        raceTimeline.setCycleCount(Timeline.INDEFINITE);
        updateUI();
        stopForFuelButton.setOnAction(event -> handleFuelStop(true));
        continueWithoutFuelButton.setOnAction(event -> handleFuelStop(false));
        repairButton.setOnAction(event -> handleRepair(true));
        withdrawButton.setOnAction(event -> handleRepair(false));
        pickUpButton.setOnAction(event -> handleTraveler(true));
        drivePastButton.setOnAction(event -> handleTraveler(false));
        continueAfterWeatherButton.setOnAction(event -> handleWeatherContinue());
        raceTimeline.play();
    }

    private void updateUI() {
        currentDistanceLabel.setText("Current distance: " + (int) playerDistance + " km");
        raceLengthLabel.setText("Length: " + (int) raceLength + " km");
        fuelGauge.setProgress(fuelLevel);
    }

    public void advanceRace() {
        if (!isRacing) return;
        playerDistance += speed;
        fuelLevel -= fuelConsumptionRate; // Adjust rate as needed
        if (fuelLevel < 0) fuelLevel = 0;
        updateUI();
        if (playerDistance >= 30 && playerDistance < 35) {
            fuelStopPopup.setVisible(true);
        } else if (playerDistance >= 50 && playerDistance < 55) {
            breakdownPopup.setVisible(true);
        }
        if (playerDistance >= raceLength || fuelLevel <= 0) {
            finishRace();
        }
        if (fuelLevel < 0.2) {
            fuelGauge.setStyle("-fx-accent: red;");
        } else if (fuelLevel < 0.5) {
            fuelGauge.setStyle("-fx-accent: orange;");
        } else {
            fuelGauge.setStyle("-fx-accent: green;");
        }
    }

    private void handleFuelStop(boolean refuel) {
        fuelStopPopup.setVisible(false);
        if (refuel) {
            fuelGauge.setProgress(1);
        }
    }

    private void handleRepair(boolean pay) {
        breakdownPopup.setVisible(false);
        if (pay) {

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
        raceTimeline.stop();
        isRacing = false;
        // Show result screen
    }
}
