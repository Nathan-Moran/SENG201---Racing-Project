package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seng201.team0.models.Course;
import seng201.team0.models.Difficulty;
import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Controller for the Course and Route Selection Scene. Allows the player to
 * choose a course and then a specific route within that course to begin a race.
 * Displays player's current money and races remaining, and indicates completed courses.
 */
public class CourseAndRouteSelectionController implements Initializable {
    /**
     * The game environment, providing access to game state and services.
     */
    protected GameEnvironment gameEnvironment;
    /**
     * The scene navigator for handling transitions between different scenes.
     */
    protected SceneNavigator sceneNavigator;

    /**
     * Constructs a CourseAndRouteSelectionController.
     * @param gameEnvironment The current game environment.
     * @param sceneNavigator The scene navigator for scene transitions.
     */
    public CourseAndRouteSelectionController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }

    /**
     * VBox container for the main course selection menu.
     */
    @FXML private VBox courseMenu;
    /**
     * Label displaying the player's current money.
     */
    @FXML private Label moneyLabel;
    /**
     * Label displaying the number of races remaining in the season.
     */
    @FXML private Label racesRemainingLabel;
    /**
     * VBox container for the desert route selection menu.
     */
    @FXML private VBox desertRouteMenu;
    /**
     * VBox container for the mountain route selection menu.
     */
    @FXML private VBox mountainRouteMenu;
    /**
     * VBox container for the country route selection menu.
     */
    @FXML private VBox countryRouteMenu;
    /**
     * VBox container for the city route selection menu.
     */
    @FXML private VBox cityRouteMenu;

    /**
     * Label indicating if the Desert course has been completed.
     */
    @FXML private Label desertCompletedLabel;
    /**
     * Label indicating if the Mountain course has been completed.
     */
    @FXML private Label mountainCompletedLabel;
    /**
     * Label indicating if the Country course has been completed.
     */
    @FXML private Label countryCompletedLabel;
    /**
     * Label indicating if the City course has been completed.
     */
    @FXML private Label cityCompletedLabel;

    /**
     * A map linking Course enum values to their corresponding completion labels.
     */
    private Map<Course, Label> courseLabels = new HashMap<>();

    /**
     * The currently selected {@link Course} for the race.
     */
    private Course selectedCourse;

    /**
     * Initializes the controller. This method is automatically called by the FXMLLoader
     * after the FXML file has been loaded. It sets up initial UI visibility,
     * updates money and races remaining labels, and displays completion indicators for courses.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        courseMenu.setVisible(true);
        courseMenu.setManaged(true);

        moneyLabel.setText(String.valueOf(gameEnvironment.getBalance()));
        racesRemainingLabel.setText(String.valueOf(gameEnvironment.getRacesRemaining()));


        desertRouteMenu.setVisible(false);
        desertRouteMenu.setManaged(false);

        mountainRouteMenu.setVisible(false);
        mountainRouteMenu.setManaged(false);

        countryRouteMenu.setVisible(false);
        countryRouteMenu.setManaged(false);

        cityRouteMenu.setVisible(false);
        cityRouteMenu.setManaged(false);

        selectedCourse = null;


        courseLabels.put(Course.DESERT, desertCompletedLabel);
        courseLabels.put(Course.MOUNTAIN, mountainCompletedLabel);
        courseLabels.put(Course.COUNTRY, countryCompletedLabel);
        courseLabels.put(Course.CITY, cityCompletedLabel);


        for (Course course : Course.values()) {
            Label label = courseLabels.get(course);
            if (gameEnvironment.hasWonCourse(course)) {
                label.setVisible(true);
            } else {
                label.setVisible(false);
            }
        }
    }


    /**
     * Handles the selection of the Desert course. Checks for sufficient funds
     * and then displays the desert route selection menu.
     * @param event The MouseEvent that triggered this method.
     */
    @FXML
    void onDesertSelected(javafx.scene.input.MouseEvent event) {
        selectedCourse = Course.DESERT;
        if (gameEnvironment.getBalance() < selectedCourse.getEntryFee()) {
            showAlert("Invalid Funds", "You do not have the required funds to pick this course");
            return;
        }
        showRouteMenu(desertRouteMenu);
    }

    /**
     * Handles the selection of the Mountain course. Checks for sufficient funds
     * and then displays the mountain route selection menu.
     * @param event The MouseEvent that triggered this method.
     */
    @FXML
    void onMountainSelected(javafx.scene.input.MouseEvent event) {
        selectedCourse = Course.MOUNTAIN;
        if (gameEnvironment.getBalance() < selectedCourse.getEntryFee()) {
            showAlert("Invalid Funds", "You do not have the required funds to pick this course");
            return;
        }
        showRouteMenu(mountainRouteMenu);
    }

    /**
     * Handles the selection of the Country course. Checks for sufficient funds
     * and then displays the country route selection menu.
     * @param event The MouseEvent that triggered this method.
     */
    @FXML
    void onCountrySelected(javafx.scene.input.MouseEvent event) {
        selectedCourse = Course.COUNTRY;
        if (gameEnvironment.getBalance() < selectedCourse.getEntryFee()) {
            showAlert("Invalid Funds", "You do not have the required funds to pick this course");
            return;
        }
        showRouteMenu(countryRouteMenu);
    }

    /**
     * Handles the selection of the City course. Checks for sufficient funds
     * and then displays the city route selection menu.
     * @param event The MouseEvent that triggered this method.
     */
    @FXML
    void onCitySelected(javafx.scene.input.MouseEvent event) {
        selectedCourse = Course.CITY;
        if (gameEnvironment.getBalance() < selectedCourse.getEntryFee()) {
            showAlert("Invalid Funds", "You do not have the required funds to pick this course");
            return;
        }
        showRouteMenu(cityRouteMenu);
    }

    /**
     * Displays an error alert dialog with a specified title and message.
     * @param title The title of the alert dialog.
     * @param message The main message content of the alert dialog.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Hides all course and route menus, then makes the specified route menu visible.
     * @param routeMenu The VBox representing the route menu to be displayed.
     */
    void showRouteMenu(VBox routeMenu) {
        courseMenu.setVisible(false);
        courseMenu.setManaged(false);

        desertRouteMenu.setVisible(false);
        desertRouteMenu.setManaged(false);

        mountainRouteMenu.setVisible(false);
        mountainRouteMenu.setManaged(false);

        countryRouteMenu.setVisible(false);
        countryRouteMenu.setManaged(false);

        cityRouteMenu.setVisible(false);
        cityRouteMenu.setManaged(false);

        routeMenu.setVisible(true);
        routeMenu.setManaged(true);
    }

    /**
     * Handles the selection of the Desert Drift route. Creates and starts a new race.
     * @param event The MouseEvent that triggered this method.
     * @throws IOException If an error occurs during scene transition to the race scene.
     */
    @FXML
    void onDesertDriftSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.DESERT_DRIFT, event);
    }

    /**
     * Handles the selection of the Desert Long route. Creates and starts a new race.
     * @param event The MouseEvent that triggered this method.
     * @throws IOException If an error occurs during scene transition to the race scene.
     */
    @FXML
    void onDesertLongSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.DESERT_LONG, event);
    }

    /**
     * Handles the selection of the Mountain Steep route. Creates and starts a new race.
     * @param event The MouseEvent that triggered this method.
     * @throws IOException If an error occurs during scene transition to the race scene.
     */
    @FXML
    void onMountainSteepSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.MOUNTAIN_STEEP, event);
    }

    /**
     * Handles the selection of the Mountain Curves route. Creates and starts a new race.
     * @param event The MouseEvent that triggered this method.
     * @throws IOException If an error occurs during scene transition to the race scene.
     */
    @FXML
    void onMountainCurvesSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.MOUNTAIN_CURVES, event);
    }

    /**
     * Handles the selection of the Country Straight route. Creates and starts a new race.
     * @param event The MouseEvent that triggered this method.
     * @throws IOException If an error occurs during scene transition to the race scene.
     */
    @FXML
    void onCountryStraightSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.COUNTRY_STRAIGHT, event);
    }

    /**
     * Handles the selection of the Country Twisty route. Creates and starts a new race.
     * @param event The MouseEvent that triggered this method.
     * @throws IOException If an error occurs during scene transition to the race scene.
     */
    @FXML
    void onCountryTwistSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.COUNTRY_TWISTY, event);
    }

    /**
     * Handles the selection of the City Alleys route. Creates and starts a new race.
     * @param event The MouseEvent that triggered this method.
     * @throws IOException If an error occurs during scene transition to the race scene.
     */
    @FXML
    void onCityAlleysSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.CITY_ALLEYS, event);
    }

    /**
     * Handles the selection of the City Traffic route. Creates and starts a new race.
     * @param event The MouseEvent that triggered this method.
     * @throws IOException If an error occurs during scene transition to the race scene.
     */
    @FXML
    void onCityTrafficSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.CITY_TRAFFIC, event);
    }

    /**
     * Creates a new {@link Race} instance with the selected course, route, and current difficulty,
     * then switches the scene to the Race simulation.
     * @param selectedRoute The {@link Route} chosen for the race.
     * @param event The MouseEvent that triggered this method.
     * @throws IOException If an error occurs during scene transition to the race scene.
     */
    void createRaceAndStart(Route selectedRoute, javafx.scene.input.MouseEvent event) throws IOException {
        Difficulty difficulty = gameEnvironment.getDifficulty();
        if (selectedCourse == null || selectedRoute == null || difficulty == null) {
            System.err.println("Course, Route, or Difficulty not selected!");
            return;
        }
        Race newRace = new Race(selectedCourse, selectedRoute, difficulty);
        gameEnvironment.setCurrentRace(newRace);
        sceneNavigator.switchToSceneRace(event);
    }

}