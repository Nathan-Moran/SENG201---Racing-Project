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

/**
 * Controller for the Race Scene. Manages the real-time simulation and
 * visual representation of a race, including player and opponent progress,
 * fuel levels, event pop-ups, and race conclusion logic.
 */
public class RaceController {

    /**
     * ImageView for displaying the background image of the race route.
     */
    @FXML
    private ImageView routeImage;
    /**
     * Label for displaying the player's current money.
     */
    @FXML
    private Label moneyLabel;
    /**
     * Label for displaying the total length of the current race route.
     */
    @FXML
    private Label raceLengthLabel;
    /**
     * ProgressBar for visualizing the player's current fuel level.
     */
    @FXML
    private ProgressBar fuelGauge;
    /**
     * VBox container for displaying the race leaderboard.
     */
    @FXML
    private VBox leaderboardBox;
    /**
     * Label for displaying the remaining time in the race.
     */
    @FXML
    private Label timerLabel;

    // Popups
    /**
     * VBox container for the fuel stop event popup.
     */
    @FXML
    private VBox fuelStopPopup;
    /**
     * VBox container for the breakdown event popup.
     */
    @FXML
    private VBox breakdownPopup;
    /**
     * VBox container for the traveler event popup.
     */
    @FXML
    private VBox travelerPopup;
    /**
     * VBox container for the weather event popup.
     */
    @FXML
    private VBox weatherPopup;
    /**
     * VBox container for the traveler pay confirmation popup.
     */
    @FXML
    private VBox travelerPayPopup;

    /**
     * Button to stop for fuel during a fuel stop event.
     */
    @FXML
    private Button stopForFuelButton;
    /**
     * Button to continue without fueling during a fuel stop event.
     */
    @FXML
    private Button continueWithoutFuelButton;
    /**
     * Button to withdraw from the race (typically due to breakdown).
     */
    @FXML
    private Button withdrawButton;
    /**
     * Button to repair the car during a breakdown event.
     */
    @FXML
    private Button repairButton;
    /**
     * Button to pick up a traveler during a traveler event.
     */
    @FXML
    private Button pickUpButton;
    /**
     * Button to drive past a traveler during a traveler event.
     */
    @FXML
    private Button drivePastButton;
    /**
     * Button to continue after a weather event (race cancellation).
     */
    @FXML
    private Button continueAfterWeatherButton;

    //Race animations
    /**
     * Rectangle representing the visual racetrack line for car animation.
     */
    @FXML
    private Rectangle raceTrackLine;

    /**
     * ImageView for displaying the player's car during the race animation.
     */
    @FXML
    private ImageView carImage;

    /**
     * The game environment, providing access to game state and services.
     */
    protected GameEnvironment gameEnvironment;
    /**
     * The scene navigator for switching between different application scenes.
     */
    protected SceneNavigator sceneNavigator;

