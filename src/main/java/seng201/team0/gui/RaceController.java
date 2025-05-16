package seng201.team0.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import seng201.team0.models.*;
import seng201.team0.services.*;

import java.util.List;

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

    private RaceManager raceManager;
    private Timeline raceTimeline;

    public RaceController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    public void initialize() {
        // Setup race manager
        Race currentRace = gameEnvironment.getCurrentRace();
        Car currentCar = gameEnvironment.getSelectedCar();
        Difficulty difficulty = gameEnvironment.getDifficulty();
        Course course = gameEnvironment.getSelectedCourse();
        int numberOfOpponents = course.getNumberOfOpponents();
        List<OpponentCar> opponents = difficulty.generateOpponents(numberOfOpponents);

        double speed = RaceCalculations.calculateEffectiveSpeed(currentCar, currentRace.getRoute());
        double fuelConsumptionRate = RaceCalculations.calculateFuelConsumptionRate(currentCar);

        raceManager = new RaceManager(currentRace, currentCar, opponents, speed, fuelConsumptionRate);

        fuelGauge.setProgress(1);

        raceTimeline = new Timeline(new KeyFrame(Duration.millis(100), event -> advanceRace()));
        raceTimeline.setCycleCount(Timeline.INDEFINITE);
        raceTimeline.play();
        stopForFuelButton.setOnAction(event -> handleFuelStop(true));
        continueWithoutFuelButton.setOnAction(event -> handleFuelStop(false));
        repairButton.setOnAction(event -> handleRepair(true));
        withdrawButton.setOnAction(event -> handleRepair(false));
        pickUpButton.setOnAction(event -> handleTraveler(true));
        drivePastButton.setOnAction(event -> handleTraveler(false));
        continueAfterWeatherButton.setOnAction(event -> handleWeatherContinue());

        updateUI();
    }

    private void advanceRace() {
        raceManager.advanceRaceTick();
        eventChecker();

        double playerDistance = raceManager.getPlayerDistance();
        updateUI();
        if (raceManager.isRaceFinished()) {
            finishRace();
        }
    }

    private void updateUI() {
        currentDistanceLabel.setText("Current distance: " + (int) raceManager.getPlayerDistance() + " km");
        raceLengthLabel.setText("Length: " + (int) raceManager.getRace().getRoute().getLength() + " km");
        fuelGauge.setProgress(raceManager.getFuelLevel());
        updateLeaderboardDisplay();
        updateFuelGauge();
    }

    private void updateFuelGauge() {
        double fuelLevel = raceManager.getFuelLevel();
        if (fuelLevel < 0.2) {
            fuelGauge.setStyle("-fx-accent: red;");
        } else if (fuelLevel < 0.5) {
            fuelGauge.setStyle("-fx-accent: orange;");
        } else {
            fuelGauge.setStyle("-fx-accent: green;");
        }
    }

    private void updateLeaderboardDisplay() {
        leaderboardBox.getChildren().remove(1, leaderboardBox.getChildren().size()); // Keep title, remove old entries

        // Player
        Label playerLabel = new Label("Player: " + (int) raceManager.getPlayerDistance() + " km");
        leaderboardBox.getChildren().add(playerLabel);

        // Opponents
        List<OpponentCar> opponents = raceManager.getOpponents();
        for (int i = 0; i < opponents.size(); i++) {
            OpponentCar opponent = opponents.get(i);
            Label opponentLabel = new Label("Opponent " + (i + 1) + ": " + (int) opponent.getCurrentDistance() + " km");
            leaderboardBox.getChildren().add(opponentLabel);
        }
    }

    private void eventChecker() {
        RaceEvent event = raceManager.getCurrentEvent();
        if (event != null && raceManager.isWaiting()) {
            switch (event.getType()) {
                case BREAKDOWN:
                    breakdownPopup.setVisible(true);
                    break;
                case TRAVELER:
                    travelerPopup.setVisible(true);
                    break;
                case WEATHER:
                    weatherPopup.setVisible(true);
                    break;
                case FUEL_STOP:
                    fuelStopPopup.setVisible(true);
                    break;
            }
        }

    }

    private void handleFuelStop(boolean refuel) {
        fuelStopPopup.setVisible(false);
        if (refuel) {
            raceManager.refuel();
        }
        raceManager.clearCurrentEvent();
        raceManager.setWaiting(false, 0);
        updateUI();
    }

    private void handleRepair(boolean pay) {
        breakdownPopup.setVisible(false);
        if (pay) {
            gameEnvironment.setBalance(gameEnvironment.getBalance() - 500);
            raceManager.setWaiting(true, 50);
        } else {
            raceTimeline.stop();
        }
        raceManager.clearCurrentEvent();
    }

    private void handleTraveler(boolean pickUp) {
        travelerPopup.setVisible(false);
        if (pickUp) {
            gameEnvironment.setBalance(gameEnvironment.getBalance() + 500);
            raceManager.setWaiting(true, 50);
        }
        raceManager.clearCurrentEvent();
    }

    private void handleWeatherContinue() {
        weatherPopup.setVisible(false);
        raceTimeline.stop();
        gameEnvironment.setBalance(gameEnvironment.getBalance() - gameEnvironment.getCurrentRace().getCourse().getEntryFee());
        raceManager.clearCurrentEvent();
    }

    private void finishRace() {
        raceTimeline.stop();
        // Show results screen or navigate to next scene here
    }
}
