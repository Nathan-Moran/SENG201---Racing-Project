package seng201.team0.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import seng201.team0.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RaceController {

    @FXML private ImageView routeImage;
    @FXML private Label currentDistanceLabel;
    @FXML private Label raceLengthLabel;
    @FXML private ProgressBar fuelGauge;
    @FXML private VBox leaderboardBox;

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

    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    private Race currentRace;  // Holds the current race information
    private Car currentCar;
    private Difficulty currentDifficulty;
    private double playerDistance = 0;
    private boolean isRacing = true;
    private double fuelLevel = 1.0; // 1.0 = full, 0.0 = empty
    private Timeline raceTimeline;
    private List<OpponentCar> opponents;
    private final double speed = RaceCalculations.calculateEffectiveSpeed(currentCar, currentRace.getRoute()); // km per tick
    private final double fuelConsumptionRate = RaceCalculations.calculateFuelConsumptionRate(currentCar);
    private boolean isWaiting = false;
    private int waitTicksRemaining = 0; // 1 tick = 100ms, so 50 = 5 seconds
    private int tickCount = 0;
    int secondsElapsed = tickCount / 10;


    public RaceController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }


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
        this.currentRace = gameEnvironment.getCurrentRace(); // Make race is set
        Route selectedRoute = currentRace.getRoute();
        Difficulty difficulty = gameEnvironment.getDifficulty();
        Course course = gameEnvironment.getSelectedCourse();
        int numberOfOpponents = course.getNumberOfOpponents();
        this.opponents = difficulty.generateOpponents(numberOfOpponents);
        currentRace.updateRace(secondsElapsed);
        raceTimeline.play();
    }

    private void updateUI() {
        currentDistanceLabel.setText("Current distance: " + (int) playerDistance + " km");
        raceLengthLabel.setText("Length: " + (int) currentRace.getRoute().getLength() + " km");
        fuelGauge.setProgress(fuelLevel);
    }

    public void advanceRace() {
        if (!isRacing) return;
        tickCount++;
        if (isWaiting) {
            waitTicksRemaining--;
            if (waitTicksRemaining <= 0) {
                isWaiting = false;
            }
            return; // Don't advance player during repair
        }

        playerDistance += speed;
        fuelLevel -= fuelConsumptionRate; // Adjust rate as needed
        if (fuelLevel < 0) fuelLevel = 0;
        updateUI();
        if (playerDistance >= 30 && playerDistance < 35) {
            fuelStopPopup.setVisible(true);
        } else if (playerDistance >= 50 && playerDistance < 55) {
            breakdownPopup.setVisible(true);
        }
        if (playerDistance >= currentRace.getRoute().getLength() || fuelLevel <= 0) {
            finishRace();
        }
        if (fuelLevel < 0.2) {
            fuelGauge.setStyle("-fx-accent: red;");
        } else if (fuelLevel < 0.5) {
            fuelGauge.setStyle("-fx-accent: orange;");
        } else {
            fuelGauge.setStyle("-fx-accent: green;");
        }
        updateLeaderboardDisplay();
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
            gameEnvironment.setBalance(gameEnvironment.getBalance() - 500);
            isWaiting = true;
            waitTicksRemaining = 50;
        } else {
            raceTimeline.stop();
            isRacing = false;
        }
    }

    private void handleTraveler(boolean pickUp) {
        travelerPopup.setVisible(false);
        if (pickUp) {
            gameEnvironment.setBalance(gameEnvironment.getBalance() + 500);
            isWaiting = true;
            waitTicksRemaining = 50;
        }
    }

    private void handleWeatherContinue() {
        weatherPopup.setVisible(false);
        isRacing = false;
        gameEnvironment.setBalance(gameEnvironment.getBalance() - gameEnvironment.getCurrentRace().getCourse().getEntryFee());
    }

    private void finishRace() {
        raceTimeline.stop();
        isRacing = false;
        // Show result screen
    }

    private void updateLeaderboardDisplay() {
        leaderboardBox.getChildren().remove(1, leaderboardBox.getChildren().size()); // Keep title, remove old entries

        // Player
        Label playerLabel = new Label("Player: " + (int) playerDistance + " km");
        leaderboardBox.getChildren().add(playerLabel);

        // Opponents
        for (int i = 0; i < opponents.size(); i++) {
            OpponentCar opponent = opponents.get(i);
            Label opponentLabel = new Label("Opponent " + (i + 1) + ": " + (int) opponent.getCurrentDistance() + " km");
            leaderboardBox.getChildren().add(opponentLabel);
        }
    }
}
