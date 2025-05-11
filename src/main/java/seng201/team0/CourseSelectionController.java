package seng201.team0;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import seng201.team0.gui.GameEnvironment;

public class CourseSelectionController {

    @FXML private VBox courseSelectionMenu;
    @FXML private VBox desertRouteMenu;
    @FXML private VBox mountainRouteMenu;
    @FXML private VBox countryRouteMenu;
    @FXML private VBox cityRouteMenu;

    @FXML
    private void onDesertSelected(ActionEvent event) {
        GameEnvironment.setSelectedCourse(Course.DESERT);
        showRouteMenu(desertRouteMenu);
    }

    @FXML
    private void onMountainSelected(ActionEvent event) {
        GameEnvironment.setSelectedCourse(Course.MOUNTAIN);
        showRouteMenu(mountainRouteMenu);
    }

    @FXML
    private void onCountrySelected(ActionEvent event) {
        GameEnvironment.setSelectedCourse(Course.COUNTRY);
        showRouteMenu(countryRouteMenu);
    }

    @FXML
    private void onCitySelected(ActionEvent event) {
        GameEnvironment.setSelectedCourse(Course.CITY);
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
        private void onDesertDriftSelected(ActionEvent event) {
            GameEnvironment.setSelectedRoute(Route.DESERT_DRIFT);
            startRace();
        }

        @FXML
        private void onDesertLongSelected(ActionEvent event) {
            GameEnvironment.setSelectedRoute(Route.DESERT_LONG);
            startRace();
        }

        @FXML
        private void onMountainSteepSelected(ActionEvent event) {
            GameEnvironment.setSelectedRoute(Route.MOUNTAIN_STEEP);
            startRace();
        }

        @FXML
        private void onMountainCurvesSelected(ActionEvent event) {
            GameEnvironment.setSelectedRoute(Route.MOUNTAIN_CURVES);
            startRace();
        }

        @FXML
        private void onCountryStraightSelected(ActionEvent event) {
            GameEnvironment.setSelectedRoute(Route.COUNTRY_STRAIGHT);
            startRace();
        }

        @FXML
        private void onCountryTwistSelected(ActionEvent event) {
            GameEnvironment.setSelectedRoute(Route.COUNTRY_TWISTY);
            startRace();
        }

        @FXML
        private void onCityAlleysSelected(ActionEvent event) {
            GameEnvironment.setSelectedRoute(Route.CITY_ALLEYS);
            startRace();
        }

        @FXML
        private void onCityTrafficSelected(ActionEvent event) {
            GameEnvironment.setSelectedRoute(Route.CITY_TRAFFIC);
            startRace();
        }

        private void startRace() {
//        ScreenNavigator.loadScreen("RaceScreen.fxml");
        }
}

