package seng201.team0.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
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
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the Race screen, managing the visual representation and user interaction during a race.
 * This class orchestrates the race simulation by interacting with the {@link RaceManager} and updates
 * the UI accordingly, including player and opponent progress, fuel levels, and race events.
 */
public class RaceController implements Initializable {

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
    private Label timerLabel;

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
    private ImageView startFlagImageView; // Not directly used in provided methods but often for visual effects
    @FXML
    private ImageView finishFlagImageView; // Not directly used in provided methods but often for visual effects
    @FXML
    private ImageView carImage;
    @FXML
    private Label timeLeftLabel; // Appears to be a duplicate/alternative to timerLabel, verify usage


    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    private RaceManager raceManager;
    private Timeline raceTimeline;
    private Timeline timerTimeline;
    private Timeline travelerPayTimeline;

    /**
     * Constructs a RaceController with the given game environment and scene navigator.
     * @param gameEnvironment The {@link GameEnvironment} instance for the current game session.
     * @param sceneNavigator The {@link SceneNavigator} to handle scene transitions.
     */
    public RaceController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    /**
     * Initializes the Race screen. This method is automatically called after the FXML file has been loaded.
     * It sets up the {@link RaceManager}, loads the route image, initializes UI elements,
     * and sets up event handlers for user interactions and timelines for race simulation.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Setup race manager
        Race currentRace = gameEnvironment.getCurrentRace();
        Car currentCar = gameEnvironment.getSelectedCar();
        Course selectedCourse = gameEnvironment.getSelectedCourse();

        // Load route image based on selected course
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
                // Handle case where imagePath is null or empty
            }
        }
        if (currentCar == null) {
            System.err.println("No car selected! Cannot calculate speed.");
            return; // Prevent further initialization if no car is selected
        }

        // Generate opponents and initialize RaceManager
        Course course = gameEnvironment.getSelectedCourse();
        int numberOfOpponents = course.getNumberOfOpponents();
        List<OpponentCar> opponents = currentRace.getRoute().generateOpponents(numberOfOpponents);

        double speed = RaceCalculations.calculateEffectiveSpeed(currentCar, currentRace.getRoute());
        double fuelConsumptionRate = RaceCalculations.calculateFuelConsumptionRate(currentCar);
        raceManager = new RaceManager(currentRace, currentCar, opponents, speed, fuelConsumptionRate);

        // Initialize UI elements and game state for the race
        fuelGauge.setProgress(1); // Full fuel at start
        raceManager.deductEntryFee(gameEnvironment); // Deduct entry fee
        gameEnvironment.decrementRacesRemaining(); // Decrement races left in season

        timerLabel.setText(String.format("%.0f", raceManager.getRaceDurationSeconds())); // Display initial race duration

        // Setup race simulation timeline
        raceTimeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            try {
                advanceRace();
            } catch (IOException e) {
                // Handle or log the IOException, potentially show an error to the user
                throw new RuntimeException("Failed to advance race due to I/O error: " + e.getMessage(), e);
            }
        }));
        raceTimeline.setCycleCount(Timeline.INDEFINITE); // Runs continuously until stopped

        // Setup race timer timeline for UI
        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (raceManager.isRacing()) {
                double timeLeft = raceManager.getRaceDurationSeconds() - raceManager.getTimeElapsedSeconds();
                timerLabel.setText(String.format("%.0f", Math.max(0, timeLeft))); // Display remaining time, not less than 0
            }
        }));
        timerTimeline.setCycleCount((int) raceManager.getRaceDurationSeconds()); // Runs for the duration of the race

        // Setup timeline for temporary traveler pay popup
        travelerPayTimeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> travelerPayPopup.setVisible(false))
        );
        travelerPayTimeline.setCycleCount(1); // Plays once

        // Set up button actions for race events
        stopForFuelButton.setOnAction(event -> handleFuelStop(true));
        continueWithoutFuelButton.setOnAction(event -> handleFuelStop(false));
        repairButton.setOnAction(event -> {
            try {
                handleRepair(true);
            } catch (IOException e) {
                throw new RuntimeException("Failed to handle repair due to I/O error: " + e.getMessage(), e);
            }
        });
        withdrawButton.setOnAction(event -> {
            try {
                handleRepair(false); // Withdraws if repair not paid
            } catch (IOException e) {
                throw new RuntimeException("Failed to handle withdrawal due to I/O error: " + e.getMessage(), e);
            }
        });
        pickUpButton.setOnAction(event -> handleTraveler(true));
        drivePastButton.setOnAction(event -> handleTraveler(false));
        continueAfterWeatherButton.setOnAction(event -> {
            try {
                handleWeatherContinue();
            } catch (IOException e) {
                throw new RuntimeException("Failed to handle weather continue due to I/O error: " + e.getMessage(), e);
            }
        });

        // Initial UI update and start the race
        updateUI();
        startRace();
    }

    /**
     * Retrieves the image path for a given course.
     * This method maps {@link Course} names to their corresponding background image files.
     * @param course The {@link Course} for which to get the image path.
     * @return A string representing the relative path to the course's background image.
     */
    private String getImagePathForCourse(Course course) {
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
                return "/fxml/desert.png"; // Default image if course name doesn't match
        }
    }

    /**
     * Starts the race simulation by playing the associated timelines.
     */
    private void startRace() {
        raceTimeline.play();
        timerTimeline.play();
    }

    /**
     * Advances the race by one tick in the {@link RaceManager}.
     * Checks for race events and updates the UI after each tick.
     * If the race finishes, it calls the {@link #finishRace()} method.
     * @throws IOException If there's an issue with scene navigation (e.g., FXML loading).
     */
    private void advanceRace() throws IOException {
        if (raceManager.isRacing()) {
            raceManager.advanceRaceTick();
            eventChecker(); // Check for and display any new events
            updateUI();     // Update all UI elements
            if (raceManager.isRaceFinished()) {
                finishRace(); // End the race if manager indicates it's finished
            }
        }
    }

    /**
     * Updates all dynamic UI elements on the Race screen.
     * This includes player money, race length display, fuel gauge, leaderboard,
     * and car animation along the track.
     */
    private void updateUI() {
        moneyLabel.setText(String.valueOf((int) gameEnvironment.getBalance()));
        raceLengthLabel.setText((int) raceManager.getRace().getRoute().getLength() + " km");
        fuelGauge.setProgress(raceManager.getFuelLevel());
        updateLeaderboardDisplay();
        updateFuelGauge(); // Update fuel gauge color based on level

        // Update car animation position
        if (carImage != null && raceTrackLine != null && raceManager != null && raceManager.getRace().getRoute().getLength() > 0) {
            double playerDistance = raceManager.getPlayerDistance();
            double routeLength = raceManager.getRace().getRoute().getLength();
            double progress = 0.0;

            if (routeLength > 0) {
                progress = playerDistance / routeLength;
            }
            progress = Math.max(0, Math.min(1, progress)); // Clamp progress between 0 and 1

            double startPosition = raceTrackLine.getLayoutX();
            double trackVisualWidth = raceTrackLine.getWidth();
            double carImageWidth = carImage.getFitWidth();

            // Calculate new car position along the track visual
            double updatedCarPosition = startPosition + (progress * (trackVisualWidth - carImageWidth));

            carImage.setLayoutX(updatedCarPosition);
        }
    }

    /**
     * Updates the color of the fuel gauge based on the current fuel level.
     * Red for low fuel, orange for medium, and green for high fuel.
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
     * Updates the display of the leaderboard, showing player and opponent distances.
     * Clears previous entries and adds new ones based on current race manager data.
     */
    private void updateLeaderboardDisplay() {
        // Remove existing leaderboard entries (keeping the title/header)
        if (leaderboardBox.getChildren().size() > 1) {
            leaderboardBox.getChildren().remove(1, leaderboardBox.getChildren().size());
        }

        // Add player's current distance
        Label playerLabel = new Label("Player: " + (int) raceManager.getPlayerDistance() + " km");
        leaderboardBox.getChildren().add(playerLabel);

        // Add opponents' current distances
        List<OpponentCar> opponents = raceManager.getOpponents();
        for (int i = 0; i < opponents.size(); i++) {
            OpponentCar opponent = opponents.get(i);
            Label opponentLabel = new Label("Opponent " + (i + 1) + ": " + (int) opponent.getCurrentDistance() + " km");
            leaderboardBox.getChildren().add(opponentLabel);
        }
    }

    /**
     * Checks for active race events and displays the appropriate popup.
     * Pauses the race timeline while an event popup is active, requiring user interaction.
     */
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
            // If waiting ends and no new event, hide travelerPayPopup (if still visible)
            travelerPayPopup.setVisible(false);
        }
    }

    /**
     * Handles the player's choice at a fuel stop event.
     * Hides the fuel stop popup, updates the race manager based on the choice,
     * updates the UI, and resumes the race timeline.
     * @param refuel {@code true} if the player chose to refuel, {@code false} otherwise.
     */
    private void handleFuelStop(boolean refuel) {
        fuelStopPopup.setVisible(false); // Hide the popup
        raceManager.handleFuelStop(refuel); // Let manager handle the logic
        updateUI(); // Update UI to reflect changes (e.g., fuel level)
        raceTimeline.play(); // Resume race simulation
        timerTimeline.play(); // Resume timer
    }

    /**
     * Handles the player's choice at a breakdown event.
     * If the player chooses to pay for repairs, it checks for sufficient funds,
     * deducts money, and resumes the race after a short wait. If funds are insufficient,
     * an error is displayed. If the player chooses not to pay, they withdraw from the race.
     * @param pay {@code true} if the player chose to pay for repairs, {@code false} otherwise.
     * @throws IOException If there's an issue with scene navigation (e.g., FXML loading) when finishing the race.
     */
    private void handleRepair(boolean pay) throws IOException {
        breakdownPopup.setVisible(false); // Hide the breakdown popup
        final int REPAIR_COST = 250; // Defined as a constant for clarity

        if (pay) {
            if (gameEnvironment.getBalance() < REPAIR_COST) {
                // Show error if funds are insufficient
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Insufficient Funds");
                alert.setHeaderText(null);
                alert.setContentText("You do not have the required funds to repair your car. You must withdraw from the race.");
                alert.showAndWait();
                // Re-show breakdown popup as player cannot pay and needs to choose again
                breakdownPopup.setVisible(true);
                return; // Exit method, do not proceed with repair/resume race
            }
            raceManager.handleRepair(true, gameEnvironment); // Player pays for repair
        } else {
            // Player chose NOT to pay (withdraw)
            raceManager.handleRepair(false, gameEnvironment); // Player withdraws
            finishRace(); // Immediately finish the race due to withdrawal
            return; // Exit method as race is finished
        }
        raceTimeline.play(); // Resume race simulation
        timerTimeline.play(); // Resume timer
    }

    /**
     * Handles the player's choice at a traveler event.
     * If the player chooses to pick up the traveler, a temporary "pay" popup is displayed,
     * and the race resumes after a short wait. Otherwise, the race simply resumes.
     * @param pickUp {@code true} if the player chose to pick up the traveler, {@code false} otherwise.
     */
    private void handleTraveler(boolean pickUp) {
        travelerPopup.setVisible(false); // Hide the traveler popup
        if (pickUp) {
            travelerPayPopup.setVisible(true); // Show temporary pay popup
            travelerPayTimeline.playFromStart(); // Start timer for pay popup
            raceManager.handleTraveler(true, gameEnvironment); // Let manager handle profit and wait
            raceTimeline.play(); // Resume race simulation
            timerTimeline.play(); // Resume timer
        } else {
            raceManager.handleTraveler(false, gameEnvironment); // Let manager handle simply clearing event
            raceTimeline.play(); // Resume race simulation
            timerTimeline.play(); // Resume timer
        }
    }

    /**
     * Handles the player's decision to continue after a weather event.
     * Hides the weather popup, instructs the race manager to handle the weather event
     * (which typically cancels the race), and then triggers the race finish sequence.
     * @throws IOException If there's an issue with scene navigation (e.g., FXML loading) when finishing the race.
     */
    private void handleWeatherContinue() throws IOException {
        weatherPopup.setVisible(false); // Hide the weather popup
        raceManager.handleWeather(gameEnvironment); // Let manager handle weather effects (race cancellation)
        raceTimeline.play(); // Resume race simulation (briefly, before finish)
        timerTimeline.play(); // Resume timer (briefly, before finish)
        finishRace(); // Trigger finish sequence to reflect cancellation
    }

    /**
     * Finalizes the race, stopping all timelines and transitioning to the race finish scene.
     * It retrieves all final race results and statistics from the {@link RaceManager}.
     * @throws IOException If there's an issue with scene navigation (e.g., FXML loading).
     */
    private void finishRace() throws IOException {
        raceTimeline.stop(); // Stop the main race simulation
        timerTimeline.stop(); // Stop the race timer

        // Let RaceManager handle all post-race updates to GameEnvironment
        raceManager.processRaceOutcome(gameEnvironment);

        // Retrieve final results for display
        String reason = raceManager.getFinishReason();
        String placementText = raceManager.getFinalPlacementText();
        List<String> leaderboard = raceManager.getLeaderboardStrings();
        int earnings = raceManager.getMoneyEarned();

        // Navigate to the race finish scene with all relevant data
        sceneNavigator.switchToRaceFinishScene(reason, placementText, leaderboard, earnings);
    }
}