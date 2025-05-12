package seng201.team0.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    @FXML private ProgressBar progressBar;

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
    private final double raceLength = 100; // Placeholder - set this dynamically based on route
    private boolean isRacing = true;

    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    public RaceController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }


    public void initialize() {
        progressBar.setProgress(0);
        updateUI();

        stopForFuelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleFuelStop(true);
            }
        });

        continueWithoutFuelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleFuelStop(false);
            }
        });

        repairButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleRepair(true);
            }
        });

        withdrawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleRepair(false);
            }
        });

        pickUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleTraveler(true);
            }
        });

        drivePastButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleTraveler(false);
            }
        });

        continueAfterWeatherButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleWeatherContinue();
            }
        });
    }

    private void updateUI() {
        currentDistanceLabel.setText("Current distance: " + (int) playerDistance + " km");
        raceLengthLabel.setText("Length: " + (int) raceLength + " km");
        progressBar.setProgress(playerDistance / raceLength);
    }

    public void advanceRace(double distance) {
        if (!isRacing) return;

        playerDistance += distance;
        updateUI();

        // Trigger events at certain distances (example logic)
        if (playerDistance >= 30 && playerDistance < 35) {
            fuelStopPopup.setVisible(true);
        }

        if (playerDistance >= 50 && playerDistance < 55) {
            breakdownPopup.setVisible(true);
        }

        if (playerDistance >= raceLength) {
            finishRace();
        }
    }

    private void handleFuelStop(boolean refuel) {
        fuelStopPopup.setVisible(false);
        if (refuel) {
            // Add refueling time logic
        }
    }

    private void handleRepair(boolean pay) {
        breakdownPopup.setVisible(false);
        if (pay) {
            // Deduct money and add delay logic
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
        // Refund player and end race
    }

    private void finishRace() {
        isRacing = false;
        // Show result screen, calculate position and award prize
    }
}