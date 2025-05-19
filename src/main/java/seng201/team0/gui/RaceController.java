package seng201.team0.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import seng201.team0.models.*;
import seng201.team0.services.*;

import java.io.IOException;

import java.util.List;
import java.util.Objects;

public class RaceController {

    @FXML
    private ImageView routeImage;
    @FXML
    private Label currentDistanceLabel;
    @FXML
    private Label raceLengthLabel;
    @FXML
    private ProgressBar fuelGauge;
    @FXML
    private VBox leaderboardBox;

    // Popups
    @FXML
    private VBox fuelStopPopup;
    @FXML
    private VBox breakdownPopup;
    @FXML
    private VBox travelerPopup;
    @FXML
    private VBox weatherPopup;

    @FXML
    private Button stopForFuelButton;
    @FXML
    private Button continueWithoutFuelButton;
    @FXML
    private Button withdrawButton;
    @FXML
    private Button repairButton;
    @FXML
    private Button pickUpButton;
    @FXML
    private Button drivePastButton;
    @FXML
    private Button continueAfterWeatherButton;

    //Added some stuff for race animations
    @FXML
    private Rectangle raceTrackLine;
    @FXML
    private ImageView startFlagImageView;
    @FXML
    private ImageView finishFlagImageView;
    @FXML
    private ImageView carImage;
    @FXML
    private Label timeLeftLabel;

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
        if (currentCar == null) {
            System.err.println("No car selected! Cannot calculate speed.");
            return;
        }
        Course course = gameEnvironment.getSelectedCourse();
        int numberOfOpponents = course.getNumberOfOpponents();
        List<OpponentCar> opponents = currentRace.getRoute().generateOpponents(numberOfOpponents);

        double speed = RaceCalculations.calculateEffectiveSpeed(currentCar, currentRace.getRoute());
        double fuelConsumptionRate = RaceCalculations.calculateFuelConsumptionRate(currentCar);
        raceManager = new RaceManager(currentRace, currentCar, opponents, speed, fuelConsumptionRate);
        fuelGauge.setProgress(1);
        raceManager.deductEntryFee(gameEnvironment);
        gameEnvironment.decrementRacesRemaining();
        raceTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            try {
                advanceRace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
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

    private void advanceRace() throws IOException {
        raceManager.advanceRaceTick();
        eventChecker();
        updateUI();
        if (raceManager.isRaceFinished()) {
            finishRace();
        }
    }

    private void updateUI() {
        currentDistanceLabel.setText("Current distance: " + (int) raceManager.getPlayerDistance() + " km");
        raceLengthLabel.setText((int) raceManager.getRace().getRoute().getLength() + " km");
        fuelGauge.setProgress(raceManager.getFuelLevel());
        updateLeaderboardDisplay();
        updateFuelGauge();

        //some code for the car animation
        //might wanna move this code to a controller
        if (carImage != null && raceTrackLine != null && raceManager != null && raceManager.getRace().getRoute().getLength() > 0) {
            double playerDistance = raceManager.getPlayerDistance();
            double routeLength = raceManager.getRace().getRoute().getLength();
            double progress = 0.0;

            if (routeLength > 0) {
                progress = playerDistance / routeLength;
            }
            progress = Math.max(0, Math.min(1, progress));

            double startPosition = raceTrackLine.getLayoutX();
            double trackVisualWidth = raceTrackLine.getWidth();
            double carImageWidth = carImage.getFitWidth();

            double updatedCarPosition = startPosition + (progress * (trackVisualWidth - carImageWidth));

            carImage.setLayoutX(updatedCarPosition);
        }
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
            raceTimeline.pause();
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
        raceManager.handleFuelStop(refuel);
        updateUI();
        raceTimeline.play();
    }

    private void handleRepair(boolean pay) {
        breakdownPopup.setVisible(false);
        raceManager.handleRepair(pay, gameEnvironment);
        raceTimeline.play();
    }

    private void handleTraveler(boolean pickUp) {
        travelerPopup.setVisible(false);
        raceManager.handleTraveler(pickUp, gameEnvironment);
        raceTimeline.play();
    }

    private void handleWeatherContinue() {
        weatherPopup.setVisible(false);
        raceManager.handleWeather(gameEnvironment);
        raceTimeline.play();
    }



    private void finishRace() throws IOException {
        raceTimeline.stop();
        String reason = raceManager.getFinishReason();
        List<String> leaderboard = raceManager.getLeaderboardStrings();
        int earnings = raceManager.getMoneyEarned();
        raceManager.awardPrizeMoney(gameEnvironment);
        int placement;
        if (Objects.equals(reason, "Car broke down! You withdrew from the race.")) {
            placement = raceManager.getOpponents().size() + 1;
        } else {
            placement = raceManager.getPlayerPlacement();
        }

        String placementText;
        switch (placement) {
            case 1:
                placementText = "🏆 You finished 1st!";
                break;
            case 2:
                placementText = "🥈 You finished 2nd!";
                break;
            case 3:
                placementText = "🥉 You finished 3rd!";
                break;
            default:
                placementText = "You finished " + placement + "th.";
                break;
        }

        sceneNavigator.switchToRaceFinishScene(reason, placementText, leaderboard, earnings);
    }
}
