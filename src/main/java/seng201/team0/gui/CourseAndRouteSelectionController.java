package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import seng201.team0.models.Course;
import seng201.team0.models.Difficulty;
import seng201.team0.models.Race;
import seng201.team0.models.Route;
import seng201.team0.services.GameEnvironment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseAndRouteSelectionController implements Initializable {
    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    public CourseAndRouteSelectionController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }


    @FXML private VBox courseMenu;
    @FXML private Label moneyLabel;
    @FXML private Label racesRemainingLabel;
    @FXML private VBox desertRouteMenu;
    @FXML private VBox mountainRouteMenu;
    @FXML private VBox countryRouteMenu;
    @FXML private VBox cityRouteMenu;

    private Course selectedCourse;
    private Route selectedRoute;

    @FXML
    void onDesertSelected(javafx.scene.input.MouseEvent event) {
        selectedCourse = Course.DESERT;
        showRouteMenu(desertRouteMenu);
    }

    @FXML
    void onMountainSelected(javafx.scene.input.MouseEvent event) {
        selectedCourse = Course.MOUNTAIN;
        showRouteMenu(mountainRouteMenu);
    }

    @FXML
    void onCountrySelected(javafx.scene.input.MouseEvent event) {
        selectedCourse = Course.COUNTRY;
        showRouteMenu(countryRouteMenu);
    }

    @FXML
    void onCitySelected(javafx.scene.input.MouseEvent event) {
        selectedCourse = Course.CITY;
        showRouteMenu(cityRouteMenu);
    }

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

    // Desert routes
    @FXML
    void onDesertDriftSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.DESERT_DRIFT, event);
    }

    @FXML
    void onDesertLongSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.DESERT_LONG, event);
    }

    // Mountain routes
    @FXML
    void onMountainSteepSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.MOUNTAIN_STEEP, event);
    }

    @FXML
    void onMountainCurvesSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.MOUNTAIN_CURVES, event);
    }

    // Country routes
    @FXML
    void onCountryStraightSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.COUNTRY_STRAIGHT, event);
    }

    @FXML
    void onCountryTwistSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.COUNTRY_TWISTY, event);
    }

    // City routes
    @FXML
    void onCityAlleysSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.CITY_ALLEYS, event);
    }

    @FXML
    void onCityTrafficSelected(javafx.scene.input.MouseEvent event) throws IOException {
        createRaceAndStart(Route.CITY_TRAFFIC, event);
    }

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
        selectedRoute = null;
    }

}