    /**
     * The RaceManager instance handling the core race simulation logic.
     */
    private RaceManager raceManager;
    /**
     * Timeline for advancing the race simulation ticks.
     */
    private Timeline raceTimeline;
    /**
     * Timeline for updating the on-screen timer.
     */
    private Timeline timerTimeline;
    /**
     * Timeline for controlling the visibility of the traveler pay popup.
     */
    private Timeline travelerPayTimeline;
    /**
     * Constructs a RaceController.
     * @param gameEnvironment The current game environment.
     * @param sceneNavigator The scene navigator for scene transitions.
     */
    public RaceController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
        gameEnvironment.getMusicManager().initializeMusic("/music/RaceMusic.mp3");
    }

    /**
     * Initializes the Race Scene controller. This method is automatically called
     * by the FXMLLoader after the FXML file has been loaded. It sets up the
     * RaceManager, initializes UI elements, configures event handlers for pop-up buttons,
     * and starts the race simulation timelines.
     */
    public void initialize() {
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
                }
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
        raceManager = new RaceManager(gameEnvironment, currentRace, currentCar, opponents, speed, fuelConsumptionRate);
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
                timerLabel.setText(String.format("%.0f", Math.max(0, timeLeft)));
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

    /**
     * Determines the image path for the race background based on the selected course.
     * @param course The Course enum representing the selected course.
     * @return The string path to the background image resource.
     */
    private String getImagePathForCourse(Course course) {
        return switch (course.getName().toLowerCase()) {
            case "desert" -> "/fxml/desert.jpg";
            case "mountain" -> "/fxml/mountain.png";
            case "country" -> "/fxml/country.png";
            case "city" -> "/fxml/city.jpg";
            default -> "/fxml/desert.png";
        };
    }

    /**
     * Starts the race simulation by playing the race and timer timelines.
     */
    private void startRace() {
        raceTimeline.play();
        timerTimeline.play();
    }

    /**
     * Advances the race simulation by one tick. This method is called repeatedly
     * by the {@code raceTimeline}. It updates the race manager, checks for events,
     * updates the UI, and triggers the race finish sequence if the race concludes.
     * @throws IOException If an error occurs during scene transition to the finish screen.
     */
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

    /**
     * Updates all dynamic UI elements on the race screen, including money,
     * race length, fuel gauge, leaderboard, and car animation.
     */
    private void updateUI() {
        moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
        raceLengthLabel.setText(raceManager.getRace().getRoute().getLength() + " km");
        fuelGauge.setProgress(raceManager.getFuelLevel());
        updateLeaderboardDisplay();
        updateFuelGauge();

        if (carImage != null && raceTrackLine != null && raceManager != null && raceManager.getRace().getRoute().getLength() > 0) {
            double updatedCarPosition = getUpdatedCarPosition();

            carImage.setLayoutX(updatedCarPosition);
        }

    }

    private double getUpdatedCarPosition() {
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

        return startPosition + (progress * (trackVisualWidth - carImageWidth));
    }


    /**
     * Updates the color of the fuel gauge based on the current fuel level
     * to provide visual warnings for low fuel.
     */
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

    /**
     * Updates the leaderboard display in the UI, showing player and opponent distances.
     */
    private void updateLeaderboardDisplay() {
        leaderboardBox.getChildren().remove(1, leaderboardBox.getChildren().size());

        // Player
        Label playerLabel = new Label(gameEnvironment.getName() + ": "+ (int) raceManager.getPlayerDistance() + " km");
        leaderboardBox.getChildren().add(playerLabel);

        // Opponents
        List<OpponentCar> opponents = raceManager.getOpponents();
        for (int i = 0; i < opponents.size(); i++) {
            OpponentCar opponent = opponents.get(i);
            Label opponentLabel = new Label(opponent.getName() + " : " + (int) opponent.getCurrentDistance() + " km");
            leaderboardBox.getChildren().add(opponentLabel);
        }
    }


    /**
     * Checks for active race events (breakdown, traveler, weather, fuel stop)
     * and pauses the race timeline to display the corresponding pop-up.
     */
    private void eventChecker() {
        RaceEvent event = raceManager.getCurrentEvent();
        if (event != null && raceManager.isWaiting()) {
            raceTimeline.pause();
            timerTimeline.pause();
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
            travelerPayPopup.setVisible(false);
        }
    }

    /**
     * Handles the player's decision at a fuel stop event.
     * @param refuel True if the player chooses to refuel, false otherwise.
     */
    private void handleFuelStop(boolean refuel) {
        fuelStopPopup.setVisible(false);
        raceManager.handleFuelStop(refuel);
        updateUI();
        raceTimeline.play();
        timerTimeline.play();
    }

    /**
     * Handles the player's decision during a car breakdown event.
     * @param pay True if the player chooses to pay for repairs, false to withdraw.
     * @throws IOException If an error occurs during scene transition to the finish screen.
     */
    private void handleRepair(boolean pay) throws IOException {
        breakdownPopup.setVisible(false);
        if (pay) {
            if (gameEnvironment.getBalance() < 250) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Funds");
                alert.setHeaderText(null);
                alert.setContentText("You do not have the required funds to repair");
                alert.showAndWait();
                breakdownPopup.setVisible(true);
                return;
            }
            raceManager.handleRepair(true, gameEnvironment);
        } else {

            raceManager.handleRepair(false, gameEnvironment);
            finishRace();
            return;
        }
        raceTimeline.play();
        timerTimeline.play();
    }


    /**
     * Handles the player's decision during a traveler event.
     * @param pickUp True if the player chooses to pick up the traveler, false to drive past.
     */
    private void handleTraveler(boolean pickUp) {
        travelerPopup.setVisible(false);
        if (pickUp) {
            travelerPayPopup.setVisible(true);
            travelerPayTimeline.playFromStart();
            raceManager.handleTraveler(true, gameEnvironment);
        } else {
            raceManager.handleTraveler(false, gameEnvironment);
        }
        raceTimeline.play();
        timerTimeline.play();
    }

    /**
     * Handles the continuation after a weather event, which cancels the race.
     * @throws IOException If an error occurs during scene transition to the finish screen.
     */
    private void handleWeatherContinue() throws IOException {
        weatherPopup.setVisible(false);
        raceManager.handleWeather(gameEnvironment);
        raceTimeline.play();
        timerTimeline.play();
        finishRace();
    }


    /**
     * Concludes the race simulation. Stops all timelines, retrieves race results
     * from the RaceManager, awards prize money, updates game environment stats,
     * and navigates to the Race Finish Scene.
     * @throws IOException If an error occurs during scene transition to the finish screen.
     */
    private void finishRace() throws IOException {
        raceTimeline.stop();
        timerTimeline.stop();
        String reason = raceManager.getFinishReason();
        List<String> leaderboard = raceManager.getLeaderboardStrings();

        if (!raceManager.isRaceCancelled()) {
            raceManager.awardPrizeMoney(gameEnvironment);
        }

        int earnings = raceManager.getMoneyEarned();

        int placement;
        if (Objects.equals(reason, "Car broke down! You withdrew from the race.") || Objects.equals(reason, "Weather has cancelled the race!") || Objects.equals(reason, "Out of fuel!")) {
            placement = raceManager.getOpponents().size() + 1;
        } else {
            placement = raceManager.getPlayerPlacement();
        }

        String placementText = getString(reason, placement);
        gameEnvironment.getMusicManager().stopMusic();
        gameEnvironment.getMusicManager().initializeMusic("/music/MenuMusic.mp3");
        gameEnvironment.updateHasWonCourse(gameEnvironment.getSelectedCourse(), placement);
        gameEnvironment.getShopService().unlockNewCars();
        gameEnvironment.addRacePlacement(placement);
        gameEnvironment.addPrizeMoney(earnings);
        sceneNavigator.switchToRaceFinishScene(reason, placementText, leaderboard, earnings);
    }

    private static String getString(String reason, int placement) {
        String placementText;
        switch (reason) {
            case "Weather has cancelled the race!" -> placementText = "Race Cancelled Due to Weather";
            case "Out of fuel!" -> placementText = "Ran out of fuel!";
            case "Time ran out!" -> placementText = "Time ran out!";
            case "Finished the race!" -> placementText = switch (placement) {
                case 1 -> "🏆 You finished 1st!";
                case 2 -> "🥈 You finished 2nd!";
                case 3 -> "🥉 You finished 3rd!";
                default -> "You finished " + placement + "th.";
            };
            case null, default -> placementText = "Race Over";
        }
        return placementText;
    }
}