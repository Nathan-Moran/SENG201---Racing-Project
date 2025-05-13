package seng201.team0;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import seng201.team0.gui.GameEnvironment;
import seng201.team0.gui.SceneNavigator;

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

    @FXML
    private void onDesertSelected(ActionEvent event) {
        gameEnvironment.setSelectedCourse(Course.DESERT);
        showRouteMenu(desertRouteMenu);
    }

    @FXML
    private void onMountainSelected(ActionEvent event) {
        gameEnvironment.setSelectedCourse(Course.MOUNTAIN);
        showRouteMenu(mountainRouteMenu);
    }

    @FXML
    private void onCountrySelected(ActionEvent event) {
        gameEnvironment.setSelectedCourse(Course.COUNTRY);
        showRouteMenu(countryRouteMenu);
    }

    @FXML
    private void onCitySelected(ActionEvent event) {
        gameEnvironment.setSelectedCourse(Course.CITY);
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

    @FXML
    private void onDesertDriftSelected(ActionEvent event) throws IOException {
        gameEnvironment.setSelectedRoute(Route.DESERT_DRIFT);
        startRace(event);
    }

    @FXML
    private void onDesertLongSelected(ActionEvent event) throws IOException {
        gameEnvironment.setSelectedRoute(Route.DESERT_LONG);
        startRace(event);
    }

    @FXML
    private void onMountainSteepSelected(ActionEvent event) throws IOException {
        gameEnvironment.setSelectedRoute(Route.MOUNTAIN_STEEP);
        startRace(event);
    }

    @FXML
    private void onMountainCurvesSelected(ActionEvent event) throws IOException {
        gameEnvironment.setSelectedRoute(Route.MOUNTAIN_CURVES);
        startRace(event);
    }

    @FXML
    private void onCountryStraightSelected(ActionEvent event) throws IOException {
        gameEnvironment.setSelectedRoute(Route.COUNTRY_STRAIGHT);
        startRace(event);
    }

    @FXML
    private void onCountryTwistySelected(ActionEvent event) throws IOException {
        gameEnvironment.setSelectedRoute(Route.COUNTRY_TWISTY);
        startRace(event);
    }

    @FXML
    private void onCityAlleysSelected(ActionEvent event) throws IOException {
        gameEnvironment.setSelectedRoute(Route.CITY_ALLEYS);
        startRace(event);
    }

    @FXML
    private void onCityTrafficSelected(ActionEvent event) throws IOException {
        gameEnvironment.setSelectedRoute(Route.CITY_TRAFFIC);
        startRace(event);
    }

    private void startRace(ActionEvent event) throws IOException {
        sceneNavigator.switchToSceneRace(event);
    }
}
