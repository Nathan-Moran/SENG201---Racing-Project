package seng201.team0.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
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
    private Label moneyLabel;
    @FXML
    private Label raceLengthLabel;
    @FXML
    private ProgressBar fuelGauge;
    @FXML
    private VBox leaderboardBox;
    @FXML
    private Label timerLabel; // Added timer label

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
    private VBox travelerPayPopup;

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
    private Timeline timerTimeline;
    private Timeline travelerPayTimeline;

    public RaceController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    public void initialize() {
        // Setup race manager
        Race currentRace = gameEnvironment.getCurrentRace();
        Car currentCar = gameEnvironment.getSelectedCar();
        Course selectedCourse = gameEnvironment.getSelectedCourse();
        if (selectedCourse != null) {
            String imagePath = getImagePathForCourse(selectedCourse);
            if (imagePath != null && !imagePath.isEmpty()) {
                try {
                    Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
                    routeImage.setImage(backgroundImage);
                } catch (NullPointerException e) {
                    System.err.println("Error loading background image: " + imagePath);
                    // Optionally set a default image here if loading fails
                }
            } else {

            }
        }
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

        timerLabel.setText(String.format("%.0f", raceManager.getRaceDurationSeconds()));

        raceTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            try {
                advanceRace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        raceTimeline.setCycleCount(Timeline.INDEFINITE);

        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (raceManager.isRacing()) {
                double timeLeft = raceManager.getRaceDurationSeconds() - raceManager.getTimeElapsedSeconds();
                timerLabel.setText(String.format("%.0f", Math.max(0, timeLeft))); // Ensure no negative time
            }
        }));
        timerTimeline.setCycleCount((int) raceManager.getRaceDurationSeconds());

        travelerPayTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> travelerPayPopup.setVisible(false))
        );
        travelerPayTimeline.setCycleCount(1);

        stopForFuelButton.setOnAction(event -> handleFuelStop(true));
        continueWithoutFuelButton.setOnAction(event -> handleFuelStop(false));
        repairButton.setOnAction(event -> {
            try {
                handleRepair(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        withdrawButton.setOnAction(event -> {
            try {
                handleRepair(false);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        pickUpButton.setOnAction(event -> handleTraveler(true));
        drivePastButton.setOnAction(event -> handleTraveler(false));
        continueAfterWeatherButton.setOnAction(event -> {
            try {
                handleWeatherContinue();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        updateUI();
        startRace();
    }

    private String getImagePathForCourse(Course course) {
        // You'll need to define the logic to map courses to image paths
        switch (course.getName().toLowerCase()) {
            case "desert":
                return "/fxml/desert.jpg";
            case "mountain":
                return "/fxml/mountain.png";
            case "country":
                return "/fxml/country.png";
            case "city":
                return "/fxml/city.png";
            default:
                return "/fxml/desert.png"; // Default image
        }
    }

    private void startRace() {
        raceTimeline.play();
        timerTimeline.play();
    }

    private void advanceRace() throws IOException {
        if (raceManager.isRacing()) {
            raceManager.advanceRaceTick();
            eventChecker();
            updateUI();
            if (raceManager.isRaceFinished()) {
                finishRace();
            }
        }
    }

    private void updateUI() {
        moneyLabel.setText(String.valueOf((int) gameEnvironment.getBalance()));
        raceLengthLabel.setText((int) raceManager.getRace().getRoute().getLength() + " km");
        fuelGauge.setProgress(raceManager.getFuelLevel());
        updateLeaderboardDisplay();
        updateFuelGauge();
        //some code for the car animation
        //might want to move this code to a controller
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
            timerTimeline.pause(); // Pause the timer during events
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
        } else if (!raceManager.isWaiting() && raceManager.getCurrentEvent() == null && travelerPayPopup.isVisible()) {
            travelerPayPopup.setVisible(false); // Ensure it's hidden if waiting ends without a new event
        }
    }

    private void handleFuelStop(boolean refuel) {
        fuelStopPopup.setVisible(false);
        raceManager.handleFuelStop(refuel);
        updateUI();
        raceTimeline.play();
        timerTimeline.play(); // Resume timer
    }

    private void handleRepair(boolean pay) throws IOException { // Add throws IOException here
        breakdownPopup.setVisible(false);
        if (pay) {
            if (gameEnvironment.getBalance() < 250) { // Assuming REPAIR_COST is 250
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Funds");
                alert.setHeaderText(null);
                alert.setContentText("You do not have the required funds to repair");
                alert.showAndWait();
                // If funds are insufficient, stay in breakdown state, don't resume race
                breakdownPopup.setVisible(true); // Re-show popup
                return; // Exit method
            }
            raceManager.handleRepair(true, gameEnvironment); // Pass true as player chose to pay
        } else {
            // Player chose NOT to pay (withdraw)
            raceManager.handleRepair(false, gameEnvironment); // Pass false for pay
            finishRace(); // Immediately finish the race
            return; // Exit method
        }
        raceTimeline.play();
        timerTimeline.play(); // Resume timer
    }


    private void handleTraveler(boolean pickUp) {
        travelerPopup.setVisible(false);
        if (pickUp) {
            travelerPayPopup.setVisible(true);
            travelerPayTimeline.playFromStart();
            raceManager.handleTraveler(true, gameEnvironment);
            raceTimeline.play();
            timerTimeline.play();
        } else {
            raceManager.handleTraveler(false, gameEnvironment);
            raceTimeline.play();
            timerTimeline.play();
        }
    }

    private void handleWeatherContinue() throws IOException {
        weatherPopup.setVisible(false);
        raceManager.handleWeather(gameEnvironment);
        raceTimeline.play();
        timerTimeline.play(); // Resume timer
        finishRace(); // Trigger finish sequence to show cancellation
    }


    private void finishRace() throws IOException {
        raceTimeline.stop();
        timerTimeline.stop();
        String reason = raceManager.getFinishReason();
        List<String> leaderboard = raceManager.getLeaderboardStrings();
        int earnings = raceManager.getMoneyEarned();
        if (!raceManager.isRaceCancelled()) {
            raceManager.awardPrizeMoney(gameEnvironment);
        }
        int placement;
        if (Objects.equals(reason, "Car broke down! You withdrew from the race.") || Objects.equals(reason, "Weather has cancelled the race!") || Objects.equals(reason, "Out of fuel!")) {
            placement = raceManager.getOpponents().size() + 1; // Last place or indicates withdrawal/cancellation
        } else {
            placement = raceManager.getPlayerPlacement();
        }

        String placementText;
        if (Objects.equals(reason, "Weather has cancelled the race!")) {
            placementText = "Race Cancelled Due to Weather";
        } else if (Objects.equals(reason, "Out of fuel!")) {
            placementText = "Ran out of fuel!";
        } else if (Objects.equals(reason, "Time ran out!")) {
            placementText = "Time ran out!";
        } else if (Objects.equals(reason, "Finished the race!")) {
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
        } else {
            placementText = "Race Over"; // Default message
        }
        gameEnvironment.updateHasWonCourse(gameEnvironment.getSelectedCourse(), placement);
        gameEnvironment.getShopService().unlockNewPartsAndCars();
        gameEnvironment.addRacePlacement(placement);
        gameEnvironment.addPrizeMoney(earnings);
        sceneNavigator.switchToRaceFinishScene(reason, placementText, leaderboard, earnings);
    }
}