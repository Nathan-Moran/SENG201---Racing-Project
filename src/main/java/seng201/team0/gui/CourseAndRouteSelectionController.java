package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import seng201.team0.Course;
import seng201.team0.Race;
import seng201.team0.Route;

import java.io.IOException;

public class CourseAndRouteSelectionController {
    protected GameEnvironment gameEnvironment;
    protected SceneNavigator sceneNavigator;

    public CourseAndRouteSelectionController(GameEnvironment gameEnvironment, SceneNavigator sceneNavigator) {
        this.gameEnvironment = gameEnvironment;
        this.sceneNavigator = sceneNavigator;
    }


    @FXML private VBox courseSelectionMenu;
    @FXML private VBox desertRouteMenu;
    @FXML private VBox mountainRouteMenu;
    @FXML private VBox countryRouteMenu;
    @FXML private VBox cityRouteMenu;

    private Course selectedCourse;
    private Route selectedRoute;

    @FXML
    private void onDesertSelected(ActionEvent event) {
        selectedCourse = Course.DESERT;
        showRouteMenu(desertRouteMenu);
    }

    @FXML
    private void onMountainSelected(ActionEvent event) {
        selectedCourse = Course.MOUNTAIN;
        showRouteMenu(mountainRouteMenu);
    }

    @FXML
    private void onCountrySelected(ActionEvent event) {
        selectedCourse = Course.COUNTRY;
        showRouteMenu(countryRouteMenu);
    }

    @FXML
    private void onCitySelected(ActionEvent event) {
        selectedCourse = Course.CITY;
        showRouteMenu(cityRouteMenu);
    }

    private void showRouteMenu(VBox routeMenu) {
        courseSelectionMenu.setVisible(false);
        courseSelectionMenu.setManaged(false);

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
    private void onDesertDriftSelected(ActionEvent event) throws IOException {
        createRaceAndStart(Route.DESERT_DRIFT, event);
    }

    @FXML
    private void onDesertLongSelected(ActionEvent event) throws IOException {
        createRaceAndStart(Route.DESERT_LONG, event);
    }

    // Mountain routes
    @FXML
    private void onMountainSteepSelected(ActionEvent event) throws IOException {
        createRaceAndStart(Route.MOUNTAIN_STEEP, event);
    }

    @FXML
    private void onMountainCurvesSelected(ActionEvent event) throws IOException {
        createRaceAndStart(Route.MOUNTAIN_CURVES, event);
    }

    // Country routes
    @FXML
    private void onCountryStraightSelected(ActionEvent event) throws IOException {
        createRaceAndStart(Route.COUNTRY_STRAIGHT, event);
    }

    @FXML
    private void onCountryTwistySelected(ActionEvent event) throws IOException {
        createRaceAndStart(Route.COUNTRY_TWISTY, event);
    }

    // City routes
    @FXML
    private void onCityAlleysSelected(ActionEvent event) throws IOException {
        createRaceAndStart(Route.CITY_ALLEYS, event);
    }

    @FXML
    private void onCityTrafficSelected(ActionEvent event) throws IOException {
        createRaceAndStart(Route.CITY_TRAFFIC, event);
    }

    private void createRaceAndStart(Route selectedRoute, ActionEvent event) throws IOException {
        Race newRace = new Race(selectedCourse, selectedRoute);
        gameEnvironment.setCurrentRace(newRace);
        sceneNavigator.switchToSceneRace(event);
    }
}